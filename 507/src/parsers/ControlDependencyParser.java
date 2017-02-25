package parsers;

import datastructures.NodeWrapper;
import jgrapht.experimental.dag.DirectedAcyclicGraph;
import jgrapht.graph.DefaultEdge;
import jgrapht.graph.EdgeReversedGraph;
import jgrapht.graph.SimpleDirectedGraph;

public class ControlDependencyParser {
	private DirectedAcyclicGraph<NodeWrapper, DefaultEdge> cdg;
	private DirectedAcyclicGraph<NodeWrapper, DefaultEdge> cfg;

	/**
	 * Build a new control dependency graph.
	 * 
	 * @param m
	 *            The source cfg that will be parsed.
	 * @return
	 * 
	 */
	public ControlDependencyParser(DirectedAcyclicGraph<NodeWrapper, DefaultEdge> cfg) {
		this.cfg = cfg;
		cdg = new DirectedAcyclicGraph<NodeWrapper, DefaultEdge>(DefaultEdge.class);
		parse(cfg);
	}

	/**
	 * @return the control flow graph associated with this node.
	 */
	public DirectedAcyclicGraph<NodeWrapper, DefaultEdge> getCDG() {
		return cdg;
	}

	private void parse(DirectedAcyclicGraph<NodeWrapper, DefaultEdge> cfg) {
		SimpleDirectedGraph<NodeWrapper, DefaultEdge> fdt = buildForwardDominanceTree(cfg);
		// buildControlDependenceGraph(cfg, fdt);
	}

	public SimpleDirectedGraph<NodeWrapper, DefaultEdge> buildForwardDominanceTree(
			DirectedAcyclicGraph<NodeWrapper, DefaultEdge> cfg2) {
		Dominators<NodeWrapper, DefaultEdge> test = new Dominators<>(cfg2, cfg2.iterator().next());
		return test.getDominatorTree();
		// return new EdgeReversedGraph<>(cfg);
	}

	private void buildControlDependenceGraph(DirectedAcyclicGraph<NodeWrapper, DefaultEdge> cfg,
			EdgeReversedGraph<NodeWrapper, DefaultEdge> fdt) {

		for (DefaultEdge flowEdge : cfg.edgeSet()) {
			NodeWrapper srcNode = cfg.getEdgeSource(flowEdge);
			if (fdt.outDegreeOf(srcNode) > 1) {

			}

		}
	}
}