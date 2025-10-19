/* ----------------------------------------------------------------------------  */
/*   ( The Authentic JS/JAVA CodeBuff )
 ___ _                      _              _ 
 | _ ) |_  __ _ _ _ __ _ __| |_ __ ____ _ (_)
 | _ \ ' \/ _` | '_/ _` / _` \ V  V / _` || |
 |___/_||_\__,_|_| \__,_\__,_|\_/\_/\__,_|/ |
                                        |__/ 
 */
/* --------------------------------------------------------------------------   */
/*    Youtube: https://youtube.com/@code-with-Bharadwaj                        */
/*    Github : https://github.com/Manu577228                                  */
/*    Portfolio : https://manu-bharadwaj-portfolio.vercel.app/portfolio      */
/* -----------------------------------------------------------------------  */

import java.util.*;

class Graph {
    int V; // Number of vertices
    List<Integer>[] adj; // Adjacency list

    Graph(int V) {
        this.V = V;
        adj = new ArrayList[V];
        for (int i = 0; i < V; i++) adj[i] = new ArrayList<>();
    }

    void addEdge(int u, int v) {
        adj[u].add(v); // Add directed edge u -> v
    }
}

class TarjanSCC {
    Graph graph;
    int V;
    int index = 0; // DFS discovery time
    int[] indices;
    int[] lowLink;
    boolean[] onStack;
    Deque<Integer> stack;
    List<List<Integer>> sccs;

    TarjanSCC(Graph g) {
        this.graph = g;
        this.V = g.V;
        indices = new int[V];
        Arrays.fill(indices, -1); // Unvisited nodes marked -1
        lowLink = new int[V];
        onStack = new boolean[V];
        stack = new ArrayDeque<>();
        sccs = new ArrayList<>();
    }

    void strongConnect(int v) {
        indices[v] = index;
        lowLink[v] = index;
        index++;
        stack.push(v);
        onStack[v] = true;

        // Explore neighbors
        for (int w : graph.adj[v]) {
            if (indices[w] == -1) { // Node not visited
                strongConnect(w);
                lowLink[v] = Math.min(lowLink[v], lowLink[w]);
            } else if (onStack[w]) { // Node in current stack
                lowLink[v] = Math.min(lowLink[v], indices[w]);
            }
        }

        // Found root of SCC
        if (lowLink[v] == indices[v]) {
            List<Integer> scc = new ArrayList<>();
            int w;
            do {
                w = stack.pop();
                onStack[w] = false;
                scc.add(w);
            } while (w != v);
            sccs.add(scc);
        }
    }

    List<List<Integer>> getSCCs() {
        for (int v = 0; v < V; v++) {
            if (indices[v] == -1) strongConnect(v);
        }
        return sccs;
    }
}

public class Main {
    public static void main(String[] args) {
        Graph g = new Graph(8);
        int[][] edges = {{0,1},{1,2},{2,0},{2,3},{3,4},{4,5},{5,3},{6,5},{6,7}};
        for (int[] e : edges) g.addEdge(e[0], e[1]);

        TarjanSCC tarjan = new TarjanSCC(g);
        List<List<Integer>> sccs = tarjan.getSCCs();
        System.out.println("Strongly Connected Components: " + sccs);
    }
}
