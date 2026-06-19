package combinatorics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Permutations {

    /**
     * Generates all unique permutations of the input array.
     * Handles duplicate elements correctly by skipping redundant paths.
     *
     * @param nums the input array of integers
     * @return a list of all unique permutations
     */
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        if (nums == null) {
            return result;
        }

        // Clone and sort the array to handle duplicates and avoid mutating the original input
        int[] sortedNums = nums.clone();
        Arrays.sort(sortedNums);

        boolean[] visited = new boolean[sortedNums.length];
        
        // Start the DFS with an empty current permutation
        dfs(sortedNums, new ArrayList<>(), visited, result);
        
        return result;
    }

    private void dfs(int[] nums, List<Integer> currentPermutation, boolean[] visited, List<List<Integer>> result) {
        // Base case: if the current permutation is complete
        if (currentPermutation.size() == nums.length) {
            // We must create a new ArrayList copy, otherwise we would be adding 
            // a reference to a list that keeps changing
            result.add(new ArrayList<>(currentPermutation));
            return;
        }

        // Explore choices
        for (int i = 0; i < nums.length; i++) {
            // If the number at index i is already used, skip it
            if (visited[i]) {
                continue;
            }

            // Skip duplicate elements to avoid duplicate permutations.
            // We only select a duplicate element if its predecessor is already visited.
            if (i > 0 && nums[i] == nums[i - 1] && !visited[i - 1]) {
                continue;
            }

            // 1. Make the choice
            visited[i] = true;
            currentPermutation.add(nums[i]);

            // 2. Move deeper into the tree (DFS)
            dfs(nums, currentPermutation, visited, result);

            // 3. Undo the choice (Backtrack)
            currentPermutation.remove(currentPermutation.size() - 1);
            visited[i] = false;
        }
    }

    // Example usage:
    public static void main(String[] args) {
        Permutations p = new Permutations();
        int[] numbers = {1, 2, 3};
        List<List<Integer>> allPermutations = p.permute(numbers);
        System.out.println("Permutations of [1, 2, 3]: " + allPermutations);

        int[] duplicates = {1, 1, 2};
        List<List<Integer>> duplicatePermutations = p.permute(duplicates);
        System.out.println("Permutations of [1, 1, 2]: " + duplicatePermutations);
    }
}
