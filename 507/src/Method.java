import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.type.Type;

import java.io.StringReader;
import java.util.*;

/*
 * Method class that holds information about a particular method
 * - the name of the method, the return type of the method.
 */

public class Method {
	private String methodName;
	private Type returnType;
	private List<Parameter> parameters;
	private BlockStmt body;
	
	public Method(MethodDeclaration methodDeclaration){
		this.methodName = methodDeclaration.getNameAsString();
		this.parameters = methodDeclaration.getParameters();
		this.returnType = methodDeclaration.getType();
		this.body = methodDeclaration.getBody().get();
		
	}
	
	public String getMethodName(){
		return this.methodName;
	}
	
	public Type getReturnType(){
		return this.returnType;
	}
	
	public List<Parameter> getMethodParameters(){
		return this.parameters;
	}
	
	public String getBody(){
		return this.body.toString();
	}
	
	public String getBodyWithoutComments(){
		String bodyWithoutComments = "";
		String originalBody = this.body.toString();
		boolean foundCommentInline = false;
		boolean foundCommentBlock = false;
		int i =0;
		while(i<originalBody.length()){
			if(i+2<originalBody.length()){
				if(originalBody.substring(i, i+2).compareTo("/*")==0){
					foundCommentBlock = true;
					i+=2;
				}
				else if(originalBody.substring(i, i+2).compareTo("*/")==0){
					foundCommentBlock = false;
					i+=2;
				}
				else if(originalBody.substring(i, i+2).compareTo("//")==0){
					foundCommentInline = true;
					i+=2;
				}
			}
			if(i+1<originalBody.length()){
				if(foundCommentInline && originalBody.charAt(i)=='\n'){
					foundCommentInline = false;
					i++;
				}
			}
			if(!(foundCommentBlock || foundCommentInline)){
				bodyWithoutComments = bodyWithoutComments + originalBody.charAt(i);
			}
			i++;
			
		}
		return bodyWithoutComments;
	}
}
