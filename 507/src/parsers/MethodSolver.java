package parsers;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;

import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.symbolsolver.javaparsermodel.JavaParserFacade;
import com.github.javaparser.symbolsolver.javaparsermodel.declarations.JavaParserMethodDeclaration;
import com.github.javaparser.symbolsolver.model.declarations.MethodDeclaration;
import com.github.javaparser.symbolsolver.model.resolution.SymbolReference;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;

public class MethodSolver {
	CombinedTypeSolver typeSolver;
	BlockStmt methodBody;
	HashMap<MethodCallExpr, Optional<BlockStmt>> bodyMap;

	public MethodSolver(BlockStmt methodBody) {
		this.methodBody = methodBody;
		this.typeSolver = new CombinedTypeSolver();
		// typeSolver.add(new JavaParserTypeSolver(new
		// File("C:\\Projects\\CPSC_507\\src")));
		this.typeSolver.add(new ReflectionTypeSolver());
		bodyMap = new HashMap<MethodCallExpr, Optional<BlockStmt>>();
		processMethod();
	}

	class TypeCalculatorVisitor extends VoidVisitorAdapter<JavaParserFacade> {

		@Override
		public void visit(MethodCallExpr methodCallExpr, JavaParserFacade facade) {
			try {
				modifyMethodName(methodCallExpr, facade);
			} catch (Exception e) {
				System.out.println("ERROR " + e.getMessage());
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}
	}

	private void modifyMethodName(MethodCallExpr methodCallExpr,
			JavaParserFacade facade) {
		SymbolReference<MethodDeclaration> methodRef = facade.solve(methodCallExpr);
		Optional<BlockStmt> methodBdy = null;
		if (methodRef.isSolved()) {
			MethodDeclaration methodDecl = methodRef.getCorrespondingDeclaration();

			for (MethodDeclaration test : methodDecl.declaringType().getDeclaredMethods()) {
				if (test instanceof JavaParserMethodDeclaration) {
					if (test.getQualifiedName().equals(methodDecl.getQualifiedName())) {
						// System.out.println(((JavaParserMethodDeclaration)
						// test).getWrappedNode());
						methodBdy = ((JavaParserMethodDeclaration) test).getWrappedNode().getBody();
					}
				}
			}
			methodCallExpr.removeScope();
			methodCallExpr.setName(methodDecl.getQualifiedName());

			if (methodBdy != null && methodBdy.isPresent()) {
				this.bodyMap.put(methodCallExpr, methodBdy);
			}

		} else {
			System.out.println("???");
		}
	}

	private void processMethod() {
		this.methodBody.accept(new TypeCalculatorVisitor(), JavaParserFacade.get(typeSolver));
		if (this.bodyMap != null) {
			Set<MethodCallExpr> bodySet = this.bodyMap.keySet();
			for (MethodCallExpr b : bodySet) {
				System.out.println("BEFORE"+ methodBody);

				b.setAsParentNodeOf(this.bodyMap.get(b).get());
				System.out.println("AFTER"+ methodBody);
			}
		}

	}

}