package gre.lab1.groupK;

import gre.lab1.graph.DirectedGraph;
import gre.lab1.graph.GraphScc;
import gre.lab1.graph.SccAlgorithm;
import java.util.Stack;

/**
 * Implement the Tarjan's scc algorithm, in a complexity of O(M+N) (N as number of vertices, M as the number of edges)
 */
public final class TarjanAlgorithm implements SccAlgorithm {
    int[] dfsNum; // discovery's number during the DFS, for each vertex (values from 0 to n-1)
    private int dfsCounter; // used to count already found vertices (from 0 to n-1), used in dfsnum
    private int sccCounter; // counter for scc numbering
    private Stack<Integer> verticesStack; // stack to store visited but unclassified vertices
    int VERTICES_NB;
    int[] scc; // array of scc numbers for each vertex, -1 if not defined, starting at 0
    int[] low; // array of low mark for each vertex
    private DirectedGraph graph; // parsed graph

    /**
     * Apply the Tarjan algorithm
     * 
     * @param graph The graph on which to look for the scc
     * @return A graph with its scc
     */
    @Override
    public GraphScc compute(DirectedGraph graph) {
        this.graph = graph;
        VERTICES_NB = graph.getNVertices();
        dfsNum = new int[VERTICES_NB];
        scc = new int[VERTICES_NB];
        // Fill array with -1
        for (int i = 0; i < VERTICES_NB; i++) {
            scc[i] = -1;
        }
        low = new int[VERTICES_NB];

        dfsCounter = 0;
        sccCounter = 0; // values are applied from 0 (and displayed starting at 1)
        verticesStack = new Stack<>();

        for (int i = 0; i < VERTICES_NB; i++) {
            if (scc[i] == -1) {
                scc(i);
            }
        }

        return new GraphScc(graph, sccCounter, scc);
    }

    /**
     * Recursive function for Tarjan's scc algorithm, process the successors of a vertex given its index
     * 
     * @param vertexIndex Index of the vertex
     */
    private void scc(int vertexIndex) {
        dfsCounter++;
        dfsNum[vertexIndex] = dfsCounter; // save discovery number
        low[vertexIndex] = dfsCounter;
        verticesStack.push(vertexIndex);

        for (int successor : graph.getSuccessorList(vertexIndex)) {

            // If successor has not been visited, manage it first
            if (dfsNum[successor] == 0)
                scc(successor);

            // If the scc has not been defined
            if (scc[successor] == -1) {
                low[vertexIndex] = Math.min(low[vertexIndex], low[successor]);
            }
        }

        // If we have found a new scc
        if (low[vertexIndex] == dfsNum[vertexIndex]) {
            int vertexToClassify;

            // Pop all vertices from the stack until we considered all vertices in the
            // scc of number sccCounter, they are all placed at the top of the stack verticesStack
            do {
                vertexToClassify = verticesStack.pop();
                scc[vertexToClassify] = sccCounter;
            } while (vertexToClassify != vertexIndex);
            sccCounter++; // increment after usage to start SCC numbering 0
        }
    }
}
