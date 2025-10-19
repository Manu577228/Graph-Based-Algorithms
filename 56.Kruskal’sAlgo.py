# Kruskal’s Algorithm is a Greedy algorithm used to find the
# Minimum Spanning Tree (MST) of a weighted, undirected graph.
# It works by sorting all edges in increasing order of weight and a
# dding them one by one, ensuring no cycles are formed.

# Explanation

# Let’s break down how Kruskal’s Algorithm works step-by-step:

# Sort all edges of the graph based on their weights in ascending order.

# Initialize a disjoint set (Union-Find) to keep track of connected components.

# Iterate through sorted edges:

# If the edge connects two different components, include it in the MST.

# If it forms a cycle, skip it.

# Repeat until you have V−1 edges in your MST (where V is the number of vertices).

# The total weight of included edges gives the minimum cost.

class DisjointSet:
    def __init__(self, n):
        self.parent = [i for i in range(n)]
        self.rank = [0] * n

    def find(self, x):
        if self.parent[x] != x:
            self.parent[x] = self.find(self.parent[x])
        return self.parent[x]

    def union(self, x, y):
        rootX = self.find(x)
        rootY = self.find(y)
        if rootX != rootY:
            if self.rank[rootX] < self.rank[rootY]:
                self.parent[rootX] = rootY
            elif self.rank[rootX] > self.rank[rootY]:
                self.parent[rootY] = rootX
            else:
                self.parent[rootY] = rootX
                self.rank[rootX] += 1
            return True
        return False

def kruskal(n, edges):
        edges.sort(key=lambda x: x[2])

        ds = DisjointSet(n)
        mst_weight = 0
        mst_edges = []

        for u, v, w in edges:
            if ds.union(u, v):
                mst_weight += w
                mst_edges.append((u, v, w))

        return mst_weight, mst_edges


edges = [
    (0, 1, 10),
    (0, 2, 6),
    (0, 3, 5),
    (1, 3, 15),
    (2, 3, 4)
]
n = 4 


mst_weight, mst_edges = kruskal(n, edges)

print("Edges in the Minimum Spanning Tree:")
for u, v, w in mst_edges:
    print(f"{u} -- {v} == {w}")

print("Total Weight of MST:", mst_weight)
