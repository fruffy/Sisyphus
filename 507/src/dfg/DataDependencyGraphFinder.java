package dfg;

import java.awt.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.TreeMap;
// java.util.HashSet;
import java.util.TreeSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Set;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.stmt.ReturnStmt;
//import datastructures.NicePair;

import core.Method;
import datastructures.EntryStmt;
import datastructures.NodeWrapper;
import datastructures.PDGGraphViz;
import datastructures.ReturnNode;
import org.jgrapht.graph.DirectedPseudograph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedPseudograph;
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
	private static void recursivelyGetAssignments(Node node, LinkedList<String> ret){
		if (node instanceof AssignExpr){
			AssignExpr ae = (AssignExpr)node;
			ret.add(ae.getTarget().toString());
		}
		else if (node instanceof VariableDeclarator){
			VariableDeclarator vd = (VariableDeclarator)node;
			ret.add(vd.getNameAsString());
		}

		for (Node child : node.getChildNodes()){
			recursivelyGetAssignments(child, ret);
		}
	}

	private static LinkedList<String> assignmentsWithin(Node n){
		LinkedList<String> ret = new LinkedList<String>();
		recursivelyGetAssignments(n, ret);
		System.err.println("Assignments within " + n + ": " + ret);
		return ret;
	}

	private class ReachingDefs{
		private TreeMap<String, HashSet<NodeWrapper>> innerMap = 
				new TreeMap<String, HashSet<NodeWrapper>>();

		public HashSet<NodeWrapper> defsFor(String var){
			HashSet<NodeWrapper> reaching = this.innerMap.get(var);
			if (var != null){
				return reaching;
			}
			return new HashSet<NodeWrapper>();
		}

		public void addDefFor(String var, NodeWrapper nw){
			HashSet<NodeWrapper> reaching = this.innerMap.get(var);
			if (var != null){
				reaching.add(nw);
			}
			this.innerMap.put(var, new HashSet<NodeWrapper>());
			this.innerMap.get(var).add(nw);
		}

		public void addAll(ReachingDefs that){
			for (Entry<String, HashSet<NodeWrapper>> thatEntry : that.innerMap.entrySet()){
				HashSet<NodeWrapper> ourEntry = this.innerMap.get(thatEntry.getKey());
				if (ourEntry == null){
					ourEntry = new HashSet<NodeWrapper>();
					this.innerMap.put(thatEntry.getKey(), ourEntry);
				}
				ourEntry.addAll(thatEntry.getValue());
			}
		}

		public void addAllFor(String var, HashSet<NodeWrapper> newEntries){
			HashSet<NodeWrapper> ourEntry = this.innerMap.get(var);
			if (ourEntry == null){
				ourEntry = new HashSet<NodeWrapper>();
				this.innerMap.put(var, ourEntry);
			}
			ourEntry.addAll(newEntries);

		}

		public Set<Entry<String, HashSet<NodeWrapper>>> entries(){
			return this.innerMap.entrySet();
		}


	}

	private class Worklist{

		private LinkedHashSet<NodeWrapper> innerSet;

		public boolean isEmpty(){
			return innerSet.isEmpty();
		}

		public void push(NodeWrapper elem){
			innerSet.add(elem);
		}

		//public void push(Collection<NodeWrapper> coll){
		//	innerSet.addAll(coll);
		//}

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

	private final Method initialMethod;

	private final DirectedPseudograph<NodeWrapper, DefaultEdge> cfg;
	private final Set<Node> allNodes;
	private Worklist worklist;

	private TreeMap<NodeWrapper, ReachingDefs> exitSet = new TreeMap<NodeWrapper, ReachingDefs>();
	private TreeMap<NodeWrapper, ReachingDefs> entrySet = new TreeMap<NodeWrapper, ReachingDefs>();

	private NodeWrapper initialNode;

	private ReachingDefs entrySetFor(NodeWrapper nw){
		return this.entrySet.get(nw);
	}

	private ReachingDefs exitSetFor(NodeWrapper nw){
		return this.exitSet.get(nw);
	}


	ReachingDefs bottom(){
		return new ReachingDefs();
	}

	private boolean inKillSet(String defVar, NodeWrapper node){

		//Assignments kill definitions of the variables they assign to
		for (String assignedVar: assignmentsWithin(node.NODE)){
			//Checkin if the think we possibly kill is 
			//an assignment to the variable we are assigning
			if (assignedVar == defVar){
				return true;
			}
		}

		//Only assignments kill
		//TODO other things that kill?
		return false;
	}

	ReachingDefs genSet(NodeWrapper node){
		ReachingDefs ret = new ReachingDefs();

		//Assignments generate definitions of the variables they assign to
		for (String assignedExpr : assignmentsWithin(node.NODE)){
			//Check if the thing we possibly kill is 
			//an assignment to the variable we are assigning
			ret.addDefFor(assignedExpr, node);
			return ret;
		}

		//TODO other things that gen? Method calls?

		return ret;
	}


	public DataDependencyGraphFinder(DirectedPseudograph<NodeWrapper, DefaultEdge> cfg, 
			Method method,
			NodeWrapper initialNode){
		this.cfg = cfg;
		this.initialMethod = method;
		this.initialNode = initialNode;
		Set<NodeWrapper> cfgNodeWrappers = cfg.vertexSet();
		ArrayList<Node> cfgNodeList = new ArrayList<Node>();
		for(NodeWrapper n: cfgNodeWrappers){
			cfgNodeList.add(n.NODE);
		}
		this.allNodes = new HashSet<Node>(cfgNodeList);
		this.worklist = new Worklist();


		System.err.println("\n\n\n************\nDDG Method:\n" + this.initialMethod.getBody());
		System.err.println("*** CFG:\n" + PDGGraphViz.stringDot(cfg));

	}

	public DirectedPseudograph<NodeWrapper, DefaultEdge> findReachingDefs(){
		//Get the function parameters
		//Create a DFG node for each one
		TreeMap<String, NodeWrapper> paramNodes = new TreeMap<String, NodeWrapper>();
		for (Parameter p : initialMethod.getMethodParameters()){
			paramNodes.put(p.getNameAsString(), new NodeWrapper(p));
			//Add parameter to our node list
			allNodes.add(p);
		}

		//Set the reaching defs to bottom (empty set) for each node
		//Add all nodes to our worklist initially, since we need to update values for each node
		for (Node n : allNodes){

			NodeWrapper nw = new NodeWrapper(n);
			System.err.println("Creating sets for " + n + " addr " + nw);
			this.entrySet.put(nw, bottom());
			exitSet.put(nw, bottom());


			//Initial nodes have all free variables in their entry and exit sets
			if (n.equals(this.initialNode)){ 

				ReachingDefs allFrees = new ReachingDefs();
				for (String fv : ASTUtil.freeVars(initialMethod.getBody())){
					//All non-parameters are free variables
					//That are initially defined at the initial node
					NodeWrapper paramNode = paramNodes.get(fv);
					if (paramNode == null){
						allFrees.addDefFor(fv, nw);
					} else {
						allFrees.addDefFor(fv, paramNode);
					}
				}
				this.entrySetFor(nw).addAll(allFrees);
				exitSetFor(nw).addAll(allFrees);
				//Initial node goes into the worklist
				worklist.push(nw);
			}
			//Parameter nodes define their parameters
			else if (n instanceof Parameter){
				Parameter p = (Parameter)n;
				this.entrySetFor(nw).addDefFor(p.getNameAsString(), nw);
				exitSetFor(nw).addDefFor(p.getNameAsString(), nw);

			} else {
				//Non special nodes go in the worklist
				worklist.push(nw);
			}
		}



		//Keep updating by the dataflow equations until nothing is on our worklist
		while (!worklist.isEmpty()){
			NodeWrapper currentNode = worklist.pop();
			System.err.println("Entry Set: " + this.entrySet);
			System.err.println("Entry set contains node? " + this.entrySet.containsKey(currentNode));
			System.err.println("Worklist popped " + currentNode.NODE + " addr " + currentNode);
			ReachingDefs currentEntry = this.entrySet.get(currentNode);
			ReachingDefs currentExit = exitSet.get(currentNode);

			ReachingDefs newEntry = 
					new ReachingDefs();
			newEntry.addAll(this.entrySetFor(currentNode));
			ReachingDefs newExit = 
					new ReachingDefs();
			newExit.addAll(exitSetFor(currentNode));





			//Our entry contains all of our predecessors' exits
			for (DefaultEdge incomingEdge : cfg.incomingEdgesOf(currentNode) ){
				NodeWrapper previousNode = cfg.getEdgeSource(incomingEdge);
				if(exitSetFor(previousNode)!=null){
					newEntry.addAll(exitSetFor(previousNode));
				}
				System.err.println("Adding " + exitSetFor(previousNode) 
				+ " from " + previousNode.NODE + " to entry for " + currentNode.NODE);
			}

			System.err.println("New entry after union " + newEntry);


			//Our exit set: add everything from entry that wasn't killed
			//Plus anything new we defined in this statement
			for (Entry<String, HashSet<NodeWrapper>> varAndDefs : newEntry.entries()){
				if (!inKillSet(varAndDefs.getKey(), currentNode)){
					newExit.addAllFor(varAndDefs.getKey(), varAndDefs.getValue());
				}
			}
			System.err.println("Adding newly generated set " + genSet(currentNode) + " for node " + currentNode.NODE);
			newExit.addAll(genSet(currentNode));

			if (newEntry == null && newExit == null){
				throw new RuntimeException("Null sets");
			}

			//Update our new entry and exit values
			//System.out.println("ddg exitset "+exitSet);
			System.err.println("New entry set " + newEntry + " for " + currentNode);
			System.err.println("New exit set " + newExit + " for " + currentNode);
			this.entrySet.put(currentNode, newEntry);
			exitSet.put(currentNode, newExit);

			if (currentNode.toString().equals("datastructures.NodeWrapper@ee23fc61")){
				System.err.println("FOO");
			}

			//If we changed, add all our successors to the worklist
			if (!newExit.equals(currentExit)){
				Set<DefaultEdge> outgoingEdges = cfg.outgoingEdgesOf(currentNode);
				for (DefaultEdge edge : outgoingEdges){
					worklist.push(cfg.getEdgeTarget(edge));
					System.err.println("Adding " + cfg.getEdgeTarget(edge).NODE + " to worklist");
				}

			}
			else {
				System.err.println("No need to update. New: " + newExit + "  , old  " + currentExit);
			}

		}

		//DirectedPseudograph<Node, DefaultEdge> ret =
		//		new DirectedPseudograph<Node, DefaultEdge>(DefaultEdge.class);
		DirectedPseudograph<NodeWrapper, DefaultEdge> ret2 =
				new DirectedPseudograph<NodeWrapper, DefaultEdge>(DefaultEdge.class);

		for (NodeWrapper nw : exitSet.keySet()){
			ret2.addVertex(nw);
		}

		//Manually add am exit node
		NodeWrapper exitNode = new NodeWrapper(new ReturnNode());
		ret2.addVertex(exitNode);

		//Add all our params too
		for (NodeWrapper p : paramNodes.values()){
			ret2.addVertex(p);
		}

		for (NodeWrapper nw : exitSet.keySet()){
			for (Entry<String, HashSet<NodeWrapper>> defPair : exitSetFor(nw).entries()){
				for (NodeWrapper defLoc : defPair.getValue()){
					//If a def reaches a node, and that node references that variable
					//then we have an edge
					//TODO check use, not occurrence
					if (ASTUtil.occursFree(nw.NODE, defPair.getKey())){
						//ret.addEdge(defPair.b, n);
						if (!ret2.containsVertex(defLoc)){
							ret2.addVertex(defLoc);
						}
						ret2.addEdge(defLoc, nw);
					} else{
						//System.out.println("Node " + nw.NODE + ", " + " doesn't contain " + defPair.a);
					}
				}
			}
			//If this is a return statement, then we have an edge to ExitNode
			//The special node representing the "value" assigned to
			//i.e. the value returned
			if (nw.NODE instanceof ReturnStmt){
				ret2.addEdge(nw, exitNode);
			}
		}

		System.err.println("Made DDG:\n" + PDGGraphViz.stringDot(ret2));

		return ret2;
	}

}
