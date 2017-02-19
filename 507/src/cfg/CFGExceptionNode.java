package cfg;

import java.util.Map;
import java.util.Set;
import javax.lang.model.type.TypeMirror;

import com.github.javaparser.ast.Node;

/**
 * Represents a basic block that contains exactly one {@link Node} which can throw an exception.
 * This block has exactly one non-exceptional successor, and one or more exceptional successors.
 *
 * <p>The following invariant holds.
 *
 * <pre>
 * getNode().getBlock() == this
 * </pre>
 *
 * @author Stefan Heule
 */
public interface CFGExceptionNode extends CFGSingleSuccessorNode {

    /** @return the node of this block */
    Node getNode();

    /** @return the list of exceptional successor blocks as an unmodifiable map */
    Map<TypeMirror, Set<CFGNode>> getExceptionalSuccessors();
}
