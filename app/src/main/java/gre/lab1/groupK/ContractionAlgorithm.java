package gre.lab1.groupK;

import gre.lab1.graph.DirectedGraph;
import gre.lab1.graph.GenericAlgorithm;
import gre.lab1.graph.GraphCondensation;
import gre.lab1.graph.GraphScc;
import gre.lab1.graph.SccAlgorithm;

import java.util.ArrayList;
import java.util.List;

/**
 * Algorithm that contracts a graph using its scc
 */
public class ContractionAlgorithm implements GenericAlgorithm<GraphCondensation> {

    private SccAlgorithm sccAlgo;

    /**
     * Create a ContractionAlgorithm with a specified scc algorithm
     * 
     * @param sccAlgo The algorithm to find the scc used to contract the graph
     */
    public ContractionAlgorithm(SccAlgorithm sccAlgo) {
        this.sccAlgo = sccAlgo;
    }

    /**
     * Condense a graph
     * 
     * @param graph The directed graph.
     * @return The condensation of the graph
     */
    @Override
    public GraphCondensation compute(DirectedGraph graph) {
        GraphScc graphScc = sccAlgo.compute(graph);
        int[] scc = graphScc.components(); // array of scc numbers for each vertex

        // Create an empty graph as the final condensation graph
        DirectedGraph condensationGraph = new DirectedGraph(graphScc.count());

        // Adjacence matrix of condensation graph
        boolean[][] condSCCAdjMatrix = new boolean[graphScc.count()][graphScc.count()];

        // Allocate the final mapping from scc to original vertices
        List<List<Integer>> mapping = new ArrayList<>(graphScc.count());
        for (int i = 0; i < graphScc.count(); i++) {
            mapping.add(new ArrayList<>());
        }

        // Process the original graph to add edges in the condensation
        // For each vertex, we look at each successor
        for (int originalVertex = 0; originalVertex < scc.length; originalVertex++) {
            for (int successor : graph.getSuccessorList(originalVertex)) {

                // If the 2 vertices are on different scc and there is not an existing
                // connection between the 2 scc
                if (scc[originalVertex] != scc[successor]
                        && condSCCAdjMatrix[scc[originalVertex]][scc[successor]] == false) {

                    // We add a new edge in condensationGraph and take note of new edge in condSCCAdjMatrix
                    condensationGraph.addEdge(scc[originalVertex], scc[successor]);
                    condSCCAdjMatrix[scc[originalVertex]][scc[successor]] = true;
                }
            }

            // Add the current vertex in the list of vertices of the associated scc
            mapping.get(scc[originalVertex]).add(originalVertex);
        }

        return new GraphCondensation(graph, condensationGraph, mapping);
    }
}
