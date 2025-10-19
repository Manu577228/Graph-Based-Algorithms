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

import java.io.*;
import java.util.*;

public class Main {

    /* -------------------- DSU Class Implementation -------------------- */
    static class DSU {
        int[] parent; // parent[i] points to the parent of node i
        int[] rank;   // rank[i] stores the approximate depth of tree for optimization

        // Constructor: Initialize DSU with 'n' elements
        DSU(int n) {
            parent = new int[n];
            rank = new int[n];

            // Initially, each node is its own parent (individual set)
            for (int i = 0; i < n; i++) {
                parent[i] = i;
                rank[i] = 0;
            }
        }

        // Find operation: returns representative (root) of set containing 'x'
        int find(int x) {
            // If x is not the parent of itself, recursively find its root
            if (parent[x] != x) {
                // Path Compression: make the parent of x point directly to root
                parent[x] = find(parent[x]);
            }
            return parent[x]; // Return the root of the set
        }

        // Union operation: merges sets containing x and y
        void union(int x, int y) {
            int xRoot = find(x); // Find root of x
            int yRoot = find(y); // Find root of y

            // If both elements are already in same set, no need to merge
            if (xRoot == yRoot)
                return;

            // Union by Rank: attach smaller tree under root of taller tree
            if (rank[xRoot] < rank[yRoot]) {
                parent[xRoot] = yRoot;
            } else if (rank[xRoot] > rank[yRoot]) {
                parent[yRoot] = xRoot;
            } else {
                // If ranks are equal, choose one root and increase its rank
                parent[yRoot] = xRoot;
                rank[xRoot]++;
            }
        }
    }

    /* -------------------- Main Method -------------------- */
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);

        // Example: DSU with 5 elements (0 to 4)
        DSU dsu = new DSU(5);

        // Perform some unions
        dsu.union(0, 1); // Merge sets containing 0 and 1
        dsu.union(1, 2); // Merge sets containing 1 and 2
        dsu.union(3, 4); // Merge sets containing 3 and 4

        // Print parent array after unions
        out.println("Parent array after unions: " + Arrays.toString(dsu.parent));

        // Check connectivity between nodes
        out.println("Are 0 and 2 connected? " + (dsu.find(0) == dsu.find(2)));
        out.println("Are 0 and 4 connected? " + (dsu.find(0) == dsu.find(4)));

        out.flush();
    }
}
