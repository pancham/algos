package lc;

// https://leetcode.com/problems/sort-colors/
// This is counting sort problem: https://www.geeksforgeeks.org/dsa/counting-sort/

public class SortColors75 {

    public void sortColors(int[] nums) {
        int zeroCount = 0, oneCount = 0, twoCount = 0;

        for (int num : nums) {
            if (num == 0) zeroCount++;
            else if (num == 1) oneCount++;
            else twoCount++;
        }

        for (int i = 0; i < nums.length; i++) {
            if (i < zeroCount) {
                nums[i] = 0;
            } else if (i < zeroCount + oneCount) {
                nums[i] = 1;
            } else {
                nums[i] = 2;
            }
        }
        
    }

}
