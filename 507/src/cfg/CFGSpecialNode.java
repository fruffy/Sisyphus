package cfg;

/**
 * Represents a special basic block; i.e., one of the following:
 *
 * <ul>
 *   <li>Entry block of a method.
 *   <li>Regular exit block of a method.
 *   <li>Exceptional exit block of a method.
 * </ul>
 *
 * @author Stefan Heule
 */
public interface CFGSpecialNode extends CFGSingleSuccessorNode {

    /** The types of special basic blocks */
    public static enum SpecialBlockType {

        /** The entry block of a method */
        ENTRY,

        /** The exit block of a method */
        EXIT,

        /** A special exit block of a method for exceptional termination */
        EXCEPTIONAL_EXIT,
    }

    /** @return the type of this special basic block */
    SpecialBlockType getSpecialType();
}
