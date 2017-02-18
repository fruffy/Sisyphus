package cfg;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Base class of the {@link CFGNode} implementation hierarchy.
 *
 * @author Stefan Heule
 */
public abstract class CFGNodeImpl implements CFGNode {

    /** A unique ID for this node. */
    protected long id = CFGNodeImpl.uniqueID();

    /** The last ID that has already been used. */
    protected static long lastId = 0;

    /** The type of this basic block. */
    protected BlockType type;

    /** The set of predecessors. */
    protected Set<CFGNodeImpl> predecessors;

    /** @return a fresh identifier */
    private static long uniqueID() {
        return lastId++;
    }

    public CFGNodeImpl() {
        predecessors = new HashSet<>();
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public BlockType getType() {
        return type;
    }

    /** @return the list of predecessors of this basic block */
    public Set<CFGNodeImpl> getPredecessors() {
        return Collections.unmodifiableSet(predecessors);
    }

    public void addPredecessor(CFGNodeImpl pred) {
        predecessors.add(pred);
    }

    public void removePredecessor(CFGNodeImpl pred) {
        predecessors.remove(pred);
    }
}
