package parsers;

import java.util.List;

import com.github.javaparser.ast.Node;

import core.Method;
import jgrapht.experimental.dag.DirectedAcyclicGraph;
import jgrapht.graph.DefaultEdge;

public class ControlFlowParser {
	List<Method> methList;

	public ControlFlowParser(List<Method> methList) {
		this.methList = methList;
		buildCFG();
	}

	private void buildCFG() {

		for (Method m : methList) {

			ControlFlowMethodParser cfgParse = new ControlFlowMethodParser(m);
			DirectedAcyclicGraph<Node, DefaultEdge> dag = cfgParse.getCFG();

			System.out.println("\n++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			for (DefaultEdge e : dag.edgeSet()) {
				System.out.println(dag.getEdgeSource(e) + " --> " + dag.getEdgeTarget(e));
			}
			// System.out.println("Control Flow Raw Content " + s);
			System.out.println("\n***************");
		}
	}

}