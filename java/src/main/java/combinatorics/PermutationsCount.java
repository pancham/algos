package combinatorics;

import java.util.*;

/**
 * Count-only variant of permutation generation.
 *
 * Computes the number of unique permutations mathematically without generating them.
 * This bypasses the output-sensitive lower bound that makes generation O(n * n!) —
 * since there is no output to write, the bottleneck disappears entirely.
 *
 * Formula — the multinomial coefficient:
 *
 *   count = n! / (f1! * f2! * ... * fm!)
 *
 *   where n   = total number of elements
 *         fi  = frequency of each distinct value
 *         m   = number of distinct values
 *
 * Why this formula works:
 *   If all n elements were distinct, there would be n! arrangements.
 *   But when fi elements share the same value, swapping them among themselves
 *   produces arrangements that look identical. There are fi! such swaps for each
 *   group, so we divide out fi! to remove those duplicates.
 *   Doing this for every group gives n! / (f1! * f2! * ... * fm!).
 *
 * Example: [1, 1, 2]
 *   n=3, freq(1)=2, freq(2)=1
 *   count = 3! / (2! * 1!) = 6 / 2 = 3
 *   actual permutations: [1,1,2]  [1,2,1]  [2,1,1]  ✓
 */
public class PermutationsCount {

    /**
     * Counts all unique permutations of the input array.
     *
     * Implementation — multinomial via cascading binomial coefficients:
     *
     *   Instead of computing n! and dividing by each fi! (which overflows for large n),
     *   we use the identity:
     *
     *     n! / (f1! * f2! * ... * fm!)  =  C(n, f1) * C(n-f1, f2) * C(n-f1-f2, f3) * ...
     *
     *   Interpretation: choose f1 positions from n for the first value (C(n, f1) ways),
     *   then choose f2 positions from the remaining n-f1 for the second value, and so on.
     *   Each C(n,k) is computed without large intermediates (see binomial() below).
     *
     *   Trace for [1, 1, 2]:
     *     Frequencies: f(1)=2, f(2)=1
     *     remaining=3: C(3,2) = 3  → 3 ways to place the two 1s among 3 positions
     *     remaining=1: C(1,1) = 1  → 1 way to place the one 2 in the last position
     *     count = 3 * 1 = 3  ✓
     *
     *   Trace for [1, 1, 1, 2, 2]:
     *     Frequencies: f(1)=3, f(2)=2
     *     remaining=5: C(5,3) = 10  → 10 ways to place the three 1s among 5 positions
     *     remaining=2: C(2,2) = 1   → 1 way to place the two 2s in the last 2 positions
     *     count = 10 * 1 = 10  ✓  (verify: 5! / (3! * 2!) = 120 / 12 = 10)
     *
     * Complexity:
     *   Time  — O(n log n) to sort + O(n) to count frequencies + O(n) for binomial calls.
     *           Each binomial(remaining, f) call costs O(min(f, remaining-f)) <= O(n),
     *           and the sum of all fi = n, so total binomial work across all groups is O(n).
     *           Overall: O(n log n).
     *           vs O(n * n!) to generate all permutations — factorial savings.
     *   Space — O(d) for the frequency list where d = number of distinct values (d <= n).
     *
     * @param nums the input array of integers
     * @return the count of unique permutations
     */
    public long countPermutations(int[] nums) {
        if (nums == null || nums.length == 0) return 1; // one permutation: []

        int[] sorted = nums.clone();
        Arrays.sort(sorted);

        // Collect frequencies of each distinct value
        List<Integer> freqs = new ArrayList<>();
        int freq = 1;
        for (int i = 1; i < sorted.length; i++) {
            if (sorted[i] == sorted[i - 1]) {
                freq++;
            } else {
                freqs.add(freq);
                freq = 1;
            }
        }
        freqs.add(freq); // add the last group

        // Apply: C(n, f1) * C(n-f1, f2) * C(n-f1-f2, f3) * ...
        long count = 1;
        int remaining = sorted.length;
        for (int f : freqs) {
            count *= binomial(remaining, f);
            remaining -= f;
        }

        return count;
    }

    /**
     * Computes C(n, k) = n! / (k! * (n-k)!) iteratively.
     *
     * Uses the recurrence C(n,k) = C(n,k-1) * (n-k+1) / k, interleaving
     * multiply and divide to keep intermediate values small and avoid overflow
     * for moderate n (safe up to approximately n=62 in a long).
     *
     * Also exploits the symmetry C(n,k) = C(n, n-k), always using the smaller k.
     *
     * Complexity: O(min(k, n-k))
     */
    private long binomial(int n, int k) {
        if (k > n - k) k = n - k;
        long result = 1;
        for (int i = 0; i < k; i++) {
            result = result * (n - i) / (i + 1);
        }
        return result;
    }

    // -------------------------------------------------------------------------
    // Main
    // -------------------------------------------------------------------------

    public static void main(String[] args) {
        PermutationsCount pc = new PermutationsCount();

        // All distinct — count = n!
        int[] nums1 = {1, 2, 3};
        System.out.println("[1,2,3]       → " + pc.countPermutations(nums1));  // 6 = 3!

        // One duplicate pair
        int[] nums2 = {1, 1, 2};
        System.out.println("[1,1,2]       → " + pc.countPermutations(nums2));  // 3

        // Two groups of duplicates
        int[] nums3 = {1, 1, 2, 2};
        System.out.println("[1,1,2,2]     → " + pc.countPermutations(nums3));  // 6 = 4!/(2!*2!)

        // All identical — only one unique permutation
        int[] nums4 = {1, 1, 1};
        System.out.println("[1,1,1]       → " + pc.countPermutations(nums4));  // 1

        // Larger case: 5!/(3!*2!) = 10
        int[] nums5 = {1, 1, 1, 2, 2};
        System.out.println("[1,1,1,2,2]   → " + pc.countPermutations(nums5));  // 10

        // Single element
        int[] nums6 = {5};
        System.out.println("[5]           → " + pc.countPermutations(nums6));  // 1
    }
}
