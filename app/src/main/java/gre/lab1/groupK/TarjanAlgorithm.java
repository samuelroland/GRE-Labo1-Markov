package gre.lab1.groupK;

import gre.lab1.graph.DirectedGraph;
import gre.lab1.graph.GraphScc;
import gre.lab1.graph.SccAlgorithm;
import java.util.Stack;
import java.util.Arrays;
import java.util.List;

public final class TarjanAlgorithm implements SccAlgorithm {
    private int n; // counter for dfsnum
    private int k; // counter for components numerotation
    private Stack<Integer> p; // stack to store visited but unclassified vertices
    int VERTICES_NB;
    int[] dfsnum;
    int[] scc;
    int[] low;
    private DirectedGraph graph;

    @Override
    public GraphScc compute(DirectedGraph graph) {
        this.graph = graph;
        VERTICES_NB = graph.getNVertices();
        dfsnum = new int[VERTICES_NB];
        scc = new int[VERTICES_NB];
        low = new int[VERTICES_NB];

        n = 0;
        k = 0;
        p = new Stack<>();

        for (int i = 0; i < VERTICES_NB; i++) {
            if (scc[i] == 0) {
                scc(i);
            }
        }

        return new GraphScc(graph, k, scc);
    }

    // Recursive algorithm to find scc from a given vertice indexed at u
    private void scc(int u) {
        n++;
        dfsnum[u] = n;
        low[u] = n;
        p.push(u);

        for (int v : graph.getSuccessorList(u)) {

            // If v has not been visited, manage it first
            if (dfsnum[v] == 0)
                scc(v);

            // If the scc has not been defined
            if (scc[v] == 0) {
                low[u] = Math.min(low[u], low[v]);
            }
        }

        // If we have found a new scc
        if (low[u] == dfsnum[u]) {
            k++;
            int w = u;

            // Pop all vertices from the stack until we considered all vertices in the
            // k scc, they are all placed at the top of the stack p
            do {
                w = p.pop();
                scc[w] = k;
            } while (w != u);
        }
    }
}
