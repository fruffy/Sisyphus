import com.github.javaparser.ast.stmt.BlockStmt;

/**
 * A Normalizer transforms BlockStmts into a normalized form, so that
 * they can be compared to each other and detect near clones.
 *
 */
public class Normalizer {
	protected final BlockStmt startBlock;

	public Normalizer(BlockStmt startBlock){
		this.startBlock = startBlock;
	}

}
