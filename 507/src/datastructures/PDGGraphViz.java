package datastructures;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import org.jgrapht.Graph;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.io.ComponentNameProvider;
import org.jgrapht.io.DOTExporter;
import org.jgrapht.io.IntegerComponentNameProvider;
import com.github.javaparser.ast.Node;

public class PDGGraphViz {
	
	private static class NodeWrapperName implements ComponentNameProvider<NodeWrapper>{

		@Override
		public String getName(datastructures.NodeWrapper component) {
			String ret = component.NODE.toString();
			if (component.NODE.getRange().isPresent()){
				ret = component.NODE.getRange().get().toString() + ": " + ret;
			}
			return ret;
		}
	
	
	}
	
	private static class NodeName implements ComponentNameProvider<Node>{

		@Override
		public String getName(Node component) {
			String ret = component.toString();
			if (component.getRange().isPresent()){
				ret = component.getRange().get().toString() + ": " + ret;
			}
			return ret;
		}
	
	
	}
	
	public static void writeDot(Graph<NodeWrapper, DefaultEdge> g, String filename){
    	Writer writer;
		try {
			writer = new FileWriter(filename);
			
			ComponentNameProvider<NodeWrapper> vertexNames = new NodeWrapperName();
			
			DOTExporter<NodeWrapper, DefaultEdge> export = 
	    			new DOTExporter<NodeWrapper, DefaultEdge>(new IntegerComponentNameProvider<>(), vertexNames, null);
	    	
			export.exportGraph(g, writer);
			writer.close();
		} catch (IOException e) {
			System.err.println("Couldn't write graph to file " + filename);
		}
    	
    	
    	
    }
	
	public static void writeDotNode(Graph<Node, DefaultEdge> g, String filename){
    	Writer writer;
		try {
			writer = new FileWriter(filename);
			
			ComponentNameProvider<Node> vertexNames = new NodeName();
			
			DOTExporter<Node, DefaultEdge> export = 
	    			new DOTExporter<Node, DefaultEdge>(new IntegerComponentNameProvider<>(), vertexNames, null);
	    	
			//export.exportGraph(g, writer);
			writer.close();
		} catch (IOException e) {
			System.err.println("Couldn't write graph to file " + filename);
		}
    	
    	
    	
    }

}
