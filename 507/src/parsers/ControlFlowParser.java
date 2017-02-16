package parsers;

import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.stmt.IfStmt;

import core.Method;

public class ControlFlowParser {
	private BlockStmt methBody;

	public ControlFlowParser(Method meth) {

		this.methBody = meth.getFilteredBody();
	}

	public void parse() {
		List<Node> children = methBody.getChildNodes();
		
		for (int i = children.size() - 1; i >= 0; i--) {
			Node ch = children.get(i);
			if (ch instanceof IfStmt) {

				System.out.println("If Statement: \n" + ch.toString());

			} else if (ch instanceof ExpressionStmt) {
				System.out.println("Expression: \n" + ch.toString());

			} else {
				System.out.println("Node: \n" + ch.toString());
			}
			System.out.println();

		}
	}

}
