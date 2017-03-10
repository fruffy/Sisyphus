package parsers;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.expr.AssignExpr.Operator;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.visitor.ModifierVisitor;
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

	class MethodResolveVisitor extends ModifierVisitor<JavaParserFacade> {

		@Override
		public MethodCallExpr visit(MethodCallExpr methodCallExpr, JavaParserFacade facade) {
			try {
				if (modifyMethodName(methodCallExpr, facade)) {
					return null;
				}
			} catch (RuntimeException e) {
				System.out.println("ERROR " + e.getMessage());
			} catch (Throwable t) {
				t.printStackTrace();
			}
			return methodCallExpr;
		}
	}

	private boolean modifyMethodName(MethodCallExpr methodCallExpr, JavaParserFacade facade) {
		SymbolReference<MethodDeclaration> methodRef = facade.solve(methodCallExpr);
		Node methodCallParent = methodCallExpr.getParentNode().get();
		Optional<BlockStmt> methodBdy = null;
		if (methodRef.isSolved()) {
			MethodDeclaration methodDecl = methodRef.getCorrespondingDeclaration();

			for (MethodDeclaration test : methodDecl.declaringType().getDeclaredMethods()) {
				if (test instanceof JavaParserMethodDeclaration) {
					if (test.getQualifiedName().equals(methodDecl.getQualifiedName())) {
						methodBdy = ((JavaParserMethodDeclaration) test).getWrappedNode().getBody();
						List<Expression > methodArguments = methodCallExpr.getArguments();
						List<Parameter> resolveArguments = ((JavaParserMethodDeclaration) test).getWrappedNode().getParameters();
						for (int i = 0; i < methodArguments.size(); i++) {
							Expression quasimode = new AssignExpr(new NameExpr(resolveArguments.get(i).getNameAsString()),methodArguments.get(i),  Operator.ASSIGN);
							int childIndex = methodCallParent.getChildNodes().indexOf(methodCallExpr);
							methodBdy.get().addStatement(childIndex, quasimode);							
						}
					}
				}
			}
			methodCallExpr.removeScope();
			methodCallExpr.setName(methodDecl.getQualifiedName());

			if (methodBdy != null && methodBdy.isPresent()) {
				
				BlockStmt newBody = methodBdy.get().clone();
				newBody.accept(new MethodResolveVisitor(), JavaParserFacade.get(typeSolver));
				if (methodCallParent instanceof ReturnStmt) {
					this.methodBody.addStatement(newBody);
					methodCallParent.remove();
				} else {
					//this.methodBody.addStatement(newBody);
					for (Node n : methodBdy.get().getChildNodes()) {
						Node newNode = n.clone();
						int childIndex = this.methodBody.getChildNodes().indexOf(methodCallParent);
						if (newNode instanceof Statement) {
							this.methodBody.addStatement(childIndex,(Statement) newNode);
						} else if (newNode instanceof Expression) {
							this.methodBody.addStatement(childIndex,(Expression) newNode);
						}
					}
					methodCallExpr.remove();
				}				
				return true;
			}
		} else {
			System.out.println("???");
		}
		return false;
	}

	private void processMethod() {
		this.methodBody.accept(new MethodResolveVisitor(), JavaParserFacade.get(typeSolver));

	}

}