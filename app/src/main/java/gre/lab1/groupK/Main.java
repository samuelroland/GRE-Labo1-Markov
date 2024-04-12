package gre.lab1.groupK;

import java.io.IOException;

import gre.lab1.graph.*;

/**
 * Makes the lab
 */
public class Main {
    /**
     * Takes some files, each one representing a graph, print their SCC with their characteristics.
     * 
     * @param args No arg is needed
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        System.out.println("Labo 1 - GRE");
        final String[] FILES = new String[] { "data/chaine1.txt", "data/chaine2.txt" };
        for (var file : FILES) {
            System.out.println("\n\n>> Calculating the SCC of the graph in " + file + ":");
            // Read the given graph file, parse it and return a DirectedGraph structure
            DirectedGraph graph = DirectedGraphReader.fromFile(file);

            // Apply the GraphCondensation algorigthm using the Tarjan algorithm
            ContractionAlgorithm ca = new ContractionAlgorithm(new TarjanAlgorithm());
            GraphCondensation graphCond = ca.compute(graph);

            // Search for persistant or transitive states of each vertex of the condensated graph
            // and show the scc number, the state, the included vertices and the successors list if persistant
            for (var i = 0; i < graphCond.condensation().getNVertices(); i++) {
                System.out.println("\nStrongly Connected Component: C" + i);
                boolean isPersistant = graphCond.condensation().getSuccessorList(i).isEmpty();
                System.out.println("Statut: " + (isPersistant ? "persistant" : "transitive"));
                System.out.println("Included vertices: " + graphCond.mapping().get(i));
                if (!isPersistant) {
                    System.out.println("Successors list: " + graphCond.condensation().getSuccessorList(i));
                }
            }
        }
        // et UNIQUEMENT ceux-ci ;
        // - Documentation soignée comprenant :
        // - la javadoc, avec auteurs et description des implémentations ;
        // - des commentaires sur les différentes parties de vos algorithmes.

    }
}
