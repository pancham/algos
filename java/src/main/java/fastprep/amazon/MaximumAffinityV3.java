package fastprep.amazon;

import java.util.*;

public class MaximumAffinityV3 {
    
    public static long maximumAffinity(int[] affinity, int[][] rules) {
        int n = affinity.length;
        
        // Build rule map (convert to 0-indexed)
        Map<Integer, Integer> ruleMap = new HashMap<>();
        for (int[] rule : rules) {
            ruleMap.put(rule[0] - 1, rule[1] - 1);
        }
        
        // Use DP with memoization
        Map<String, Long> memo = new HashMap<>();
        boolean[] used = new boolean[n];
        return dfs(affinity, ruleMap, used, true, memo);
    }
    
    private static long dfs(int[] affinity, Map<Integer, Integer> ruleMap, 
                           boolean[] used, boolean isRegionA, Map<String, Long> memo) {
        // Create memoization key
        StringBuilder key = new StringBuilder();
        for (boolean u : used) key.append(u ? '1' : '0');
        key.append(isRegionA ? 'A' : 'B');
        
        if (memo.containsKey(key.toString())) {
            return memo.get(key.toString());
        }
        
        // Check if all elements used
        boolean allUsed = true;
        for (boolean u : used) {
            if (!u) {
                allUsed = false;
                break;
            }
        }
        if (allUsed) return 0;
        
        long best = Long.MIN_VALUE;
        
        // Try each unused index for current region
        for (int i = 0; i < affinity.length; i++) {
            if (used[i]) continue;
            
            // Mark this index as used by current region
            used[i] = true;
            long score = isRegionA ? affinity[i] : 0;
            
            // Check if there's a rule forcing the next choice
            if (ruleMap.containsKey(i)) {
                int forcedIdx = ruleMap.get(i);
                if (forcedIdx < affinity.length && !used[forcedIdx]) {
                    // The OTHER region MUST take forcedIdx (it's their turn anyway due to alternation)
                    used[forcedIdx] = true;
                    long forcedScore = !isRegionA ? affinity[forcedIdx] : 0;
                    
                    // After both moves, it's back to the original region's turn
                    long future = dfs(affinity, ruleMap, used, isRegionA, memo);
                    best = Math.max(best, score + forcedScore + future);
                    used[forcedIdx] = false;
                } else {
                    // Forced index already used, just switch regions normally
                    long future = dfs(affinity, ruleMap, used, !isRegionA, memo);
                    best = Math.max(best, score + future);
                }
            } else {
                // No rule triggered, switch regions normally
                long future = dfs(affinity, ruleMap, used, !isRegionA, memo);
                best = Math.max(best, score + future);
            }
            
            used[i] = false;
        }
        
        long result = (best == Long.MIN_VALUE) ? 0 : best;
        memo.put(key.toString(), result);
        return result;
    }
    
    public static void main(String[] args) {
        System.out.println("=== Testing Maximum Affinity Problem ===\n");
        
        // Sample Case 0
        System.out.println("Sample Case 0:");
        int[] affinity0 = {1, 2, 3, 4};
        int[][] rules0 = {};
        long result0 = maximumAffinity(affinity0, rules0);
        System.out.println("affinity = " + Arrays.toString(affinity0));
        System.out.println("rules = " + Arrays.deepToString(rules0));
        System.out.println("Result: " + result0);
        System.out.println("Expected: 7");
        System.out.println("Match: " + (result0 == 7 ? "✓" : "✗"));
        System.out.println();
        
        // Sample Case 1
        System.out.println("Sample Case 1:");
        int[] affinity1 = {1, 2, 3, 4, 5, 6};
        int[][] rules1 = {{1, 2}, {3, 4}, {5, 6}};
        long result1 = maximumAffinity(affinity1, rules1);
        System.out.println("affinity = " + Arrays.toString(affinity1));
        System.out.println("rules = " + Arrays.deepToString(rules1));
        System.out.println("Result: " + result1);
        System.out.println("Expected: 12");
        System.out.println("Match: " + (result1 == 12 ? "✓" : "✗"));
        System.out.println();
        
        // Example from Code Question 2
        System.out.println("Example from Code Question 2:");
        int[] affinity2 = {3, 2, -4, 8, 3, -7};
        int[][] rules2 = {{2, 4}, {3, 6}};
        long result2 = maximumAffinity(affinity2, rules2);
        System.out.println("affinity = " + Arrays.toString(affinity2));
        System.out.println("rules = " + Arrays.deepToString(rules2));
        System.out.println("Result: " + result2);
        System.out.println("Expected: 7");
        System.out.println("Match: " + (result2 == 7 ? "✓" : "✗"));
        System.out.println();
        
        // Detailed trace
        System.out.println("=== Understanding Sample Case 1 ===");
        System.out.println("affinity = [1, 2, 3, 4, 5, 6] (0-indexed: [0,1,2,3,4,5])");
        System.out.println("rules (1-indexed) = [[1,2], [3,4], [5,6]]");
        System.out.println("rules (0-indexed) = [[0,1], [2,3], [4,5]]");
        System.out.println();
        System.out.println("From explanation: 'indices chosen in order [4, 3]. This forces regionB to");
        System.out.println("choose at indices [2, 1].'");
        System.out.println("Wait, that's 1-indexed in explanation, so 0-indexed: [3, 2] chosen, forces [1, 0]");
        System.out.println();
        System.out.println("Let me reread... '6, 4, 2' in explanation means values 6, 4, 2");
        System.out.println("In the array: 6 is at index 5, 4 is at index 3, 2 is at index 1");
        System.out.println("Region A picks index 5 (value 6), rule [4,5] forces B to pick index 4 (value 5)");
        System.out.println("Region A picks index 3 (value 4), rule [2,3] forces B to pick index 2 (value 3)");  
        System.out.println("Region A picks index 1 (value 2), rule [0,1] forces B to pick index 0 (value 1)");
        System.out.println("Region A sum: 6 + 4 + 2 = 12 ✓");
    }
}
