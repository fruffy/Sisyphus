package normalizers;
import com.github.javaparser.ast.stmt.BlockStmt;

/**
 * A Normalizer performs a transformation on BlockStmts (more) normalized form, so that
 * they can be compared to each other and detect near clones.
 * A Normalizer may perform a single transformation, or it may perform several.
 *
 */
public abstract class Normalizer {
	protected BlockStmt startBlock;

	public void initialize(BlockStmt startBlock){
		this.startBlock = startBlock;
	}
	
	public abstract BlockStmt result();

}
