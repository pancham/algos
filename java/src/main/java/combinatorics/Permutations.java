package combinatorics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Permutations {

    /**
     * Generates all unique permutations of the input array.
     * Handles duplicate elements correctly by skipping redundant paths.
     *
     * Complexity:
     *   Two separate costs combine to give the final bound:
     *
     *   1. Tree traversal: O(n * n!)
     *      The recursion tree is built by filling one slot per level. At each level,
     *      the number of nodes equals the number of distinct partial permutations at that depth:
     *
     *      Level 0 — root (nothing placed yet):
     *        Only one starting state exists: the empty path [].
     *        Nodes: 1
     *
     *      Level 1 — first slot filled:
     *        From the root we can place any of the n elements. Each choice creates a node.
     *        [1,_,_]   [2,_,_]   [3,_,_]   ...
     *        Nodes: n
     *
     *      Level 2 — second slot filled:
     *        Each of the n level-1 nodes fills the second slot. One element is already in
     *        visited[], so n-1 elements remain. Each level-1 node spawns n-1 children:
     *          [1,_,_] → [1,2,_]  [1,3,_]  ...   (n-1 children)
     *          [2,_,_] → [2,1,_]  [2,3,_]  ...   (n-1 children)
     *          [3,_,_] → [3,1,_]  [3,2,_]  ...   (n-1 children)
     *        Nodes: n * (n-1)
     *
     *      Level 3 — third slot filled:
     *        Each of the n*(n-1) level-2 nodes fills the third slot. Two elements are now
     *        in visited[], so n-2 remain. Each level-2 node spawns n-2 children:
     *          [1,2,_] → [1,2,3]  [1,2,4]  ...   (n-2 children)
     *          [1,3,_] → [1,3,2]  [1,3,4]  ...   (n-2 children)
     *        Nodes: n * (n-1) * (n-2)
     *
     *      Pattern: at level k, nodes = n * (n-1) * ... * (n-k+1) = n!/(n-k)! = P(n,k)
     *        ...
     *        Level k:  n!/(n-k)!      nodes  (also written P(n,k) — n permute k)
     *        ...
     *        Level n:  n!             nodes  (leaves — fully placed permutations)
     *
     *      Full tree for n=3:
     *        Level 0:                    []                        → 1 node
     *        Level 1:         [1]        [2]        [3]            → 3 nodes
     *        Level 2:     [1,2] [1,3] [2,1] [2,3] [3,1] [3,2]    → 6 nodes
     *        Level 3:  [1,2,3][1,3,2][2,1,3][2,3,1][3,1,2][3,2,1] → 6 nodes (leaves)
     *
     *      Total nodes = 1 + n + n(n-1) + ... + n!
     *                  = n! * (1/n! + 1/(n-1)! + ... + 1/1! + 1/0!)
     *                  = n! * sum(1/k! for k=0..n)
     *                  ≈ e * n!   (since sum(1/k!) converges to e = 2.718...)
     *                  = O(n!)
     *
     *      Concrete check for n=3:
     *        1 + 3 + 6 + 6 = 16 ≈ e * 3! = 2.718 * 6 = 16.3 ✓
     *
     *      Unlike combinations (where each node does O(1) loop body work), here each node
     *      runs the for loop over ALL n elements to check visited[], regardless of depth.
     *      So each node costs O(n), not O(1).
     *
     *      Tree traversal cost = O(n) per node * O(n!) nodes = O(n * n!)
     *
     *   2. Copy cost: n * n!
     *      At each of the n! leaf nodes, we copy the current path via
     *      new ArrayList<>(currentPermutation). Every permutation has exactly n elements,
     *      so every copy costs exactly O(n). With n! permutations:
     *
     *      Copy cost = n! results * O(n) per copy = O(n * n!)
     *
     *      Unlike subsets (where copy dominated via the binomial identity), here both
     *      tree traversal and copy cost are independently O(n * n!) — they contribute equally.
     *
     *   Total: O(n * n!) + O(n * n!) = O(n * n!)
     *
     *   With duplicates: the skip rule prunes branches early, reducing actual work below
     *   O(n * n!). The true count of unique permutations is n! / (f1! * f2! * ... * fm!)
     *   where fi are the frequencies of each distinct value. O(n * n!) remains the worst-case
     *   bound (achieved when all elements are distinct).
     *
     *   Time  — O(n * n!)
     *   Space — O(n * n!) output + O(n) call stack (depth = n) + O(n) visited array
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
