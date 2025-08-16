package lc;

/**
 * You are given an integer array height of length n. There are n vertical lines drawn such that the two endpoints of the ith line are (i, 0) and (i, height[i]).
 *
 * Find two lines that together with the x-axis form a container, such that the container contains the most water.
 *
 * Return the maximum amount of water a container can store.
 */

public class ContainerWithMostWater11 {
    public static void main(String[] args) {
        int[] height = {1,8,6,2,5,4,8,3,7};

        ContainerWithMostWater11 sol = new ContainerWithMostWater11();
        int area = sol.maxArea(height);
        System.out.println("area = " + area);
    }

    public int maxArea(int[] height) {
        int maxarea = 0;
        int left = 0;
        int right = height.length - 1;
        while (left < right) {
            int width = right - left;
            maxarea = Math.max(
                    maxarea,
                    Math.min(height[left], height[right]) * width
            );
            if (height[left] <= height[right]) {
                left++;
            } else {
                right--;
            }
        }
        return maxarea;
    }
}
