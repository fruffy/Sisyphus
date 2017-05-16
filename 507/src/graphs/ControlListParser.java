package graphs;

import java.util.LinkedList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedAcyclicGraph;
import org.jgrapht.graph.DirectedPseudograph;

import datastructures.Method;
import datastructures.NodeWrapper;

public class ControlListParser {
	List<DirectedPseudograph<NodeWrapper, DefaultEdge>> cfgList;
	List<DirectedAcyclicGraph<NodeWrapper, DefaultEdge>> cdgList;
	List<Graph<NodeWrapper, DefaultEdge>> fdtList;

	public ControlListParser(List<Method> methList) {
		cfgList = new LinkedList<DirectedPseudograph<NodeWrapper, DefaultEdge>>();
		cdgList = new LinkedList<DirectedAcyclicGraph<NodeWrapper, DefaultEdge>>();
		fdtList = new LinkedList<Graph<NodeWrapper, DefaultEdge>>();
		buildCFG(methList);
	}

	private void buildCFG(List<Method> methList) {

		for (Method m : methList) {
			ControlFlowBuilder cfgParse = new ControlFlowBuilder(m);
			DirectedPseudograph<NodeWrapper, DefaultEdge> cfg = cfgParse.getCFG();
			ControlDependenceBuilder cdgParse = new ControlDependenceBuilder(cfg);
			DirectedAcyclicGraph<NodeWrapper, DefaultEdge> cdg = cdgParse.getCDG();
			Graph<NodeWrapper, DefaultEdge> fdt = cdgParse.buildForwardDominanceTree(cfg);

			this.cfgList.add(cfg);
			this.cdgList.add(cdg);
			this.fdtList.add(fdt);
			// Debug

			System.out.println("\n++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			System.out.println(m.getBody().toString());
			System.out.println("Control Flow Raw Content ");
			printGraph(cfg);
			System.out.println("Forward Dominator Raw Content ");
			printGraph(fdt);
			System.out.println("Control Dependence Raw Content ");
			printGraph(cdg);
		}
	}

	private void printGraph(Graph<NodeWrapper, DefaultEdge> graph) {

		for (NodeWrapper n : graph.vertexSet()) {
			System.out.println(n.NODE);
			for (DefaultEdge e : graph.outgoingEdgesOf(n)) {
				System.out.println("\t --> " + graph.getEdgeTarget(e).NODE);
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