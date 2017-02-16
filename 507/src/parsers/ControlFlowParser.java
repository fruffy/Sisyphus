package parsers;

import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.ReturnStmt;

import core.Method;

public class ControlFlowParser {
	private List<Method> methList;

	public ControlFlowParser(List<Method> methList) {
		this.methList = methList;
		buildCFG();
	}

	private void buildCFG() {
		for (Method m : methList) {
			parse(m.getFilteredBody());
			System.out.println("***************");
		}
	}

	private void parseRec(List<Node> children) {
		for (int i = children.size() - 1; i >= 0; i--) {
			Node ch = children.get(i);
			if (ch instanceof IfStmt) {
				IfStmt ifch = (IfStmt) ch;
				System.out.println("Else Statement: ");
				if (ifch.getElseStmt().isPresent()) {
					parseRec(ifch.getElseStmt().get().getChildNodes());
					System.out.println("------");
				}
				System.out.println("If Statement: " + ifch.getCondition());
				parseRec(ifch.getThenStmt().getChildNodes());
				System.out.println("------");

			} else if (ch instanceof ExpressionStmt) {
				System.out.println("Expression: " + ch.toString());

			} else if (ch instanceof ReturnStmt) {
				System.out.println("Return: " + ch.toString());

			} else {
				System.out.println("Node: " + ch.toString());
			}

		}
	}

	public void parse(BlockStmt methBody) {
		List<Node> children = methBody.getChildNodes();
		parseRec(children);
	}
}
