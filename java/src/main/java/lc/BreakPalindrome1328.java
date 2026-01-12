package lc;

public class BreakPalindrome1328 {
    public String breakPalindrome(String palindrome) {
        if (palindrome.length() == 1) return "";

        char[] carr = palindrome.toCharArray();

        int mid = carr.length % 2 == 1 ? carr.length/2 : -1;


        for (int i = (int)'a'; i <= (int)'z'; i++) {
            char c = (char) i;
            for (int j = 0; j < carr.length/2; j++) {
                if (carr[j] != c) { 
                    if (carr[j] < c) { // cases such as "aa" or "aaaa"
                        // find the next character which is greater than c
                        for (int k = 0; k < carr.length; k++) {
                            if (k == mid) continue; // for strings scuch as "ada" - changing the middle char will always result in a palindrome
                            if (carr[k] > c) {
                                carr[k] = c;
                                return new String(carr);
                            }
                        }
                        carr[carr.length - 1] = c;
                        return new String(carr);
                    }

                    carr[j] = c;
                    return new String(carr);
                }
            }
        }

        return "";
    }

    public static void main(String[] args) {
        BreakPalindrome1328 bp = new BreakPalindrome1328();
        System.out.println(bp.breakPalindrome("abccba")); // "aaccba"
        System.out.println(bp.breakPalindrome("a")); // ""
        System.out.println(bp.breakPalindrome("aa")); // "ab"
        System.out.println(bp.breakPalindrome("aaa")); // "aab"
        System.out.println(bp.breakPalindrome("aba")); // "abb"
        System.out.println(bp.breakPalindrome("abba")); // "aaba"
        System.out.println(bp.breakPalindrome("aaaa")); // "aaab"
    }
}
