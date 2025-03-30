package misc;

/**
 * https://algo.monster/liteproblems/1567
 *
 * Given an array of integers nums, find the maximum length of a subarray where the product of all its elements is positive.
 *
 * A subarray of an array is a consecutive sequence of zero or more values taken out of that array.
 *
 * Return the maximum length of a subarray with positive product.
 *
 *
 *
 * Example 1:
 *
 * Input: nums = [1,-2,-3,4]
 * Output: 4
 * Explanation: The array nums already has a positive product of 24.
 * Example 2:
 *
 * Input: nums = [0,1,-2,-3,-4]
 * Output: 3
 * Explanation: The longest subarray with positive product is [1,-2,-3] which has a product of 6.
 * Notice that we cannot include 0 in the subarray since that'll make the product 0 which is not positive.
 * Example 3:
 *
 * Input: nums = [-1,-2,-3,0,1]
 * Output: 2
 * Explanation: The longest subarray with positive product is [-1,-2] or [-2,-3].
 */
public class MaxSubarrayLengthPostiveProduct {
    public int getMaxLen(int[] nums) {
        int positiveCount = nums[0] > 0 ? 1 : 0; // Initialize the length of positive product subarray
        int negativeCount = nums[0] < 0 ? 1 : 0; // Initialize the length of negative product subarray
        int maxLength = positiveCount; // Store the maximum length of subarray with positive product

        // Iterate over the array starting from the second element
        for (int i = 1; i < nums.length; ++i) {
            if (nums[i] > 0) {
                // If the current number is positive, increase the length of positive product subarray
                ++positiveCount;
                // If there was a negative product subarray, increase its length too, as a positive number
                // won't affect the sign of an already negative product.
                negativeCount = negativeCount > 0 ? negativeCount + 1 : 0;
            } else if (nums[i] < 0) {
                int previousNegativeCount = negativeCount;

                // A negative number will change the positive product to negative
                negativeCount = positiveCount + 1;

                // if there was a previous negative count, then this negative umber will make the product positive
                positiveCount = previousNegativeCount > 0 ? previousNegativeCount + 1 : 0;
            } else {
                // If the current number is zero, reset the lengths as any sequence will be discontinued by zero
                positiveCount = 0;
                negativeCount = 0;
            }
            // Update the maximum length if the current positive product subarray is longer
            maxLength = Math.max(maxLength, positiveCount);
        }
        return maxLength; // Return the maximum length found
    }

    public static void main(String[] args) {
        int[] nums = new int[] {
//                1,-2,-3,4       // outputs 4
//                0,1,-2,-3,-4    // outputs 3
                -1,-2,-3,0,1    // outputs 2
        };

        MaxSubarrayLengthPostiveProduct p = new MaxSubarrayLengthPostiveProduct();
        int result = p.getMaxLen(nums);
        System.out.println("result = " + result);
    }

}
