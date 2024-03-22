package gre.lab1.groupK;

import gre.lab1.graph.DirectedGraph;
import gre.lab1.graph.GenericAlgorithm;
import gre.lab1.graph.GraphCondensation;
import gre.lab1.graph.GraphScc;

import java.util.ArrayList;
import java.util.List;

public class ContractionAlgorithm implements GenericAlgorithm<GraphCondensation> {


    @Override
    public GraphCondensation compute(DirectedGraph graph) {

        TarjanAlgorithm tarjan = new TarjanAlgorithm();
        GraphScc graphScc = tarjan.compute(graph);
        int[] scc = graphScc.components();

        DirectedGraph condensationGraph = new DirectedGraph(graphScc.count());

        int[] list = new int[graphScc.count()];
        // Parcours du graphe d'origine pour ajouter les arêtes dans le graphe condensé
        for (int vertex = 0; vertex < scc.length; vertex++) {
            for (int successor : graph.getSuccessorList(vertex)) {
                //TODO demander l'avis du prof pour le numéro des indexes. # des SCC commence à 1 alors que les indexes des sommets commencent à 0
                //TODO demander pour passer un GraphScc en paramètre -> avoir accès à la liste des SCC ici. pas possible avec DirectedGraph.
                //si pour chaque paire de sommet dans le graphe d'origine, u et v appartiennent à des composantes fortement connexes différentes,
                // alors on ajoute une arête entre les composantes fortement connexes de u et v dans le graphe condensé
                if (scc[vertex] != scc[successor] && list[scc[successor]] != scc[vertex]) {
                    condensationGraph.addEdge(scc[vertex] - 1, scc[successor] - 1);
                    list[scc[successor]] = scc[vertex];
                }
            }
        }

        // Création du mapping entre les sommets du graphe d'origine et les composantes fortement connexes
        List<List<Integer>> mapping = new ArrayList<>();
        for (int i = 0; i < graphScc.count(); i++) {
            mapping.add(new ArrayList<>());
        }
        for (int vertex = 0; vertex < scc.length; vertex++) {
            mapping.get(scc[vertex] - 1).add(vertex);
        }
        return new GraphCondensation(graph, condensationGraph, mapping);

    }
}
