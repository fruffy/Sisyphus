package core;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import datastructures.Method;
import matching.ASTDetector;
import matching.DeckardDetector;
import matching.KrinkeDetector;
import parsers.SyntaxParser;

public class CodeHelper {
	
	
	public static void main(String[] args) throws IOException {

		String srcName = "../examples/TestCodeV2.java";

		File srcfile = new File(srcName);
		File leetfile = new File("../examples/LeetcodeTest.java");
		List<File> libCollection = SourceCodeCollector.listClasses(new File("../jre_library/java"));
		SyntaxParser libparser;
		SyntaxParser srcparser, leetcodeparser;
		ArrayList<Method> srcMethods, libMethods, leetcodeMethods;
		if (!(srcfile.exists()) || srcfile.isDirectory()) {
			System.err.println("Input file does not exist or is not a valid input.");
			return;
		}
		if (libCollection.isEmpty()) {
			System.err.println("Error reading reference library.");
			return;
		}

		// initialize java parser for both library and source code.
		try {
			libparser = new SyntaxParser(libCollection);
			srcparser = new SyntaxParser(srcfile);
			//leetcodeparser = new SyntaxParser(leetfile);
		} catch (IOException e) {
			new RuntimeException(e);
			return;
		}

		srcMethods = srcparser.getMethods();
		libMethods = libparser.getMethods();
		//leetcodeMethods = leetcodeparser.getMethods();
		/*
		 * for (Method method : libMethods) {
		 * System.out.println(method.getMethodName()); }
		 */
		// ArrayList<Method> libTest = new ArrayList<Method>();
		// libTest.add(libMethods.get(223));

		//File temp = new File(System.getProperty("java.io.tmpdir") + "/CodeHelper","library.lib");
		//temp.getParentFile().mkdirs();
		//FileOutputStream fileOut = new FileOutputStream(temp);
		//ObjectOutputStream out = new ObjectOutputStream(fileOut);
		//out.writeObject(libparser);
		//out.close();
		//XStream xstream = new XStream(); // does not require XPP3 library
		//xstream.toXML(libparser, new OutputStreamWriter(fileOut));
		//fileOut.write(xml);
		//fileOut.close();
		//System.out.printf("Serialized data is saved in /tmp/library.lib");
		//srcparser = (SyntaxParser)xstream.fromXML(new FileInputStream(temp));
		//ControlListParser test = new ControlListParser(srcMethods);
		matchSrcWithLib(srcMethods,libMethods);

		/*
		 * int numParticipants = 13; int numMethods =
		 * srcMethods.size()/numParticipants;
		 * testCodeMatches(srcMethods,cloneDetect,numParticipants,numMethods);
		 * 
		 * testLeetcodeMatches(leetcodeMethods,cloneDetect);
		 */
	}

	/**
	 * Find matches between src and library functions
	 * 
	 * @param srcMethods
	 * @param cloneDetect
	 */
	public static void matchSrcWithLib(ArrayList<Method> srcMethods, ArrayList<Method> libMethods) {
		
		System.out.println("Method Count :" +libMethods.size());
		KrinkeDetector cloneDetect = new KrinkeDetector(libMethods);
		System.out.println("ANALYSIS: PDG");
		ArrayList<Method[]> matchesPDG = (ArrayList<Method[]>) cloneDetect.findSimiliarMethods(srcMethods);
		Analysis analysisPDG = new Analysis(matchesPDG,srcMethods.size(),libMethods.size());
		int[] tpFp = analysisPDG.tpfp();
		System.out.println("Number of test code functions = " + srcMethods.size());
		System.out.println("Number of matches = " + matchesPDG.size());
		System.out.println("Number of true positives = " + tpFp[0]);
		System.out.println("Number of false positives = " + tpFp[1]);
		System.out.println("Number of true negatives = " + tpFp[2]);
		System.out.println("Number of false negatives = " + tpFp[3]);
		System.out.printf("Percentage of true positives %.2f%%\n", 100 * tpFp[0] / (double) matchesPDG.size());
		System.out.printf("Percentage of actual matches %.2f%%\n", 100 * tpFp[0] / (double) srcMethods.size());
		
		ASTDetector cloneDetect2 = new ASTDetector(libMethods);
		System.out.println("ANALYSIS: AST");
		ArrayList<Method> srcTest = new ArrayList<Method>();
		ArrayList<Method[]> matchesAST = (ArrayList<Method[]>) cloneDetect2.findSimiliarMethods(srcMethods);
		Analysis analysisAST = new Analysis(matchesAST,srcMethods.size(),libMethods.size());
		tpFp = analysisAST.tpfp();
		System.out.println("Number of test code functions = " + srcMethods.size());
		System.out.println("Number of matches = " + matchesAST.size());
		System.out.println("Number of true positives = " + tpFp[0]);
		System.out.println("Number of false positives = " + tpFp[1]);
		System.out.println("Number of true negatives = " + tpFp[2]);
		System.out.println("Number of false negatives = " + tpFp[3]);
		System.out.printf("Percentage of true positives %.2f%%\n", 100 * tpFp[0] / (double) matchesAST.size());
		System.out.printf("Percentage of actual matches %.2f%%\n", 100 * tpFp[0] / (double) srcMethods.size());

		DeckardDetector cloneDetect3 = new DeckardDetector(libMethods);
		System.out.println("ANALYSIS: AST Deckard");
		ArrayList<Method> srcTest2 = new ArrayList<Method>();
		srcTest2.add(srcMethods.get(55));
		ArrayList<Method[]> matchesDeckard = (ArrayList<Method[]>) cloneDetect3
				.findSimiliarMethods(srcMethods);
		Analysis analysisDeckard = new Analysis(matchesDeckard,srcMethods.size(),libMethods.size());
		tpFp = analysisDeckard.tpfp();
		System.out.println("Number of test code functions = " + srcMethods.size());
		System.out.println("Number of matches = " + matchesDeckard.size());
		System.out.println("Number of true positives = " + tpFp[0]);
		System.out.println("Number of false positives = " + tpFp[1]);
		System.out.println("Number of true negatives = " + tpFp[2]);
		System.out.println("Number of false negatives = " + tpFp[3]);
		System.out.printf("Percentage of true positives %.2f%%\n", 100 * tpFp[0] / (double) matchesDeckard.size());
		System.out.printf("Percentage of actual matches %.2f%%\n", 100 * tpFp[0] / (double) srcMethods.size());
	
		System.out.close();
	}
/*
	public static void testLeetcodeMatches(ArrayList<Method> srcMethods, CloneDetector cloneDetect) {

		int numMethods = srcMethods.size();
		int numDiffMethods = 5;
		int participants = numMethods / numDiffMethods;
		int pIndex = numMethods / numDiffMethods;
		int totalPossibleMatches = 0;
		while (pIndex != 0) {
			totalPossibleMatches += pIndex;
			pIndex--;
		}
		System.out.println("Testing PDG matching in Leetcode");
		double tp[] = new double[numDiffMethods];
		double fp[] = new double[numDiffMethods];
		double tpProportion[] = new double[numDiffMethods];
		for (int i = 0; i < numMethods; i++) {
			for (int j = i + 1; j < numMethods; j++) {
				Method src1 = srcMethods.get(i);
				Method src2 = srcMethods.get(j);
				boolean match = cloneDetect.matchMethodPDGs(src1, src2);
				// System.out.println("match "+match);
				if (match) {
					String src1Name = src1.getMethodName();
					String src2Name = src2.getMethodName();
					if (src1Name.substring(0, src1Name.length() - 2)
							.equals(src2Name.substring(0, src2Name.length() - 2))) {
						tp[i / participants]++;
					} else {
						fp[i / participants]++;
					}
				}
			}
			tpProportion[i / participants] = (tp[i / participants] * 100.0) / totalPossibleMatches;
		}

		System.out.println("Percentage of true positives out of all possible matches for each method:");
		System.out.println(Arrays.toString(tpProportion));
		System.out.println("Number of false positives should be zero");
		System.out.println(Arrays.toString(fp));
		System.out.println();

		System.out.println("Testing AST matching inLeetcode");
		tp = new double[numDiffMethods];
		fp = new double[numDiffMethods];
		tpProportion = new double[numDiffMethods];
		for (int i = 0; i < numMethods; i++) {
			for (int j = i + 1; j < numMethods; j++) {
				Method src1 = srcMethods.get(i);
				Method src2 = srcMethods.get(j);
				boolean match = cloneDetect.matchMethodDeclaration(src1, src2);
				// System.out.println("match "+match);
				if (match) {
					String src1Name = src1.getMethodName();
					String src2Name = src2.getMethodName();
					if (src1Name.substring(0, src1Name.length() - 2)
							.equals(src2Name.substring(0, src2Name.length() - 2))) {
						tp[i / participants]++;
					} else {
						fp[i / participants]++;
					}
				}
			}
			tpProportion[i / participants] = (tp[i / participants] * 100.0) / totalPossibleMatches;
		}

		System.out.println("Percentage of true positives out of all possible matches for each method:");
		System.out.println(Arrays.toString(tpProportion));
		System.out.println("Number of false positives should be zero");
		System.out.println(Arrays.toString(fp));

		System.out.println();

		System.out.println("Testing AST Deckard in LeetCode");
		tp = new double[numDiffMethods];
		fp = new double[numDiffMethods];
		tpProportion = new double[numDiffMethods];
		for (int i = 0; i < numMethods; i++) {
			for (int j = i + 1; j < numMethods; j++) {
				Method src1 = srcMethods.get(i);
				Method src2 = srcMethods.get(j);
				boolean match = cloneDetect.matchMethods(src1, src2, 4);
				// System.out.println("match "+match);
				if (match) {
					String src1Name = src1.getMethodName();
					String src2Name = src2.getMethodName();
					if (src1Name.substring(0, src1Name.length() - 2)
							.equals(src2Name.substring(0, src2Name.length() - 2))) {
						tp[i / participants]++;
					} else {
						fp[i / participants]++;
					}
				}
			}
			tpProportion[i / participants] = (tp[i / participants] * 100.0) / totalPossibleMatches;
		}

		System.out.println("Percentage of true positives out of all possible matches for each method:");
		System.out.println(Arrays.toString(tpProportion));
		System.out.println("Number of false positives should be zero");
		System.out.println(Arrays.toString(fp));
		System.out.println();

	}*/

}