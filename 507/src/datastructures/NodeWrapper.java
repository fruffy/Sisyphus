package datastructures;

import com.github.javaparser.ast.Node;

public class NodeWrapper implements Comparable {
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

	@Override
	public int compareTo(Object arg0) {
		return ((Integer)this.hashCode()).compareTo(arg0.hashCode()); 
	}

}
