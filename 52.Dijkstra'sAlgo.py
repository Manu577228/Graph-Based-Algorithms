# Dijkstra’s Algorithm finds the shortest path from a given source node 
# to all other nodes in a weighted graph with non-negative edge weights.
# It uses a greedy approach and a priority queue (min-heap) to always 
# pick the vertex with the smallest distance.

# Explanation

# Start from the source node with distance 0.

# Use a priority queue to always select the node with the current smallest distance.

# For each neighbor, if a shorter path is found via the current node, update it.

# Continue until all nodes have been processed.

# Works on both directed and undirected weighted graphs with non-negative weights.

# Visual Representation of the Graph:
        
    #           (4)
    #      0 ----------> 1
    #      |              \
    #    (1)|               \(1)
    #      |                  \
    #      v                   v
    #      2 ------(5)-------> 3
    #       \ 
    #      (2)\ 
    #         -> 1  (Alternate shorter path)
            
    #     Explanation:
    #     0 -> 1 (weight 4)
    #     0 -> 2 (weight 1)
    #     1 -> 3 (weight 1)
    #     2 -> 1 (weight 2)
    #     2 -> 3 (weight 5)

import heapq

def dijkstra(graph, start):
    n = len(graph)

    distances = [float('inf')] * n;

    distances[start] = 0

    pq = [(0, start)]

    while pq:
        current_dist, u = heapq.heappop(pq)

        if current_dist > distances[u]:
            continue

        for neighbor, weight in graph[u]:
            new_dist = current_dist + weight

            if new_dist < distances[neighbor]:
                distances[neighbor] = new_dist
                heapq.heappush(pq, (new_dist, neighbor))

    return distances

graph = [
    [(1, 4), (2, 1)], 
    [(3, 1)],
    [(1, 2), (3, 5)],
    []
]

result = dijkstra(graph, 0)

print("Shortest distances from source 0:", result)


   
