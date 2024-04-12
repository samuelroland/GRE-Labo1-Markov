package gre.lab1.groupK;

import gre.lab1.graph.DirectedGraph;
import gre.lab1.graph.GenericAlgorithm;
import gre.lab1.graph.GraphCondensation;
import gre.lab1.graph.GraphScc;
import gre.lab1.graph.SccAlgorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Algorithm that contracts a graph using its SCC
 */
public class ContractionAlgorithm implements GenericAlgorithm<GraphCondensation> {

    private SccAlgorithm sccAlgo;

    /**
     * Create a ContractionAlgorithm with a specified SCC algorithm
     * 
     * @param sccAlgo The algorithm to find the SCC used to contract the graph
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

        DirectedGraph condensationGraph = new DirectedGraph(graphScc.count());

        boolean[][] condSCCAdjMatrix = new boolean[graphScc.count()][graphScc.count()]; // adjacence matrix of condensation graph

        // Allocate final ajdacence list of condensation graph
        List<List<Integer>> mapping = new ArrayList<>(graphScc.count());
        for (int i = 0; i < graphScc.count(); i++) {
            mapping.add(new ArrayList<>());
        }

        // Process the original graph to add edges in the condensation
        for (int originalVertexIndex = 0; originalVertexIndex < scc.length; originalVertexIndex++) {
            for (int successor : graph.getSuccessorList(originalVertexIndex)) {
                // u and v belong to different SCC and these are not connected yet
                // Adds an edge between two SCC in the condensation if, for each edge of the original graph,
                // If the 2 vertices are on different scc and the
                if (scc[originalVertexIndex] != scc[successor]
                        && condSCCAdjMatrix[scc[originalVertexIndex] - 1][scc[successor] - 1] == false) {
                    condensationGraph.addEdge(scc[originalVertexIndex] - 1, scc[successor] - 1);

                    condSCCAdjMatrix[scc[originalVertexIndex] - 1][scc[successor] - 1] = true;
                }
            }
            mapping.get(scc[originalVertexIndex] - 1).add(originalVertexIndex);
        }
        return new GraphCondensation(graph, condensationGraph, mapping);

    }
}
