package dynamic.packageselection;

/**
 * https://www.fastprep.io/problems/amazon-get-num-perfect-packaging
 *
 * Amazon aims to optimally package parcels to improve efficiency. There are n parcels (0-based indexing), and the
 * price of the ith parcel is denoted by prices[i]. Amazon uses five different stamps (indexed from 1 to 5)
 * to label the parcels.
 *
 * Packaging is done sequentially from left to right, and each parcel must be labeled with one of the five stamps.
 * A packaging is considered perfect if it adheres to the following rules (for i > 0):
 *
 * If prices[i] > prices[i-1], then stamp[i] > stamp[i-1] (where stamp[i] is the index of the stamp used to label the ith parcel).
 * If prices[i] < prices[i-1], then stamp[i] < stamp[i-1].
 * If prices[i] = prices[i-1], then stamp[i] ≠ stamp[i-1].
 *
 * Given an array of n integers, find the number of perfect packaging possibilities using the five different stamps.
 * Since the number of perfect packaging can be large, return the result modulo (10^9 + 7).
 *
 * Note: If no valid packaging can be done based on the given rules, return 0.
 *
 * Function Description
 *
 * Complete the function getNumPerfectPackaging in the editor.
 *
 * getNumPerfectPackaging has the following parameter:
 *
 * int prices[n]: the price of the parcels
 * Returns
 *
 * int: the number of perfect packaging that can be done modulo (10^9 + 7)
 *
 * Constraints:
 * 1 ≤ n ≤ 105
 * 1 ≤ prices[i] ≤ 105
 */
import java.util.Arrays;

public class PackageSelection {

    private static final int MOD = 1_000_000_007;
    private static int[][] memo;  // Memoization table
    private static int[] prices;  // Input prices array

    public static void main(String[] args) {
        prices = new int[]{3, 1, 1};        // {1, 2, 3}
        int n = prices.length;
        memo = new int[n][6]; // Memoization table
        for (int[] row : memo) Arrays.fill(row, -1); // Initialize with -1

        // Start recursion from first parcel (index 0), with a "virtual" stamp 0
        System.out.println("Perfect Packaging Possibilities: " + countWays(0, 0));
        System.out.println("Perfect Packaging Possibilities with DP: " + countWaysDP(prices));
        System.out.println("done");
    }

    private static int countWays(int i, int prevStamp) {
        // When we reach i == prices.length, it means we have successfully assigned valid stamps to all parcels
        // while following the constraints. In this case, we have found one valid way to label the parcels,
        // so we return 1
        if (i == prices.length) {
            return 1;
        }
        if (memo[i][prevStamp] != -1) {
            return memo[i][prevStamp]; // Return cached result
        }

        int ways = 0;
        for (int currStamp = 1; currStamp <= 5; currStamp++) {
            if (i == 0 || isValid(prices[i - 1], prices[i], prevStamp, currStamp)) {
                ways = (ways + countWays(i + 1, currStamp)) % MOD;
            }
        }
        return memo[i][prevStamp] = ways; // Store result
    }

    private static boolean isValid(int prevPrice, int currPrice, int prevStamp, int currStamp) {
        if (currPrice > prevPrice) return currStamp > prevStamp;
        if (currPrice < prevPrice) return currStamp < prevStamp;
        return currStamp != prevStamp; // currPrice == prevPrice
    }

    public static int countWaysDP(int[] prices) {
        int n = prices.length;
        if (n == 0) {
            return 0;
        }
        if (n == 1) {
            return 5;
        }

        int[] prev = new int[6]; // Using indices 1-5 for stamps 1-5
        for (int j = 1; j <= 5; j++) {
            prev[j] = 1;
        }

        for (int i = 1; i < n; i++) {
            int currentPrice = prices[i];
            int prevPrice = prices[i - 1];
            int[] curr = new int[6];

            for (int j = 1; j <= 5; j++) {
                int sum = 0;
                if (currentPrice > prevPrice) {
                    // Current stamp must be greater than previous
                    for (int k = 1; k < j; k++) {
                        sum = (sum + prev[k]) % MOD;
                    }
                } else if (currentPrice < prevPrice) {
                    // Current stamp must be less than previous
                    for (int k = j + 1; k <= 5; k++) {
                        sum = (sum + prev[k]) % MOD;
                    }
                } else {
                    // Current stamp must be different from previous
                    for (int k = 1; k <= 5; k++) {
                        if (k != j) {
                            sum = (sum + prev[k]) % MOD;
                        }
                    }
                }
                curr[j] = sum;
            }
            prev = curr;
        }

        int total = 0;
        for (int j = 1; j <= 5; j++) {
            total = (total + prev[j]) % MOD;
        }
        return total;
    }
}
