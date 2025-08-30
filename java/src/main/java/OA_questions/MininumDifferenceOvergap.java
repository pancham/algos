import java.util.*;

public class MininumDifferenceOvergap {


    /*
     * given an array nums
     * find the minimum value of |nums[a] - nums[b| where |a - b| >= k
     * Contraints:
     *      w <= heights.length <= 10^5
     *      1 <= heights[i] <= 10^9
     */
    public int minimumDifference(int[] heights, int gap) {
        TreeSet<Integer> set = new TreeSet<>();
        set.add(heights[0]);

        int minDiff = Integer.MAX_VALUE;
        for (int i = gap; i < heights.length; ++i) {
            int height = heights[i];
            Integer lowerHeight = set.floor(height);
            Integer upperHeight = set.ceiling(height);

            if (lowerHeight != null) {
                minDiff = Math.min(minDiff, height - lowerHeight);
            }
            if (upperHeight != null) {
                minDiff = Math.min(minDiff, upperHeight - height);
            }
            if (minDiff == 0) break;

            set.add(heights[i - gap + 1]);
        }
        return minDiff;
    }
}


class Main_MininumDifferenceOvergap {
    public static void main(String[] args) {
        MininumDifferenceOvergap m = new MininumDifferenceOvergap();
        
        int[] heights = {1, 5, 4, 10, 9};
        int gap = 3;
        System.out.println(m.minimumDifference(heights, gap));
    }
}
