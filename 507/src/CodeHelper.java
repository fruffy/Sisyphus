import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.type.Type;

import normalizers.VariableNameNormalizer;

public class CodeHelper {
	public static void main(String[] args) {
		
		String libName = "Math.class";
		// String functionCode = args[0];
		String srcName = "src/TestCode.java";
		//String libName = "lib.txt";
		//String srcName = "code.txt";
		ArrayList<String> libMethodNames = new ArrayList<String>(); 
		ArrayList<String> srcMethodNames = new ArrayList<String>();
		ArrayList<Type> libMethodReturnTypes = new ArrayList<Type>(); 
		ArrayList<Type> srcMethodReturnTypes = new ArrayList<Type>();
		List<Parameter> srcMethodParameter = new ArrayList<Parameter> ();
		ArrayList<Method> libMethods = new ArrayList<Method>();
		ArrayList<Method> srcMethods = new ArrayList<Method>();
		List<Node> libMethodNodes = new ArrayList<Node>();
		List<Node> srcMethodNodes = new ArrayList<Node>();
		//initialize java parser for both library and source code.
		try{
			SyntaxParser libparser = new SyntaxParser(libName);
			SyntaxParser srcparser = new SyntaxParser(srcName);
			libMethodNames = libparser.getMethodNames();
			srcMethodNames = srcparser.getMethodNames();
			libMethodReturnTypes = libparser.getReturnTypes();
			srcMethodReturnTypes = srcparser.getReturnTypes();
			libMethods = libparser.getMethods();
			srcMethods = srcparser.getMethods();
			srcMethodParameter = srcMethods.get(0).getMethodParameters();
			libMethodNodes = libMethods.get(0).getMethodNodes();
			srcMethodNodes = srcMethods.get(0).getMethodNodes();
			//System.out.println(srcMethodNodes.size());
		}catch (IOException e) {
			e.printStackTrace();
			new RuntimeException(e);
		}
		
		
		// Debug
		//System.out.println("Working Directory = " + System.getProperty("user.dir"));
		
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
		System.out.println(srcMethods.get(0).getFilteredBody());*/
		System.out.println("Body of method after renaming");
		System.out.println(srcMethods.get(0).normalize(new VariableNameNormalizer()).getFilteredBody());
		System.out.println("Nodes of method");
		for(Node node: srcMethodNodes){
			System.out.println(node.toString());
		}
		
		// ugly stuff
		CloneDetector cloneDetect = new CloneDetector();
		/*System.out.println("Testing match method");
		Method libMethod = libMethods.get(0);
		Method srcMethod = srcMethods.get(0);
		System.out.println("Method1: "+libMethod.getMethodName()+" Method2: "+srcMethod.getMethodName());
		System.out.println(cloneDetect.matchMethods(libMethod, srcMethod));
		libMethod = libMethods.get(1);
		srcMethod = srcMethods.get(1);
		System.out.println("Method1: "+libMethod.getMethodName()+" Method2: "+srcMethod.getMethodName());
		System.out.println(cloneDetect.matchMethods(libMethod, srcMethod));
		
		for (Method match : cloneDetect.findSimiliarMethods(srcMethods, libMethods)) {
			System.out.println("Matched Method is: "+match.getMethodName());

		}*/
		
		Method srcMethod1 = srcMethods.get(0);
		Method srcMethod2 = srcMethods.get(1);
		System.out.println("Testing matchMethodNodes");
		System.out.println("Method1: "+srcMethod1.getMethodName()+" Method2: "+srcMethod2.getMethodName());
		System.out.println(cloneDetect.matchMethodNodes(srcMethod1, srcMethod2));
		
		
	}
}
