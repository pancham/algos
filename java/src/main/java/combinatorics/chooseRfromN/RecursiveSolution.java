package combinatorics.chooseRfromN;

import java.util.Arrays;

public class RecursiveSolution {
    int maxpos;
    int S;

    private int[][] memo;

    public int solve(int maxpos, int S) {
        this.maxpos = maxpos;
        this.S = S;

        memo = new int[maxpos][S + 1]; // Memoization table
        for (int[] row : memo) Arrays.fill(row, -1);

        int count = solution(0, 0);
        return count;
    }

    private int solution(int curpos, int currentSum) {
        if (curpos >= maxpos) {
            return 0;
        }

        if (memo[curpos][currentSum] != -1) {
            return memo[curpos][currentSum];
        }

        int count = 0;
        for (int i = 0; i <= 9; i++) {
            int sum = currentSum + i;
            if (sum > S) {
                return memo[curpos][currentSum] = 0;
            }

            if (sum == S) {
                return memo[curpos][currentSum] = count + 1;
            } else {
                count = count + solution(curpos + 1, sum);
            }
        }

        return memo[curpos][currentSum] = count;
    }
}
