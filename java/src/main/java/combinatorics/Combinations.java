package combinatorics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Generates combinations and subsets of an array.
 * Includes enhancements for input validation, duplicate handling,
 * and generating combinations of a specific size with recursive pruning.
 */
public class Combinations {

    /**
     * Generates all unique subsets (power set) of the input array.
     * Handles duplicates by skipping redundant elements.
     *
     * Complexity:
     * Two separate costs combine to give the final bound:
     *
     * 1. Tree traversal: O(2^n)
     * Each element is either included or excluded, giving 2^n subsets and a
     * recursion tree with O(2^n) nodes, each doing O(1) work.
     *
     * 2. Copy cost: n * 2^(n-1)
     * Recording each subset copies the current path via new
     * ArrayList<>(currentCombination).
     * Subset sizes range from 0 to n, so total copy work = sum of s * C(n,s) for
     * s=0..n.
     * Using the identity s * C(n,s) = n * C(n-1, s-1), the sum collapses to n *
     * 2^(n-1),
     * since sum of C(n-1, s-1) = total subsets of (n-1) elements = 2^(n-1).
     * The 'n' multiplier comes entirely from this per-result copy, not an extra
     * loop.
     *
     * Total: n * 2^(n-1) + 2^n
     * = n * 2^(n-1) + 2 * 2^(n-1) [rewrite 2^n as 2 * 2^(n-1)]
     * = 2^(n-1) * (n + 2)
     * = O(n * 2^n)
     * The copy term dominates (e.g. at n=20: traversal=1M, copy=10M).
     *
     * Time — O(n * 2^n)
     * Space — O(n * 2^n) output + O(n) call stack (recursion depth <= n)
     *
     * @param nums the input array of integers
     * @return a list of all unique subsets
     */
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        if (nums == null) {
            return result;
        }

        // Clone and sort the array to handle duplicates and protect the caller's input
        int[] sortedNums = nums.clone();
        Arrays.sort(sortedNums);

        // Start DFS search with an empty combination
        dfsSubsets(0, sortedNums, new ArrayList<>(), result);

        return result;
    }

    private void dfsSubsets(int start, int[] nums, List<Integer> currentCombination, List<List<Integer>> result) {
        // Every state reached is a unique subset
        result.add(new ArrayList<>(currentCombination));

        for (int i = start; i < nums.length; i++) {
            // Skip duplicates at the same recursion level
            if (i > start && nums[i] == nums[i - 1]) {
                continue;
            }

            // 1. Make choice
            currentCombination.add(nums[i]);

            // 2. Recurse (i + 1 ensures each element is used at most once per path)
            dfsSubsets(i + 1, nums, currentCombination, result);

            // 3. Backtrack
            currentCombination.remove(currentCombination.size() - 1);
        }
    }

    /**
     * Generates all unique combinations of size k from the input array.
     * Handles duplicates and uses pruning for efficiency.
     *
     * Complexity:
     * Two separate costs combine to give the final bound:
     *
     * 1. Tree traversal: O(C(n,k))
     * The recursion tree has C(n,k) leaf nodes (one per valid combination) and
     * a comparable number of internal nodes, each doing O(1) work.
     *
     * 2. Copy cost: k * C(n,k)
     * Every result has exactly size k, so each copy via new
     * ArrayList<>(currentCombination)
     * costs exactly O(k). With C(n,k) results, copy work = k * C(n,k).
     * Unlike subsets, no identity is needed — size is fixed, so it is direct
     * multiplication.
     *
     * Total: C(n,k) + k * C(n,k)
     * = C(n,k) * (1 + k)
     * = O(k * C(n,k))
     * The copy term dominates for any k >= 1.
     *
     * Pruning: the guard (currentCombination.size() + remaining < k) cuts subtrees
     * where not enough elements remain to reach size k. Does not change the
     * asymptotic
     * bound but meaningfully reduces the constant factor.
     *
     * Time — O(k * C(n,k))
     * Space — O(k * C(n,k)) output + O(k) call stack (recursion depth <= k)
     *
     * @param nums the input array of integers
     * @param k    the size of combinations to generate
     * @return a list of all unique combinations of size k
     */
    public List<List<Integer>> combine(int[] nums, int k) {
        List<List<Integer>> result = new ArrayList<>();
        if (nums == null || k < 0 || k > nums.length) {
            return result;
        }

        // Clone and sort the array to handle duplicates and protect the caller's input
        int[] sortedNums = nums.clone();
        Arrays.sort(sortedNums);

        dfsCombine(0, sortedNums, k, new ArrayList<>(), result);

        return result;
    }

    private void dfsCombine(int start, int[] nums, int k, List<Integer> currentCombination,
            List<List<Integer>> result) {
        // Base case: combination of size k is complete
        if (currentCombination.size() == k) {
            result.add(new ArrayList<>(currentCombination));
            return;
        }

        // Pruning: if the remaining elements in the array are not enough
        // to complete the combination of size k, stop recursion.
        if (currentCombination.size() + (nums.length - start) < k) {
            return;
        }

        for (int i = start; i < nums.length; i++) {
            // Skip duplicates at the SAME recursion level, this prevents subset such as [1]
            // to appear twice if there arre two 1's. [1,1] would still be a valid subset.
            if (i > start && nums[i] == nums[i - 1]) {
                continue;
            }

            // 1. Make choice
            currentCombination.add(nums[i]);

            // 2. Recurse
            dfsCombine(i + 1, nums, k, currentCombination, result);

            // 3. Backtrack
            currentCombination.remove(currentCombination.size() - 1);
        }
    }

    // Example usage:
    public static void main(String[] args) {
        Combinations c = new Combinations();

        // 1. Test subsets with duplicates
        int[] numsWithDuplicates = { 1, 2, 2 };
        System.out.println("Subsets of [1, 2, 2]:");
        System.out.println(c.subsets(numsWithDuplicates));

        // 2. Test combine (size k = 2) with duplicates
        System.out.println("\nCombinations of size 2 from [1, 2, 2]:");
        System.out.println(c.combine(numsWithDuplicates, 2));
    }
}
