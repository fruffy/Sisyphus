package normalizers;

import java.util.LinkedList;

import com.github.javaparser.ast.Node;

import normalizers.Normalizer;

public class StandardForm {
	public static Node toStandardForm(Node node){
		LinkedList<Normalizer> norms = new LinkedList<Normalizer>();
		norms.addLast(new VariableNameNormalizer());
		norms.addLast(new LoopNormalizer());
		ListNormalizer norm = new ListNormalizer(norms);
		norm.initialize(node);
		return norm.result();
	}
}
