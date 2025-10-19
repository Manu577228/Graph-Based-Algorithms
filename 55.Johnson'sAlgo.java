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
/*    Portfolio : https://manu-bharadwaj-portfolio.vercel.app/portfolio        */
/* -----------------------------------------------------------------------  */
/*    Online Compiler (Java): https://onecompiler.com/java                     */
/*    Online Compiler (JS)  : https://onecompiler.com/javascript               */
/*    Code Formatter (TP)   : https://www.tutorialspoint.com/online_java_formatter.htm */
/* -----------------------------------------------------------------------  */

import java.util.*;

public class JohnsonAlgorithm {

    // Step 1: Bellman-Ford Algorithm
    static int[] bellmanFord(List<List<int[]>> graph, int V, int src) {
        int[] dist = new int[V];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[src] = 0;

        // Relax edges (V - 1) times
        for (int i = 0; i < V - 1; i++) {
            for (int u = 0; u < V; u++) {
                for (int[] edge : graph.get(u)) {
                    int v = edge[0], w = edge[1];
                    if (dist[u] != Integer.MAX_VALUE && dist[u] + w < dist[v]) {
                        dist[v] = dist[u] + w;
                    }
                }
            }
        }

        // Check for negative weight cycles
        for (int u = 0; u < V; u++) {
            for (int[] edge : graph.get(u)) {
                int v = edge[0], w = edge[1];
                if (dist[u] != Integer.MAX_VALUE && dist[u] + w < dist[v]) {
                    System.out.println("Graph contains negative weight cycle");
                    return null;
                }
            }
        }
        return dist;
    }

    // Step 2: Dijkstra's Algorithm
    static int[] dijkstra(List<List<int[]>> graph, int src) {
        int V = graph.size();
        int[] dist = new int[V];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[src] = 0;

        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
        pq.add(new int[]{0, src});

        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int d = curr[0], u = curr[1];
            if (d > dist[u]) continue;

            for (int[] edge : graph.get(u)) {
                int v = edge[0], w = edge[1];
                if (dist[u] != Integer.MAX_VALUE && dist[u] + w < dist[v]) {
                    dist[v] = dist[u] + w;
                    pq.add(new int[]{dist[v], v});
                }
            }
        }
        return dist;
    }

    // Step 3: Johnson’s Algorithm
    static int[][] johnsonsAlgorithm(List<List<int[]>> originalGraph) {
        int V = originalGraph.size();

        // Add a new vertex connected to all vertices with weight 0
        List<List<int[]>> newGraph = new ArrayList<>();
        for (List<int[]> list : originalGraph) {
            newGraph.add(new ArrayList<>(list));
        }
        List<int[]> newEdges = new ArrayList<>();
        for (int v = 0; v < V; v++) {
            newEdges.add(new int[]{v, 0});
        }
        newGraph.add(newEdges);

        // Run Bellman-Ford from the new vertex
        int[] h = bellmanFord(newGraph, V + 1, V);
        if (h == null) return null;

        // Reweight edges
        List<List<int[]>> reweightedGraph = new ArrayList<>();
        for (int u = 0; u < V; u++) {
            List<int[]> edges = new ArrayList<>();
            for (int[] edge : originalGraph.get(u)) {
                int v = edge[0], w = edge[1];
                edges.add(new int[]{v, w + h[u] - h[v]});
            }
            reweightedGraph.add(edges);
        }

        // Run Dijkstra for each vertex
        int[][] allPairs = new int[V][V];
        for (int u = 0; u < V; u++) {
            int[] dist = dijkstra(reweightedGraph, u);
            for (int v = 0; v < V; v++) {
                if (dist[v] < Integer.MAX_VALUE)
                    dist[v] = dist[v] + h[v] - h[u];
            }
            allPairs[u] = dist;
        }
        return allPairs;
    }

    // Driver Code
    public static void main(String[] args) {
        // Example Graph (Directed)
        // 0 → 1 (1), 0 → 2 (4)
        // 1 → 2 (-2)
        // 2 → 3 (2)
        // 3 → 1 (3)

        List<List<int[]>> graph = new ArrayList<>();
        graph.add(Arrays.asList(new int[]{1, 1}, new int[]{2, 4}));
        graph.add(Arrays.asList(new int[]{2, -2}));
        graph.add(Arrays.asList(new int[]{3, 2}));
        graph.add(Arrays.asList(new int[]{1, 3}));

        int[][] result = johnsonsAlgorithm(graph);

        System.out.println("All Pairs Shortest Paths:");
        for (int i = 0; i < result.length; i++) {
            System.out.print("From vertex " + i + ": ");
            System.out.println(Arrays.toString(result[i]));
        }
    }
}
