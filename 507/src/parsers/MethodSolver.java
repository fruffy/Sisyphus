package parsers;

import java.io.File;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
import com.github.javaparser.ast.stmt.ForeachStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.github.javaparser.ast.visitor.Visitable;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.symbolsolver.javaparsermodel.JavaParserFacade;
import com.github.javaparser.symbolsolver.javaparsermodel.declarations.JavaParserMethodDeclaration;
import com.github.javaparser.symbolsolver.model.declarations.MethodDeclaration;
import com.github.javaparser.symbolsolver.model.resolution.SymbolReference;
import com.github.javaparser.symbolsolver.model.resolution.UnsolvedSymbolException;
import com.github.javaparser.symbolsolver.model.typesystem.ReferenceType;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;

public class MethodSolver {
	CombinedTypeSolver typeSolver;
	BlockStmt methodBody;
	int counter;
	Set<String> canaryMethods;
	int maxDepth;

	public MethodSolver(BlockStmt methodBody, int maxDepth) {
		this.methodBody = methodBody;
		this.typeSolver = new CombinedTypeSolver();
		this.typeSolver.add(new JavaParserTypeSolver(new File("../jre_library")));
		//this.typeSolver.add(new JavaParserTypeSolver(new File("../experimental/java.base")));

		// this.typeSolver.add(new JavaParserTypeSolver(new File("./")));
		//this.typeSolver.add(new ReflectionTypeSolver());
		this.counter = 0;
		this.maxDepth = maxDepth;
		this.canaryMethods = new HashSet<String>();
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
		Node returnNode = null;
		if (methodRef.isSolved()) {
			MethodDeclaration methodDecl = methodRef.getCorrespondingDeclaration();
			//System.out.println("CHECKING..." + methodDecl.getSignature() + " vs " + methodCallAncestor.get().getSignature());
			if (!methodCallAncestor.isPresent()
					|| methodDecl.getName().equals(methodCallAncestor.get().getName())) {
				System.err.println("Redundant Call!");
				//return null;
				return new NameExpr(methodCallExpr.getName());
			}
			//System.out.println("matchingDeclaration: " + methodDecl.getQualifiedName());
			if (methodDecl instanceof JavaParserMethodDeclaration) {
				NameExpr replacementVariable = new NameExpr("output");
				AssignExpr methodCallAssignment = normalizeMethodCalls(methodCallExpr, methodDecl, replacementVariable);
				if (methodCallAssignment != null) {
					returnNode = replacementVariable;
				}
				/*
				 * methodCallExpr.removeScope();
				 * methodCallExpr.setName(methodDecl.getQualifiedName());
				 */
				Optional<BlockStmt> declarationBody = ((JavaParserMethodDeclaration) methodDecl).getWrappedNode()
						.getBody();
				if (declarationBody != null && declarationBody.isPresent()) {
					// System.out.println(declarationBody.get());

					if (!this.canaryMethods.contains(methodDecl.getQualifiedSignature())) {
						this.canaryMethods.add(methodDecl.getQualifiedSignature());
						//declarationBody.get().accept(new MethodResolveVisitor(), JavaParserFacade.get(typeSolver));
					} else {
						System.err.println("Cycle Detected!");
						return new NameExpr(methodCallExpr.getName());
					}
					this.canaryMethods.clear();
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
						// TODO: Find way to handle additional combinations
						System.err.println("ALERT: METHOD CALL IS NOT RESOLVED: " + methodCallExpr.getNameAsString()
								+ ": " + methodCallParent.getClass());
						return new NameExpr(methodCallExpr.getName());
					}
					return returnNode;
				}
			} else {
				System.err.println("GOT THIS METHOD WRONG " + methodDecl.getClass());
			}
		} else {
			System.out.println("Could not solve method call: " + methodCallExpr.getNameAsString());
		}
		return returnNode;
	}

	private AssignExpr normalizeMethodCalls(MethodCallExpr methodCallExpr, MethodDeclaration matchingDeclaration,
			NameExpr replacementVariable) {
		List<Expression> methodArguments = methodCallExpr.getArguments();
		List<Parameter> matchArguments = ((JavaParserMethodDeclaration) matchingDeclaration).getWrappedNode()
				.getParameters();
		Node attachmentBody = null;
		Node methodCallParent = methodCallExpr.getParentNode().get();
		List<AssignExpr> methodCallAssignmentList = new LinkedList<AssignExpr>();
		NameExpr replacementVariable_temp = replacementVariable;
		AssignExpr methodCallAssignment = null;

		while (!(methodCallParent instanceof BlockStmt)) {
			replacementVariable_temp = new NameExpr(replacementVariable.getNameAsString() + this.counter);
			MethodCallNormalizer mNorm = new MethodCallNormalizer(replacementVariable_temp);
			methodCallAssignment = mNorm.extractCall(methodCallParent, attachmentBody);

			if (methodCallAssignment != null) {
				methodCallAssignmentList.add(methodCallAssignment);
				this.counter++;
			}

			attachmentBody = methodCallParent;
			methodCallParent = methodCallParent.getParentNode().get();
		}
		int childIndex = ((BlockStmt) methodCallParent).getStatements().indexOf(attachmentBody);
		for (int i = 0; i < methodArguments.size(); i++) {
			Expression argumentAssignment = new AssignExpr(new NameExpr(matchArguments.get(i).toString()),
					methodArguments.get(i), Operator.ASSIGN);
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
		} else if (methodCallParent instanceof ForStmt || methodCallParent instanceof ForeachStmt) {
			// TODO: Find way to handle ForStmts and ForEachStmts
			System.err.println("Damn");
		} else {
			int childIndex = ((BlockStmt) methodCallParent).getStatements().indexOf(attachmentBody) + 1;
			for (Node n : newBody.getChildNodes()) {
				Node temp = n.clone();
				if (temp instanceof ReturnStmt) {
					if (methodCallAssignment != null) {
						methodCallAssignment.setValue(((ReturnStmt) temp).getExpression().get());
					}
					break;
				}
				if (n instanceof Statement) {
					((NodeWithStatements<?>) methodCallParent).addStatement(childIndex, (Statement) temp);
					childIndex++;
				} else if (n instanceof Expression) {
					((NodeWithStatements<?>) methodCallParent).addStatement(childIndex, (Expression) temp);
					childIndex++;
				}
			}
		}
	}

	private void processMethod() {
		this.methodBody.accept(new MethodResolveVisitor(), JavaParserFacade.get(typeSolver));
	}

}