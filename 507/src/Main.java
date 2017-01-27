import java.io.BufferedReader;
import java.io.IOException;

public class Main {
	public static void main(String[] args) {
		SyntaxParser libParser;
		SyntaxParser functionParser;
		BufferedReader libReader;
		BufferedReader functionReader;

		String libName = "lib.txt";
		// String functionCode = args[0];
		String functionName = "code.txt";

		System.out.println("Working Directory = " + System.getProperty("user.dir"));
		libParser = new SyntaxParser(libName);
		functionParser = new SyntaxParser(functionName);
		libParser.parse(libName);
		//functionParser.parse(functionName);
		try {
			libReader = libParser.getParsedObject();
			functionReader = functionParser.getParsedObject();
			System.out.println(detectClone(libReader, functionReader));
			libParser.closeParser();
			functionParser.closeParser();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

	}

	// The algorithm
	private static boolean detectClone(BufferedReader bufferedLibReader, BufferedReader bufferedFunctionReader)
			throws IOException {
		String funLine;
		String libLine;
		while (true) {

			libLine = bufferedLibReader.readLine();
			funLine = bufferedFunctionReader.readLine();

			if (libLine == null || funLine == null) {
				break;
			}
			if (!(libLine.equals(funLine))) {
				return false;
			}
		}
		return true;
	}
}
