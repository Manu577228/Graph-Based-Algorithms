/* ----------------------------------------------------------------------------  */
/*   ( The Authentic JS/JAVA CodeBuff )
 ___ _                      _              _ 
 | _ ) |_  __ _ _ _ __ _ __| |_ __ ____ _ (_)
 | _ \ ' \/ _` | '_/ _` / _` \ V  V / _` || |
 |___/_||_\__,_|_| \__,_\__,_|\_/\_/\__,_|/ |
                                        |__/ 
 */
/* --------------------------------------------------------------------------   */
/*    Youtube: https://youtube.com/@code-with-Bharadwaj                        */
/*    Github : https://github.com/Manu577228                                  */
/*    Portfolio : https://manu-bharadwaj-portfolio.vercel.app/portfolio       */
/* -----------------------------------------------------------------------  */

import java.io.*;
import java.util.*;
import org.apache.commons.math3.optim.linear.*;
import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;
import org.apache.commons.math3.optim.linear.Relationship;

/*
 * Hungarian Algorithm (Assignment Problem) in Java
 * ------------------------------------------------
 * The algorithm finds the optimal assignment that minimizes total cost.
 */

public class HungarianAlgorithmDemo {

    public static void main(String[] args) throws Exception {
        
        // Step 1: Define the cost matrix
        int[][] costMatrix = {
            {4, 1, 3},
            {2, 0, 5},
            {3, 2, 2}
        };
        
        // Step 2: Convert to double matrix for computation
        double[][] matrix = new double[costMatrix.length][costMatrix[0].length];
        for (int i = 0; i < costMatrix.length; i++) {
            for (int j = 0; j < costMatrix[0].length; j++) {
                matrix[i][j] = costMatrix[i][j];
            }
        }
        
        // Step 3: Apply the Hungarian Algorithm using a helper function
        int[] result = hungarianAlgorithm(matrix);
        
        // Step 4: Print the optimal assignment and total minimum cost
        double totalCost = 0;
        System.out.println("Optimal Assignment:");
        for (int i = 0; i < result.length; i++) {
            System.out.println("Worker " + i + " → Job " + result[i]);
            totalCost += costMatrix[i][result[i]];
        }
        System.out.println("Total Minimum Cost: " + totalCost);
    }

    // Implementation of Hungarian Algorithm
    public static int[] hungarianAlgorithm(double[][] matrix) {
        int n = matrix.length;
        int m = matrix[0].length;

        double[][] cost = new double[n][m];
        for (int i = 0; i < n; i++)
            System.arraycopy(matrix[i], 0, cost[i], 0, m);

        double[] u = new double[n + 1];
        double[] v = new double[m + 1];
        int[] p = new int[m + 1];
        int[] way = new int[m + 1];

        for (int i = 1; i <= n; i++) {
            p[0] = i;
            double[] minv = new double[m + 1];
            Arrays.fill(minv, Double.POSITIVE_INFINITY);
            boolean[] used = new boolean[m + 1];
            int j0 = 0;
            do {
                used[j0] = true;
                int i0 = p[j0];
                double delta = Double.POSITIVE_INFINITY;
                int j1 = 0;
                for (int j = 1; j <= m; j++) {
                    if (!used[j]) {
                        double cur = cost[i0 - 1][j - 1] - u[i0] - v[j];
                        if (cur < minv[j]) {
                            minv[j] = cur;
                            way[j] = j0;
                        }
                        if (minv[j] < delta) {
                            delta = minv[j];
                            j1 = j;
                        }
                    }
                }
                for (int j = 0; j <= m; j++) {
                    if (used[j]) {
                        u[p[j]] += delta;
                        v[j] -= delta;
                    } else {
                        minv[j] -= delta;
                    }
                }
                j0 = j1;
            } while (p[j0] != 0);

            do {
                int j1 = way[j0];
                p[j0] = p[j1];
                j0 = j1;
            } while (j0 != 0);
        }

        int[] ans = new int[n];
        for (int j = 1; j <= m; j++) {
            if (p[j] != 0) {
                ans[p[j] - 1] = j - 1;
            }
        }
        return ans;
    }
}
