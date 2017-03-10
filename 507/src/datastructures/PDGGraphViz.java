package datastructures;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import org.jgrapht.Graph;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.io.ComponentNameProvider;
import org.jgrapht.io.DOTExporter;
import org.jgrapht.io.IntegerComponentNameProvider;

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

}
