package core;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.type.Type;

import normalizers.VariableNameNormalizer;
import parsers.SyntaxParser;

public class CodeHelper {
	public static void main(String[] args) {

		String srcName = "src/examples/Testcode.java";

		File libFile = new File("Math_full.class");
		File srcfile = new File(srcName);

		SyntaxParser libparser;
		SyntaxParser srcparser;
		ArrayList<Method> srcMethods;

		if (!(srcfile.exists()) || srcfile.isDirectory()) {
			System.out.println("Input file does not exist or is not a valid input.");
			// Debug
			// System.out.println("Working Directory = " +
			// System.getProperty("user.dir"));
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

		//testing(srcparser, libparser);

		srcMethods = srcparser.getMethods();
		CloneDetector cloneDetect = new CloneDetector(libparser.getMethods());
		cloneDetect.findSimiliarMethods(srcMethods);

	}

	/*
	 * Method to test individual features of the CloneDetector class if needed
	 * Purpose is to keep the main code flow clean and readable
	 */

	public static void testing(SyntaxParser srcparser, SyntaxParser libparser) {
		ArrayList<String> libMethodNames = new ArrayList<String>();
		ArrayList<String> srcMethodNames = new ArrayList<String>();
		ArrayList<Type> libMethodReturnTypes = new ArrayList<Type>();
		ArrayList<Type> srcMethodReturnTypes = new ArrayList<Type>();
		ArrayList<Parameter> srcMethodParameter = new ArrayList<Parameter>();
		ArrayList<Method> libMethods = libparser.getMethods();
		ArrayList<Method> srcMethods = srcparser.getMethods();
		ArrayList<Node> libMethodNodes = new ArrayList<Node>();
		ArrayList<Node> srcMethodNodes = new ArrayList<Node>();
		// Test the results of javaparser functions
		
		  /*System.out.println("library method names");
		  System.out.println(libMethodNames);
		  System.out.println("library method return types");
		  System.out.println(libMethodReturnTypes);
		  System.out.println("source code method names");
		  System.out.println(srcMethodNames);
		  System.out.println("source method return types");
		  System.out.println(srcMethodReturnTypes);
		  System.out.println("Parameter type of main method");
		  System.out.println(srcMethodParameter.get(0).getType());
		  System.out.println("Body of method with comments");
		  System.out.println(srcMethods.get(0).getBody());
		  System.out.println("Body of method without comments");
		  System.out.println(srcMethods.get(0).getFilteredBody());
		 
		System.out.println("Body of method after renaming");
		System.out.println(srcMethods.get(0).normalize(new VariableNameNormalizer()).getFilteredBody());
		System.out.println("Nodes of method"); for(Node node:
		srcMethodNodes){ System.out.println(node.getClass().toString()); }*/
		 
		System.out.println("Testing Source NodeFeature of method: "+srcMethods.get(3).getMethodName());
		NodeFeature nodeFeature = srcMethods.get(3).getMethodFeature();
		System.out.println(nodeFeature.getFeatureMap());
		
		System.out.println("Testing Lib NodeFeature of method: "+libMethods.get(39).getMethodName());
		NodeFeature nodeFeature2 = libMethods.get(41).getMethodFeature();
		System.out.println(nodeFeature2.getFeatureMap());
		
		CloneDetector cloneDetect = new CloneDetector();
		System.out.println(cloneDetect.matchMethodNodeFeatures(srcMethods.get(3), libMethods.get(41), 1.0));

		// ugly stuff
		/*CloneDetector cloneDetect = new CloneDetector();
		
		  System.out.println("Testing match method"); Method libMethod =
		  libMethods.get(0); Method srcMethod = srcMethods.get(0);
		  System.out.println("Method1: "+libMethod.getMethodName()+" Method2: "
		  +srcMethod.getMethodName());
		  System.out.println(cloneDetect.matchMethods(libMethod, srcMethod));
		  libMethod = libMethods.get(1); srcMethod = srcMethods.get(1);
		  System.out.println("Method1: "+libMethod.getMethodName()+" Method2: "
		  +srcMethod.getMethodName());
		  System.out.println(cloneDetect.matchMethods(libMethod, srcMethod));
		  
		  for (Method match : cloneDetect.findSimiliarMethods(srcMethods,
		  libMethods)) {
		  System.out.println("Matched Method is: "+match.getMethodName()); }
		 

		Method srcMethod1 = srcMethods.get(0);
		Method srcMethod2 = srcMethods.get(1);
		System.out.println("Testing matchMethodNodes");
		System.out.println("Method1: " + srcMethod1.getMethodName() + " Method2: " + srcMethod2.getMethodName());
		System.out.println(cloneDetect.matchMethodNodes(srcMethod1, srcMethod2));
		System.out.println("Testing matchMethodNodeFeatures");
		srcMethod2 = srcMethods.get(2);
		System.out.println("Method1: " + srcMethod1.getMethodName() + " Method2: " + srcMethod2.getMethodName());
		System.out.println(cloneDetect.matchMethodNodeFeatures(srcMethod1, srcMethod2, 0.0));
		srcMethod2 = srcMethods.get(3);
		System.out.println("Method1: " + srcMethod1.getMethodName() + " Method2: " + srcMethod2.getMethodName());
		System.out.println(cloneDetect.matchMethodNodeFeatures(srcMethod1, srcMethod2, 0.0));
	*/
	}

}
