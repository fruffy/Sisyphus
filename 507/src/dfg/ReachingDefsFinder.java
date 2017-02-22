package dfg;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.utils.Pair;

import cfg.CFGNode;

public class ReachingDefsFinder {
	
	private class Worklist{
		
		private LinkedHashSet<CFGNode> innerSet;

		public boolean isEmpty(){
			return innerSet.isEmpty();
		}
		
		public void push(CFGNode elem){
			innerSet.add(elem);
		}
		
		public void push(Collection<CFGNode> coll){
			innerSet.addAll(coll);
		}
		
		public CFGNode pop(){
			Iterator<CFGNode> iterator = innerSet.iterator();
			CFGNode result = iterator.next();
			iterator.remove();
			return result;
		}
		
		public Worklist(){
			this.innerSet = new LinkedHashSet<CFGNode>();
		}
		
	}
	
	private final Set<CFGNode> allNodes;
	private Worklist worklist;
	
	private HashMap<CFGNode, Set<Pair<String, AssignExpr>>> exitSet;
	private HashMap<CFGNode, Set<Pair<String, AssignExpr>>> entrySet;
	
	static Set<Pair<String, AssignExpr>> bottom(){
		return new HashSet<Pair<String, AssignExpr>>();
	}
	
	//TODO move this into CFG package?
	static Set<Pair<String, AssignExpr>> killSet(CFGNode node){
		return null;
	}
	
	static Set<Pair<String, AssignExpr>> genSet(CFGNode node){
		return null;
	}
	
	
	public ReachingDefsFinder(Set<CFGNode> allNodes){
		this.allNodes = allNodes;
		this.worklist = new Worklist();
	}
	
	public Map<CFGNode, Set<Pair<String, AssignExpr>>> findReachingDefs(){
		//Set the reaching defs to bottom (empty set) for each node
		for (CFGNode node : allNodes){
			entrySet.put(node, bottom());
			exitSet.put(node, bottom());
		}
		
		
		//Add all nodes to our worklist initially, since we need to update values for each node
		worklist.push(allNodes);
		
		//Keep updating by the dataflow equations until nothing is on our worklist
		while (!worklist.isEmpty()){
			CFGNode currentNode = worklist.pop();
			Set<Pair<String, AssignExpr>> currentEntry = entrySet.get(currentNode);
			Set<Pair<String, AssignExpr>> currentExit = exitSet.get(currentNode);
			
			Set<Pair<String, AssignExpr>> newEntry = entrySet.get(currentNode);
			Set<Pair<String, AssignExpr>> newExit = exitSet.get(currentNode);
			
			if (false){ //TODO check if is initial label
				//TODO set to free vars of graph
			}
			else {
				//Our entry is our predecessor's exit
				for (CFGNode predNode : currentNode.getPredecessors()){
					newEntry.addAll(exitSet.get(predNode));
				}
				
				//Our exit set: add everything from entry that wasn't killed
				//Plus anything new we defined in this statement
				Set<Pair<String, AssignExpr>> killSet = killSet(currentNode);
				for (Pair<String, AssignExpr> pair : newEntry){
					if (!killSet.contains(pair)){
						newExit.add(pair);
					}
				}
				newExit.addAll(genSet(currentNode));
			}
			
			//Update our new entry and exit values
			entrySet.put(currentNode, newEntry);
			exitSet.put(currentNode, newExit);
			
			//Add all our successors to the worklist
			//TODO faster successor check?
			for (CFGNode maybeSuccessor : allNodes){
				if (maybeSuccessor.getPredecessors().contains(currentNode)){
					worklist.push(maybeSuccessor);
				}
			}
		}
		
		
		//TODO which to return?
		return exitSet;
	}

}
