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

public class DijkstraAlgorithm {

    // Pair class to store (node, weight)
    static class Pair {
        int node, weight;
        Pair(int node, int weight) {
            this.node = node;
            this.weight = weight;
        }
    }

    // Dijkstra's Algorithm Function
    static int[] dijkstra(List<List<Pair>> graph, int start) {
        int n = graph.size(); // total vertices
        int[] dist = new int[n]; // store shortest distances

        // Initialize all distances as infinity
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[start] = 0; // distance to source = 0

        // PriorityQueue to get minimum distance node first
        PriorityQueue<Pair> pq = new PriorityQueue<>(Comparator.comparingInt(p -> p.weight));
        pq.offer(new Pair(start, 0));

        while (!pq.isEmpty()) {
            // Extract the node with smallest distance
            Pair curr = pq.poll();
            int u = curr.node;
            int currentDist = curr.weight;

            // Skip if outdated distance
            if (currentDist > dist[u]) continue;

            // Explore all adjacent vertices
            for (Pair edge : graph.get(u)) {
                int v = edge.node;
                int newDist = currentDist + edge.weight;

                // If found a shorter path, update it
                if (newDist < dist[v]) {
                    dist[v] = newDist;
                    pq.offer(new Pair(v, newDist));
                }
            }
        }
        return dist;
    }

    public static void main(String[] args) throws IOException {

        // Create a graph as an adjacency list
        List<List<Pair>> graph = new ArrayList<>();

        // Number of vertices
        int vertices = 4;
        for (int i = 0; i < vertices; i++) graph.add(new ArrayList<>());

        // Adding edges (u, v, w)
        graph.get(0).add(new Pair(1, 4));
        graph.get(0).add(new Pair(2, 1));
        graph.get(1).add(new Pair(3, 1));
        graph.get(2).add(new Pair(1, 2));
        graph.get(2).add(new Pair(3, 5));

        // Run Dijkstra from source vertex 0
        int[] result = dijkstra(graph, 0);

        // Print final shortest distances
        System.out.print("Shortest distances from source 0: ");
        System.out.println(Arrays.toString(result));
    }
}
