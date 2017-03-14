package core;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import parsers.SyntaxParser;

public class CodeHelper {
	public static void main(String[] args) {
		
		String srcName = "src/examples/TestCode.java";
		File libFile = new File("Library.java");
		File srcfile = new File(srcName);

		SyntaxParser libparser;
		SyntaxParser srcparser;
		ArrayList<Method> srcMethods, libMethods;

		if (!(srcfile.exists()) || srcfile.isDirectory()) {
			System.out.println("Input file does not exist or is not a valid input.");
			// Debug
			/* System.out.println("Working Directory = " +
			 System.getProperty("user.dir"));*/
			return;
		}
		if (!(libFile.exists()) || libFile.isDirectory()) {
			System.out.println("Error reading reference library.");
			// Debug
			// System.out.println("Working Directory = " +
			// System.getProperty("user.dir"));
			return;
		}

		// initialize java parser for both library and source code.
		try {
			libparser = new SyntaxParser(libFile);
			srcparser = new SyntaxParser(srcfile);
		} catch (IOException e) {
			new RuntimeException(e);
			return;
		}

		// testing(srcparser, libparser);
		srcMethods = srcparser.getMethods();
		libMethods = libparser.getMethods();

		CloneDetector cloneDetect = new CloneDetector(libparser.getMethods());

		System.out.println("ANALYSIS: PDG");
		ArrayList<Method[]> matchesPDG = (ArrayList<Method[]>) cloneDetect.findSimiliarMethodsPDG(srcMethods);
		/*for(Method[] match:matchesPDG){
			System.out.println("All matches: "+match[0].getReturnType()+
								" "+match[0].getMethodName()+", "+
								match[1].getReturnType()+" "+match[1].getMethodName());
		}*/
		Analysis analysisPDG = new Analysis(matchesPDG);
		int[] tpFp = analysisPDG.tpfp();
		System.out.println("Number of test code functions = "+ srcMethods.size());
		System.out.println("Number of matches = "+matchesPDG.size());
		System.out.println("Number of true positives = "+tpFp[0]);
		System.out.println("Number of false positives = "+tpFp[1]);
		
		System.out.println("ANALYSIS: AST");
		ArrayList<Method> srcTest = new ArrayList<Method>();
		ArrayList<Method[]> matchesAST = (ArrayList<Method[]>) cloneDetect.findSimiliarMethodsAST(srcMethods);
		Analysis analysisAST = new Analysis(matchesAST);
		tpFp = analysisAST.tpfp();
		System.out.println("Number of test code functions = "+ srcMethods.size());
		System.out.println("Number of matches = "+matchesAST.size());
		System.out.println("Number of true positives = "+tpFp[0]);
		System.out.println("Number of false positives = "+tpFp[1]);

		Method method1 = srcMethods.get(46);
		Method method2 = libMethods.get(1);
		method1.constructPDG();

	}
}
