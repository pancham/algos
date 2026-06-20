package combinatorics;

import java.util.*;

/**
 * Optimized subset and combination generation with three improvements
 * over the baseline DFS approach in Combinations.java:
 *
 * 1. Bitmask iteration for subsets — avoids recursion stack; O(1) extra space
 * during traversal
 * vs O(n) call stack in DFS.
 * 2. Pre-allocated result lists — sizes ArrayList to the exact output count
 * upfront,
 * eliminating repeated internal array resizing.
 * 3. Lazy iterator variants — yield one result at a time, reducing peak memory
 * from
 * O(n * 2^n) / O(k * C(n,k)) down to O(n) / O(k).
 *
 * Asymptotic time complexity is unchanged — the output-sensitive lower bound
 * cannot be beaten
 * because you must write every result to memory regardless of the algorithm
 * used.
 * These are constant-factor and peak-memory improvements.
 */
public class CombinationsOptimized {

    // -------------------------------------------------------------------------
    // Subsets
    // -------------------------------------------------------------------------

    /**
     * Generates all unique subsets using bitmask iteration instead of DFS.
     *
     * Each integer mask in [0, 2^n) represents a candidate subset:
     * bit i set means nums[i] is included. Masks that select elements with the same
     * values produce duplicate subsets, which we skip via isValidMask().
     *
     * Duplicate rule: for a sorted array, a duplicate element at position i is only
     * valid
     * if the element at position i-1 is also selected. This mirrors the DFS skip
     * rule
     * (i > start and nums[i] == nums[i-1]) but operates on bit patterns.
     *
     * Advantage over DFS: no recursion stack; O(1) extra space during traversal.
     *
     * Complexity:
     * Two separate costs combine to give the final bound:
     *
     * 1. Mask iteration: O(2^n)
     * Iterate over all 2^n masks, each validated in O(n).
     *
     * 2. Copy cost: n * 2^(n-1)
     * Each subset is copied into a new list. Total copy work = sum of s * C(n,s)
     * for s = 0..n, which equals n * 2^(n-1) by the binomial identity
     * (see Combinations.java for the full derivation). The 'n' multiplier comes
     * entirely from this per-result copy, not from an extra loop.
     *
     * Total: 2^n + n * 2^(n-1)
     * = n * 2^(n-1) + 2 * 2^(n-1) [rewrite 2^n as 2 * 2^(n-1)]
     * = 2^(n-1) * (n + 2)
     * = O(n * 2^n). Copy term dominates (e.g. at n=20: traversal=1M, copy=10M).
     *
     * Time — O(n * 2^n)
     * Space — O(n * 2^n) output + O(1) extra (no recursion stack, vs O(n) in DFS)
     */
    public List<List<Integer>> subsets(int[] nums) {
        if (nums == null)
            return new ArrayList<>();

        int[] sorted = nums.clone();
        Arrays.sort(sorted);
        int n = sorted.length;
        int total = 1 << n;

        // Pre-allocate to 2^n — the maximum number of subsets before deduplication
        List<List<Integer>> result = new ArrayList<>(total);

        for (int mask = 0; mask < total; mask++) {
            if (!isValidMask(mask, sorted))
                continue;
            // Pre-allocate each subset to its exact size using the bit count of the mask
            List<Integer> subset = new ArrayList<>(Integer.bitCount(mask));
            for (int i = 0; i < n; i++) {
                if ((mask >> i & 1) == 1)
                    subset.add(sorted[i]);
            }
            result.add(subset);
        }
        return result;
    }

    /**
     * A mask is invalid if it selects a duplicate element without selecting its
     * predecessor.
     * e.g. sorted=[1,2,2]: mask 100 selects index 2 (second 2) but not index 1
     * (first 2),
     * producing the same subset [2] as mask 010 — a duplicate.
     */
    private boolean isValidMask(int mask, int[] sorted) {
        for (int i = 1; i < sorted.length; i++) {
            if (sorted[i] == sorted[i - 1]
                    && (mask >> i & 1) == 1
                    && (mask >> (i - 1) & 1) == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Lazy variant: yields one subset at a time without materializing the full
     * output.
     *
     * Uses a bitmask counter — next() builds the subset for the current mask and
     * advances to the next valid mask. Only one subset exists in memory at a time.
     *
     * Complexity:
     * Time — O(n * 2^n) total across all next() calls (same as eager version).
     * Space — O(n) peak: only one subset in memory at any moment,
     * vs O(n * 2^n) for the eager version — significant savings for large n.
     * Example: n=25 eager needs ~800MB; lazy needs a few KB.
     */
    public Iterable<List<Integer>> subsetsLazy(int[] nums) {
        if (nums == null)
            return Collections.emptyList();

        int[] sorted = nums.clone();
        Arrays.sort(sorted);
        int total = 1 << sorted.length;

        return () -> new Iterator<List<Integer>>() {
            int mask = findNextValid(0);

            public boolean hasNext() {
                return mask < total;
            }

            public List<Integer> next() {
                if (!hasNext())
                    throw new NoSuchElementException();
                List<Integer> subset = new ArrayList<>(Integer.bitCount(mask));
                for (int i = 0; i < sorted.length; i++) {
                    if ((mask >> i & 1) == 1)
                        subset.add(sorted[i]);
                }
                mask = findNextValid(mask + 1);
                return subset;
            }

            private int findNextValid(int from) {
                while (from < total && !isValidMask(from, sorted))
                    from++;
                return from;
            }
        };
    }

    // -------------------------------------------------------------------------
    // Combinations
    // -------------------------------------------------------------------------

    /**
     * Generates all unique combinations of size k using DFS with pruning and
     * pre-allocation.
     *
     * Same DFS + backtracking algorithm as Combinations.java, with two
     * improvements:
     * - result list pre-allocated to C(n,k) via the binomial() helper.
     * - each combination list pre-allocated to k, avoiding resizing during
     * backtracking.
     *
     * Pruning (carried over from baseline): the guard
     * current.size() + (nums.length - start) < k
     * cuts branches where not enough elements remain to reach size k. Does not
     * change the
     * asymptotic bound but meaningfully reduces the constant factor.
     *
     * Complexity:
     * Two separate costs combine to give the final bound:
     *
     * 1. Tree traversal: C(n,k) nodes, each doing O(1) work.
     *
     * 2. Copy cost: k * C(n,k)
     * Every result has exactly size k, so each copy via new ArrayList<>(current)
     * costs exactly O(k). With C(n,k) results, copy work = k * C(n,k).
     * Unlike subsets, no identity is needed — size is fixed, so it is direct
     * multiplication.
     *
     * Total: C(n,k) + k * C(n,k)
     * = C(n,k) * (1 + k)
     * = O(k * C(n,k)). Copy term dominates for any k >= 1.
     *
     * Time — O(k * C(n,k))
     * Space — O(k * C(n,k)) output + O(k) call stack (recursion depth <= k)
     */
    public List<List<Integer>> combine(int[] nums, int k) {
        if (nums == null || k < 0 || k > nums.length)
            return new ArrayList<>();

        int[] sorted = nums.clone();
        Arrays.sort(sorted);

        // Pre-allocate to exact result count C(n,k)
        List<List<Integer>> result = new ArrayList<>(binomial(sorted.length, k));
        dfsCombine(0, sorted, k, new ArrayList<>(k), result);
        return result;
    }

    private void dfsCombine(int start, int[] nums, int k,
            List<Integer> current, List<List<Integer>> result) {
        if (current.size() == k) {
            result.add(new ArrayList<>(current));
            return;
        }
        if (current.size() + (nums.length - start) < k)
            return;

        for (int i = start; i < nums.length; i++) {
            if (i > start && nums[i] == nums[i - 1])
                continue;
            current.add(nums[i]);
            dfsCombine(i + 1, nums, k, current, result);
            current.remove(current.size() - 1);
        }
    }

    /**
     * Lazy variant: yields one combination at a time without materializing the full
     * output.
     *
     * Maintains an index array [c0, c1, ..., c_{k-1}] where each ci is a position
     * in sorted[].
     * Combinations are enumerated in lexicographic order using this rule:
     * - Find the rightmost index ci that can still advance (ci < n - k + i).
     * - Increment ci and reset all indices to its right to ci+1, ci+2, ...
     * This enumerates all C(n,k) index arrays without recursion.
     *
     * Duplicate handling: an index array is invalid if it picks a duplicate element
     * (sorted[ci] == sorted[ci-1]) without also picking its predecessor (ci-1).
     * The advance() loop skips invalid arrays automatically, mirroring the DFS skip
     * rule.
     *
     * Complexity:
     * Time — O(k * C(n,k)) total across all next() calls.
     * Space — O(k) peak: only the index array and one combination in memory at any
     * moment,
     * vs O(k * C(n,k)) for the eager version.
     */
    public Iterable<List<Integer>> combineLazy(int[] nums, int k) {
        if (nums == null || k < 0 || k > nums.length)
            return Collections.emptyList();

        int[] sorted = nums.clone();
        Arrays.sort(sorted);
        int n = sorted.length;

        return () -> new Iterator<List<Integer>>() {
            // Initialize to [0, 1, ..., k-1] — always valid for a sorted array since
            // consecutive indices mean every chosen duplicate has its predecessor chosen.
            int[] indices = initIndices(k);
            boolean hasNext = true;

            public boolean hasNext() {
                return hasNext;
            }

            public List<Integer> next() {
                if (!hasNext())
                    throw new NoSuchElementException();
                List<Integer> combo = new ArrayList<>(k);
                for (int idx : indices)
                    combo.add(sorted[idx]);
                advance();
                return combo;
            }

            private void advance() {
                while (true) {
                    // Find the rightmost index that can still be incremented
                    int i = k - 1;
                    while (i >= 0 && indices[i] == n - k + i)
                        i--;

                    if (i < 0) {
                        hasNext = false;
                        return;
                    }

                    // Increment it and reset everything to its right to consecutive positions
                    indices[i]++;
                    for (int j = i + 1; j < k; j++)
                        indices[j] = indices[j - 1] + 1;

                    // Skip invalid arrays (duplicate elements chosen without their predecessor)
                    if (isValidIndices(indices))
                        return;
                }
            }

            // An index array is invalid if it picks a duplicate element
            // (sorted[pos] == sorted[pos-1]) without also picking sorted[pos-1].
            // Since indices are sorted, sorted[pos-1] is picked iff indices[j-1] == pos-1.
            private boolean isValidIndices(int[] idx) {
                for (int j = 0; j < idx.length; j++) {
                    int pos = idx[j];
                    if (pos > 0 && sorted[pos] == sorted[pos - 1]) {
                        if (j == 0 || idx[j - 1] != pos - 1)
                            return false;
                    }
                }
                return true;
            }

            private int[] initIndices(int size) {
                int[] idx = new int[size];
                for (int i = 0; i < size; i++)
                    idx[i] = i;
                return idx;
            }
        };
    }

    // -------------------------------------------------------------------------
    // Utilities
    // -------------------------------------------------------------------------

    /**
     * Computes C(n, k) iteratively using the recurrence C(n,k) = C(n,k-1) * (n-k+1)
     * / k.
     * Avoids large intermediate factorials by interleaving multiply and divide.
     */
    private int binomial(int n, int k) {
        if (k > n - k)
            k = n - k; // C(n,k) == C(n, n-k); use the smaller k
        long result = 1;
        for (int i = 0; i < k; i++) {
            result = result * (n - i) / (i + 1);
        }
        return (int) result;
    }

    public static void main(String[] args) {
        CombinationsOptimized c = new CombinationsOptimized();
        int[] nums = { 1, 2, 2 };

        System.out.println("Subsets (eager):      " + c.subsets(nums));
        System.out.print("Subsets (lazy):       ");
        c.subsetsLazy(nums).forEach(s -> System.out.print(s + " "));
        System.out.println();

        System.out.println("Combine k=2 (eager):  " + c.combine(nums, 2));
        System.out.print("Combine k=2 (lazy):   ");
        c.combineLazy(nums, 2).forEach(s -> System.out.print(s + " "));
        System.out.println();
    }
}
