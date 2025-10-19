# Tarjan’s Algorithm is used to find Strongly Connected Components (SCCs) in a directed graph.
# An SCC is a maximal set of vertices where every vertex is 
# reachable from every other vertex in the set.

# Explanation

# Tarjan’s algorithm is DFS-based.

# It maintains discovery times and a low-link value for each node:

# discovery_time[v]: Time when node v was first visited.

# low_link[v]: The smallest discovery time reachable from v, including itself.

# A stack keeps track of the nodes in the current DFS path.

# Whenever a node's low_link is equal to its discovery_time, we have found a root 
# of an SCC, and we pop all nodes from the stack until we reach this node.

# The algorithm efficiently finds all SCCs in O(V + E) time.

from collections import defaultdict

class Graph:
    def __init__(self, vertices):
        self.V = vertices
        self.graph = defaultdict(list)

    def add_edge(self, u, v):
        self.graph[u].append(v)
    
class TarjanSCC:
    def __init__(self, graph):
        self.graph = graph
        self.V = graph.V
        self.index = 0
        self.stack = []
        self.on_stack = [False] * self.V
        self.indices = [-1] * self.V
        self.low_link = [-1] * self.V
        self.sccs = []

    def strongconnect(self, v):
        self.indices[v] = self.index
        self.low_link[v] = self.index
        self.index += 1
        self.stack.append(v)
        self.on_stack[v] = True

        for w in self.graph.graph[v]:
            if self.indices[w] == -1:
                self.strongconnect(w)
                self.low_link[v] = min(self.low_link[v], self.low_link[w])
            elif self.on_stack[w]:
                self.low_link[v] = min(self.low_link[v], self.indices[w])

        if self.low_link[v] == self.indices[v]:
            scc = []
            while True:
                w = self.stack.pop()
                self.on_stack[w] = False
                scc.append(w)
                if w == v:
                    break
            self.sccs.append(scc)

    def get_sccs(self):
        for v in range(self.V):
            if self.indices[v] == -1:
                self.strongconnect(v)
        return self.sccs
    
g = Graph(8)
edges = [(0,1),(1,2),(2,0),(2,3),(3,4),(4,5),(5,3),(6,5),(6,7)]
for u,v in edges:
    g.add_edge(u,v)

tarjan = TarjanSCC(g)
sccs = tarjan.get_sccs()
print("Strongly Connected Components:", sccs)

         
      


        

