package misc;

public class RangeSummation {

    /**
     * https://www.fastprep.io/problems/amazon-get-data-dependence-sum
     *
     * Data analysts at Amazon are analyzing time-series data. It was concluded that the data of the nth item was
     * dependent on the data of some xth day if there is a positive integer k such that floor(n / k) = x
     * where floor() represents the largest integer less than or equal to z.
     *
     * Given n, find the sum of all the days' numbers on which the data of the xth (0 ≤ x < n) will be dependent.
     * @param arg
     */
    public static void main(String[] arg) {
        int n = (int) Math.pow(10, 10);
        long withRange = findDependentSum(n);
        long withoutRange = getSumInefficiently(n);
        System.out.println("with range summation " + withRange);
        System.out.println("without range summation " + withoutRange);

        if (withRange == withoutRange) {
            System.out.println("Values match");
        } else {
            System.out.println("Values do not match, difference: " + (withRange - withoutRange));
        }
    }

    public static long findDependentSum(int n) {
        long sum = 0;
        int sqrtN = (int) Math.sqrt(n);

        // Case 1: This is for larger values of x (or smaller values of k)
        // consider the smallest value of k that gives x = sqrt(n)
        //  k = n / sqrt(n) = sqrt(n)
        // This means that for 1 <= k <= sqrt(n), all the values of x will be greater sqrt(n). This k range of values
        // will cover all x values >= sqrt(n)
        // For this smaller range of k values, we iterate directly over the range
        int last = -1;
        for (int k = 1; k <= sqrtN; k++) {
            int current = (n / k);
            if (current != last) {
                sum += current;
                last = current;
            }
        }

        // Case 2: This is for smaller values of x (or larger values of k), using range summation
        // All x values >= sqrt(n) are covered in the Case 1, by iterating over smaller values of k.
        // What remains now is x < sqrt(n), and we accomplish it in the following loop by iterating
        // over x values upto sqrt(n)
        // Note that this loop also proceeds upto sqrt(n), as the loop in Case 1. However, case 1 is iterating
        // for k values and this loop is iterating for x values.
        for (int x = 1; x <= sqrtN; x++) {
            int startK = n / (x + 1) + 1;  // First k where floor(n/k) == x
            int endK = n / x;              // Last k where floor(n/k) == x

            if (startK <= endK && endK != sqrtN) { // the check for end != sqrtN, is required to avoid duplicate, because it is included in Case 1
                sum += x;
            }
        }

        return sum;
    }

    public static long getSumInefficiently(long n) {
        /**
         * 0, 1 and n will always be included, reasons:
         * For any k > n, x will always be 0.
         * for k == n, x will be 1
         * for k == 1, x will be n
         *
         * The remaining possible values for k, which can result in an integer in the range 0<=x<=n, are 2 to n-1.
         * Further, for any k > n/2 - the result will always be 1.
         */

        if (n == 1) return 1;
        long lastX = 0;
        long count = 1 + n;
        for(long k = n/2; k >= 2; k--) {
            long x = n/k;
            if (lastX != x) {
                count += x;
                lastX = x;
            }
        }
        return count;
    }
}
