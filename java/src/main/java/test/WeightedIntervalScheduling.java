package test;

import java.util.*;

public class WeightedIntervalScheduling {

    static class Call {
        int start, end, volume;

        Call(int start, int duration, int volume) {
            this.start = start;
            this.end = start + duration;
            this.volume = volume;
        }
    }

    public static int phoneCalls(List<Integer> start, List<Integer> duration, List<Integer> volume) {
        int n = start.size();
        Call[] calls = new Call[n];
        for (int i = 0; i < n; i++) {
            calls[i] = new Call(start.get(i), duration.get(i), volume.get(i));
        }

        // Sort by end time
        Arrays.sort(calls, Comparator.comparingInt(c -> c.end));

        // TreeMap: key = end time, value = max volume up to that time
        TreeMap<Integer, Integer> map = new TreeMap<>();
        map.put(0, 0);  // Base case: 0 volume at time 0

        int maxVolume = 0;

        for (Call call : calls) {
            // -1 to avoid overlaps since floorKey checks for less than or equal
            Integer prevKey = map.floorKey(call.start - 1);
            int prevVolume = (prevKey != null) ? map.get(prevKey) : 0;

            int currVolume = prevVolume + call.volume;
            if (currVolume > maxVolume) {
                maxVolume = currVolume;
                map.put(call.end, maxVolume);
            }
        }

        return maxVolume;
    }

    public static int phoneCalls2(List<Integer> start, List<Integer> duration, List<Integer> volume) {
        int n = start.size();
        Call[] calls = new Call[n];

        for (int i = 0; i < n; i++) {
            calls[i] = new Call(start.get(i), duration.get(i), volume.get(i));
        }

        // Sort calls by end time
        Arrays.sort(calls, Comparator.comparingInt(c -> c.end));

        // dp[i] will store max volume using first i calls
        int[] dp = new int[n];
        dp[0] = calls[0].volume;

        for (int i = 1; i < n; i++) {
            int incl = calls[i].volume;
            int l = binarySearch(calls, i);
            if (l != -1) {
                incl += dp[l];
            }
            dp[i] = Math.max(incl, dp[i - 1]);
        }

        return dp[n - 1];
    }

    // Binary search to find the last call before calls[i] that doesn't overlap
    private static int binarySearch(Call[] calls, int index) {
        int low = 0, high = index - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            if (calls[mid].end < calls[index].start) {
                if (calls[mid + 1].end < calls[index].start) {
                    low = mid + 1;
                } else {
                    return mid;
                }
            } else {
                high = mid - 1;
            }
        }
        return -1;
    }

    // Test method
    public static void main(String[] args) {
//        List<Integer> start = Arrays.asList(10, 5, 15, 18, 30);
//        List<Integer> duration = Arrays.asList(30, 12, 20, 35, 35);
//        List<Integer> volume = Arrays.asList(50, 51, 20, 25, 10);

        List<Integer> start = Arrays.asList(1,2,4);
        List<Integer> duration = Arrays.asList(2,2,1);
        List<Integer> volume = Arrays.asList(1,2,3);

//        List<Integer> start = Arrays.asList(1,10,100);
//        List<Integer> duration = Arrays.asList(1,10,100);
//        List<Integer> volume = Arrays.asList(1,10,100);

        int result = phoneCalls(start, duration, volume);
        System.out.println("Max Order Volume: " + result);  // Expected: 76

        result = phoneCalls2(start, duration, volume);
        System.out.println("Max Order Volume2: " + result);
    }
}

