package parsers;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.expr.AssignExpr.Operator;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.EnclosedExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.nodeTypes.NodeWithStatements;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.stmt.WhileStmt;
import com.github.javaparser.ast.type.PrimitiveType;
import com.github.javaparser.ast.type.PrimitiveType.Primitive;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.github.javaparser.ast.visitor.Visitable;
import com.github.javaparser.symbolsolver.javaparsermodel.JavaParserFacade;
import com.github.javaparser.symbolsolver.javaparsermodel.declarations.JavaParserMethodDeclaration;
import com.github.javaparser.symbolsolver.model.declarations.MethodDeclaration;
import com.github.javaparser.symbolsolver.model.resolution.SymbolReference;
import com.github.javaparser.symbolsolver.model.resolution.UnsolvedSymbolException;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;

public class MethodSolver {
	CombinedTypeSolver typeSolver;
	BlockStmt methodBody;
	int counter;
	String canaryMethod;

	public MethodSolver(BlockStmt methodBody) {
		this.methodBody = methodBody;
		this.typeSolver = new CombinedTypeSolver();
		this.typeSolver.add(new JavaParserTypeSolver(new File("../jre_library")));
		//this.typeSolver.add(new JavaParserTypeSolver(new File("./")));
		// this.typeSolver.add(new ReflectionTypeSolver());
		this.counter = 0;
		processMethod();
	}

	class MethodResolveVisitor extends ModifierVisitor<JavaParserFacade> {

		@Override
		public Visitable visit(MethodCallExpr methodCallExpr, JavaParserFacade facade) {

			try {
				return modifyMethodName(methodCallExpr, facade);
			} catch (UnsolvedSymbolException e) {
				System.out.println("ERROR " + e.getMessage());
			} catch (RuntimeException e) {
				System.out.println("ERROR " + e.getMessage());
				//e.printStackTrace();
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
		Optional<com.github.javaparser.ast.body.MethodDeclaration> methodCallAncestor = methodCallExpr
				.getAncestorOfType(com.github.javaparser.ast.body.MethodDeclaration.class);
		if (methodCallAncestor.get().getNameAsString().equals(methodCallExpr.getNameAsString())) {
			return methodCallExpr;
		}
		Optional<BlockStmt> declarationBody = null;
		Node returnNode = null;
		if (methodRef.isSolved()) {
			MethodDeclaration methodDecl = methodRef.getCorrespondingDeclaration();
			Expression argumentAssignment;
			AssignExpr methodCallAssignment = null;
			List<AssignExpr> methodCallAssignmentList = new LinkedList<AssignExpr>();
			Expression callExtractor = null;
			for (MethodDeclaration matchingDeclaration : methodDecl.declaringType().getDeclaredMethods()) {
				if (matchingDeclaration instanceof JavaParserMethodDeclaration) {
					if (matchingDeclaration.getSignature().equals(methodDecl.getSignature())) {
						//System.out.println("matchingDeclaration: " + matchingDeclaration.getQualifiedName());
						declarationBody = ((JavaParserMethodDeclaration) matchingDeclaration).getWrappedNode()
								.getBody();
						List<Expression> methodArguments = methodCallExpr.getArguments();
						List<Parameter> matchArguments = ((JavaParserMethodDeclaration) matchingDeclaration)
								.getWrappedNode().getParameters();

						while (!(methodCallParent instanceof BlockStmt)) {
							NameExpr replacementVariable = new NameExpr("output" + counter);

							if (methodCallParent instanceof IfStmt) {
								callExtractor = ((IfStmt) methodCallParent).getCondition();
								if (callExtractor.equals(methodCallExpr)) {
									((IfStmt) methodCallParent).setCondition(replacementVariable);
									methodCallAssignment = new AssignExpr(
											new VariableDeclarationExpr(new PrimitiveType(Primitive.BOOLEAN),
													replacementVariable.getNameAsString()),
											callExtractor, Operator.ASSIGN);
									returnNode = replacementVariable;
									methodCallAssignmentList.add(methodCallAssignment);
									counter++;
								} else {

								}
							}
							if (methodCallParent instanceof ReturnStmt) {
								callExtractor = ((ReturnStmt) methodCallParent).getExpression().get();
								((ReturnStmt) methodCallParent).setExpression(replacementVariable);
								methodCallAssignment = new AssignExpr(
										new VariableDeclarationExpr(methodCallParent
												.getAncestorOfType(
														com.github.javaparser.ast.body.MethodDeclaration.class)
												.get().getType(), replacementVariable.getNameAsString()),
										callExtractor, Operator.ASSIGN);
								methodCallAssignmentList.add(methodCallAssignment);
								returnNode = replacementVariable;
								counter++;
							}
							if (methodCallParent instanceof WhileStmt) {

								callExtractor = ((WhileStmt) methodCallParent).getCondition();
								((WhileStmt) methodCallParent).setCondition(replacementVariable);

								methodCallAssignment = new AssignExpr(
										new VariableDeclarationExpr(methodCallParent
												.getAncestorOfType(
														com.github.javaparser.ast.body.MethodDeclaration.class)
												.get().getType(), replacementVariable.getNameAsString()),
										callExtractor, Operator.ASSIGN);
								methodCallAssignmentList.add(methodCallAssignment);
								returnNode = replacementVariable;
								counter++;
							}
							if (methodCallParent instanceof EnclosedExpr) {
								if (attachmentBody instanceof AssignExpr) {
									callExtractor = ((AssignExpr) attachmentBody).getValue();

								} else {
									callExtractor = ((EnclosedExpr) methodCallParent).getInner().get();
								}
								((EnclosedExpr) methodCallParent).setInner(replacementVariable);
								methodCallAssignment = new AssignExpr(
										new VariableDeclarationExpr(methodCallParent
												.getAncestorOfType(
														com.github.javaparser.ast.body.MethodDeclaration.class)
												.get().getType(), replacementVariable.getNameAsString()),
										callExtractor, Operator.ASSIGN);
								returnNode = replacementVariable;
								methodCallAssignmentList.add(methodCallAssignment);
								counter++;
							}
							if (methodCallParent instanceof BinaryExpr) {
								Node temp = ((BinaryExpr) methodCallParent).getLeft();
								if (temp.equals(attachmentBody)) {
									if (attachmentBody instanceof AssignExpr) {
										callExtractor = ((AssignExpr) attachmentBody).getValue();

									} else {
										callExtractor = ((BinaryExpr) methodCallParent).getLeft();
									}
									((BinaryExpr) methodCallParent).setLeft(replacementVariable);
									methodCallAssignment = new AssignExpr(
											new VariableDeclarationExpr(methodCallParent
													.getAncestorOfType(
															com.github.javaparser.ast.body.MethodDeclaration.class)
													.get().getType(), replacementVariable.getNameAsString()),
											callExtractor, Operator.ASSIGN);
									returnNode = replacementVariable;
									methodCallAssignmentList.add(methodCallAssignment);

									counter++;

								} else {
									if (attachmentBody instanceof AssignExpr) {
										callExtractor = ((AssignExpr) attachmentBody).getValue();

									} else {
										callExtractor = ((BinaryExpr) methodCallParent).getRight();
									}
									((BinaryExpr) methodCallParent).setRight(replacementVariable);
									methodCallAssignment = new AssignExpr(
											new VariableDeclarationExpr(methodCallParent
													.getAncestorOfType(
															com.github.javaparser.ast.body.MethodDeclaration.class)
													.get().getType(), replacementVariable.getNameAsString()),
											callExtractor, Operator.ASSIGN);
									returnNode = replacementVariable;
									methodCallAssignmentList.add(methodCallAssignment);

									counter++;
								}
							}
							if (methodCallParent instanceof ExpressionStmt) {
									if (attachmentBody instanceof AssignExpr) {
										callExtractor = ((AssignExpr) attachmentBody).getValue();
										((ExpressionStmt) methodCallParent).setExpression(replacementVariable);
										methodCallAssignment = new AssignExpr(
												new VariableDeclarationExpr(methodCallParent
														.getAncestorOfType(
																com.github.javaparser.ast.body.MethodDeclaration.class)
														.get().getType(), replacementVariable.getNameAsString()),
												callExtractor, Operator.ASSIGN);
										returnNode = replacementVariable;
										methodCallAssignmentList.add(methodCallAssignment);
										counter++;
									}
							}
						attachmentBody = methodCallParent;
						methodCallParent = methodCallParent.getParentNode().get();
					}
					int childIndex = methodCallParent.getChildNodes().indexOf(attachmentBody);
					for (int i = 0; i < methodArguments.size(); i++) {
						argumentAssignment = new AssignExpr(new NameExpr(matchArguments.get(i).toString()),
								methodArguments.get(i), Operator.ASSIGN);
						childIndex = methodCallParent.getChildNodes().indexOf(attachmentBody);
						((BlockStmt) methodCallParent).addStatement(childIndex++,
								new ExpressionStmt(argumentAssignment));
					}
					for (AssignExpr m : methodCallAssignmentList) {
						((BlockStmt) methodCallParent).addStatement(childIndex, m);
						childIndex++;
					}

					methodCallExpr.removeScope();
					methodCallExpr.setName(methodDecl.getQualifiedName());
					if (declarationBody != null && declarationBody.isPresent()) {

						declarationBody.get().accept(new MethodResolveVisitor(), JavaParserFacade.get(typeSolver));

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
							System.out.println("ALERT: METHOD CALL IS NOT RESOLVED: "+ methodCallExpr.getNameAsString()+ ": " + methodCallParent.getClass());
						}
						return null;
					}
				}
			}
		}
	} else {
		System.out.println("??? " + methodCallExpr.getNameAsString());
	}
	return returnNode;
	}

	private void replaceMethod(Node attachmentBody, Node methodCallParent, AssignExpr methodCallAssignment,
			BlockStmt newBody) {
		if (methodCallParent instanceof IfStmt) {
			((IfStmt) methodCallParent).setThenStmt(newBody);
		} else if (methodCallParent instanceof ForStmt) {
			System.out.println("Damn");
		} else {
			int childIndex = methodCallParent.getChildNodes().indexOf(attachmentBody) + 1;
			for (Node n : newBody.getChildNodes()) {
				n = n.clone();
				if (n instanceof ReturnStmt) {
					if (methodCallAssignment != null) {
						methodCallAssignment.setValue(((ReturnStmt) n).getExpression().get());
					}
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