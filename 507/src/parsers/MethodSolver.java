package parsers;

import java.io.File;
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
import com.github.javaparser.ast.nodeTypes.NodeWithStatements;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.stmt.Statement;
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
		// this.typeSolver.add(new JavaParserTypeSolver(new File("./")));
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
				// e.printStackTrace();
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

		Optional<com.github.javaparser.ast.body.MethodDeclaration> methodCallAncestor = methodCallExpr
				.getAncestorOfType(com.github.javaparser.ast.body.MethodDeclaration.class);
		if ((methodCallExpr.getScope().get() + methodCallExpr.getNameAsString()).equals(methodCallAncestor.get().getNameAsString())) {
			return methodCallExpr.getName();
		}
		Node returnNode = null;
		if (methodRef.isSolved()) {
			MethodDeclaration methodDecl = methodRef.getCorrespondingDeclaration();

			for (MethodDeclaration matchingDeclaration : methodDecl.declaringType().getDeclaredMethods()) {
				if (matchingDeclaration instanceof JavaParserMethodDeclaration) {
					if (matchingDeclaration.getSignature().equals(methodDecl.getSignature())) {
						// System.out.println("matchingDeclaration: " +
						// matchingDeclaration.getQualifiedName());
						NameExpr replacementVariable =  new NameExpr("output");
						AssignExpr methodCallAssignment  = normalizeMethodCalls(methodCallExpr, matchingDeclaration, replacementVariable);
						if (methodCallAssignment != null) {
							returnNode = replacementVariable;
						}
						
						/*methodCallExpr.removeScope();
						methodCallExpr.setName(methodDecl.getQualifiedName());*/
						Optional<BlockStmt> declarationBody = ((JavaParserMethodDeclaration) matchingDeclaration)
								.getWrappedNode().getBody();
						if (declarationBody != null && declarationBody.isPresent()) {

							declarationBody.get().accept(new MethodResolveVisitor(), JavaParserFacade.get(typeSolver));
							Node methodCallParent = methodCallExpr.getParentNode().get();
							Node attachmentBody = methodCallExpr;
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
								System.out.println("ALERT: METHOD CALL IS NOT RESOLVED: "
										+ methodCallExpr.getNameAsString() + ": " + methodCallParent.getClass());
							}
							return returnNode;
						}
					}
				}
			}
		} else {
			System.out.println("??? " + methodCallExpr.getNameAsString());
		}
		return returnNode;
	}

	private AssignExpr normalizeMethodCalls(MethodCallExpr methodCallExpr, MethodDeclaration matchingDeclaration, NameExpr replacementVariable) {
		List<Expression> methodArguments = methodCallExpr.getArguments();
		List<Parameter> matchArguments = ((JavaParserMethodDeclaration) matchingDeclaration).getWrappedNode()
				.getParameters();
		Node attachmentBody = null;
		Node methodCallParent = methodCallExpr.getParentNode().get();
		List<AssignExpr> methodCallAssignmentList = new LinkedList<AssignExpr>();
		NameExpr replacementVariable_temp = replacementVariable;
		AssignExpr methodCallAssignment = null;
		
		while (!(methodCallParent instanceof BlockStmt)) {
			replacementVariable_temp =  new NameExpr(replacementVariable.getNameAsString() + this.counter);
			MethodCallNormalizer mNorm = new MethodCallNormalizer(replacementVariable_temp);
			methodCallAssignment = mNorm.extractCall(methodCallParent, attachmentBody);
			
			if (methodCallAssignment != null) {
				methodCallAssignmentList.add(methodCallAssignment);
				this.counter++;
			}

			attachmentBody = methodCallParent;
			methodCallParent = methodCallParent.getParentNode().get();
		}
		int childIndex = methodCallParent.getChildNodes().indexOf(attachmentBody);
		for (int i = 0; i < methodArguments.size(); i++) {
			Expression argumentAssignment = new AssignExpr(new NameExpr(matchArguments.get(i).toString()),
					methodArguments.get(i), Operator.ASSIGN);
			childIndex = methodCallParent.getChildNodes().indexOf(attachmentBody);
			((BlockStmt) methodCallParent).addStatement(childIndex++, new ExpressionStmt(argumentAssignment));
		}
		for (AssignExpr m : methodCallAssignmentList) {
			((BlockStmt) methodCallParent).addStatement(childIndex++, m);
		}
		replacementVariable.setName(replacementVariable_temp.getNameAsString());
		return methodCallAssignment;
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
				} if (n instanceof Statement) {
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