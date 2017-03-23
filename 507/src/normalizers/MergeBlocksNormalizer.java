package normalizers;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.Statement;
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
			BlockStmt ret = new BlockStmt(); 
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
							ret.addStatement(subStmt);
						}
					}
					else{
						ret.addStatement(newStmt);
					}
				}
			}

			return ret;
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
		this.startBlock.accept(new MergeBlocksVisitor(), null);
		return this.startBlock;
	}

}
