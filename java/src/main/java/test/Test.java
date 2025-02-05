package test;

import java.util.*;

public class Test {

    public static void main(String[] args) {

        System.out.println("Hello");

        System.out.println("Min " + minGroupVariance(new int[] {4,2,3,4,1,2,3,4}) );
    }

    public static int minGroupVariance(int[] arr) {
        class Data {
            int index;
            int numOfOccurrences;

            public Data(int index, int numOfOccurences) {
                this.index = index;
                this.numOfOccurrences = numOfOccurences;
            }
        }

        Map<Integer, Data> map = new HashMap<>();
        int result = Integer.MAX_VALUE;
        for (int i = 0; i < arr.length; i++) {
            if (!map.containsKey(arr[i])) {
                map.put(arr[i], new Data(i, 1));
            } else {
                Data data = map.get(arr[i]);
                data.numOfOccurrences++;
                int len = i - data.index + 1;

                if (result > len - data.numOfOccurrences) {
                    result = len - data.numOfOccurrences;
                }

                // reset the values for current number as the same number may be repeated: [4,2,3,4,1,2,3,4]
                data.index = i;
                data.numOfOccurrences = 1;
            }
        }

        return result;
    }
}
