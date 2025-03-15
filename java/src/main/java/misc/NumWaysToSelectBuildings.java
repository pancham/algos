package misc;

/**
 * https://algo.monster/liteproblems/2222
 * You are given a 0-indexed binary string s which represents the types of buildings along a street where:
 *
 * s[i] = '0' denotes that the ith building is an office and
 * s[i] = '1' denotes that the ith building is a restaurant.
 * As a city official, you would like to select 3 buildings for random inspection. However, to ensure variety, no two consecutive buildings out of the selected buildings can be of the same type.
 *
 * For example, given s = "001101", we cannot select the 1st, 3rd, and 5th buildings as that would form "011" which is not allowed due to having two consecutive buildings of the same type.
 * Return the number of valid ways to select 3 buildings.
 *
 *
 *
 * Example 1:
 *
 * Input: s = "001101"
 * Output: 6
 * Explanation:
 * The following sets of indices selected are valid:
 * - [0,2,4] from "001101" forms "010"
 * - [0,3,4] from "001101" forms "010"
 * - [1,2,4] from "001101" forms "010"
 * - [1,3,4] from "001101" forms "010"
 * - [2,4,5] from "001101" forms "101"
 * - [3,4,5] from "001101" forms "101"
 * No other selection is valid. Thus, there are 6 total ways.
 * Example 2:
 *
 * Input: s = "11100"
 * Output: 0
 * Explanation: It can be shown that there are no valid selections.
 *
 *
 * Constraints:
 *
 * 3 <= s.length <= 105
 * s[i] is either '0' or '1'.
 */
public class NumWaysToSelectBuildings {

    public static void main(String[] args) {
    }

    // Method to count the number of ways to form a "010" or "101" pattern in the given string.
    public long numberOfWays(String s) {
        // Length of the input string.
        long length = s.length();
        // Counter for zeros in the input string.
        long countZeros = 0;

        // Count the number of zeros in the input string.
        for (char c : s.toCharArray()) {
            if (c == '0') {
                countZeros++;
            }
        }

        // Counter for ones, which is the total length minus the number of zeros.
        long countOnes = length - countZeros;
        // Variable to store the total number of patterns found.
        long totalWays = 0;
        // Temp counters for zeros and ones as we iterate through the string.
        long tempCountZeros = 0, tempCountOnes = 0;

        // Iterate through the characters of the string to count the patterns.
        for (char c : s.toCharArray()) {
            if (c == '0') {
                // When we find a '0', we increase the total count of valid patterns found
                // by the number of '1's found before multiplied by the number of '1's that
                // can potentially come after this '0' to complete the pattern.
                totalWays += tempCountOnes * (countOnes - tempCountOnes);
                // Increase the temporary count of zeros since we encountered a '0'.
                tempCountZeros++;
            } else {
                // Similarly, when we find a '1', we increase the count of valid patterns by
                // the temporary count of '0's multiplied by the number of '0's that can come
                // after to complete the pattern.
                totalWays += tempCountZeros * (countZeros - tempCountZeros);
                // Increase the temporary count of ones since we encountered a '1'.
                tempCountOnes++;
            }
        }

        // Return the total number of patterns found.
        return totalWays;
    }
}
