package cfg;


/**
 * Represents a conditional basic block that contains exactly one boolean {@link Node}.
 *
 * @author Stefan Heule
 */
public interface CFGConditionalNode extends CFGNode {

    /** @return the entry block of the then branch */
    CFGNode getThenSuccessor();

    /** @return the entry block of the else branch */
    CFGNode getElseSuccessor();

/*    *//** @return the flow rule for information flowing from this block to its then successor *//*
    Store.FlowRule getThenFlowRule();

    *//** @return the flow rule for information flowing from this block to its else successor *//*
    Store.FlowRule getElseFlowRule();*/

/*    *//** Set the flow rule for information flowing from this block to its then successor. *//*
    void setThenFlowRule(Store.FlowRule rule);

    *//** Set the flow rule for information flowing from this block to its else successor. *//*
    void setElseFlowRule(Store.FlowRule rule);*/
}
