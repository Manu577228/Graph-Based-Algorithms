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
/* -----------------------------------------------------------------------  */

import java.util.*;

public class PrimsAlgorithm {

    // Edge class to represent graph edges
    static class Edge implements Comparable<Edge> {
        int vertex;
        int weight;

        Edge(int vertex, int weight) {
            this.vertex = vertex;
            this.weight = weight;
        }

        @Override
        public int compareTo(Edge other) {
            return this.weight - other.weight; // Sort by weight for PriorityQueue
        }
    }

    // Prim's Algorithm
    public static int primsMST(Map<Integer, List<Edge>> graph, int start) {
        boolean[] visited = new boolean[graph.size()]; // Track visited vertices
        PriorityQueue<Edge> pq = new PriorityQueue<>(); // Min-heap for smallest edges
        pq.add(new Edge(start, 0)); // Start from 'start' vertex with weight 0
        int totalCost = 0;

        while (!pq.isEmpty()) {
            Edge current = pq.poll(); // Get edge with smallest weight
            int v = current.vertex;
            int w = current.weight;

            if (visited[v]) continue; // Skip if vertex already visited

            visited[v] = true; // Mark vertex as visited
            totalCost += w; // Add edge weight to MST total cost

            // Explore all neighbors
            for (Edge neighbor : graph.get(v)) {
                if (!visited[neighbor.vertex]) {
                    pq.add(new Edge(neighbor.vertex, neighbor.weight)); // Add unvisited neighbor to heap
                }
            }
        }

        return totalCost; // Return total weight of MST
    }

    public static void main(String[] args) {
        // Graph represented as adjacency list
        Map<Integer, List<Edge>> graph = new HashMap<>();
        for (int i = 0; i < 4; i++) graph.put(i, new ArrayList<>());

        // Add edges (undirected graph)
        graph.get(0).add(new Edge(1, 2)); graph.get(1).add(new Edge(0, 2));
        graph.get(0).add(new Edge(2, 3)); graph.get(2).add(new Edge(0, 3));
        graph.get(0).add(new Edge(3, 1)); graph.get(3).add(new Edge(0, 1));
        graph.get(1).add(new Edge(3, 2)); graph.get(3).add(new Edge(1, 2));
        graph.get(2).add(new Edge(3, 4)); graph.get(3).add(new Edge(2, 4));

        int mstCost = primsMST(graph, 0); // Start from vertex 0
        System.out.println("Minimum Spanning Tree Cost: " + mstCost);
    }
}
