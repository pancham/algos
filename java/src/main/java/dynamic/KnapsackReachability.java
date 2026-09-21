package dynamic;

import java.util.*;

/**
 * KnapsackReachability
 *
 * Demonstrates 1D Boolean Dynamic Programming (Subset Sum / Target Reachability)
 * across the three knapsack boundedness variations:
 *
 * 1. 0/1 Knapsack      - Each item/coin can be used AT MOST ONCE.
 * 2. Bounded (0/k)     - Each item/coin can be used UP TO k (or count[i]) TIMES.
 * 3. Unbounded         - Each item/coin can be used UNLIMITED TIMES.
 *
 * State definition:
 * dp[j] = true if an amount/sum of exactly j is achievable, false otherwise.
 *
 * Base condition:
 * dp[0] = true (sum of 0 is always achievable with an empty set).
 */
public class KnapsackReachability {

    // ------------------------------------------------------------------------
    // 1. 0/1 KNAPSACK REACHABILITY
    // ------------------------------------------------------------------------
    //
    // Each coin can be used at most ONCE.
    // Loop direction: DESCENDING (amount down to coin)
    // Why: Prevents using the same coin multiple times in the same iteration.
    //
    // Corresponding LeetCode Problems:
    // - LC 416: Partition Equal Subset Sum (Medium)
    //   -> Can we partition nums into two subsets with equal sum?
    //   -> Target = totalSum / 2. Direct 1-to-1 match.
    //
    // - LC 1049: Last Stone Weight II (Medium)
    //   -> Partition stones into two piles to minimize difference.
    //   -> Equivalent to finding the largest achievable sum <= totalSum / 2.
    //
    // - LC 494: Target Sum (Medium)
    //   -> Assign + / - to reach target.
    //   -> Transforms to subset sum target = (totalSum + target) / 2.
    //   -> Same loop structure, but counts ways (dp[j] += dp[j - num]) instead of ||.
    // ------------------------------------------------------------------------

    public static boolean canReach01(int[] coins, int amount) {
        boolean[] dp = new boolean[amount + 1];
        dp[0] = true;

        for (int coin : coins) {
            // DESCENDING: ensures each coin is used at most once
            for (int j = amount; j >= coin; j--) {
                dp[j] = dp[j] || dp[j - coin];
            }
        }

        return dp[amount];
    }

    // ------------------------------------------------------------------------
    // 2. BOUNDED (0/k) KNAPSACK REACHABILITY
    // ------------------------------------------------------------------------
    //
    // Each coin i can be used at most counts[i] times.
    // Loop direction: DESCENDING for each allowed copy.
    //
    // Corresponding LeetCode Problems:
    // - LC 2585: Number of Ways to Earn Points (Hard)
    //   -> types[i] = [count_i, marks_i].
    //   -> Can solve at most count_i questions of type i.
    //   -> Exact bounded knapsack structure (sums combinations modulo 10^9 + 7).
    //
    // - LC 474: Ones and Zeroes (Medium)
    //   -> 2D Bounded Knapsack: each string costs zeros and ones, used at most once.
    //   -> Outer bounds on m zeros and n ones.
    //
    // - Classic Competitive Programming: POJ 1742 (Coins)
    //   -> Given coin values A[1..n] and quantities C[1..n], find how many
    //      sums in 1..m can be formed. Exact boolean bounded reachability.
    // ------------------------------------------------------------------------

    public static boolean canReachBounded(int[] coins, int[] counts, int amount) {
        boolean[] dp = new boolean[amount + 1];
        dp[0] = true;

        for (int i = 0; i < coins.length; i++) {
            int coin = coins[i];
            int k = counts[i];

            // Process each of the k copies as an independent 0/1 item
            for (int x = 0; x < k; x++) {
                // DESCENDING: ensures this particular copy is used at most once
                for (int j = amount; j >= coin; j--) {
                    dp[j] = dp[j] || dp[j - coin];
                }
            }
        }

        return dp[amount];
    }

    // ------------------------------------------------------------------------
    // 3. UNBOUNDED KNAPSACK REACHABILITY
    // ------------------------------------------------------------------------
    //
    // Each coin can be used UNLIMITED times.
    // Loop direction: ASCENDING (coin up to amount)
    // Why: Freshly computed dp[j - coin] can be reused immediately for dp[j],
    //      allowing the same coin to be selected multiple times.
    //
    // Corresponding LeetCode Problems:
    // - LC 322: Coin Change (Medium)
    //   -> Minimum coins to reach amount.
    //   -> dp[j] = min(dp[j], dp[j - coin] + 1) with ASCENDING loop.
    //
    // - LC 518: Coin Change II (Medium)
    //   -> Total number of combinations to form amount.
    //   -> dp[j] = dp[j] + dp[j - coin] with ASCENDING loop.
    //
    // - LC 139: Word Break (Medium)
    //   -> Can s be segmented using dictionary words unlimited times?
    //   -> dp[j] = true if dp[j - word.length()] is true.
    //
    // - LC 279: Perfect Squares (Medium)
    //   -> Least number of perfect square numbers (1, 4, 9, 16...) that sum to n.
    //   -> Squares can be reused unlimited times.
    // ------------------------------------------------------------------------

    public static boolean canReachUnbounded(int[] coins, int amount) {
        boolean[] dp = new boolean[amount + 1];
        dp[0] = true;

        for (int coin : coins) {
            // ASCENDING: allows the same coin to be reused repeatedly
            for (int j = coin; j <= amount; j++) {
                dp[j] = dp[j] || dp[j - coin];
            }
        }

        return dp[amount];
    }

    // ------------------------------------------------------------------------
    // DEMONSTRATION & COMPARISON
    // ------------------------------------------------------------------------

    public static void main(String[] args) {
        // Example: coins = {3, 5}, target amount = 9
        //
        // 0/1:
        //   Can we make 9 using each of {3, 5} at most once?
        //   Possible sums: {0, 3, 5, 8} -> 9 is FALSE.
        //
        // Bounded (counts = {2, 1}):
        //   Coins available: 3 (up to 2 times), 5 (up to 1 time).
        //   Possible sums: 0, 3, 5, 6 (3+3), 8 (3+5), 11 (3+3+5) -> 9 is FALSE.
        //
        // Bounded (counts = {3, 1}):
        //   Coins available: 3 (up to 3 times), 5 (up to 1 time).
        //   Possible sums include 9 (3+3+3) -> 9 is TRUE.
        //
        // Unbounded:
        //   Coins can be reused unlimited times.
        //   3 + 3 + 3 = 9 -> 9 is TRUE.

        int[] coins = { 3, 5 };
        int amount = 9;

        boolean zeroOne = canReach01(coins, amount);

        int[] countsLimited = { 2, 1 };
        boolean boundedLimited = canReachBounded(coins, countsLimited, amount);

        int[] countsEnough = { 3, 1 };
        boolean boundedEnough = canReachBounded(coins, countsEnough, amount);

        boolean unbounded = canReachUnbounded(coins, amount);

        System.out.println("Amount = " + amount + ", Coins = " + Arrays.toString(coins));
        System.out.println("0/1 Reachable?                   : " + zeroOne);
        System.out.println("Bounded (counts [2, 1]) Reachable: " + boundedLimited);
        System.out.println("Bounded (counts [3, 1]) Reachable: " + boundedEnough);
        System.out.println("Unbounded Reachable?             : " + unbounded);
    }
}

/*
 * ============================================================================
 * LEETCODE PROBLEM MAPPING & PATTERN CHEATSHEET
 * ============================================================================
 *
 * VARIATION 1: 0/1 KNAPSACK (DESCENDING LOOP)
 * ----------------------------------------------------------------------------
 * Loop: for (int j = amount; j >= coin; j--)
 *
 * Problem                             Target Value / DP Transition
 * ----------------------------------  ----------------------------------------
 * LC 416. Partition Equal Subset Sum  dp[j] = dp[j] || dp[j - num]
 * LC 1049. Last Stone Weight II       dp[j] = dp[j] || dp[j - stone]
 * LC 494. Target Sum                  dp[j] += dp[j - num]
 * LC 474. Ones and Zeroes             dp[i][j] = max(dp[i][j], dp[i-z][j-o] + 1)
 *
 *
 * VARIATION 2: BOUNDED / 0-k KNAPSACK (COPIES + DESCENDING LOOP)
 * ----------------------------------------------------------------------------
 * Loop: for (int x = 0; x < k; x++)
 *           for (int j = amount; j >= coin; j--)
 *
 * Problem                             Target Value / DP Transition
 * ----------------------------------  ----------------------------------------
 * LC 2585. Number of Ways to Earn     dp[j] = (dp[j] + dp[j - mark * count])
 *          Points                     (bounded by available questions)
 * POJ 1742. Coins (Classic)           dp[j] = dp[j] || dp[j - coin]
 *                                     (bounded by available coin supply)
 *
 *
 * VARIATION 3: UNBOUNDED KNAPSACK (ASCENDING LOOP)
 * ----------------------------------------------------------------------------
 * Loop: for (int j = coin; j <= amount; j++)
 *
 * Problem                             Target Value / DP Transition
 * ----------------------------------  ----------------------------------------
 * LC 322. Coin Change                 dp[j] = min(dp[j], dp[j - coin] + 1)
 * LC 518. Coin Change II              dp[j] += dp[j - coin]
 * LC 139. Word Break                  dp[j] = dp[j] || dp[j - word.length()]
 * LC 279. Perfect Squares             dp[j] = min(dp[j], dp[j - sq] + 1)
 *
 *
 * KEY TRANSITION EXTENSIONS
 * -------------------------
 * 1. Reachability (Boolean):
 *    dp[j] = dp[j] || dp[j - cost]
 *
 * 2. Counting Number of Combinations:
 *    dp[j] = dp[j] + dp[j - cost]
 *
 * 3. Min/Max Optimization:
 *    dp[j] = min(dp[j], dp[j - cost] + 1)
 *    dp[j] = max(dp[j], dp[j - cost] + value)
 * ============================================================================
 */
