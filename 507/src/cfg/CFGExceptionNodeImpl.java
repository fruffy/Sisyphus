package cfg;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.lang.model.type.TypeMirror;
import com.github.javaparser.ast.Node;

/**
 * Base class of the {@link CFGNode} implementation hierarchy.
 *
 * @author Stefan Heule
 */
public class CFGExceptionNodeImpl extends CFGSingleSuccessorNodeImpl implements CFGExceptionNode {

	/** Set of exceptional successors. */
	protected Map<TypeMirror, Set<CFGNode>> exceptionalSuccessors;

	public CFGExceptionNodeImpl() {
		type = BlockType.EXCEPTION_BLOCK;
		exceptionalSuccessors = new HashMap<>();
	}

	/** The node of this block. */
	protected Node node;

	/** Set the node. */
	/*
	 * public void setNode(Node c) { node = c; c.setBlock(this); }
	 */

	@Override
	public Node getNode() {
		return node;
	}

	/** Add an exceptional successor. */
	public void addExceptionalSuccessor(CFGNodeImpl b, TypeMirror cause) {
		if (exceptionalSuccessors == null) {
			exceptionalSuccessors = new HashMap<>();
		}
		Set<CFGNode> blocks = exceptionalSuccessors.get(cause);
		if (blocks == null) {
			blocks = new HashSet<CFGNode>();
			exceptionalSuccessors.put(cause, blocks);
		}
		blocks.add(b);
		b.addPredecessor(this);
	}

	@Override
	public Map<TypeMirror, Set<CFGNode>> getExceptionalSuccessors() {
		if (exceptionalSuccessors == null) {
			return Collections.emptyMap();
		}
		return Collections.unmodifiableMap(exceptionalSuccessors);
	}

	@Override
	public String toString() {
		return "ExceptionBlock(" + node + ")";
	}
}
