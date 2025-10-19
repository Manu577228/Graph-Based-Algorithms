# Tarjan’s Algorithm efficiently finds all bridges (critical connections) 
# in a connected undirected graph using Depth First Search (DFS).
# A bridge is an edge whose removal increases the number of connected components in the graph.
# It runs in O(V + E) time using discovery and low-link values.

# Explanation

# During DFS traversal, we assign each node:

# disc[u] → time when the node u was first visited.

# low[u] → earliest visited vertex reachable from u (including back edges).

# For every edge (u, v):

# If v is not visited → DFS deeper.

# After DFS of v, update low[u] = min(low[u], low[v]).

# If low[v] > disc[u] → edge (u, v) is a bridge.

# Essentially, it detects connections that don’t have an alternate path.

from collections import defaultdict

class Graph:
    def __init__(self, vertices):
        self.V = vertices
        self.graph = defaultdict(list)
        self.time = 0
        self.bridges = []

    def add_edge(self, u, v):
        self.graph[u].append(v)
        self.graph[v].append(u)

    def dfs(self, u, parent, visited, disc, low):
        visited[u] = True
        self.time += 1
        disc[u] = low[u] = self.time

        for v in self.graph[u]:
            if v == parent:
                continue
            if not visited[v]:
                self.dfs(v, u, visited, disc, low)

                low[u] = min(low[u], low[v])

                if low[v] > disc[u]:
                    self.bridges.append((u, v))
            else:
                low[u] = min(low[u], disc[v])

    def find_bridges(self):
        visited = [False] * self.V
        disc = [float("inf")] * self.V
        low = [float("inf")] * self.V

        for i in range(self.V):
            if not visited[i]:
                self.dfs(i, -1, visited, disc, low)

        return self.bridges
    
# Example Graph:
# 0 --- 1 --- 2
#       |
#       3
# Removing edge (1, 2) or (1, 3) disconnects the graph -> bridges

g = Graph(4)
g.add_edge(0, 1)
g.add_edge(1, 2)
g.add_edge(1, 3)

result = g.find_bridges()
print("Bridges found:", result)
        