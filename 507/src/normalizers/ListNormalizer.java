package normalizers;

import java.util.List;

import com.github.javaparser.ast.stmt.BlockStmt;

/**
 * Lets us compose normalization tools, by taking a list of normalizers
 * and applying them in sequence
 *
 */
public class ListNormalizer extends Normalizer {

	private List<Normalizer> subNormalizers;
	
	public ListNormalizer(BlockStmt startBlock, List<Normalizer> subNormalizers){
		this.subNormalizers = subNormalizers;
	}

	@Override
	public BlockStmt result() {
		BlockStmt current = this.startBlock;
		for (Normalizer norm : this.subNormalizers){
			norm.initialize(current);
			current = norm.result();
		}
		return current;
	}
	


}
