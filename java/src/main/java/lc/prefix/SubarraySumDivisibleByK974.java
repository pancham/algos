package lc.prefix;

import java.util.HashMap;
import java.util.Map;

/**
 * Neetcode: https://www.youtube.com/watch?v=bcXy-T4Sc3E
 */
public class SubarraySumDivisibleByK974 {
    public static void main(String[] args) {
        int[] nums = {4,5,0,-2,-3,1};
        int k = 5; // count = 7

        SubarraySumDivisibleByK974 s = new SubarraySumDivisibleByK974();
        System.out.println(s.subarraysDivByK(nums, k));
    }

    /**
     * (prefixSum[j] - prefix[i]) % k = 0
     * divisor * quotient + remainder
     * 
     * (d1 * k + r1) - (d2 * k + r2) 
     * = (d1 - d2) * k + (r1 - r2)
     * The first term (k * (d1 - d2)) is divisible by k, so for the entire expression to be divisible by k, r1 - r2 must also be divisible by k. 
     * This gives us an equation:
     * r1 - r2 = C * k
     * -> r1 = C * k + r2
     * 
     * Since (0 <= r1 <= k-1), the only possible value of C is 0, resulting in:
     * r1 = r2
     */
    public int subarraysDivByK(int[] nums, int k) {
        // Create a hashmap to store the frequencies of prefix sum remainders.
        Map<Integer, Integer> countMap = new HashMap<>();
        // Initialize with remainder 0 having frequency 1.
        countMap.put(0, 1);

        // 'answer' will keep the total count of subarrays divisible by k.
        int answer = 0;
        // 'modSum' will store the cumulative mod.
        int prefixSum = 0;

        // Loop through all numbers in the array.
        for (int num : nums) {
            prefixSum += num;
            // Update the cumulative sum and adjust it to always be positive and within the range of [0, k-1]
            int mod = (prefixSum % k + k) % k;
            // If this remainder has been seen before, add the number of times it has been seen to the answer.
            answer += countMap.getOrDefault(mod, 0);
            // Increase the frequency of this remainder by 1.
            countMap.merge(mod, 1, Integer::sum);
        }

        // Return the total count of subarrays that are divisible by 'k'.
        return answer;
    }

}
