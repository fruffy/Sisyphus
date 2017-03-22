package core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.type.Type;

import datastructures.EntryStmt;
import datastructures.PDGGraphViz;
import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedPseudograph;

/*
 * The detector class
 * Takes the parser output and tries to find similarities
 * Right now we only have a simple line by line comparison of a full file
 * 
*/

public class CloneDetector {
	private List<Method> methodLibrary;
	private double mismatch;
	private double match;

	public CloneDetector(List<Method> libMethods) {
		this.methodLibrary = libMethods;
	}

	public CloneDetector() {
	}

	/**
	 * Takes a list of Method objects and matches it against a reference library
	 * Returns a list of matched methods. Compare AST's of methods
	 * 
	 * @param srcMethods
	 * @return
	 */
	public List<Method[]> findSimiliarMethodsAST(List<Method> srcMethods) {

		List<Method[]> matchedMethods = new ArrayList<Method[]>();
		if (methodLibrary == null) {
			System.out.println("Warning: Method library not initialised, nothing to compare to!");
			return matchedMethods;
		}
		for (Method src : srcMethods) {
			for (Method ref : methodLibrary) {
				if (matchMethodDeclaration(src, ref)) {
					Method[] matched = { src, ref };
					/*
					 * System.out.println("Match! " + src.getSignature() +
					 * " with return type " + src.getReturnType() +
					 * " can be replaced by " + ref.getSignature()+
					 * " with return type " + ref.getReturnType());
					 */
					matchedMethods.add(matched);
				}

			}
		}
		return matchedMethods;

	}

	/**
	 * Takes a list of Method objects and matches it against a reference library
	 * Returns a list of matched methods. Compare NodeFeatures of Methods
	 * 
	 * @param srcMethods
	 * @return
	 */
	public List<Method[]> findSimiliarMethodsNodeFeatures(List<Method> srcMethods) {

		List<Method[]> matchedMethods = new ArrayList<Method[]>();
		if (methodLibrary == null) {
			System.out.println("Warning: Method library not initialised, nothing to compare to!");
			return matchedMethods;
		}
		double bestMatch;
		for (Method src : srcMethods) {
			int count = 0;
			bestMatch = Integer.MAX_VALUE;
			for (Method ref : methodLibrary) {
				match = 0;
				if (matchMethodNodeFeatures(src, ref, 4)) {
					Method[] matched = { src, ref };
					if (bestMatch > match) {
						bestMatch = match;
						matchedMethods.add(matched);
					}
					/*System.out.println("Match! " + src.getSignature() + " with return type " + src.getReturnType()
							+ " can be replaced by " + ref.getSignature() + " with return type " + ref.getReturnType());*/

					//matchedMethods.add(matched);
				}
				count++;

			}
		}
		return matchedMethods;

	}

	/**
	 * Takes a list of Method objects and matches it against a reference library
	 * Returns a list of matched methods. Compare PDGs
	 * 
	 * @param srcMethods
	 * @return
	 */
	public List<Method[]> findSimiliarMethodsPDG(List<Method> srcMethods) {

		List<Method[]> matchedMethods = new ArrayList<Method[]>();
		if (methodLibrary == null) {
			System.out.println("Warning: Method library not initialised, nothing to compare to!");
			return matchedMethods;
		}
		double bestMatch = 0;
		for (Method src : srcMethods) {
			bestMatch = 0;
			for (Method ref : methodLibrary) {
				mismatch = 0;
				match = 0;

				if (matchMethodPDGs(src, ref)) {
					Method[] matched = { src, ref };
					if (bestMatch < match / mismatch) {
						bestMatch = match / mismatch;
						matchedMethods.add(matched);
					}
					/*System.out.print("Match! " + src.getSignature() + " with return type " + src.getReturnType()
							+ " can be replaced by " + ref.getSignature() + " with return type " + ref.getReturnType());*/

					//System.out.printf(" Confidence %.2f%%\n", match / mismatch);
					// matchedMethods.add(matched);
				}
			}
		}
		// return matchedMethods;
		return matchedMethods;

	}

	/**
	 * Compares edges edges edge1 and edge2 from method1pdg and method2pdf
	 * respectively
	 * 
	 * @param method1pdg
	 * @param method2pdg
	 * @param edge1
	 * @param edge2
	 * @return
	 */
	private boolean compareEdgeAttributes(DirectedGraph<Node, DefaultEdge> method1pdg,
			DirectedGraph<Node, DefaultEdge> method2pdg, DefaultEdge edge1, DefaultEdge edge2) {
		if (!(method1pdg.getEdgeSource(edge1) instanceof EntryStmt
				&& method2pdg.getEdgeSource(edge2) instanceof EntryStmt)) {
			if (!method1pdg.getEdgeSource(edge1).equals(method2pdg.getEdgeSource(edge2))) {
				return false;
			}
		}
		if (!method1pdg.getEdgeTarget(edge1).equals(method2pdg.getEdgeTarget(edge2))) {
			return false;
		}
		return true;
	}

	/**
	 * 
	 * Check if there exists a path of size k in g1 for which there is a similar
	 * path (with the same vertex and edge attributes) in g2 Store the
	 * respective similar subgraphs in maxGraph. Return true if we are able to
	 * find k-length similar paths. Return true if the length of similar paths
	 * is less than k but equal the longest path of g1 and g2 Return false
	 * otherwise Algorithm taken from the krinke paper
	 * http://www.eecs.yorku.ca/course_archive/2004-05/F/6431/ResearchPapers/Krinke.pdf
	 * l stores the height of the maxGraph lastMatched1, lastMatched2 store the
	 * nodes in method1pdg and method2 pdg respectively to store the nodes that
	 * last matched with each other. matchedPdgHeight stores the maximum height
	 * of method1Pdg and method2Pdg from lastMatched1 and lastMatched2
	 * respectively plus the height of maxGraph. lastMatched1, lastMatched2 and
	 * matchedPdgHeight are used to calculate max height of PDG's so that we can
	 * get an idea of how much more we needed to get if the function returns
	 * false
	 * 
	 * @param v1
	 * @param v2
	 * @param method1pdg
	 * @param method2pdg
	 * @param maxGraph
	 * @param l
	 * @param k
	 * @param lastMatched1
	 * @param lastMatched2
	 * @param matchedPdgHeight
	 * @return
	 */
	private boolean maximalPathSimilar(Node v1, Node v2, DirectedPseudograph<Node, DefaultEdge> method1pdg,
			DirectedPseudograph<Node, DefaultEdge> method2pdg, DirectedPseudograph<Node, DefaultEdge> maxGraph, int[] l,
			int k, Node[] lastMatched1, Node[] lastMatched2, int[] matchedPdgHeight) {
		boolean mapSuccess = false;
		Set<DefaultEdge> edges1Set = method1pdg.outgoingEdgesOf(v1);
		Set<DefaultEdge> edges2Set = method2pdg.outgoingEdgesOf(v2);

		if ((edges1Set.size() == 0 && edges2Set.size() == 0) || (l[0] >= k)) {
			mapSuccess = true;
		} else if (edges1Set.size() == 0 || edges2Set.size() == 0) {
			method1pdg.outgoingEdgesOf(lastMatched1[0]);
			int height1 = this.height(lastMatched1[0], method1pdg);
			int height2 = this.height(lastMatched2[0], method2pdg);
			matchedPdgHeight[0] += l[0] + Math.max(height1, height2);
			mapSuccess = false;
		} else {
			for (DefaultEdge edge1 : edges1Set) {
				for (DefaultEdge edge2 : edges2Set) {
					if (compareEdgeAttributes(method1pdg, method2pdg, edge1, edge2)) {
						maxGraph.addVertex(method1pdg.getEdgeTarget(edge1));
						if (!maxGraph.containsEdge(v1, method1pdg.getEdgeTarget(edge1))) {
							match++;
							maxGraph.addEdge(v1, method1pdg.getEdgeTarget(edge1));
							lastMatched1[0] = method1pdg.getEdgeTarget(edge1);
							lastMatched2[0] = method2pdg.getEdgeTarget(edge1);
							l[0]++;
							boolean success = maximalPathSimilar(method1pdg.getEdgeTarget(edge1),
									method2pdg.getEdgeTarget(edge2), method1pdg, method2pdg, maxGraph, l, k,
									lastMatched1, lastMatched2, matchedPdgHeight);
							mapSuccess = mapSuccess || success;
						}
					} else {
						mismatch++;
					}
				}
			}
			method1pdg.outgoingEdgesOf(lastMatched1[0]);
			method2pdg.outgoingEdgesOf(lastMatched2[0]);
			int height1 = this.height(lastMatched1[0], method1pdg);
			int height2 = this.height(lastMatched2[0], method2pdg);
			matchedPdgHeight[0] += l[0] + Math.max(height1, height2);
		}
		return mapSuccess;

	}

	/**
	 * Calculate the height of Node root in graph g
	 * 
	 * @param root
	 * @param g
	 * @return
	 */
	private int height(Node root, DirectedPseudograph<Node, DefaultEdge> g) {
		if (root == null) {
			return 0;
		}
		// System.out.println(root);
		Set<DefaultEdge> edgeSet = g.outgoingEdgesOf(root);
		if (edgeSet.size() == 0) {
			return 0;
		}
		int maxHeight = 0;
		for (DefaultEdge edge : edgeSet) {
			Node target = g.getEdgeTarget(edge);
			if (!(target.equals(root))) {
				int height = height(target, g);
				if (height > maxHeight) {
					maxHeight = height;
				}
			}
		}
		return 1 + maxHeight;
	}

	/**
	 * Use graph similarity algorithm to match pdgs of method1 and method2
	 * 
	 * @param method1
	 * @param method2
	 * @return
	 */
	public boolean matchMethodPDGs(Method method1, Method method2) {
		/*
		 * System.out.println("Checking match: "+method1.getMethodName()+
		 * " "+method2.getMethodName());
		 */
		if (!method1.getReturnType().equals(method2.getReturnType())) {
			return false;
		}
		if (method1.getMethodParameters().size() != method2.getMethodParameters().size()) {
			return false;
		}
		if (!checkParameters(method1.getMethodParameters(), method2.getMethodParameters())) {
			return false;
		}
		method1.initPDG();
		method2.initPDG();
		DirectedPseudograph<Node, DefaultEdge> method1pdg = method1.getPDG();
		DirectedPseudograph<Node, DefaultEdge> method2pdg = method2.getPDG();

		// Get the root nodes of the method pdg's
		Iterator<Node> iter1 = method1pdg.vertexSet().iterator();
		Iterator<Node> iter2 = method2pdg.vertexSet().iterator();
		Node v1 = iter1.next();
		Node v2 = iter2.next();
		if (!(v1 instanceof EntryStmt && v2 instanceof EntryStmt) && !v1.equals(v2)) {
			return false;
		}
		// Initialize maximal graphs
		DirectedPseudograph<Node, DefaultEdge> maxGraph = new DirectedPseudograph<>(DefaultEdge.class);
		maxGraph.addVertex(v1);
		// maximum size of maximal subgraph
		int k = 100;
		int[] l = { 0 };
		Node[] lastMatched1 = { v1 };
		Node[] lastMatched2 = { v2 };
		int[] matchedPdgHeight = { 0 };

		boolean matched = maximalPathSimilar(v1, v2, method1pdg, method2pdg, maxGraph, l, k, lastMatched1, lastMatched2,
				matchedPdgHeight);

		if (matched) {
			PDGGraphViz.writeDotNode(method1pdg, "pdg1.dot");
			PDGGraphViz.writeDotNode(method2pdg, "pdg2.dot");
			PDGGraphViz.writeDotNode(maxGraph, "max.dot");
		}
		return matched;
	}

	/*
	 * Checks if the abstract syntax tree of the body of method 1 is the same as
	 * that of method2
	 */
	public boolean matchMethodDeclaration(Method method1, Method method2) {
		if (method1.getBody().equals(method2.getBody())) {
			return true;
		}
		return false;
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

	public boolean checkParameters(List<Parameter> params1, List<Parameter> params2) {
		HashMap<Type, Integer> map1 = new HashMap<Type, Integer>();
		HashMap<Type, Integer> map2 = new HashMap<Type, Integer>();

		for (Parameter p1 : params1) {
			if (map1.containsKey(p1.getType())) {
				map1.put(p1.getType(), map1.get(p1.getType()) + 1);
			}
			map1.put(p1.getType(), 0);
		}

		for (Parameter p2 : params2) {
			if (map2.containsKey(p2.getType())) {
				map2.put(p2.getType(), map2.get(p2.getType()) + 1);
			}
			map2.put(p2.getType(), 0);
		}

		if (map1.size() != map2.size()) {
			return false;
		}

		for (Type key1 : map1.keySet()) {
			if (!map2.containsKey(key1)) {
				return false;
			}
			if (map1.get(key1) != map2.get(key1)) {
				return false;
			}
		}
		return true;
	}

	/*
	 * Check if distance between method1 NodeFeature and method2 NodeFeature is
	 * below the threshold
	 */
	public boolean matchMethodNodeFeatures(Method method1, Method method2, double threshold) {
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
		int[] featureArray1 = new int[feature1.getFeatureVectorSize()];
		int[] featureArray2 = new int[feature2.getFeatureVectorSize()];

		int count = 0;
		for (String key : featureMap1.keySet()) {
			featureArray1[count] = featureMap1.get(key);
			featureArray2[count] = featureMap2.get(key);
			count++;
		}

		double dist = calculateDistance(featureArray1, featureArray2);
		// System.out.println("dist "+dist);
		dist = match;
		if (dist <= threshold) {
			/*
			 * System.out.println("Considering "+method1.getMethodName()+" "
			 * +method2.getMethodName());
			 * System.out.println("Method1 parameters"); List<Parameter> params1
			 * = method1.getMethodParameters(); for(Parameter p: params1){
			 * System.out.println(p.getType()); }
			 * System.out.println("Method2 parameters"); List<Parameter> params2
			 * = method2.getMethodParameters(); for(Parameter p: params2){
			 * System.out.println(p.getType()); }
			 * System.out.println("Return "+method1+" "+method2.getReturnType())
			 * ; System.out.println("featureMap1 "+featureMap1);
			 * System.out.println("featureMap2 "+featureMap2);
			 * System.out.println("dist "+dist);
			 */
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
	 * ref : methodLibrary) { if (matchMethodPDGsDeckard(src, ref,4)) { Method[]
	 * matched = {src,ref}; matchedMethods.add(matched); }
	 * 
	 * } } return matchedMethods;
	 * 
	 * }
	 * 
	 * private NodeFeature getNodeFeature(DirectedPseudograph<Node, DefaultEdge>
	 * pdg,Node current,DirectedPseudograph<Node, DefaultEdge> visited) {
	 * NodeFeature nodeFeature = new NodeFeature(); if(!(current instanceof
	 * EntryStmt)){ nodeFeature.addNode(current); } Set<DefaultEdge> edges1Set =
	 * pdg.outgoingEdgesOf(current); if (edges1Set.size() == 0) { return
	 * nodeFeature; } for (DefaultEdge edge: edges1Set){ Node target =
	 * pdg.getEdgeTarget(edge); //System.out.println("target "+target);
	 * visited.addVertex(target); if(!visited.containsEdge(current,target)){
	 * visited.addEdge(current, target); NodeFeature childMethodFeature =
	 * getNodeFeature(pdg,target,visited);
	 * nodeFeature.combineNodeFeatures(childMethodFeature); } } return
	 * nodeFeature;
	 * 
	 * }
	 * 
	 * 
	 * public boolean matchMethodPDGsDeckard(Method method1, Method method2,
	 * double threshold){
	 * //System.out.println("Considering "+method1.getMethodName()+" "+method2.
	 * getMethodName()); DirectedPseudograph<Node, DefaultEdge> method1pdg =
	 * method1.getPDG(); DirectedPseudograph<Node, DefaultEdge> method2pdg =
	 * method2.getPDG(); DirectedPseudograph<Node, DefaultEdge> visited1 = new
	 * DirectedPseudograph<>(DefaultEdge.class); DirectedPseudograph<Node,
	 * DefaultEdge> visited2 = new DirectedPseudograph<>(DefaultEdge.class);
	 * 
	 * //Get the root nodes of the method pdg's Iterator<Node> iter1 =
	 * method1pdg.vertexSet().iterator(); Iterator<Node> iter2 =
	 * method2pdg.vertexSet().iterator();
	 * 
	 * Node v1 = iter1.next(); Node v2 = iter2.next();
	 * 
	 * visited1.addVertex(v1); visited2.addVertex(v2);
	 * 
	 * NodeFeature feature1 = getNodeFeature(method1pdg,v1,visited1);
	 * NodeFeature feature2 = getNodeFeature(method2pdg,v2,visited2);
	 * //System.out.println("feature1: "+feature1.getFeatureMap());
	 * //System.out.println("feature2: "+feature2.getFeatureMap());
	 * 
	 * feature1.makeComparableNodeFeatures(feature2); HashMap<Node,Integer>
	 * featureMap1 = feature1.getFeatureMap(); HashMap<Node,Integer> featureMap2
	 * = feature2.getFeatureMap();
	 * 
	 * int[] featureArray1 = new int[feature1.getFeatureVectorSize()]; int[]
	 * featureArray2 = new int[feature2.getFeatureVectorSize()]; int count = 0;
	 * for(Node key:featureMap1.keySet()){ featureArray1[count] =
	 * featureMap1.get(key); featureArray2[count] = featureMap2.get(key);
	 * count++; }
	 * 
	 * double dist = calculateDistance(featureArray1, featureArray2);
	 * //System.out.println("distance "+dist); if (dist <= threshold) { return
	 * true; } return false;
	 * 
	 * }
	 */

}
