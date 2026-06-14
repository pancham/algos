package fastprep.amazon;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

// https://www.fastprep.io/problems/amazon-split-prefix-suffix
public class SplitPrefixSuffix {

    public static void main(String[] args) {
        String categories = "abbcac";
        int k = 1;
        test(categories, k);
        

        for (int i = 0; i < 100; i++) {
            String rs = generateRandomString((int) Math.pow(10, 5), 26);
            test(rs, k);
        }
    }

    public static void test(String categories, int k) {
        SplitPrefixSuffix s = new SplitPrefixSuffix();
        long start = System.currentTimeMillis();
        int c1 = s.splitPrefixSuffix(categories, k);
        long end = System.currentTimeMillis();
        System.out.println("Method1 Time: " + (end - start) + " ms");

        start = System.currentTimeMillis();
        int c2 = s.splitPrefixSuffix2(categories, k);
        end = System.currentTimeMillis();
        System.out.println("Method2 Time: " + (end - start) + " ms");

        if (c1 == c2) {
            // System.out.println("Match: " + c1);
        } else {
            System.out.println("Mismatch: Method1 = " + c1 + ", Method2 = " + c2);
        }
    }

    public int splitPrefixSuffix(String categories, int k) {
        char[] cats = categories.toCharArray();

        Map<Character, Integer> suffixCount = new java.util.HashMap<>();

        for (int i = 0; i < cats.length; i++) {
            suffixCount.put(cats[i], suffixCount.getOrDefault(cats[i], 0) + 1);
        }

        int count = 0;
        Set<Character> sharedCats = new HashSet<>();
        for (int i = 0; i < cats.length; i++) {
            char c = cats[i];
            suffixCount.put(c, suffixCount.get(c) - 1);
            if (suffixCount.get(c) == 0) {
                suffixCount.remove(c);
            }

            if (suffixCount.containsKey(c)) {
                sharedCats.add(c);
            } else {
                sharedCats.remove(c);
            }

            if (sharedCats.size() > k) {
                count++;
            }
        }

        return count;
    }

    public int splitPrefixSuffix2(String categories, int k) {
        int n = categories.length();
        Map<Character, Integer> prefixCount = new HashMap<>();
        Map<Character, Integer> suffixCount = new HashMap<>();
        
        // Initialize suffix with all characters
        for (char c : categories.toCharArray()) {
            suffixCount.put(c, suffixCount.getOrDefault(c, 0) + 1);
        }
        
        int count = 0;
        
        // Try splits at positions 0 to n-2 (to keep suffix non-empty)
        for (int i = 0; i < n - 1; i++) {
            char c = categories.charAt(i);
            
            // Move character from suffix to prefix
            prefixCount.put(c, prefixCount.getOrDefault(c, 0) + 1);
            suffixCount.put(c, suffixCount.get(c) - 1);
            if (suffixCount.get(c) == 0) {
                suffixCount.remove(c);
            }
            
            // Count shared categories
            int shared = 0;
            for (char key : prefixCount.keySet()) {
                if (suffixCount.containsKey(key)) {
                    shared++;
                }
            }
            
            if (shared > k) {
                count++;
            }
        }
        
        return count;
    }

    public static String generateRandomString(int length, int uniqueChars) {
        Random rand = new Random();
        StringBuilder sb = new StringBuilder();
        
        // Ensure we have at least 'uniqueChars' different characters
        char[] alphabet = new char[uniqueChars];
        for (int i = 0; i < uniqueChars; i++) {
            alphabet[i] = (char)('a' + i);
        }
        
        for (int i = 0; i < length; i++) {
            sb.append(alphabet[rand.nextInt(uniqueChars)]);
        }
        
        return sb.toString();
    }
}
