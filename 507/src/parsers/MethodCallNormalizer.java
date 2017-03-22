package parsers;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.EnclosedExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.expr.AssignExpr.Operator;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.stmt.WhileStmt;
import com.github.javaparser.ast.type.PrimitiveType;
import com.github.javaparser.ast.type.PrimitiveType.Primitive;

public class MethodCallNormalizer {
	NameExpr replacementVariable;

	public MethodCallNormalizer(NameExpr replacementVariable) {
		this.replacementVariable = replacementVariable;
	}

	public AssignExpr extractCall(Node methodCallParent, Node attachmentBody) {
		
		if (methodCallParent instanceof IfStmt) {
			return extractCall((IfStmt) methodCallParent, attachmentBody);
		} else if (methodCallParent instanceof ForStmt) {
			return extractCall((ForStmt) methodCallParent, attachmentBody);
		} else if (methodCallParent instanceof WhileStmt) {
			return extractCall((WhileStmt) methodCallParent, attachmentBody);
		} else if (methodCallParent instanceof ReturnStmt) {
			return extractCall((ReturnStmt) methodCallParent, attachmentBody);
		} else if (methodCallParent instanceof EnclosedExpr) {
			return extractCall((EnclosedExpr) methodCallParent, attachmentBody);
		} else if (methodCallParent instanceof BinaryExpr) {
			return extractCall((BinaryExpr) methodCallParent, attachmentBody);
		} else if (methodCallParent instanceof ExpressionStmt) {
			return extractCall((ExpressionStmt) methodCallParent, attachmentBody);
		} else {
			return null;
		}
	}

	public AssignExpr extractCall(IfStmt methodCallParent, Node attachmentBody) {
		Expression callExtractor;
		callExtractor = methodCallParent.getCondition();
		if (callExtractor.equals(attachmentBody)) {
			methodCallParent.setCondition(this.replacementVariable);
		}
		return addAssignBoolean(callExtractor);
	}

	public AssignExpr extractCall(ForStmt methodCallParent, Node attachmentBody) {
		Expression callExtractor;
		callExtractor = methodCallParent.getCompare().get();
		if (callExtractor.equals(attachmentBody)) {
			methodCallParent.setCompare(this.replacementVariable);
		}
		return addAssignBoolean(callExtractor);
	}

	public AssignExpr extractCall(WhileStmt methodCallParent, Node attachmentBody) {
		Expression callExtractor = methodCallParent.getCondition();
		methodCallParent.setCondition(this.replacementVariable);
		return addAssignBoolean(callExtractor);
	}

	public AssignExpr extractCall(ReturnStmt methodCallParent, Node attachmentBody) {
		Expression callExtractor;
		callExtractor = methodCallParent.getExpression().get();
		methodCallParent.setExpression(this.replacementVariable);
		return addAssignType(methodCallParent, callExtractor);
	}

	public AssignExpr extractCall(EnclosedExpr methodCallParent, Node attachmentBody) {
		Expression callExtractor;
		if (attachmentBody instanceof AssignExpr) {
			callExtractor = ((AssignExpr) attachmentBody).getValue();

		} else {
			callExtractor = methodCallParent.getInner().get();
		}
		methodCallParent.setInner(this.replacementVariable);
		return addAssignType(methodCallParent, callExtractor);
	}

	public AssignExpr extractCall(BinaryExpr methodCallParent, Node attachmentBody) {
		Expression callExtractor;
		Node temp = methodCallParent.getLeft();
		if (temp.equals(attachmentBody)) {
			if (attachmentBody instanceof AssignExpr) {
				callExtractor = ((AssignExpr) attachmentBody).getValue();

			} else {
				callExtractor = methodCallParent.getLeft();
			}
			methodCallParent.setLeft(this.replacementVariable);
		} else {
			if (attachmentBody instanceof AssignExpr) {
				callExtractor = ((AssignExpr) attachmentBody).getValue();

			} else {
				callExtractor = methodCallParent.getRight();
			}
			methodCallParent.setRight(this.replacementVariable);
		}
		return addAssignType(methodCallParent, callExtractor);
	}

	public AssignExpr extractCall(ExpressionStmt methodCallParent, Node attachmentBody) {
		Expression callExtractor = null;
		if (attachmentBody instanceof AssignExpr) {
			callExtractor = ((AssignExpr) attachmentBody).getValue();
			methodCallParent.setExpression(this.replacementVariable);
			return addAssignType(methodCallParent, callExtractor);
		} else {
			return null;
		}
	}

	private AssignExpr addAssignBoolean(Expression callExtractor) {
		AssignExpr methodCallAssignment = new AssignExpr(
				new VariableDeclarationExpr(new PrimitiveType(Primitive.BOOLEAN),
						replacementVariable.getNameAsString()),
				callExtractor, Operator.ASSIGN);
		return methodCallAssignment;
	}

	private AssignExpr addAssignType(Node methodCallParent, Expression callExtractor) {
		AssignExpr methodCallAssignment = new AssignExpr(new VariableDeclarationExpr(methodCallParent
				.getAncestorOfType(com.github.javaparser.ast.body.MethodDeclaration.class).get().getType(),
				replacementVariable.getNameAsString()), callExtractor, Operator.ASSIGN);
		return methodCallAssignment;
	}
}
