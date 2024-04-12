package gre.lab1.groupK;

import gre.lab1.graph.DirectedGraph;
import gre.lab1.graph.GraphScc;
import gre.lab1.graph.SccAlgorithm;
import java.util.Stack;

/**
 * Class that implements the Tarjan's SCC algorithm
 */
public final class TarjanAlgorithm implements SccAlgorithm {
    private int dfsCounter; // counter for dfsnum
    private int sccCounter; // counter for components numerotation
    private Stack<Integer> verticesStack; // stack to store visited but unclassified vertices
    int VERTICES_NB;
    int[] dfsnum;
    int[] scc;
    int[] low;
    private DirectedGraph graph;

    /**
     * Recursive algorithm to find scc from a given vertex indexed at vertexIndex
     * @param graph The graph whose SCC we want to find
     * @return A graph with its SCC
     */
    @Override
    public GraphScc compute(DirectedGraph graph) {
        this.graph = graph;
        VERTICES_NB = graph.getNVertices();
        dfsnum = new int[VERTICES_NB];
        scc = new int[VERTICES_NB];
        low = new int[VERTICES_NB];

        dfsCounter = 0;
        sccCounter = 0;
        verticesStack = new Stack<>();

        for (int i = 0; i < VERTICES_NB; i++) {
            if (scc[i] == 0) {
                scc(i);
            }
        }

        return new GraphScc(graph, sccCounter, scc);
    }

    /**
     * Recursive function for Tarjan's SCC algorithm, takes
     * @param vertexIndex Index of the vertex that is wanted to analyze
     */
    private void scc(int vertexIndex) {
        dfsCounter++;
        dfsnum[vertexIndex] = dfsCounter;
        low[vertexIndex] = dfsCounter;
        verticesStack.push(vertexIndex);

        for (int successor : graph.getSuccessorList(vertexIndex)) {

            // If successor has not been visited, manage it first
            if (dfsnum[successor] == 0)
                scc(successor);

            // If the scc has not been defined
            if (scc[successor] == 0) {
                low[vertexIndex] = Math.min(low[vertexIndex], low[successor]);
            }
        }

        // If we have found a new scc
        if (low[vertexIndex] == dfsnum[vertexIndex]) {
            sccCounter++;
            int vertexToClassify;

            // Pop all vertices from the stack until we considered all vertices in the
            // k scc, they are all placed at the top of the stack p
            do {
                vertexToClassify = verticesStack.pop();
                scc[vertexToClassify] = sccCounter;
            } while (vertexToClassify != vertexIndex);
        }
    }
}
