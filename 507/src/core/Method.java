package core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.comments.Comment;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.type.Type;

import normalizers.Normalizer;

/*
 * Method class that holds information about a particular method
 * - the name of the method, the return type of the method.
 */

public class Method {
	private String methodName;
	private Type returnType;
	private List<Parameter> parameters;
	private BlockStmt body;
	private MethodDeclaration originalDecl;

	public Method(MethodDeclaration methodDeclaration) {
		this.originalDecl = methodDeclaration;
		this.methodName = methodDeclaration.getNameAsString();
		this.parameters = methodDeclaration.getParameters();
		this.returnType = methodDeclaration.getType();
		this.body = methodDeclaration.getBody().get();
		this.trimBody();
	}

	public String getMethodName() {
		return this.methodName;
	}

	public Type getReturnType() {
		return this.returnType;
	}

	public List<Parameter> getMethodParameters() {
		return this.parameters;
	}

	public BlockStmt getBody() {
		return this.body;
	}

	public BlockStmt getFilteredBody() {
		BlockStmt filteredBody = (BlockStmt) this.body.clone();
		for (Comment co : filteredBody.getAllContainedComments()) {
			co.remove();
		}

		return filteredBody;
	}

	public void trimBody() {
		BlockStmt filteredBody = (BlockStmt) this.body.clone();
		for (Comment co : filteredBody.getAllContainedComments()) {
			co.remove();
		}
	}

	public MethodDeclaration getFilteredMethod() {
		MethodDeclaration methodDeclaration = this.originalDecl;
		for (Comment co : methodDeclaration.getAllContainedComments()) {
			co.remove();
		}

		return methodDeclaration;
	}

	/**
	 * Attempt to build a visual representation of the function tree Intended as
	 * basis of understanding of requirements for the control graph tree
	 */
	public void printVisualRepresentation() {
		System.out.println(originalDecl.getNameAsString());
		List<Node> children = originalDecl.getChildNodes();
		recVisualRepresentation(children);

	}

	/**
	 * Recursive builder for visual representation
	 * 
	 * @param children
	 */
	private void recVisualRepresentation(List<Node> children) {
		if (children == null) {
			return;
		}
		for (int i = 0; i < children.size(); i++) {
			System.out.print(children.get(i).toString() + " ");
			if (children.get(i).getChildNodes() != null) {
				recVisualRepresentation(children.get(i).getChildNodes());
			}
		}
		System.out.println();
	}

	/*
	 * Do a traversal of the nodes of the method body without comments and
	 * return the list
	 */
	public List<Node> getMethodNodes() {
		List<Node> methodNodes = new ArrayList<Node>();
		List<Node> queueNodes = new ArrayList<Node>();
		queueNodes.add(this.getFilteredMethod());
		while (!queueNodes.isEmpty()) {
			Node current = queueNodes.remove(0);
			// System.out.println("current: "+current+", class:
			// "+current.getClass());
			if (!(current instanceof Comment)) {
				methodNodes.add(current);
			}
			List<Node> currentChildren = current.getChildNodes();
			for (Node child : currentChildren) {
				queueNodes.add(child);
			}
		}
		return methodNodes;

	}

	/*
	 * Combine the NodeFeatures of all Nodes into one NodeFeature at the root
	 * This will be the characteristic feature of the whole method (from the
	 * Deckard paper)
	 */
	private NodeFeature getMethodFeature(Node current) {
		NodeFeature nodeFeature = new NodeFeature();
		// If a node is of Primitive type then we want to store its
		// value(whether it
		// is an int or double) rather than the fact that it is a Primitive type
		// because that information is more useful.
		// Do the same for MethodCallExpression.
		if (current.getClass().toString().equals("class com.github.javaparser.ast.type.PrimitiveType")
				|| current.getClass().toString().equals("class com.github.javaparser.ast.expr.MethodCallExpr")) {
			nodeFeature.addClasses(current.toString());
		} else {
			nodeFeature.addClasses(current.getClass().toString());
		}
		if (current.getChildNodes().size() == 0) {
			return nodeFeature;
		}
		List<Node> currentChildren = current.getChildNodes();
		for (Node child : currentChildren) {
			NodeFeature childMethodFeature = getMethodFeature(child);
			nodeFeature.combineNodeFeatures(childMethodFeature);
		}
		return nodeFeature;

	}

	public NodeFeature getMethodFeature() {
		List<Node> methodNodes = getMethodNodes();
		NodeFeature methodFeature = getMethodFeature(methodNodes.get(0));
		return methodFeature;
	}

	/**
	 * Return a new method that is equivalent to this method, but normalized by
	 * the given normalizer
	 */
	public Method normalize(Normalizer norm) {
		norm.initialize(this.originalDecl);
		return new Method((MethodDeclaration) norm.result());
	}
}
