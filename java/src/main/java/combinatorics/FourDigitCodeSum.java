package combinatorics;


public class FourDigitCodeSum {
    static int combinatoricsSolution(int S) {
        // https://cp-algorithms.com/combinatorics/inclusion-exclusion.html#number-of-upper-bound-integer-sums
        /**
         * The number of combinations of \(r\) objects chosen from a set of \(n\) objects is \({n \choose r}=\frac{n!}{r!(n-r)!}\)
         */
        if (S < 0 || S > 36) return 0; // Max sum is 9+9+9+9 = 36

        int count = 0;
        for (int k = 0; k <= (S / 10); k++) {
            int sign = (k % 2 == 0) ? 1 : -1;
            count += sign * binomial(4, k) * binomial(S - 10 * k + 3, 3);
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

    static int basicSolution(int S) {
        int count = 0;
        if (S == 0) return 1;

        for (int i = 1; i <= 9999; i++) {
            int cur = i;
            int sum = 0;
            while (cur > 0) {
                sum += cur % 10;
                cur /= 10;
            }
            if (sum == S) {
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
                            continue;
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
        for (int S = 0; S <= 36; S++) {
            int c0 = basicSolution(S);
            int c1 = combinatoricsSolution(S);

            if (c0 == c1) {
                System.out.println(String.format("match for %d = %d",  S, c0));
            } else {
                System.out.println(String.format("MISMATCH for %d, %d and %d",  S, c0, c1));
                mismatch++;
            }

        }

        System.out.println(String.format("MISMATCH count %d", mismatch));
    }
}
