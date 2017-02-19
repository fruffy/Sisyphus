/*
 * (C) Copyright 2003-2017, by Barak Naveh and Contributors.
 *
 * JGraphT : a free Java graph-theory library
 *
 * This program and the accompanying materials are dual-licensed under
 * either
 *
 * (a) the terms of the GNU Lesser General Public License version 2.1
 * as published by the Free Software Foundation, or (at your option) any
 * later version.
 *
 * or (per the licensee's choosing)
 *
 * (b) the terms of the Eclipse Public License v1.0 as published by
 * the Eclipse Foundation.
 */
package jgrapht;

/**
 * An interface for a graph whose edges have non-uniform weights.
 *
 * @param <V> the graph vertex type
 * @param <E> the graph edge type
 *
 * @author Barak Naveh
 * @since Jul 23, 2003
 */
public interface WeightedGraph<V, E>
    extends Graph<V, E>
{
    /**
     * The default weight for an edge.
     */
    double DEFAULT_EDGE_WEIGHT = 1.0;

    /**
     * Assigns a weight to an edge.
     *
     * @param e edge on which to set weight
     * @param weight new weight for edge
     */
    void setEdgeWeight(E e, double weight);
}

// End WeightedGraph.java
