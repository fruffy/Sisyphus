package core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.Parameter;

import datastructures.BackEdge;
import datastructures.EntryStmt;
import datastructures.NodeWrapper;
import datastructures.PDGGraphViz;
import jgrapht.DirectedGraph;
import jgrapht.experimental.dag.DirectedAcyclicGraph;
import jgrapht.graph.DefaultEdge;
import jgrapht.graph.DirectedPseudograph;
import parsers.ControlFlowParser;

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
	public List<Method[]> findSimiliarMethodsAST(List<Method> srcMethods) {

		List<Method[]> matchedMethods = new ArrayList<Method[]>();
		if (methodLibrary == null) {
			System.out.println("Warning: Method library not initialised, nothing to compare to!");
			return matchedMethods;
		}
		for (Method src : srcMethods) {
			for (Method ref : methodLibrary) {
				if (matchMethodNodeFeatures(src, ref,1)) {
					Method[] matched = {src,ref};
					/*System.out.println("Match! " + src.getMethodName() + " with return type " + src.getReturnType() + 
							" can be replaced by " + ref.getMethodName()+ " with return type " + ref.getReturnType());*/
					matchedMethods.add(matched);
				}

			}
		}
		return matchedMethods;

	}
	
	/**
	 * Takes a list of Method objects and matches it against a reference library
	 * Returns a list of matched methods
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
		for (Method src : srcMethods) {
			for (Method ref : methodLibrary) {
				if (matchMethodPDGs(src, ref)) {
					Method[] matched = {src,ref};
					/*System.out.println("Match! " + src.getMethodName() + " with return type " + src.getReturnType() + 
							" can be replaced by " + ref.getMethodName()+ " with return type " + ref.getReturnType());*/
					matchedMethods.add(matched);
				}

			}
		}
		return matchedMethods;

	}

	
	private boolean compareEdgeAttributes(DirectedGraph<Node, DefaultEdge> method1pdg, 
										DirectedGraph<Node, DefaultEdge> method2pdg,
										DefaultEdge edge1, DefaultEdge edge2){
		if(!(method1pdg.getEdgeSource(edge1) instanceof EntryStmt && method2pdg.getEdgeSource(edge2) instanceof EntryStmt)){
			if(!method1pdg.getEdgeSource(edge1).equals(method2pdg.getEdgeSource(edge2))){
				return false;
			}
		}
		if(!method1pdg.getEdgeTarget(edge1).equals(method2pdg.getEdgeTarget(edge2))){
			return false;
		}
		return true;
	}
	
	/**
	 * Check if there exists a path of size k in g1 for which there is a
	 * similar path (with the same vertex and edge attributes) in g2
	 * Store the respective similar subgraphs in maxGraph.
	 * Return true if we are able to find k-length similar paths.
	 * Return true if the length of similar paths is less than k 
	 * but equal the longest path of g1 and g2
	 * Return false otherwise
	 * Algorithm taken from the krinke paper 
	 * http://www.eecs.yorku.ca/course_archive/2004-05/F/6431/ResearchPapers/Krinke.pdf
	 * @param v1
	 * @param v2
	 * @param method1pdg
	 * @param method2pdg
	 * @param maxGraph
	 * @param l
	 * @param k
	 * @param lastMatched1, lastMatched2 and matchedPdgHeight are 
	 * helper variables to calculate height of maximal graph and pdg
	 * for comparison purposes
	 * @return
	 */
	private boolean maximalPathSimilar(Node v1, Node v2,
			DirectedPseudograph<Node, DefaultEdge> method1pdg, 
			DirectedPseudograph<Node, DefaultEdge> method2pdg,
			DirectedPseudograph<Node, DefaultEdge> maxGraph, 
			int[] l,int k, Node[] lastMatched1, Node[] lastMatched2,
			int[] matchedPdgHeight){
		boolean mapSuccess = false;
		Set<DefaultEdge> edges1Set = method1pdg.outgoingEdgesOf(v1);
		Set<DefaultEdge> edges2Set = method2pdg.outgoingEdgesOf(v2);
		
		if((edges1Set.size() == 0 && edges2Set.size() == 0) || (l[0] >=k)){
			mapSuccess = true;
		}
		else if(edges1Set.size() == 0 || edges2Set.size() == 0){
			method1pdg.outgoingEdgesOf(lastMatched1[0]);
			int height1 = this.height(lastMatched1[0],method1pdg);
			int height2 = this.height(lastMatched2[0],method2pdg);
			matchedPdgHeight[0]+= l[0] + Math.max(height1,height2);
			mapSuccess = false;
		}
		else{
			for(DefaultEdge edge1: edges1Set){
				for(DefaultEdge edge2: edges2Set){
					if(compareEdgeAttributes(method1pdg,method2pdg,edge1,edge2)){
						maxGraph.addVertex(method1pdg.getEdgeTarget(edge1));
						if(!maxGraph.containsEdge(v1,method1pdg.getEdgeTarget(edge1))){
							maxGraph.addEdge(v1, method1pdg.getEdgeTarget(edge1));
							lastMatched1[0] = method1pdg.getEdgeTarget(edge1);
							lastMatched2[0] = method2pdg.getEdgeTarget(edge1);
							l[0]++;
							boolean success =  maximalPathSimilar(method1pdg.getEdgeTarget(edge1),method2pdg.getEdgeTarget(edge2),
									method1pdg,method2pdg,maxGraph,l,k, lastMatched1,lastMatched2, matchedPdgHeight);
							mapSuccess = mapSuccess || success;
						}
					}
				}
			}
			method1pdg.outgoingEdgesOf(lastMatched1[0]);
			method2pdg.outgoingEdgesOf(lastMatched2[0]);
			int height1 = this.height(lastMatched1[0],method1pdg);
			int height2 = this.height(lastMatched2[0],method2pdg);
			matchedPdgHeight[0]+= l[0] + Math.max(height1,height2);
		}
		return mapSuccess;
		
	}
	
	/**
	 * Calculate the height
	 */
	private int height(Node root, DirectedPseudograph<Node, DefaultEdge> g){
		if(root==null){
			return 0;
		}
		//System.out.println(root);
		Set<DefaultEdge> edgeSet = g.outgoingEdgesOf(root);
		if(edgeSet.size() == 0){
			return 0;
		}
		int maxHeight = 0;
		for(DefaultEdge edge: edgeSet){
			Node target = g.getEdgeTarget(edge);
			if(!(target.equals(root))){
				int height = height(target,g);
				if(height > maxHeight){
					maxHeight = height;
				}
			}
		}
		return 1 + maxHeight;
	}
	public boolean matchMethodPDGs(Method method1, Method method2){
		/*System.out.println("Checking match: "+method1.getMethodName()+
							" "+method2.getMethodName());*/
		DirectedPseudograph<Node, DefaultEdge> method1pdg = method1.getPDG();
		DirectedPseudograph<Node, DefaultEdge> method2pdg = method2.getPDG();
		
		//Get the root nodes of the method pdg's
		Iterator<Node> iter1 = method1pdg.vertexSet().iterator();
		Iterator<Node> iter2 = method2pdg.vertexSet().iterator();
		Node v1 = iter1.next();
		Node v2 = iter2.next();
		if(!(v1 instanceof EntryStmt && v2 instanceof EntryStmt) && !v1.equals(v2)){
			return false;
		}
		//Initialize maximal graphs
		DirectedPseudograph<Node, DefaultEdge> maxGraph = new DirectedPseudograph<>(DefaultEdge.class);
        maxGraph.addVertex(v1);
        //maximum size of maximal subgraph
        int k = 100;
        int[] l = {0};
        Node[] lastMatched1 = {v1};
        Node[] lastMatched2 = {v2};
        int[] matchedPdgHeight = {0};
		boolean matched = maximalPathSimilar(v1, v2,method1pdg, 
				method2pdg,maxGraph, l,k,lastMatched1,lastMatched2,matchedPdgHeight);
		
	
		if(matched){
			/*System.out.println("Checking match: "+method1.getMethodName()+
					" "+method2.getMethodName());
			System.out.println("\n+++++++++++++++++method1pdg++++++++++++++++++++++++++++++++");
			for (DefaultEdge e : method1pdg.edgeSet()) {
				System.out.println(method1pdg.getEdgeSource(e) + " --> " + method1pdg.getEdgeTarget(e));
			}
			System.out.println("***************");
			
			System.out.println("+++++++++++++++++method2pdg++++++++++++++++++++++++++++++++");
			for (DefaultEdge e : method2pdg.edgeSet()) {
				System.out.println(method2pdg.getEdgeSource(e) + " --> " + method2pdg.getEdgeTarget(e));
			}
			System.out.println("***************");
			System.out.println("+++++++++++++++++maximalSimilarGraph++++++++++++++++++++++++++++++++");
			for (DefaultEdge e : maxGraph.edgeSet()) {
				System.out.println(maxGraph.getEdgeSource(e) + " --> " + maxGraph.getEdgeTarget(e));
			}
			System.out.println("lastMatched "+ lastMatched1[0]);
			System.out.println("maxGraphHeight " + l[0]);
			System.out.println("maxPDGHeight "+ matchedPdgHeight[0]);
			System.out.println("ifMatched "+matched);
			System.out.println("\n***************");*/
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

}
