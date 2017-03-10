package dfg;

import java.awt.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.utils.Pair;

import datastructures.EntryStmt;
import datastructures.NodeWrapper;
import jgrapht.graph.DirectedPseudograph;
import jgrapht.graph.DefaultEdge;
import jgrapht.graph.DirectedPseudograph;
import visitors.ASTUtil;

/**
 * Based on "Reaching Definitions" from Principles of Program Analysis
 * by Nielson, Nielson and Hankin
 * pp 43-44
 *
 */
public class DataDependencyGraphFinder {
	
	/**
	 * Returns null if the node isn't an assignment
	 * Otherwise, it checks if it's an assignment (or wrapper around it)
	 * and returns it
	 */
	private static void recursivelyGetAssignments(Node node, LinkedList<AssignExpr> foundAssigns){
		if (node instanceof AssignExpr){
			foundAssigns.add((AssignExpr)node);
		}
		for (Node child : node.getChildNodes()){
			recursivelyGetAssignments(child, foundAssigns);
		}
	}
	
	private static LinkedList<AssignExpr> assignmentsWithin(Node n){
		LinkedList<AssignExpr> ret = new LinkedList<AssignExpr>();
		recursivelyGetAssignments(n, ret);
		return ret;
	}

	private class Worklist{

		private LinkedHashSet<NodeWrapper> innerSet;

		public boolean isEmpty(){
			return innerSet.isEmpty();
		}

		public void push(NodeWrapper elem){
			innerSet.add(elem);
		}

		public void push(Collection<NodeWrapper> coll){
			innerSet.addAll(coll);
		}

		public NodeWrapper pop(){
			Iterator<NodeWrapper> iterator = innerSet.iterator();
			NodeWrapper result = iterator.next();
			iterator.remove();
			return result;
		}

		public Worklist(){
			this.innerSet = new LinkedHashSet<NodeWrapper>();
		}

	}

	private final DirectedPseudograph<NodeWrapper, DefaultEdge> cfg;
	private final Set<Node> allNodes;
	private Worklist worklist;

	private HashMap<NodeWrapper, Set<Pair<String, NodeWrapper>>> exitSet = new HashMap<NodeWrapper, Set<Pair<String, NodeWrapper>>>();
	private HashMap<NodeWrapper, Set<Pair<String, NodeWrapper>>> entrySet = new HashMap<NodeWrapper, Set<Pair<String, NodeWrapper>>>();


	static Set<Pair<String, NodeWrapper>> bottom(){
		return new HashSet<Pair<String, NodeWrapper>>();
	}

	private boolean inKillSet(Pair<String, NodeWrapper> defPair, NodeWrapper node){
		
		//Assignments kill definitions of the variables they assign to
		for (AssignExpr aexpr : assignmentsWithin(node.NODE)){
			//Check if the think we possibly kill is 
			//an assignment to the variable we are assigning
			if (aexpr.getTarget().toString() == defPair.a){
				return true;
			}
		}

		//Only assignments kill
		//TODO other things that kill?
		return false;
	}

	static Set<Pair<String, NodeWrapper>> genSet(NodeWrapper node){
		HashSet<Pair<String, NodeWrapper>> ret = new HashSet<Pair<String, NodeWrapper>>();

		//Assignments generate definitions of the variables they assign to
		for (AssignExpr aexpr : assignmentsWithin(node.NODE)){
			//Check if the thing we possibly kill is 
			//an assignment to the variable we are assigning
			ret.add(new Pair<String, NodeWrapper>(aexpr.getTarget().toString(), node));
			return ret;
		}
		
		//TODO other things that gen? Method calls?
		
		return ret;
	}


	public DataDependencyGraphFinder(DirectedPseudograph<NodeWrapper, DefaultEdge> cfg){
		this.cfg = cfg;
		Set<NodeWrapper> cfgNodeWrappers = cfg.vertexSet();
		ArrayList<Node> cfgNodeList = new ArrayList<Node>();
		for(NodeWrapper n: cfgNodeWrappers){
			cfgNodeList.add(n.NODE);
		}
		this.allNodes = new HashSet<Node>(cfgNodeList);
		this.worklist = new Worklist();
		
	}

	public DirectedPseudograph<NodeWrapper, DefaultEdge> findReachingDefs(){
		//Set the reaching defs to bottom (empty set) for each node
		//Add all nodes to our worklist initially, since we need to update values for each node
		for (Node n : allNodes){
			NodeWrapper nw = new NodeWrapper(n);
			entrySet.put(nw, bottom());
			exitSet.put(nw, bottom());
			worklist.push(nw);
		}

		//Keep updating by the dataflow equations until nothing is on our worklist
		while (!worklist.isEmpty()){
			NodeWrapper currentNode = worklist.pop();
			Set<Pair<String, NodeWrapper>> currentEntry = entrySet.get(currentNode);
			Set<Pair<String, NodeWrapper>> currentExit = exitSet.get(currentNode);

			Set<Pair<String, NodeWrapper>> newEntry = entrySet.get(currentNode);
			Set<Pair<String, NodeWrapper>> newExit = exitSet.get(currentNode);

			if (currentNode.NODE instanceof EntryStmt){ 
				//TODO set to free vars of graph
				
			}
			else {
				//Our entry contains all of our predecessors' exits
				for (DefaultEdge incomingEdge : cfg.incomingEdgesOf(currentNode) ){
					NodeWrapper previousNode = cfg.getEdgeSource(incomingEdge);
					newEntry.addAll(exitSet.get(previousNode));
				}

				//Our exit set: add everything from entry that wasn't killed
				//Plus anything new we defined in this statement
				for (Pair<String, NodeWrapper> pair : newEntry){
					if (!inKillSet(pair, currentNode)){
						newExit.add(pair);
					}
				}
				newExit.addAll(genSet(currentNode));
			}

			//Update our new entry and exit values
			//System.out.println("ddg exitset "+exitSet);
			entrySet.put(currentNode, newEntry);
			exitSet.put(currentNode, newExit);

			//If we changed, add all our successors to the worklist
			if (!newExit.equals(currentExit)){
				Set<DefaultEdge> outgoingEdges = cfg.outgoingEdgesOf(currentNode);
				for (DefaultEdge edge : outgoingEdges){
					worklist.push(cfg.getEdgeTarget(edge));
				}
				
			}
			
		}

		//DirectedPseudograph<Node, DefaultEdge> ret =
		//		new DirectedPseudograph<Node, DefaultEdge>(DefaultEdge.class);
		DirectedPseudograph<NodeWrapper, DefaultEdge> ret2 =
				new DirectedPseudograph<NodeWrapper, DefaultEdge>(DefaultEdge.class);

		for (NodeWrapper nw : exitSet.keySet()){
			ret2.addVertex(nw);
		}
		
		for (NodeWrapper nw : exitSet.keySet()){
			for (Pair<String, NodeWrapper> defPair : exitSet.get(nw)){
				//If a def reaches a node, and that node references that variable
				//then we have an edge
				//TODO check use, not occurrence
				if (ASTUtil.occursFree(nw.NODE, defPair.a)){
					//ret.addEdge(defPair.b, n);
					if (!ret2.containsVertex(defPair.b)){
						ret2.addVertex(defPair.b);
					}
					ret2.addEdge(defPair.b, nw);
				} else{
					//System.out.println("Node " + nw.NODE + ", " + " doesn't contain " + defPair.a);
				}
			}
		}
		
		return ret2;
	}

}
