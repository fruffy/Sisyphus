package examples;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import core.CloneDetector;
import core.Method;
import datastructures.NodeWrapper;
import jgrapht.experimental.dag.DirectedAcyclicGraph;
import jgrapht.graph.DefaultEdge;
import jgrapht.graph.DirectedPseudograph;
import parsers.ControlDependencyParser;
import parsers.ControlFlowParser;
import parsers.SyntaxParser;

public class TestCode {

	private int buildCFG(List<Method> methList) {

		for (int i = 0, j = 2; i < methList.length(); i++) {
			j = 3;
			j++;
		}
		System.out.print("exit");
		return num;
	}
	private int buildCFG(int numbers) {

		while ( numbers < 3 ) {
			j = 3;
			numbers++;
		}
		System.out.print("exit");
		return numbers;
	}

	public int cdgStmt(int num) {
		if (num < 0) {
			num = num + 2; // Check2
		} else {
			num = num - 1;
		}
		System.out.print("exit");
		return num;
	}

	public int ifStmt(int num) {
		if (number < 0) {
			num++; // Check1
		} else {
			num--;
			return num;
		}
		if (number < 0) {
			num = num + 2; // Check2
		}
		return Math.abs(num);

	}

	public int absoluteVal(int number) {
		// hello?
		// System.out.println("functionCall");
		/*
		 * testing comments Mogambo
		 */
		try {
			int temp = 3;
		} catch (IOException e) {
			int temp = 4;
			return;
		}

		if (number < 0) {
			return 0 - number; // Check1
		} else {
			return number;
		} /*
			 * Check2
			 */

	}

	public int absolute(int num) {
		// System.out.println("hello");
		int temp2 = 5;
		if (num < 0) {
			return 0 - num;
		} else {
			return num;
		}

	}

	public int absolute2(int num) {
		System.out.println("hello");
		int temp2 = 5;
		if (num < 0) {
			return 0 - num;
		}
		return num;

	}

	public int absoluteLib(int num) {
		return Math.abs(num);

	}

	public double absoluteLib(double num) {
		return Math.abs(num);
	}

	public int noOfLegs() {
		int test = 3 + 3;
		int test2 = 2;
		return test + test2;
	}

	// Exact copy of version from math, with different variable names
	public static int abs(int x) {
		return (x < 0) ? -x : x;
	}

}
