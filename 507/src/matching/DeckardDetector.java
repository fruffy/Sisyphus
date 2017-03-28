package matching;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import datastructures.Method;
import datastructures.NodeFeature;

public class DeckardDetector extends CloneDetector {
	private double match;

	public DeckardDetector(List<Method> libMethods) {
		super(libMethods);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Takes a list of Method objects and matches it against a reference library
	 * Returns a list of matched methods. Compare NodeFeatures of Methods
	 * 
	 * @param srcMethods
	 * @return
	 */
	public List<Method[]> findSimiliarMethods(List<Method> srcMethods) {

		List<Method[]> matchedMethods = new ArrayList<Method[]>();
		if (methodLibrary == null) {
			System.out.println("Warning: Method library not initialised, nothing to compare to!");
			return matchedMethods;
		}
		double bestMatch;
		for (Method src : srcMethods) {
			Method[] finalMatched = null;
			bestMatch = Integer.MAX_VALUE;
			this.match = 0;
			for (Method ref : this.methodLibrary) {
				if (matchMethods(src, ref, 30)) {
					Method[] matched = { src, ref };

					if (bestMatch >= this.match) {
						bestMatch = this.match;
						finalMatched = matched;
					}
					// matchedMethods.add(matched);
				}
			}
			if (finalMatched != null) {
				matchedMethods.add(finalMatched);
				/*
				 * System.out.println("Match! " + finalMatched[0].getSignature()
				 * + " with return type " + finalMatched[0].getReturnType() +
				 * " can be replaced by " + finalMatched[1].getSignature() +
				 * " with return type " + finalMatched[1].getReturnType());
				 */
			}
		}
		return matchedMethods;

	}

	/*
	 * Calculate the squared euclidean distance between vec1 and vec2
	 */
	private double calculateDistance(int[] vec1, int[] vec2) {
		double distance = 0.0;
		for (int i = 0; i < vec1.length; i++) {
			distance += Math.abs(vec1[i] - vec2[i]);
		}
		return distance;
	}

	/*
	 * Check if distance between method1 NodeFeature and method2 NodeFeature is
	 * below the threshold
	 */
	public boolean matchMethods(Method method1, Method method2, double threshold) {

		if (!method1.getReturnType().equals(method2.getReturnType())) {
			return false;
		}
		if (method1.getMethodParameters().size() != method2.getMethodParameters().size()) {
			return false;
		}

		if (!checkParameters(method1.getMethodParameters(), method2.getMethodParameters())) {
			return false;
		}

		NodeFeature feature1 = method1.getMethodFeature();
		NodeFeature feature2 = method2.getMethodFeature();

		feature1.makeComparableNodeFeatures(feature2);
		HashMap<String, Integer> featureMap1 = feature1.getFeatureMap();
		HashMap<String, Integer> featureMap2 = feature2.getFeatureMap();
		/*
		 * System.out.println("featureMap1 "); System.out.println(featureMap1);
		 * System.out.println("featureMap2 "); System.out.println(featureMap2);
		 */
		int[] featureArray1 = new int[feature1.getFeatureVectorSize()];
		int[] featureArray2 = new int[feature2.getFeatureVectorSize()];

		int count = 0;
		for (String key : featureMap1.keySet()) {
			featureArray1[count] = featureMap1.get(key);
			featureArray2[count] = featureMap2.get(key);
			count++;
		}

		double dist = calculateDistance(featureArray1, featureArray2);
		this.match = dist;
		if (dist <= threshold) {
			return true;
		}
		return false;
	}

	// USELESS STUFF THAT WE MIGHT NEED LATER
	/*
	 * public List<Method[]> findSimiliarMethodsPDGDeckard(List<Method>
	 * srcMethods) {
	 * 
	 * List<Method[]> matchedMethods = new ArrayList<Method[]>(); if
	 * (methodLibrary == null) { System.out.
	 * println("Warning: Method library not initialised, nothing to compare to!"
	 * ); return matchedMethods; } for (Method src : srcMethods) { for (Method
	 * ref : methodLibrary) { if (matchMethodPDGsDeckard(src, ref, 4)) {
	 * Method[] matched = { src, ref }; matchedMethods.add(matched); }
	 * 
	 * } } return matchedMethods;
	 * 
	 * }
	 * 
	 * private NodeFeature getNodeFeature(DirectedPseudograph<Node, DefaultEdge>
	 * pdg, Node current, DirectedPseudograph<Node, DefaultEdge> visited) {
	 * NodeFeature nodeFeature = new NodeFeature(); if (!(current instanceof
	 * EntryStmt)) { nodeFeature.addNode(current.toString()); } Set<DefaultEdge>
	 * edges1Set = pdg.outgoingEdgesOf(current); if (edges1Set.size() == 0) {
	 * return nodeFeature; } for (DefaultEdge edge : edges1Set) { Node target =
	 * pdg.getEdgeTarget(edge); // System.out.println("target // "+target);
	 * visited.addVertex(target); if (!visited.containsEdge(current, target)) {
	 * visited.addEdge(current, target); NodeFeature childMethodFeature =
	 * getNodeFeature(pdg, target, visited);
	 * nodeFeature.combineNodeFeatures(childMethodFeature); } } return
	 * nodeFeature;
	 * 
	 * }
	 * 
	 * public boolean matchMethodPDGsDeckard(Method method1, Method method2,
	 * double threshold) { DirectedPseudograph<Node, DefaultEdge> method1pdg =
	 * method1.getPDG(); DirectedPseudograph<Node, DefaultEdge> method2pdg =
	 * method2.getPDG(); DirectedPseudograph<Node, DefaultEdge> visited1 = new
	 * DirectedPseudograph<>(DefaultEdge.class); DirectedPseudograph<Node,
	 * DefaultEdge> visited2 = new DirectedPseudograph<>(DefaultEdge.class);
	 * 
	 * // Get the root nodes of the method pdg's Iterator<Node> iter1 =
	 * method1pdg.vertexSet().iterator(); Iterator<Node> iter2 =
	 * method2pdg.vertexSet().iterator();
	 * 
	 * Node v1 = iter1.next(); Node v2 = iter2.next();
	 * 
	 * visited1.addVertex(v1); visited2.addVertex(v2);
	 * 
	 * NodeFeature feature1 = getNodeFeature(method1pdg, v1, visited1);
	 * NodeFeature feature2 = getNodeFeature(method2pdg, v2, visited2); //
	 * System.out.println("feature1: "+feature1.getFeatureMap()); //
	 * System.out.println("feature2: "+feature2.getFeatureMap());
	 * 
	 * feature1.makeComparableNodeFeatures(feature2); HashMap<String, Integer>
	 * featureMap1 = feature1.getFeatureMap(); HashMap<String, Integer>
	 * featureMap2 = feature2.getFeatureMap();
	 * 
	 * int[] featureArray1 = new int[feature1.getFeatureVectorSize()]; int[]
	 * featureArray2 = new int[feature2.getFeatureVectorSize()]; int count = 0;
	 * for (String key : featureMap1.keySet()) { featureArray1[count] =
	 * featureMap1.get(key); featureArray2[count] = featureMap2.get(key);
	 * count++; }
	 * 
	 * double dist = calculateDistance(featureArray1, featureArray2); //
	 * System.out.println("distance "+dist); if (dist <= threshold) { return
	 * true; } return false;
	 * 
	 * }
	 */
}