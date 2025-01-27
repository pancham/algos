package dynamic;

import java.util.*;

public class BalancedPartition {
    /**
     * The questions is typically asked to partition an array of numbers in 2 parts such that the difference of sums of
     * the two partitions is minimum.
     * The questions can also take different flavor such as assign processes that take x units of processing power to
     * two processors such as the different between allocated processing powers is minimum.
     * s
     * This is a variation of knapsack 0/1, where the value and weight arrays are same and max(target) weight = (sum of all items)/2
     */

    public int minimizeDifference(int[] wt){
        int maxW = 0;
        for (int j : wt) {
            maxW += j;
        }
        int target = maxW / 2;

        int[][] T = new int[wt.length + 1][target + 1];
        for(int i=1; i <= wt.length; i++) {
            for(int w=0; w <= target; w++) {
                int valueWithoutIncludingCurrent = T[i - 1][w];
                if(wt[i - 1] <= w) {
                    int valueIncludingCurrent = wt[i - 1] + T[i - 1][w - wt[i - 1]];
                    T[i][w] = Math.max(valueWithoutIncludingCurrent, valueIncludingCurrent);
                } else {
                    T[i][w] = valueWithoutIncludingCurrent;
                }
            }
        }

        return T[wt.length][target];
    }

    public List<List<Integer>> getPartitions(int[] wt){
        int maxW = 0;
        for (int j : wt) {
            maxW += j;
        }
        int target = maxW / 2;

        int[][] T = new int[wt.length + 1][target + 1];
        int[][] U = new int[wt.length + 1][target + 1];

        for(int i = 1; i <= wt.length; i++) {
            for(int w=0; w <= target; w++) {
                int valueWithoutIncludingCurrent = T[i - 1][w];
                if(wt[i - 1] <= w) {
                    int valueIncludingCurrent = wt[i - 1] + T[i - 1][w - wt[i - 1]];
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

        System.out.println("Max: " + T[wt.length][target]);

        Set<Integer> selections = new TreeSet<>();
        int w = target;
        for (int i = wt.length; i > 0; i--) {
            if (U[i][w] == 1) {
                selections.add(wt[i - 1]);
                w -= wt[i - 1];
            }
        }

        List<List<Integer>> partitions = new ArrayList<>(2);
        List<Integer> p1 = new ArrayList<>(selections);
        partitions.add(p1);

        List<Integer> p2 = new ArrayList<>();
        for (int curW: wt) {
            if (!selections.contains(curW)) {
                p2.add(curW);
            }
        }
        Collections.sort(p2);
        partitions.add(p2);

        return partitions;
    }

    public static void main(String[] args){
        BalancedPartition k = new BalancedPartition();
        int[] wt = {4, 2, 3, 5, 5, 6, 9, 7, 8, 10};

        int r = k.minimizeDifference(wt);
        System.out.println(r);

        List<List<Integer>> partitions = k.getPartitions(wt);
        System.out.println(partitions);
    }
}
