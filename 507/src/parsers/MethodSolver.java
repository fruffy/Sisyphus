package parsers;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.symbolsolver.javaparsermodel.JavaParserFacade;
import com.github.javaparser.symbolsolver.javaparsermodel.declarations.JavaParserMethodDeclaration;
import com.github.javaparser.symbolsolver.model.declarations.MethodDeclaration;
import com.github.javaparser.symbolsolver.model.resolution.SymbolReference;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;

public class MethodSolver {
	CombinedTypeSolver typeSolver;
	BlockStmt methodBody;
	HashMap<MethodCallExpr, List<Node>> bodyMap;
	List<MethodCallExpr> callList;

	public MethodSolver(BlockStmt methodBody) {
		this.methodBody = methodBody;
		this.callList = new LinkedList<MethodCallExpr>();
		this.typeSolver = new CombinedTypeSolver();
		this.typeSolver.add(new JavaParserTypeSolver(new File("../jre_library")));
		this.typeSolver.add(new JavaParserTypeSolver(new File("./")));
		// this.typeSolver.add(new ReflectionTypeSolver());
		this.bodyMap = new HashMap<MethodCallExpr, List<Node>>();
		processMethod();
	}

	class MethodCallExprVisitor extends VoidVisitorAdapter<JavaParserFacade> {

		@Override
		public void visit(MethodCallExpr methodCallExpr, JavaParserFacade facade) {
			callList.add(methodCallExpr);
		}
	}

	class TypeCalculatorVisitor extends VoidVisitorAdapter<JavaParserFacade> {

		@Override
		public void visit(MethodCallExpr methodCallExpr, JavaParserFacade facade) {
			try {
				modifyMethodName(methodCallExpr, facade);
			} catch (RuntimeException e) {
				System.out.println("ERROR " + e.getMessage());
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}
	}

	private void modifyMethodName(MethodCallExpr methodCallExpr, JavaParserFacade facade) {
		SymbolReference<MethodDeclaration> methodRef = facade.solve(methodCallExpr);
		Node methodCallParent = methodCallExpr.getParentNode().get();
		Optional<BlockStmt> methodBdy = null;
		if (methodRef.isSolved()) {
			MethodDeclaration methodDecl = methodRef.getCorrespondingDeclaration();

			for (MethodDeclaration test : methodDecl.declaringType().getDeclaredMethods()) {
				if (test instanceof JavaParserMethodDeclaration) {
					if (test.getQualifiedName().equals(methodDecl.getQualifiedName())) {
						methodBdy = ((JavaParserMethodDeclaration) test).getWrappedNode().getBody();
					}
				}
			}
			methodCallExpr.removeScope();
			methodCallExpr.setName(methodDecl.getQualifiedName());

			if (methodBdy != null && methodBdy.isPresent()) {
				// BlockStmt newNode = methodBdy.get().clone();
				// newNode.setRange(methodCallExpr.getRange().get());
				// methodCallParent.setAsParentNodeOf(newNode.getChildNodes());


				//this.bodyMap.put(methodCallExpr, methodBdy.get().getChildNodes());
				// System.out.println(methodCallExpr+ "\n------------------");
				//this.methodBody.register(new PropagatingAstObserver(), ObserverRegistrationMode.SELF_PROPAGATING);.addStatement(index, expr).addAndGetStatement(methodBdy.get().clone());

				for (Node n : methodBdy.get().getChildNodes()) {
					Node newNode = n.clone();
					newNode.setParentNode(methodCallParent);	
				}

				methodCallExpr.remove();
			}

		} else {
			System.out.println("???");
		}
	}

	private void processMethod() {
		// this.methodBody.accept(new MethodCallExprVisitor(),
		// JavaParserFacade.get(typeSolver));
		this.methodBody.accept(new TypeCalculatorVisitor(), JavaParserFacade.get(typeSolver));

	}

	private void attachMethodCallBodies() {
		Iterator<MethodCallExpr> test = this.callList.iterator();
		while (test.hasNext()) {
			try {
				modifyMethodName(test.next(), JavaParserFacade.get(typeSolver));
			} catch (RuntimeException e) {
				System.out.println("ERROR " + e.getMessage());
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}

		if (this.bodyMap != null) {
			Set<MethodCallExpr> bodySet = this.bodyMap.keySet();
			Iterator<MethodCallExpr> iter = bodySet.iterator();
			while (iter.hasNext()) {
				MethodCallExpr m = iter.next();
				Node methodCallParent = m.getParentNode().get();
				List<Node> newNodes = bodyMap.get(m);
				for (Node node : newNodes) {
					Node newNode = node.clone();
					newNode.setRange(m.getRange().get());
					methodCallParent.setAsParentNodeOf(newNode);
				}
				m.remove();
			}
		}
	}

}