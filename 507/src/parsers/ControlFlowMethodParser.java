package parsers;

import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.BreakStmt;
import com.github.javaparser.ast.stmt.CatchClause;
import com.github.javaparser.ast.stmt.ContinueStmt;
import com.github.javaparser.ast.stmt.EmptyStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.stmt.TryStmt;

import core.Method;
import jgrapht.experimental.dag.DirectedAcyclicGraph;
import jgrapht.graph.DefaultEdge;

public class ControlFlowMethodParser {
	public Method meth;

	public ControlFlowMethodParser() {
	}

	private Node parseRec(DirectedAcyclicGraph<Node, DefaultEdge> cfg, Node currentNode, List<Node> previousNodes) {

		List<Node> children = currentNode.getChildNodes();
		for (int i = 0; i < children.size(); i++) {

			currentNode = children.get(i);

			if (currentNode instanceof IfStmt) {
				previousNodes = parseConditional(cfg, previousNodes, (IfStmt) currentNode);
				currentNode = ((IfStmt) currentNode).getCondition();
				
			} else if (currentNode instanceof TryStmt) {
				previousNodes = parseTryCatch(cfg, previousNodes, currentNode);
			} else {
				
				cfg.addVertex(currentNode);
				addGraphEdges(cfg, currentNode, previousNodes);
				if (currentNode instanceof ReturnStmt || currentNode instanceof BreakStmt
						|| currentNode instanceof ContinueStmt) {
					return null;

				}
				previousNodes.clear();
				previousNodes.add(currentNode);
			}
		}
		return currentNode;
	}

	private void addGraphEdges(DirectedAcyclicGraph<Node, DefaultEdge> cfg, Node currentNode,
			List<Node> previousNodes) {
		for (Node n : previousNodes) {
			if (n != null) {
				cfg.addEdge(n, currentNode);
			}
		}
	}

	private List<Node> parseTryCatch(DirectedAcyclicGraph<Node, DefaultEdge> cfg, List<Node> previousNodes,
			Node currentNode) {
		TryStmt chTry = (TryStmt) currentNode;
		List<Node> tempNodes = new ArrayList<Node>();

		tempNodes.add(parseRec(cfg, chTry.getTryBlock().get(), previousNodes));
		for (CatchClause clause : chTry.getCatchClauses()) {
			tempNodes.add(parseRec(cfg, clause.getBody(), previousNodes));
		}
		if (chTry.getFinallyBlock().isPresent()) {
			tempNodes.add(parseRec(cfg, chTry.getTryBlock().get(), previousNodes));
		}
	return tempNodes;	
		
	}

	private List<Node> parseConditional(DirectedAcyclicGraph<Node, DefaultEdge> cfg, List<Node> previousNodes,
			IfStmt ifNode) {
		List<Node> tempNodes = new ArrayList<Node>();
		Expression ifCondition = ifNode.getCondition();
		cfg.addVertex(ifCondition);
		addGraphEdges(cfg, ifCondition, previousNodes);		
		previousNodes.clear();
		previousNodes.add(ifCondition);

		if (ifNode.getElseStmt().isPresent()) {

			tempNodes.add(parseRec(cfg, ifNode.getThenStmt(), previousNodes));
			previousNodes.clear();
			previousNodes.add(ifCondition);
			tempNodes.add(parseRec(cfg, ifNode.getElseStmt().get(), previousNodes));

		} else {
			tempNodes.add(ifCondition);
			tempNodes.add(parseRec(cfg, ifNode.getThenStmt(), previousNodes));
		}
		return tempNodes;
	}

	public DirectedAcyclicGraph<Node, DefaultEdge> parse(BlockStmt methBody) {
		DirectedAcyclicGraph<Node, DefaultEdge> cfg = new DirectedAcyclicGraph<>(DefaultEdge.class);
		System.out.println(methBody.toString());
		List<Node> previousNode = new ArrayList<Node>();
		EmptyStmt init = new EmptyStmt();
		cfg.addVertex(init);
		previousNode.add(init);
		parseRec(cfg, methBody, previousNode);
		return cfg;
	}
}