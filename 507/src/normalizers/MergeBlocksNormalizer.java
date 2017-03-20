package normalizers;

import java.util.LinkedList;
import java.util.Optional;

import com.github.javaparser.Range;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.comments.Comment;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.expr.BooleanLiteralExpr;
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
import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.github.javaparser.ast.visitor.Visitable;

public class MergeBlocksNormalizer extends Normalizer {



	private class MergeBlocksVisitor extends ModifierVisitor<Object>{
		
		int freshNum = 0;
		
		private String freshPrefix(){
			freshNum++;
			return "____fresh" + freshNum;
		}

		public MergeBlocksVisitor(){

		}

		@Override
		public Visitable visit(BlockStmt n, Object arg) {
			NodeList<Statement> newStatementList = new NodeList<Statement>();
			for (Statement stmt : n.getStatements()){
				Statement newStmt = (Statement)stmt.accept(this, arg);
				if (newStmt != null) {
					//Merge if we have nested blocks
					//Because we've already called accept, we know it has no nested blocks inside it
					if (newStmt instanceof BlockStmt){
						VariableNameNormalizer freshener = new VariableNameNormalizer(freshPrefix());
						freshener.initialize(newStmt);
						BlockStmt freshened = (BlockStmt)(freshener.result());
						for (Statement subStmt : freshened.getStatements() ){
							newStatementList.add(subStmt);
						}
					}
					else{
						newStatementList.add(newStmt);
					}
				}
			}

			Comment comment = n.getComment();
			BlockStmt r = new BlockStmt(n.getRange().orElse(null), newStatementList);
			r.setComment(comment);
			return r;
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
		return (Node)this.startBlock.accept(new MergeBlocksVisitor(), null);
	}

}
