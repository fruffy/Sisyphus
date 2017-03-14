package parsers;

import java.io.File;
import java.util.List;
import java.util.Optional;

import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.expr.AssignExpr.Operator;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.nodeTypes.NodeWithBlockStmt;
import com.github.javaparser.ast.nodeTypes.NodeWithExpression;
import com.github.javaparser.ast.nodeTypes.NodeWithStatements;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.type.PrimitiveType;
import com.github.javaparser.ast.type.PrimitiveType.Primitive;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.ast.type.WildcardType;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.github.javaparser.ast.visitor.Visitable;
import com.github.javaparser.symbolsolver.javaparsermodel.JavaParserFacade;
import com.github.javaparser.symbolsolver.javaparsermodel.declarations.JavaParserMethodDeclaration;
import com.github.javaparser.symbolsolver.model.declarations.MethodDeclaration;
import com.github.javaparser.symbolsolver.model.resolution.SymbolReference;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;

public class MethodSolver {
	CombinedTypeSolver typeSolver;
	BlockStmt methodBody;
	int loopCanary = 0;

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
		public Visitable visit(MethodCallExpr methodCallExpr, JavaParserFacade facade) {

			try {
				return modifyMethodName(methodCallExpr, facade);
			} catch (RuntimeException e) {
				System.out.println("ERROR " + e.getMessage());
			} catch (Throwable t) {
				t.printStackTrace();
			}
			return methodCallExpr;
		}
	}

	class RemoveVisitor extends ModifierVisitor<MethodCallExpr> {
		@Override
		public MethodCallExpr visit(MethodCallExpr n, MethodCallExpr arg) {
			if (n.equals(arg)) {
				return null;
			}
			return n;
		}
	}

	private Node modifyMethodName(MethodCallExpr methodCallExpr, JavaParserFacade facade) {
		SymbolReference<MethodDeclaration> methodRef = facade.solve(methodCallExpr);
		Node attachmentBody = null;
		Node methodCallParent = methodCallExpr.getParentNode().get();
		Optional<BlockStmt> declarationBody = null;
		Node returnNode = null;
		if (methodRef.isSolved()) {
			MethodDeclaration methodDecl = methodRef.getCorrespondingDeclaration();
			Expression argumentAssignment;
			AssignExpr methodCallAssignment = null;
			Expression callExtractor = null;
			int tracker = 0;
			for (MethodDeclaration matchingDeclaration : methodDecl.declaringType().getDeclaredMethods()) {
				if (matchingDeclaration instanceof JavaParserMethodDeclaration) {
					if (matchingDeclaration.getSignature().equals(methodDecl.getSignature())) {
						declarationBody = ((JavaParserMethodDeclaration) matchingDeclaration).getWrappedNode()
								.getBody();
						List<Expression> methodArguments = methodCallExpr.getArguments();
						List<Parameter> matchArguments = ((JavaParserMethodDeclaration) matchingDeclaration)
								.getWrappedNode().getParameters();
						for (int i = 0; i < methodArguments.size(); i++) {
							argumentAssignment = new AssignExpr(new NameExpr(matchArguments.get(i).toString()),
									methodArguments.get(i), Operator.ASSIGN);

							while (!(methodCallParent instanceof BlockStmt)) {

								if (methodCallParent instanceof IfStmt) {
									callExtractor = ((IfStmt) methodCallParent).getCondition();
									if (callExtractor.equals(methodCallExpr)) {
										NameExpr replacementVariable = new NameExpr("output");
										((IfStmt) methodCallParent).setCondition(replacementVariable);
										methodCallAssignment = new AssignExpr(
												new VariableDeclarationExpr(new PrimitiveType(Primitive.BOOLEAN),
														replacementVariable.getNameAsString()),
												callExtractor, Operator.ASSIGN);
										returnNode = replacementVariable;
									}
								}
								if (methodCallParent instanceof ReturnStmt) {
									NameExpr replacementVariable = new NameExpr("output");
									callExtractor = ((ReturnStmt) methodCallParent).getExpression().get();
									((ReturnStmt) methodCallParent).setExpression(replacementVariable);

									methodCallAssignment = new AssignExpr(
											new VariableDeclarationExpr(methodCallParent
													.getAncestorOfType(
															com.github.javaparser.ast.body.MethodDeclaration.class)
													.get().getType(), replacementVariable.getNameAsString()),
											callExtractor, Operator.ASSIGN);
									returnNode = replacementVariable;
								}
								attachmentBody = methodCallParent;
								methodCallParent = methodCallParent.getParentNode().get();
							}
							int childIndex = methodCallParent.getChildNodes().indexOf(attachmentBody);
							((BlockStmt) methodCallParent).addStatement(childIndex++,
									new ExpressionStmt(argumentAssignment));
							if (methodCallAssignment != null) {
								((BlockStmt) methodCallParent).addStatement(childIndex, methodCallAssignment);
							}
						}

						methodCallParent = methodCallExpr.getParentNode().get();
						attachmentBody = methodCallExpr;

						// methodCallExpr.removeScope();
						// methodCallExpr.setName(methodDecl.getQualifiedName());
						if (declarationBody != null && declarationBody.isPresent()) {
							
							if (loopCanary <100) {
								System.out.println(loopCanary);
								loopCanary++;
								declarationBody.get().accept(new MethodResolveVisitor(), JavaParserFacade.get(typeSolver));
							} else {
								attachmentBody = methodCallExpr;
								while(attachmentBody.remove()) {
									attachmentBody = attachmentBody.getParentNode().get();
								}
							}
							methodCallParent = methodCallExpr.getParentNode().get();
							attachmentBody = methodCallExpr;
							BlockStmt newBody = declarationBody.get().clone();
							if (methodCallParent instanceof BlockStmt) {
								replaceMethod(attachmentBody, methodCallParent, methodCallAssignment, newBody);
							} else if (methodCallParent instanceof AssignExpr) {
								attachmentBody = methodCallParent.getParentNode().get();
								methodCallParent = methodCallParent.getParentNode().get().getParentNode().get();
								replaceMethod(attachmentBody, methodCallParent, methodCallAssignment, newBody);
							} else if (methodCallParent instanceof ExpressionStmt) {
								attachmentBody = methodCallParent;
								methodCallParent = methodCallParent.getParentNode().get();
								replaceMethod(attachmentBody, methodCallParent, methodCallAssignment, newBody);
							} else {
								System.out.println(methodCallParent.getClass());
								// methodCallExpr.removeScope();
								// methodCallExpr.setName(methodDecl.getQualifiedName());
							}
							return returnNode;
						}
					}
				}

			}

		} else

		{
			System.out.println("???");
		}
		return returnNode;
	}

	private void replaceMethod(Node attachmentBody, Node methodCallParent, AssignExpr methodCallAssignment,
			BlockStmt newBody) {
		if (methodCallParent instanceof IfStmt) {
			((IfStmt) methodCallParent).setThenStmt(newBody);
		} else if (methodCallParent instanceof ForStmt) {

		} else {
			int childIndex = methodCallParent.getChildNodes().indexOf(attachmentBody) + 1;
			for (Node n : newBody.getChildNodes()) {
				n = n.clone();
				if (n instanceof ReturnStmt) {
					methodCallAssignment.setValue(((ReturnStmt) n).getExpression().get());
					break;
				} else if (n instanceof Statement) {
					((NodeWithStatements<?>) methodCallParent).addStatement(childIndex, (Statement) n);
					childIndex++;
				} else if (n instanceof Expression) {
					((NodeWithStatements<?>) methodCallParent).addStatement(childIndex, (Expression) n);
					childIndex++;
				}
			}
		}
	}

	private void processMethod() {
		this.methodBody.accept(new MethodResolveVisitor(), JavaParserFacade.get(typeSolver));

	}

}