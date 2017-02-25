package parsers;

import java.util.ArrayList;
import java.util.LinkedList;
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
import datastructures.NodeWrapper;
import jgrapht.experimental.dag.DirectedAcyclicGraph;
import jgrapht.graph.DefaultEdge;

/**
 * Construct a new control flow graph.
 * 
 * @param cfg
 *            The cfg we want to build over recursive iterations, it will be
 *            passed along the individual functions.
 * @param previousNodes
 *            A list of the predecessor nodes of the current iteration. A list
 *            is necessary as we might have multiple entry edges for one single
 *            node.
 */
public class ControlFlowParser {
	private DirectedAcyclicGraph<NodeWrapper, DefaultEdge> cfg;
	private List<NodeWrapper> previousNodes;

	/**
	 * Build a new control flow graph.
	 * 
	 * @param m
	 *            The method that will be parsed.
	 * 
	 */
	public ControlFlowParser(Method m) {
		this.cfg = new DirectedAcyclicGraph<>(DefaultEdge.class);
		this.previousNodes = new LinkedList<NodeWrapper>();
		parse(m.getFilteredBody());

	}

	/**
	 * @return the control flow graph associated with this node.
	 */
	public DirectedAcyclicGraph<NodeWrapper, DefaultEdge> getCFG() {
		return cfg;
	}

	/**
	 * Initialises an empty control flow graph which will be filled by the
	 * {@link ControlFlowMethodParser#parseRec(DirectedAcyclicGraph<Node,
	 * DefaultEdge>, Node, List<Node>)}
	 *
	 * @param methBody
	 *            The AST of the method we want to infer a cfg from. *
	 * 
	 */
	// TODO: Fix the cfg initialisation so that an empty statement is not
	// necessary as first node.
	public void parse(BlockStmt methodBody) {

		// Initialise the graph with an empty statement and list
		// TODO: Remove
		NodeWrapper init = new NodeWrapper(new EmptyStmt());
		this.cfg.addVertex(init);
		this.previousNodes.add(init);
		parseRec(methodBody);
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
	 * @param currentNode
	 *            The current Node we want to examine and handle. In general
	 *            this is a block statement which will be decomposed in a for
	 *            loop.
	 * @return The last node of the iteration. It represents a list of exit
	 *         statements of the current block.
	 */
	// TODO: Add capability to handle:
	// - while and for (each) loops
	// - Switch Case
	// - Do Statements
	// - maybe parallel programming primitives?
	private NodeWrapper parseRec(Node currentStmt) {
		NodeWrapper currentNode = new NodeWrapper (currentStmt);
		// Get a list of children contained in the block
		List<Node> children = currentNode.NODE.getChildNodes();
		for (Node child : children) {
			currentNode = new NodeWrapper(child);
			// Handle if statement
			if (currentNode.NODE instanceof IfStmt) {
				currentNode = parseConditional((IfStmt) currentNode.NODE);
			}
			// Handle try catch
			else if (currentNode.NODE instanceof TryStmt) {
				parseTryCatch((TryStmt) currentNode.NODE);
			}
			// Handle default case
			else {
				// Add the node as vertex and add all vertices of List
				// this.previousNodes as predecessor
				addGraphElements(currentNode);

				// Might be a return statement
				// Everything after is dead code so we do not need to proceed
				if (currentNode.NODE instanceof ReturnStmt || currentNode.NODE instanceof BreakStmt
						|| currentNode.NODE instanceof ContinueStmt) {
					return null;

				}
				// Clear the current list and add the latest node as next
				// predecessor
				refreshPreviousNodes(currentNode);
			}
		}
		return currentNode;
	}

	private void refreshPreviousNodes(NodeWrapper currentNode) {
		this.previousNodes.clear();
		this.previousNodes.add(currentNode);
	}

	/*
	 * This method is a specialised version of parseRec. It handles the control
	 * flow of a try catch statement and collects all the possible exit points
	 * in a tempNodes List. The returned node of parseTryCatch may be added to a
	 * list which forms a collection of entry nodes for the subsequent element.
	 */
	private void parseTryCatch(TryStmt currentNode) {
		// Initialise an empty arrayList which collects possible exit nodes
		List<NodeWrapper> tempNodes = new ArrayList<NodeWrapper>();

		// Parse and add the last node of the try, catch and finally blocks
		tempNodes.add(parseRec(currentNode.getTryBlock().get()));

		// We can have multiple catch blocks
		for (CatchClause clause : currentNode.getCatchClauses()) {
			tempNodes.add(parseRec(clause.getBody()));
		}
		if (currentNode.getFinallyBlock().isPresent()) {
			tempNodes.add(parseRec(currentNode.getTryBlock().get()));
		}
		this.previousNodes = tempNodes;

	}

	/*
	 * This method is a specialised version of parseRec. It handles the control
	 * flow of an if statement and collects all the possible exit points in a
	 * tempNodes List. The returned node of parseConditional may be added to a
	 * list which forms a collection of entry nodes for the subsequent element.
	 */
	private NodeWrapper parseConditional(IfStmt ifNode) {
		// Initialise an empty arrayList which collects possible exit nodes
		List<NodeWrapper> tempNodes = new ArrayList<NodeWrapper>();

		// We do not want to add the if statement as vertex
		// Instead, use the condition expression
		NodeWrapper ifCondition = new NodeWrapper(ifNode.getCondition());
		addGraphElements(ifCondition);

		refreshPreviousNodes(ifCondition);

		// Check if there is an else statement
		if (ifNode.getElseStmt().isPresent()) {
			// Parse and add the last node of the then block
			tempNodes.add(parseRec(ifNode.getThenStmt()));
			refreshPreviousNodes(ifCondition);

			// Parse and add the last node of the else block
			tempNodes.add(parseRec(ifNode.getElseStmt().get()));

		} else {
			// If there is no else statement, we still have two possible paths
			tempNodes.add(ifCondition);
			tempNodes.add(parseRec(ifNode.getThenStmt()));
		}
		this.previousNodes = tempNodes;
		return ifCondition;
	}

	/**
	 * This is a simple auxiliary function which adds the graph edge between two
	 * vertices. It handles null values which may be added due to a return
	 * statement in the control flow. As this can generally be considered a dead
	 * end, a null element is ignored.
	 * 
	 * @param targetNode
	 *            The node the newly created edge will point to.
	 */
	private void addGraphElements(NodeWrapper targetNode) {
		this.cfg.addVertex(targetNode);
		for (NodeWrapper srcNode : this.previousNodes) {
			if (srcNode != null) {
				this.cfg.addEdge(srcNode, targetNode);
			}
		}
	}

}