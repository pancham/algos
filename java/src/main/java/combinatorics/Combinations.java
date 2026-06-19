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
     * @param nums the input array of integers
     * @param k the size of combinations to generate
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

    private void dfsCombine(int start, int[] nums, int k, List<Integer> currentCombination, List<List<Integer>> result) {
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
            // Skip duplicates at the same recursion level
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
        int[] numsWithDuplicates = {1, 2, 2};
        System.out.println("Subsets of [1, 2, 2]:");
        System.out.println(c.subsets(numsWithDuplicates));

        // 2. Test combine (size k = 2) with duplicates
        System.out.println("\nCombinations of size 2 from [1, 2, 2]:");
        System.out.println(c.combine(numsWithDuplicates, 2));
    }
}
