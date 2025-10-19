# Johnson’s Algorithm is used to find the shortest paths between 
# all pairs of vertices in a weighted directed graph. It efficiently combines 
# Bellman-Ford and Dijkstra’s Algorithm to handle 
# graphs with negative edge weights (but without negative cycles).

# Explanation

# Johnson’s Algorithm works in three main steps:

# Add a new vertex q connected to all other vertices with edge weight 0.

# Run Bellman-Ford from q to detect negative weight cycles and to compute a 
# potential function h(v) that reweights edges so that all edge weights become non-negative.

# Run Dijkstra’s Algorithm from each vertex using the reweighted edges to find shortest paths efficiently.

# Finally, we adjust the distances back to original weights using the potential differences.

# This algorithm is very efficient for sparse graphs, combining the flexibility 
# of Bellman-Ford and the speed of Dijkstra.

import heapq

def bellman_ford(graph, V, src):
    distance = [float('inf')] * V
    distance[src] = 0

    for _ in range(V - 1):
        for u in range(V):
            for v, w in graph[u]:
                if distance[u] + w < distance[v]:
                    distance[v] = distance[u] + w

    for u in range(V):
        for v, w in graph[u]:
            if distance[u] + w < distance[v]:
                print("Graph contains negative weight cycle")
                return None
    return distance
        
def dijkstra(graph, src):
    V = len(graph)
    dist = [float('inf')] * V
    dist[src] = 0
    pq = [(0, src)]

    while pq:
        d, u = heapq.heappop(pq)
        if d > dist[u]:
            continue
        for v, w in graph[u]:
            if dist[u] + w < dist[v]:
                dist[v] = dist[u] + w
                heapq.heappush(pq, (dist[v], v))
    return dist

def johnsons_algorithm(original_graph):
    V = len(original_graph)

    new_graph = [edges[:] for edges in original_graph]
    new_graph.append([(v, 0) for v in range(V)])

    h = bellman_ford(new_graph, V + 1, V)
    if h is None:
        return None
    
    reweighted_graph = [[] for _ in range(V)]
    for u in range(V):
        for v, w in original_graph[u]:
            reweighted_graph[u].append((v, w + h[u] - h[v]))

    all_pairs = []
    for u in range(V):
        dist = dijkstra(reweighted_graph, u)
        # Adjust distances back to original weights
        for v in range(V):
            if dist[v] < float('inf'):
                dist[v] += h[v] - h[u]
        all_pairs.append(dist)
    return all_pairs

# Example graph (directed)
# 0 → 1 (weight 1)
# 0 → 2 (weight 4)
# 1 → 2 (weight -2)
# 2 → 3 (weight 2)
# 3 → 1 (weight 3)
graph = [
    [(1, 1), (2, 4)],
    [(2, -2)],
    [(3, 2)],
    [(1, 3)]
]

result = johnsons_algorithm(graph)

print("All Pairs Shortest Paths:")
for i, row in enumerate(result):
    print(f"From vertex {i}: {row}")
