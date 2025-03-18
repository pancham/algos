package misc;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.function.BiFunction;

/**
 * https://www.youtube.com/watch?v=yAoN-EQRszk  (The solution is from this video)
 *
 * https://www.youtube.com/watch?v=aX1F2-DrBkQ
 *
 * You are given an integer array nums. The range of a subarray of nums is the difference between the largest and smallest element in the subarray.
 *
 * Return the sum of all subarray ranges of nums.
 *
 * A subarray is a contiguous non-empty sequence of elements within an array.
 *
 * Example 1:
 *
 * Input: nums = [1,2,3]
 * Output: 4
 * Explanation: The 6 subarrays of nums are the following:
 * [1], range = largest - smallest = 1 - 1 = 0
 * [2], range = 2 - 2 = 0
 * [3], range = 3 - 3 = 0
 * [1,2], range = 2 - 1 = 1
 * [2,3], range = 3 - 2 = 1
 * [1,2,3], range = 3 - 1 = 2
 * So the sum of all ranges is 0 + 0 + 0 + 1 + 1 + 2 = 4.
 * Example 2:
 *
 * Input: nums = [1,3,3]
 * Output: 4
 * Explanation: The 6 subarrays of nums are the following:
 * [1], range = largest - smallest = 1 - 1 = 0
 * [3], range = 3 - 3 = 0
 * [3], range = 3 - 3 = 0
 * [1,3], range = 3 - 1 = 2
 * [3,3], range = 3 - 3 = 0
 * [1,3,3], range = 3 - 1 = 2
 * So the sum of all ranges is 0 + 0 + 0 + 2 + 0 + 2 = 4.
 * Example 3:
 *
 * Input: nums = [4,-2,-3,4,1]
 * Output: 59
 * Explanation: The sum of all subarray ranges of nums is 59.
 *
 *
 * Constraints:
 *
 * 1 <= nums.length <= 1000
 * -10^9 <= nums[i] <= 10^9
 */
public class SumOfSubarrayRanges2104 {

    public long subArrayRanges(int[] nums) {

        // dec_stack -> finds previous and next greater elements, used to determine the number of sub-arrays in which an item is greatest
        Deque<Integer> dec_stack = new ArrayDeque<>();
        // inc_stack -> finds previous and next lesser elements, used to determine the number of sub-arrays in which an item is lowest
        Deque<Integer> inc_stack = new ArrayDeque<>();

        long sum = 0;

        // Note that it is "<=" so that the logic in pushToStack can be run one more time to process
        // any remaining items on the stack.
        for (int i = 0; i <= nums.length; i++) {
            long max_subarray_sum_for_prev_items = pushToStack(dec_stack, nums, i, (a, b) -> a < b);
            long min_subarray_sum_for_prev_items = pushToStack(inc_stack, nums, i, (a, b) -> a > b);
            sum += max_subarray_sum_for_prev_items - min_subarray_sum_for_prev_items;
        }

        return sum;
    }

    private long pushToStack(Deque<Integer> stack, int nums[], int i, BiFunction<Integer, Integer, Boolean> compare) {
        long sum = 0;

        while (!stack.isEmpty() &&
                compare.apply(nums[stack.peek()], nums[i])
                || i == nums.length     // this option is there to process all remaining items
        ) {
            int pop_i = stack.pop();
            int prev_i = stack.isEmpty() ? -1 : stack.peek();

            int left_items_count = pop_i - prev_i;
            int right_items_count = i - pop_i;
            sum += left_items_count * right_items_count * (long) nums[pop_i]; // make sure to typecast to long
        }
        stack.push(i);
        return sum;
    }

    public static void main(String[] args) {
        int[] nums = new int[] {
//                1,2,3         // returns 4
//                1,3,3         // returns 4
                4,-2,-3,4,1     // returns 50
        };
        
        SumOfSubarrayRanges2104 s = new SumOfSubarrayRanges2104();
        long result = s.subArrayRanges(nums);
        System.out.println("result = " + result);


    }
}
