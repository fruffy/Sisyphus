package cfg;

/*>>>
import org.checkerframework.checker.nullness.qual.Nullable;
*/


/**
 * Implementation of a non-special basic block.
 *
 * @author Stefan Heule
 */
public abstract class CFGSingleSuccessorNodeImpl extends CFGNodeImpl implements CFGSingleSuccessorNode {

    /** Internal representation of the successor. */
    protected /*@Nullable*/ CFGNodeImpl successor;

    /**
     * The rule below say that EACH store at the end of a single successor block flow to the
     * corresponding store of the successor.
     */
   /* protected Store.FlowRule flowRule = Store.FlowRule.EACH_TO_EACH;*/

    @Override
    public /*@Nullable*/ CFGNode getSuccessor() {
        return successor;
    }

    /** Set a basic block as the successor of this block. */
    public void setSuccessor(CFGNodeImpl successor) {
        this.successor = successor;
        successor.addPredecessor(this);
    }

/*    @Override
    public Store.FlowRule getFlowRule() {
        return flowRule;
    }

    @Override
    public void setFlowRule(Store.FlowRule rule) {
        flowRule = rule;
    }*/
}
