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
/*    Portfolio : https://manu-bharadwaj-portfolio.vercel.app/portfolio       */
/* -----------------------------------------------------------------------  */

import java.io.*;
import java.util.*;

public class TarjanBridges {

    static class Graph {
        private int V;                               // number of vertices
        private List<Integer>[] adj;                 // adjacency list
        private int time;                            // global timer
        private List<String> bridges;                // store all bridges

        // Constructor: initialize graph
        Graph(int vertices) {
            this.V = vertices;
            adj = new ArrayList[vertices];
            for (int i = 0; i < vertices; i++) {
                adj[i] = new ArrayList<>();
            }
            bridges = new ArrayList<>();
            time = 0;
        }

        // Step 1: Add edge (undirected)
        void addEdge(int u, int v) {
            adj[u].add(v);
            adj[v].add(u);
        }

        // Step 2: DFS utility to find bridges
        private void dfs(int u, int parent, boolean[] visited, int[] disc, int[] low) {
            visited[u] = true;               // mark current node as visited
            disc[u] = low[u] = ++time;       // set discovery & low time

            // explore neighbors
            for (int v : adj[u]) {
                if (v == parent) continue;   // skip the edge to parent

                if (!visited[v]) {           // if v is not visited
                    dfs(v, u, visited, disc, low);

                    // after DFS, update low value of u
                    low[u] = Math.min(low[u], low[v]);

                    // check for bridge condition
                    if (low[v] > disc[u]) {
                        bridges.add("(" + u + ", " + v + ")");
                    }
                } else {
                    // back edge found
                    low[u] = Math.min(low[u], disc[v]);
                }
            }
        }

        // Step 3: Main function to find all bridges
        List<String> findBridges() {
            boolean[] visited = new boolean[V];
            int[] disc = new int[V];
            int[] low = new int[V];

            for (int i = 0; i < V; i++) {
                if (!visited[i]) {
                    dfs(i, -1, visited, disc, low);
                }
            }

            return bridges;
        }
    }

    // ---------------------- DRIVER CODE ----------------------------
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // Example graph:
        // 0 --- 1 --- 2
        //       |
        //       3
        Graph g = new Graph(4);
        g.addEdge(0, 1);
        g.addEdge(1, 2);
        g.addEdge(1, 3);

        List<String> bridges = g.findBridges();

        PrintWriter out = new PrintWriter(System.out);
        out.println("Bridges found: " + bridges);
        out.flush();
    }
}
