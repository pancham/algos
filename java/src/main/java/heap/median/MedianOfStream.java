package heap.median;

import java.util.Comparator;
import java.util.PriorityQueue;

public class MedianOfStream {
    /**
     * Refer https://www.baeldung.com/java-stream-integers-median-using-heap
     *
     * The solution uses 2 heaps:
     * A min-heap that contains the larger half of the elements, with the minimum element at the root
     * A max-heap that contains the smaller half of the elements, with the maximum element at the root
     */

    // larger half of elements
    private final PriorityQueue<Integer> minHeap;

    // smaller half of elements
    private final PriorityQueue<Integer> maxHeap;


    MedianOfStream() {
        minHeap = new PriorityQueue<>();
        maxHeap = new PriorityQueue<>(Comparator.reverseOrder());
    }

    void add(int num) {
        if (minHeap.size() == maxHeap.size()) {
            maxHeap.offer(num);
            minHeap.offer(maxHeap.poll());
        } else {
            minHeap.offer(num);
            maxHeap.offer(minHeap.poll());
        }
    }

    double getMedian() {
        double median;
        if (minHeap.size() > maxHeap.size()) {
            median = minHeap.peek();
        } else {
            median = (minHeap.peek() + maxHeap.peek()) / 2.0;
        }
        return median;
    }

    public static void main(String[] args) {
        MedianOfStream m = new MedianOfStream();

        m.add(1);
        m.add(2);
        m.add(3);
        m.add(4);
        System.out.println("Median " + m.getMedian());

        m.add(6);
        System.out.println("Median " + m.getMedian());
    }
}
