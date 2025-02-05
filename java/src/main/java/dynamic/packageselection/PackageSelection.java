package dynamic.packageselection;

/**
 * https://www.fastprep.io/problems/amazon-get-num-perfect-packaging
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
}
