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

    // Step 1: Check if vertex v can be added to the path
    static boolean isValid(int v, int[][] graph, int[] path, int pos) {
        // If vertex is not connected to the previous one, return false
        if (graph[path[pos - 1]][v] == 0)
            return false;

        // If vertex already exists in path, return false
        for (int i = 0; i < pos; i++)
            if (path[i] == v)
                return false;

        return true;
    }

    // Step 2: Recursive utility to find Hamiltonian Cycle
    static boolean hamiltonianCycleUtil(int[][] graph, int[] path, int pos) {
        // Base case: all vertices are included
        if (pos == graph.length) {
            // Check if last vertex connects to first
            return graph[path[pos - 1]][path[0]] == 1;
        }

        // Try different vertices as next candidate
        for (int v = 1; v < graph.length; v++) {
            if (isValid(v, graph, path, pos)) {
                path[pos] = v;

                // Recurse further
                if (hamiltonianCycleUtil(graph, path, pos + 1))
                    return true;

                // Backtrack if not valid
                path[pos] = -1;
            }
        }
        return false;
    }

    // Step 3: Function to find and print Hamiltonian Cycle
    static void hamiltonianCycle(int[][] graph) {
        int n = graph.length;
        int[] path = new int[n];
        Arrays.fill(path, -1);
        path[0] = 0; // Start at vertex 0

        if (!hamiltonianCycleUtil(graph, path, 1)) {
            System.out.println("No Hamiltonian Cycle exists");
            return;
        }

        // Print the cycle
        System.out.print("Hamiltonian Cycle exists:\n");
        for (int i = 0; i < n; i++)
            System.out.print(path[i] + " ");
        System.out.println(path[0]);
    }

    // Step 4: Example graph
    public static void main(String[] args) {
        int[][] graph = {
                {0, 1, 0, 1, 0},
                {1, 0, 1, 1, 1},
                {0, 1, 0, 0, 1},
                {1, 1, 0, 0, 1},
                {0, 1, 1, 1, 0}
        };

        hamiltonianCycle(graph);
    }
}
