package normalizers;

import java.util.Collection;
import java.util.LinkedList;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.nodeTypes.NodeWithAnnotations;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.type.ReferenceType;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.github.javaparser.ast.visitor.Visitable;
import com.github.javaparser.utils.Pair;

import datastructures.VariableEnv;

public class VariableNameNormalizer extends Normalizer {
	
	static String cleanTypeName(Type type){
		return type.toString().replaceAll("[^-_A-Za-z0-9]/", "__");
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
	private class VisitInfo{
		public VariableEnv gamma;
		public Collection<Pair<String, String>> declsForParent;

		public VisitInfo(VariableEnv argGamma, Collection<Pair<String, String>> decls){
			gamma = argGamma;
			declsForParent = decls;
		}
	}

	private class VariableNameVisitor extends ModifierVisitor<VisitInfo>{
		public VariableNameVisitor(){}

		//We override this to change variable names to their normalized form
		@Override
		public Visitable visit(final VariableDeclarator n, final VisitInfo info) {

			Type type = (Type) n.getType().accept(this, info);
			if (type == null) {
				return null;
			}
			n.setType(type);

			//visitComment(n, info); //TODO need this?

			//We don't visit the name for this variable, because we don't want to rename it yet
			//i.e. we want to distinguish names in declarations from names in expressions
			final SimpleName id = (SimpleName) n.getName();
			if (id == null) {
				return null;
			}

			//We generate a new, standardized name for this declaration, based on its type
			String cleanTypeName = cleanTypeName(type);
			String newName = info.gamma.freshKey("var_" + cleanTypeName);
			n.setName(newName);

			//Tell our parent that we declared a new name
			info.declsForParent.add(new Pair<String, String>(id.getIdentifier(), newName));

			if (n.getInitializer().isPresent()) {
				n.setInitializer((Expression) n.getInitializer().get().accept(this, info));
			}
			return n;
		}

		//Similar to variable declarator, but for method params
		@Override
		public Visitable visit(final Parameter n, final VisitInfo info) {
			//visitComment(n, info);
			visitAnnotations(n, info);

			final SimpleName id = (SimpleName) n.getName();

			Type type = (Type) n.getType().accept(this, info);

			//We generate a new, standardized name for this declaration, based on its type
			String newName = info.gamma.freshKey("param_" + cleanTypeName(type));
			System.out.println("Choosing new name " + newName + " for param " + n);
			n.setName(newName);

			//Tell our parent that we declared a new name
			info.declsForParent.add(new Pair<String, String>(id.getIdentifier(), newName));

			n.setType(type);
			return n;
		}

		//When we see a variable, we rename it to whatever we've stored in our Gamma
		@Override
		public Visitable visit(SimpleName n, VisitInfo info) {
			//System.out.println("Visiting SimpleName " + n);
			String newIdent = info.gamma.lookup(n.getIdentifier());
			if (newIdent != null){
				System.out.println("Renaming " + n + " to " + newIdent);
				n.setIdentifier(newIdent);
			}
			return n;
		}

		//Same as simple name
		//TODO need both?
		@Override
		public Visitable visit(final NameExpr n, final VisitInfo info) {
			//visitComment(n, info);
			//System.out.println("Visiting NameExpr " + n);
			String newIdent = info.gamma.lookup(n.getNameAsString());
			if (newIdent != null){
				System.out.println("Renaming " + n + " to " + newIdent);
				n.setName(newIdent);
			}
			else {
				System.out.println("Couldn't find name " + n.getNameAsString() + " in gamma ");
			}
			return n;
		}

		//We need to process the statements of a BlockStmt in order
		//So that we can know what variables were declared earlier
		@Override
		public Visitable visit(final BlockStmt n, final VisitInfo info) {
			//visitComment(n, info);

			//The environment of variables declared after each statement
			VariableEnv gammaCurrent = info.gamma;

			//Where we'll store the modified statements from our list after we process them
			NodeList<Statement> newStatementList = new NodeList<Statement>();

			for (Statement stmt : n.getStatements()){
				//Make the map where we store declarations from the statement, so we can use them later on
				LinkedList<Pair<String, String>> childDecls = new LinkedList<Pair<String, String>>();

				//Process the statement, receiving its declarations in a new list
				Statement newStmt = (Statement) stmt.accept(this, new VisitInfo(gammaCurrent, childDecls) );

				//Add all the declarations from inside that statement to our environemnt for future statements
				gammaCurrent = gammaCurrent.appendFront(childDecls);
				newStatementList.add(newStmt);
			}

			n.setStatements(newStatementList);
			return n;
		}

		//We need to add the variables from a method declaration into our scope
		@Override
		public Visitable visit(final MethodDeclaration n, final VisitInfo info) {
			//visitComment(n, info);
			n.setAnnotations((NodeList<AnnotationExpr>) n.getAnnotations().accept(this, info));
			n.setTypeParameters(modifyList(n.getTypeParameters(), info));
			n.setType((Type) n.getType().accept(this, info));


			//We process parameters manually so we can rename them
			//and store their new names, to use when processing the body
			NodeList<Parameter> newParams = new NodeList<Parameter>();
			LinkedList<Pair<String, String>> childDecls = new LinkedList<Pair<String, String>>();
			for (Parameter param : n.getParameters()){
				Parameter newParam = (Parameter)param.accept(this, new VisitInfo(info.gamma, childDecls));
				newParams.add(newParam);
			}
			n.setParameters(newParams);


			n.setThrownExceptions((NodeList<ReferenceType>) n.getThrownExceptions().accept(this, info));

			//Now, when processing the body, use an extended environment with the parameters' new names
			VariableEnv newGamma = info.gamma.appendFront(childDecls);
			if (n.getBody().isPresent()) {
				n.setBody((BlockStmt) n.getBody().get().accept(this, new VisitInfo(newGamma, info.declsForParent)));
			}
			return n;
		}
		
		public Visitable visit(final Node n, final VisitInfo info) {
			return null;
		}
		

		//Helpers copied from ModifyVisitor, they should really be protected
		private <N extends Node> NodeList<N> modifyList(NodeList<N> list, VisitInfo arg) {
			if (list == null) {
				return null;
			}
			return (NodeList<N>) list.accept(this, arg);
		}

		private void visitAnnotations(NodeWithAnnotations<?> n, VisitInfo arg) {
			n.setAnnotations((NodeList<AnnotationExpr>) n.getAnnotations().accept(this, arg));
		}



		//TODO: find all places where variables are defined
		//So that we can carry their decls forwards
		//i.e. for loops, functions, catch-blocks
	}

	@Override
	public Node result() {
		VariableNameVisitor v = new VariableNameVisitor();
		Visitable ret = this.startBlock.clone().accept(v, 
				new VisitInfo(VariableEnv.empty(), new LinkedList<Pair<String, String>>()));
		return (Node)ret;
	}

}
