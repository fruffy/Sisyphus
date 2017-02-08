package parsers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SimpleName;

public class EclipseParser {

	// use ASTParse to parse string
	public static void parse(String str) {
		ASTParser parser = ASTParser.newParser(AST.JLS8);
		parser.setSource(str.toCharArray());
		parser.setKind(ASTParser.K_COMPILATION_UNIT);

		final CompilationUnit cu = (CompilationUnit) parser.createAST(null);

		cu.accept(new ASTVisitor() {

			/*
			 * Set names = new HashSet(); public boolean
			 * visit(VariableDeclarationFragment node) { SimpleName name =
			 * node.getName(); this.names.add(name.getIdentifier());
			 * System.out.println("Declaration of '" + name + "' at line" +
			 * cu.getLineNumber(name.getStartPosition())); cu.getJavaElement();
			 * return true; // do not continue }
			 */
			public boolean visit(MethodDeclaration meth) {
				Block block = meth.getBody();
				System.out.println(
						"Method '" + meth.getName() + "' at line " + cu.getLineNumber(meth.getStartPosition()));
				// System.out.println("Body " + meth.getBody().toString());

				// System.out.println("Declarations " + meth.parameters());
				// here can access the first element of the returned statement
				// list
				// String str = block.statements().get(0).toString();

				// System.out.println("Statements " +str);

				block.accept(new ASTVisitor() {

					public boolean visit(SimpleName node) {
					/*	Expression exp = node.getExpression();
						if (exp != null) {
						    ITypeBinding typeBinding = node.getExpression().resolveTypeBinding();
						    System.out.println("Type: " + typeBinding);	
						}*/
					   // ITypeBinding typeBinding = node.getExpression().resolveTypeBinding();
					   // System.out.println("Type: " + typeBinding.toString());
						System.out.println("Name: " + node.getFullyQualifiedName());

						return true;
					}

				});
				return true;

			}
			/*
			 * public boolean visit(SimpleName node) { if
			 * (this.names.contains(node.getIdentifier())) {
			 * System.out.println("Usage of '" + node + "' at line " +
			 * cu.getLineNumber(node.getStartPosition())); } return true; }
			 */

		});

	}

	// read file content into a string
	public static String readFileToString(String filePath) throws IOException {
		StringBuilder fileData = new StringBuilder(1000);
		BufferedReader reader = new BufferedReader(new FileReader(filePath));

		char[] buf = new char[10];
		int numRead = 0;
		while ((numRead = reader.read(buf)) != -1) {
			//System.out.println(numRead);
			String readData = String.valueOf(buf, 0, numRead);
			fileData.append(readData);
			buf = new char[1024];
		}

		reader.close();

		return fileData.toString();
	}

	// loop directory to get file list
	public static void ParseFilesInDir() throws IOException {
		parse(readFileToString("src/TestCode.java"));
		/*
		 * File dirs = new File("."); String dirPath = dirs.getCanonicalPath() +
		 * File.separator+"src"+File.separator;
		 * 
		 * 
		 * File root = new File(dirPath);
		 * //System.out.println(rootDir.listFiles()); File[] files =
		 * root.listFiles ( ); String filePath = null;
		 * 
		 * for (File f : files ) { filePath = f.getAbsolutePath();
		 * System.out.println(f.getName()); if(f.isFile()){
		 * parse(readFileToString(filePath)); } }
		 */
	}

	public static void main(String[] args) throws IOException {
		ParseFilesInDir();
	}
}