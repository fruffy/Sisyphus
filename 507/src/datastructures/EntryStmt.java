package datastructures;

import com.github.javaparser.Range;
import com.github.javaparser.ast.AllFieldsConstructor;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.comments.LineComment;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.visitor.CloneVisitor;
import com.github.javaparser.ast.visitor.GenericVisitor;
import com.github.javaparser.ast.visitor.VoidVisitor;

public class EntryStmt extends Statement {
	LineComment label;
	@AllFieldsConstructor
	public EntryStmt() {
		this(null);
		label = new LineComment("*Entry*");
	}

	public EntryStmt(Range range) {
		super(range);
	}

	@Override
	public <R, A> R accept(final GenericVisitor<R, A> v, final A arg) {
		return label.accept(v, arg);
	}

	@Override
	public <A> void accept(final VoidVisitor<A> v, final A arg) {
		System.out.print(label.getContent());
	}

	@Override
	public boolean remove(Node node) {
		if (node == null)
			return false;
		return super.remove(node);
	}

	@Override
	public EntryStmt clone() {
		return (EntryStmt) accept(new CloneVisitor(), null);
	}

}