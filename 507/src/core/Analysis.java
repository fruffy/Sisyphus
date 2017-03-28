package core;

import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.type.Type;

import datastructures.Method;

public class Analysis {
	private ArrayList<Method[]> matches;
	private int numSrcMethods;
	private int numLibMethods;

	public Analysis(ArrayList<Method[]> matches, int numSrcMethods, int numLibMethods) {
		this.matches = matches;
		this.numSrcMethods = numSrcMethods;
		this.numLibMethods = numLibMethods;
	}

	/**
	 * Calculate the true positives and false positives
	 * 
	 * @return
	 */
	public int[] tpfp() {
		int countTP = 0;
		int countFP = 0;
		int countTN = 0;
		int countFN = 0;
		for (int i = 0; i < this.matches.size(); i++) {
			Method[] match = this.matches.get(i);
			Method srcMatch = match[0];
			String srcName = srcMatch.getMethodName();
			List<Parameter> srcParams = srcMatch.getMethodParameters();
			Type srcReturnType = srcMatch.getReturnType();
			Method libMatch = match[1];
			String libName = libMatch.getMethodName();
			List<Parameter> libParams = libMatch.getMethodParameters();
			Type libReturnType = libMatch.getReturnType();
			boolean trueMatch = false;
			if (srcReturnType.equals(libReturnType) && srcParams.size() == libParams.size()) {
				if (srcName.contains("absoluteValue") && libName.equals("abs")) {
					trueMatch = true;
				} else if (srcName.contains("maximum") && libName.equals("max")) {
					trueMatch = true;
				} else if (srcName.contains("minimum") && libName.equals("min")) {
					trueMatch = true;
				} else if (srcName.contains("power") && libName.equals("pow")) {
					trueMatch = true;
				} else if (srcName.contains("swap") && libName.equals("swap")) {
					trueMatch = true;
				} else if (srcName.contains("equals") && libName.equals("equals")
						&& libParams.get(0).getType().equals(srcParams.get(0).getType())) {
					trueMatch = true;
				} else if (srcName.contains("fillArray") && libName.equals("fill")
						&& libParams.get(0).getType().equals(srcParams.get(0).getType())) {
					trueMatch = true;
				} else if (srcName.contains("fillArrayPartially") && libName.equals("fill")
						&& libParams.get(0).getType().equals(srcParams.get(0).getType())) {
					trueMatch = true;
				} else if (srcName.contains("returnCopy") && libName.equals("copyOf")) {
					trueMatch = true;
				} else if (srcName.contains("returnCopyRange") && libName.equals("copyOfRange")) {
					trueMatch = true;
				} else if (srcName.contains("convertToList") && libName.equals("asList")) {
					trueMatch = true;
				} else if (srcName.contains("arrToString") && libName.equals("toString")
						&& libParams.get(0).getType().equals(srcParams.get(0).getType())) {
					trueMatch = true;
				}
				if (trueMatch) {
					/*
					 * System.out.println("True Match: "+ srcReturnType+" "
					 * +srcName +", "+ libReturnType+" "+libName);
					 */
					countTP++;
				} else {
					countFP++;
				}

			} else {
				countFP++;
			}

		}
		countTN = this.numSrcMethods * this.numLibMethods - this.numSrcMethods;
		countFN = this.numSrcMethods - countTP;
		int[] result = { countTP, countFP, countTN, countFN };
		return result;
	}
}