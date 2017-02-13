package core;

import java.util.ArrayList;
import java.util.HashMap;
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
	private List<Method> methodLibrary;

	public CloneDetector(List<Method> libMethods) {
		this.methodLibrary = libMethods;
	}

	public CloneDetector() {
	}

	/**
	 * Takes a list of Method objects and matches it against a reference library
	 * Returns a list of matched methods
	 * 
	 * @param srcMethods
	 * @return
	 */
	public List<Method> findSimiliarMethods(List<Method> srcMethods) {

		List<Method> matchedMethods = new ArrayList<Method>();
		if (methodLibrary == null) {
			System.out.println("Warning: Method library not initialised, nothing to compare to!");
			return matchedMethods;
		}
		for (Method src : srcMethods) {
			for (Method ref : methodLibrary) {
				if (matchMethodNodeFeatures(src, ref,1)) {
					System.out.println("Match! " + src.getMethodName() + " with return type " + src.getReturnType() + 
							" can be replaced by " + ref.getMethodName()+ " with return type " + ref.getReturnType());
					matchedMethods.add(src);
				}

			}
		}
		return matchedMethods;

	}

	/**
	 * Takes a list of Method objects and matches it against a provided
	 * reference library Returns a list of matched methods
	 * 
	 * @param srcMethods
	 * @return
	 */
	public List<Method> findSimiliarMethods(List<Method> srcMethods, List<Method> refMethods) {
		List<Method> matchedMethods = new ArrayList<Method>();
		for (Method src : srcMethods) {
			for (Method ref : refMethods) {
				if (matchMethodNodes(src, ref)) {
					System.out.println("Match! " + src.getMethodName() + " can be replaced by " + ref.getMethodName());
					matchedMethods.add(src);
				}

			}
		}
		return matchedMethods;

	}

	/*
	 * Checks if the abstract syntax tree of the body of method 1 is the same as
	 * that of method2
	 */
	public boolean matchMethodNodes(Method method1, Method method2) {
		List<Node> nodes1 = method1.getMethodNodes();
		List<Node> nodes2 = method2.getMethodNodes();
		if (nodes1.size() != nodes2.size()) {
			return false;
		}

		for (int i = 0; i < nodes1.size(); i++) {
			if (!nodes1.get(i).getClass().equals(nodes2.get(i).getClass())) {
				return false;
			}
		}
		return true;
	}

	/*
	 * Calculate the squared euclidean distance between vec1 and vec2
	 */
	private double calculateDistance(int[] vec1, int[] vec2) {
		double distance = 0.0;
		for (int i = 0; i < vec1.length; i++) {
			distance += (vec1[i] - vec2[i]) * (vec1[i] - vec2[i]);
		}
		return distance;
	}

	/*
	 * Check if distance between method1 NodeFeature and method2 NodeFeature is
	 * below the threshold
	 */
	public boolean matchMethodNodeFeatures(Method method1, Method method2, double threshold) {
		NodeFeature feature1 = method1.getMethodFeature();
		NodeFeature feature2 = method2.getMethodFeature();
		feature1.makeComparableNodeFeatures(feature2);
		HashMap<String,Integer> featureMap1 = feature1.getFeatureMap();
		HashMap<String,Integer> featureMap2 = feature2.getFeatureMap();
		
		int[] featureArray1 = new int[feature1.getFeatureVectorSize()];
		int[] featureArray2 = new int[feature2.getFeatureVectorSize()];
		int count = 0;
		for(String key:featureMap1.keySet()){
			featureArray1[count] = featureMap1.get(key);
			featureArray2[count] = featureMap2.get(key);
			count++;
		}
		
		/*System.out.println("After comparison: ");
		System.out.println(method1.getMethodName());
		System.out.println(feature1.getFeatureMap());
		for(Integer val: featureArray1){
			System.out.print(val+" ");
		}
		System.out.println(method2.getMethodName());
		System.out.println(feature2.getFeatureMap());
		for(Integer val: featureArray2){
			System.out.print(val+" ");
		}*/
		
		double dist = calculateDistance(featureArray1, featureArray2);
		if (dist <= threshold) {
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
		return matchMethodNodes(method1, method2);

	}

}
