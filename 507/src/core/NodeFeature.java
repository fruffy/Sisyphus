package core;
import java.util.HashMap;

/*
 * A class to hold counts of different node types in
 * a hashmap
 */
public class NodeFeature {
	private int featureVectorSize;
	private HashMap<String,Integer> featureMap;
	
	public NodeFeature(){
		featureVectorSize = 0;
		featureMap =  new HashMap<String,Integer>();
	}
	
	/*
	 * increase the count of class in the feature vector.
	 * If it does not exist add it to the hashmap
	 */
	public void addNode(String nodeClass){
		if(featureMap.containsKey(nodeClass)){
			featureMap.put(nodeClass, featureMap.get(nodeClass)+1);
		}
		else{
			featureMap.put(nodeClass, 1);
			featureVectorSize++;
		}
	}
	
	/*
	 * Add nodeClass as a dummy class with a count of zero
	 * just for the sake of comparison purposes later
	 */
	public void addDummyNode(String nodeClass){
		featureMap.put(nodeClass, 0);
		featureVectorSize++;
	}
	
	public int getFeatureVectorSize(){
		return featureVectorSize;
	}
	
	public HashMap<String,Integer> getFeatureMap(){
		return featureMap;
	}
	
	/*
	 * Add elements to this NodeFeatures object so that
	 * both of them (this and n) are comparable
	 */
	public void makeComparableNodeFeatures(NodeFeature n){
		for(String key: n.getFeatureMap().keySet()){
			if(!featureMap.containsKey(key)){
				addDummyNode(key);
			}
		}
		for(String key:this.featureMap.keySet()){
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
		for(String key: n.getFeatureMap().keySet()){
			addNode(key);
		}
		
	}

}
