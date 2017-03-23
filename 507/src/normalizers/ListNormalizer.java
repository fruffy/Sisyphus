package normalizers;

import java.util.List;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.MethodDeclaration;

/**
 * Lets us compose normalization tools, by taking a list of normalizers
 * and applying them in sequence
 *
 */
public class ListNormalizer extends Normalizer {

	private List<Normalizer> subNormalizers;

	public ListNormalizer(List<Normalizer> subNormalizers){
		this.subNormalizers = subNormalizers;
	}

	@Override
	public Node result() {
		Node current = this.startBlock;
		MethodDeclaration startDecl = (MethodDeclaration)startBlock;
		boolean nonEmptyStart = startDecl.getBody().isPresent() && !startDecl.getBody().get().getChildNodes().isEmpty();
		for (Normalizer norm : this.subNormalizers){
			norm.initialize(current);
			current = norm.result();
			
			MethodDeclaration currentMethod = (MethodDeclaration)current;
			if (currentMethod.getBody().isPresent()){
				if (currentMethod.getBody().get().getChildNodes().isEmpty() && nonEmptyStart){
					System.err.println("!!!!!!Empty body children");
				}
			}
			else{
				System.err.println("!!!!!!Method with no body");
			}
		}
		return current;
	}



}
