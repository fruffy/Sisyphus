package visitors;

import java.util.List;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.stmt.ReturnStmt;

/**
 * Check if a node is a return statement that returns
 * something other than a variable
 *
 */
public class ReturnNonVarCheck extends DefaultValueVisitor<Boolean> {

	private ReturnNonVarCheck() {
		//Nodes return false by default
		super(false);
	}
	
	//We override the visit method to check if
	@Override
	public Boolean visit(ReturnStmt n, Object arg) {
		List<Node> children = n.getChildNodes();
		if (children.size() == 1){
			Node returnedVal = children.get(0);
			if (returnedVal instanceof NameExpr){
				return false;
			}
			return true;
		}
		throw new RuntimeException("Return node shouldn't have more than one child");
	}
	
	public static boolean isNonVarReturn(Node n){
		ReturnNonVarCheck visitor = new ReturnNonVarCheck();
		return n.accept(visitor, null);
	}

}
