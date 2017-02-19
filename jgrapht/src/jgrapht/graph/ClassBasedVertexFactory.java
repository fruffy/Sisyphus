/*
 * (C) Copyright 2007-2017, by France Telecom and Contributors.
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
package jgrapht.graph;

import jgrapht.*;

/**
 * A {@link VertexFactory} for producing vertices by using a class as a factory.
 *
 * @param <V> the graph vertex type
 *
 * @author Guillaume Boulmier
 * @since July 5, 2007
 */
public class ClassBasedVertexFactory<V>
    implements VertexFactory<V>
{
    private final Class<? extends V> vertexClass;

    /**
     * Create a new class based vertex factory.
     * 
     * @param vertexClass the vertex class
     */
    public ClassBasedVertexFactory(Class<? extends V> vertexClass)
    {
        this.vertexClass = vertexClass;
    }

    /**
     * @see VertexFactory#createVertex()
     */
    @Override
    public V createVertex()
    {
        try {
            return this.vertexClass.newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Vertex factory failed", e);
        }
    }
}

// End ClassBasedVertexFactory.java
