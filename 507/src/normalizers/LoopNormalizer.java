package normalizers;

import java.util.LinkedList;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.comments.Comment;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.DoStmt;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.ForeachStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.stmt.WhileStmt;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.ast.type.TypeParameter;
import com.github.javaparser.ast.visitor.CloneVisitor;
import com.github.javaparser.ast.visitor.Visitable;

public class LoopNormalizer extends Normalizer {

	private static BlockStmt mergeBlocks(Statement s1, Statement s2){
		NodeList<Statement> newStmts = new NodeList<Statement>();

		if (s1 instanceof BlockStmt && s2 instanceof BlockStmt){
			BlockStmt b1 = (BlockStmt) s1;
			BlockStmt b2 = (BlockStmt) s2;
			newStmts.addAll(b1.getStatements());
			newStmts.addAll(b2.getStatements());

		} else if (s1 instanceof BlockStmt){
			BlockStmt b1 = (BlockStmt) s1;
			newStmts.addAll(b1.getStatements());
			newStmts.add(s2);
		} else if (s2 instanceof BlockStmt){
			BlockStmt b2 = (BlockStmt) s2;
			newStmts.add(s1);
			newStmts.addAll(b2.getStatements());
		} else {
			newStmts.add(s1);
			newStmts.add(s2);

		}
		return new BlockStmt(newStmts);
	}

	private static BlockStmt exprBlock(NodeList<Expression> l){
		NodeList<Statement> newList = new NodeList<Statement>();
		for (Expression e : l){
			newList.add(new ExpressionStmt(e));
		}
		return new BlockStmt(newList);
	}

	private class FixLoopsVisitor extends CloneVisitor{

		public FixLoopsVisitor(){
			
		}

		@Override
		public Visitable visit(ForStmt n, Object arg) {
			//Clone this like before
			Statement body = cloneNode(n.getBody(), arg);
			Expression compare = cloneNode(n.getCompare(), arg);
			NodeList<Expression> initialization = cloneList(n.getInitialization(), arg);
			NodeList<Expression> update = cloneList(n.getUpdate(), arg);
			Comment comment = cloneNode(n.getComment(), arg);

			//While loop body is old body, plus the update at the end
			Statement newBody = mergeBlocks(body, exprBlock(update));
			WhileStmt loop = new WhileStmt(compare, newBody);

			//Finally, return a block with the initialization appended to the loop

			BlockStmt r = new BlockStmt();
			r.setStatements(mergeBlocks(exprBlock(initialization), loop).getStatements());
			r.setComment(comment);

			return r;
		}		
		@Override
		public Visitable visit(DoStmt n, Object arg) {
			Statement body = cloneNode(n.getBody(), arg);
			Expression condition = cloneNode(n.getCondition(), arg);
			Comment comment = cloneNode(n.getComment(), arg);

			//Make a while loop from our Do-While loop i.e. same cond and body
			WhileStmt loop = new WhileStmt(condition, body);

			//Finally, return a block with an initial body run appended to the loop

			BlockStmt r = new BlockStmt();
			r.setStatements(mergeBlocks(body, loop).getStatements());
			r.setComment(comment);

			return r;
		}
		
		
		//http://stackoverflow.com/questions/85190/how-does-the-java-for-each-loop-work
		@Override
	    public Visitable visit(ForeachStmt n, Object arg) {
	        Statement body = cloneNode(n.getBody(), arg);
	        Expression iterable = cloneNode(n.getIterable(), arg);
	        VariableDeclarationExpr variable = cloneNode(n.getVariable(), arg);
	        Comment comment = cloneNode(n.getComment(), arg);
	        
	        Type iterType = 
	        		new TypeParameter("Iterable", 
	        				new NodeList<ClassOrInterfaceType>(variable.getElementType()));
	        VariableDeclarationExpr iterDecl = new VariableDeclarationExpr(iterType, "__iter");
	        
	        //New body: Create variable with iter.next() assigned to it
	        Expression elemValue = new MethodCallExpr(new NameExpr("__iter"), "next");
	        Expression elemDecl = 
	        		new AssignExpr(variable, elemValue, AssignExpr.Operator.ASSIGN); 
	        //Then do the normal loop body
	        Statement newBody = mergeBlocks(new ExpressionStmt(elemDecl), body);
	        
	        //End condition, check if iter has next
	        Expression endCond = new MethodCallExpr(new NameExpr("__iter"), "hasNext");
	        
	        //Make the for loop doing each step of the for-each
	        ForStmt loop = 
	        		new ForStmt(new NodeList<Expression>(iterDecl),
	        					endCond, new NodeList<Expression>(), newBody);
	        
	        //Finally, turn our for-loop into a while loop and return
	        return visit(loop, arg);
	}

		private <N extends Node> NodeList<N> cloneList(NodeList<N> list, Object arg) {
			if (list == null) {
				return null;
			}
			return (NodeList<N>) list.accept(this, arg);
		}


	}

	@Override
	public Node result() {
		return (Node)this.startBlock.accept(new FixLoopsVisitor(), null);
	}

}
