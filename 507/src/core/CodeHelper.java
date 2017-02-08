package core;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.type.Type;

import normalizers.VariableNameNormalizer;
import parsers.SyntaxParser;

public class CodeHelper {
	public static void main(String[] args) {

		// String srcName = args[0];
		String srcName = "src/examples/Testcode.java";

		File libFile = new File("Math_full.class");
		File srcfile = new File(srcName);
		SyntaxParser libparser;
		SyntaxParser srcparser;

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

	/*	ArrayList<String> libMethodNames = new ArrayList<String>();
		ArrayList<String> srcMethodNames = new ArrayList<String>();
		ArrayList<Type> libMethodReturnTypes = new ArrayList<Type>();
		ArrayList<Type> srcMethodReturnTypes = new ArrayList<Type>();
		List<Parameter> srcMethodParameter = new ArrayList<Parameter>();*/
		ArrayList<Method> libMethods = new ArrayList<Method>();
		ArrayList<Method> srcMethods = new ArrayList<Method>();
		List<Node> libMethodNodes = new ArrayList<Node>();
		List<Node> srcMethodNodes = new ArrayList<Node>();

		// initialize java parser for both library and source code.
		try {
			libparser = new SyntaxParser(libFile);
			srcparser = new SyntaxParser(srcfile);
		} catch (IOException e) {
			new RuntimeException(e);
			return;
		}

		srcMethods = srcparser.getMethods();
		
		//srcMethodParameter = srcMethods.get(0).getMethodParameters();

		/*System.out.println("Testing NodeFeature of method");
		NodeFeature nodeFeature = srcMethods.get(0).getMethodFeature();
		System.out.println(nodeFeature.getFeatureMap());
		System.out.println(nodeFeature.getFeatureVector());*/

		CloneDetector cloneDetect = new CloneDetector(libparser.getMethods());
		cloneDetect.findSimiliarMethods(srcMethods);

	}
}
