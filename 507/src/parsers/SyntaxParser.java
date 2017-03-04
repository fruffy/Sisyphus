package parsers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.symbolsolver.javaparsermodel.JavaParserFacade;
import com.github.javaparser.symbolsolver.model.declarations.Declaration;
import com.github.javaparser.symbolsolver.model.declarations.FieldDeclaration;
import com.github.javaparser.symbolsolver.model.declarations.TypeParameterDeclaration;
import com.github.javaparser.symbolsolver.model.methods.MethodUsage;
import com.github.javaparser.symbolsolver.model.resolution.SymbolReference;
import com.github.javaparser.symbolsolver.model.typesystem.ReferenceType;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;

import core.Method;

/*
 * The parser class
 * Takes a filename and attempts to parse it
 * Right now we only have a generic file reader
 * 
*/

public class SyntaxParser {

	private CompilationUnit cu;
	private ArrayList<MethodDeclaration> methodDeclarationList;
	private ArrayList<MethodCallExpr> MethodCallExprList;

	private CombinedTypeSolver t;

	public SyntaxParser(File inputFile) throws FileNotFoundException {
		this.t = new CombinedTypeSolver(new ReflectionTypeSolver());
		this.cu = JavaParser.parse(inputFile);
		this.methodDeclarationList = this.getMethodDeclaration();
		this.MethodCallExprList = this.getMethodInvocation();
		processJavaFile1(inputFile);
		// processJavaFile( JavaParserFacade.get(this.t));

	}

	/********************************************************************************************/

	class TypeCalculatorVisitor extends VoidVisitorAdapter<JavaParserFacade> {

		@Override
		public void visit(MethodCallExpr n, JavaParserFacade javaParserFacade) {
			super.visit(n, javaParserFacade);
			com.github.javaparser.symbolsolver.model.declarations.MethodDeclaration nest = javaParserFacade.solve(n).getCorrespondingDeclaration();
			System.out.println(n.toString() + " has type " + nest.getSignature());

			for (TypeParameterDeclaration test : nest.getTypeParameters()) {
				System.out.println(n.getNameAsString() + " is " + test.getQualifiedName());
			}

		}
	}

	public void processJavaFile1(File inputFile) throws FileNotFoundException {
		//CombinedTypeSolver typeSolver = new CombinedTypeSolver(new ReflectionTypeSolver());
		CombinedTypeSolver typeSolver = new CombinedTypeSolver(new JavaParserTypeSolver(new File("./reference/")));
		//typeSolver.add(new JavaParserTypeSolver(new File("StrictMath.class")));
		//typeSolver.add(new ReflectionTypeSolver());
		//typeSolver.add(new JavaParserTypeSolver(new File("C:\\Projects\\CPSC_507")));

		CompilationUnit agendaCu = JavaParser.parse(inputFile);

		agendaCu.accept(new TypeCalculatorVisitor(), JavaParserFacade.get(typeSolver));

	}
	// TODO: Convert to Java Code

	public void processJavaFile(JavaParserFacade facade) {

		ArrayList<MethodCallExpr> methodInvocations = getMethodInvocations();

		for (MethodCallExpr methodCallExpr : methodInvocations) {
			// System.out.println(methodCallExpr);

			try {
				SymbolReference<com.github.javaparser.symbolsolver.model.declarations.MethodDeclaration> methodRef = facade
						.solve(methodCallExpr);
				if (methodRef.isSolved()) {
					com.github.javaparser.symbolsolver.model.declarations.MethodDeclaration methodDecl = methodRef
							.getCorrespondingDeclaration();
					System.out.println("  -> " + methodDecl.getQualifiedSignature());
				} else {
					System.out.println("???");
				}
			} catch (Exception e) {
				System.out.println(" ERR ${e.message}");
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}
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

	/*
	 * Returns an arraylist of all MethodCallExpr objects from which we can gain
	 * information about the methods in a file
	 */
	public ArrayList<MethodDeclaration> getMethodDeclaration() {
		ArrayList<MethodDeclaration> methodDecList = new ArrayList<MethodDeclaration>();
		new VoidVisitorAdapter<Object>() {
			@Override
			public void visit(MethodDeclaration n, Object arg) {
				super.visit(n, arg);
				methodDecList.add(n);
				// System.out.println(n.getName());
			}
		}.visit(this.cu, null);
		return methodDecList;
	}

	/*
	 * Returns an arraylist of all MethodCallExpr objects from which we can gain
	 * information about the methods in a file
	 */
	public ArrayList<MethodCallExpr> getMethodInvocation() {
		ArrayList<MethodCallExpr> methodCallList = new ArrayList<MethodCallExpr>();
		new VoidVisitorAdapter<Object>() {
			@Override
			public void visit(MethodCallExpr n, Object arg) {
				super.visit(n, arg);
				methodCallList.add(n);
				// System.out.println(n.getName());
			}
		}.visit(this.cu, null);
		return methodCallList;
	}

	/*
	 * Returns an arraylist of Method objects from parsing information
	 */
	public ArrayList<Method> getMethods() {
		ArrayList<Method> methodList = new ArrayList<Method>();
		for (MethodDeclaration n : this.methodDeclarationList) {
			methodList.add(new Method(n));
		}
		return methodList;
	}

	/* Returns an arraylist of method names in the class */
	public ArrayList<String> getMethodNames() {
		ArrayList<Method> methodList = this.getMethods();
		ArrayList<String> methodNames = new ArrayList<String>();
		for (Method call : methodList) {
			methodNames.add(call.getMethodName());
		}
		return methodNames;
	}

	/* Returns an arraylist of method names in the class */
	public ArrayList<MethodCallExpr> getMethodInvocations() {
		return MethodCallExprList;
	}

	/* Returns an arraylist of method return types in the class */
	public ArrayList<Type> getReturnTypes() {
		ArrayList<Method> methodList = this.getMethods();
		ArrayList<Type> returnTypes = new ArrayList<Type>();
		for (Method call : methodList) {
			returnTypes.add(call.getReturnType());
		}
		return returnTypes;
	}
}