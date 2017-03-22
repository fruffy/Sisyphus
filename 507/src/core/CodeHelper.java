package core;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import parsers.SyntaxParser;

public class CodeHelper {
	public static void main(String[] args) {
		
		String srcName = "../examples/TestCodeV2.java";
		File libFile2 = new File("Math.java");
		File libFile1 = new File("Arrays.java");

		File srcfile = new File(srcName);

		SyntaxParser libparser1, libparser2, libparser;
		SyntaxParser srcparser;
		ArrayList<Method> srcMethods, libMethods;
		if (!(srcfile.exists()) || srcfile.isDirectory()) {
			System.out.println("Input file does not exist or is not a valid input.");
			// Debug
			/* System.out.println("Working Directory = " +
			 System.getProperty("user.dir"));*/
			return;
		}
		if (!(libFile1.exists()) || libFile1.isDirectory()) {
			System.out.println("Error reading reference library.");
			// Debug
			// System.out.asdasdaprintln("Working Directory = " +
			// System.getProperty("user.dir"));
			return;
		}

		// initialize java parser for both library and source code.
		try {
			libparser1 = new SyntaxParser(libFile1);
			libparser2 = new SyntaxParser(libFile2);
			srcparser = new SyntaxParser(srcfile);
		} catch (IOException e) {
			new RuntimeException(e);
			return;
		}

		// testing(srcparser, libparser);
		srcMethods = srcparser.getMethods();
		libMethods = libparser1.getMethods();
		libMethods.addAll(libparser2.getMethods());
/*		for (Method method : libMethods) {
			System.out.println(method.getMethodName());
		}*/
		//ArrayList<Method> libTest = new ArrayList<Method>();
		//libTest.add(libMethods.get(223));

		CloneDetector cloneDetect = new CloneDetector(libMethods);
		matchSrcWithLib(srcMethods,cloneDetect);
		
		int numParticipants = 12;
		int numMethods = srcMethods.size()/numParticipants;
		testCodeMatches(srcMethods,cloneDetect,numParticipants,numMethods);

	}
	
	/**
	 * Find matches between src and library functions
	 * @param srcMethods
	 * @param cloneDetect
	 */
	public static void matchSrcWithLib(ArrayList<Method> srcMethods, CloneDetector cloneDetect){
		System.out.println("ANALYSIS: PDG");
		ArrayList<Method[]> matchesPDG = (ArrayList<Method[]>) cloneDetect.findSimiliarMethodsPDG(srcMethods);
		Analysis analysisPDG = new Analysis(matchesPDG);
		int[] tpFp = analysisPDG.tpfp();
		System.out.println("Number of test code functions = "+ srcMethods.size());
		System.out.println("Number of matches = "+matchesPDG.size());
		System.out.println("Number of true positives = "+tpFp[0]);
		System.out.println("Number of false positives = "+tpFp[1]);
		System.out.printf("Percentage of true positives %.2f%%\n", 100*tpFp[0]/(double)matchesPDG.size());
		
		System.out.println("ANALYSIS: AST");
		ArrayList<Method> srcTest = new ArrayList<Method>();
		ArrayList<Method[]> matchesAST = (ArrayList<Method[]>) cloneDetect.findSimiliarMethodsAST(srcMethods);
		Analysis analysisAST = new Analysis(matchesAST);
		tpFp = analysisAST.tpfp();
		System.out.println("Number of test code functions = "+ srcMethods.size());
		System.out.println("Number of matches = "+matchesAST.size());
		System.out.println("Number of true positives = "+tpFp[0]);
		System.out.println("Number of false positives = "+tpFp[1]);
		System.out.printf("Percentage of true positives %.2f%%\n", 100*tpFp[0]/(double)matchesAST.size());

		System.out.println("ANALYSIS: AST Deckard");
		ArrayList<Method> srcTest2 = new ArrayList<Method>();
		srcTest2.add(srcMethods.get(55));
		ArrayList<Method[]> matchesDeckard = (ArrayList<Method[]>) cloneDetect.findSimiliarMethodsNodeFeatures(srcMethods);
		Analysis analysisDeckard = new Analysis(matchesDeckard);
		tpFp = analysisDeckard.tpfp();
		System.out.println("Number of test code functions = "+ srcMethods.size());
		System.out.println("Number of matches = "+matchesDeckard.size());
		System.out.println("Number of true positives = "+tpFp[0]);
		System.out.println("Number of false positives = "+tpFp[1]);
		System.out.printf("Percentage of true positives %.2f%%\n", 100*tpFp[0]/(double)matchesDeckard.size());
		System.out.println("Proportion of true positives out of all possible true positives "+tpFp[0]/(srcMethods.size()*1.0));

		/*System.out.println("ANALYSIS: AST Simple Matching");
		ArrayList<Method[]> matchesAST = (ArrayList<Method[]>) cloneDetect.findSimiliarMethodsAST(srcMethods);
		Analysis analysisAST = new Analysis(matchesAST);
		tpFp = analysisAST.tpfp();
		System.out.println("Number of test code functions = "+ srcMethods.size());
		System.out.println("Number of matches = "+matchesAST.size());
		System.out.println("Number of true positives = "+tpFp[0]);
		System.out.println("Number of false positives = "+tpFp[1]);
		System.out.println("Ratiof of true positives to false positives "+ tpFp[0]/(tpFp[1]*1.0));*/
	}
	
	/**
	 * Find matches between methods implemented by different participants
	 * @param srcMethods
	 * @param cloneDetect
	 * @param numParticipants
	 * @param numMethods
	 */
	public static void testCodeMatches(ArrayList<Method> srcMethods, CloneDetector cloneDetect, int numParticipants, int numMethods){
		
		int pIndex = numParticipants;
		int totalPossibleMatches = 0;
		while(pIndex!=0){
			totalPossibleMatches += pIndex;
			pIndex--;
		}
		System.out.println("Testing PDG matching between participants' code");
		float[] tpProportion = new float[numMethods];
		for(int n = 0; n<numMethods; n++){
			for(int i = n*numParticipants; i<n*numParticipants + numParticipants; i++){
				for(int j = i+1; j<n*numParticipants + numParticipants; j++){
					/*System.out.println("i "+i+" j "+j);
					System.out.println("Trying match between "+
				srcMethods.get(i).getMethodName()+" "+srcMethods.get(j).getMethodName());*/
					boolean match = cloneDetect.matchMethodPDGs(srcMethods.get(i), srcMethods.get(j));
					//System.out.println("match "+match);
					if(match){
						tpProportion[n]++;
					}
				}
			}
			tpProportion[n] = tpProportion[n]/totalPossibleMatches;
		}
	
		System.out.println("Proportion of true positives out of all possible matches for each method:");
		for (int i = 0; i<tpProportion.length; i++){
			System.out.print(tpProportion[i]+" ");
		}
		System.out.print("\n");
		
		System.out.println("Testing AST matching between participants' code");
		tpProportion = new float[numMethods];
		for(int n = 0; n<numMethods; n++){
			for(int i = n*numParticipants; i<n*numParticipants + numParticipants; i++){
				for(int j = i+1; j<n*numParticipants + numParticipants; j++){
					/*System.out.println("i "+i+" j "+j);
					System.out.println("Trying match between "+
				srcMethods.get(i).getMethodName()+" "+srcMethods.get(j).getMethodName());*/
					boolean match = cloneDetect.matchMethodDeclaration(srcMethods.get(i), srcMethods.get(j));
					//System.out.println("match "+match);
					if(match){
						tpProportion[n]++;
					}
				}
			}
			tpProportion[n] = tpProportion[n]/totalPossibleMatches;
		}
	
		System.out.println("Proportion of true positives out of all possible matches for each method:");
		for (int i = 0; i<tpProportion.length; i++){
			System.out.print(tpProportion[i]+" ");
		}
		System.out.print("\n");
		
		System.out.println("Testing AST Deckard between participants' code");
		tpProportion = new float[numMethods];
		for(int n = 0; n<numMethods; n++){
			for(int i = n*numParticipants; i<n*numParticipants + numParticipants; i++){
				for(int j = i+1; j<n*numParticipants + numParticipants; j++){
					/*System.out.println("i "+i+" j "+j);
					System.out.println("Trying match between "+
				srcMethods.get(i).getMethodName()+" "+srcMethods.get(j).getMethodName());*/
					boolean match = cloneDetect.matchMethodNodeFeatures(srcMethods.get(i), srcMethods.get(j),4.0);
					//System.out.println("match "+match);
					if(match){
						tpProportion[n]++;
					}
				}
			}
			tpProportion[n] = tpProportion[n]/totalPossibleMatches;
		}
	
		System.out.println("Proportion of true positives out of all possible matches for each method:");
		for (int i = 0; i<tpProportion.length; i++){
			System.out.print(tpProportion[i]+" ");
		}
		System.out.print("\n");
	
	}
		
		/*System.out.println("ANALYSIS: PDG Deckard");
		ArrayList<Method[]> matchesPDGDeckard = (ArrayList<Method[]>) cloneDetect.findSimiliarMethodsPDGDeckard(srcMethods);
		Analysis analysisPDGDeckard = new Analysis(matchesPDGDeckard);
		int[] tpFp = analysisPDGDeckard.tpfp();
		System.out.println("Number of test code functions = "+ srcMethods.size());
		System.out.println("Number of matches = "+matchesPDGDeckard.size());
		System.out.println("Number of true positives = "+tpFp[0]);
		System.out.println("Number of false positives = "+tpFp[1]);
		System.out.println("Ratiof of true positives to false positives "+ tpFp[0]/(tpFp[1]*1.0));*/
		
		/*Method method1 = srcMethods.get(46);
		Method method2 = libMethods.get(1);
		method1.constructPDG();
		
		PDGGraphViz.writeDot(method1.getCdg(), "cdg.dot");
		PDGGraphViz.writeDot(method1.getDdg(), "ddg.dot");
		method1.printComparison();*/
}
