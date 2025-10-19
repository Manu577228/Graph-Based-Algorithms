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

class Edge {
    int v;            // destination vertex
    int capacity;     // edge capacity
    int flow;         // current flow
    Edge rev;         // reverse edge

    Edge(int v, int capacity) {
        this.v = v;
        this.capacity = capacity;
        this.flow = 0;
    }
}

class Dinic {
    private int n;                   // number of vertices
    private List<Edge>[] graph;      // adjacency list

    @SuppressWarnings("unchecked")
    Dinic(int n) {
        this.n = n;
        graph = new ArrayList[n];
        for (int i = 0; i < n; i++) graph[i] = new ArrayList<>();
    }

    // Step 1: Add edge (and reverse edge for residual graph)
    void addEdge(int u, int v, int capacity) {
        Edge a = new Edge(v, capacity);
        Edge b = new Edge(u, 0);
        a.rev = b;
        b.rev = a;
        graph[u].add(a);
        graph[v].add(b);
    }

    // Step 2: Build level graph using BFS
    boolean bfs(int s, int t, int[] level) {
        Arrays.fill(level, -1);
        level[s] = 0;
        Queue<Integer> q = new LinkedList<>();
        q.add(s);
        while (!q.isEmpty()) {
            int u = q.poll();
            for (Edge e : graph[u]) {
                if (level[e.v] < 0 && e.flow < e.capacity) {
                    level[e.v] = level[u] + 1;
                    q.add(e.v);
                }
            }
        }
        return level[t] >= 0;
    }

    // Step 3: DFS to send blocking flow
    int dfs(int u, int t, int flow, int[] level, int[] start) {
        if (u == t) return flow;
        for (; start[u] < graph[u].size(); start[u]++) {
            Edge e = graph[u].get(start[u]);
            if (level[e.v] == level[u] + 1 && e.flow < e.capacity) {
                int currFlow = Math.min(flow, e.capacity - e.flow);
                int pushed = dfs(e.v, t, currFlow, level, start);
                if (pushed > 0) {
                    e.flow += pushed;
                    e.rev.flow -= pushed;
                    return pushed;
                }
            }
        }
        return 0;
    }

    // Step 4: Main max flow function
    int maxFlow(int s, int t) {
        if (s == t) return 0;
        int total = 0;
        int[] level = new int[n];

        while (bfs(s, t, level)) {
            int[] start = new int[n];
            while (true) {
                int flow = dfs(s, t, Integer.MAX_VALUE, level, start);
                if (flow == 0) break;
                total += flow;
            }
        }
        return total;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        Dinic dinic = new Dinic(6);

        dinic.addEdge(0, 1, 16);
        dinic.addEdge(0, 2, 13);
        dinic.addEdge(1, 2, 10);
        dinic.addEdge(2, 1, 4);
        dinic.addEdge(1, 3, 12);
        dinic.addEdge(3, 2, 9);
        dinic.addEdge(2, 4, 14);
        dinic.addEdge(4, 3, 7);
        dinic.addEdge(3, 5, 20);
        dinic.addEdge(4, 5, 4);

        System.out.println("Maximum Flow: " + dinic.maxFlow(0, 5));
    }
}
