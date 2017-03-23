package normalizers;

import java.util.LinkedList;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.MethodDeclaration;

import normalizers.Normalizer;

public class StandardForm {
	public static Node toStandardForm(Node node){
		//System.err.println("Changed body:\n" + node);
		
		MethodDeclaration startDecl = (MethodDeclaration)node;
		boolean nonEmptyStart = startDecl.getBody().isPresent() && !startDecl.getBody().get().getChildNodes().isEmpty();
		
				
		LinkedList<Normalizer> norms = new LinkedList<Normalizer>();
		norms.addLast(new VariableNameNormalizer());
		norms.addLast(new LoopNormalizer());
		norms.addLast(new MergeBlocksNormalizer());
		norms.addLast(new VariableNameNormalizer());
		ListNormalizer norm = new ListNormalizer(norms);
		norm.initialize(node);
		Node ret = norm.result();
		//System.err.println("to:\n" + ret);
		
		MethodDeclaration currentMethod = (MethodDeclaration)ret;
		if (currentMethod.getBody().isPresent()){
			if (currentMethod.getBody().get().getChildNodes().isEmpty() && nonEmptyStart){
				System.err.println("!!!!!!Empty body children");
			}
		}
		else{
			System.err.println("!!!!!!Method with no body");
		}
		
		return ret;
	}
}
