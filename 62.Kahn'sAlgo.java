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

public class KahnsAlgorithm {

    // Function implementing Kahn's Algorithm for Topological Sort
    static List<Integer> kahnTopologicalSort(int vertices, int[][] edges) {
        // Step 1: Create adjacency list
        Map<Integer, List<Integer>> graph = new HashMap<>();
        for (int i = 0; i < vertices; i++) graph.put(i, new ArrayList<>());
        for (int[] edge : edges) graph.get(edge[0]).add(edge[1]);
        // Example: if edges are {5,0}, {4,0} => graph = {5:[0], 4:[0]}

        // Step 2: Compute indegree for each vertex
        int[] indegree = new int[vertices];
        for (int u : graph.keySet()) {
            for (int v : graph.get(u)) indegree[v]++;
        }
        // indegree[v] = number of incoming edges to vertex v

        // Step 3: Initialize queue with vertices having indegree = 0
        Queue<Integer> q = new LinkedList<>();
        for (int i = 0; i < vertices; i++) {
            if (indegree[i] == 0) q.offer(i);
        }
        // These are starting points (no dependencies)

        // Step 4: Process queue
        List<Integer> topoOrder = new ArrayList<>();
        while (!q.isEmpty()) {
            int node = q.poll();          // Take one vertex with indegree 0
            topoOrder.add(node);          // Add it to topological order

            // Decrease indegree of all its adjacent vertices
            for (int neighbor : graph.get(node)) {
                indegree[neighbor]--;
                // If indegree becomes 0, add to queue
                if (indegree[neighbor] == 0) q.offer(neighbor);
            }
        }

        // Step 5: Check if topological sort is possible (no cycles)
        if (topoOrder.size() == vertices) return topoOrder;
        return new ArrayList<>(); // Graph has a cycle
    }

    public static void main(String[] args) throws Exception {
        int V = 6;
        int[][] E = {
            {5, 2}, {5, 0}, {4, 0},
            {4, 1}, {2, 3}, {3, 1}
        };

        List<Integer> order = kahnTopologicalSort(V, E);
        System.out.println("Topological Order: " + order);
    }
}
