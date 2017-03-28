package matching;

import java.util.HashMap;
import java.util.List;

import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.type.Type;

import datastructures.Method;

/*
 * The detector class
 * Takes the parser output and tries to find similarities
 * Right now we only have a simple line by line comparison of a full file
 * 
*/

public abstract class CloneDetector {
	protected List<Method> methodLibrary;
	protected double mismatch;
	protected double match;

	public CloneDetector(List<Method> libMethods) {
		this.methodLibrary = libMethods;
	}

	protected boolean checkParameters(List<Parameter> params1, List<Parameter> params2) {
		HashMap<Type, Integer> map1 = new HashMap<Type, Integer>();
		HashMap<Type, Integer> map2 = new HashMap<Type, Integer>();

		for (Parameter p1 : params1) {
			if (map1.containsKey(p1.getType())) {
				map1.put(p1.getType(), map1.get(p1.getType()) + 1);
			}
			map1.put(p1.getType(), 0);
		}

		for (Parameter p2 : params2) {
			if (map2.containsKey(p2.getType())) {
				map2.put(p2.getType(), map2.get(p2.getType()) + 1);
			}
			map2.put(p2.getType(), 0);
		}

		if (map1.size() != map2.size()) {
			return false;
		}

		for (Type key1 : map1.keySet()) {
			if (!map2.containsKey(key1)) {
				return false;
			}
			if (map1.get(key1) != map2.get(key1)) {
				return false;
			}
		}
		return true;
	}
}
