package mincores;

import java.util.*;

public class MinCores {
    public static void main(String[] args) {
//        System.out.println("Hello");

        // [1, 3, 4], end = [3, 5, 6]
//        List<List<Integer>> times = List.of(
//                List.of(1, 3),
//                List.of(3, 5),
//                List.of(4, 6)
//        );

        List<List<Integer>> times = List.of(
                List.of(1, 5),
                List.of(8, 9),
                List.of(8, 9)
        );

        int ret = minCores(times);
        System.out.println(ret);
    }

    public static int minCores(List<List<Integer>> times) {
        SortedMap<Integer, Integer> map = new TreeMap<>();

        for (List<Integer> pair: times) {
            map.put(pair.get(0), map.getOrDefault(pair.get(0), 0) + 1);    // +1 for each start time
            map.put(pair.get(1), map.getOrDefault(pair.get(1), 0) -1);   // -1 for each end time
        }

        int count = 0, ans = 0;
        for (int value: map.values()) {
            count += value;
            ans = Math.max(ans, cur);
        }

        return ans;
    }

    public static int minCores2(List<List<Integer>> times) {
        List<Integer> startArr = new ArrayList<>();
        List<Integer> endArr = new ArrayList<>();

        for (List<Integer> pair: times) {
            startArr.add(pair.get(0));
            endArr.add(pair.get(1));
        }

        Collections.sort(startArr);
        Collections.sort(endArr);

        int min = 1; // minimum 1 is always required
        int numCores = 0;
        int end = 0;

        for (Integer start : startArr) {
            while (start >= endArr.get(j)) {
                end++; // finished tasks
                numCores--;
            }
            numCores++;
            if (numCores > min) {
                min = numCores;
            }
        }

        return min;
    }


}