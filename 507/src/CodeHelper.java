import java.io.IOException;

public class CodeHelper {
	public static void main(String[] args) {
		
		SyntaxParser libParser;
		SyntaxParser functionParser;
		String libName = "lib.txt";
		// String functionCode = args[0];
		String functionName = "code.txt";
		
		// Debug
		System.out.println("Working Directory = " + System.getProperty("user.dir"));
		
		// Where the parsed data should be returned
			libParser = new SyntaxParser(libName);
			functionParser = new SyntaxParser(functionName);
		
		// Test the javaparser function
		libParser.parse(libName);

		try {
			// ugly stuff
			CloneDetector cloneDect = new CloneDetector();
			System.out.println(cloneDect.detectClone(libParser.getParsedObject(), functionParser.getParsedObject()));
			libParser.closeParser();
			functionParser.closeParser();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

	}
}
