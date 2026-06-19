package combinatorics;

import java.util.ArrayList;
import java.util.List;

/**
 * Generates permutations of an array using the in-place swapping method.
 * 
 * =========================================================================
 * CORE CONCEPT: "Locked Prefix" vs. "Pending Suffix"
 * =========================================================================
 * Instead of building a permutation from scratch in a separate list and 
 * checking a 'visited' array, the swapping method rearranges the input 
 * array in-place.
 * 
 * At any point during the recursion, the array is split into two logical parts:
 * 
 *   - Locked Prefix: nums[0 ... start-1]
 *     The elements before 'start' are already placed in their final positions
 *     for this branch of the recursion tree.
 * 
 *   - Pending Suffix: nums[start ... N-1] (where N is the length of the array)
 *     The elements from 'start' to the end of the array are the pool of choices
 *     we have left to place.
 * 
 * =========================================================================
 * HOW THE RECURSION WORKS
 * =========================================================================
 * 1. start represents the current position in the array we want to fill.
 * 2. We loop index i from start to N-1.
 * 3. For each index i, we swap the element at start with the element at i.
 *    This acts as our "choice" to place the element nums[i] at position start.
 * 4. We recurse on start + 1 to solve the remaining subproblem.
 * 5. Once the recursive call returns, we swap the elements back (backtrack)
 *    to restore the original array order before proceeding to the next choice of i.
 * 6. Base case: When start == nums.length, all positions are filled. We copy 
 *    the current state of the array into our results list.
 * 
 * =========================================================================
 * ADVANTAGES
 * =========================================================================
 * - Space Efficiency: O(1) auxiliary space (excluding the recursion stack). 
 *   We do not need an auxiliary boolean 'visited' array or a temporary list 
 *   to track the current path.
 */
public class PermutationsSwap {

    /**
     * Generates all permutations of the input array.
     * Note: If the input contains duplicate elements, this method will
     * generate duplicate permutations.
     * 
     * @param nums the input array of integers
     * @return a list of all permutations
     */
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        if (nums == null) {
            return result;
        }

        // We clone the input array to avoid mutating the original input array passed by the caller
        int[] numsCopy = nums.clone();
        backtrack(numsCopy, 0, result);
        return result;
    }

    private void backtrack(int[] nums, int start, List<List<Integer>> result) {
        // Base case: if start reaches the end, a complete permutation is formed
        if (start == nums.length) {
            List<Integer> permutation = new ArrayList<>();
            for (int num : nums) {
                permutation.add(num);
            }
            result.add(permutation);
            return;
        }

        for (int i = start; i < nums.length; i++) {
            // 1. Make the choice: swap start and i to place nums[i] at position start
            swap(nums, start, i);

            // 2. Recurse: find all permutations for the remaining suffix
            backtrack(nums, start + 1, result);

            // 3. Backtrack: swap back to restore the original state
            swap(nums, start, i);
        }
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    // Example usage:
    public static void main(String[] args) {
        PermutationsSwap p = new PermutationsSwap();
        int[] numbers = {1, 2, 3};
        List<List<Integer>> allPermutations = p.permute(numbers);
        System.out.println("Permutations (Swap Method) of [1, 2, 3]:");
        System.out.println(allPermutations);
    }
}
