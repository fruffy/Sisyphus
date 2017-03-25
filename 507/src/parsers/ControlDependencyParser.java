package parsers;

import java.util.Iterator;

import datastructures.DominatorTree;
import datastructures.EntryStmt;
import datastructures.NodeWrapper;
import org.jgrapht.Graph;
import org.jgrapht.graph.DirectedAcyclicGraph;
import org.jgrapht.graph.DefaultEdge;

public class ControlDependencyParser {
	private DirectedAcyclicGraph<NodeWrapper, DefaultEdge> cdg;

	/**
	 * Build a new control dependency graph.
	 * 
	 * @param m
	 *            The source cfg that will be parsed.
	 * @return
	 * 
	 */
	public ControlDependencyParser(Graph<NodeWrapper, DefaultEdge> cfg) {
		// this.cfg = cfg;
			cdg = new DirectedAcyclicGraph<NodeWrapper, DefaultEdge>(DefaultEdge.class);
			parse(cfg);
	}

	/**
	 * @return the control flow graph associated with this node.
	 */
	public DirectedAcyclicGraph<NodeWrapper, DefaultEdge> getCDG() {
		return cdg;
	}

	public Graph<NodeWrapper, DefaultEdge> buildForwardDominanceTree(
			Graph<NodeWrapper, DefaultEdge> cfg) {
		if (cfg.vertexSet().size() == 0) {
			return cfg;
		}
		DominatorTree<NodeWrapper, DefaultEdge> fdtBuilder = new DominatorTree<>(cfg,
				cfg.vertexSet().iterator().next());
		return fdtBuilder.getDominatorTree();
	}

	private void parse(Graph<NodeWrapper, DefaultEdge> cfg) {
		Graph<NodeWrapper, DefaultEdge> fdt = buildForwardDominanceTree(cfg);
		buildControlDependenceGraph(cfg, fdt);
	}
//TODO: Fix potential bug in the generation of the control dependence parser
	private void buildControlDependenceGraph(Graph<NodeWrapper, DefaultEdge> cfg,
			Graph<NodeWrapper, DefaultEdge> fdt) {
		NodeWrapper entry = new NodeWrapper(new EntryStmt());
		NodeWrapper previousNode = entry;
		cdg.addVertex(entry);
		Iterator<NodeWrapper> vertexIterator = cfg.vertexSet().iterator();
		while (vertexIterator.hasNext()) {
			NodeWrapper n = vertexIterator.next();
			cdg.addVertex(n);

			if (fdt.outDegreeOf(n) == 0 && vertexIterator.hasNext()) {
				cdg.addEdge(previousNode, n);
			} else {
				cdg.addEdge(entry, n);
				previousNode = n;
			}

		}

	}
}