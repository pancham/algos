package mincores;

import java.util.*;

public class MinCores {
    public static void main(String[] args) {
//        System.out.println("Hello");

        // [1, 3, 4], end = [3, 5, 6]
        List<List<Integer>> times = List.of(
                List.of(1, 3),
                List.of(3, 5),
                List.of(4, 6)
        );

        int ret = minCores(times);
        System.out.println(ret);
    }

    public static int minCores(List<List<Integer>> times) {
        List<Integer> startArr = new ArrayList<>();
        List<Integer> endArr = new ArrayList<>();

        for (List<Integer> pair: times) {
            startArr.add(pair.get(0));
            endArr.add(pair.get(1));
        }

        Collections.sort(startArr);
        Collections.sort(endArr);

        int max = 0;
        int current = 0;
        int j = 0;

        for (Integer integer : startArr) {
            if (integer <= endArr.get(j)) {
                current++;
            } else {
                j++;
                if (current > max) {
                    max = current;
                }
                current = 0;
            }
        }

        return max;
    }


}