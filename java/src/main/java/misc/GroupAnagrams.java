package misc;

import java.math.BigInteger;
import java.util.*;

import prime.Prime;

/**
 * Given an array of strings strs, group the anagrams together. You can return the answer in any order.
 *
 *
 *
 * Example 1:
 *
 * Input: strs = ["eat","tea","tan","ate","nat","bat"]
 *
 * Output: [["bat"],["nat","tan"],["ate","eat","tea"]]
 */
public class GroupAnagrams {

    public static void main(String[] args) {
//        List<String> strs = List.of("eat","tea","tan","ate","nat","bat");
//        List<String> strs = List.of("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa","aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        List<String> strs = List.of("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaab");
        List<List<String>> groups = groupAnagrams1(strs);

        System.out.println(groups);
    }

    static List<List<String>> groupAnagrams1(List<String> strs) {
        Map<List<Integer>, List<String>> map = new HashMap<>();
        for (String s: strs) {
            List<Integer> list = new ArrayList<>(26);
            for (int i = 0; i < 26; i++) {
                list.add(0);
            }
            for (char c : s.toCharArray()) {
                int index = (int)c -(int)('a');
                list.set(index, list.get(index) + 1);
            }

            if (!map.containsKey(list)) {
                map.put(list, new ArrayList<>());
            }
            map.get(list).add(s);
        }

        List<List<String>> ret = new ArrayList<>();
        ret.addAll(map.values());

        return ret;
    }

    static List<List<String>> groupAnagrams(List<String> strs) {
        Map<BigInteger, List<String>> map = new HashMap<>();

        // Generate a prime for each character in the alphabet
        List<Integer> primes = Prime.generateNPrimes(26);

        // Note that product of prime numbers is always unique, so we map each character to a prime number
        // and get a unique number for the set of characters
        for (String s: strs) {
            BigInteger p = new BigInteger("1");
            for (char c: s.toCharArray()) {
                BigInteger i = new BigInteger(primes.get(c -(int)('a')).toString());
                p = p.multiply(i);
            }

            if (!map.containsKey(p)) {
                map.put(p, new ArrayList<>());
            }
            map.get(p).add(s);
        }


        List<List<String>> ret = new ArrayList<>();
        ret.addAll(map.values());

        return ret;
    }
}
