package gre.lab1.groupK;

import gre.lab1.graph.DirectedGraph;
import gre.lab1.graph.GraphScc;
import gre.lab1.graph.SccAlgorithm;
import java.util.Stack;

/**
 * Implement the Tarjan's SCC algorithm
 */
public final class TarjanAlgorithm implements SccAlgorithm {
    int[] dfsnum; // discovery's number during the DFS, for each vertex (values from 0 to n-1)
    private int dfsCounter; // used to count already found vertices (from 0 to n-1), used in dfsnum
    private int sccCounter; // counter for scc numbering
    private Stack<Integer> verticesStack; // stack to store visited but unclassified vertices
    int VERTICES_NB;
    int[] scc; // array of scc numbers for each vertex
    int[] low; // array of low mark for each vertex
    private DirectedGraph graph; // parsed graph

    /**
     * Apply the Tarjan algorithm
     * 
     * @param graph The graph on which to look for the scc
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

        System.out.println("sccCounter" + sccCounter);
        return new GraphScc(graph, sccCounter, scc);
    }

    /**
     * Recursive function for Tarjan's SCC algorithm, process the successors of a vertex given its index
     * 
     * @param vertexIndex Index of the vertex
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
            // scc of number sccCounter, they are all placed at the top of the stack verticesStack
            do {
                vertexToClassify = verticesStack.pop();
                scc[vertexToClassify] = sccCounter;
            } while (vertexToClassify != vertexIndex);
        }
    }
}
