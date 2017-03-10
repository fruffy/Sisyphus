package parsers;

import java.io.File;
import java.util.List;
import java.util.Optional;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.expr.AssignExpr.Operator;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.nodeTypes.NodeWithStatements;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.github.javaparser.symbolsolver.javaparsermodel.JavaParserFacade;
import com.github.javaparser.symbolsolver.javaparsermodel.declarations.JavaParserMethodDeclaration;
import com.github.javaparser.symbolsolver.model.declarations.MethodDeclaration;
import com.github.javaparser.symbolsolver.model.resolution.SymbolReference;
import com.github.javaparser.symbolsolver.resolution.javaparser.contexts.MethodCallExprContextResolutionTest;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;

public class MethodSolver {
	CombinedTypeSolver typeSolver;
	BlockStmt methodBody;

	public MethodSolver(BlockStmt methodBody) {
		this.methodBody = methodBody;
		this.typeSolver = new CombinedTypeSolver();
		this.typeSolver.add(new JavaParserTypeSolver(new File("../jre_library")));
		this.typeSolver.add(new JavaParserTypeSolver(new File("./")));
		// this.typeSolver.add(new ReflectionTypeSolver());
		processMethod();
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
				e.printStackTrace();
			} /*
				 * catch (Throwable t) { t.printStackTrace(); }
				 */
			return methodCallExpr;
		}
	}

	private boolean modifyMethodName(MethodCallExpr methodCallExpr, JavaParserFacade facade) {
		SymbolReference<MethodDeclaration> methodRef = facade.solve(methodCallExpr);
		BlockStmt attachmentBody =  methodCallExpr.getAncestorOfType(BlockStmt.class).get();
		Node methodCallParent = methodCallExpr.getParentNode().get();
		Optional<BlockStmt> methodBdy = null;
		if (methodRef.isSolved()) {
			MethodDeclaration methodDecl = methodRef.getCorrespondingDeclaration();

			for (MethodDeclaration matchingDeclaration : methodDecl.declaringType().getDeclaredMethods()) {
				if (matchingDeclaration instanceof JavaParserMethodDeclaration) {
					if (matchingDeclaration.getSignature().equals(methodDecl.getSignature())) {
						methodBdy = ((JavaParserMethodDeclaration) matchingDeclaration).getWrappedNode().getBody();
						List<Expression> methodArguments = methodCallExpr.getArguments();
						List<Parameter> matchArguments = ((JavaParserMethodDeclaration) matchingDeclaration)
								.getWrappedNode().getParameters();
						for (int i = 0; i < methodArguments.size(); i++) {
							Expression argumentAssignment = new AssignExpr(new NameExpr(matchArguments.get(i).toString()),
											methodArguments.get(i), Operator.ASSIGN);
							if (methodCallParent instanceof BlockStmt) {
								int childIndex = methodCallParent.getChildNodes().indexOf(methodCallExpr);
								((BlockStmt) methodCallParent).addStatement(childIndex, new ExpressionStmt(argumentAssignment));
							} else if (methodCallParent instanceof VariableDeclarator) {
								//int childIndex = attachmentBody.getChildNodes().indexOf(methodCallParent);
								((VariableDeclarator) methodCallParent).setInitializer(argumentAssignment);
							} else {
								int childIndex = attachmentBody.getChildNodes().indexOf(methodCallParent);
								attachmentBody.addStatement(childIndex, new ExpressionStmt(argumentAssignment));
							}
						}
					}
				}
				methodCallExpr.removeScope();
				methodCallExpr.setName(methodDecl.getQualifiedName());

				if (methodBdy != null && methodBdy.isPresent()) {

					BlockStmt newBody = methodBdy.get().clone();
					if (methodCallParent instanceof ReturnStmt) {
						this.methodBody.addStatement(newBody);
						// methodCallParent.remove();
					} else if (methodCallParent instanceof BlockStmt) {

						int childIndex = methodCallParent.getChildNodes().indexOf(methodCallExpr);
						((BlockStmt) methodCallParent).addStatement(childIndex, newBody);
						/*
						 * for (Node n : methodBdy.get().getChildNodes()) { Node
						 * newNode = n.clone(); if (newNode instanceof
						 * Statement) {
						 * this.methodBody.addStatement(childIndex,(Statement)
						 * newNode); } else if (newNode instanceof Expression) {
						 * this.methodBody.addStatement(childIndex,(Expression)
						 * newNode); } }
						 */
						methodCallExpr.remove();
						newBody.accept(new MethodResolveVisitor(), JavaParserFacade.get(typeSolver));
					}
					return true;
				}
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