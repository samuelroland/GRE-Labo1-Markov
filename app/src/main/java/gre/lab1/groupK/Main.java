package gre.lab1.groupK;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import gre.lab1.graph.*;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Labo 1 - GRE");
        final String[] FILES = new String[] { "data/chaine1.txt", "data/chaine2.txt" };
        for (var file : FILES) {
            System.out.println("\n\n>> Calculating the SCC of the graph in " + file + ":");
            // Lecture d'un graphe orienté depuis un fichier
            DirectedGraph graph = DirectedGraphReader.fromFile(file);

            // Apply the GraphCondensation algorigthm using the Tarjan algorithm
            ContractionAlgorithm ca = new ContractionAlgorithm(new TarjanAlgorithm());
            GraphCondensation graphCond = ca.compute(graph);

            // rechercher les états persistants ou transitoires
            // si degré sortant = 1 et que c'est une boucle alors c'est un état persistant
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
        // TODO
        // - Écrire le code dans les fichiers Main.java, TarjanAlgorithm.java,
        // ContractionAlgorithm.java
        // et UNIQUEMENT ceux-ci ;
        // - Documentation soignée comprenant :
        // - la javadoc, avec auteurs et description des implémentations ;
        // - des commentaires sur les différentes parties de vos algorithmes.

    }
}
