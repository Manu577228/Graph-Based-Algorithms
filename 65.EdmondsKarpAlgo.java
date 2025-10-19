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

public class EdmondsKarp {
    
    // BFS to find augmenting path
    static int[] bfs(int[][] capacity, int[][] flow, int source, int sink) {
        int n = capacity.length;
        int[] parent = new int[n];  
        Arrays.fill(parent, -1);  // Initialize parent array
        Queue<Integer> queue = new LinkedList<>();
        queue.add(source);
        
        while (!queue.isEmpty()) {
            int u = queue.poll();
            for (int v = 0; v < n; v++) {
                // If residual capacity exists and v not visited
                if (capacity[u][v] - flow[u][v] > 0 && parent[v] == -1) {
                    parent[v] = u;
                    queue.add(v);
                    if (v == sink) {
                        return parent;  // Path found
                    }
                }
            }
        }
        return null;  // No path exists
    }
    
    static int edmondsKarp(int[][] capacity, int source, int sink) {
        int n = capacity.length;
        int[][] flow = new int[n][n];  // Initialize flow matrix with 0
        int maxFlow = 0;
        
        while (true) {
            int[] parent = bfs(capacity, flow, source, sink);  // Find path
            if (parent == null) break;  // No more augmenting path
            
            // Find minimum capacity along the path
            int pathFlow = Integer.MAX_VALUE;
            int v = sink;
            while (v != source) {
                int u = parent[v];
                pathFlow = Math.min(pathFlow, capacity[u][v] - flow[u][v]);
                v = u;
            }
            
            // Update the flows along the path
            v = sink;
            while (v != source) {
                int u = parent[v];
                flow[u][v] += pathFlow;
                flow[v][u] -= pathFlow;  // Update reverse flow
                v = u;
            }
            
            maxFlow += pathFlow;  // Add path flow to total
        }
        
        return maxFlow;
    }
    
    public static void main(String[] args) {
        int[][] capacity = {  // Capacity matrix
            {0, 16, 13, 0, 0, 0},
            {0, 0, 10, 12, 0, 0},
            {0, 4, 0, 0, 14, 0},
            {0, 0, 9, 0, 0, 20},
            {0, 0, 0, 7, 0, 4},
            {0, 0, 0, 0, 0, 0}
        };
        
        int source = 0;
        int sink = 5;
        
        System.out.println("Maximum Flow: " + edmondsKarp(capacity, source, sink));
    }
}
