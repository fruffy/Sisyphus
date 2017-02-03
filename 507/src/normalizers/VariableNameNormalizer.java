package normalizers;

import java.util.Collection;
import java.util.LinkedList;

import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.stmt.BlockStmt;
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

		public VisitInfo(){
			gamma = VariableEnv.empty();
			declsForParent = new LinkedList<Pair<String, String>>();
		}
	}

	private class VariableNameVisitor extends ModifierVisitor<VisitInfo>{
		public VariableNameVisitor(){}

		@Override
		public Visitable visit(final VariableDeclarator n, final VisitInfo info) {

			Type type = (Type) n.getType().accept(this, info);
			if (type == null) {
				return null;
			}
			n.setType(type);

			//visitComment(n, info); //TODO need this?
			final SimpleName id = (SimpleName) n.getName().accept(this, info);
			if (id == null) {
				return null;
			}

			//We generate a new, standardized name for this declaration, based on its type
			String newName = info.gamma.freshKey("var" + type.toString());
			n.setName(newName);

			//Tell our parent that we declared a new name
			info.declsForParent.add(new Pair<String, String>(id.toString(), newName));

			if (n.getInitializer().isPresent()) {
				n.setInitializer((Expression) n.getInitializer().get().accept(this, info));
			}
			return n;
		}
	}

	@Override
	public BlockStmt result() {
		VariableNameVisitor v = new VariableNameVisitor();
		return (BlockStmt)this.startBlock.clone().accept(v, new VisitInfo());		
	}

}
