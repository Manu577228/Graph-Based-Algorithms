# Depth First Search (DFS) 
# explores a graph by going as deep as possible along each branch before backtracking.

# Breadth First Search (BFS) explores all neighbors at the present depth 
# before moving on to nodes at the next depth level.

# Explanation

# Graphs are made up of nodes (vertices) and edges (connections).
# To traverse or explore all vertices in a graph:

# DFS uses a stack (or recursion).

# BFS uses a queue.

# They are used in:

# Finding connected components.

# Pathfinding.

# Cycle detection.

# Topological sorting, etc.

# Example Graph

# Let’s take the following undirected graph:

#      1
#     / \
#    2   3
#   / \
#  4   5

# Adjacency representation:

# graph = {
#   1: [2, 3],
#   2: [1, 4, 5],
#   3: [1],
#   4: [2],
#   5: [2]
# }

from collections import deque

graph = {
  1: [2, 3],
  2: [1, 4, 5],
  3: [1],
  4: [2],
  5: [2]
}

# DFS 
def dfs(start, visited=None):
    if visited is None:
        visited = set()
    visited.add(start)
    print(start, end = " ")
    for neighbor in graph[start]:
        if neighbor not in visited:
            dfs(neighbor, visited)

# BFS
def bfs(start):
    visited = set()
    q = deque([start])
    visited.add(start)
    while q:
        node = q.popleft()
        print(node, end = " ")
        for neighbor in graph[node]:
            if neighbor not in visited:
                visited.add(neighbor)
                q.append(neighbor)

print("DFS Traversal starting from the node 1:")
dfs(1)
print("\nBFS Traversal starting from the node 1:")
bfs(1)





