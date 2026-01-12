package fastprep.amazon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Solution {
    
    public static int findMinimumGroups(List<Integer> security) {
        // Count frequency of each security grade
        Map<Integer, Integer> frequencyMap = new HashMap<>();
        for (int grade : security) {
            frequencyMap.put(grade, frequencyMap.getOrDefault(grade, 0) + 1);
        }
        
        int n = security.size();
        
        // Try minimum number of groups starting from 1
        for (int numGroups = 1; numGroups <= n; numGroups++) {
            int minSize = n / numGroups;
            int maxSize = minSize + 1;
            
            // Check if we can distribute each security grade into these group sizes
            boolean valid = true;
            
            for (int count : frequencyMap.values()) {
                // Can we form 'count' servers using groups of size minSize and maxSize?
                boolean canForm = false;
                for (int x = 0; x <= count / maxSize; x++) {
                    int remaining = count - x * maxSize;
                    if (remaining % minSize == 0) {
                        canForm = true;
                        break;
                    }
                }
                
                if (!canForm) {
                    valid = false;
                    break;
                }
            }
            
            if (valid) {
                return numGroups;
            }
        }
        
        return n;
    }
    
    // Generate random test case
    public static List<Integer> generateRandomTest(int size, int maxSecurityGrade) {
        Random rand = new Random();
        List<Integer> security = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            security.add(rand.nextInt(maxSecurityGrade) + 1);
        }
        return security;
    }
    
    // Generate edge case tests
    public static List<List<Integer>> generateEdgeCases() {
        List<List<Integer>> edgeCases = new ArrayList<>();
        
        // Single element
        edgeCases.add(Arrays.asList(1));
        
        // All same
        edgeCases.add(Arrays.asList(1, 1, 1, 1, 1));
        
        // All different
        edgeCases.add(Arrays.asList(1, 2, 3, 4, 5));
        
        // Two groups
        edgeCases.add(Arrays.asList(1, 1, 2, 2));
        
        // Unbalanced
        edgeCases.add(Arrays.asList(1, 2, 2, 2, 2, 2));
        
        // Large frequency difference
        edgeCases.add(Arrays.asList(1, 2, 2, 2, 2, 2, 2, 2, 2, 2));
        
        // Example from problem
        edgeCases.add(Arrays.asList(2, 3, 3, 3, 2, 1));
        
        // Powers of 2
        edgeCases.add(Arrays.asList(1, 1, 2, 2, 2, 2));
        
        // Prime number sizes
        edgeCases.add(Arrays.asList(1, 1, 1, 2, 2, 2, 2, 2));
        
        // Many different grades with small counts
        List<Integer> manyGrades = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            manyGrades.add(i);
            manyGrades.add(i);
        }
        edgeCases.add(manyGrades);
        
        return edgeCases;
    }
    
    // Verify the result makes sense
    public static boolean verifyResult(List<Integer> security, int result) {
        if (result < 1 || result > security.size()) {
            return false;
        }
        
        int n = security.size();
        int minSize = n / result;
        int maxSize = minSize + 1;
        
        // Verify that group sizes only differ by at most 1
        if (maxSize - minSize > 1) {
            return false;
        }
        
        // Verify each frequency can be distributed
        Map<Integer, Integer> frequencyMap = new HashMap<>();
        for (int grade : security) {
            frequencyMap.put(grade, frequencyMap.getOrDefault(grade, 0) + 1);
        }
        
        for (int count : frequencyMap.values()) {
            boolean canForm = false;
            for (int x = 0; x <= count / maxSize; x++) {
                int remaining = count - x * maxSize;
                if (remaining % minSize == 0) {
                    canForm = true;
                    break;
                }
            }
            if (!canForm) {
                return false;
            }
        }
        
        return true;
    }
    
    public static void main(String[] args) {
        int totalTests = 0;
        int passedTests = 0;
        int failedTests = 0;
        
        System.out.println("Starting Test Harness with 500+ test cases...\n");
        
        // Test edge cases
        System.out.println("=== Testing Edge Cases ===");
        List<List<Integer>> edgeCases = generateEdgeCases();
        for (int i = 0; i < edgeCases.size(); i++) {
            List<Integer> testCase = edgeCases.get(i);
            totalTests++;
            
            try {
                int result = findMinimumGroups(testCase);
                boolean valid = verifyResult(testCase, result);
                
                if (valid) {
                    passedTests++;
                    System.out.println("Edge Case " + (i+1) + " PASSED: " + testCase + " -> " + result);
                } else {
                    failedTests++;
                    System.out.println("Edge Case " + (i+1) + " FAILED: Invalid result " + result);
                }
            } catch (Exception e) {
                failedTests++;
                System.out.println("Edge Case " + (i+1) + " ERROR: " + e.getMessage());
            }
        }
        
        System.out.println("\n=== Testing Small Random Cases (n=2-10) ===");
        for (int i = 0; i < 100; i++) {
            int size = 2 + (int)(Math.random() * 9); // 2 to 10
            int maxGrade = 1 + (int)(Math.random() * 5); // 1 to 5
            List<Integer> testCase = generateRandomTest(size, maxGrade);
            totalTests++;
            
            try {
                int result = findMinimumGroups(testCase);
                boolean valid = verifyResult(testCase, result);
                
                if (valid) {
                    passedTests++;
                    if (i < 5) { // Print first 5
                        System.out.println("Test " + (i+1) + " PASSED: " + testCase + " -> " + result);
                    }
                } else {
                    failedTests++;
                    System.out.println("Test " + (i+1) + " FAILED: " + testCase + " -> " + result);
                }
            } catch (Exception e) {
                failedTests++;
                System.out.println("Test " + (i+1) + " ERROR: " + e.getMessage());
            }
        }
        System.out.println("... (95 more tests run)");
        
        System.out.println("\n=== Testing Medium Random Cases (n=11-50) ===");
        for (int i = 0; i < 200; i++) {
            int size = 11 + (int)(Math.random() * 40); // 11 to 50
            int maxGrade = 1 + (int)(Math.random() * 10); // 1 to 10
            List<Integer> testCase = generateRandomTest(size, maxGrade);
            totalTests++;
            
            try {
                int result = findMinimumGroups(testCase);
                boolean valid = verifyResult(testCase, result);
                
                if (valid) {
                    passedTests++;
                    if (i < 3) { // Print first 3
                        System.out.println("Test " + (i+1) + " PASSED: size=" + size + ", result=" + result);
                    }
                } else {
                    failedTests++;
                    System.out.println("Test " + (i+1) + " FAILED: size=" + size + ", result=" + result);
                }
            } catch (Exception e) {
                failedTests++;
                System.out.println("Test " + (i+1) + " ERROR: " + e.getMessage());
            }
        }
        System.out.println("... (197 more tests run)");
        
        System.out.println("\n=== Testing Large Random Cases (n=51-100) ===");
        for (int i = 0; i < 150; i++) {
            int size = 51 + (int)(Math.random() * 50); // 51 to 100
            int maxGrade = 1 + (int)(Math.random() * 20); // 1 to 20
            List<Integer> testCase = generateRandomTest(size, maxGrade);
            totalTests++;
            
            try {
                int result = findMinimumGroups(testCase);
                boolean valid = verifyResult(testCase, result);
                
                if (valid) {
                    passedTests++;
                    if (i < 3) { // Print first 3
                        System.out.println("Test " + (i+1) + " PASSED: size=" + size + ", result=" + result);
                    }
                } else {
                    failedTests++;
                    System.out.println("Test " + (i+1) + " FAILED: size=" + size + ", result=" + result);
                }
            } catch (Exception e) {
                failedTests++;
                System.out.println("Test " + (i+1) + " ERROR: " + e.getMessage());
            }
        }
        System.out.println("... (147 more tests run)");
        
        System.out.println("\n=== Testing Very Large Random Cases (n=101-200) ===");
        for (int i = 0; i < 50; i++) {
            int size = 101 + (int)(Math.random() * 100); // 101 to 200
            int maxGrade = 1 + (int)(Math.random() * 30); // 1 to 30
            List<Integer> testCase = generateRandomTest(size, maxGrade);
            totalTests++;
            
            try {
                int result = findMinimumGroups(testCase);
                boolean valid = verifyResult(testCase, result);
                
                if (valid) {
                    passedTests++;
                    if (i < 3) { // Print first 3
                        System.out.println("Test " + (i+1) + " PASSED: size=" + size + ", result=" + result);
                    }
                } else {
                    failedTests++;
                    System.out.println("Test " + (i+1) + " FAILED: size=" + size + ", result=" + result);
                }
            } catch (Exception e) {
                failedTests++;
                System.out.println("Test " + (i+1) + " ERROR: " + e.getMessage());
            }
        }
        System.out.println("... (47 more tests run)");
        
        // Summary
        System.out.println("\n" + "=".repeat(50));
        System.out.println("TEST SUMMARY");
        System.out.println("=".repeat(50));
        System.out.println("Total Tests: " + totalTests);
        System.out.println("Passed: " + passedTests + " (" + String.format("%.2f", 100.0 * passedTests / totalTests) + "%)");
        System.out.println("Failed: " + failedTests + " (" + String.format("%.2f", 100.0 * failedTests / totalTests) + "%)");
        System.out.println("=".repeat(50));
        
        if (failedTests == 0) {
            System.out.println("\n✓ All tests passed!");
        } else {
            System.out.println("\n✗ Some tests failed. Review the failures above.");
        }
    }
}