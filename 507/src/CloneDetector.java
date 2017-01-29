import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

/*
 * The detector class
 * Takes the parser output and tries to find similarities
 * Right now we only have a simple line by line comparison of a full file
 * 
*/

public class CloneDetector {
	
	
	public CloneDetector() {
		
	}

	/*
	 * This method should do some kind of comparison between the method names in the lib file
	 * and the source file. Right now it just checks if two methods have the same name.
	 * The method should return a list of near matches of method names.
	 */
	public ArrayList<String[]> analyzeMethodNames(ArrayList<String> libMethodNames, ArrayList<String> srcMethodNames){
		ArrayList<String[]> similarMethodNames = new ArrayList<String[]> ();
		for (String libMethod: libMethodNames){
			for(String srcMethod: srcMethodNames){
				if(libMethod.compareToIgnoreCase(srcMethod)==0){
					String[] matches = {libMethod, srcMethod};
					similarMethodNames.add(matches);
				}
			}
		}
		return similarMethodNames;	
		
	}
}
