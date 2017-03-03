package core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.symbolsolver.javaparsermodel.JavaParserFacade;
import com.github.javaparser.symbolsolver.model.typesystem.ReferenceType;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JarTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;

public class Testing {
	static class TypeCalculatorVisitor extends VoidVisitorAdapter<JavaParserFacade> {
		@Override
		public void visit(ReturnStmt n, JavaParserFacade javaParserFacade) {
			super.visit(n, javaParserFacade);
			// System.out.println(n.getExpr().toString() + " has type " +
			// javaParserFacade.getType(n.getExpr()));
		}

		@Override
		public void visit(MethodCallExpr n, JavaParserFacade javaParserFacade) {
			System.out.println(n);
			super.visit(n, javaParserFacade);
			System.out.println(n.toString() + " has type " + javaParserFacade.getType(n).describe());
			if (javaParserFacade.getType(n).isReferenceType()) {
				for (ReferenceType ancestor : javaParserFacade.getType(n).asReferenceType().getAllAncestors()) {
					// System.out.println("Ancestor " + ancestor.describe());
				}
			}
		}
	}

	public static void main(String[] args) throws FileNotFoundException {
		File inputFile = new File("src/examples/JavaTest.java");
		CombinedTypeSolver typeSolver = new CombinedTypeSolver(new ReflectionTypeSolver());
		CompilationUnit agendaCu = JavaParser.parse(inputFile);

		agendaCu.accept(new TypeCalculatorVisitor(), JavaParserFacade.get(typeSolver));

	}
}
