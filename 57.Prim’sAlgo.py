# Prim’s Algorithm is a greedy algorithm used to find the Minimum Spanning Tree (MST) of a connected, 
# weighted, and undirected graph.
# It starts from an arbitrary node and repeatedly adds the smallest 
# edge that connects a visited vertex to an unvisited vertex.

# Explanation

# The goal of Prim’s Algorithm is to connect all vertices with the minimum total edge weight 
# without forming cycles.
# At each step:

# Start from any vertex.

# Pick the smallest edge connecting the current tree to a new vertex.

# Repeat until all vertices are included in the tree.

# It uses a priority queue (min-heap) to efficiently get the smallest edge at each step.

# Time Complexity: O(E log V)
# Space Complexity: O(V + E)

# Example Graph

# Consider this weighted undirected graph:

#      2
#  (A)---(B)
#   | \   |
#   3 \ \ 1
#   |   \ \
#  (C)---(D)
#      4

# Edges:
# A-B (2), A-C (3), A-D (1), B-D (2), C-D (4)

import heapq

def prims_algorithm(graph, start):
    visited = set()
    min_heap = [(0, start)]
    total_cost = 0

    while min_heap:
        weight, node = heapq.heappop(min_heap)

        if node in visited:
            continue

        visited.add(node)
        total_cost += weight

        for next_weight, neighbor in graph[node]:
            if neighbor not in visited:
                heapq.heappush(min_heap, (next_weight, neighbor))

    return total_cost

graph = {
    'A': [(2, 'B'), (3, 'C'), (1, 'D')],
    'B': [(2, 'A'), (2, 'D')],
    'C': [(3, 'A'), (4, 'D')],
    'D': [(1, 'A'), (2, 'B'), (4, 'C')]
}

mst_cost = prims_algorithm(graph, 'A')

print("Minimum Spanning Tree Cost:", mst_cost)
        
