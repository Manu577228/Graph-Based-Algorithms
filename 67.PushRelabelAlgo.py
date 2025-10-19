# The Push-Relabel algorithm is a method to compute the maximum flow in a flow network.
# It maintains a preflow (may exceed the capacities temporarily) and uses push and relabel operations to send flow from the 
# source to the sink efficiently.

# Explanation

# Preflow Initialization: Start by sending the maximum 
# possible flow from the source to its neighbors (saturating outgoing edges).

# Excess Flow: Maintain an excess array, where excess[u] represents 
# how much flow is at node u waiting to be pushed further.

# Push Operation: If a vertex u has excess flow and an adjacent vertex v has a lower height, 
# push as much flow as possible from u to v.

# Relabel Operation: If u cannot push flow to any neighbor (all neighbors are 
# higher or edges are full), increase u's height to allow pushing later.

# Termination: Repeat push and relabel until no vertex (except source and sink) has excess flow.

# Push-Relabel is particularly efficient for dense graphs and has better practical 
# performance than Edmonds-Karp in many cases.

class PushRelabel:
    def __init__(self, n):
        self.n = n
        self.capacity = [[0] * n for _ in range(n)]
        self.flow = [[0] * n for _ in range(n)]
        self.height = [0] * n
        self.excess = [0] * n

    def add_edge(self, u, v, cap):
        self.capacity[u][v] = cap

    def push(self, u, v):
        push_flow = min(self.excess[u], self.capacity[u][v] - self.flow[u][v])
        self.flow[u][v] += push_flow
        self.flow[v][u] -= push_flow
        self.excess[u] -= push_flow
        self.excess[v] += push_flow
        print(f"Pushed {push_flow} from {u} to {v}")

    def relabel(self, u):
        min_height = float('inf')
        for v in range(self.n):
            if self.capacity[u][v] - self.flow[u][v] > 0:
                min_height = min(min_height, self.height[v])
        self.height[u] = min_height + 1
        print(f"Relabeled vertex {u} to height {self.height[u]}")

    def discharge(self, u):
        while self.excess[u] > 0:
            for v in range(self.n):
                if self.capacity[u][v] - self.flow[u][v] > 0 and self.height[u] == self.height[v] + 1:
                    self.push(u, v)
                    break
            else:
                self.relabel(u)

    def max_flow(self, s, t):
        self.height[s] = self.n
        for v in range(self.n):
            if self.capacity[s][v] > 0:
                self.flow[s][v] = self.capacity[s][v]
                self.flow[v][s] = -self.capacity[s][v]
                self.excess[v] = self.capacity[s][v]
                self.excess[s] -= self.capacity[s][v]
        active = [i for i in range(self.n) if i != s and i != t and self.excess[i] > 0]
        while active:
            u = active.pop(0)
            self.discharge(u)
            if self.excess[u] > 0:
                active.append(u)
        return sum(self.flow[s][i] for i in range(self.n))


pr = PushRelabel(4)
pr.add_edge(0, 1, 100)
pr.add_edge(0, 2, 100)
pr.add_edge(1, 2, 1)
pr.add_edge(1, 3, 100)
pr.add_edge(2, 3, 100)

print("Maximum flow:", pr.max_flow(0, 3))

        