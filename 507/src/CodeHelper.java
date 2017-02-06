import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.ParseException;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.type.Type;

import normalizers.VariableNameNormalizer;

public class CodeHelper {
	public static void main(String[] args) {

		// String srcName = args[0];
		String srcName = "src/Testcode.java";

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

		ArrayList<String> libMethodNames = new ArrayList<String>();
		ArrayList<String> srcMethodNames = new ArrayList<String>();
		ArrayList<Type> libMethodReturnTypes = new ArrayList<Type>();
		ArrayList<Type> srcMethodReturnTypes = new ArrayList<Type>();
		List<Parameter> srcMethodParameter = new ArrayList<Parameter>();
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
		
		//srcparser.getSymbols();

		/*libMethodNames = libparser.getMethodNames();
		srcMethodNames = srcparser.getMethodNames();
		libMethodReturnTypes = libparser.getReturnTypes();
		srcMethodReturnTypes = srcparser.getReturnTypes();*/
		libMethods = libparser.getMethods();
		srcMethods = srcparser.getMethods();
		srcMethodParameter = srcMethods.get(0).getMethodParameters();
		libMethodNodes = libMethods.get(0).getMethodNodes();
		srcMethodNodes = srcMethods.get(0).getMethodNodes();

		// Test the results of javaparser functions
		System.out.println("\n* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
		System.out.println("library method names");
		System.out.println(libMethodNames);
		System.out.println("\n* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
		System.out.println("library method return types");
		System.out.println(libMethodReturnTypes);
		System.out.println("\n* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
		System.out.println("source code method names");
		System.out.println(srcMethodNames);
		System.out.println("\n* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
		System.out.println("source method return types");
		System.out.println(srcMethodReturnTypes);
		System.out.println("\n* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
		System.out.println("Parameter type of main method");
		System.out.println(srcMethodParameter.get(0).getType());
		System.out.println("\n* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
		System.out.println("Body of method with comments");
		System.out.println(srcMethods.get(0).getBody());
		System.out.println("\n* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
		System.out.println("Body of method without comments");
		System.out.println(srcMethods.get(0).getFilteredBody());

		System.out.println("\n# # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # #");
		System.out.println("Body of method after renaming");
		System.out.println(srcMethods.get(0).normalize(new VariableNameNormalizer()).getFilteredBody());

		// ugly stuff
		CloneDetector cloneDetect = new CloneDetector();

		Method srcMethod1 = srcMethods.get(0);
		Method srcMethod2 = srcMethods.get(1);
		System.out.println("\n- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");

		System.out.println("Testing matchMethodNodes");
		System.out.println("Method1: " + srcMethod1.getMethodName() + " Method2: " + srcMethod2.getMethodName());
		System.out.println(cloneDetect.matchMethodNodes(srcMethod1, srcMethod2));

	}
}
