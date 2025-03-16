package misc;

/**
 * You are given a 0-indexed integer array nums, where nums[i] is a digit between 0 and 9 (inclusive).
 *
 * The triangular sum of nums is the value of the only element present in nums after the following process terminates:
 *
 * Let nums comprise of n elements. If n == 1, end the process. Otherwise, create a new 0-indexed integer array newNums of length n - 1.
 * For each index i, where 0 <= i < n - 1, assign the value of newNums[i] as (nums[i] + nums[i+1]) % 10, where % denotes modulo operator.
 * Replace the array nums with newNums.
 * Repeat the entire process starting from step 1.
 * Return the triangular sum of nums.
 *
 * <pre>
 * <img src="./triangularsum2221-1.png" />
 * </pre>
 * <p>
 Input: nums = [1,2,3,4,5]
 Output: 8
 </p>
 *
 */
public class TriangularSum2221 {
    public int triangularSum(int[] nums) {
        int[] newNums = nums.clone();
        int size = newNums.length;
        while (size > 1) {
            for (int i = 0; i < size - 1; i++) {
                newNums[i] = (newNums[i] + newNums[i + 1]) % 10;
            }

            size = size - 1;
        }

        return newNums[0];
    }

    public static void main(String[] args) {
        int[] nums = new int[] {
                1,2,3,4,5   // returns 8
//                5           // returns 5
        };

        TriangularSum2221 ts = new TriangularSum2221();
        int result = ts.triangularSum(nums);
        System.out.println("result = " + result);
    }
}
