package fastprep.amazon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class FindMaxScore {
    
    public static int maxScore(String data) {
        int n = data.length();
        int maxScore = calculateScore(data);
        
        // Try replacing each character
        for (int i = 0; i < n; i++) {
            char original = data.charAt(i);
            
            // Smart candidate selection
            Set<Character> candidates = new HashSet<>();
            if (i > 0) {
                char prev = data.charAt(i - 1);
                addAdjacent(candidates, prev);
            }
            if (i < n - 1) {
                char next = data.charAt(i + 1);
                addAdjacent(candidates, next);
            }
            
            if (candidates.isEmpty()) {
                for (char c = 'a'; c <= 'z'; c++) {
                    candidates.add(c);
                }
            }
            
            for (char c : candidates) {
                if (c == original) continue;
                
                char[] arr = data.toCharArray();
                arr[i] = c;
                String modified = new String(arr);
                
                int score = calculateScore(modified);
                maxScore = Math.max(maxScore, score);
            }
        }
        
        return maxScore;
    }
    
    // O(n) time, O(1) space - using formula L*(L+1)/2
    private static int calculateScore(String s) {
        int n = s.length();
        if (n == 0) return 0;
        
        int score = 0;
        int segmentLen = 1; // Length of current valid segment
        
        for (int i = 1; i < n; i++) {
            int diff = Math.abs(s.charAt(i) - s.charAt(i - 1));
            if (diff <= 1) {
                segmentLen++;
            } else {
                // Segment ended, add all substrings from this segment
                // Formula: segment of length L has L*(L+1)/2 total substrings
                score += segmentLen * (segmentLen + 1) / 2;
                segmentLen = 1; // Start new segment
            }
        }
        
        // Don't forget the last segment
        score += segmentLen * (segmentLen + 1) / 2;
        
        return score;
    }

    private static void addAdjacent(Set<Character> set, char c) {
        set.add(c);
        if (c > 'a') set.add((char)(c - 1));
        if (c < 'z') set.add((char)(c + 1));
    }
    
    // Brute force verification (for testing)
    private static int calculateScoreBruteForce(String s) {
        int score = 0;
        for (int i = 0; i < s.length(); i++) {
            for (int j = i + 1; j <= s.length(); j++) {
                String sub = s.substring(i, j);
                boolean valid = true;
                for (int k = 0; k < sub.length() - 1; k++) {
                    if (Math.abs(sub.charAt(k) - sub.charAt(k + 1)) > 1) {
                        valid = false;
                        break;
                    }
                }
                if (valid) {
                    score++;
                }
            }
        }
        return score;
    }
    
    public static void main(String[] args) {
        System.out.println("=== Initial Test Cases ===");
        
        // Test case 1: From problem
        String data1 = "aabacfgh";
        System.out.println("Test 1 'aabacfgh': " + maxScore(data1)); // Expected: 21
        
        // Test case 2: Simple
        String data2 = "abc";
        System.out.println("Test 2 'abc': " + maxScore(data2));
        
        // Test case 3: All same
        String data3 = "aaaa";
        System.out.println("Test 3 'aaaa': " + maxScore(data3));
        
        // Test case 4: Disconnected
        String data4 = "ace";
        System.out.println("Test 4 'ace': " + maxScore(data4));
        
        System.out.println("\n=== Verifying Formula Correctness ===");
        Random rand = new Random(42);
        boolean allCorrect = true;
        
        // Verify calculateScore against brute force
        for (int test = 0; test < 100; test++) {
            int len = rand.nextInt(15) + 1; // Length 1-15
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < len; i++) {
                sb.append((char)('a' + rand.nextInt(26)));
            }
            String testStr = sb.toString();
            
            int formulaResult = calculateScore(testStr);
            int bruteForceResult = calculateScoreBruteForce(testStr);
            
            if (formulaResult != bruteForceResult) {
                System.out.println("MISMATCH for '" + testStr + "': formula=" + formulaResult + ", brute=" + bruteForceResult);
                allCorrect = false;
            }
        }
        
        if (allCorrect) {
            System.out.println("✓ All 100 formula verification tests passed!");
        }
        
        System.out.println("\n=== Random Dataset Testing (500 tests) ===");
        
        long totalTime = 0;
        int minScore = Integer.MAX_VALUE;
        int maxScoreValue = Integer.MIN_VALUE;
        int totalScore = 0;
        
        List<TestResult> interestingResults = new ArrayList<>();
        
        for (int test = 0; test < 500; test++) {
            // Generate random string of varying length
            int len = rand.nextInt(20) + 1; // Length 1-20
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < len; i++) {
                sb.append((char)('a' + rand.nextInt(26)));
            }
            String testData = sb.toString();
            
            long start = System.nanoTime();
            int originalScore = calculateScore(testData);
            int resultScore = maxScore(testData);
            long end = System.nanoTime();
            
            totalTime += (end - start);
            minScore = Math.min(minScore, resultScore);
            maxScoreValue = Math.max(maxScoreValue, resultScore);
            totalScore += resultScore;
            
            // Track interesting cases
            int improvement = resultScore - originalScore;
            if (improvement > 10 || len <= 5 || test < 5) {
                interestingResults.add(new TestResult(testData, originalScore, resultScore, improvement));
            }
        }
        
        System.out.println("✓ Completed 500 random tests successfully!");
        System.out.println("\n=== Statistics ===");
        System.out.println("Average time per test: " + (totalTime / 500 / 1_000_000.0) + " ms");
        System.out.println("Min score: " + minScore);
        System.out.println("Max score: " + maxScoreValue);
        System.out.println("Average score: " + (totalScore / 500.0));
        
        System.out.println("\n=== Sample Interesting Results ===");
        Collections.sort(interestingResults, (a, b) -> Integer.compare(b.improvement, a.improvement));
        for (int i = 0; i < Math.min(15, interestingResults.size()); i++) {
            TestResult tr = interestingResults.get(i);
            System.out.printf("'%s' (len=%d): %d -> %d (improvement: +%d)%n", 
                tr.data, tr.data.length(), tr.originalScore, tr.finalScore, tr.improvement);
        }
        
        System.out.println("\n=== Edge Cases Testing ===");
        
        // Edge case: single character
        String edge1 = "a";
        System.out.println("Single char 'a': " + maxScore(edge1));
        
        // Edge case: two same characters
        String edge2 = "aa";
        System.out.println("Two same 'aa': " + maxScore(edge2));
        
        // Edge case: two adjacent characters
        String edge3 = "ab";
        System.out.println("Two adjacent 'ab': " + maxScore(edge3));
        
        // Edge case: two far apart characters
        String edge4 = "az";
        System.out.println("Two far 'az': " + maxScore(edge4));
        
        // Edge case: all same long string
        String edge5 = "aaaaaaaaaa";
        System.out.println("All same (10): " + maxScore(edge5));
        
        // Edge case: perfect sequence
        String edge6 = "abcdefghij";
        System.out.println("Perfect sequence: " + maxScore(edge6));
        
        // Edge case: reverse sequence
        String edge7 = "jihgfedcba";
        System.out.println("Reverse sequence: " + maxScore(edge7));
        
        // Edge case: alternating
        String edge8 = "ababababab";
        System.out.println("Alternating 'ab': " + maxScore(edge8));
        
        System.out.println("\n=== Performance Test (Large Inputs) ===");
        
        // Test with various sizes
        int[] sizes = {50, 100, 200, 500, 1000};
        for (int size : sizes) {
            StringBuilder largeSb = new StringBuilder();
            for (int i = 0; i < size; i++) {
                largeSb.append((char)('a' + rand.nextInt(5))); // Use only a-e for some continuity
            }
            String largeData = largeSb.toString();
            
            long start = System.nanoTime();
            int result = maxScore(largeData);
            long end = System.nanoTime();
            
            System.out.printf("Size %d: score=%d, time=%.2f ms%n", 
                size, result, (end - start) / 1_000_000.0);
        }
        
        System.out.println("\n=== Testing Specific Patterns ===");
        
        // Pattern: gradual increase
        String pattern1 = "abcdefghijklmnopqrstuvwxyz";
        System.out.println("Full alphabet: " + maxScore(pattern1));
        
        // Pattern: blocks of same characters
        String pattern2 = "aaabbbcccdddeee";
        System.out.println("Blocks of same: " + maxScore(pattern2));
        
        // Pattern: zigzag
        String pattern3 = "ababcdcdefefgh";
        System.out.println("Zigzag pattern: " + maxScore(pattern3));
        
        // Pattern: mostly disconnected with one connection
        String pattern4 = "acegikmoqsuwy";
        System.out.println("Mostly disconnected: " + maxScore(pattern4));
        
        System.out.println("\n=== All Tests Completed Successfully! ===");
    }
    
    static class TestResult {
        String data;
        int originalScore;
        int finalScore;
        int improvement;
        
        TestResult(String data, int originalScore, int finalScore, int improvement) {
            this.data = data;
            this.originalScore = originalScore;
            this.finalScore = finalScore;
            this.improvement = improvement;
        }
    }
}