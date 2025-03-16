package misc;

/**
 * Given a binary string s, return the minimum number of character swaps to make it alternating, or -1 if it is impossible.
 *
 * The string is called alternating if no two adjacent characters are equal. For example, the strings "010" and "1010" are
 * alternating, while the string "0100" is not.
 *
 * Any two characters may be swapped, even if they are not adjacent.
 *
 * Example 1:
 *
 * Input: s = "111000"
 * Output: 1
 * Explanation: Swap positions 1 and 4: "111000" -> "101010"
 * The string is now alternating.
 * Example 2:
 *
 * Input: s = "010"
 * Output: 0
 * Explanation: The string is already alternating, no swaps are needed.
 * Example 3:
 *
 * Input: s = "1110"
 * Output: -1
 *
 * Constraints:
 *
 * 1 <= s.length <= 1000
 * s[i] is either '0' or '1'.
 */
public class MinSwapsForBinaryStrAlternating1864 {
    public int minSwaps(String s) {
//    APPROACH:
//    There are 2 possible alternating combinations: 0101... or 1010..
//    Count 0s and 1s → If their absolute difference is greater than 1, return -1 (since alternating is impossible).
//    Define a helper function → countSwaps(start_char) compares string s against two patterns: "0101.."
//    (alternating starting with '0') and "1010..."(alternating starting with '1'), counting mismatches to
//    determine the number of swaps needed.
//          Compute swaps for both starting patterns → Call count_swaps("0") and count_swaps("1").
//          Even-length case → Return the minimum swaps needed for a valid alternating sequence.
//          Odd-length case → Choose swaps based on whether 0s or 1s are in excess.

        int count0 = 0;
        for (char c: s.toCharArray()) {
            if (c == '0') {
                count0++;
            }
        }
        int count1 = s.length() - count0;
        if (Math.abs(count1 - count0) > 1) {
            return -1;
        }
        int numSwapsWithStartChar0 = countSwaps(s, '0');
        int numSwapsWithStartChar1 = countSwaps(s, '1');

        if (s.length() %2 == 0) {
            return Math.min(numSwapsWithStartChar0, numSwapsWithStartChar1);
        }

        return (count0 > count1) ? numSwapsWithStartChar0: numSwapsWithStartChar1;
    }

    int countSwaps(String s, char c) {
        int count = 0;
        char expectedNextChar = c;
        for (char ch: s.toCharArray()) {
            if (ch != expectedNextChar) {
                count++;
            }

            expectedNextChar = (expectedNextChar == '0' ? '1' : '0');
        }
        // each swap will change 2 positions, hence devide by 2
        return count / 2;
    }

    public static void main(String[] args) {
//        String s = "111000";    // returns 1
        String s = "1110";        // returns -1

        MinSwapsForBinaryStrAlternating1864 sw = new MinSwapsForBinaryStrAlternating1864();
        int result = sw.minSwaps(s);
        System.out.println("result = " + result);
    }
}
