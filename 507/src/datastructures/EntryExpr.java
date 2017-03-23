package datastructures;

import com.github.javaparser.Range;
import com.github.javaparser.ast.AllFieldsConstructor;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.visitor.CloneVisitor;
import com.github.javaparser.ast.visitor.GenericVisitor;
import com.github.javaparser.ast.visitor.VoidVisitor;

public class EntryExpr extends Expression {
	NameExpr label;
	@AllFieldsConstructor
	public EntryExpr() {
		this(null);
		label = new NameExpr("*Entry*");
	}

	public EntryExpr(Range range) {
		super(range);
	}

	@Override
	public <R, A> R accept(final GenericVisitor<R, A> v, final A arg) {		
		return label.accept(v, arg);
	}

	@Override
	public <A> void accept(final VoidVisitor<A> v, final A arg) {
		System.out.print(label.getNameAsString());
	}

	@Override
	public boolean remove(Node node) {
		if (node == null)
			return false;
		return super.remove(node);
	}

	@Override
	public EntryExpr clone() {
		return (EntryExpr) accept(new CloneVisitor(), null);
	}

}
