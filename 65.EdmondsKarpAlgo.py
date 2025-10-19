# The Edmonds-Karp Algorithm is an implementation of the Ford-Fulkerson method to compute 
# the maximum flow in a flow network.
# It uses Breadth-First Search (BFS) to find the shortest augmenting path in terms of the number of edges.

# Explanation:

# In a flow network, the goal is to push as much flow as possible from a source node S to a sink node T.

# Ford-Fulkerson works by repeatedly finding augmenting paths and increasing the flow along them.

# Edmonds-Karp specifically uses BFS to always find the shortest path (fewest edges) from S to T.

# The BFS approach ensures the algorithm runs in O(V * E²) time, where V is the number of 
# vertices and E is the number of edges.

# Steps:

# Initialize flow in all edges to 0.

# While there exists a path from source to sink in the residual graph (found via BFS):

# Find the minimum capacity along this path.

# Augment the flow along the path by this minimum capacity.


from collections import deque

def bfs(C, F, source, sink):
    n = len(C)
    parent = [-1] * n 
    queue = deque()
    queue.append(source)
    while queue:
        u = queue.popleft()
        for v in range(n):
            if C[u][v] - F[u][v] > 0 and parent[v] == -1:
                parent[v] = u
                queue.append(v)
                if v == sink:
                    return parent
    return None

def edmonds_karp(C, source, sink):
    n = len(C)
    F = [[0] * n for _ in range(n)] 
    max_flow = 0
    
    while True:
        parent = bfs(C, F, source, sink) 
        if not parent:  
            break
        

        v = sink
        path_flow = float('inf')
        while v != source:
            u = parent[v]
            path_flow = min(path_flow, C[u][v] - F[u][v])
            v = u
        

        v = sink
        while v != source:
            u = parent[v]
            F[u][v] += path_flow
            F[v][u] -= path_flow
            v = u
        
        max_flow += path_flow  
    
    return max_flow

# Example usage
C = [  # Capacity matrix
    [0, 16, 13, 0, 0, 0],
    [0, 0, 10, 12, 0, 0],
    [0, 4, 0, 0, 14, 0],
    [0, 0, 9, 0, 0, 20],
    [0, 0, 0, 7, 0, 4],
    [0, 0, 0, 0, 0, 0]
]

source = 0
sink = 5

print("Maximum Flow:", edmonds_karp(C, source, sink))
