package parsers;

import java.util.LinkedList;
import java.util.List;

import com.github.javaparser.ast.Node;

import core.Method;
import jgrapht.experimental.dag.DirectedAcyclicGraph;
import jgrapht.graph.DefaultEdge;

/**
 * @author Ruffy
 *
 */
public class ControlFlowListParser {
	List<DirectedAcyclicGraph<Node, DefaultEdge>> cfgList;

	public ControlFlowListParser(List<Method> methList) {
		cfgList = new LinkedList<DirectedAcyclicGraph<Node, DefaultEdge>>();
		buildCFG(methList);
	}

	private void buildCFG(List<Method> methList) {

		for (Method m : methList) {

			ControlFlowParser cfgParse = new ControlFlowParser(m);
			DirectedAcyclicGraph<Node, DefaultEdge> dag = cfgParse.getCFG();
			this.cfgList.add(dag);

			// Debug
			System.out.println(m.getFilteredBody().toString());
			System.out.println("\n++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			System.out.println("Control Flow Raw Content ");

			for (DefaultEdge e : dag.edgeSet()) {
				System.out.println(dag.getEdgeSource(e) + " --> " + dag.getEdgeTarget(e));
			}
			System.out.println("\n***************");

		}
	}

	/**
	 * @return the cfgList
	 */
	public List<DirectedAcyclicGraph<Node, DefaultEdge>> getCfgList() {
		return cfgList;
	}

	/**
	 * Print out a structured list of the graphs associated with the list parser
	 */
	public void printContainedGraphs() {

		for (DirectedAcyclicGraph<Node, DefaultEdge> cfg : cfgList) {

			System.out.println("\n++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			System.out.println("Control Flow Raw Content ");

			for (DefaultEdge e : cfg.edgeSet()) {
				System.out.println(cfg.getEdgeSource(e) + " --> " + cfg.getEdgeTarget(e));
			}
			System.out.println("\n***************");
		}
	}
}