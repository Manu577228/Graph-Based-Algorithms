# Kosaraju’s Algorithm is used to find all strongly connected components in a directed graph.
# A strongly connected component (SCC) is a maximal subset of vertices such that every vertex 
# is reachable from every other vertex in that subset.

# Explanation

# Kosaraju’s algorithm works in two main phases:

# DFS & Stack: Perform a DFS on the original graph and push nodes onto a stack in 
# the order of their finishing times.

# Transpose & DFS: Reverse all edges (transpose the graph) and perform DFS 
# in the order defined by the stack. Each DFS traversal in this phase gives one SCC.

# Why it works:

# The finishing time stack ensures we always start DFS from vertices that are 
# guaranteed to reach all nodes in their SCCs in the transposed graph.

# Transposing the graph ensures that the DFS in phase 2 only explores vertices within the same SCC.

graph = {
    0: [1],
    1: [2],
    2: [0, 3],
    3: [4],
    4: []
}

def dfs(v, visited, stack, g):
    visited[v] = True
    for u in g[v]:
        if not visited[u]:
            dfs(u, visited, stack, g)
    stack.append(v)

def transpose_graph(g):
    gt = {i : [] for i in g}
    for v in g:
        for u in g[v]:
            gt[u].append(v)
    return gt

def dfs_transposed(v, visited, gt, component):
    visited[v] = True
    component.append(v)
    for u in gt[v]:
        if not visited[u]:
            dfs_transposed(u, visited, gt, component)

def kosaraju_scc(g):
    visited = {i : False for i in g}
    stack = []

    for v in g:
        if not visited[v]:
            dfs(v, visited, stack, g)

    gt = transpose_graph(g)

    visited = {i : False for i in g}
    sccs = []

    while stack:
        v = stack.pop()
        if not visited[v]:
            component = []
            dfs_transposed(v, visited, gt, component)
            sccs.append(component)

    return sccs

result = kosaraju_scc(graph)
print("Strongly Connected Components:", result)




