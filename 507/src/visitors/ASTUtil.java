package visitors;

import java.util.HashSet;
import java.util.List;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.DoStmt;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.ForeachStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.SwitchStmt;
import com.github.javaparser.ast.stmt.WhileStmt;

public class ASTUtil {
	public static boolean occursFree(Node node, String ident){

		return freesWithEnv(node, new HashSet(), new HashSet()).contains(ident);
	}

	private static HashSet<String> freesWithEnv(Node node){
		return freesWithEnv(node, new HashSet(), new HashSet());
	}

	private static HashSet<String> freesWithEnv(
			Node node, 
			HashSet<String> gamma,
			HashSet<String> declsForParent) {
		//If this expr is a variable, see if it's what we're looking for
		//TODO check if actually free, right now just checking if occurs at all
		HashSet<String> ret = new HashSet<String>();
		String nodeString = node.toString();
		if ((node instanceof SimpleName) && !gamma.contains(nodeString)){
			ret.add(nodeString);
		}
		
		//Otherwise, check if we're a declaration, and if so, add it to our environment
		if (node instanceof VariableDeclarationExpr){
			VariableDeclarationExpr decl = ((VariableDeclarationExpr)node);
			for (VariableDeclarator var : decl.getVariables()){
				declsForParent.add(var.toString());
			}
		}
		
		//Check if any children contain the identifier
		HashSet<String> ourGamma = new HashSet<String>();
		HashSet<String> childDecls = new HashSet<String>();
		for (Node child : orderedChildNodes(node)){
			ret.addAll(freesWithEnv(child, gamma, childDecls));
			//Add everything new from the last statement to our env
			ourGamma.addAll(childDecls);
		}
		
		if (! (node instanceof BlockStmt)){
			//Make sure our parent knows about what our children declared
			//unless we're a block statement, in which case we define a new scope
			declsForParent.addAll(childDecls);
		}
		
		return ret;
	}


	//Make sure the child nodes list is in the correct order
	// i.e. the order matching variable scoping
	//May not cover all cases, but we cover the common ones
	public static List<Node> orderedChildNodes(Node n){
		NodeList<Node> ret = new NodeList<Node>();

		if (n instanceof ForStmt){
			ForStmt fs = (ForStmt)n;
			ret.addAll(fs.getInitialization());
			if (fs.getCompare().isPresent()){
				ret.add(fs.getCompare().get());
			}
			ret.addAll(fs.getUpdate());
			ret.add(fs.getBody());
			return ret;
		}
		if (n instanceof ForeachStmt){
			ForeachStmt fs = (ForeachStmt)n;
			ret.add(fs.getIterable());
			ret.add(fs.getBody());
			return ret;
		}
		else if (n instanceof IfStmt){
			IfStmt is = (IfStmt)n;
			ret.add(is.getCondition());
			ret.add(is.getThenStmt());
			if (is.getElseStmt().isPresent()){
				ret.add(is.getElseStmt().get());
			}
			return ret;
		}
		else if (n instanceof WhileStmt){
			WhileStmt ws = (WhileStmt)n;
			ret.add(ws.getCondition());
			ret.add(ws.getBody());
			return ret;
		}
		else if (n instanceof DoStmt){
			DoStmt ws = (DoStmt)n;
			ret.add(ws.getBody());
			ret.add(ws.getCondition());
			return ret;
		}
		else if (n instanceof SwitchStmt){
			SwitchStmt ss = (SwitchStmt)n;
			ret.add(ss.getSelector());
			ret.addAll(ss.getEntries());
			return ret;
		}
		else if (n instanceof BlockStmt){
			BlockStmt bs = (BlockStmt)n;
			ret.addAll(bs.getStatements());
			return ret;
		}
		else {
			return n.getChildNodes();
		}
	}
}
