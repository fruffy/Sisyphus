package datastructures;

import com.github.javaparser.ast.Node;

public class NodeWrapper {
	public Node NODE;

	public NodeWrapper(Node n) {
		this.NODE = n;
	}

	public boolean equals(NodeWrapper n) {
		return this.NODE == n.NODE;
	}

}
