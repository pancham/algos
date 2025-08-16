package test;

import java.util.*;

public class BitmaskSolution {
    public static int superBitStrings(int n, List<Integer> bitStrings) {
        Set<Integer> uniqueSuperStrings = new HashSet<>();

        for (int num : bitStrings) {
            int base = num;
            int fullMask = (1 << n) - 1;  // All bits set for n bits
            int zeroMask = fullMask ^ base;  // Positions where original bit is 0

            // Generate all subsets of zeroMask (i.e., flip combinations)
            int subset = zeroMask;
            while (true) {
                int superStr = base | subset;
                uniqueSuperStrings.add(superStr);

                if (subset == 0) break;
                subset = (subset - 1) & zeroMask;
            }
        }

        return uniqueSuperStrings.size();
    }

    // Sample usage / test
    public static void main(String[] args) {
        int n = 5;
        List<Integer> bitStrings = Arrays.asList(10, 26);  // 01010 and 11010
        int result = superBitStrings(n, bitStrings);
        System.out.println(result);  // Expected: 8
    }
}
