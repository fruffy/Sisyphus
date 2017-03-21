package parsers;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedPseudograph;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.BreakStmt;
import com.github.javaparser.ast.stmt.CatchClause;
import com.github.javaparser.ast.stmt.ContinueStmt;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.stmt.TryStmt;
import com.github.javaparser.ast.stmt.WhileStmt;

import core.Method;
import datastructures.BackEdge;
import datastructures.EntryStmt;
import datastructures.NodeWrapper;

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
	private DirectedPseudograph<NodeWrapper, DefaultEdge> cfg;
	private List<NodeWrapper> previousNodes;

	/**
	 * Build a new control flow graph.
	 * 
	 * @param m
	 *            The method that will be parsed.
	 * 
	 */
	public ControlFlowParser(Method m) {
		this.cfg = new DirectedPseudograph<>(DefaultEdge.class);
		this.previousNodes = new LinkedList<NodeWrapper>();
		parse(m.getBody());

	}

	/**
	 * @return the control flow graph associated with this node.
	 */
	public DirectedPseudograph<NodeWrapper, DefaultEdge> getCFG() {
		return cfg;
	}

	/**
	 * Initialises an empty control flow graph which will be filled by the
	 * {@link ControlFlowMethodParser#parseRec(DirectedPseudograph<Node,
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
		// Remove them after initialising the graph
		// TODO: Figure out a better way...

		NodeWrapper initNode = new NodeWrapper(new EntryStmt());
		this.cfg.addVertex(initNode);
		this.previousNodes.add(initNode);
		parseRec(methodBody);
		this.cfg.removeVertex(initNode);
	}

	/**
	 * An initialiser for the control flow graph which adds the first atomic
	 * node type. Intended to remove the node once it is attached, but
	 * unfortunately this does not work...
	 * 
	 * @param entryNode
	 *            The first node in the AST of the body.
	 */
	@SuppressWarnings("unused")
	private void init(Node entryNode) {
		if (entryNode instanceof Statement) {
			init(entryNode.getChildNodes().get(0));
		} else {
			NodeWrapper initNode = new NodeWrapper(entryNode);
			this.cfg.addVertex(initNode);
			this.previousNodes.add(initNode);
		}
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
	// - Switch Case
	// - Do Statements
	// - maybe parallel programming primitives?
	private NodeWrapper parseRec(Node statement) {
		if (statement.getChildNodes().size() == 0) {
			return null;
		}
		NodeWrapper currentNode = new NodeWrapper(statement);
		List<Node> children = currentNode.NODE.getChildNodes();
		for (Node child : children) {		
			currentNode = new NodeWrapper(child);
			// Handle conditionals
			if (currentNode.NODE instanceof IfStmt) {
				currentNode = parseIfStmt((IfStmt) currentNode.NODE);
			} else if (currentNode.NODE instanceof ForStmt) {
				currentNode = parseForLoop((ForStmt) currentNode.NODE);
			} else if (currentNode.NODE instanceof WhileStmt) {
				currentNode = parseWhileLoop((WhileStmt) currentNode.NODE);
			}
			else if (currentNode.NODE instanceof TryStmt) {
				parseTryCatch((TryStmt) currentNode.NODE);
			}
			// Handle default case
			else {
				addGraphElements(currentNode);
				if (currentNode.NODE instanceof ReturnStmt) {
					if(((ReturnStmt)currentNode.NODE).getExpression().isPresent()) {
						parseRec(((ReturnStmt)currentNode.NODE).getExpression().get());
					}
					// Everything after is dead code, no need to proceed
					return null;
				} else  if ( currentNode.NODE instanceof BreakStmt || currentNode.NODE instanceof ContinueStmt) {
					return null;
				}
				// Clear the current list and add next predecessor
				refreshPreviousNodes(currentNode);
			}
		}

		return currentNode;
	}

	private NodeWrapper parseWhileLoop(WhileStmt currentNode) {
		List<NodeWrapper> tempNodes = new ArrayList<NodeWrapper>();
		NodeWrapper exitNode = null;
		// We do not want to add the while statement as vertex
		// Instead, use the condition expression
		NodeWrapper entryNode = new NodeWrapper(currentNode.getCondition());
		addGraphElements(entryNode);
		refreshPreviousNodes(entryNode);
		tempNodes.add(entryNode);
		
		// Parse the body of the for statement
		exitNode = parseRec(currentNode.getBody());
		if (exitNode != null) {
			//It can happen that a while statement is empty and the control flow is in the condition clause
			//TODO: Figure out a way to handle this 
			refreshPreviousNodes(entryNode);
			addGraphElements(exitNode, new BackEdge());
		}
		this.previousNodes = tempNodes;
		return entryNode;
	}

	private NodeWrapper parseForLoop(ForStmt currentNode) {
		List<NodeWrapper> tempNodes = new ArrayList<NodeWrapper>();
		NodeWrapper entryNode = null;
		NodeWrapper exitNode = null;
		// These operations will be executed regardless
		// Add them to the control flow
		for (Node n : currentNode.getInitialization()) {
			entryNode = new NodeWrapper(n);
			addGraphElements(entryNode);
			refreshPreviousNodes(entryNode);
		}
		// We do not want to add the for statement as vertex
		// Instead, use the condition expression
		// This is the only way to exit the loop
		if (currentNode.getCompare().isPresent()) {
			entryNode = new NodeWrapper(currentNode.getCompare().get());
			addGraphElements(entryNode);
			refreshPreviousNodes(entryNode);
			tempNodes.add(entryNode);
		}
		// Parse the body of the for statement
		exitNode = parseRec(currentNode.getBody());
		refreshPreviousNodes(exitNode);
		// These operations will run after the loop
		// Add them to the control flow and save the last node
		for (Node n : currentNode.getUpdate()) {
			exitNode = new NodeWrapper(n);
			addGraphElements(exitNode);
			refreshPreviousNodes(exitNode);
		}
		// Connect the entry of the for loop (condition) with the exit operation
		refreshPreviousNodes(entryNode);
		addGraphElements(exitNode, new BackEdge());
		this.previousNodes = tempNodes;
		return entryNode;
	}

	/**
	 * This method is a specialised version of parseRec. It handles the control
	 * flow of a try catch statement and collects all the possible exit points
	 * in a tempNodes List. The returned node of parseTryCatch may be added to a
	 * list which forms a collection of entry nodes for the subsequent element.
	 */
	private void parseTryCatch(TryStmt currentNode) {
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

	/**
	 * This method is a specialised version of parseRec. It handles the control
	 * flow of an if statement and collects all the possible exit points in a
	 * tempNodes List. The returned node of parseConditional may be added to a
	 * list which forms a collection of entry nodes for the subsequent element.
	 */
	private NodeWrapper parseIfStmt(IfStmt currentNode) {
		// Initialise an empty arrayList which collects possible exit nodes
		List<NodeWrapper> tempNodes = new ArrayList<NodeWrapper>();
		// We do not want to add the if statement as vertex
		// Instead, use the condition expression
		NodeWrapper ifCondition = new NodeWrapper(currentNode.getCondition());
		addGraphElements(ifCondition);
		refreshPreviousNodes(ifCondition);
		// Check if there is an else statement
		if (currentNode.getElseStmt().isPresent()) {
			// Parse and add the last node of the then and else blocks
			tempNodes.add(parseRec(currentNode.getThenStmt()));
			refreshPreviousNodes(ifCondition);
			tempNodes.add(parseRec(currentNode.getElseStmt().get()));
		} else {
			// If there is no else statement, we still have two possible paths
			tempNodes.add(ifCondition);
			tempNodes.add(parseRec(currentNode.getThenStmt()));
		}
		this.previousNodes = tempNodes;
		return ifCondition;
	}

	/**
	 * Clear the old list of preceding nodes and add the current node to it.
	 * 
	 * @param targetNode
	 *            The node the newly created edge will point to.
	 */
	private void refreshPreviousNodes(NodeWrapper currentNode) {
		this.previousNodes.clear();
		this.previousNodes.add(currentNode);
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
		if(targetNode == null){
			return;
		}
		this.cfg.addVertex(targetNode);
		for (NodeWrapper srcNode : this.previousNodes) {
			if (srcNode != null) {
				this.cfg.addEdge(srcNode, targetNode);
			}
		}
	}

	private void addGraphElements(NodeWrapper targetNode, BackEdge back) {
		this.cfg.addVertex(targetNode);
		for (NodeWrapper srcNode : this.previousNodes) {
			if (srcNode != null) {
				this.cfg.addEdge(targetNode, srcNode, back);
			}
		}
	}
}