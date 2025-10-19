# Disjoint Set Union (DSU) is a data structure used to efficiently 
# manage a collection of disjoint sets, supporting union and find operations. 
# Path Compression optimizes the find operation by flattening 
# the structure of the tree, making future queries faster.

# Explanation:

# DSU Basics:
# DSU maintains sets where each set has a representative (or parent).

# find(x): Returns the representative of the set containing x.

# union(x, y): Merges the sets containing x and y.

# Path Compression:
# While finding the parent of an element, we update the parent of every 
# visited node to point directly to the root. This reduces the tree height and 
# makes subsequent find operations nearly constant time.

# Use Case:
# DSU is widely used in algorithms involving connectivity, like Kruskal’s 
# Minimum Spanning Tree, detecting cycles in graphs, and network connectivity queries.

class DSU:
    def __init__(self, n):
        self.parent = [i for i in range(n)]
        self.rank = [0] * n

    def find(self, x):
        if self.parent[x] != x:
            self.parent[x] = self.find(self.parent[x])
        return self.parent[x]
    
    def union(self, x, y):
        xroot = self.find(x)
        yroot = self.find(y)

        if xroot == yroot:
            return
        
        if self.rank[xroot] < self.rank[yroot]:
            self.parent[xroot] = yroot
        elif self.rank[xroot] > self.rank[yroot]:
            self.parent[yroot] = xroot
        else:
            self.parent[yroot] = xroot
            self.rank[xroot] += 1

dsu = DSU(5)  

dsu.union(0, 1)  # Merge sets containing 0 and 1
dsu.union(1, 2)  # Merge sets containing 1 and 2
dsu.union(3, 4)  # Merge sets containing 3 and 4

print("Parent array after unions:", dsu.parent)

print("Are 0 and 2 connected?", dsu.find(0) == dsu.find(2))

print("Are 0 and 4 connected?", dsu.find(0) == dsu.find(4))

        

