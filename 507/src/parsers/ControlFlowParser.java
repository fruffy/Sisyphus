package parsers;

import java.util.ArrayList;
import java.util.LinkedList;
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
	private DirectedAcyclicGraph<Node, DefaultEdge> cfg;
	private List<Node> previousNodes;

	/**
	 * Build a new control flow graph.
	 * 
	 * @param m
	 *            The method that will be parsed.
	 * 
	 */
	public ControlFlowParser(Method m) {
		this.cfg = new DirectedAcyclicGraph<>(DefaultEdge.class);
		this.previousNodes = new LinkedList<Node>();
		parse(m.getFilteredBody());

	}

	/**
	 * @return the control flow graph associated with this node.
	 */
	public DirectedAcyclicGraph<Node, DefaultEdge> getCFG() {
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
		EmptyStmt init = new EmptyStmt();
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
	private Node parseRec(Node currentNode) {

		// Get a list of children contained in the block
		List<Node> children = currentNode.getChildNodes();
		for (int i = 0; i < children.size(); i++) {

			currentNode = children.get(i);

			// Handle if statement
			if (currentNode instanceof IfStmt) {
				this.previousNodes = parseConditional((IfStmt) currentNode);
				currentNode = ((IfStmt) currentNode).getCondition();

			}
			// Handle try catch
			else if (currentNode instanceof TryStmt) {
				this.previousNodes = parseTryCatch((TryStmt) currentNode);
			}
			// Handle default case
			else {
				// Add the node as vertex and add all vertices of List
				// this.previousNodes as predecessor
				this.cfg.addVertex(currentNode);
				addGraphEdges(currentNode);

				// Might be a return statement
				// Everything after is dead code so we do not need to proceed
				if (currentNode instanceof ReturnStmt || currentNode instanceof BreakStmt
						|| currentNode instanceof ContinueStmt) {
					return null;

				}
				// Clear the current list and add the latest node as next
				// predecessor
				this.previousNodes.clear();
				this.previousNodes.add(currentNode);
			}
		}
		return currentNode;
	}

	/*
	 * This method is a specialised version of parseRec. It handles the control
	 * flow of a try catch statement and collects all the possible exit points
	 * in a tempNodes List. The returned node of parseTryCatch may be added to a
	 * list which forms a collection of entry nodes for the subsequent element.
	 */
	private List<Node> parseTryCatch(TryStmt currentNode) {
		// Initialise an empty arrayList which collects possible exit nodes
		List<Node> tempNodes = new ArrayList<Node>();

		// Parse and add the last node of the try, catch and finally blocks
		tempNodes.add(parseRec(currentNode.getTryBlock().get()));

		// We can have multiple catch blocks
		for (CatchClause clause : currentNode.getCatchClauses()) {
			tempNodes.add(parseRec(clause.getBody()));
		}
		if (currentNode.getFinallyBlock().isPresent()) {
			tempNodes.add(parseRec(currentNode.getTryBlock().get()));
		}
		return tempNodes;

	}

	/*
	 * This method is a specialised version of parseRec. It handles the control
	 * flow of an if statement and collects all the possible exit points in a
	 * tempNodes List. The returned node of parseConditional may be added to a
	 * list which forms a collection of entry nodes for the subsequent element.
	 */
	private List<Node> parseConditional(IfStmt ifNode) {
		// Initialise an empty arrayList which collects possible exit nodes
		List<Node> tempNodes = new ArrayList<Node>();

		// We do not want to add the if statement as vertex
		// Instead, use the condition expression
		Expression ifCondition = ifNode.getCondition();
		this.cfg.addVertex(ifCondition);
		addGraphEdges(ifCondition);

		this.previousNodes.clear();
		this.previousNodes.add(ifCondition);

		// Check if there is an else statement
		if (ifNode.getElseStmt().isPresent()) {
			// Parse and add the last node of the then block
			tempNodes.add(parseRec(ifNode.getThenStmt()));
			this.previousNodes.clear();
			this.previousNodes.add(ifCondition);

			// Parse and add the last node of the else block
			tempNodes.add(parseRec(ifNode.getElseStmt().get()));

		} else {
			// If there is no else statement, we still have two possible paths
			tempNodes.add(ifCondition);
			tempNodes.add(parseRec(ifNode.getThenStmt()));
		}
		return tempNodes;
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
	private void addGraphEdges(Node targetNode) {
		for (Node srcNode : this.previousNodes) {
			if (srcNode != null) {
				this.cfg.addEdge(srcNode, targetNode);
			}
		}
	}

}