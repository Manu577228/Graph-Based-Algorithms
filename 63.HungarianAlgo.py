# The Hungarian Algorithm is a combinatorial optimization algorithm used to solve 
# the assignment problem — finding the most efficient way to assign tasks to agents such that 
# the total cost is minimized (or profit maximized).
# It works on a cost matrix and ensures an optimal one-to-one assignment between workers and tasks.

# Explanation

# Imagine you have n workers and n jobs. Each worker has a different cost (or time) 
# for completing each job.
# We want to assign each worker exactly one job so that the total cost is minimum.

# The Hungarian Algorithm follows these main steps:

# Subtract the Row Minimum: For each row, subtract the smallest element in 
# that row from all elements in that row.

# Subtract the Column Minimum: For each column, subtract the smallest element 
# in that column from all elements in that column.

# Cover All Zeros with Minimum Lines: Use the fewest number of horizontal and 
# vertical lines to cover all zeros.

# Adjust the Matrix:

# If the number of lines < n, find the smallest uncovered value.

# Subtract it from uncovered elements and add it to elements covered twice.

# Repeat Until an Optimal Assignment Is Found: When exactly n lines are needed to cover all zeros, 
# an optimal assignment can be made.

import numpy as np

original_cost = np.array([
    [4, 1, 3],
    [2, 0, 5],
    [3, 2, 2]
])

cost_matrix = original_cost.copy()

row_min = np.min(cost_matrix, axis = 1)
cost_matrix = cost_matrix - row_min[:, np.newaxis]

col_min = np.min(cost_matrix, axis = 0)
cost_matrix = cost_matrix - col_min

from scipy.optimize import linear_sum_assignment
row_ind, col_ind = linear_sum_assignment(cost_matrix)

total_cost = original_cost[row_ind, col_ind].sum()

print("Optimal Assignment:")
for i in range(len(row_ind)):
    print(f"Worker {row_ind[i]} → Job {col_ind[i]}")
print("Total Minimum Cost:", total_cost)


