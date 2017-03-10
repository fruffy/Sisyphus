package visitors;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.expr.NameExpr;

public class ASTUtil {
	public static boolean occursFree(Node node, String ident){
		//If this expr is a variable, see if it's what we're looking for
		//TODO check if actually free, right now just checking if occurs at all
		if (node.toString().equals(ident)){
			return true;
		}
		else {
			System.out.println("Node " + node + " is not string " + ident);
		}
		//Check if any children contain the identifier
		for (Node child : node.getChildNodes()){
			if (occursFree(child, ident)){
				return true;
			}
		}
		return false;
		
		
	}
}
