package fastprep.amazon;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CountSpecialSubStrings {
    
    public static int countSpecialSubstrings(String genome) {
        int n = genome.length();
        int count = 0;
        
        // Type 1: Length 2 with same characters
        for (int i = 0; i < n - 1; i++) {
            if (genome.charAt(i) == genome.charAt(i + 1)) {
                count++;
            }
        }
        
        // Type 2: Check all possible substrings of length >= 3
        for (int i = 0; i < n; i++) {
            char firstChar = genome.charAt(i);

            int j = i + 1;
            if (j >= n) {
                continue;
            }

            char middleChar = genome.charAt(i + 1);
            
            // Use this if Middle character must be different from first
            // if (middleChar == firstChar) continue;
            
            // Build the middle section - all characters must be middleChar
            
            while (j < n && genome.charAt(j) == middleChar) {
                // handle special substring of form "bbb" or "bbbb"
                // do not use this if middleChar must be different from firstChar
                if ((j >= (i+2)) && middleChar == firstChar) {
                    count++;
                }

                j++;
            }
            
            // j now points to first character after the middle section
            // Check if this character equals firstChar
            if (j < n && genome.charAt(j) == firstChar) {
                // We found one valid substring from i to j
                count++;
            }
        }
        
        return count;
    }
    
    // Brute force verification
    private static boolean isSpecial(String s) {
        int n = s.length();
        if (n < 2) return false;
        
        if (n == 2) {
            return s.charAt(0) == s.charAt(1);
        }
        
        if (s.charAt(0) != s.charAt(n - 1)) return false;
        
        String middle = s.substring(1, n - 1);
        if (middle.isEmpty()) return false;
        
        char firstMiddleChar = middle.charAt(0);
        for (char c : middle.toCharArray()) {
            if (c != firstMiddleChar) return false;
        }
        
        return true;
    }
    
    
    public static void main(String[] args) {
        // System.out.println(countSpecialSubstrings("abbbba"));
        // Solution harness = new CountSpecialSubStrings();
        // harness.runAllTests();
    }
}

class TestHarness {
    private Random rand = new Random(42);
    private int totalTests = 0;
    private int passedTests = 0;
    private int failedTests = 0;
    private List<TestCase> failedCases = new ArrayList<>();
    
    static class TestCase {
        String input;
        int expected;
        int actual;
        
        TestCase(String input, int expected, int actual) {
            this.input = input;
            this.expected = expected;
            this.actual = actual;
        }
    }
    
    public void runAllTests() {
        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.println("║          SPECIAL SUBSTRINGS TEST HARNESS                   ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");
        
        // Run predefined tests
        runPredefinedTests();
        
        // Run random tests
        runRandomTests(500);
        
        // Run edge case tests
        runEdgeCaseTests();
        
        // Run performance tests
        runPerformanceTests();
        
        // Print summary
        printSummary();
    }
    
    private void runPredefinedTests() {
        System.out.println("\n═══════════════════════════════════════════════════════════");
        System.out.println("PREDEFINED TEST CASES");
        System.out.println("═══════════════════════════════════════════════════════════");
        
        testCase("aa", 1, "Simple type 1");
        testCase("aba", 1, "Simple type 2");
        testCase("abba", 2, "bb + abba");
        testCase("aabaa", 3, "aa + aba + aa");
        testCase("abbbba", 5, "bb×3 + abbba + abbbba");
        testCase("abbaa", 2, "bb + aa (NOT abbaa - middle has 2 distinct)");
        testCase("abbaaa", 4, "bb + abba + aa×2");
        testCase("abcd", 0, "No special substrings");
        testCase("aaaa", 3, "aa at positions 0-1, 1-2, 2-3");
        testCase("xyyxzzx", 3, "yy + xyyx + zz");
        testCase("aabbaa", 4, "aa + bb + aa + abba");
        testCase("abcba", 1, "bcb");
        testCase("ababab", 5, "ab×3 + aba×2");
        testCase("aabbaabbaa", -1, "Complex pattern");
        testCase("xyyx", 2, "yy + xyyx");
    }
    
    private void runRandomTests(int numTests) {
        System.out.println("\n═══════════════════════════════════════════════════════════");
        System.out.println("RANDOM TEST CASES (" + numTests + " tests)");
        System.out.println("═══════════════════════════════════════════════════════════");
        
        int randomPassed = 0;
        int randomFailed = 0;
        
        for (int test = 0; test < numTests; test++) {
            // Generate random string with varying length and character diversity
            int len = rand.nextInt(50) + 1;
            int alphabetSize = rand.nextInt(5) + 2; // Use 2-6 different characters
            
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < len; i++) {
                sb.append((char)('a' + rand.nextInt(alphabetSize)));
            }
            String testStr = sb.toString();
            
            int calculated = CountSpecialSubStrings.countSpecialSubstrings(testStr);
            int bruteForce = countSpecialBruteForce(testStr);
            
            totalTests++;
            if (calculated == bruteForce) {
                passedTests++;
                randomPassed++;
            } else {
                failedTests++;
                randomFailed++;
                if (failedCases.size() < 10) { // Keep first 10 failures
                    failedCases.add(new TestCase(testStr, bruteForce, calculated));
                }
            }
            
            // Progress indicator
            if ((test + 1) % 100 == 0) {
                System.out.print(".");
            }
        }
        
        System.out.println();
        System.out.println("Random tests: " + randomPassed + " passed, " + randomFailed + " failed");
    }
    
    private void runEdgeCaseTests() {
        System.out.println("\n═══════════════════════════════════════════════════════════");
        System.out.println("EDGE CASE TESTS");
        System.out.println("═══════════════════════════════════════════════════════════");
        
        testCase("a", 0, "Single character");
        testCase("ab", 0, "Two different characters");
        testCase("aaa", 2, "Three same characters");
        testCase("abcdefghijklmnopqrstuvwxyz", 0, "Full alphabet");
        testCase("aaaaaaaaaa", 9, "Ten same characters");
        testCase("ababababab", 9, "Alternating pattern");
        testCase("aabbccddee", 5, "Pairs pattern");
        testCase("abcabcabc", 0, "Repeating abc");
        testCase("aaabbbaaabbb", 10, "Blocks of same");
        testCase("xyzzyxyzzy", 4, "Pattern with zz");
    }
    
    private void runPerformanceTests() {
        System.out.println("\n═══════════════════════════════════════════════════════════");
        System.out.println("PERFORMANCE TESTS");
        System.out.println("═══════════════════════════════════════════════════════════");
        
        int[] sizes = {100, 500, 1000, 5000, 10000, 50000, 100000, 300000};
        
        System.out.printf("%-10s %-15s %-15s %-15s%n", "Size", "Count", "Time(ms)", "Status");
        System.out.println("─────────────────────────────────────────────────────────");
        
        for (int size : sizes) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < size; i++) {
                sb.append((char)('a' + rand.nextInt(3))); // Use a, b, c
            }
            String largeData = sb.toString();
            
            long start = System.nanoTime();
            int result = CountSpecialSubStrings.countSpecialSubstrings(largeData);
            long end = System.nanoTime();
            double timeMs = (end - start) / 1_000_000.0;
            
            String status = timeMs < 1000 ? "✓ Fast" : timeMs < 5000 ? "○ OK" : "✗ Slow";
            System.out.printf("%-10d %-15d %-15.2f %-15s%n", size, result, timeMs, status);
        }
    }
    
    private void testCase(String input, int expected, String description) {
        totalTests++;
        int actual = CountSpecialSubStrings.countSpecialSubstrings(input);
        
        if (expected == -1) {
            // For complex cases, verify against brute force
            expected = countSpecialBruteForce(input);
        }
        
        boolean passed = (actual == expected);
        
        if (passed) {
            passedTests++;
            System.out.printf("✓ PASS: %-20s | Input: %-15s | Result: %d%n", 
                description, input.length() <= 15 ? "'" + input + "'" : "(len=" + input.length() + ")", actual);
        } else {
            failedTests++;
            failedCases.add(new TestCase(input, expected, actual));
            System.out.printf("✗ FAIL: %-20s | Input: %-15s | Expected: %d, Got: %d%n", 
                description, input.length() <= 15 ? "'" + input + "'" : "(len=" + input.length() + ")", 
                expected, actual);
        }
    }
    
    private void printSummary() {
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║                      TEST SUMMARY                          ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");
        
        System.out.println("Total Tests:  " + totalTests);
        System.out.println("Passed:       " + passedTests + " (" + 
            String.format("%.1f%%", 100.0 * passedTests / totalTests) + ")");
        System.out.println("Failed:       " + failedTests + " (" + 
            String.format("%.1f%%", 100.0 * failedTests / totalTests) + ")");
        
        if (failedTests > 0) {
            System.out.println("\n╔════════════════════════════════════════════════════════════╗");
            System.out.println("║                   FAILED TEST DETAILS                      ║");
            System.out.println("╚════════════════════════════════════════════════════════════╝");
            
            int displayCount = Math.min(10, failedCases.size());
            for (int i = 0; i < displayCount; i++) {
                TestCase tc = failedCases.get(i);
                System.out.println("\nFailed Test #" + (i + 1) + ":");
                System.out.println("  Input:    '" + tc.input + "'");
                System.out.println("  Expected: " + tc.expected);
                System.out.println("  Actual:   " + tc.actual);
                
                // Show what the brute force found
                System.out.println("  Special substrings:");
                for (int start = 0; start < tc.input.length(); start++) {
                    for (int end = start + 2; end <= tc.input.length(); end++) {
                        String sub = tc.input.substring(start, end);
                        if (isSpecial(sub)) {
                            System.out.println("    [" + start + ":" + end + "] = '" + sub + "'");
                        }
                    }
                }
            }
            
            if (failedCases.size() > 10) {
                System.out.println("\n... and " + (failedCases.size() - 10) + " more failed cases");
            }
        }
        
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        if (failedTests == 0) {
            System.out.println("║          ✓✓✓ ALL TESTS PASSED! ✓✓✓                        ║");
        } else {
            System.out.println("║          ✗✗✗ SOME TESTS FAILED ✗✗✗                        ║");
        }
        System.out.println("╚════════════════════════════════════════════════════════════╝");
    }
    
    private static boolean isSpecial(String s) {
        int n = s.length();
        if (n < 2) return false;
        
        if (n == 2) {
            return s.charAt(0) == s.charAt(1);
        }
        
        if (s.charAt(0) != s.charAt(n - 1)) return false;
        
        String middle = s.substring(1, n - 1);
        if (middle.isEmpty()) return false;
        
        char firstMiddleChar = middle.charAt(0);
        for (char c : middle.toCharArray()) {
            if (c != firstMiddleChar) return false;
        }
        
        return true;
    }
    
    private int countSpecialBruteForce(String genome) {
        int count = 0;
        for (int i = 0; i < genome.length(); i++) {
            for (int j = i + 2; j <= genome.length(); j++) {
                if (isSpecial(genome.substring(i, j))) {
                    count++;
                }
            }
        }
        return count;
    }
}
