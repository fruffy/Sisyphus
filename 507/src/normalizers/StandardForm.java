package normalizers;

import java.util.LinkedList;

import com.github.javaparser.ast.Node;

import normalizers.Normalizer;

public class StandardForm {
	public static Node toStandardForm(Node node){
		System.err.println("Changed body:\n" + node);
				
		LinkedList<Normalizer> norms = new LinkedList<Normalizer>();
		norms.addLast(new VariableNameNormalizer());
		norms.addLast(new LoopNormalizer());
		norms.addLast(new MergeBlocksNormalizer());
		norms.addLast(new VariableNameNormalizer());
		ListNormalizer norm = new ListNormalizer(norms);
		norm.initialize(node);
		Node ret = norm.result();
		System.err.println("to:\n" + ret);
		return ret;
	}
}
