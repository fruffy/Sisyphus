package cfg;

public class CFGSpecialNodeImpl extends CFGSingleSuccessorNodeImpl implements CFGSpecialNode {

    /** The type of this special basic block. */
    protected SpecialBlockType specialType;

    public CFGSpecialNodeImpl(SpecialBlockType type) {
        this.specialType = type;
        this.type = BlockType.SPECIAL_BLOCK;
    }

    @Override
    public SpecialBlockType getSpecialType() {
        return specialType;
    }

    @Override
    public String toString() {
        return "SpecialBlock(" + specialType + ")";
    }
}
