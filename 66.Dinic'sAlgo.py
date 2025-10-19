# Dinic’s Algorithm is an efficient method to compute the maximum flow in a flow network.
# It builds a level graph using BFS and then sends blocking flows using DFS until 
# no more augmenting paths exist.

# Explanation

# Imagine we have a network of pipes (edges) connecting tanks (nodes), 
# each pipe having a capacity (maximum flow it can carry).
# Our goal is to push as much flow as possible from a source (s) to a sink (t) 
# without exceeding the pipe capacities.

# Dinic’s Algorithm works in two phases repeatedly:

# Level Graph Construction (BFS) → find shortest distances (in edges) from s to all reachable nodes.

# Blocking Flow Computation (DFS) → send flow only along those shortest paths until we can’t send anymore.

# This process repeats until no augmenting paths remain, giving us the maximum flow.

from collections import deque

class Edge:
    def __init__(self, v, capacity):
        self.v = v
        self.capacity = capacity
        self.flow = 0
        self.rev = None

class Dinic:
    def __init__(self, n):
        self.n = n
        self.graph = [[] for _ in range(n)]

    def add_edge(self, u, v, capacity):
        forward = Edge(v, capacity)
        backward = Edge(u, 0)
        forward.rev = backward
        backward.rev = forward
        self.graph[u].append(forward)
        self.graph[v].append(backward)

    def bfs(self, s, t, level):
        queue = deque([s])
        level[s] = 0
        while queue:
            u = queue.popleft()
            for e in self.graph[u]:
                if level[e.v] < 0 and e.flow < e.capacity:
                    level[e.v] = level[u] + 1
                    queue.append(e.v)
        return level[t] >= 0
    
    def dfs(self, u, t, flow, level, start):
        if u == t:
            return flow
        while start[u] < len(self.graph[u]):
            e = self.graph[u][start[u]]
            if level[e.v] == level[u] + 1 and e.flow < e.capacity:
                curr_flow = min(flow, e.capacity - e.flow)
                pushed = self.dfs(e.v, t, curr_flow, level, start)
                if pushed > 0:
                    e.flow += pushed
                    e.rev.flow -= pushed
                    return pushed
            start[u] += 1
        return 0
    
    def max_flow(self, s, t):
        total = 0
        while True:
            level = [-1] * self.n
            if not self.bfs(s, t, level):
                break
            start = [0] * self.n
            while True:
                flow = self.dfs(s, t, float('inf'), level, start)
                if flow == 0:
                    break
                total += flow
        return total
    
if __name__ == "__main__":
    n = 6
    dinic = Dinic(n)
    dinic.add_edge(0, 1, 16)
    dinic.add_edge(0, 2, 13)
    dinic.add_edge(1, 2, 10)
    dinic.add_edge(2, 1, 4)
    dinic.add_edge(1, 3, 12)
    dinic.add_edge(3, 2, 9)
    dinic.add_edge(2, 4, 14)
    dinic.add_edge(4, 3, 7)
    dinic.add_edge(3, 5, 20)
    dinic.add_edge(4, 5, 4)

    print("Maximum Flow:", dinic.max_flow(0, 5))
        

        