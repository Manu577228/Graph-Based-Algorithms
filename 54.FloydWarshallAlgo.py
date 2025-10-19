# The Floyd Warshall Algorithm is a dynamic programming algorithm used to find the shortest
# paths between all pairs of vertices in a weighted graph.
# It works with both directed and undirected graphs, and can handle 
# negative edge weights (but not negative cycles).

# Explanation

# The main idea is that the algorithm tries to improve the 
# shortest distance between any two vertices (i, j) by checking whether there exists an 
# intermediate vertex k that makes the path from i → k → j shorter than the currently 
# known path from i → j.

# Formally,
# For each vertex k (as an intermediate node), we update the distance matrix:

# dist[i][j] = min(dist[i][j],dist[i][k]+dist[k][j])

# This runs for every combination of (i, j, k) — resulting in a time complexity of O(V³).

INF = float('inf')

graph = [
    [0, 5, INF, 10],
    [INF, 0, 3, INF],
    [INF, INF, 0, 1],
    [INF, INF, INF, 0]
]

V = len(graph)

dist = [row[:] for row in graph]

for k in range(V):
    for i in range(V):
        for j in range(V):
            if dist[i][k] + dist[k][j] < dist[i][j]:
                dist[i][j] = dist[i][k] + dist[k][j]

print("Shortest distances between every pair of vertices:")
for i in range(V):
    for j in range(V):
        if dist[i][j] == INF:
            print("INF", end = " ")
        else:
            print(dist[i][j], end = " ")
    print()

    


