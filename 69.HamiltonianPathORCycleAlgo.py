# A Hamiltonian Path in a graph is a path that visits every vertex exactly once.
# A Hamiltonian Cycle (or Circuit) is a Hamiltonian Path that 
# starts and ends at the same vertex, forming a cycle.

# Explanation

# The Hamiltonian Path Problem is a classic NP-complete problem in graph theory.
# The goal is to determine whether such a path or cycle exists in a given graph.

# In Hamiltonian Path, we just need to visit all vertices once.

# In Hamiltonian Cycle, we must also return to the starting vertex.

# Since the problem is NP-complete, no polynomial-time solution exists for general graphs.
# Hence, we usually solve it using Backtracking — trying all 
# possible paths and checking if one satisfies the conditions.

def is_valid(v, graph, path, pos):
    if graph[path[pos - 1]][v] == 0:
        return False
    if v in path:
        return False
    return True

def hamiltonian_cycle_until(graph, path, pos):
    if pos == len(graph):
        return graph[path[pos - 1]][path[0]] == 1
    
    for v in range(1, len(graph)):
        if is_valid(v, graph, path, pos):
            path[pos] = v
            if hamiltonian_cycle_until(graph, path, pos + 1):
                return True
            path[pos] = -1
    return False

def hamiltonian_cycle(graph):
    n = len(graph)
    path = [-1] * n
    path[0] = 0

    if not hamiltonian_cycle_until(graph, path, 1):
        print("No Hamiltonian Cycle exists")
        return False
    
    print("Hamiltonian Cycle exists:")
    print(*path, path[0])
    return True

graph = [
    [0, 1, 0, 1, 0],
    [1, 0, 1, 1, 1],
    [0, 1, 0, 0, 1],
    [1, 1, 0, 0, 1],
    [0, 1, 1, 1, 0]
]

hamiltonian_cycle(graph)
