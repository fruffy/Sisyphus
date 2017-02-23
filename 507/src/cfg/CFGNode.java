package cfg;

import java.util.Set;

/**
 * Represents a basic block in a control flow graph.
 *
 * @author Stefan Heule
 */
public interface CFGNode {

    /** The types of basic blocks */
    public static enum BlockType {

        /** A regular basic block. */
        REGULAR_BLOCK,

        /** A conditional basic block. */
        CONDITIONAL_BLOCK,

        /** A special basic block. */
        SPECIAL_BLOCK,

        /** A basic block that can throw an exception. */
        EXCEPTION_BLOCK,
    }

    /** @return the type of this basic block */
    BlockType getType();

    /** @return the unique identifier of this block */
    long getId();
    
    /** We need the predecessors of nodes for data-flow analysis */
    Set<CFGNodeImpl> getPredecessors();
}
