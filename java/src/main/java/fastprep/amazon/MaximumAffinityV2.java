package fastprep.amazon;

import java.util.*;

public class MaximumAffinityV2 {
    
    static class State {
        boolean[] used;
        boolean isRegionA;
        
        State(int n) {
            used = new boolean[n];
            isRegionA = true;
        }
        
        State(State other) {
            this.used = other.used.clone();
            this.isRegionA = other.isRegionA;
        }
    }
    
    public static long maximumAffinity(int[] affinity, int[][] rules) {
        int n = affinity.length;
        
        // Build rule map (1-indexed in input)
        Map<Integer, Integer> ruleMap = new HashMap<>();
        for (int[] rule : rules) {
            ruleMap.put(rule[0] - 1, rule[1] - 1); // Convert to 0-indexed
        }
        
        // Use memoization with DFS
        Map<String, Long> memo = new HashMap<>();
        boolean[] used = new boolean[n];
        return solve(affinity, ruleMap, used, true, memo);
    }
    
    private static long solve(int[] affinity, Map<Integer, Integer> ruleMap, 
                             boolean[] used, boolean isRegionA, Map<String, Long> memo) {
        // Create state key for memoization
        StringBuilder key = new StringBuilder();
        for (boolean u : used) key.append(u ? '1' : '0');
        key.append(isRegionA ? 'A' : 'B');
        
        if (memo.containsKey(key.toString())) {
            return memo.get(key.toString());
        }
        
        // Check if all used
        boolean allUsed = true;
        for (boolean u : used) {
            if (!u) {
                allUsed = false;
                break;
            }
        }
        if (allUsed) return 0;
        
        long best = Long.MIN_VALUE;
        
        // Try each unused index
        for (int i = 0; i < affinity.length; i++) {
            if (used[i]) continue;
            
            // Choose this index for current region
            used[i] = true;
            long currentScore = isRegionA ? affinity[i] : 0;
            
            // Check if rule forces next choice
            if (ruleMap.containsKey(i)) {
                int forced = ruleMap.get(i);
                if (forced < affinity.length && !used[forced]) {
                    used[forced] = true;
                    long forcedScore = !isRegionA ? affinity[forced] : 0;
                    long future = solve(affinity, ruleMap, used, isRegionA, memo);
                    best = Math.max(best, currentScore + forcedScore + future);
                    used[forced] = false;
                } else {
                    // Forced index already used, just continue
                    long future = solve(affinity, ruleMap, used, !isRegionA, memo);
                    best = Math.max(best, currentScore + future);
                }
            } else {
                // No forced move, switch regions
                long future = solve(affinity, ruleMap, used, !isRegionA, memo);
                best = Math.max(best, currentScore + future);
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
        
        //Manual trace of example
        System.out.println("=== Manual Trace ===");
        System.out.println("affinity (0-indexed) = [3, 2, -4, 8, 3, -7]");
        System.out.println("                        [0, 1,  2, 3, 4,  5]");
        System.out.println("rules (1-indexed) = [[2,4], [3,6]]");
        System.out.println("rules (0-indexed) = [[1,3], [2,5]]");
        System.out.println();
        System.out.println("From the table:");
        System.out.println("A picks index 2 (value -4), rule [2,5] forces B to pick index 5 (value -7)");
        System.out.println("A picks index 3 (value 8), rule [1,3] forces B to pick index 1 (value 2)");
        System.out.println("A picks index 4 (value 3), max remaining");
        System.out.println("B picks index 0 (value 3), last piece");
        System.out.println("Region A total: -4 + 8 + 3 = 7");
    }
}
