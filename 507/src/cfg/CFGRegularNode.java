package cfg;

import java.util.List;

import com.github.javaparser.ast.Node;

/**
 * A regular basic block that contains a sequence of {@link Node}s.
 *
 * <p>The following invariant holds.
 *
 * <pre>
 * forall n in getContents() :: n.getBlock() == this
 * </pre>
 *
 * @author Stefan Heule
 */
public interface CFGRegularNode extends CFGSingleSuccessorNode {

    /** @return the unmodifiable sequence of {@link Node}s. */
    List<Node> getContents();

    /** @return the regular successor block */
    CFGNode getRegularSuccessor();

    /** Is this block empty (i.e., does it not contain any contents). */
    boolean isEmpty();
}
