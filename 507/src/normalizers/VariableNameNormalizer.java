package normalizers;

import java.util.Collection;
import java.util.LinkedList;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.github.javaparser.ast.visitor.Visitable;
import com.github.javaparser.utils.Pair;

import datastructures.VariableEnv;

public class VariableNameNormalizer extends Normalizer {

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

		public VisitInfo(VariableEnv argGamma, LinkedList<Pair<String, String>> decls){
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
			String newName = info.gamma.freshKey("var" + type.toString());
			n.setName(newName);

			//Tell our parent that we declared a new name
			info.declsForParent.add(new Pair<String, String>(id.getIdentifier(), newName));

			if (n.getInitializer().isPresent()) {
				n.setInitializer((Expression) n.getInitializer().get().accept(this, info));
			}
			return n;
		}

		//When we see a variable, we rename it to whatever we've stored in our Gamma
		@Override
		public Visitable visit(SimpleName n, VisitInfo info) {
			n.setIdentifier(info.gamma.lookup(n.getIdentifier()));
			if (n.getIdentifier() != null){
				return n;
			}
			return null;
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
		
		//TODO: find all places where variables are defined
		//So that we can carry their decls forwards
		//i.e. for loops, functions, catch-blocks
	}

	@Override
	public BlockStmt result() {
		VariableNameVisitor v = new VariableNameVisitor();
		Visitable ret = this.startBlock.clone().accept(v, 
				new VisitInfo(VariableEnv.empty(), new LinkedList<Pair<String, String>>()));
		return (BlockStmt)ret;
	}

}
