package gre.lab1.groupK;

import java.io.IOException;

import gre.lab1.graph.DirectedGraph;
import gre.lab1.graph.DirectedGraphReader;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Labo 1 - GRE");
        final String[] FILES = new String[] { "data/chaine1.txt", "data/chaine2.txt" };
        for (var file : FILES) {
            System.out.println("\n\nCalculating the components of the graph in " + file + ":");
            // Lecture d'un graphe orienté depuis un fichier
            DirectedGraph graph = DirectedGraphReader.fromFile(file);

            // Apply the Tarjan algorithm
            TarjanAlgorithm ta = new TarjanAlgorithm();
            System.out.println(ta.compute(graph));

        }
        // TODO
        // - Écrire le code dans les fichiers Main.java, TarjanAlgorithm.java,
        // ContractionAlgorithm.java
        // et UNIQUEMENT ceux-ci ;
        // - Documentation soignée comprenant :
        // - la javadoc, avec auteurs et description des implémentations ;
        // - des commentaires sur les différentes parties de vos algorithmes.

    }
}
