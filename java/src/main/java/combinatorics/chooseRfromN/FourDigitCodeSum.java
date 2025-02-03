package combinatorics.chooseRfromN;

public class FourDigitCodeSum {
    /**
     * <p>Bionomial Coefficients: <a href="https://cp-algorithms.com/combinatorics/binomial-coefficients.html">
     *     https://cp-algorithms.com/combinatorics/binomial-coefficients.html</a></p>
     * <p>Stars and Bars: <a href="https://cp-algorithms.com/combinatorics/stars_and_bars.html">https://cp-algorithms.com/combinatorics/stars_and_bars.html</a></p>
     * <p>Number of upper bound integer sums: <a href="https://cp-algorithms.com/combinatorics/inclusion-exclusion.html#number-of-upper-bound-integer-sums">
     *     https://cp-algorithms.com/combinatorics/inclusion-exclusion.html#number-of-upper-bound-integer-sums</a></p>
     * <p>Bionomial coefficients formula:</p>
     *         <img src="./bionomial-coefficients.png" width="100%" />
     * <p>The formula for d numbers that sum up to s with the restriction 0 <= x <= b:</p>
     *        <img src="./choose-r-from-n-with-bounds.png" width="100%" />
     *
     * @param sum
     * @return The number of valid combinations that sum up to s
     */
    static int combinatoricsSolution(int sum, int numDigits, int maxPerDigit) {
        int maxSum = numDigits * maxPerDigit;
        if (sum < 0 || sum > maxSum) return 0; // Max sum is 9+9+9+9 = 36

        int count = 0;
        for (int k = 0; k <= numDigits; k++) { // (S / 10)
            int sign = (k % 2 == 0) ? 1 : -1;
            count += sign * binomial(numDigits, k)
                    * binomial(sum + (numDigits - 1) - (maxPerDigit + 1) * k, numDigits - 1);
        }
        return count;
    }

    static int binomial(int n, int k) {
        if (k > n || k < 0) return 0;
        int res = 1;
        for (int i = 0; i < k; i++) {
            res = res * (n - i) / (i + 1);
        }
        return res;
    }

    static int basicSolution(int sum, int numDigits, int maxPerDigit) {
        int max = 0;
        for (int i = numDigits - 1; i >= 0; i--) {
            max += maxPerDigit * (Math.pow(10, i));
        }
        int count = 0;
        if (sum == 0) return 1;

        for (int i = 1; i <= max; i++) {
            int cur = i;
            int sumSoFar = 0;
            while (cur > 0) {
                sumSoFar += cur % 10;
                cur /= 10;
            }
            if (sumSoFar == sum) {
                count++;
            }
        }

        return count;
    }

    static int nestedLoopSolution(int S) {
        int count = 0;
        for (int a = 0; a <= 9; a++) {
            if (a > S) break;
            if (a == S) {
                count++;
                continue;
            }
            for (int b = 0; b <= 9; b++) {
                if (a + b > S) break;
                if (a + b == S) {
                    count++;
                    continue;
                }
                for (int c = 0; c <= 9; c++) {
                    if (a + b + c > S) break;
                    if (a +b + c == S) {
                        count++;
                        continue;
                    }
                    for (int d = 0; d <= 9; d++) {
                        if (a + b + c + d  > S) break;
                        if (a + b + c + d  == S) {
                            count++;
                            break;
                        }
                    }
                }

            }
        }

        return count;
    }

    static class Result {
        int count;
    }
    static void recursiveSolution(int curpos, int maxpos, int currentSum, int S, Result r) {
        if (curpos >= maxpos) {
            return;
        }

        for (int i = 0; i <= 9; i++) {
            int sum = currentSum + i;
            if (sum > S) {
                return;
            }
            if (sum == S) {
                r.count++;
                return;
            }
            recursiveSolution(curpos + 1, maxpos, sum, S, r);
        }
    }

    public static void main(String[] args) {

        int mismatch = 0;
        int numDigits = 4;
        int maxPerDigit = 9;
        for (int S = 0; S <= 36; S++) {
            int c0 = basicSolution(S, numDigits, maxPerDigit);
            int c1 = combinatoricsSolution(S, numDigits, maxPerDigit);

            if (c0 == c1) {
                System.out.printf("match for %d = %d%n",  S, c0);
            } else {
                System.out.printf("MISMATCH for %d, %d and %d%n",  S, c0, c1);
                mismatch++;
            }

        }

        System.out.printf("MISMATCH count %d%n", mismatch);
    }
}
