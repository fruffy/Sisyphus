package parsers;

import java.util.LinkedList;
import java.util.List;

import core.Method;
import datastructures.NodeWrapper;
import jgrapht.DirectedGraph;
import jgrapht.experimental.dag.DirectedAcyclicGraph;
import jgrapht.graph.DefaultEdge;
import jgrapht.graph.DirectedPseudograph;

public class ControlListParser {
	List<DirectedPseudograph<NodeWrapper, DefaultEdge>> cfgList;
	List<DirectedAcyclicGraph<NodeWrapper, DefaultEdge>> cdgList;
	List<DirectedAcyclicGraph<NodeWrapper, DefaultEdge>> fdtList;

	public ControlListParser(List<Method> methList) {
		cfgList = new LinkedList<DirectedPseudograph<NodeWrapper, DefaultEdge>>();
		cdgList = new LinkedList<DirectedAcyclicGraph<NodeWrapper, DefaultEdge>>();
		fdtList = new LinkedList<DirectedAcyclicGraph<NodeWrapper, DefaultEdge>>();

		buildCFG(methList);
	}

	private void buildCFG(List<Method> methList) {

		for (Method m : methList) {

			ControlFlowParser cfgParse = new ControlFlowParser(m);
			DirectedPseudograph<NodeWrapper, DefaultEdge> cfg = cfgParse.getCFG();
			ControlDependencyParser cdgParse = new ControlDependencyParser(cfg);
			DirectedAcyclicGraph<NodeWrapper, DefaultEdge> cdg = cdgParse.getCDG();
			DirectedAcyclicGraph<NodeWrapper, DefaultEdge> fdt = cdgParse.buildForwardDominanceTree(cfg);

			this.cfgList.add(cfg);
			this.cdgList.add(cdg);
			this.cdgList.add(fdt);
			// Debug
			System.out.println("\n++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			System.out.println(m.getFilteredBody().toString());
			System.out.println("Control Flow Raw Content ");
			printGraph(m, cfg);
			
			//System.out.println("Forward Dominator Raw Content ");
			//printGraph(m, fdt);
			
			System.out.println("Control Dependence Raw Content ");
			printGraph(m, cdg);

		}
	}

	private void printGraph(Method m, DirectedGraph<NodeWrapper, DefaultEdge> cdg) {

		/*
		 * for (DefaultEdge e : cdg.edgeSet()) {
		 * System.out.println(cdg.getEdgeSource(e).NODE + " --> " +
		 * cdg.getEdgeTarget(e).NODE); }
		 */
		for (NodeWrapper n : cdg.vertexSet()) {
			System.out.println(n.NODE);
			for (DefaultEdge e : cdg.outgoingEdgesOf(n)) {
				System.out.println("\t --> " + cdg.getEdgeTarget(e).NODE);
			}
		}
		System.out.println("\n***************");

	}

	/**
	 * @return the cfgList
	 */
	public List<DirectedPseudograph<NodeWrapper, DefaultEdge>> getCfgList() {
		return cfgList;
	}
}