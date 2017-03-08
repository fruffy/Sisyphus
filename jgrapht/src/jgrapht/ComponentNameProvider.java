package jgrapht;

/**
 * Provides a name for a component.
 *
 * @param <T> the type of the component
 */
public interface ComponentNameProvider<T>
{

    /**
     * Returns a unique name. This is useful when exporting a graph, as it ensures that all
     * vertices/edges are assigned simple, consistent names.
     *
     * @param component the component to be named
     * @return the name of the component
     */
    String getName(T component);

}

// End ComponentNameProvider.java
