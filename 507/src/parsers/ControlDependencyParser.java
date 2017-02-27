package parsers;

import java.util.Iterator;

import com.github.javaparser.ast.stmt.EmptyStmt;

import datastructures.DominatorTree;
import datastructures.NodeWrapper;
import jgrapht.DirectedGraph;
import jgrapht.experimental.dag.DirectedAcyclicGraph;
import jgrapht.graph.DefaultEdge;

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
	public ControlDependencyParser(DirectedGraph<NodeWrapper, DefaultEdge> cfg) {
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

	private void parse(DirectedGraph<NodeWrapper, DefaultEdge> cfg) {
		DirectedAcyclicGraph<NodeWrapper, DefaultEdge> fdt = buildForwardDominanceTree(cfg);
		buildControlDependenceGraph(cfg, fdt);

	}

	private DirectedAcyclicGraph<NodeWrapper, DefaultEdge> buildForwardDominanceTree(
			DirectedGraph<NodeWrapper, DefaultEdge> cfg) {
		DominatorTree<NodeWrapper, DefaultEdge> fdtBuilder = new DominatorTree<>(cfg,
				cfg.vertexSet().iterator().next());
		//DominatorTree2<NodeWrapper, DefaultEdge> fdtBuilder = new DominatorTree2<NodeWrapper, DefaultEdge>(cfg, cfg.vertexSet().iterator().next());
		//return fdtBuilder.getDominationTree();
		return fdtBuilder.getDominatorTree();
	}

	private void buildControlDependenceGraph(DirectedGraph<NodeWrapper, DefaultEdge> cfg,
			DirectedAcyclicGraph<NodeWrapper, DefaultEdge> fdt) {
		NodeWrapper entry = new NodeWrapper(new EmptyStmt());
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