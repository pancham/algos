package dynamic;

import java.util.*;

/**
 * BalancedPartition
 *
 * Partitions an array of positive integers into two subsets such that the
 * absolute difference between their sums is minimized.
 *
 * Problem Reduction to 0/1 Knapsack:
 * - Total sum: S = sum(wt)
 * - Target: target = S / 2
 * - Each item i has weight = wt[i] and value = wt[i]
 * - Knapsack capacity = target
 * - Finding the subset with sum closest to target (<= S / 2) minimizes the
 *   difference: minDiff = (S - subsetSum) - subsetSum = S - 2 * subsetSum
 *
 * This file provides BOTH approaches:
 * 1. 1D DP Array (Space-Optimized, Knapsack-style)
 *    - Space: O(target) = O(S)
 *    - Reconstructs selections via immutable Node parent pointers.
 *
 * 2. 2D DP Table (Classic Grid)
 *    - Space: O(n * target) = O(n * S)
 *    - Reconstructs selections by backtracking through T[i][w].
 *
 * Complexity Comparison:
 * - 1D DP: Time O(n * target), Space O(target)
 * - 2D DP: Time O(n * target), Space O(n * target)
 */
public class BalancedPartition {

    // ------------------------------------------------------------------------
    // RESULT CLASS
    // ------------------------------------------------------------------------

    public static class Result {
        public final int minDifference;
        public final int sum1;
        public final int sum2;
        public final List<Integer> partition1;
        public final List<Integer> partition2;

        public Result(int minDifference, int sum1, int sum2,
                      List<Integer> partition1, List<Integer> partition2) {
            this.minDifference = minDifference;
            this.sum1 = sum1;
            this.sum2 = sum2;
            this.partition1 = partition1;
            this.partition2 = partition2;
        }

        @Override
        public String toString() {
            return "minDifference=" + minDifference
                    + " (sum1=" + sum1 + ", sum2=" + sum2 + ")"
                    + "\n  partition1=" + partition1
                    + "\n  partition2=" + partition2;
        }
    }

    // ========================================================================
    // 1. ONE-DIMENSIONAL DP (KNAPSACK STYLE)
    // ========================================================================

    // Immutable node representing an element choice in the 1D DP path.
    // Preserves the historical state at path[w - wt[i]] so subsequent
    // 1D updates do not corrupt the reconstruction chain.
    private static class Node {
        final int index;
        final Node parent;

        Node(int index, Node parent) {
            this.index = index;
            this.parent = parent;
        }
    }

    /**
     * 1D DP: Partitions the array with minimal sum difference.
     * Uses a single 1D DP array of size (target + 1) and immutable Node path tracking.
     *
     * Time:  O(n * target)
     * Space: O(target)
     */
    public static Result partition1D(int[] wt) {
        if (wt == null || wt.length == 0) {
            return new Result(0, 0, 0, Collections.emptyList(), Collections.emptyList());
        }

        int totalSum = 0;
        for (int x : wt) {
            totalSum += x;
        }

        int target = totalSum / 2;
        int[] dp = new int[target + 1];
        Node[] path = new Node[target + 1];

        // 1D 0/1 Knapsack: outer loop over items, inner loop BACKWARD
        for (int i = 0; i < wt.length; i++) {
            for (int w = target; w >= wt[i]; w--) {
                int candidate = dp[w - wt[i]] + wt[i];
                if (candidate > dp[w]) {
                    dp[w] = candidate;
                    path[w] = new Node(i, path[w - wt[i]]);
                }
            }
        }

        int sum1 = dp[target];
        int sum2 = totalSum - sum1;
        int minDifference = sum2 - sum1;

        // Reconstruct partition 1 from index chain
        boolean[] inPartition1 = new boolean[wt.length];
        for (Node curr = path[target]; curr != null; curr = curr.parent) {
            inPartition1[curr.index] = true;
        }

        List<Integer> partition1 = new ArrayList<>();
        List<Integer> partition2 = new ArrayList<>();

        for (int i = 0; i < wt.length; i++) {
            if (inPartition1[i]) {
                partition1.add(wt[i]);
            } else {
                partition2.add(wt[i]);
            }
        }

        return new Result(minDifference, sum1, sum2, partition1, partition2);
    }

    /**
     * 1D DP: Computes the minimum difference using a single 1D array without path tracking.
     */
    public static int minimizeDifference1D(int[] wt) {
        if (wt == null || wt.length == 0) {
            return 0;
        }

        int totalSum = 0;
        for (int x : wt) {
            totalSum += x;
        }

        int target = totalSum / 2;
        int[] dp = new int[target + 1];

        for (int item : wt) {
            for (int w = target; w >= item; w--) {
                dp[w] = Math.max(dp[w], dp[w - item] + item);
            }
        }

        int bestSubsetSum = dp[target];
        return totalSum - 2 * bestSubsetSum;
    }

    // ========================================================================
    // 2. TWO-DIMENSIONAL DP (CLASSIC GRID)
    // ========================================================================

    /**
     * 2D DP: Partitions the array with minimal sum difference.
     * Uses a full 2D table T[n + 1][target + 1] and backtracks to reconstruct partitions.
     *
     * Time:  O(n * target)
     * Space: O(n * target)
     */
    public static Result partition2D(int[] wt) {
        if (wt == null || wt.length == 0) {
            return new Result(0, 0, 0, Collections.emptyList(), Collections.emptyList());
        }

        int totalSum = 0;
        for (int x : wt) {
            totalSum += x;
        }

        int target = totalSum / 2;
        int n = wt.length;
        int[][] T = new int[n + 1][target + 1];

        // Fill 2D DP table
        for (int i = 1; i <= n; i++) {
            for (int w = 0; w <= target; w++) {
                int withoutCurrent = T[i - 1][w];
                if (wt[i - 1] <= w) {
                    int withCurrent = wt[i - 1] + T[i - 1][w - wt[i - 1]];
                    T[i][w] = Math.max(withoutCurrent, withCurrent);
                } else {
                    T[i][w] = withoutCurrent;
                }
            }
        }

        int sum1 = T[n][target];
        int sum2 = totalSum - sum1;
        int minDifference = sum2 - sum1;

        // Backtrack through 2D table to determine which items belong to partition 1
        boolean[] inPartition1 = new boolean[n];
        int w = target;
        for (int i = n; i > 0 && w > 0; i--) {
            // If T[i][w] != T[i - 1][w], item i - 1 was included in the optimal subset
            if (T[i][w] != T[i - 1][w]) {
                inPartition1[i - 1] = true;
                w -= wt[i - 1];
            }
        }

        List<Integer> partition1 = new ArrayList<>();
        List<Integer> partition2 = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            if (inPartition1[i]) {
                partition1.add(wt[i]);
            } else {
                partition2.add(wt[i]);
            }
        }

        return new Result(minDifference, sum1, sum2, partition1, partition2);
    }

    /**
     * 2D DP: Computes the minimum difference using a 2D table.
     */
    public static int minimizeDifference2D(int[] wt) {
        if (wt == null || wt.length == 0) {
            return 0;
        }

        int totalSum = 0;
        for (int x : wt) {
            totalSum += x;
        }

        int target = totalSum / 2;
        int n = wt.length;
        int[][] T = new int[n + 1][target + 1];

        for (int i = 1; i <= n; i++) {
            for (int w = 0; w <= target; w++) {
                int withoutCurrent = T[i - 1][w];
                if (wt[i - 1] <= w) {
                    int withCurrent = wt[i - 1] + T[i - 1][w - wt[i - 1]];
                    T[i][w] = Math.max(withoutCurrent, withCurrent);
                } else {
                    T[i][w] = withoutCurrent;
                }
            }
        }

        int bestSubsetSum = T[n][target];
        return totalSum - 2 * bestSubsetSum;
    }

    // ========================================================================
    // 3. CONVENIENCE & BACKWARD-COMPATIBLE METHODS
    // ========================================================================

    /**
     * Default partition method (delegates to 1D DP).
     */
    public static Result partition(int[] wt) {
        return partition1D(wt);
    }

    /**
     * Computes the minimum sum difference (delegates to 1D DP).
     */
    public int minimizeDifference(int[] wt) {
        return minimizeDifference1D(wt);
    }

    /**
     * Returns the closest subset sum <= totalSum / 2.
     * (Equivalent to the legacy return value of minimizeDifference in older code).
     */
    public int maxSubsetSum(int[] wt) {
        if (wt == null || wt.length == 0) {
            return 0;
        }

        int totalSum = 0;
        for (int x : wt) {
            totalSum += x;
        }

        int target = totalSum / 2;
        int[] dp = new int[target + 1];

        for (int item : wt) {
            for (int w = target; w >= item; w--) {
                dp[w] = Math.max(dp[w], dp[w - item] + item);
            }
        }

        return dp[target];
    }

    /**
     * Returns the two balanced partitions as a list of two subset lists.
     * Preserves duplicate values properly.
     */
    public List<List<Integer>> getPartitions(int[] wt) {
        Result res = partition(wt);
        return Arrays.asList(res.partition1, res.partition2);
    }

    // ========================================================================
    // 4. EXAMPLE & VERIFICATION
    // ========================================================================

    public static void main(String[] args) {
        BalancedPartition bp = new BalancedPartition();
        int[] wt = { 4, 2, 3, 5, 5, 6, 9, 7, 8, 10 };

        int totalSum = 0;
        for (int x : wt) totalSum += x;
        int target = totalSum / 2;

        System.out.println("Input array : " + Arrays.toString(wt));
        System.out.println("Total sum   : " + totalSum);
        System.out.println("Target (S/2): " + target);
        System.out.println();

        // 1. One-Dimensional DP
        System.out.println("--- 1D DP (Knapsack Style) ---");
        Result res1D = partition1D(wt);
        System.out.println(res1D);
        System.out.println();

        // 2. Two-Dimensional DP
        System.out.println("--- 2D DP (Classic Grid) ---");
        Result res2D = partition2D(wt);
        System.out.println(res2D);
        System.out.println();

        // Verify that both versions yield the same minimum difference and subset sums
        System.out.println("Match minDifference? " + (res1D.minDifference == res2D.minDifference));
        System.out.println("Match sum1?          " + (res1D.sum1 == res2D.sum1));
        System.out.println("Match sum2?          " + (res1D.sum2 == res2D.sum2));
    }
}
