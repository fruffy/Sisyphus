package core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.stmt.EmptyStmt;

import jgrapht.experimental.dag.DirectedAcyclicGraph;
import jgrapht.graph.DefaultEdge;
import parsers.ControlFlowMethodParser;

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
				if (matchMethodNodeFeatures(src, ref,4)) {
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
	
	private boolean compareEdgeAttributes(DirectedAcyclicGraph<Node, DefaultEdge> g1, 
										DirectedAcyclicGraph<Node, DefaultEdge> g2,
										DefaultEdge edge1, DefaultEdge edge2){
		if(g1.getEdgeSource(edge1).equals(g2.getEdgeSource(edge2)) &&
		   g1.getEdgeTarget(edge1).equals(g2.getEdgeTarget(edge2))){
			return true;
		}
		return false;
	}
	
	/**
	 * Check if there exists a path of size k in g1 for which there is a
	 * similar path (with the same vertex and edge attributes) in g2
	 * Store the respective similar subgraphs in maximalg1 and maximalg2.
	 * Return true if we are able to find k-length similar paths.
	 * Return true if the length of similar paths is less than k 
	 * but equal the longest path of g1 and g2
	 * Return false otherwise
	 * Algorithm taken from the krinke paper 
	 * http://www.eecs.yorku.ca/course_archive/2004-05/F/6431/ResearchPapers/Krinke.pdf
	 * @param v1
	 * @param v2
	 * @param g1
	 * @param g2
	 * @param maximalg1
	 * @param maximalg2
	 * @param l
	 * @param k
	 * @return
	 */
	private boolean maximalPathSimilar(Node v1, Node v2,
						DirectedAcyclicGraph<Node, DefaultEdge> g1, 
						DirectedAcyclicGraph<Node, DefaultEdge> g2,
						DirectedAcyclicGraph<Node, DefaultEdge> maximalg1, 
						DirectedAcyclicGraph<Node, DefaultEdge> maximalg2,int l,int k){
		
		Set<DefaultEdge> edges1Set = g1.outgoingEdgesOf(v1);
		Set<DefaultEdge> edges2Set = g2.outgoingEdgesOf(v2);
		DefaultEdge[] edges1 = new DefaultEdge[edges1Set.size()];
		DefaultEdge[] edges2 = new DefaultEdge[edges2Set.size()];
		edges1Set.toArray(edges1);
		edges2Set.toArray(edges2);
		
		if((edges1.length == 0 && edges2.length == 0) || (l >=k)){
			return true;
		}
		else if(edges1.length == 0 || edges2.length == 0){
			return false;
		}
		boolean[] edges2Added = new boolean[edges2.length];
		boolean mapSuccess = false;
		for(int i = 0; i<edges1.length; i++){
			for(int j = 0; j<edges2.length; j++){
				if(compareEdgeAttributes(g1,g2,edges1[i],edges2[j]) &&
						!edges2Added[j]){
					edges2Added[j] = true;
					maximalg1.addVertex(g1.getEdgeTarget(edges1[i]),false);
					DefaultEdge newEdge1 = maximalg1.addEdge(v1, g1.getEdgeTarget(edges1[i]));
					maximalg2.addVertex(g2.getEdgeTarget(edges2[j]),false);
					DefaultEdge newEdge2 = maximalg2.addEdge(v2, g2.getEdgeTarget(edges2[j]));
					if(!(newEdge1 == null && newEdge2 == null)){
						mapSuccess = mapSuccess || maximalPathSimilar(g1.getEdgeTarget(edges1[i]),g2.getEdgeTarget(edges2[j]),
											g1,g2,maximalg1,maximalg2,l+1,k);
					}
				}
			}
		}
		return mapSuccess;
		
	}
	
	public boolean matchMethodPDGs(Method method1, Method method2){
		ControlFlowMethodParser cfgParse1 = new ControlFlowMethodParser(method1);
		ControlFlowMethodParser cfgParse2 = new ControlFlowMethodParser(method2);
		//Need to change it so that we get the method's pdg instead of cdg
		DirectedAcyclicGraph<Node, DefaultEdge> method1pdg = cfgParse1.getCFG();
		DirectedAcyclicGraph<Node, DefaultEdge> method2pdg = cfgParse2.getCFG();
		
		//Get the root nodes of the method pdg's
		Iterator<Node> iter1 = method1pdg.iterator();
		Iterator<Node> iter2 = method1pdg.iterator();
		Node v1 = iter1.next();
		Node v2 = iter2.next();
		
		//Initialize maximal graphs
		DirectedAcyclicGraph<Node, DefaultEdge> method1maxGraph = new DirectedAcyclicGraph<>(DefaultEdge.class);
		DirectedAcyclicGraph<Node, DefaultEdge> method2maxGraph = new DirectedAcyclicGraph<>(DefaultEdge.class);
        method1maxGraph.addVertex(v1);
        method2maxGraph.addVertex(v2);
        
        //maximum size of maximal subgraph
        int k = 20;
		boolean matched = maximalPathSimilar(v1, v2,method1pdg, method2pdg,method1maxGraph, method2maxGraph,1,k);
		System.out.println("\n+++++++++++++++++maximalSimilarGraph1++++++++++++++++++++++++++++++++");
		for (DefaultEdge e : method1maxGraph.edgeSet()) {
			System.out.println(method1maxGraph.getEdgeSource(e) + " --> " + method1maxGraph.getEdgeTarget(e));
		}
		// System.out.println("Control Flow Raw Content " + s);
		System.out.println("\n***************");
		System.out.println("\n+++++++++++++++++maximalSimilarGraph2++++++++++++++++++++++++++++++++");
		for (DefaultEdge e : method2maxGraph.edgeSet()) {
			System.out.println(method2maxGraph.getEdgeSource(e) + " --> " + method2maxGraph.getEdgeTarget(e));
		}
		// System.out.println("Control Flow Raw Content " + s);
		System.out.println("\n***************");
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
