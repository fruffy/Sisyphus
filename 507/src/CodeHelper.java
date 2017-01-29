import java.io.IOException;
import java.util.*;

public class CodeHelper {
	public static void main(String[] args) {
		
		String libName = "Math.class";
		// String functionCode = args[0];
		String srcName = "src/TestCode.java";
		ArrayList<String> libMethodNames = new ArrayList<String>(); 
		ArrayList<String> srcMethodNames = new ArrayList<String>();
		ArrayList<String> libMethodReturnTypes = new ArrayList<String>(); 
		ArrayList<String> srcMethodReturnTypes = new ArrayList<String>();
		
		//initialize java parser for both library and source code.
		try{
			SyntaxParser libparser = new SyntaxParser(libName);
			SyntaxParser srcparser = new SyntaxParser(srcName);
			libMethodNames = libparser.getMethodNames();
			srcMethodNames = srcparser.getMethodNames();
			libMethodReturnTypes = libparser.getReturnTypes();
			srcMethodReturnTypes = srcparser.getReturnTypes();
			
		}catch (IOException e) {
			e.printStackTrace();
			new RuntimeException(e);
		}
		
		
		// Debug
		//System.out.println("Working Directory = " + System.getProperty("user.dir"));
		
		// Test the results of javaparser functions
		System.out.println("library method names");
		System.out.println(libMethodNames);
		System.out.println("library method return types");
		System.out.println(libMethodReturnTypes);
		System.out.println("source code method names");
		System.out.println(srcMethodNames);
		System.out.println("source method return types");
		System.out.println(srcMethodReturnTypes);
		
		// ugly stuff
		CloneDetector cloneDetect = new CloneDetector();
		ArrayList<String[]> nearMatches = cloneDetect.analyzeMethodNames(libMethodNames, srcMethodNames);
		System.out.println("near matches of method names between lib and src are: ");
		for(String[] match: nearMatches){
			System.out.print("["+match[0]+" "+match[1]+"], ");
		}
		System.out.println("");
	}
}
