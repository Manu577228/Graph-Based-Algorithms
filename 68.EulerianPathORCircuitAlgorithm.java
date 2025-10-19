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

public class Main {

    // Step 1: Graph class creation
    static class Graph {
        int V;                             // number of vertices
        Map<Integer, List<Integer>> adj;   // adjacency list

        Graph(int v) {
            V = v;
            adj = new HashMap<>();
            for (int i = 0; i < V; i++) {
                adj.put(i, new ArrayList<>());
            }
        }

        // Step 2: Add edges to graph
        void addEdge(int u, int v) {
            adj.get(u).add(v);
            adj.get(v).add(u);              // undirected graph
        }

        // Step 3: Check Eulerian properties
        int isEulerian() {
            int odd = 0;
            for (int v = 0; v < V; v++) {
                if (adj.get(v).size() % 2 != 0)
                    odd++;
            }

            // Step 4: Apply Eulerian Path/Circuit conditions
            if (odd == 0) return 2;   // Eulerian Circuit
            else if (odd == 2) return 1; // Eulerian Path
            else return 0;             // Not Eulerian
        }
    }

    // Step 5: Driver method
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);

        Graph g1 = new Graph(5);
        g1.addEdge(0, 1);
        g1.addEdge(1, 2);
        g1.addEdge(2, 0);
        g1.addEdge(0, 3);
        g1.addEdge(3, 4);

        int res = g1.isEulerian();

        // Step 6: Output result
        if (res == 2)
            out.println("Graph has an Eulerian Circuit");
        else if (res == 1)
            out.println("Graph has an Eulerian Path");
        else
            out.println("Graph is not Eulerian");

        out.flush();
    }
}
