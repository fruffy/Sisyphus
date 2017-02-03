package normalizers;

import java.util.List;

import com.github.javaparser.ast.Node;

/**
 * Lets us compose normalization tools, by taking a list of normalizers
 * and applying them in sequence
 *
 */
public class ListNormalizer extends Normalizer {

	private List<Normalizer> subNormalizers;

	public ListNormalizer(Node startBlock, List<Normalizer> subNormalizers){
		this.subNormalizers = subNormalizers;
	}

	@Override
	public Node result() {
		Node current = this.startBlock;
		for (Normalizer norm : this.subNormalizers){
			norm.initialize(current);
			current = norm.result();
		}
		return current;
	}



}
