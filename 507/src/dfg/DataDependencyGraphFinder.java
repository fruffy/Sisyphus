package dfg;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.utils.Pair;

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

	private class Worklist{

		private LinkedHashSet<Node> innerSet;

		public boolean isEmpty(){
			return innerSet.isEmpty();
		}

		public void push(Node elem){
			innerSet.add(elem);
		}

		public void push(Collection<Node> coll){
			innerSet.addAll(coll);
		}

		public Node pop(){
			Iterator<Node> iterator = innerSet.iterator();
			Node result = iterator.next();
			iterator.remove();
			return result;
		}

		public Worklist(){
			this.innerSet = new LinkedHashSet<Node>();
		}

	}

	private final DirectedPseudograph<NodeWrapper, DefaultEdge> cfg;
	private final Set<Node> allNodes;
	private Worklist worklist;

	private HashMap<Node, Set<Pair<String, AssignExpr>>> exitSet = new HashMap<Node, Set<Pair<String, AssignExpr>>>();
	private HashMap<Node, Set<Pair<String, AssignExpr>>> entrySet = new HashMap<Node, Set<Pair<String, AssignExpr>>>();

	static Set<Pair<String, AssignExpr>> bottom(){
		return new HashSet<Pair<String, AssignExpr>>();
	}

	private boolean inKillSet(Pair<String, AssignExpr> defPair, Node node){
		//Assignments kill definitions of the variables they assign to
		if (node instanceof AssignExpr){
			AssignExpr aexpr = (AssignExpr)node;
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

	static Set<Pair<String, AssignExpr>> genSet(Node node){
		HashSet<Pair<String, AssignExpr>> ret = new HashSet<Pair<String, AssignExpr>>();

		//Assignments generate definitions of the variables they assign to
		if (node instanceof AssignExpr){
			AssignExpr aexpr = (AssignExpr)node;
			//Check if the think we possibly kill is 
			//an assignment to the variable we are assigning
			new Pair<String, AssignExpr>(aexpr.getTarget().toString(), aexpr);
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
		for (Node node : allNodes){
			//System.out.println(node);
			entrySet.put(node, bottom());
			exitSet.put(node, bottom());
		}


		//Add all nodes to our worklist initially, since we need to update values for each node
		worklist.push(allNodes);

		//Keep updating by the dataflow equations until nothing is on our worklist
		while (!worklist.isEmpty()){
			Node currentNode = worklist.pop();
			Set<Pair<String, AssignExpr>> currentEntry = entrySet.get(currentNode);
			Set<Pair<String, AssignExpr>> currentExit = exitSet.get(currentNode);

			Set<Pair<String, AssignExpr>> newEntry = entrySet.get(currentNode);
			Set<Pair<String, AssignExpr>> newExit = exitSet.get(currentNode);

			if (false){ //TODO check if is initial label
				//TODO set to free vars of graph
			}
			else {
				//Our entry contains all of our predecessors' exits
				for (DefaultEdge incomingEdge : cfg.incomingEdgesOf(new NodeWrapper(currentNode)) ){
					NodeWrapper previousNode = cfg.getEdgeSource(incomingEdge);
					newEntry.addAll(exitSet.get(previousNode));
				}

				//Our exit set: add everything from entry that wasn't killed
				//Plus anything new we defined in this statement
				for (Pair<String, AssignExpr> pair : newEntry){
					if (!inKillSet(pair, currentNode)){
						newExit.add(pair);
					}
				}
				newExit.addAll(genSet(currentNode));
			}

			//Update our new entry and exit values
			entrySet.put(currentNode, newEntry);
			exitSet.put(currentNode, newExit);

			//Add all our successors to the worklist
			Set<DefaultEdge> outgoingEdges = cfg.outgoingEdgesOf(new NodeWrapper(currentNode));
			for (DefaultEdge edge : outgoingEdges){
				worklist.push(cfg.getEdgeTarget(edge).NODE);
			}
		}

		DirectedPseudograph<Node, DefaultEdge> ret =
				new DirectedPseudograph<Node, DefaultEdge>(DefaultEdge.class);
		DirectedPseudograph<NodeWrapper, DefaultEdge> ret2 =
				new DirectedPseudograph<NodeWrapper, DefaultEdge>(DefaultEdge.class);

		for (Node n : allNodes){
			for (Pair<String, AssignExpr> defPair : exitSet.get(n)){
				//If a def reaches a node, and that node references that variable
				//then we have an edge
				//TODO check use, not occurrence
				if (ASTUtil.occursFree(n, defPair.a)){
					ret.addEdge(defPair.b, n);
					ret2.addEdge(new NodeWrapper(defPair.b), new NodeWrapper(n));
				}
			}
		}
		
		return ret2;
	}

}
