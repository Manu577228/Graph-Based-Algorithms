import java.io.*;
import java.util.*;

/*
 * Bellman-Ford Algorithm in Java
 * Finds shortest paths from a single source, detects negative-weight cycles
 */

public class BellmanFordAlgo {

    // Class to represent an Edge (u -> v with weight w)
    static class Edge {
        int u, v, w;
        Edge(int u, int v, int w) {
            this.u = u;
            this.v = v;
            this.w = w;
        }
    }

    // Bellman-Ford implementation
    static void bellmanFord(List<Edge> edges, int V, int source) {
        // Step 1: Initialize distances with "infinity"
        int[] dist = new int[V];
        int[] parent = new int[V]; // to reconstruct path
        Arrays.fill(dist, Integer.MAX_VALUE);
        Arrays.fill(parent, -1);

        // Distance to source is zero
        dist[source] = 0;

        // Step 2: Relax all edges (V-1) times
        for (int i = 1; i < V; i++) {
            boolean updated = false; // flag to detect early termination
            for (Edge e : edges) {
                if (dist[e.u] != Integer.MAX_VALUE && dist[e.u] + e.w < dist[e.v]) {
                    dist[e.v] = dist[e.u] + e.w;
                    parent[e.v] = e.u;
                    updated = true;
                }
            }
            if (!updated) break; // No updates → already optimal
        }

        // Step 3: Check for negative-weight cycles
        boolean hasNegCycle = false;
        for (Edge e : edges) {
            if (dist[e.u] != Integer.MAX_VALUE && dist[e.u] + e.w < dist[e.v]) {
                hasNegCycle = true;
                break;
            }
        }

        // Step 4: Output results
        if (hasNegCycle) {
            System.out.println("Negative cycle detected! Shortest paths are undefined.");
            return;
        }

        System.out.println("Vertex | Distance from Source | Path");
        for (int v = 0; v < V; v++) {
            System.out.printf("%6d | %20s | ", v,
                (dist[v] == Integer.MAX_VALUE ? "∞" : dist[v]));
            printPath(parent, v);
            System.out.println();
        }
    }

    // Helper function to print reconstructed path
    static void printPath(int[] parent, int v) {
        if (v == -1) return;
        List<Integer> path = new ArrayList<>();
        int cur = v;
        Set<Integer> seen = new HashSet<>(); // avoid infinite loop
        while (cur != -1) {
            if (seen.contains(cur)) { // detects cycle in parent chain
                System.out.print("(cycle)");
                return;
            }
            seen.add(cur);
            path.add(cur);
            cur = parent[cur];
        }
        Collections.reverse(path);
        for (int i = 0; i < path.size(); i++) {
            System.out.print(path.get(i));
            if (i < path.size() - 1) System.out.print("->");
        }
    }

    public static void main(String[] args) {
        // Example 1: Graph without a negative cycle
        int V = 5;
        List<Edge> edges = new ArrayList<>();

        // Define edges (u, v, w)
        edges.add(new Edge(0, 1, 6));
        edges.add(new Edge(0, 2, 7));
        edges.add(new Edge(1, 2, 5));
        edges.add(new Edge(1, 3, -4));
        edges.add(new Edge(2, 4, -3));
        edges.add(new Edge(4, 3, 7));

        System.out.println("---- Run 1: No Negative Cycle ----");
        bellmanFord(edges, V, 0);

        // Example 2: Add an edge that introduces a negative cycle
        edges.add(new Edge(3, 1, -10)); // creates cycle 1->3->1 with -14 total

        System.out.println("\n---- Run 2: With Negative Cycle ----");
        bellmanFord(edges, V, 0);
    }
}
