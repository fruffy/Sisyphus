/*
  This file is part of JOP, the Java Optimized Processor
    see <http://www.jopdesign.com/>
  Copyright (C) 2008, Benedikt Huber (benedikt.huber@gmail.com)
  This program is free software: you can redistribute it and/or modify
  it under the terms of the GNU General Public License as published by
  the Free Software Foundation, either version 3 of the License, or
  (at your option) any later version.
  This program is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU General Public License for more details.
  You should have received a copy of the GNU General Public License
  along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package datastructures;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jgrapht.DirectedGraph;
import org.jgrapht.experimental.dag.DirectedAcyclicGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.traverse.DepthFirstIterator;


/**
 * Compute Dominators of a graph, following: A Simple, Fast Dominance Algorithm
 * (Cooper, Keith D. and Harvey, Timothy J. and Kennedy, Ken).
 * http://dx.doi.org/10.1145/357062.357071
 */
public class DominatorTree<V, E> {

	private DirectedGraph<V, E> graph;
	private List<V> vertexPreOrder;
	private Map<V, V> idom = null;
	private Map<V, Integer> preOrderMap;

	protected int getOrder(V vertex) {
		return preOrderMap.get(vertex);
	}

	protected V getIDom(V vertex) {
		if (idom == null)
			computeDominators();
		return idom.get(vertex);
	}

	/**
	 * Dominators, using default pre-oder traversal
	 * 
	 * @param g
	 *            the graph
	 * @param entry
	 *            the entry node
	 */
	public DominatorTree(DirectedGraph<V, E> g, V entry) {
		this(g, dfsPreOrder(g, entry));
	}

	private static <V, E> List<V> dfsPreOrder(DirectedGraph<V, E> g, V exit) {
		DepthFirstIterator<V, E> iter = new DepthFirstIterator<V, E>(g, exit);
		iter.setCrossComponentTraversal(false);
		List<V> trav = new LinkedList<V>();
		while (iter.hasNext()) {
			trav.add(iter.next());
		}
		return trav;
	}

	/**
	 * (Immediate) Dominators based on the given pre-order traversal of the
	 * graph.
	 * 
	 * @param g
	 *            the graph
	 * @param preOrder
	 *            a pre-order DFS traversal of the graph. Its first node is the
	 *            entry point of the graph.
	 */
	public DominatorTree(DirectedGraph<V, E> g, List<V> preOrder) {
		this.graph = g;
		this.vertexPreOrder = preOrder;
		// just making sure we have a non-empty graph,
		assert vertexPreOrder != null && !vertexPreOrder.isEmpty();

		this.preOrderMap = new HashMap<V, Integer>();
		for (int i = 0; i < this.vertexPreOrder.size(); i++) {
			preOrderMap.put(vertexPreOrder.get(i), i);
		}
	}

	protected void computeDominators() {
		if (this.idom != null)
			return;
		this.idom = new HashMap<V, V>();
		V firstElement = vertexPreOrder.get(0);
		idom.put(firstElement, firstElement);
		/*if (!graph.incomingEdgesOf(vertexPreOrder.get(0)).isEmpty())
			throw new AssertionError("The entry of the flow graph is not allowed to have incoming edges");
*/
		boolean changed;
		do {
			changed = false;
			for (V v : vertexPreOrder) {
				if (v.equals(firstElement))
					continue;
				V oldIdom = getIDom(v);
				V newIdom = null;
				for (E edge : graph.incomingEdgesOf(v)) {
					V pre = graph.getEdgeSource(edge);
					if (getIDom(pre) == null)
						/* not yet analyzed */ continue;
					if (newIdom == null) {
						/*
						 * If we only have one (defined) predecessor pre,
						 * IDom(v) = pre
						 */
						newIdom = pre;
					} else {
						/*
						 * compute the intersection of all defined predecessors
						 * of v
						 */
						newIdom = intersectIDoms(pre, newIdom);
					}
				}
				if (newIdom == null)
					throw new AssertionError("newIDom == null !, for " + v);
				if (!newIdom.equals(oldIdom)) {
					changed = true;
					this.idom.put(v, newIdom);
				}
			}
		} while (changed);
	}

	private V intersectIDoms(V v1, V v2) {
		while (v1 != v2) {
			if (getOrder(v1) < getOrder(v2)) {
				v2 = getIDom(v2);
			} else {
				v1 = getIDom(v1);
			}
		}
		return v1;
	}
	
	public DirectedAcyclicGraph<V, DefaultEdge> getDominatorTree() {
		computeDominators();
		DirectedAcyclicGraph<V, DefaultEdge> domTree = new DirectedAcyclicGraph<V, DefaultEdge>(DefaultEdge.class);
		
		//List<V> reversedSet = new ArrayList<V>(graph.vertexSet());
		//Collections.reverse(reversedSet);

		for (V node : graph.vertexSet()) {
			domTree.addVertex(node);
			V idom = getIDom(node);
			if (idom != null && !node.equals(idom)) {
				domTree.addVertex(idom);
				domTree.addEdge(idom, node);
			}
		}
		return domTree;
	}
}