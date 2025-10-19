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

public class FloydWarshallDemo {
    public static void main(String[] args) {

        // Step 1: Define a large number to represent "infinity" for unreachable nodes
        int INF = 99999;

        // Step 2: Define the graph as an adjacency matrix
        int[][] graph = {
            {0, 5, INF, 10},
            {INF, 0, 3, INF},
            {INF, INF, 0, 1},
            {INF, INF, INF, 0}
        };

        // Step 3: Number of vertices in the graph
        int V = graph.length;

        // Step 4: Create a distance matrix (initially a copy of the graph)
        int[][] dist = new int[V][V];
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                dist[i][j] = graph[i][j];
            }
        }

        // Step 5: Apply Floyd Warshall algorithm
        for (int k = 0; k < V; k++) {             // For each intermediate vertex
            for (int i = 0; i < V; i++) {         // For each source vertex
                for (int j = 0; j < V; j++) {     // For each destination vertex
                    // If the path through vertex k is shorter, update dist[i][j]
                    if (dist[i][k] + dist[k][j] < dist[i][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                    }
                }
            }
        }

        // Step 6: Print the final shortest distance matrix
        System.out.println("Shortest distances between every pair of vertices:");
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                if (dist[i][j] == INF)
                    System.out.print("INF ");
                else
                    System.out.print(dist[i][j] + " ");
            }
            System.out.println();
        }
    }
}
