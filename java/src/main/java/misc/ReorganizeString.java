package misc;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * https://www.youtube.com/watch?v=2g_b1aYTHeg
 * Given a string s, rearrange the characters of s so that any two adjacent characters are not the same.
 *
 * Return any possible rearrangement of s or return "" if not possible.
 *
 *
 *
 * Example 1:
 *
 * Input: s = "aab"
 * Output: "aba"
 * Example 2:
 *
 * Input: s = "aaab"
 * Output: ""
 *
 *
 * Constraints:
 *
 * 1 <= s.length <= 500
 * s consists of lowercase English letters.
 */
public class ReorganizeString {
    public static void main(String[] args) {
//        String s = "aab";
//        String s = "aaab";
        String s = "cxmwmmm";

        ReorganizeString r = new ReorganizeString();
        String res = r.reorganizeString(s);
        System.out.println("res: " + res);
    }

    class Pair {
        Character c;
        int count;

        public Pair(Character c, int count) {
            this.c = c;
            this.count = count;
        }
    }

    public String reorganizeString(String str) {
        Map<Character, Integer> m1 = new HashMap<>();

        for (Character c: str.toCharArray()) {
            m1.compute(c, (k, v) -> (v == null) ? 1 : v + 1);
        }

        // create a max heap
        PriorityQueue<Pair> mh = new PriorityQueue<>((a, b) -> b.count - a.count);
        for (Map.Entry<Character, Integer> e: m1.entrySet()) {
            mh.add(new Pair(e.getKey(), e.getValue()));
        }

        StringBuffer s = new StringBuffer();
        Pair prev = null;

        while (mh.size() > 0) {
            // get the most frequent item
            Pair cur = mh.poll();
            s.append(cur.c);
            cur.count--;   //

            // add back only if previous character is left, i.e. prev.count > 0
            if (prev != null && prev.count > 0) {
                mh.add(prev);
            }

            // If heap is empty, but still character instances are left to be used - then it is not possible
            // to reorganize
            if (mh.size() == 0 && cur.count > 0) {
                return "";
            }

            prev = cur;
        }

        return s.toString();
    }

}
