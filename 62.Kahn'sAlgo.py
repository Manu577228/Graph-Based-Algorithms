# In a Directed Acyclic Graph, topological sort arranges the vertices such that for every directed 
# edge u → v, vertex u comes before v in the ordering.
# Kahn’s Algorithm uses in-degree (the number of edges directed into a node) 
# and a queue to maintain processing order.

# Explanation:

# Compute the in-degree for each vertex.

# Push all vertices with in-degree = 0 into a queue.

# While the queue is not empty:

# Remove a vertex from the queue and add it to the topological order.

# For each of its neighbors, reduce their in-degree by 1.

# If any neighbor’s in-degree becomes 0, add it to the queue.

# If all vertices are processed, you get a valid topological order. Otherwise, the graph has a cycle.

from collections import defaultdict, deque

def kahn_topological_sort(vertices, edges):
    graph = defaultdict(list)
    for u, v in edges:
        graph[u].append(v)

    indegree = [0] * vertices
    for u in graph:
        for v in graph[u]:
            indegree[v] += 1

    q = deque()
    for i in range(vertices):
        if indegree[i] == 0:
            q.append(i)

    topo_order = []
    while q:
        node = q.popleft()
        topo_order.append(node)

        for neighbor in graph[node]:
            indegree[neighbor] -= 1
            if indegree[neighbor] == 0:
                q.append(neighbor)

    if len(topo_order) == vertices:
        return topo_order
    else:
        return []
    
V = 6
E = [(5, 2), (5, 0), (4, 0), (4, 1), (2, 3), (3, 1)]

order = kahn_topological_sort(V, E)
print("Topological Order:", order)
