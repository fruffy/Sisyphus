package core;
import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.Parameter;

/*
 * The detector class
 * Takes the parser output and tries to find similarities
 * Right now we only have a simple line by line comparison of a full file
 * 
*/

public class CloneDetector {
	private List <Method> methodLibrary;

	public CloneDetector(List<Method> libMethods) {
		this.methodLibrary = libMethods;
	}
	
	
	public List<Method> findSimiliarMethods(List<Method> srcMethods) {
		List<Method> matchedMethods = new ArrayList<Method>();
		for (Method src : srcMethods) {
			for (Method ref : methodLibrary) {
				if (matchMethodNodeFeatures(src, ref, 1)) {
					System.out.println("Match! "+ src.getMethodName() +" can be replaced by "+ ref.getMethodName());
					matchedMethods.add(src);
				}

			}
		}
		return matchedMethods;

	}
	
	/*
	 * Checks if the abstract syntax tree of the body of method 1
	 * is the same as that of method2
	 */
	private boolean matchMethodNodes(Method method1, Method method2){
		List<Node> nodes1 = method1.getMethodNodes();
		List<Node> nodes2 = method2.getMethodNodes();
		if(nodes1.size()!=nodes2.size()){
			return false;
		}

		for(int i = 0; i<nodes1.size(); i++){
			//System.out.println(nodes1.get(i).getClass());
			if(!nodes1.get(i).getClass().equals(nodes2.get(i).getClass())){
				return false;
			}
		}
		return true;
	}
	
	/*
	 * Calculate the squared euclidean distance between vec1 and vec2
	 */
	private double calculateDistance(int[] vec1, int[] vec2){
		double distance = 0.0;
		for(int i = 0; i<vec1.length; i++){
			distance += (vec1[i] - vec2[i])*(vec1[i] - vec2[i]);
		}
		return distance;
	}
	
	/*
	 * Check if distance between method1 NodeFeature and method2 NodeFeature is below the
	 * threshold
	 */
	public boolean matchMethodNodeFeatures(Method method1, Method method2,double threshold){
		NodeFeature feature1 = method1.getMethodFeature();
		NodeFeature feature2 = method2.getMethodFeature();
		feature1.makeComparableNodeFeatures(feature2);
		//System.out.println(feature1.getFeatureVectorSize());
		//System.out.println(feature2.getFeatureVectorSize());
		int[] featureArray1 = feature1.getFeatureVector();
		int[] featureArray2 = feature2.getFeatureVector();
		double dist = calculateDistance(featureArray1,featureArray2);
		if(dist <= threshold){
			return true;
		}
		return false;
	}
	
	
	/*
	 * This method should do some kind of comparison between Method 1 and Method
	 * 2 and return true if they are exact/near matches. Right now they just
	 * return true if they are exact matches
	 */
	public boolean matchMethods(Method method1, Method method2) {
		if (method1.getMethodName().compareToIgnoreCase(method2.getMethodName()) != 0) {
			return false;

		}
		if (method1.getReturnType().toString().compareTo(method2.getReturnType().toString()) != 0) {
			return false;
		}
		List<Parameter> parameters1 = method1.getMethodParameters();
		List<Parameter> parameters2 = method2.getMethodParameters();
		if (parameters1.size() != parameters2.size()) {
			return false;
		}
		boolean[] param2Matched = new boolean[parameters2.size()];
		for (int i = 0; i < parameters1.size(); i++) {
			boolean foundMatch = false;
			for (int j = 0; j < parameters2.size(); j++) {
				if (parameters1.get(i).getType().toString().compareTo(parameters2.get(j).toString()) == 0) {
					if (!param2Matched[j]) {
						param2Matched[j] = true;
						foundMatch = true;
					}

				}
			}
			if (!foundMatch) {
				return false;
			}
		}
		return matchMethodNodes(method1,method2);

	}

}
