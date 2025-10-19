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

public class KruskalsAlgorithm {

    // Step 1: Disjoint Set (Union-Find) class
    static class DisjointSet {
        int[] parent, rank;

        DisjointSet(int n) {
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) parent[i] = i; // Each node is its own parent
        }

        int find(int x) {
            // Find the representative (root) of the set with path compression
            if (parent[x] != x)
                parent[x] = find(parent[x]);
            return parent[x];
        }

        boolean union(int x, int y) {
            // Union by rank
            int rootX = find(x);
            int rootY = find(y);

            if (rootX == rootY) return false; // Already in same set → would form a cycle

            if (rank[rootX] < rank[rootY]) parent[rootX] = rootY;
            else if (rank[rootX] > rank[rootY]) parent[rootY] = rootX;
            else {
                parent[rootY] = rootX;
                rank[rootX]++;
            }
            return true; // Union successful
        }
    }

    // Step 2: Edge class to represent graph edges
    static class Edge implements Comparable<Edge> {
        int u, v, w;
        Edge(int u, int v, int w) {
            this.u = u;
            this.v = v;
            this.w = w;
        }

        // Sort edges by weight
        public int compareTo(Edge other) {
            return Integer.compare(this.w, other.w);
        }
    }

    // Step 3: Kruskal's Algorithm function
    static void kruskal(int n, List<Edge> edges) {
        Collections.sort(edges); // Sort edges by weight
        DisjointSet ds = new DisjointSet(n);

        int mstWeight = 0;
        List<Edge> mstEdges = new ArrayList<>();

        for (Edge e : edges) {
            // Try adding this edge if it doesn’t form a cycle
            if (ds.union(e.u, e.v)) {
                mstWeight += e.w;
                mstEdges.add(e);
            }
        }

        // Step 4: Print the result
        System.out.println("Edges in the Minimum Spanning Tree:");
        for (Edge e : mstEdges) {
            System.out.println(e.u + " -- " + e.v + " == " + e.w);
        }
        System.out.println("Total Weight of MST: " + mstWeight);
    }

    // Step 5: Main method
    public static void main(String[] args) throws Exception {
        int n = 4; // Number of vertices

        // Graph represented as list of edges
        List<Edge> edges = Arrays.asList(
            new Edge(0, 1, 10),
            new Edge(0, 2, 6),
            new Edge(0, 3, 5),
            new Edge(1, 3, 15),
            new Edge(2, 3, 4)
        );

        // Run Kruskal’s Algorithm
        kruskal(n, edges);
    }
}
