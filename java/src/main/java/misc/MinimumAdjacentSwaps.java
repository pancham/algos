package misc;

public class MinimumAdjacentSwaps {

    // Function to find the minimum number of swaps required to make the given array sorted
    public int minimumSwaps(int[] nums) {
        int n = nums.length; // Length of the given array
        int minIndex = 0, maxIndex = 0; // Initialize indices for minimum and maximum elements

        // Loop through the array to find the indices for the minimum and maximum elements
        for (int i = 0; i < n; ++i) {
            // Update the index of the minimum element found so far
            if (nums[i] < nums[minIndex] || (nums[i] == nums[minIndex] && i < minIndex)) {
                minIndex = i;
            }
            // Update the index of the maximum element found so far
            if (nums[i] > nums[maxIndex] || (nums[i] == nums[maxIndex] && i > maxIndex)) {
                maxIndex = i;
            }
        }

        // If the minimum and maximum elements are at the same position, no swaps are needed
//        if (minIndex == maxIndex) {
//            return 0;
//        }

        if (minIndex == 0 && maxIndex == nums.length) {
            return 0;
        }

        // Number of swaps for max element
        int numOfRightSwaps = (nums.length - 1 - maxIndex);

        // Number of swaps for min element
        int numOfLeftSwaps = minIndex;

        // If min element is to the right of max element, then one less swap is required, since
        // moving the max element would move the min element by 1 toward beginning
        int adjustment = (minIndex > maxIndex) ? 1 : 0;

        return numOfLeftSwaps + numOfRightSwaps - adjustment;
    }
}
