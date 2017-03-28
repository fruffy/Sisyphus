package parsers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import datastructures.Method;

/*
 * The parser class
 * Takes a filename and attempts to parse it
 * Right now we only have a generic file reader
 * 
*/

public class SyntaxParser {

	private ArrayList<MethodDeclaration> methodDeclarationList;

	public SyntaxParser(File inputFile) throws FileNotFoundException {
		this.methodDeclarationList = this.getMethodDeclaration(inputFile);
	}

	public SyntaxParser(List<File> inputFiles) throws FileNotFoundException {
		this.methodDeclarationList = this.getMethodDeclarations(inputFiles);
	}

	/*
	 * Returns an arraylist of all MethodCallExpr objects from which we can gain
	 * information about the methods in a file
	 */
	private ArrayList<MethodDeclaration> getMethodDeclaration(File inputFile) throws FileNotFoundException {

		ArrayList<MethodDeclaration> methodDecList = new ArrayList<MethodDeclaration>();
		CompilationUnit compilationUnit = JavaParser.parse(inputFile);
		new VoidVisitorAdapter<Object>() {
			@Override
			public void visit(MethodDeclaration n, Object arg) {
				super.visit(n, arg);
				if (n.getBody().isPresent()) {
					methodDecList.add(n);
				}
			}
		}.visit(compilationUnit, null);
		return methodDecList;
	}

	/*
	 * Returns an arraylist of all MethodCallExpr objects from which we can gain
	 * information about the methods in a file
	 */
	private ArrayList<MethodDeclaration> getMethodDeclarations(List<File> inputFiles) throws FileNotFoundException {
		ArrayList<MethodDeclaration> methodDecList = new ArrayList<MethodDeclaration>();
		for (File file : inputFiles) {
			CompilationUnit compilationUnit = JavaParser.parse(file);
			new VoidVisitorAdapter<Object>() {
				@Override
				public void visit(MethodDeclaration n, Object arg) {
					super.visit(n, arg);
					if (n.getBody().isPresent()) {
						methodDecList.add(n);
					}
				}
			}.visit(compilationUnit, null);
		}
		return methodDecList;
	}

	/*
	 * Returns an arraylist of Method objects from parsing information
	 */
	public ArrayList<Method> getMethods() {
		ArrayList<Method> methodList = new ArrayList<Method>();
		for (MethodDeclaration n : this.methodDeclarationList) {
			try {
				Method nMethod = new Method(n);
				methodList.add(nMethod);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return methodList;
	}
}