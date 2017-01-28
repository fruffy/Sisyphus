import java.io.BufferedReader;
import java.io.IOException;

/*
 * The detector class
 * Takes the parser output and tries to find similarities
 * Right now we only have a simple line by line comparison of a full file
 * 
*/
public class CloneDetector {
	
	
	public CloneDetector() {
		
	}

	// The algorithm
	public boolean detectClone(BufferedReader bufferedLibReader, BufferedReader bufferedFunctionReader)
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
