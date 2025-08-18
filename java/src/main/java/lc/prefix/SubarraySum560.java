package lc.prefix;

import java.util.HashMap;
import java.util.Map;

/**
 * Neetcode: https://www.youtube.com/watch?v=fFVZt-6sgyo
 */
public class SubarraySum560 {
    public static void main(String[] args) {

        // int[] nums = new int[]{1, 1, 1}; 
        // int k = 2;  // count = 2

        int[] nums = new int[]{1, 2, 3}; 
        int k = 3;  // count = 2

        SubarraySum560 s = new SubarraySum560();
        System.out.println(s.subarraySum(nums, k));
    }

    public int subarraySum(int[] nums, int k) {
        // Map for storing the cumulative sum and its frequency.
        Map<Integer, Integer> sumFrequencyMap = new HashMap<>();

        // Initializing with zero sum having frequency one.
        sumFrequencyMap.put(0, 1);

        int totalCount = 0; // This will hold the number of subarrays that sum to k.
        int prefixSum = 0; // This holds the cumulative sum of elements.

        // Loop over all elements in the array.
        for (int num : nums) {
            // Add the current element to the cumulative sum.
            prefixSum += num;

            int diff = prefixSum - k;

            // If cumulativeSum - k exists in map, then there are some subarrays ending with num that sum to k.
            totalCount += sumFrequencyMap.getOrDefault(diff, 0);

            // Increment the frequency of the current cumulative sum in the map.
            // If the cumulative sum isn't already in the map, DefaultValue (0) will be used first.
            sumFrequencyMap.merge(prefixSum, 1, Integer::sum);
        }

        // Return the total count of subarrays that sum to k.
        return totalCount;
    }

}
