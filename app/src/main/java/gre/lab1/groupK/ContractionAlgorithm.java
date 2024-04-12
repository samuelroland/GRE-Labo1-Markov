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
        // Parcours du graphe d'origine pour ajouter les arêtes dans le graphe condensé
        for (int vertex = 0; vertex < scc.length; vertex++) {
            for (int successor : graph.getSuccessorList(vertex)) {
                // si pour chaque paire de sommet dans le graphe d'origine, u et v appartiennent à des composantes fortement connexes différentes,
                // alors on ajoute une arête entre les composantes fortement connexes de u et v dans le graphe condensé
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
