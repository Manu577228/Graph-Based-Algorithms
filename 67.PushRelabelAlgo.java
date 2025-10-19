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

import java.io.*;
import java.util.*;

public class PushRelabel {

    int n;                      // Number of vertices
    int[][] capacity;            // Capacity matrix
    int[][] flow;                // Flow matrix
    int[] height;                // Height of vertices
    int[] excess;                // Excess flow at vertices

    // Constructor to initialize graph
    public PushRelabel(int n) {
        this.n = n;
        capacity = new int[n][n];
        flow = new int[n][n];
        height = new int[n];
        excess = new int[n];
    }

    // Function to add edge with capacity
    public void addEdge(int u, int v, int cap) {
        capacity[u][v] = cap;
    }

    // Push operation from u to v
    public void push(int u, int v) {
        int pushFlow = Math.min(excess[u], capacity[u][v] - flow[u][v]);
        flow[u][v] += pushFlow;
        flow[v][u] -= pushFlow;
        excess[u] -= pushFlow;
        excess[v] += pushFlow;
        System.out.println("Pushed " + pushFlow + " from " + u + " to " + v);
    }

    // Relabel operation on vertex u
    public void relabel(int u) {
        int minHeight = Integer.MAX_VALUE;
        for (int v = 0; v < n; v++) {
            if (capacity[u][v] - flow[u][v] > 0) {
                minHeight = Math.min(minHeight, height[v]);
            }
        }
        height[u] = minHeight + 1;
        System.out.println("Relabeled vertex " + u + " to height " + height[u]);
    }

    // Discharge excess flow at vertex u
    public void discharge(int u) {
        while (excess[u] > 0) {
            boolean pushed = false;
            for (int v = 0; v < n; v++) {
                if (capacity[u][v] - flow[u][v] > 0 && height[u] == height[v] + 1) {
                    push(u, v);
                    pushed = true;
                    break;
                }
            }
            if (!pushed) {
                relabel(u);
            }
        }
    }

    // Main function to calculate maximum flow
    public int maxFlow(int s, int t) {
        height[s] = n;  // Set source height to number of vertices
        for (int v = 0; v < n; v++) {
            if (capacity[s][v] > 0) {
                flow[s][v] = capacity[s][v];
                flow[v][s] = -capacity[s][v];
                excess[v] = capacity[s][v];
                excess[s] -= capacity[s][v];
                System.out.println("Initial push from source " + s + " to " + v + " = " + capacity[s][v]);
            }
        }

        // List of active vertices (excluding source and sink)
        Queue<Integer> active = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            if (i != s && i != t && excess[i] > 0) active.add(i);
        }

        // Process active vertices
        while (!active.isEmpty()) {
            int u = active.poll();
            int oldExcess = excess[u];
            discharge(u);
            if (excess[u] > 0) active.add(u);
        }

        // Total flow is sum of flows leaving source
        int maxFlow = 0;
        for (int i = 0; i < n; i++) maxFlow += flow[s][i];
        return maxFlow;
    }

    // Example usage
    public static void main(String[] args) {
        PushRelabel pr = new PushRelabel(4);
        pr.addEdge(0, 1, 100);
        pr.addEdge(0, 2, 100);
        pr.addEdge(1, 2, 1);
        pr.addEdge(1, 3, 100);
        pr.addEdge(2, 3, 100);

        System.out.println("Maximum flow: " + pr.maxFlow(0, 3));
    }
}
