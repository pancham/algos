package test;

import java.util.*;

public class Test {

    public static void main(String[] args) {

        List<Integer> list = List.of(3);
//        List<Integer> list = List.of(7,5,1,9,1);

        long m = findMaximumMinimum(list, 1);
        System.out.println(m);
    }

    public static long findMaximumMinimum(List<Integer> parcels, long extra_parcels) {
        if (parcels.size() == 0) {
            return extra_parcels;
        }
        if (parcels.size() == 1) {
            return parcels.get(0) + extra_parcels;
        }

        int max = Collections.max(parcels);

        long remaining = extra_parcels;

        for (int parcel: parcels) {
            if (parcel < max) {
                int adjust = (max - parcel);
                remaining -= adjust;
            }

            if (remaining <= 0) {
                break;
            }
        }

        // distribute remaining equally
        long increment = 0;
        if (remaining > 0) {
            increment += (remaining/parcels.size() + 1);
        }

        return max + increment;
    }

    public static long findMaximumMinimum2(List<Integer> parcels, long extra_parcels) {
        int max = Collections.max(parcels);

        int[] p = parcels.stream().mapToInt(Integer::intValue).toArray();
        long remaining = extra_parcels;

        for (int i = 0; i < p.length; i++) {
            if (p[i] < max) {
                int adjust = (max - p[i]);
                p[i] += adjust;
                remaining -= adjust;
            }

            if (remaining <= 0) {
                break;
            }
        }

        long result = 0;
        while (remaining > 0) {
            for (int i = 0; i < p.length; i++) {
                p[i] += 1;
                remaining -= 1;
                if (remaining <= 0) {
                    result = p[i];
                    break;
                }
             }
        }

        return result;
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
