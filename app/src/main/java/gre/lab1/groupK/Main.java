package gre.lab1.groupK;

import java.io.IOException;

import gre.lab1.graph.DirectedGraph;
import gre.lab1.graph.DirectedGraphReader;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Labo 1 - GRE");
        // Lecture d'un graphe orienté depuis un fichier
        DirectedGraph graph = DirectedGraphReader.fromFile("data/chaine1.txt");

        // Apply the Tarjan algorithm
        TarjanAlgorithm ta = new TarjanAlgorithm();
        System.out.println(ta.compute(graph));

        // TODO
        // - Écrire le code dans les fichiers Main.java, TarjanAlgorithm.java,
        // ContractionAlgorithm.java
        // et UNIQUEMENT ceux-ci ;
        // - Documentation soignée comprenant :
        // - la javadoc, avec auteurs et description des implémentations ;
        // - des commentaires sur les différentes parties de vos algorithmes.

    }
}
