package cfg;


/**
 * Implementation of a conditional basic block.
 *
 * @author Stefan Heule
 */
public class CFGConditionalNodeImpl extends CFGNodeImpl implements CFGConditionalNode {

    /** Successor of the then branch. */
    protected CFGNodeImpl thenSuccessor;

    /** Successor of the else branch. */
    protected CFGNodeImpl elseSuccessor;

    /**
     * The rules below say that the THEN store before a conditional block flows to BOTH of the
     * stores of the then successor, while the ELSE store before a conditional block flows to BOTH
     * of the stores of the else successor.
     */
/*    protected Store.FlowRule thenFlowRule = Store.FlowRule.THEN_TO_BOTH;

    protected Store.FlowRule elseFlowRule = Store.FlowRule.ELSE_TO_BOTH;*/

    /**
     * Initialize an empty conditional basic block to be filled with contents and linked to other
     * basic blocks later.
     */
    public CFGConditionalNodeImpl() {
        type = BlockType.CONDITIONAL_BLOCK;
    }

    /** Set the then branch successor. */
    public void setThenSuccessor(CFGNodeImpl b) {
        thenSuccessor = b;
        b.addPredecessor(this);
    }

    /** Set the else branch successor. */
    public void setElseSuccessor(CFGNodeImpl b) {
        elseSuccessor = b;
        b.addPredecessor(this);
    }

    @Override
    public CFGNode getThenSuccessor() {
        return thenSuccessor;
    }

    @Override
    public CFGNode getElseSuccessor() {
        return elseSuccessor;
    }

/*    @Override
    public Store.FlowRule getThenFlowRule() {
        return thenFlowRule;
    }

    @Override
    public Store.FlowRule getElseFlowRule() {
        return elseFlowRule;
    }

    @Override
    public void setThenFlowRule(Store.FlowRule rule) {
        thenFlowRule = rule;
    }

    @Override
    public void setElseFlowRule(Store.FlowRule rule) {
        elseFlowRule = rule;
    }
*/
    @Override
    public String toString() {
        return "ConditionalBlock()";
    }
}
