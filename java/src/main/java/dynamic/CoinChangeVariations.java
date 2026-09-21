package dynamic;

import java.util.*;

/**
 * CoinChangeVariations
 *
 * Demonstrates 1D Dynamic Programming for MINIMUM ITEMS / COIN CHANGE
 * across the three boundedness variations:
 *
 * 1. 0/1 Knapsack      - Each coin/item can be used AT MOST ONCE.
 * 2. Bounded (0/k)     - Each coin/item can be used UP TO counts[i] TIMES.
 * 3. Unbounded         - Each coin/item can be used UNLIMITED TIMES.
 *
 * State definition:
 * dp[j] = minimum number of coins needed to make amount j.
 *
 * Base condition:
 * dp[0] = 0 (0 coins needed to make amount 0).
 * dp[1..amount] = amount + 1 (representing infinity / unreachable).
 */
public class CoinChangeVariations {

    private static final int INF = 1_000_000_000;

    // ------------------------------------------------------------------------
    // 1. 0/1 COIN CHANGE (USE EACH COIN AT MOST ONCE)
    // ------------------------------------------------------------------------
    //
    // Each coin can be used at most ONCE.
    // Loop direction: DESCENDING (amount down to coin)
    // Why: Prevents using the same coin multiple times in the same iteration.
    //
    // Corresponding LeetCode Problems:
    // - LC 416 Variation: Minimum elements subset sum
    //   -> What is the minimum number of elements from nums to reach target?
    //
    // - LC 474: Ones and Zeroes (Medium)
    //   -> 2D 0/1 Knapsack where each string has costs (zeros, ones) and
    //      can be used at most once. Maximizes strings count instead of min,
    //      but uses this exact descending 0/1 transition.
    //
    // - Amazon / Google OA Question: Minimum Unique Coins
    //   -> Given an array of unique coin denominations, what is the minimum
    //      number of coins needed to make amount, using each at most once?
    // ------------------------------------------------------------------------

    public static int minCoins01(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, amount + 1);
        dp[0] = 0;

        for (int coin : coins) {
            // DESCENDING: ensures each coin is used at most once
            for (int j = amount; j >= coin; j--) {
                dp[j] = Math.min(dp[j], dp[j - coin] + 1);
            }
        }

        return dp[amount] > amount ? -1 : dp[amount];
    }

    // ------------------------------------------------------------------------
    // 2. BOUNDED COIN CHANGE (USE EACH COIN UP TO counts[i] TIMES)
    // ------------------------------------------------------------------------
    //
    // Each coin i can be used at most counts[i] times.
    // Loop direction: DESCENDING for each copy.
    //
    // Corresponding LeetCode Problems:
    // - LC 1774: Closest Dessert Cost (Medium)
    //   -> Each topping can be chosen at most 2 times (k = 2).
    //   -> Uses bounded selection with each choice evaluated at most k times.
    //
    // - LC 2585: Number of Ways to Earn Points (Hard)
    //   -> Each question type has count_i problems worth marks_i.
    //   -> If asked for MINIMUM questions to reach target points, this is
    //      the exact algorithm.
    //
    // - Classic Bounded Coin Change / POJ 1742 Variation:
    //   -> Cash register / ATM change problem: Given coin denominations and
    //      available quantities in stock, find minimum coins to dispense.
    // ------------------------------------------------------------------------

    public static int minCoinsBounded(int[] coins, int[] counts, int amount) {
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, amount + 1);
        dp[0] = 0;

        for (int i = 0; i < coins.length; i++) {
            int coin = coins[i];
            int k = counts[i];

            // Repeat k times, each copy treated as a 0/1 coin
            for (int x = 0; x < k; x++) {
                // DESCENDING: ensures this copy is used at most once
                for (int j = amount; j >= coin; j--) {
                    dp[j] = Math.min(dp[j], dp[j - coin] + 1);
                }
            }
        }

        return dp[amount] > amount ? -1 : dp[amount];
    }

    // ------------------------------------------------------------------------
    // 2B. BOUNDED COIN CHANGE WITH BINARY SPLITTING (OPTIMIZED)
    // ------------------------------------------------------------------------
    //
    // When counts[i] is large, looping x from 0 to k takes O(k * amount).
    // Binary splitting decomposes k into powers of 2 (1, 2, 4, ..., R),
    // reducing the copies from k down to O(log k).
    //
    // For a chunk of size 'p':
    //   weight = p * coin
    //   cost   = p * 1 (takes p coins)
    // ------------------------------------------------------------------------

    public static int minCoinsBoundedBinarySplit(int[] coins, int[] counts, int amount) {
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, amount + 1);
        dp[0] = 0;

        for (int i = 0; i < coins.length; i++) {
            int coin = coins[i];
            int remaining = counts[i];

            for (int p = 1; remaining > 0; p <<= 1) {
                int take = Math.min(p, remaining);
                int chunkWeight = take * coin;
                int chunkCount = take; // this chunk represents 'take' coins

                for (int j = amount; j >= chunkWeight; j--) {
                    dp[j] = Math.min(dp[j], dp[j - chunkWeight] + chunkCount);
                }

                remaining -= take;
            }
        }

        return dp[amount] > amount ? -1 : dp[amount];
    }

    // ------------------------------------------------------------------------
    // 3. UNBOUNDED COIN CHANGE (USE EACH COIN UNLIMITED TIMES)
    // ------------------------------------------------------------------------
    //
    // Each coin can be used UNLIMITED times.
    // Loop direction: ASCENDING (coin up to amount)
    // Why: dp[j - coin] already reflects the best way to form (j - coin) using
    //      any coins including the current coin, allowing it to be reused.
    //
    // Corresponding LeetCode Problems:
    // - LC 322: Coin Change (Medium)
    //   -> EXACT 1-to-1 match.
    //   -> Given coins and amount, return fewest coins needed.
    //
    // - LC 279: Perfect Squares (Medium)
    //   -> Least number of perfect squares (1, 4, 9, 16...) that sum to n.
    //   -> Squares can be reused unlimited times.
    //
    // - LC 983: Minimum Cost For Tickets (Medium)
    //   -> Choose pass types (1-day, 7-day, 30-day) with unlimited purchases.
    // ------------------------------------------------------------------------

    public static int minCoinsUnbounded(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, amount + 1);
        dp[0] = 0;

        for (int coin : coins) {
            // ASCENDING: allows the same coin to be reused repeatedly
            for (int j = coin; j <= amount; j++) {
                dp[j] = Math.min(dp[j], dp[j - coin] + 1);
            }
        }

        return dp[amount] > amount ? -1 : dp[amount];
    }

    // ------------------------------------------------------------------------
    // DEMONSTRATION & COMPARISON
    // ------------------------------------------------------------------------

    public static void main(String[] args) {
        // Example: coins = {1, 2, 5}, target amount = 11
        //
        // 1. Unbounded:
        //    Can pick 5 + 5 + 1 = 11 (3 coins).
        //
        // 2. Bounded with limited counts: {1, 1, 1}
        //    Max possible sum is 1 + 2 + 5 = 8 < 11 -> impossible (-1).
        //
        // 3. Bounded with counts {1, 5, 1}:
        //    Can pick 5 (1x) + 2 (3x) = 11 (4 coins).
        //
        // 4. 0/1 with coins = {1, 2, 5, 6, 8}, amount = 11:
        //    Pick 5 + 6 = 11 (2 coins).

        int[] coins = { 1, 2, 5 };
        int amount = 11;

        int unboundedResult = minCoinsUnbounded(coins, amount);
        System.out.println("Coins = " + Arrays.toString(coins) + ", Target = " + amount);
        System.out.println("Unbounded (LC 322) Min Coins            : " + unboundedResult);

        int[] countsLimited = { 1, 1, 1 };
        int boundedLimitedResult = minCoinsBounded(coins, countsLimited, amount);
        System.out.println("Bounded (counts [1, 1, 1]) Min Coins    : " + boundedLimitedResult);

        int[] countsGenerous = { 1, 5, 1 };
        int boundedGenerousResult = minCoinsBounded(coins, countsGenerous, amount);
        int boundedBinaryResult = minCoinsBoundedBinarySplit(coins, countsGenerous, amount);
        System.out.println("Bounded (counts [1, 5, 1]) Min Coins    : " + boundedGenerousResult);
        System.out.println("Bounded (Binary Split) Min Coins        : " + boundedBinaryResult);

        int[] coins01 = { 1, 2, 5, 6, 8 };
        int zeroOneResult = minCoins01(coins01, amount);
        System.out.println("0/1 with {1, 2, 5, 6, 8} Min Coins      : " + zeroOneResult);
    }
}

/*
 * ============================================================================
 * LEETCODE PROBLEM MAPPING & PATTERN CHEATSHEET
 * ============================================================================
 *
 * VARIATION 1: 0/1 MIN ITEMS (DESCENDING LOOP)
 * ----------------------------------------------------------------------------
 * Loop: for (int j = amount; j >= coin; j--)
 *
 * Problem                             Description / Mapping
 * ----------------------------------  ----------------------------------------
 * LC 416 (Min Subset Variation)       Minimum elements to sum to totalSum / 2.
 * LC 474. Ones and Zeroes             2D 0/1 Knapsack (max items subject to bounds).
 * OA Problem: Unique Coin Change      Minimum coins where each denomination has 1 copy.
 *
 *
 * VARIATION 2: BOUNDED / 0-k MIN ITEMS (COPIES + DESCENDING LOOP)
 * ----------------------------------------------------------------------------
 * Loop: for (int x = 0; x < k; x++)
 *           for (int j = amount; j >= coin; j--)
 *
 * Problem                             Description / Mapping
 * ----------------------------------  ----------------------------------------
 * LC 1774. Closest Dessert Cost       Each topping option usable at most 2 times.
 * LC 2585. (Min Questions Variant)    Minimum questions to earn target points.
 * ATM / Cash Dispenser Problem        Dispense target amount with limited bill supply.
 *
 *
 * VARIATION 3: UNBOUNDED MIN ITEMS (ASCENDING LOOP)
 * ----------------------------------------------------------------------------
 * Loop: for (int j = coin; j <= amount; j++)
 *
 * Problem                             Description / Mapping
 * ----------------------------------  ----------------------------------------
 * LC 322. Coin Change                 EXACT 1-to-1 match. Fewest coins for amount.
 * LC 279. Perfect Squares             Fewest square numbers (1, 4, 9, 16...) for n.
 * LC 983. Minimum Cost For Tickets    Fewest / min-cost passes to cover travel days.
 *
 *
 * SUMMARY: WHY LOOP DIRECTION MATTERS
 * -----------------------------------
 * - Ascending (j = coin; j <= amount; j++)
 *   Allows reusing the current coin at j because dp[j - coin] was already
 *   updated in the SAME iteration. (Unbounded)
 *
 * - Descending (j = amount; j >= coin; j--)
 *   Prevents reusing the current coin at j because dp[j - coin] still comes
 *   from the PREVIOUS iteration. (0/1 and Bounded copies)
 * ============================================================================
 */
