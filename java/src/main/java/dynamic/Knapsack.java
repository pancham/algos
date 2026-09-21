package dynamic;

import java.util.*;

/**
 * Knapsack
 *
 * Demonstrates three boundedness variants of knapsack with 1D DP
 * and reconstruction of the selected items:
 *
 * 1. 0/1 Knapsack - each item can be used at most once
 * 2. Unbounded - each item can be used unlimited times
 * 3. Bounded - each item can be used up to count[i] times
 *
 * Example:
 * weights = {2, 3, 4}
 * values = {3, 4, 5}
 * counts = {2, 3, 1}
 * capacity = 7
 */
public class Knapsack {

    // ------------------------------------------------------------------------
    // 1. 0/1 KNAPSACK
    // ------------------------------------------------------------------------
    //
    // Each item can be selected at most once.
    //
    // Time:
    // O(n * capacity)
    //
    // Space:
    // O(capacity) for dp
    // O(capacity) for choice
    // Total: O(capacity)
    //
    // Important:
    // Iterate capacity BACKWARD.
    // This prevents the same item from being used more than once.
    // ------------------------------------------------------------------------

    // Lightweight immutable node representing an item choice in the DP path.
    // Because nodes are immutable, referencing path[c - weights[i]] captures
    // the exact historical state at that moment, preventing later 1D updates
    // from corrupting the predecessor chain.
    private static class Node {
        final int item;
        final Node parent;

        Node(int item, Node parent) {
            this.item = item;
            this.parent = parent;
        }
    }

    // ------------------------------------------------------------------------
    // 1. 0/1 KNAPSACK
    // ------------------------------------------------------------------------
    //
    // Each item can be selected at most once.
    //
    // Time:
    // O(n * capacity)
    //
    // Space:
    // O(capacity) for dp and path references
    // Total: O(capacity)
    //
    // Important:
    // Iterate capacity BACKWARD.
    // This prevents the same item from being used more than once.
    // ------------------------------------------------------------------------

    public static Result zeroOneKnapsack(
            int[] weights,
            int[] values,
            int capacity) {

        int[] dp = new int[capacity + 1];
        Node[] path = new Node[capacity + 1];

        for (int i = 0; i < weights.length; i++) {
            for (int c = capacity; c >= weights[i]; c--) {

                int candidate = dp[c - weights[i]] + values[i];

                if (candidate > dp[c]) {
                    dp[c] = candidate;
                    path[c] = new Node(i, path[c - weights[i]]);
                }
            }
        }

        List<Integer> selected = reconstruct(path[capacity]);

        return new Result(dp[capacity], selected);
    }

    // ------------------------------------------------------------------------
    // 2. UNBOUNDED KNAPSACK
    // ------------------------------------------------------------------------
    //
    // Each item can be selected unlimited times.
    //
    // Time:
    // O(n * capacity)
    //
    // Space:
    // O(capacity) for dp and path references
    // Total: O(capacity)
    //
    // Important:
    // Iterate capacity FORWARD.
    // This allows the same item to be used again.
    // ------------------------------------------------------------------------

    public static Result unboundedKnapsack(
            int[] weights,
            int[] values,
            int capacity) {

        int[] dp = new int[capacity + 1];
        Node[] path = new Node[capacity + 1];

        for (int i = 0; i < weights.length; i++) {
            for (int c = weights[i]; c <= capacity; c++) {

                int candidate = dp[c - weights[i]] + values[i];

                if (candidate > dp[c]) {
                    dp[c] = candidate;
                    path[c] = new Node(i, path[c - weights[i]]);
                }
            }
        }

        List<Integer> selected = reconstruct(path[capacity]);

        return new Result(dp[capacity], selected);
    }

    // ------------------------------------------------------------------------
    // 3. BOUNDED KNAPSACK
    // ------------------------------------------------------------------------
    //
    // Each item i can be selected from 0 to counts[i] times.
    //
    // Simple 1D approach:
    // Treat every allowed copy as a separate 0/1 item.
    //
    // Time:
    // O(capacity * totalCopies)
    //
    // where totalCopies = counts[0] + counts[1] + ...
    //
    // Space:
    // O(capacity) for dp and path references
    // Total: O(capacity)
    //
    // Important:
    // For EVERY COPY, iterate capacity BACKWARD.
    // This prevents one copy from being selected more than once.
    // ------------------------------------------------------------------------

    public static Result boundedKnapsack(
            int[] weights,
            int[] values,
            int[] counts,
            int capacity) {

        int[] dp = new int[capacity + 1];
        Node[] path = new Node[capacity + 1];

        for (int i = 0; i < weights.length; i++) {

            // Process each allowed copy as a separate 0/1 item.
            for (int copy = 0; copy < counts[i]; copy++) {

                for (int c = capacity; c >= weights[i]; c--) {

                    int candidate = dp[c - weights[i]] + values[i];

                    if (candidate > dp[c]) {
                        dp[c] = candidate;
                        path[c] = new Node(i, path[c - weights[i]]);
                    }
                }
            }
        }

        List<Integer> selected = reconstruct(path[capacity]);

        return new Result(dp[capacity], selected);
    }

    // ------------------------------------------------------------------------
    // RECONSTRUCTION
    // ------------------------------------------------------------------------
    //
    // Unified reconstruction approach for 0/1, Unbounded, and Bounded knapsack.
    // Walks backwards through the immutable Node parent chain from head to root.
    // ------------------------------------------------------------------------

    private static List<Integer> reconstruct(Node head) {
        List<Integer> selected = new ArrayList<>();

        for (Node curr = head; curr != null; curr = curr.parent) {
            selected.add(curr.item);
        }

        Collections.reverse(selected);

        return selected;
    }

    // ------------------------------------------------------------------------
    // RESULT CLASS
    // ------------------------------------------------------------------------

    public static class Result {

        public final int maxValue;

        // Original item indexes selected.
        // For unbounded/bounded knapsack, an index can appear multiple times.
        public final List<Integer> selectedItems;

        public Result(int maxValue, List<Integer> selectedItems) {
            this.maxValue = maxValue;
            this.selectedItems = selectedItems;
        }

        @Override
        public String toString() {
            return "maxValue=" + maxValue
                    + ", selectedItems=" + selectedItems;
        }
    }

    // ------------------------------------------------------------------------
    // EXAMPLE
    // ------------------------------------------------------------------------

    public static void main(String[] args) {

        int[] weights = { 10, 1, 1, 4 };
        int[] values = { 35, 26, 19, 44 };
        int[] counts = { 1, 2, 1, 2 };
        int capacity = 13;

        Result zeroOne = zeroOneKnapsack(weights, values, capacity);
        Result unbounded = unboundedKnapsack(weights, values, capacity);
        Result bounded = boundedKnapsack(weights, values, counts, capacity);

        System.out.println("0/1       : " + zeroOne);
        System.out.println("Unbounded : " + unbounded);
        System.out.println("Bounded   : " + bounded);
    }
}

/*
 * Expected maximum values for this example:
 * 
 * 0/1:
 * Item 1 + Item 2
 * weights = 3 + 4 = 7
 * values = 4 + 5 = 9
 * 
 * maxValue = 9
 * 
 * Unbounded:
 * Item 0 + Item 0 + Item 1
 * weights = 2 + 2 + 3 = 7
 * values = 3 + 3 + 4 = 10
 * 
 * maxValue = 10
 * 
 * Bounded:
 * Item 0 + Item 0 + Item 1
 * weights = 2 + 2 + 3 = 7
 * values = 3 + 3 + 4 = 10
 * 
 * maxValue = 10
 * 
 * 
 * COMPLEXITY SUMMARY
 * ------------------
 * 
 * Time Space
 * 0/1 Knapsack O(n * W) O(W)
 * Unbounded Knapsack O(n * W) O(W)
 * Bounded Knapsack O(W * totalCopies) O(W)
 * 
 * where:
 * n = number of distinct item types
 * W = capacity
 * totalCopies = sum(count[i])
 * 
 * 
 * KEY DIFFERENCE
 * --------------
 * 
 * 0/1:
 * for (c = W; c >= weight; c--)
 * // BACKWARD
 * 
 * Unbounded:
 * for (c = weight; c <= W; c++)
 * // FORWARD
 * 
 * Bounded:
 * for each allowed copy:
 * for (c = W; c >= weight; c--)
 * // BACKWARD
 * 
 * 
 * IMPORTANT RECONSTRUCTION NOTE
 * -----------------------------
 * 
 * We use an immutable Node linked list (parent pointers).
 * Each entry path[c] points to a Node(item, parentNode).
 * When path[c] is created from path[c - weight[i]], it retains
 * an immutable reference to that exact predecessor state snapshot.
 * Later 1D updates to path[c - weight[i]] do not mutate or corrupt
 * previously recorded parent references.
 * 
 * This provides a single, unified reconstruction approach that works
 * correctly across 0/1, Unbounded, and Bounded knapsack variants.
 */
