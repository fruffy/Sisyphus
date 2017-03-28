package matching;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedPseudograph;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.type.Type;

import datastructures.EntryStmt;
import datastructures.Method;
import datastructures.PDGGraphViz;

public class KrinkeDetector extends CloneDetector {

	
	private double mismatch;
	private double match;

	public KrinkeDetector(List<Method> libMethods) {
		super(libMethods);
		// TODO Auto-generated constructor stub
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
			// method1pdg.outgoingEdgesOf(lastMatched1[0]);
			// int height1 = this.height(lastMatched1[0], method1pdg);
			// int height2 = this.height(lastMatched2[0], method2pdg);
			// matchedPdgHeight[0] += l[0] + Math.max(height1, height2);
			mapSuccess = false;
		} else {
			for (DefaultEdge edge1 : edges1Set) {
				for (DefaultEdge edge2 : edges2Set) {
					if (compareEdgeAttributes(method1pdg, method2pdg, edge1, edge2)) {
						maxGraph.addVertex(method1pdg.getEdgeTarget(edge1));
						if (!maxGraph.containsEdge(v1, method1pdg.getEdgeTarget(edge1))) {
							this.match++;
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
			// method1pdg.outgoingEdgesOf(lastMatched1[0]);
			// method2pdg.outgoingEdgesOf(lastMatched2[0]);
			// int height1 = this.height(lastMatched1[0], method1pdg);
			// int height2 = this.height(lastMatched2[0], method2pdg);
			// matchedPdgHeight[0] += l[0] + Math.max(height1, height2);
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
			if (!(target.equals(root)) && !(target.equals(g.getEdgeSource(edge)))) {
				int height = height(target, g);
				if (height > maxHeight) {
					maxHeight = height;
				}
			}
		}
		return 1 + maxHeight;
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
	private boolean compareEdgeAttributes(Graph<Node, DefaultEdge> method1pdg, Graph<Node, DefaultEdge> method2pdg,
			DefaultEdge edge1, DefaultEdge edge2) {
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
	 * Use graph similarity algorithm to match pdgs of method1 and method2
	 * 
	 * @param method1
	 * @param method2
	 * @return
	 */
	private boolean matchMethods(Method method1, Method method2) {
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
		if (!(iter1.hasNext() && iter2.hasNext())) {
			return false;
		}
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
	/**
	 * Takes a list of Method objects and matches it against a reference library
	 * Returns a list of matched methods. Compare PDGs
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
		double bestMatch = 0;
		for (Method src : srcMethods) {
			Method[] finalMatched = null;
			bestMatch = 0;
			for (Method ref : methodLibrary) {
				this.mismatch = 0;
				this.match = 0;
				if (matchMethods(src, ref)) {
					Method[] matched = { src, ref };
					if (bestMatch <= match / mismatch) {
						bestMatch = match / mismatch;
						finalMatched = matched;
					}
				}
				// matchedMethods.add(matched);
			}
			if (finalMatched != null) {
				matchedMethods.add(finalMatched);
				/*
				 * System.out.print("Match! " + finalMatched[0].getSignature() +
				 * " with return type " + finalMatched[0].getReturnType() +
				 * " can be replaced by " + finalMatched[1].getSignature() +
				 * " with return type " + finalMatched[1].getReturnType());
				 * System.out.printf(" Confidence %.2f%%\n", match / mismatch);
				 */
			}
		}
		return matchedMethods;
	}	
}
