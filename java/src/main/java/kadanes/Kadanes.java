package kadanes;

public class Kadanes {
    public static int[] maxSubArrayWithIndices(int[] nums) {
        int maxSoFar = nums[0], currentMax = nums[0];
        int start = 0, end = 0, tempStart = 0;

        for (int i = 1; i < nums.length; i++) {
            // Find the maximum sum ending at index i by either extending
            // the currentMax with element i or by
            // starting a new subarray from index i
            if (nums[i] > currentMax + nums[i]) {
                currentMax = nums[i];
                tempStart = i; // Start a new subarray
            } else {
                currentMax += nums[i];
            }

            if (currentMax > maxSoFar) {
                maxSoFar = currentMax;
                start = tempStart;
                end = i;
            }
        }

        return new int[]{maxSoFar, start, end}; // Returning sum and subarray indices
    }

    public static void main(String[] args) {
        int[] arr = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        int[] result = maxSubArrayWithIndices(arr);
        System.out.println("Maximum Subarray Sum: " + result[0]);
        System.out.println("Subarray found from index " + result[1] + " to " + result[2]);
    }
}

