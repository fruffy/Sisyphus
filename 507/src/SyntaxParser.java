import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

//import com.google.common.base.Strings;
public class SyntaxParser {
	String fileName;
	BufferedReader inputBuffer = null;

	public SyntaxParser(String fileName) {
		this.fileName = fileName;
	}

	public BufferedReader getParsedObject() throws FileNotFoundException, IOException {
		FileReader inputReader;
		inputReader = new FileReader(fileName);
		inputBuffer = new BufferedReader(inputReader);
		return inputBuffer;
	}

	public void closeParser() throws IOException {
		inputBuffer.close();
	}

	public void parse(String fileName) {
		try {
			File file = new File(fileName);
			new VoidVisitorAdapter<Object>() {
				@Override
				public void visit(MethodCallExpr n, Object arg) {
					super.visit(n, arg);
					System.out.println(" [L " + n.getBegin() + "] " + n);
				}
			}.visit(JavaParser.parse(file), null);
			System.out.println(); // empty line
		} catch (IOException e) {
			new RuntimeException(e);
		}
	}
}
