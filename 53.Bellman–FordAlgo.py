# Bellman–Ford is a single-source shortest-path algorithm that handles graphs with 
# negative edge weights and detects negative-weight cycles reachable from the source.

# It runs in O(V + E) 

# O(V⋅E) time (V = vertices, E = edges) and returns shortest distances or
# reports that shortest paths are undefined when a negative cycle is reachable.

# Explanation

# Bellman–Ford repeatedly relaxes every edge up to V - 1 times 
# (each pass can propagate shortest-path information across one more edge).

# After V - 1 relaxations, if any edge can still be 
# relaxed, then there is a negative-weight cycle reachable from the source 
# (because distances can be driven down indefinitely).

# Use Bellman–Ford when your graph may contain negative-weight edges 
# (but not for dense graphs where faster algorithms for non-negative 
#  weights such as Dijkstra would be preferable).

# Time complexity: O(V . E) Space: O(V) for distances and parent pointers.

#  Finds shortest paths from a single source and detects negative-weight cycles.

#        Graph Explanation:

#        0 -> 1 (weight 6)
#        0 -> 2 (weight 7)
#        1 -> 2 (weight 5)
#        1 -> 3 (weight -4)
#        2 -> 4 (weight -3)
#        4 -> 3 (weight 7)

#    Graph Representation:

#          (6)
#      (0) -----> (1)
#       | \        |  \
#      (7) \       |   \ (-4)
#           \      |    \
#            > (2) |     > (3)
#             \(-3)| 
#              \   |
#               > (4)
#                (7)

def bellman_ford(edges, V, source):
    dist = [float('inf')] * V
    dist[source] = 0
    parent = [None] * V

    for i in range(V - 1):
        updated = False
        for(u, v, w) in edges:
            if dist[u] != float('inf') and dist[u] + w < dist[v]:
                dist[v] = dist[u] + w
                parent[v] = u
                updated = True

        if not updated:
            break

    negative_cycle = False
    for (u, v, w) in edges:
        if dist[u] != float('inf') and dist[u] + w < dist[v]:
            negative_cycle = True
            break

    return dist, parent, negative_cycle

def reconstruct_path(parent, dest, V):
    path = []
    curr = dest
    visited = set()
    while curr is not None:
        if curr in visited:
            return None
        visited.add(curr)
        path.append(curr)
        curr = parent[curr]
    path.reverse()
    return path

# -------------------------
# Example 1: No negative cycle
# -------------------------
V = 5  # nodes 0..4
edges = [
    (0, 1, 6),   # 0 -> 1 (6)
    (0, 2, 7),   # 0 -> 2 (7)
    (1, 2, 5),   # 1 -> 2 (5)
    (1, 3, -4),  # 1 -> 3 (-4) negative edge but no 3->1 yet (no cycle)
    (2, 4, -3),  # 2 -> 4 (-3)
    (4, 3, 7)    # 4 -> 3 (7)
]

dist, parent, neg = bellman_ford(edges, V, 0)

print("---- Run 1: No negative cycle ----")
print(f"Negative cycle detected? {neg}")
if not neg:
    print("Vertex | Distance from 0 | Shortest path from 0")
    for v in range(V):
        if dist[v] == float('inf'):
            dstr = "∞"
            path_str = "unreachable"
        else:
            dstr = str(dist[v])
            p = reconstruct_path(parent, v, V)
            path_str = "(path undefined)" if p is None else "->".join(map(str, p))
        print(f"{v:>6} | {dstr:>15} | {path_str}")
else:
    print("Cannot display paths because a negative-weight cycle is present.")

# -------------------------
# Example 2: Introduce a negative cycle
# -------------------------
# Add an edge that creates a negative cycle: 1 -> 3 (-4) and 3 -> 1 (-10) -> cycle weight -14
edges_with_cycle = edges + [(3, 1, -10)]

dist2, parent2, neg2 = bellman_ford(edges_with_cycle, V, 0)

print("\n---- Run 2: With a negative cycle added (3 -> 1 : -10) ----")
print(f"Negative cycle detected? {neg2}")
if not neg2:
    print("Vertex | Distance from 0 | Shortest path from 0")
    for v in range(V):
        if dist2[v] == float('inf'):
            dstr = "∞"
            path_str = "unreachable"
        else:
            dstr = str(dist2[v])
            p = reconstruct_path(parent2, v, V)
            path_str = "(path undefined)" if p is None else "->".join(map(str, p))
        print(f"{v:>6} | {dstr:>15} | {path_str}")
else:
    print("Since a negative-weight cycle is reachable from the source, shortest paths are undefined (can decrease indefinitely).")