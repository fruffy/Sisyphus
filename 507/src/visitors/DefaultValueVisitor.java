package visitors;

import com.github.javaparser.ast.ArrayCreationLevel;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.AnnotationDeclaration;
import com.github.javaparser.ast.body.AnnotationMemberDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.EmptyMemberDeclaration;
import com.github.javaparser.ast.body.EnumConstantDeclaration;
import com.github.javaparser.ast.body.EnumDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.InitializerDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.comments.BlockComment;
import com.github.javaparser.ast.comments.JavadocComment;
import com.github.javaparser.ast.comments.LineComment;
import com.github.javaparser.ast.expr.ArrayAccessExpr;
import com.github.javaparser.ast.expr.ArrayCreationExpr;
import com.github.javaparser.ast.expr.ArrayInitializerExpr;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.BooleanLiteralExpr;
import com.github.javaparser.ast.expr.CastExpr;
import com.github.javaparser.ast.expr.CharLiteralExpr;
import com.github.javaparser.ast.expr.ClassExpr;
import com.github.javaparser.ast.expr.ConditionalExpr;
import com.github.javaparser.ast.expr.DoubleLiteralExpr;
import com.github.javaparser.ast.expr.EnclosedExpr;
import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.expr.InstanceOfExpr;
import com.github.javaparser.ast.expr.IntegerLiteralExpr;
import com.github.javaparser.ast.expr.LambdaExpr;
import com.github.javaparser.ast.expr.LongLiteralExpr;
import com.github.javaparser.ast.expr.MarkerAnnotationExpr;
import com.github.javaparser.ast.expr.MemberValuePair;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.MethodReferenceExpr;
import com.github.javaparser.ast.expr.Name;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.NormalAnnotationExpr;
import com.github.javaparser.ast.expr.NullLiteralExpr;
import com.github.javaparser.ast.expr.ObjectCreationExpr;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.expr.SingleMemberAnnotationExpr;
import com.github.javaparser.ast.expr.StringLiteralExpr;
import com.github.javaparser.ast.expr.SuperExpr;
import com.github.javaparser.ast.expr.ThisExpr;
import com.github.javaparser.ast.expr.TypeExpr;
import com.github.javaparser.ast.expr.UnaryExpr;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.stmt.AssertStmt;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.BreakStmt;
import com.github.javaparser.ast.stmt.CatchClause;
import com.github.javaparser.ast.stmt.ContinueStmt;
import com.github.javaparser.ast.stmt.DoStmt;
import com.github.javaparser.ast.stmt.EmptyStmt;
import com.github.javaparser.ast.stmt.ExplicitConstructorInvocationStmt;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.ForeachStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.LabeledStmt;
import com.github.javaparser.ast.stmt.LocalClassDeclarationStmt;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.stmt.SwitchEntryStmt;
import com.github.javaparser.ast.stmt.SwitchStmt;
import com.github.javaparser.ast.stmt.SynchronizedStmt;
import com.github.javaparser.ast.stmt.ThrowStmt;
import com.github.javaparser.ast.stmt.TryStmt;
import com.github.javaparser.ast.stmt.WhileStmt;
import com.github.javaparser.ast.type.ArrayType;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.IntersectionType;
import com.github.javaparser.ast.type.PrimitiveType;
import com.github.javaparser.ast.type.TypeParameter;
import com.github.javaparser.ast.type.UnionType;
import com.github.javaparser.ast.type.UnknownType;
import com.github.javaparser.ast.type.VoidType;
import com.github.javaparser.ast.type.WildcardType;
import com.github.javaparser.ast.visitor.GenericVisitor;

/**
 * A visitor that returns the same value for each node. The purpose of this is
 * to provide a "default" value which can be extended to return a specific value
 * for some nodes, and the default in others.
 *
 * @param <Ret>
 *            The type returned by the visitor
 */
public class DefaultValueVisitor<Ret> implements GenericVisitor<Ret, Object> {

	public final Ret defaultValue;

	public DefaultValueVisitor(Ret defaultValue) {
		this.defaultValue = defaultValue;
	}

	@Override
	public Ret visit(CompilationUnit n, Object arg) {

		return defaultValue;
	}

	@Override
	public Ret visit(PackageDeclaration n, Object arg) {

		return defaultValue;
	}

	@Override
	public Ret visit(TypeParameter n, Object arg) {

		return defaultValue;
	}

	@Override
	public Ret visit(LineComment n, Object arg) {

		return defaultValue;
	}

	@Override
	public Ret visit(BlockComment n, Object arg) {

		return defaultValue;
	}

	@Override
	public Ret visit(ClassOrInterfaceDeclaration n, Object arg) {

		return defaultValue;
	}

	@Override
	public Ret visit(EnumDeclaration n, Object arg) {

		return defaultValue;
	}

	@Override
	public Ret visit(EnumConstantDeclaration n, Object arg) {

		return defaultValue;
	}

	@Override
	public Ret visit(AnnotationDeclaration n, Object arg) {

		return defaultValue;
	}

	@Override
	public Ret visit(AnnotationMemberDeclaration n, Object arg) {

		return defaultValue;
	}

	@Override
	public Ret visit(FieldDeclaration n, Object arg) {

		return defaultValue;
	}

	@Override
	public Ret visit(VariableDeclarator n, Object arg) {

		return defaultValue;
	}

	@Override
	public Ret visit(ConstructorDeclaration n, Object arg) {

		return defaultValue;
	}

	@Override
	public Ret visit(MethodDeclaration n, Object arg) {

		return defaultValue;
	}

	@Override
	public Ret visit(Parameter n, Object arg) {

		return defaultValue;
	}

	@Override
	public Ret visit(EmptyMemberDeclaration n, Object arg) {

		return defaultValue;
	}

	@Override
	public Ret visit(InitializerDeclaration n, Object arg) {

		return defaultValue;
	}

	@Override
	public Ret visit(JavadocComment n, Object arg) {

		return defaultValue;
	}

	@Override
	public Ret visit(ClassOrInterfaceType n, Object arg) {

		return defaultValue;
	}

	@Override
	public Ret visit(PrimitiveType n, Object arg) {

		return defaultValue;
	}

	@Override
	public Ret visit(ArrayType n, Object arg) {

		return defaultValue;
	}

	@Override
	public Ret visit(ArrayCreationLevel n, Object arg) {

		return defaultValue;
	}

	@Override
	public Ret visit(IntersectionType n, Object arg) {

		return defaultValue;
	}

	@Override
	public Ret visit(UnionType n, Object arg) {

		return defaultValue;
	}

	@Override
	public Ret visit(VoidType n, Object arg) {

		return defaultValue;
	}

	@Override
	public Ret visit(WildcardType n, Object arg) {

		return defaultValue;
	}

	@Override
	public Ret visit(UnknownType n, Object arg) {

		return defaultValue;
	}

	@Override
	public Ret visit(ArrayAccessExpr n, Object arg) {

		return defaultValue;
	}

	@Override
	public Ret visit(ArrayCreationExpr n, Object arg) {

		return defaultValue;
	}

	@Override
	public Ret visit(ArrayInitializerExpr n, Object arg) {

		return defaultValue;
	}

	@Override
	public Ret visit(AssignExpr n, Object arg) {

		return defaultValue;
	}

	@Override
	public Ret visit(BinaryExpr n, Object arg) {

		return defaultValue;
	}

	@Override
	public Ret visit(CastExpr n, Object arg) {

		return defaultValue;
	}

	@Override
	public Ret visit(ClassExpr n, Object arg) {

		return defaultValue;
	}

	@Override
	public Ret visit(ConditionalExpr n, Object arg) {

		return defaultValue;
	}

	@Override
	public Ret visit(EnclosedExpr n, Object arg) {

		return defaultValue;
	}

	@Override
	public Ret visit(FieldAccessExpr n, Object arg) {

		return defaultValue;
	}

	@Override
	public Ret visit(InstanceOfExpr n, Object arg) {

		return defaultValue;
	}

	@Override
	public Ret visit(StringLiteralExpr n, Object arg) {

		return defaultValue;
	}

	@Override
	public Ret visit(IntegerLiteralExpr n, Object arg) {

		return defaultValue;
	}

	@Override
	public Ret visit(LongLiteralExpr n, Object arg) {

		return defaultValue;
	}

	@Override
	public Ret visit(CharLiteralExpr n, Object arg) {

		return defaultValue;
	}

	@Override
	public Ret visit(DoubleLiteralExpr n, Object arg) {

		return defaultValue;
	}

	@Override
	public Ret visit(BooleanLiteralExpr n, Object arg) {

		return defaultValue;
	}

	@Override
	public Ret visit(NullLiteralExpr n, Object arg) {

		return defaultValue;
	}

	@Override
	public Ret visit(MethodCallExpr n, Object arg) {

		return defaultValue;
	}

	@Override
	public Ret visit(NameExpr n, Object arg) {

		return defaultValue;
	}

	@Override
	public Ret visit(ObjectCreationExpr n, Object arg) {

		return defaultValue;
	}

	@Override
	public Ret visit(ThisExpr n, Object arg) {

		return defaultValue;
	}

	@Override
	public Ret visit(SuperExpr n, Object arg) {

		return defaultValue;
	}

	@Override
	public Ret visit(UnaryExpr n, Object arg) {

		return defaultValue;
	}

	@Override
	public Ret visit(VariableDeclarationExpr n, Object arg) {

		return defaultValue;
	}

	@Override
	public Ret visit(MarkerAnnotationExpr n, Object arg) {

		return defaultValue;
	}

	@Override
	public Ret visit(SingleMemberAnnotationExpr n, Object arg) {

		return defaultValue;
	}

	@Override
	public Ret visit(NormalAnnotationExpr n, Object arg) {

		return defaultValue;
	}

	@Override
	public Ret visit(MemberValuePair n, Object arg) {

		return defaultValue;
	}

	@Override
	public Ret visit(ExplicitConstructorInvocationStmt n, Object arg) {

		return defaultValue;
	}

	@Override
	public Ret visit(LocalClassDeclarationStmt n, Object arg) {

		return defaultValue;
	}

	@Override
	public Ret visit(AssertStmt n, Object arg) {

		return defaultValue;
	}

	@Override
	public Ret visit(BlockStmt n, Object arg) {

		return defaultValue;
	}

	@Override
	public Ret visit(LabeledStmt n, Object arg) {

		return defaultValue;
	}

	@Override
	public Ret visit(EmptyStmt n, Object arg) {

		return defaultValue;
	}

	@Override
	public Ret visit(ExpressionStmt n, Object arg) {

		return defaultValue;
	}

	@Override
	public Ret visit(SwitchStmt n, Object arg) {

		return defaultValue;
	}

	@Override
	public Ret visit(SwitchEntryStmt n, Object arg) {

		return defaultValue;
	}

	@Override
	public Ret visit(BreakStmt n, Object arg) {

		return defaultValue;
	}

	@Override
	public Ret visit(ReturnStmt n, Object arg) {

		return defaultValue;
	}

	@Override
	public Ret visit(IfStmt n, Object arg) {

		return defaultValue;
	}

	@Override
	public Ret visit(WhileStmt n, Object arg) {

		return defaultValue;
	}

	@Override
	public Ret visit(ContinueStmt n, Object arg) {

		return defaultValue;
	}

	@Override
	public Ret visit(DoStmt n, Object arg) {

		return defaultValue;
	}

	@Override
	public Ret visit(ForeachStmt n, Object arg) {

		return defaultValue;
	}

	@Override
	public Ret visit(ForStmt n, Object arg) {

		return defaultValue;
	}

	@Override
	public Ret visit(ThrowStmt n, Object arg) {

		return defaultValue;
	}

	@Override
	public Ret visit(SynchronizedStmt n, Object arg) {

		return defaultValue;
	}

	@Override
	public Ret visit(TryStmt n, Object arg) {

		return defaultValue;
	}

	@Override
	public Ret visit(CatchClause n, Object arg) {

		return defaultValue;
	}

	@Override
	public Ret visit(LambdaExpr n, Object arg) {

		return defaultValue;
	}

	@Override
	public Ret visit(MethodReferenceExpr n, Object arg) {

		return defaultValue;
	}

	@Override
	public Ret visit(TypeExpr n, Object arg) {

		return defaultValue;
	}

	@Override
	public Ret visit(NodeList n, Object arg) {

		return defaultValue;
	}

	@Override
	public Ret visit(Name n, Object arg) {

		return defaultValue;
	}

	@Override
	public Ret visit(SimpleName n, Object arg) {

		return defaultValue;
	}

	@Override
	public Ret visit(ImportDeclaration n, Object arg) {

		return defaultValue;
	}

}
