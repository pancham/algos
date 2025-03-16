package misc;

import java.util.ArrayList;
import java.util.List;

/**
 * https://algo.monster/liteproblems/2100
 * You and a gang of thieves are planning on robbing a bank. You are given a 0-indexed integer array security, where security[i] is the number of guards on duty on the ith day. The days are numbered starting from 0. You are also given an integer time.
 *
 * The ith day is a good day to rob the bank if:
 *
 * There are at least time days before and after the ith day,
 * The number of guards at the bank for the time days before i are non-increasing, and
 * The number of guards at the bank for the time days after i are non-decreasing.
 * More formally, this means day i is a good day to rob the bank if and only if security[i - time] >= security[i - time + 1] >= ... >= security[i] <= ... <= security[i + time - 1] <= security[i + time].
 *
 * Return a list of all days (0-indexed) that are good days to rob the bank. The order that the days are returned in does not matter.
 * <p>
 * Example 1:
 *
 * Input: security = [5,3,3,3,5,6,2], time = 2
 * Output: [2,3]
 * Explanation:
 * On day 2, we have security[0] >= security[1] >= security[2] <= security[3] <= security[4].
 * On day 3, we have security[1] >= security[2] >= security[3] <= security[4] <= security[5].
 * No other days satisfy this condition, so days 2 and 3 are the only good days to rob the bank.
 * </p>
 * <p>
 * Example 2:
 *
 * Input: security = [1,1,1,1,1], time = 0
 * Output: [0,1,2,3,4]
 * Explanation:
 * Since time equals 0, every day is a good day to rob the bank, so return every day.
 * </p>
 * <p>
 * Example 3:
 *
 * Input: security = [1,2,3,4,5,6], time = 2
 * Output: []
 * Explanation:
 * No day has 2 days before it that have a non-increasing number of guards.
 * Thus, no day is a good day to rob the bank, so return an empty list.
 * </p>
 */
public class GoodDaysToRobBank2100 {
    public List<Integer> goodDaysToRobBank(int[] security, int time) {
        // Get the length of the `security` array.
        int n = security.length;
        // If the length is not sufficient to have days before and after the time period, return an empty list.
        if (n <= time * 2) {
            return List.of();
        }

        // Arrays to keep track of the non-increasing trend to the left and non-decreasing trend to the right.
        int[] nonIncreasingLeft = new int[n];
        int[] nonDecreasingRight = new int[n];

        // Populate the nonIncreasingLeft array by checking if each day is non-increasing compared to the previous day.
        for (int i = 1; i < n; ++i) {
            if (security[i] <= security[i - 1]) {
                nonIncreasingLeft[i] = nonIncreasingLeft[i - 1] + 1;
            }
        }

        // Populate the nonDecreasingRight array by checking if each day is non-decreasing compared to the next day.
        for (int i = n - 2; i >= 0; --i) {
            if (security[i] <= security[i + 1]) {
                nonDecreasingRight[i] = nonDecreasingRight[i + 1] + 1;
            }
        }

        // To store the good days to rob the bank.
        List<Integer> goodDays = new ArrayList<>();

        // Check each day to see if it can be a good day to rob the bank.
        for (int i = time; i < n - time; ++i) {
            // A day is good if there are at least `time` days before and after it forming non-increasing and non-decreasing trends.
            if (time <= Math.min(nonIncreasingLeft[i], nonDecreasingRight[i])) {
                goodDays.add(i);
            }
        }
        // Return the list of good days.
        return goodDays;
    }

    public static void main(String[] args) {
        int[] security = new int[] {
                5, 3, 3, 3, 5, 6, 2     // return [2, 3] with time = 2
        };
        int time = 2;

        GoodDaysToRobBank2100 g = new GoodDaysToRobBank2100();
        List<Integer> goodDays = g.goodDaysToRobBank(security, time);
        System.out.println("validDays = " + goodDays);
    }

}
