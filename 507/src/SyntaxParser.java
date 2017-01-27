import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

/*
 * The parser class
 * Takes a filename and attempts to parse it
 * Right now we only have a generic file reader
 * 
*/
public class SyntaxParser {

	String fileName;
	BufferedReader inputBuffer;

	public SyntaxParser(String fileName){
		this.fileName = fileName;		
	}

	public BufferedReader getParsedObject() throws FileNotFoundException {
		BufferedReader inputBuffer = new BufferedReader(new FileReader (fileName));
		return inputBuffer;
	}

	public void closeParser() throws IOException {
		inputBuffer.close();
	}

	/********************************************************************************************/
	/*
	 * Functions imported from the javaparser lib
	 */
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
