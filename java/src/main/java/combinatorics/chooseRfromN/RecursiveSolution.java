package combinatorics.chooseRfromN;

public class RecursiveSolution {
    int maxpos;
    int S;

    public int solve(int maxpos, int S) {
        this.maxpos = maxpos;
        this.S = S;

        return solution(0, 0);
    }

    private int solution(int curpos, int currentSum) {
        if (curpos >= maxpos) {
            return 0;
        }

        int count = 0;
        for (int i = 0; i <= 9; i++) {
            int sum = currentSum + i;
            if (sum > S) {
                return 0;
            }

            if (sum == S) {
                return count += 1;
            } else {
                count = count + solution(curpos + 1, sum);
            }
        }

        return count;
    }
}
