package normalizers;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.nodeTypes.NodeWithAnnotations;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.DoStmt;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.ForeachStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.stmt.SwitchStmt;
import com.github.javaparser.ast.stmt.WhileStmt;
import com.github.javaparser.ast.type.ReferenceType;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.github.javaparser.ast.visitor.Visitable;
import com.github.javaparser.utils.Pair;

import datastructures.VariableEnv;
import visitors.ASTUtil;

public class VariableNameNormalizer extends Normalizer {

	private String prefix = "";

	static String cleanTypeName(Type type){
		String ret = type.toString();
		ret = ret.replaceAll("\\[\\]", "_arr_");
		ret = ret.replaceAll("[^A-Za-z0-9_\\-]", "__");
		//System.err.println("Cleaning string " + type + " to " + ret);
		return ret;
	}

	/**
	 * Create a new variable name normalizer, appending the given prefix to all strings
	 * @param prefix
	 */
	public VariableNameNormalizer(String prefix){
		this.prefix = prefix;
	}

	/**
	 * Create a new variable name normalizer, renaming variables to a standard form
	 * similar to DeBruijn indices
	 */
	public VariableNameNormalizer(){

	}



	/**
	 * The arguments we pass down to the tree as we traverse it.
	 * The VariableEnv is an immutable map from strings to strings, which is used to store
	 * the new names for variables.
	 * 
	 * The Collection of pairs stores declarations made in child nodes.
	 * Parents pass their children a reference to it, so they can see which names their children declared.
	 * This is mostly useful for BlockStatements: when we have a bunch of statements, we need to see which
	 * names are declared on one line, so we can add them to the environment for the following lines.
	 * Or we will use it for method declarations, so we can have the declared parameters in the environment
	 * when traversing the body.
	 * 
	 */
	private  class VisitInfo{
		public VariableEnv gamma;
		public Collection<Pair<String, String>> declsForParent;

		public VisitInfo(VariableEnv argGamma, Collection<Pair<String, String>> decls){
			gamma = argGamma;
			declsForParent = decls;
		}
	}

	private  void visitAnnotations(NodeWithAnnotations<?> n, VisitInfo arg) {
		visitList(n.getAnnotations(), arg);
	}

	private  void visitList(NodeList<?> nlist, VisitInfo arg) {
		for (Node n : nlist){
			visit(n, arg);
		}
	}
	
	private class VariableNameVisitor extends ModifierVisitor<VisitInfo>{
		
	}

	private  void visit(Node n, VisitInfo info){
		//Names get renamed to whatever fresh name we've generated and stored in the info
		if (n instanceof SimpleName){
			SimpleName sn = (SimpleName)n;
			//System.err.println("Visiting SimpleName " + n);
			String newIdent = info.gamma.lookup(sn.getIdentifier());
			if (newIdent != null){
				//System.err.println("Renaming " + n + " to " + newIdent);
				sn.setIdentifier(newIdent);
				//Tell our parent that we declared a new name
				//info.declsForParent.add(new Pair<String, String>(sn.getIdentifier(), newIdent));
			}

		}
		//Same idea as SimpleName
		else if (n instanceof NameExpr){
			NameExpr name = (NameExpr)n;
			//visitComment(n, info);
			//System.err.println("Visiting NameExpr " + n);
			String newIdent = info.gamma.lookup(name.getNameAsString());
			if (newIdent != null){
				//System.err.println("Renaming " + n + " to " + newIdent);
				name.setName(newIdent);
			}
			else {
				//System.err.println("Couldn't find name " + n.getNameAsString() + " in gamma ");
			}
		}
		//When a variable is declared, we have to inform our infoNode of the new declaration
		//So it can add it to the environment and rename any occurrences that come after this
		//This is where the fresh names are generated
		else if (n instanceof VariableDeclarator){

			VariableDeclarator vd = (VariableDeclarator)n;

			visit(vd.getType(), info);

			//visitComment(n, info); //TODO need this?

			//We don't visit the name for this variable, because we don't want to rename it yet
			//i.e. we want to distinguish names in declarations from names in expressions
			final SimpleName id = (SimpleName) vd.getName();

			//We generate a new, standardized name for this declaration, based on its type
			String cleanTypeName = cleanTypeName(vd.getType());
			String newName = info.gamma.freshKey(prefix + "_var_" + cleanTypeName);
			vd.setName(newName);

			//Tell our parent that we declared a new name
			info.declsForParent.add(new Pair<String, String>(id.getIdentifier(), newName));

			if (vd.getInitializer().isPresent()) {
				visit(vd.getInitializer().get(), info);
			}
		}
		//Similar to variable declarator, but for method params
		else if (n instanceof Parameter) {

			Parameter p = (Parameter) n;
			//visitComment(n, info);
			visitAnnotations(p, info);

			final SimpleName id = (SimpleName) p.getName();

			visit(p.getType(), info);

			//We generate a new, standardized name for this declaration, based on its type
			String newName = info.gamma.freshKey(prefix + "_param_" + cleanTypeName(p.getType()));
			//System.err.println("Choosing new name " + newName + " for param " + n);
			p.setName(newName);

			//Tell our parent that we declared a new name
			info.declsForParent.add(new Pair<String, String>(id.getIdentifier(), newName));

		}
		//Block stmt is just like everything else
		//Except variables have scope limited to the block statement
		else if (n instanceof BlockStmt) {

			//The environment of variables declared after each statement
			VariableEnv gammaCurrent = info.gamma;

			//Where we'll store the modified statements from our list after we process them


			for (Node stmt : ASTUtil.orderedChildNodes(n)){

				//Make the map where we store declarations from the statement, so we can use them later on
				LinkedList<Pair<String, String>> childDecls = new LinkedList<Pair<String, String>>();

				//Process the statement, receiving its declarations in a new list
				visit(stmt, new VisitInfo(gammaCurrent, childDecls) );

				//Add all the declarations from inside that statement to our environemnt for future statements
				gammaCurrent = gammaCurrent.appendFront(childDecls);
				//Don't tell our parents who we declared
				//since they were limited to the scope of this block
			}

		}
		//Everything else: we assume children are in sequenced order
		//And we process them in sequence, adding variables declared
		//to the environments for the children that follow
		else {

			//The environment of variables declared after each statement
			VariableEnv gammaCurrent = info.gamma;

			//Where we'll store the modified statements from our list after we process them


			for (Node stmt : ASTUtil.orderedChildNodes(n)){

				//Make the map where we store declarations from the statement, so we can use them later on
				LinkedList<Pair<String, String>> childDecls = new LinkedList<Pair<String, String>>();

				//Process the statement, receiving its declarations in a new list
				visit(stmt, new VisitInfo(gammaCurrent, childDecls) );

				//Add all the declarations from inside that statement to our environemnt for future statements
				gammaCurrent = gammaCurrent.appendFront(childDecls);
				//Tell our parents who we declared
				info.declsForParent.addAll(childDecls);
			}

		}
	}

	






	@Override
	public Node result() {
		//System.err.println("Fixing names for " + this.startBlock);
		Node ret = startBlock;
		visit(ret, new VisitInfo(VariableEnv.empty(), new LinkedList<Pair<String, String>>()));
		//System.err.println("Changed body:\n" + startBlock);
		//System.err.println("to:\n" + ret);
		return ret;
	}

}
