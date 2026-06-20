package combinatorics;

import java.util.*;

/**
 * Count-only variants of subset and combination enumeration.
 *
 * Both methods compute counts mathematically without generating actual results.
 * This bypasses the output-sensitive lower bound that makes generation O(n * 2^n)
 * or O(k * C(n,k)) — since there is no output to write, the bottleneck disappears.
 *
 * Handles duplicate elements correctly using a sorted input and frequency grouping.
 */
public class CombinationsCount {

    // -------------------------------------------------------------------------
    // Count Subsets
    // -------------------------------------------------------------------------

    /**
     * Counts all unique subsets (power set) of the input array.
     *
     * Core idea — product formula:
     *   For each distinct value with frequency f, you independently choose how many
     *   copies to include: 0, 1, 2, ..., f. That gives (f + 1) choices per distinct value.
     *   The +1 accounts for the option of including zero copies (excluding the value entirely).
     *   Since choices across distinct values are independent, multiply them together.
     *
     *   Example: [1, 1, 2]
     *     value 1 appears twice  → f=2 → (2+1) = 3 choices: take 0, 1, or 2 ones
     *     value 2 appears once   → f=1 → (1+1) = 2 choices: take 0 or 1 twos
     *     total = 3 * 2 = 6 subsets: [], [1], [1,1], [2], [1,2], [1,1,2]
     *
     *   Why [1,1] is valid: the two 1s are distinct items even though they share the same
     *   value. Choosing both copies produces a different subset than choosing just one.
     *   The DFS in Combinations.java also generates [1,1] — the duplicate-skip rule there
     *   only prevents generating [1] twice as siblings, not taking both 1s along a deeper path.
     *
     * Complexity:
     *   Time  — O(n log n) to sort + O(n) to scan frequencies = O(n log n).
     *           vs O(n * 2^n) to generate all subsets — exponential savings.
     *   Space — O(1) extra (sort in place on a clone).
     */
    public int countSubsets(int[] nums) {
        if (nums == null || nums.length == 0) return 1; // one subset: []

        int[] sorted = nums.clone();
        Arrays.sort(sorted);

        int count = 1;
        int freq = 1;

        for (int i = 1; i < sorted.length; i++) {
            if (sorted[i] == sorted[i - 1]) {
                freq++;
            } else {
                count *= (freq + 1); // (f+1) choices for the previous distinct value
                freq = 1;
            }
        }
        count *= (freq + 1); // apply the last group

        return count;
    }

    // -------------------------------------------------------------------------
    // Count Combinations
    // -------------------------------------------------------------------------

    /**
     * Counts all unique combinations of size k from the input array.
     *
     * Core idea — 1D dynamic programming over element groups:
     *
     *   dp[j] = number of unique multiset combinations of size j
     *           using only the distinct values processed so far.
     *
     *   Initial state: dp[0] = 1 (one way to form a combination of size 0: take nothing),
     *                  dp[j] = 0 for j > 0 (no elements seen yet, no other sizes possible).
     *
     *   For each group of identical elements (value v appearing `freq` times):
     *     We decide how many copies of v to take: 0, 1, 2, ..., min(freq, j) copies.
     *     Taking 0 is already accounted for (dp[j] keeps its current value).
     *     Taking `take` copies adds dp[j - take] to dp[j] for each valid `take`.
     *
     *   We iterate j from k down to 1 (right-to-left) within each group.
     *   This is the same direction as 0/1 knapsack and is critical for correctness:
     *   it ensures that dp[j - take] still reflects the state *before* processing this
     *   group, so we never exceed the group's frequency limit of `freq`.
     *   If we iterated left-to-right, dp[j - take] might already include copies
     *   added in the current group, effectively allowing more than `freq` takes.
     *
     *   Trace for [1, 1, 2], k=2:
     *     Initial:        dp = [1, 0, 0]
     *
     *     Group v=1, f=2: iterate j from 2 down to 1
     *       j=2: take=1 → dp[2] += dp[1]=0; take=2 → dp[2] += dp[0]=1  → dp[2]=1
     *       j=1: take=1 → dp[1] += dp[0]=1                               → dp[1]=1
     *                       dp = [1, 1, 1]
     *
     *     Group v=2, f=1: iterate j from 2 down to 1
     *       j=2: take=1 → dp[2] += dp[1]=1                               → dp[2]=2
     *       j=1: take=1 → dp[1] += dp[0]=1                               → dp[1]=2
     *                       dp = [1, 2, 2]
     *
     *     Answer: dp[2] = 2 → combinations: [1,1] and [1,2]  ✓
     *
     * Complexity:
     *   Time  — O(n log n) to sort + O(d * k) for the DP, where d = number of distinct values.
     *           d <= n so the DP is at most O(n * k). Total: O(n * k).
     *           vs O(k * C(n,k)) to generate all combinations — dramatically faster when
     *           C(n,k) is large (e.g. n=20, k=10: C(20,10)=184,756 vs n*k=200).
     *   Space — O(k) for the dp array.
     */
    public int countCombinations(int[] nums, int k) {
        if (nums == null || k < 0 || k > nums.length) return 0;
        if (k == 0) return 1;

        int[] sorted = nums.clone();
        Arrays.sort(sorted);

        int[] dp = new int[k + 1];
        dp[0] = 1; // base case: one way to choose nothing

        int i = 0;
        while (i < sorted.length) {
            // Count frequency of the current distinct value
            int freq = 1;
            while (i + freq < sorted.length && sorted[i + freq] == sorted[i]) freq++;

            // Process this group right-to-left to avoid exceeding frequency limit
            for (int j = k; j >= 1; j--) {
                for (int take = 1; take <= freq && take <= j; take++) {
                    dp[j] += dp[j - take];
                }
            }

            i += freq; // advance past this group
        }

        return dp[k];
    }

    // -------------------------------------------------------------------------
    // Main
    // -------------------------------------------------------------------------

    public static void main(String[] args) {
        CombinationsCount cc = new CombinationsCount();

        int[] nums1 = {1, 1, 2};
        System.out.println("Array: [1, 1, 2]");
        System.out.println("  countSubsets:          " + cc.countSubsets(nums1));       // 6
        System.out.println("  countCombinations k=2: " + cc.countCombinations(nums1, 2)); // 2

        int[] nums2 = {1, 2, 2};
        System.out.println("Array: [1, 2, 2]");
        System.out.println("  countSubsets:          " + cc.countSubsets(nums2));       // 6
        System.out.println("  countCombinations k=2: " + cc.countCombinations(nums2, 2)); // 2

        int[] nums3 = {1, 2, 3};
        System.out.println("Array: [1, 2, 3]");
        System.out.println("  countSubsets:          " + cc.countSubsets(nums3));       // 8 = 2^3
        System.out.println("  countCombinations k=2: " + cc.countCombinations(nums3, 2)); // 3
    }
}
