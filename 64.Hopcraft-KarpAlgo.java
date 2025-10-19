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

public class HopcroftKarp {
    int U, V;
    int[] pairU, pairV, dist;
    List<List<Integer>> graph;

    // Constructor
    public HopcroftKarp(int U, int V) {
        this.U = U;
        this.V = V;
        pairU = new int[U];
        pairV = new int[V];
        dist = new int[U];

        Arrays.fill(pairU, -1);
        Arrays.fill(pairV, -1);

        graph = new ArrayList<>();
        for (int i = 0; i < U; i++) {
            graph.add(new ArrayList<>());
        }
    }

    // Add edge from u in U to v in V
    public void addEdge(int u, int v) {
        graph.get(u).add(v);
    }

    // BFS to build layers
    private boolean bfs() {
        Queue<Integer> queue = new LinkedList<>();
        for (int u = 0; u < U; u++) {
            if (pairU[u] == -1) {
                dist[u] = 0;
                queue.add(u);
            } else {
                dist[u] = Integer.MAX_VALUE;
            }
        }

        boolean found = false;
        while (!queue.isEmpty()) {
            int u = queue.poll();
            for (int v : graph.get(u)) {
                if (pairV[v] == -1) {
                    found = true; // Found an augmenting path
                } else if (dist[pairV[v]] == Integer.MAX_VALUE) {
                    dist[pairV[v]] = dist[u] + 1;
                    queue.add(pairV[v]);
                }
            }
        }
        return found;
    }

    // DFS to find augmenting paths
    private boolean dfs(int u) {
        for (int v : graph.get(u)) {
            if (pairV[v] == -1 || (dist[pairV[v]] == dist[u] + 1 && dfs(pairV[v]))) {
                pairU[u] = v;
                pairV[v] = u;
                return true;
            }
        }
        dist[u] = Integer.MAX_VALUE;
        return false;
    }

    // Main method to compute maximum matching
    public int maxMatching() {
        int result = 0;
        while (bfs()) {
            for (int u = 0; u < U; u++) {
                if (pairU[u] == -1) {
                    if (dfs(u)) {
                        result++;
                    }
                }
            }
        }
        return result;
    }

    // Example usage
    public static void main(String[] args) {
        HopcroftKarp hk = new HopcroftKarp(4, 4);
        hk.addEdge(0, 0);
        hk.addEdge(0, 1);
        hk.addEdge(1, 1);
        hk.addEdge(2, 2);
        hk.addEdge(3, 2);
        hk.addEdge(3, 3);

        System.out.println("Maximum Matching Size: " + hk.maxMatching());
        System.out.println("Pairs U -> V: " + Arrays.toString(hk.pairU));
        System.out.println("Pairs V -> U: " + Arrays.toString(hk.pairV));
    }
}
