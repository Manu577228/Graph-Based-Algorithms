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

import java.util.*;

public class DFS_BFS_Graph {
    
    // Adjacency List Representation
    static Map<Integer, List<Integer>> graph = new HashMap<>();

    // ---------------- DFS Implementation ----------------
    static void dfs(int start, Set<Integer> visited) {
        visited.add(start);                          // Mark as visited
        System.out.print(start + " ");               // Print current node
        
        for (int neighbor : graph.get(start)) {      // Explore neighbors
            if (!visited.contains(neighbor)) {       // If not visited
                dfs(neighbor, visited);              // Recursive DFS
            }
        }
    }

    // ---------------- BFS Implementation ----------------
    static void bfs(int start) {
        Queue<Integer> q = new LinkedList<>();       // Queue for BFS
        Set<Integer> visited = new HashSet<>();      // Track visited nodes
        
        q.add(start);
        visited.add(start);
        
        while (!q.isEmpty()) {
            int node = q.poll();                     // Dequeue node
            System.out.print(node + " ");            // Print current node
            
            for (int neighbor : graph.get(node)) {   // Explore neighbors
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    q.add(neighbor);                 // Enqueue neighbor
                }
            }
        }
    }

    // ---------------- Main Method ----------------
    public static void main(String[] args) {
        // Build graph
        graph.put(1, Arrays.asList(2, 3));
        graph.put(2, Arrays.asList(1, 4, 5));
        graph.put(3, Arrays.asList(1));
        graph.put(4, Arrays.asList(2));
        graph.put(5, Arrays.asList(2));

        System.out.println("DFS Traversal starting from node 1:");
        dfs(1, new HashSet<>());                     // Start DFS
        
        System.out.println("\nBFS Traversal starting from node 1:");
        bfs(1);                                      // Start BFS
    }
}
