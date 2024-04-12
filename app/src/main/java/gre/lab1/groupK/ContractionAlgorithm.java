package gre.lab1.groupK;

import gre.lab1.graph.DirectedGraph;
import gre.lab1.graph.GenericAlgorithm;
import gre.lab1.graph.GraphCondensation;
import gre.lab1.graph.GraphScc;
import gre.lab1.graph.SccAlgorithm;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Algorithm that contracts a graph using its SCC
 */
public class ContractionAlgorithm implements GenericAlgorithm<GraphCondensation> {

    private SccAlgorithm sccAlgo;

    /**
     * Create a ContractionAlgorithm with a specified SCC algorithm
     * @param sccAlgo The algorithm to find the SCC used to contract the graph
     */
    public ContractionAlgorithm(SccAlgorithm sccAlgo) {
        this.sccAlgo = sccAlgo;
    }

    /**
     * Condenses a graph
     * @param graph The directed graph.
     * @return Then condensation of the graph
     */
    @Override
    public GraphCondensation compute(DirectedGraph graph) {
        GraphScc graphScc = sccAlgo.compute(graph);
        int[] scc = graphScc.components();

        DirectedGraph condensationGraph = new DirectedGraph(graphScc.count());

        int[] list = new int[graphScc.count()];
        List<List<Integer>> mapping = new ArrayList<>(scc.length);
        for (int i = 0; i < scc.length; i++) {
            mapping.add(new ArrayList<>());
        }
        // Processes the original graph to add edges in the condensation
        for (int vertex = 0; vertex < scc.length; vertex++) {
            for (int successor : graph.getSuccessorList(vertex)) {
                // Adds an edge between two SCC in the condensation if, for each edge of the original graph,
                // u and v belong to different SCC and these are not connected yet
                if (scc[vertex] != scc[successor] && list[scc[successor]] != scc[vertex]) {
                    condensationGraph.addEdge(scc[vertex] - 1, scc[successor] - 1);
                    list[scc[successor]] = scc[vertex];
                }
            }
            mapping.get(scc[vertex] - 1).add(vertex);
        }
        return new GraphCondensation(graph, condensationGraph, mapping);

    }
}
