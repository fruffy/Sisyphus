package parsers;

import java.util.List;

import core.Method;

public class ControlFlowParser {
	List<Method> methList;

	public ControlFlowParser(List<Method> methList) {
		this.methList = methList;
		buildCFG();
	}

	private void buildCFG() {
		for (Method m : methList) {
			ControlFlowMethodParser cfgParse = new ControlFlowMethodParser();
			String s = cfgParse.parse(m.getFilteredBody()).toString();
			System.out.println("\n++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			System.out.println("Control Flow Raw Content " + s);
			System.out.println("\n***************");
		}
	}

}