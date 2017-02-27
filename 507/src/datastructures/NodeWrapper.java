package datastructures;

import com.github.javaparser.ast.Node;

public class NodeWrapper {
	public Node NODE;

	public NodeWrapper(Node n) {
		this.NODE = n;
	}
	
	@Override
	public boolean equals(Object n) {
		if (n instanceof NodeWrapper) {
			return this.NODE == ((NodeWrapper) n).NODE;
		} else if (n instanceof Node) {
			return this.NODE == (Node) n;
		} else {
			return false;
		}
	}
	@Override
    public int hashCode() {
		return this.NODE.hashCode();
	}

}
