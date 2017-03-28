package datastructures;

import java.util.List;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedAcyclicGraph;
import org.jgrapht.graph.DirectedPseudograph;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.comments.Comment;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.type.Type;

import graphs.ControlDependenceBuilder;
import graphs.ControlFlowBuilder;
import graphs.DataDependencyGraphBuilder;
import normalizers.StandardForm;
import solvers.MethodSolver;
import visitors.ASTUtil;

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
	private DirectedAcyclicGraph<NodeWrapper, DefaultEdge> cdg;
	private DirectedPseudograph<NodeWrapper, DefaultEdge> ddg;
	private DirectedPseudograph<Node, DefaultEdge> pdg;

	public Method(MethodDeclaration methodDeclaration) {
		this.originalDecl = methodDeclaration.clone();
		this.methodName = methodDeclaration.getNameAsString();
		this.parameters = methodDeclaration.getParameters();
		this.returnType = methodDeclaration.getType();
		if (!(methodDeclaration.getBody().isPresent())) {
			System.err.println("WARNING: Empty Method");
			return;
		}
		this.body = methodDeclaration.getBody().get();
		System.out.println(this.methodName);
		System.out.println("BEFORE: ***********************************************\n" + this.body);
		this.trimBody();
	
		methodDeclaration = normalize(methodDeclaration);
		resolveMethodCalls(methodDeclaration, 3);

		this.body = methodDeclaration.getBody().get();
		System.out.println("AFTER: ++++++++++++++++++++++++++++++++++++++++++++++++\n" + this.body);

	}
	
	public DirectedAcyclicGraph<NodeWrapper, DefaultEdge> getCdg(){
		return cdg;
	}
	public DirectedPseudograph<NodeWrapper, DefaultEdge> getDdg(){
		return ddg;
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
	public String getSignature(){
		return this.originalDecl.getSignature();
	}


	public void trimBody() {
		for (Comment co :  this.body.getAllContainedComments()) {
			co.remove();
		}
	}



	/*
	 * Combine the NodeFeatures of all Nodes into one NodeFeature at the root
	 * This will be the characteristic feature of the whole method (from the
	 * Deckard paper)
	 */
	private NodeFeature getMethodFeature(Node current) {
		NodeFeature nodeFeature = new NodeFeature();
		if(current.getClass().toString().equals("com.github.javaparser.ast.expr.MethodCallExpr")){
			nodeFeature.addNode(current.toString());
		}
		else{
			nodeFeature.addNode(current.getClass().toString());
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
		//System.out.println("In getMethodFeature() " +this.getMethodName());
		BlockStmt root = this.body;
		//System.out.println(root);
		NodeFeature methodFeature = getMethodFeature(root);
		return methodFeature;
	}

	/**
	 * Return a new method that is equivalent to this method, but normalized by
	 * the given normalizer
	 */
	public MethodDeclaration normalize(MethodDeclaration methodDecl) {
		return (MethodDeclaration)StandardForm.toStandardForm(methodDecl);
		
	}
	
	public boolean isRecursive(){
		return containsCallTo(this.methodName);
	}
	
	public boolean containsCallTo(String function){
		return ASTUtil.occursFree(this.body, function);
	}
	
	public void resolveMethodCalls(MethodDeclaration methodDecl, int maxDepth) {
		new MethodSolver(methodDecl.getBody().get(), maxDepth);
	}

	
	public DirectedPseudograph<Node, DefaultEdge> constructPDG(Method m){
		//System.out.println("Building pdg for method: "+this.getMethodName());
		ControlFlowBuilder cfp = new ControlFlowBuilder(m);
		DirectedPseudograph<NodeWrapper, DefaultEdge> cfg = cfp.getCFG();
		ControlDependenceBuilder cdp = new ControlDependenceBuilder(cfg);
		cdg = cdp.getCDG();
		DataDependencyGraphBuilder ddgf = new DataDependencyGraphBuilder(cfg, this, cfp.getInitialNode());
		ddg = ddgf.findReachingDefs();
		

		//combine cdg and ddg to pdg with Nodes as vertices rather
		//than NodeWrappers
		DirectedPseudograph<Node, DefaultEdge> pdgNode = new DirectedPseudograph<>(DefaultEdge.class);
		if (m.getBody() == null) {
			return pdgNode;
		}
		for(NodeWrapper n: cdg.vertexSet()){
			pdgNode.addVertex(n.NODE);
		}
		for(NodeWrapper n: ddg.vertexSet()){
			if (!pdgNode.containsVertex(n.NODE)){
				pdgNode.addVertex(n.NODE);
			}
			
		}
		for(DefaultEdge e: cdg.edgeSet()){
			pdgNode.addEdge(cdg.getEdgeSource(e).NODE, cdg.getEdgeTarget(e).NODE);
		}
		for(DefaultEdge e: ddg.edgeSet()){
			pdgNode.addEdge(ddg.getEdgeSource(e).NODE, ddg.getEdgeTarget(e).NODE);
		}
		
		
/*		PDGGraphViz.writeDot(cdg, "cdg.dot");
		PDGGraphViz.writeDot(ddg, "ddg.dot");
		PDGGraphViz.writeDotNode(pdgNode, "pdg.dot");
		*/
		return pdgNode;
	
	}
	
	public DirectedPseudograph<Node, DefaultEdge> getPDG(){
		return this.pdg;
	}
	
	public void initPDG(){
		this.pdg = constructPDG(this);
	}
}
