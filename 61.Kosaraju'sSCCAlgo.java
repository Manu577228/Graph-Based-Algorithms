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

public class KosarajuSCC {

    // Step 1: DFS and fill stack with finishing times
    static void dfs(int v, boolean[] visited, Stack<Integer> stack, List<List<Integer>> g) {
        visited[v] = true;  // Mark current node as visited
        for (int u : g.get(v)) { // Visit all neighbors
            if (!visited[u]) {
                dfs(u, visited, stack, g);
            }
        }
        stack.push(v);  // Push vertex to stack after visiting all neighbors
    }

    // Step 2: Transpose the graph
    static List<List<Integer>> transposeGraph(List<List<Integer>> g) {
        int n = g.size();
        List<List<Integer>> gt = new ArrayList<>();
        for (int i = 0; i < n; i++) gt.add(new ArrayList<>());
        for (int v = 0; v < n; v++) {
            for (int u : g.get(v)) {
                gt.get(u).add(v); // Reverse the edge
            }
        }
        return gt;
    }

    // Step 3: DFS on transposed graph to find SCC
    static void dfsTransposed(int v, boolean[] visited, List<List<Integer>> gt, List<Integer> component) {
        visited[v] = true;
        component.add(v);
        for (int u : gt.get(v)) {
            if (!visited[u]) {
                dfsTransposed(u, visited, gt, component);
            }
        }
    }

    // Kosaraju's algorithm
    static List<List<Integer>> kosarajuSCC(List<List<Integer>> g) {
        int n = g.size();
        boolean[] visited = new boolean[n];
        Stack<Integer> stack = new Stack<>();

        // Phase 1: Fill stack with vertices by finishing times
        for (int i = 0; i < n; i++) {
            if (!visited[i]) dfs(i, visited, stack, g);
        }

        // Phase 2: Transpose the graph
        List<List<Integer>> gt = transposeGraph(g);

        // Phase 3: DFS on transposed graph in stack order
        visited = new boolean[n];
        List<List<Integer>> sccs = new ArrayList<>();

        while (!stack.isEmpty()) {
            int v = stack.pop();
            if (!visited[v]) {
                List<Integer> component = new ArrayList<>();
                dfsTransposed(v, visited, gt, component);
                sccs.add(component); // Add SCC to list
            }
        }

        return sccs;
    }

    public static void main(String[] args) {
        // Graph as adjacency list
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < 5; i++) graph.add(new ArrayList<>());
        graph.get(0).add(1);
        graph.get(1).add(2);
        graph.get(2).add(0);
        graph.get(2).add(3);
        graph.get(3).add(4);

        List<List<Integer>> result = kosarajuSCC(graph);
        System.out.println("Strongly Connected Components: " + result);
    }
}
