package heap.mergesorted;

import java.util.PriorityQueue;

public class MergeKSortedArrays {

    public static int[] mergeKArrays(int[][] arrays) {
        int totalLength = 0;
        for (int[] array : arrays) {
            totalLength += array.length;
        }

        int[] result = new int[totalLength];
        int index = 0;

        PriorityQueue<ArrayElement> minHeap = new PriorityQueue<>();
        for (int i = 0; i < arrays.length; i++) {
            if (arrays[i].length > 0) {
                minHeap.add(new ArrayElement(i, 0, arrays[i][0]));
            }
        }

        while (!minHeap.isEmpty()) {
            ArrayElement element = minHeap.poll();
            result[index++] = element.value;

            if (element.colIndex + 1 < arrays[element.arrayIndex].length) {
                minHeap.add(new ArrayElement(
                        element.arrayIndex,
                        element.colIndex + 1,
                        arrays[element.arrayIndex][element.colIndex + 1]
                ));
            }
        }

        return result;
    }

    static class ArrayElement implements Comparable<ArrayElement> {
        int arrayIndex;
        int colIndex;
        int value;

        public ArrayElement(int arrayIndex, int colIndex, int value) {
            this.arrayIndex = arrayIndex;
            this.colIndex = colIndex;
            this.value = value;
        }

        @Override
        public int compareTo(ArrayElement other) {
            return Integer.compare(this.value, other.value);
        }
    }

    public static void main(String[] args) {
        int[][] arrays = {
                { 2, 6, 12, 34 },
                { 1, 9, 20, 1000 },
                { 23, 34, 90, 2000 }
        };

        int[] mergedArray = mergeKArrays(arrays);

        for (int num : mergedArray) {
            System.out.print(num + " ");
        }
    }
}
