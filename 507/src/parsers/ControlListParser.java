package parsers;

import java.util.LinkedList;
import java.util.List;

import com.github.javaparser.ast.Node;

import core.Method;
import datastructures.NodeWrapper;
import jgrapht.DirectedGraph;
import jgrapht.experimental.dag.DirectedAcyclicGraph;
import jgrapht.graph.DefaultEdge;
import jgrapht.graph.EdgeReversedGraph;
import jgrapht.graph.SimpleDirectedGraph;

public class ControlListParser {
	List<DirectedAcyclicGraph<NodeWrapper, DefaultEdge>> cfgList;
	List<SimpleDirectedGraph<NodeWrapper, DefaultEdge>> cdgList;

	public ControlListParser(List<Method> methList) {
		cfgList = new LinkedList<DirectedAcyclicGraph<NodeWrapper, DefaultEdge>>();
		cdgList = new LinkedList<SimpleDirectedGraph<NodeWrapper, DefaultEdge>>();

		buildCFG(methList);
	}

	private void buildCFG(List<Method> methList) {

		for (Method m : methList) {

			ControlFlowParser cfgParse = new ControlFlowParser(m);

			DirectedAcyclicGraph<NodeWrapper, DefaultEdge> cfg = cfgParse.getCFG();
			ControlDependencyParser cdgParse = new ControlDependencyParser(cfg);

			SimpleDirectedGraph<NodeWrapper, DefaultEdge> cdg = cdgParse.buildForwardDominanceTree(cfg);
			this.cfgList.add(cfg);
			this.cdgList.add(cdg);
			
			// Debug
			System.out.println("\n++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			System.out.println(m.getFilteredBody().toString());
			System.out.println("Control Flow Raw Content ");
			printGraph(m, cfg);
			System.out.println("Control Dependence Raw Content ");
			printGraph(m, cdg);

		}
	}

	private void printGraph(Method m, SimpleDirectedGraph<NodeWrapper, DefaultEdge> cdg) {


		for (DefaultEdge e : cdg.edgeSet()) {
			System.out.println(cdg.getEdgeSource(e).NODE + " --> " + cdg.getEdgeTarget(e).NODE);
		}
		System.out.println("\n***************");
	}

	/**
	 * @return the cfgList
	 */
	public List<DirectedAcyclicGraph<NodeWrapper, DefaultEdge>> getCfgList() {
		return cfgList;
	}
}