package fastprep.amazon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MaximumAffinityV4 {
    
    public static long maximumAffinity(List<Integer> affinity, List<List<Integer>> rules) {
        // affinity is even
        // same region cannot be chose in consecutive steps - region chose to maximize affinity
        long regionA = 0;
        
        Set<Integer> used = new HashSet<>();
        for (List<Integer> rule : rules) {
            int idx1 = rule.get(0) - 1;
            int idx2 = rule.get(1) - 1;
            
            used.add(idx1);
            used.add(idx2);
            regionA += Math.max(affinity.get(idx1), affinity.get(idx2));
        }
        
        List<Integer> free = new ArrayList<>();
        for (int i = 0; i < affinity.size(); ++i) {
            if (!used.contains(i)) {
                free.add(affinity.get(i));
            }
        }
        
        free.sort(Collections.reverseOrder());
        for (int i = 0; i < free.size()/2; i++) {
            regionA += free.get(i);
        }
        
        return regionA;
    }
    
    public static void main(String[] args) {
        System.out.println("=== Testing Maximum Affinity Problem ===\n");
        
        // Sample Case 0
        System.out.println("Sample Case 0:");
        List<Integer> affinity0 = Arrays.asList(1, 2, 3, 4);
        List<List<Integer>> rules0 = new ArrayList<>();
        long result0 = maximumAffinity(affinity0, rules0);
        System.out.println("affinity = " + affinity0);
        System.out.println("rules = " + rules0);
        System.out.println("Result: " + result0);
        System.out.println("Expected: 7");
        System.out.println("Match: " + (result0 == 7 ? "✓" : "✗"));
        System.out.println();
        
        // Sample Case 1
        System.out.println("Sample Case 1:");
        List<Integer> affinity1 = Arrays.asList(1, 2, 3, 4, 5, 6);
        List<List<Integer>> rules1 = new ArrayList<>();
        rules1.add(Arrays.asList(1, 2));
        rules1.add(Arrays.asList(3, 4));
        rules1.add(Arrays.asList(5, 6));
        long result1 = maximumAffinity(affinity1, rules1);
        System.out.println("affinity = " + affinity1);
        System.out.println("rules = " + rules1);
        System.out.println("Result: " + result1);
        System.out.println("Expected: 12");
        System.out.println("Match: " + (result1 == 12 ? "✓" : "✗"));
        System.out.println();
        
        // Example from Code Question 2
        System.out.println("Example from Code Question 2:");
        List<Integer> affinity2 = Arrays.asList(3, 2, -4, 8, 3, -7);
        List<List<Integer>> rules2 = new ArrayList<>();
        rules2.add(Arrays.asList(2, 4));
        rules2.add(Arrays.asList(3, 6));
        long result2 = maximumAffinity(affinity2, rules2);
        System.out.println("affinity = " + affinity2);
        System.out.println("rules = " + rules2);
        System.out.println("Result: " + result2);
        System.out.println("Expected: 7");
        System.out.println("Match: " + (result2 == 7 ? "✓" : "✗"));
        System.out.println();
        
        // Explanation of algorithm
        System.out.println("=== Algorithm Explanation ===");
        System.out.println("1. For each rule [x, y], region A takes the MAX of affinity[x] and affinity[y]");
        System.out.println("2. Mark both indices as used");
        System.out.println("3. For remaining free indices, sort in descending order");
        System.out.println("4. Region A takes the top half (alternating A, B, A, B...)");
        System.out.println();
        System.out.println("Example: affinity = [3, 2, -4, 8, 3, -7], rules = [[2,4], [3,6]]");
        System.out.println("Rule [2,4]: max(2, 8) = 8 → Region A gets 8");
        System.out.println("Rule [3,6]: max(-4, -7) = -4 → Region A gets -4");
        System.out.println("Used indices: 1, 3, 2, 5 (0-indexed)");
        System.out.println("Free indices: 0, 4 with values [3, 3]");
        System.out.println("Sorted descending: [3, 3]");
        System.out.println("Region A takes first half: 3");
        System.out.println("Total for Region A: 8 + (-4) + 3 = 7 ✓");
    }
}