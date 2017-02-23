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

	/**
	 * Initialises an empty control flow graph which will be filled by the
	 * {@link ControlFlowMethodParser#parseRec(DirectedAcyclicGraph<Node,
	 * DefaultEdge>, Node, List<Node>)}
	 *
	 * @param methBody
	 *            The AST of the method we want to infer a CFG from.
	 * 
	 * @return The newly created control flow graph.
	 */
	// TODO: Fix the CFG initialisation so that an empty statement is not
	// necessary as first node.
	public DirectedAcyclicGraph<Node, DefaultEdge> parse(BlockStmt methBody) {
		DirectedAcyclicGraph<Node, DefaultEdge> cfg = new DirectedAcyclicGraph<>(DefaultEdge.class);
		// Debug:
		System.out.println(methBody.toString());

		// Initialise the graph with an empty statement and list
		// TODO: Remove
		List<Node> previousNode = new ArrayList<Node>();
		EmptyStmt init = new EmptyStmt();
		cfg.addVertex(init);
		previousNode.add(init);
		parseRec(cfg, methBody, previousNode);
		return cfg;
	}

	/**
	 * Traverses the AST of the current node or body recursively and builds the
	 * control flow graph It is necessary to keep track of a list of previous
	 * nodes as we might have multiple entry edges for one node. So far the
	 * recursive functions handles conditional, try_catch, and return
	 * statements.
	 * 
	 * The returned node of parseRec may be added to a list which forms a
	 * collection of entry nodes for the subsequent element. *
	 * 
	 * @param cfg
	 *            The cfg we want to build over recursive iterations, it will be
	 *            passed along the individual functions.
	 * @param currentNode
	 *            The current Node we want to examine and handle. In general
	 *            this is a block statement which will be decomposed in a for
	 *            loop.
	 * @param previousNodes
	 *            A list of the predecessor nodes of the current iteration. A
	 *            list is necessary as we might have multiple entry edges for
	 *            one single node.
	 * @return The last node of the iteration. It represents a list of exit
	 *         statements of the current block.
	 */
	// TODO: Add capability to handle:
	// - while and for (each) loops
	// - Switch Case
	// - Do Statements
	// - maybe parallel programming primitives?
	private Node parseRec(DirectedAcyclicGraph<Node, DefaultEdge> cfg, Node currentNode, List<Node> previousNodes) {

		// Get a list of children contained in the block
		List<Node> children = currentNode.getChildNodes();
		for (int i = 0; i < children.size(); i++) {

			currentNode = children.get(i);

			// Handle if statement
			if (currentNode instanceof IfStmt) {
				previousNodes = parseConditional(cfg, previousNodes, (IfStmt) currentNode);
				currentNode = ((IfStmt) currentNode).getCondition();

			}
			// Handle try catch
			else if (currentNode instanceof TryStmt) {
				previousNodes = parseTryCatch(cfg, previousNodes, (TryStmt) currentNode);
			}
			// Handle default case
			else {
				//Add the node as vertex and add all vertices of List previousNodes as predecessor
				cfg.addVertex(currentNode);				
				addGraphEdges(cfg, currentNode, previousNodes);
				
				// Might be a return statement
				// Everything after is dead code so we do not need to proceed
				if (currentNode instanceof ReturnStmt || currentNode instanceof BreakStmt
						|| currentNode instanceof ContinueStmt) {
					return null;

				}
				//Clear the current list and add the latest node as next predecessor
				previousNodes.clear();
				previousNodes.add(currentNode);
			}
		}
		return currentNode;
	}

	/**
	 * This method is a specialised version of parseRec. It handles the control
	 * flow of a try catch statement and collects all the possible exit points
	 * in a tempNodes List. The returned node of parseTryCatch may be added to a
	 * list which forms a collection of entry nodes for the subsequent element.
	 * 
	 * @param cfg
	 *            The cfg we want to build over recursive iterations, it will be
	 *            passed along the individual functions.
	 * @param currentNode
	 *            The current Node we want to examine and handle. In general
	 *            this is a block statement which will be decomposed in a for
	 *            loop.
	 * @param previousNodes
	 *            A list of the predecessor nodes of the current iteration. A
	 *            list is necessary as we might have multiple entry edges for
	 *            one single node.
	 * @return The last node of the iteration. It represents a list of exit
	 *         statements of the current block.
	 */
	private List<Node> parseTryCatch(DirectedAcyclicGraph<Node, DefaultEdge> cfg, List<Node> previousNodes,
			TryStmt currentNode) {
		List<Node> tempNodes = new ArrayList<Node>();

		tempNodes.add(parseRec(cfg, currentNode.getTryBlock().get(), previousNodes));
		for (CatchClause clause : currentNode.getCatchClauses()) {
			tempNodes.add(parseRec(cfg, clause.getBody(), previousNodes));
		}
		if (currentNode.getFinallyBlock().isPresent()) {
			tempNodes.add(parseRec(cfg, currentNode.getTryBlock().get(), previousNodes));
		}
		return tempNodes;

	}

	/**
	 * This method is a specialised version of parseRec. It handles the control
	 * flow of an if statement and collects all the possible exit points in a
	 * tempNodes List. The returned node of parseConditional may be added to a
	 * list which forms a collection of entry nodes for the subsequent element.
	 * 
	 * @param cfg
	 *            The cfg we want to build over recursive iterations, it will be
	 *            passed along the individual functions.
	 * @param currentNode
	 *            The current Node we want to examine and handle. In general
	 *            this is a block statement which will be decomposed in a for
	 *            loop.
	 * @param previousNodes
	 *            A list of the predecessor nodes of the current iteration. A
	 *            list is necessary as we might have multiple entry edges for
	 *            one single node.
	 * @return The last node of the iteration. It represents a list of exit
	 *         statements of the current block.
	 */
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

	/**
	 * This is a simple auxiliary function which adds the graph edge between two
	 * vertices. It handles null values which may be added due to a return
	 * statement in the control flow. As this can generally be considered a dead
	 * end, a null element is ignored
	 * 
	 * @param cfg
	 *            The cfg we want to build over recursive iterations, it will be
	 *            passed along the individual functions.
	 * @param currentNode
	 *            The current Node we want to examine and handle. In general
	 *            this is a block statement which will be decomposed in a for
	 *            loop.
	 * @param previousNodes
	 *            A list of the predecessor nodes of the current iteration. A
	 *            list is necessary as we might have multiple entry edges for
	 *            one single node.
	 */
	private void addGraphEdges(DirectedAcyclicGraph<Node, DefaultEdge> cfg, Node currentNode,
			List<Node> previousNodes) {
		for (Node n : previousNodes) {
			if (n != null) {
				cfg.addEdge(n, currentNode);
			}
		}
	}
}