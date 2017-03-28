package matching;

import java.util.ArrayList;
import java.util.List;

import datastructures.Method;

public class ASTDetector extends CloneDetector {

	public ASTDetector(List<Method> libMethods) {
		super(libMethods);
		// TODO Auto-generated constructor stub
	}

	/*
	 * Checks if the abstract syntax tree of the body of method 1 is the same as
	 * that of method2
	 */
	private boolean matchMethods(Method method1, Method method2) {
		if (method1.getBody().equals(method2.getBody())) {
			return true;
		}
		return false;
	}

	/**
	 * Takes a list of Method objects and matches it against a reference library
	 * Returns a list of matched methods. Compare AST's of methods
	 * 
	 * @param srcMethods
	 * @return
	 */
	public List<Method[]> findSimiliarMethods(List<Method> srcMethods) {

		List<Method[]> matchedMethods = new ArrayList<Method[]>();
		if (methodLibrary == null) {
			System.out.println("Warning: Method library not initialised, nothing to compare to!");
			return matchedMethods;
		}
		for (Method src : srcMethods) {
			for (Method ref : methodLibrary) {
				if (matchMethods(src, ref)) {
					Method[] matched = { src, ref };
					/*
					 * System.out.println("Match! " + src.getSignature() +
					 * " with return type " + src.getReturnType() +
					 * " can be replaced by " + ref.getSignature() +
					 * " with return type " + ref.getReturnType());
					 */

					matchedMethods.add(matched);
				}

			}
		}
		return matchedMethods;

	}
}
