package dynamic;

import java.util.*;

public class Knapsack01 {

    /**
     * IMP: watch https://www.youtube.com/watch?v=-kedQt2UmnE
     * Solves 0/1 knapsack in bottom up dynamic programming
     */

    public int maxValue(int[] val, int[] wt, int maxW){
        int[][] T = new int[wt.length + 1][maxW + 1];
        for(int i=1; i <= wt.length; i++) {
            for(int w=0; w <= maxW; w++) {
                int valueWithoutIncludingCurrent = T[i - 1][w];
                if(wt[i - 1] <= w) {
                    int valueIncludingCurrent = val[i - 1] + T[i - 1][w - wt[i - 1]];
                    T[i][w] = Math.max(valueWithoutIncludingCurrent, valueIncludingCurrent);
                } else {
                    T[i][w] = valueWithoutIncludingCurrent;
                }
            }
        }

        return T[wt.length][maxW];
    }

    public List<Integer> maxSelection(int[] val, int[] wt, int maxW){
        // IMP: watch https://www.youtube.com/watch?v=-kedQt2UmnE
        int[][] T = new int[wt.length + 1][maxW + 1];
        int[][] U = new int[wt.length + 1][maxW + 1];

        for(int i = 1; i <= wt.length; i++) {
            for(int w=0; w <= maxW; w++) {
                int valueWithoutIncludingCurrent = T[i - 1][w];
                if(wt[i - 1] <= w) {
                    int valueIncludingCurrent = val[i - 1] + T[i - 1][w - wt[i - 1]];
                    if (valueIncludingCurrent > valueWithoutIncludingCurrent) {
                        T[i][w] = valueIncludingCurrent;
                        U[i][w] = 1; // the weight is in selection
                    } else {
                        T[i][w] = valueWithoutIncludingCurrent;
                    }
                } else {
                    T[i][w] = valueWithoutIncludingCurrent;
                }
            }
        }

        System.out.println("Max: " + T[wt.length][maxW]);

        List<Integer> selections = new ArrayList<>();
        int w = maxW;
        for (int i = wt.length; i > 0; i--) {
            if (U[i][w] == 1) {
                selections.add(val[i - 1]);
                w -= wt[i - 1];
            }
        }
        Collections.reverse(selections);
        return selections;
    }

    public static void main(String[] args){
        Knapsack01 k = new Knapsack01();
        int[] val = {22, 20, 15, 30, 24, 54, 21, 32, 18, 25};
        int[] wt = {4, 2, 3, 5, 5, 6, 9, 7, 8, 10};
        int maxW = 50;
        int r = k.maxValue(val, wt, maxW);
        System.out.println(r);

        List<Integer> selections = k.maxSelection(val, wt, maxW);
        System.out.println(selections);
    }
}