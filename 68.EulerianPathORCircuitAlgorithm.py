# An Eulerian Path is a trail in a graph that visits every edge exactly once.
# An Eulerian Circuit (or Cycle) is an Eulerian Path that starts and ends at the same vertex.

# Explanation

# In graph theory, Eulerian paths and circuits are used to determine whether a given 
# graph can be traversed without repeating edges.

# A graph has an Eulerian Circuit if all vertices have even degree.

# A graph has an Eulerian Path but not a Circuit if exactly two vertices have odd degree.

# Otherwise, it has neither.

# We can use Depth-First Search (DFS) or Hierholzer’s Algorithm to find such a path or circuit efficiently.

from collections import defaultdict

class Graph:
    def __init__(self, vertices):
        self.V = vertices
        self.graph = defaultdict(list)

    def add_edge(self, u, v):
        self.graph[u].append(v)
        self.graph[v].append(u)

    def is_eulerian(self):
        odd = 0
        for v in self.graph:
            if len(self.graph[v]) % 2 != 0:
                odd += 1

        if odd == 0:
            return 2
        if odd == 2:
            return 1
        else:
            return 0
        
g1 = Graph(5)
g1.add_edge(0, 1)
g1.add_edge(1, 2)
g1.add_edge(2, 0)
g1.add_edge(0, 3)
g1.add_edge(3, 4)

res = g1.is_eulerian()

if res == 2:
    print("Graph has an Eulerian Circuit")
elif res == 1:
    print("Graph has an Eulerian Path")
else:
    print("Graph is not Eulerian")
