package parsers;

import java.util.List;

import com.github.javaparser.ast.Node;
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
	private Node previousNode;

	public ControlFlowMethodParser() {
	}

	private void parseRec(DirectedAcyclicGraph<Node, DefaultEdge> cfg, Node srcNode) {

		List<Node> children = srcNode.getChildNodes();

		for (int i = 0; i < children.size(); i++) {

			Node currentNode = children.get(i);

			if (currentNode instanceof IfStmt) {
				currentNode = ((IfStmt) currentNode).getCondition();
				cfg.addVertex(currentNode);
				cfg.addEdge(this.previousNode, currentNode);
				this.previousNode = currentNode;
				parseConditional(cfg, (IfStmt) currentNode.getParentNode().get());
			} else if (currentNode instanceof TryStmt) {
				TryStmt chTry = (TryStmt) currentNode;
				parseTryCatch(cfg, chTry);
			} else if (currentNode instanceof ReturnStmt || currentNode instanceof BreakStmt || currentNode instanceof ContinueStmt) {
				cfg.addVertex(currentNode);
				cfg.addEdge(this.previousNode, currentNode);
				break;
			} else {
				cfg.addVertex(currentNode);
				cfg.addEdge(this.previousNode, currentNode);
				this.previousNode = currentNode;
			}
		}
	}

	private void parseTryCatch(DirectedAcyclicGraph<Node, DefaultEdge> cfg, TryStmt currentNode) {

		parseRec(cfg, currentNode.getTryBlock().get());
		for (CatchClause clause : currentNode.getCatchClauses()) {
			parseRec(cfg, clause.getBody());
		}
		if (currentNode.getFinallyBlock().isPresent()) {
			parseRec(cfg, currentNode.getTryBlock().get());
		}
	}

	private void parseConditional(DirectedAcyclicGraph<Node, DefaultEdge> cfg, IfStmt currentNode) {
		parseRec(cfg, currentNode.getThenStmt());
		if (currentNode.getElseStmt().isPresent()) {
			this.previousNode = currentNode.getCondition();
			parseRec(cfg, currentNode.getElseStmt().get());
		}

	}

	public DirectedAcyclicGraph<Node, DefaultEdge> parse(BlockStmt methBody) {
		DirectedAcyclicGraph<Node, DefaultEdge> cfg = new DirectedAcyclicGraph<>(DefaultEdge.class);
		System.out.println(methBody.toString());

		EmptyStmt init = new EmptyStmt();
		cfg.addVertex(init);
		this.previousNode = init;
		parseRec(cfg, methBody);
		return cfg;
	}

	public class FalseEdge extends DefaultEdge {
		private static final long serialVersionUID = 1L;

		public FalseEdge() {
			super();
		}

	}

	public class TrueEdge extends DefaultEdge {
		private static final long serialVersionUID = 1L;

		public TrueEdge() {
			super();
		}

	}
}