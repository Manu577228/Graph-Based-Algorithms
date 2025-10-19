# The Hopcroft-Karp Algorithm is an efficient algorithm to find the maximum matching in a bipartite graph. 
# It improves over the simple augmenting path approach
# by finding multiple augmenting paths simultaneously, achieving a time complexity of O(√V * E).

# Explanation

# A bipartite graph consists of two disjoint sets of vertices, say U and V, 
# where edges only connect vertices between sets, not within a set.

# A matching is a set of edges such that no two edges share a vertex.

# The maximum matching is a matching with the largest possible number of edges.

# The Hopcroft-Karp algorithm uses a BFS + DFS strategy:

# BFS (Breadth First Search) to layer the graph and find all shortest augmenting paths.

# DFS (Depth First Search) to augment along these paths in parallel.

# By processing multiple paths at once, it reduces redundant searches, 
# making it much faster than a naive augmenting-path algorithm.

from collections import deque

class HopcroftKarp:
    def __init__(self, U, V):

        self.U = U
        self.V = V
        self.pair_U = [-1] * U  # Stores matched vertex in V for U
        self.pair_V = [-1] * V  # Stores matched vertex in U for V
        self.dist = [0] * U     # Distance labels for BFS
        self.graph = [[] for _ in range(U)]  # adjacency list for U

    def add_edge(self, u, v):
        self.graph[u].append(v)

    def bfs(self):
        queue = deque()
        for u in range(self.U):
            if self.pair_U[u] == -1:
                self.dist[u] = 0
                queue.append(u)
            else:
                self.dist[u] = float('inf')

        found = False
        while queue:
            u = queue.popleft()
            for v in self.graph[u]:
                if self.pair_V[v] == -1:
                    found = True  # Found an augmenting path
                elif self.dist[self.pair_V[v]] == float('inf'):
                    self.dist[self.pair_V[v]] = self.dist[u] + 1
                    queue.append(self.pair_V[v])
        return found

    def dfs(self, u):
        for v in self.graph[u]:
            if self.pair_V[v] == -1 or (self.dist[self.pair_V[v]] == self.dist[u] + 1 and self.dfs(self.pair_V[v])):
                self.pair_U[u] = v
                self.pair_V[v] = u
                return True
        self.dist[u] = float('inf')
        return False

    def max_matching(self):
        result = 0
        while self.bfs():
            for u in range(self.U):
                if self.pair_U[u] == -1:
                    if self.dfs(u):
                        result += 1
        return result


hk = HopcroftKarp(4, 4)
hk.add_edge(0, 0)
hk.add_edge(0, 1)
hk.add_edge(1, 1)
hk.add_edge(2, 2)
hk.add_edge(3, 2)
hk.add_edge(3, 3)

print("Maximum Matching Size:", hk.max_matching())
print("Pairs U -> V:", hk.pair_U)
print("Pairs V -> U:", hk.pair_V)
