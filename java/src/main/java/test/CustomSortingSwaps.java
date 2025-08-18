package test;
import java.util.*;

public class CustomSortingSwaps {
    public static int howManySwaps2(List<Integer> arr) {
        int[] ar = new int[arr.size()];
        for (int i = 0; i < arr.size(); i++) {
            ar[i] = arr.get(i);
        }
        int n = ar.length;
        int swaps = 0;

        boolean swapped;
        do {
            swapped = false;
            for (int i = 0; i < n - 1; i++) {
                for (int j = i + 1; j < n; j++) {
                    if (ar[i] > ar[j]) {
                        // Swap arr[i] and arr[j]
                        int temp = ar[i];
                        ar[i] = ar[j];
                        ar[j] = temp;

                        swaps++;
                        swapped = true;
                        break; // Restart search after swap
                    }
                }
                if (swapped) break;
            }
        } while (swapped);

        return swaps;
    }

    public static int howManySwaps(List<Integer> arr) {
        int[] ar = new int[arr.size()];
        for (int i = 0; i < arr.size(); i++) {
            ar[i] = arr.get(i);
        }
        int n = ar.length;
        int swaps = 0;

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (ar[i] > ar[j]) {
                    // Swap elements
                    int temp = ar[i];
                    ar[i] = ar[j];
                    ar[j] = temp;
                    swaps++;
                }
            }
        }

        return swaps;
    }

    public static void main(String[] args) {
//        int[] arr = {2, 7, 12};
        List<Integer> arr = List.of(7,1,2);
        System.out.println(howManySwaps(arr)); // Output: 4
    }
}
