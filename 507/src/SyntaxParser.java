import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

/*
 * The parser class
 * Takes a filename and attempts to parse it
 * Right now we only have a generic file reader
 * 
*/

public class SyntaxParser {
	
	private CompilationUnit cu;
	private ArrayList<MethodDeclaration> methodDeclarationList;
	
	public SyntaxParser(String filename) throws FileNotFoundException{
		FileInputStream in = new FileInputStream(filename);	
		this.cu = JavaParser.parse(in);
		this.methodDeclarationList = this.getMethodDeclaration();
	}
	
	

	/********************************************************************************************/
	/*
	 * Functions imported from the javaparser lib
	 */
	public void parse() {
		new VoidVisitorAdapter<Object>() {
			@Override
			public void visit(MethodCallExpr n, Object arg) {
				super.visit(n, arg);
				System.out.println(" [L " + n.getBegin() + "] " + n);
			}
		}.visit(this.cu, null);
			
	}
	
	/*Returns an arraylist of all MethodCallExpr objects from which
	 * we can gain information about the methods in a file
	 */
	public ArrayList<MethodDeclaration> getMethodDeclaration(){
		ArrayList<MethodDeclaration> methodDecList = new ArrayList<MethodDeclaration>();
		new VoidVisitorAdapter<Object>() {
			@Override
			public void visit(MethodDeclaration n, Object arg) {
				super.visit(n, arg);
				methodDecList.add(n);
				//System.out.println(n.getName());
			}
		}.visit(this.cu, null);
		return methodDecList;
	}
	
	
	/*Returns an arraylist of method names in the class*/
	public ArrayList<String> getMethodNames() {
		ArrayList<String> methodList = new ArrayList<String> ();
		for(MethodDeclaration call: this.methodDeclarationList){
			methodList.add(call.getNameAsString());
		}
		return methodList;
	}

	/*Returns an arraylist of method return types in the class*/
	public ArrayList<String> getReturnTypes(){
		ArrayList<String> returnTypes = new ArrayList<String>();
		for(MethodDeclaration call: this.methodDeclarationList){
			String methodName = call.getNameAsString();
			String stringDeclaration = call.getDeclarationAsString();
			String[] splitedDeclaration = stringDeclaration.split("\\s");
			for(int i = 0; i<splitedDeclaration.length; i++){
				//System.out.println(splitedDeclaration[i]);
				int openParenIndex = splitedDeclaration[i].indexOf('(');
				if(openParenIndex>-1){
					if(methodName.compareTo(splitedDeclaration[i].substring(0,openParenIndex))==0){
						returnTypes.add(splitedDeclaration[i-1]);
					}
				}
			}
			
		}
		return returnTypes;
	}	
	
	
}