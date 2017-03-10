package core;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedAcyclicGraph;
import org.jgrapht.graph.DirectedPseudograph;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.comments.Comment;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.type.Type;

import datastructures.NodeWrapper;
import datastructures.PDGGraphViz;
import dfg.DataDependencyGraphFinder;
import normalizers.StandardForm;
import parsers.ControlDependencyParser;
import parsers.ControlFlowParser;
import parsers.MethodSolver;

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
		//System.out.println("BEFORE " +methodDeclaration);
		//methodDeclaration.accept(new TreeStructureVisitor(), 0);
		resolveMethodCalls(methodDeclaration);
		//System.out.println("AFTER " + methodDeclaration);
		//methodDeclaration.accept(new TreeStructureVisitor(), 0);
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
		//BlockStmt filteredBody = (BlockStmt) this.body.clone();
		for (Comment co : this.body.getAllContainedComments()) {
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
	public Method normalize() {
		return  new Method((MethodDeclaration)StandardForm.toStandardForm(this.originalDecl));
	}

	private void resolveMethodCalls(MethodDeclaration methodDecl) {
		new MethodSolver(methodDecl.getBody().get());
	}
	public DirectedGraph<NodeWrapper, DefaultEdge> getPDG(){
		ControlFlowParser cfp = new ControlFlowParser(this);
		DirectedPseudograph<NodeWrapper, DefaultEdge> cfg = cfp.getCFG();
		ControlDependencyParser cdp = new ControlDependencyParser(cfg);
		DirectedAcyclicGraph<NodeWrapper, DefaultEdge> cdg = cdp.getCDG();
		DataDependencyGraphFinder ddgf = new DataDependencyGraphFinder(cfg);
		DirectedPseudograph<NodeWrapper, DefaultEdge> ddg = ddgf.findReachingDefs();
		System.out.println("\n+++++++++++++++++cdg vertices++++++++++++++++++++++++++++++++");
		for (NodeWrapper n : cdg.vertexSet()) {
			System.out.println(n.NODE);
		}
		
		System.out.println("\n+++++++++++++++++ddg vertices++++++++++++++++++++++++++++++++");
		for (NodeWrapper n : ddg.vertexSet()) {
			System.out.println(n.NODE);
		}
		
		System.out.println("\n+++++++++++++++++cdg edges++++++++++++++++++++++++++++++++");
		for (DefaultEdge e : cdg.edgeSet()) {
			System.out.println(e);
		}
		
		System.out.println("\n+++++++++++++++++ddg edges++++++++++++++++++++++++++++++++");
		for (DefaultEdge e : ddg.edgeSet()) {
			System.out.println(e);
		}
		
		PDGGraphViz.writeDot(cdg, "cdg.dot");
		PDGGraphViz.writeDot(ddg, "ddg.dot");

		
		return cfg;
	
	}
}
