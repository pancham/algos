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
        System.out.println("with range summation " + getSumWithRangeSummation(13));
        System.out.println("without range summation " + getSumInefficiently(13));
    }

    public static long getSumWithRangeSummation(long n) {
        /**
         * Note that floor(n / k) = x, will be true for a range of k values. The range of k values is:
         *
         * (x * k) + (k - 1)
         *
         */
        long count = 0;

        int k = 2;
        do {
            long x = n / k;
            count += x;

            // advance i for range of values for which floor(n / k) will be same
            k += (x * k) + (k - 1);

        } while(k < n);

        return count;
    }

    public static long getSumInefficiently(long n) {
        /**
         * 0, 1 and n will always be included, reasons:
         * For any k > n, x will always be 0.
         * for k == n, x will be 1
         * for k == 1, x will be n
         *
         * The remaining possible values for k, which can result in an interger in the range 0<=x<=n, are 2 to n-1.
         * Further, for any k > n/2 - the result will always be 1.
         */

        if (n == 1) return 1;
        long lastX = 0;
        long count = 1 + n;
        for(long k = n/2 ; k >= 2; k--) {
            long x = n/k;
            if (lastX != x) {count += x; lastX = x;}
        }
        return count;
    }
}
