package graphs;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedPseudograph;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.expr.UnaryExpr;
import com.github.javaparser.ast.stmt.ReturnStmt;
//import datastructures.NicePair;

import datastructures.Method;
import datastructures.NodeWrapper;
import visitors.ASTUtil;

/**
 * Based on "Reaching Definitions" from Principles of Program Analysis
 * by Nielson, Nielson and Hankin
 * pp 43-44
 * 
 * 
 * This is a bit of a mess. Here be dragons.
 *
 */
public class DataDependencyGraphBuilder {

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
		else if (node instanceof UnaryExpr){
			UnaryExpr ue = (UnaryExpr)node;
			ret.add(ue.getExpression().toString());
		}

		for (Node child : node.getChildNodes()){
			recursivelyGetAssignments(child, ret);
		}
	}

	private static LinkedList<String> assignmentsWithin(Node n){
		LinkedList<String> ret = new LinkedList<String>();
		recursivelyGetAssignments(n, ret);
		return ret;
	}

	private class ReachingDefs{



		private TreeMap<String, HashSet<NodeWrapper>> innerMap = 
				new TreeMap<String, HashSet<NodeWrapper>>();

		public ReachingDefs(){}

		public ReachingDefs(ReachingDefs that){
			this.innerMap = new TreeMap<String, HashSet<NodeWrapper>>(that.innerMap);
		}

		public HashSet<NodeWrapper> defsFor(String var){
			HashSet<NodeWrapper> reaching = this.innerMap.get(var);
			if (var != null){
				return reaching;
			}
			return new HashSet<NodeWrapper>();
		}

		public void addDefFor(String var, NodeWrapper nw){
			HashSet<NodeWrapper> reaching = this.innerMap.get(var);
			if (reaching == null){
				reaching = new HashSet<NodeWrapper>();
				this.innerMap.put(var, reaching);
			}
			reaching.add(nw);
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

		@Override
		public String toString(){
			return this.innerMap.toString();
		}

		@Override
		public boolean equals(Object arg){
			ReachingDefs that = (ReachingDefs)arg;
			return this.subsetOf(that) && that.subsetOf(this);
		}

		public boolean subsetOf(ReachingDefs that){
			for (Entry<String, HashSet<NodeWrapper>> thatEntry : that.entries()){
				if (!this.innerMap.containsKey(thatEntry.getKey())){
					return thatEntry.getValue().isEmpty();
				}
				else {
					for (NodeWrapper def : thatEntry.getValue()) {
						if (!this.innerMap.get(thatEntry.getKey()).contains(def)){
							return false;
						}
					}
				}
			}
			return true;
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
	private final Set<NodeWrapper> allNodes;
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


	public DataDependencyGraphBuilder(DirectedPseudograph<NodeWrapper, DefaultEdge> cfg, 
			Method method,
			NodeWrapper initialNode){
		this.cfg = cfg;
		this.initialMethod = method;
		this.initialNode = initialNode;
		Set<NodeWrapper> cfgNodeWrappers = cfg.vertexSet();
		this.allNodes = new HashSet<NodeWrapper>();
		for(NodeWrapper n: cfgNodeWrappers){
			allNodes.add(n);
		}

		this.worklist = new Worklist();



	}

	public DirectedPseudograph<NodeWrapper, DefaultEdge> findReachingDefs(){
		//Get the function parameters
		//Create a DFG node for each one

		//System.err.println("Making DDG for " + this.initialMethod.getBody());

		//Set the reaching defs to bottom (empty set) for each node
		//Add all nodes to our worklist initially, since we need to update values for each node
		for (NodeWrapper nw : allNodes){
			Node n = nw.NODE;

			this.entrySet.put(nw, bottom());
			this.exitSet.put(nw, bottom());


			//Initial nodes have all free variables in their entry and exit sets
			if (this.initialNode != null && this.initialNode == nw){ 
				ReachingDefs allFrees = new ReachingDefs();
				//for (NodeWrapper anyNode : this.allNodes){
					for (String fv : ASTUtil.freeVars(this.initialMethod.getBody())){
						allFrees.addDefFor(fv, new NodeWrapper(new SimpleName("//INITIAL VALUE OF " + fv)));
					}
				//}
				//HashSet<String> x = ASTUtil.freeVars(initialMethod.getBody());
				//for (String fv : ASTUtil.freeVars(initialMethod.getBody())){
				//	allFrees.addDefFor(fv, new NodeWrapper(new SimpleName("//INITIAL VALUE OF " + fv)));
				//}
				this.entrySetFor(nw).addAll(allFrees);
			}

			worklist.push(nw);

		}



		//Keep updating by the dataflow equations until nothing is on our worklist
		while (!worklist.isEmpty()){
			NodeWrapper currentNode = worklist.pop();
			ReachingDefs currentEntry = this.entrySet.get(currentNode);
			ReachingDefs currentExit = exitSet.get(currentNode);

			ReachingDefs newEntry = 
					new ReachingDefs(currentEntry);
			ReachingDefs newExit = 
					new ReachingDefs(currentExit);





			//Our entry contains all of our predecessors' exits
			for (DefaultEdge incomingEdge : cfg.incomingEdgesOf(currentNode) ){
				NodeWrapper previousNode = cfg.getEdgeSource(incomingEdge);
				if(exitSetFor(previousNode)!=null){
					newEntry.addAll(exitSetFor(previousNode));
				}
			}



			//Our exit set: add everything from entry that wasn't killed
			//Plus anything new we defined in this statement
			for (Entry<String, HashSet<NodeWrapper>> varAndDefs : newEntry.entries()){
				if (!inKillSet(varAndDefs.getKey(), currentNode)){
					newExit.addAllFor(varAndDefs.getKey(), varAndDefs.getValue());
				}
			}
			newExit.addAll(genSet(currentNode));



			//Update our new entry and exit values
			//System.out.println("ddg exitset "+exitSet);
			this.entrySet.put(currentNode, newEntry);
			exitSet.put(currentNode, newExit);


			//If we changed, add all our successors to the worklist
			if (!newExit.equals(currentExit)){
				Set<DefaultEdge> outgoingEdges = cfg.outgoingEdgesOf(currentNode);
				for (DefaultEdge edge : outgoingEdges){
					worklist.push(cfg.getEdgeTarget(edge));

				}

			}
			else {
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
		NodeWrapper exitNode = new NodeWrapper(new SimpleName("//[RETURNED VALUE]"));
		ret2.addVertex(exitNode);

		HashMap<String, NodeWrapper> freeNodes = new HashMap<String, NodeWrapper>();


		for (NodeWrapper nw : entrySet.keySet()){
			for (Entry<String, HashSet<NodeWrapper>> defPair : entrySetFor(nw).entries()){

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

		//System.err.println("Made DDG:\n" + PDGGraphViz.stringDot(ret2));


		return ret2;
	}

}
