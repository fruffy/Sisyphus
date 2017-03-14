package core;
import java.util.HashMap;

import com.github.javaparser.ast.Node;

/*
 * A class to hold counts of different node types in
 * a hashmap
 */
public class NodeFeature {
	private int featureVectorSize;
	private HashMap<Node,Integer> featureMap;
	
	public NodeFeature(){
		featureVectorSize = 0;
		featureMap =  new HashMap<Node,Integer>();
	}
	
	/*
	 * increase the count of class in the feature vector.
	 * If it does not exist add it to the hashmap
	 */
	public void addNode(Node node){
		if(featureMap.containsKey(node)){
			featureMap.put(node, featureMap.get(node)+1);
		}
		else{
			featureMap.put(node, 1);
			featureVectorSize++;
		}
	}
	
	/*
	 * Add nodeClass as a dummy class with a count of zero
	 * just for the sake of comparison purposes later
	 */
	public void addDummyNode(Node node){
		featureMap.put(node, 0);
		featureVectorSize++;
	}
	
	public int getFeatureVectorSize(){
		return featureVectorSize;
	}
	
	public HashMap<Node,Integer> getFeatureMap(){
		return featureMap;
	}
	
	/*
	 * Add elements to this NodeFeatures object so that
	 * both of them (this and n) are comparable
	 */
	public void makeComparableNodeFeatures(NodeFeature n){
		for(Node key: n.getFeatureMap().keySet()){
			if(!featureMap.containsKey(key)){
				addDummyNode(key);
			}
		}
		for(Node key:this.featureMap.keySet()){
			if(!n.getFeatureMap().containsKey(key)){
				n.addDummyNode(key);
			}
		}
	}
	
	/*
	 * Add elements to this NodeFeatures object from n if this
	 * does not contain those elements
	 */
	public void combineNodeFeatures(NodeFeature n){
		for(Node key: n.getFeatureMap().keySet()){
			addNode(key);
		}
		
	}

}
