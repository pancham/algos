package fastprep.amazon;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Random;

// https://www.fastprep.io/problems/amazon-get-minimum-dock-bays
public class MnimumDockBays {
    
    public static int getMinimumDockBays(int[] truckCargoSize, long maxTurnaroundTime) {
        int n = truckCargoSize.length;
        
        // Binary search on the number of dock bays
        int left = 1;
        int right = n; // At most, we need n dock bays (one per truck)
        int result = n;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (canUnloadWithinTime(truckCargoSize, maxTurnaroundTime, mid)) {
                result = mid;
                right = mid - 1; // Try to find fewer dock bays
            } else {
                left = mid + 1; // Need more dock bays
            }
        }
        
        return result;
    }
    
    // Check if we can unload all trucks with 'dockBays' number of bays within maxTime
    private static boolean canUnloadWithinTime(int[] truckCargoSize, long maxTime, int dockBays) {
        // Min heap to track when each dock bay becomes available
        PriorityQueue<Long> dockFinishTimes = new PriorityQueue<>();
        
        // Initialize all dock bays as available at time 0
        for (int i = 0; i < dockBays; i++) {
            dockFinishTimes.offer(0L);
        }
        
        long maxFinishTime = 0;
        
        // Process each truck in order
        for (int cargoSize : truckCargoSize) {
            // Get the dock bay that becomes available earliest
            long earliestAvailable = dockFinishTimes.poll();
            
            // This dock bay will finish at earliestAvailable + cargoSize
            long finishTime = earliestAvailable + cargoSize;
            maxFinishTime = Math.max(maxFinishTime, finishTime);
            
            // Put the dock bay back with its new finish time
            dockFinishTimes.offer(finishTime);
        }
        
        // Check if all trucks can be unloaded within the time limit
        return maxFinishTime <= maxTime;
    }
    
    public static void main(String[] args) {
        System.out.println("=== Basic Test Cases ===");
        
        // Test 1: Example from problem
        int[] test1 = {3, 4, 3, 2, 3};
        long time1 = 8;
        System.out.println("Test 1: " + getMinimumDockBays(test1, time1));
        System.out.println("Expected: 3");
        System.out.println("Explanation: With 3 bays:");
        System.out.println("  Bay 1: Truck 0 (0-3), Truck 3 (3-5), Truck 4 (5-8)");
        System.out.println("  Bay 2: Truck 1 (0-4)");
        System.out.println("  Bay 3: Truck 2 (0-3)");
        
        // Test 2: All trucks can be handled by one bay
        int[] test2 = {1, 1, 1, 1, 1};
        long time2 = 10;
        System.out.println("\nTest 2: " + getMinimumDockBays(test2, time2));
        System.out.println("Expected: 1 (total time = 5)");
        
        // Test 3: Each truck needs its own bay
        int[] test3 = {5, 5, 5, 5};
        long time3 = 5;
        System.out.println("\nTest 3: " + getMinimumDockBays(test3, time3));
        System.out.println("Expected: 4 (all must run in parallel)");
        
        // Test 4: Two bays needed
        int[] test4 = {2, 3, 4};
        long time4 = 6;
        System.out.println("\nTest 4: " + getMinimumDockBays(test4, time4));
        System.out.println("Expected: 2");
        
        // Test 5: Single truck
        int[] test5 = {10};
        long time5 = 10;
        System.out.println("\nTest 5: " + getMinimumDockBays(test5, time5));
        System.out.println("Expected: 1");
        
        // Test 6: Large cargo sizes
        int[] test6 = {100, 200, 150, 180};
        long time6 = 300;
        System.out.println("\nTest 6: " + getMinimumDockBays(test6, time6));
        
        System.out.println("\n=== Detailed Simulation Test ===");
        simulateAndPrint(test1, time1, 3);
        
        System.out.println("\n=== Random Testing ===");
        Random rand = new Random(42);
        boolean allPassed = true;
        
        for (int test = 0; test < 100; test++) {
            int n = rand.nextInt(20) + 1;
            int[] cargo = new int[n];
            long totalTime = 0;
            for (int i = 0; i < n; i++) {
                cargo[i] = rand.nextInt(10) + 1;
                totalTime += cargo[i];
            }
            
            // Test with various time limits
            long timeLimit = totalTime / 2 + rand.nextInt((int)(totalTime / 2) + 1);
            
            int result = getMinimumDockBays(cargo, timeLimit);
            
            // Verify the result
            boolean canDo = canUnloadWithinTime(cargo, timeLimit, result);
            boolean cantDoWithLess = result == 1 || !canUnloadWithinTime(cargo, timeLimit, result - 1);
            
            if (!canDo || !cantDoWithLess) {
                System.out.println("FAILED: cargo=" + Arrays.toString(cargo) + 
                    ", time=" + timeLimit + ", result=" + result);
                allPassed = false;
            }
        }
        
        if (allPassed) {
            System.out.println("✓ All 100 random tests passed!");
        }
        
        System.out.println("\n=== Performance Testing ===");
        int[] sizes = {100, 500, 1000, 5000, 10000};
        
        for (int size : sizes) {
            int[] largeCargo = new int[size];
            for (int i = 0; i < size; i++) {
                largeCargo[i] = rand.nextInt(100) + 1;
            }
            long largeTime = size * 50L; // Reasonable time limit
            
            long start = System.nanoTime();
            int result = getMinimumDockBays(largeCargo, largeTime);
            long end = System.nanoTime();
            
            System.out.printf("Size %d: result=%d, time=%.2f ms%n", 
                size, result, (end - start) / 1_000_000.0);
        }
        
        System.out.println("\n=== Edge Cases ===");
        
        // Edge case: Very tight time constraint
        int[] edge1 = {5, 5, 5};
        System.out.println("Tight constraint (time=5): " + getMinimumDockBays(edge1, 5));
        
        // Edge case: Very loose time constraint
        int[] edge2 = {1, 2, 3, 4, 5};
        System.out.println("Loose constraint (time=1000): " + getMinimumDockBays(edge2, 1000));
        
        // Edge case: All same size
        int[] edge3 = {10, 10, 10, 10, 10};
        System.out.println("All same (size=10, time=25): " + getMinimumDockBays(edge3, 25));
        
        System.out.println("\n=== All Tests Completed! ===");
    }
    
    // Helper method to simulate and print the assignment
    private static void simulateAndPrint(int[] truckCargoSize, long maxTime, int dockBays) {
        System.out.println("Simulating with " + dockBays + " dock bays:");
        
        PriorityQueue<DockBay> docks = new PriorityQueue<>((a, b) -> 
            Long.compare(a.finishTime, b.finishTime));
        
        for (int i = 0; i < dockBays; i++) {
            docks.offer(new DockBay(i, 0));
        }
        
        for (int i = 0; i < truckCargoSize.length; i++) {
            DockBay dock = docks.poll();
            long startTime = dock.finishTime;
            long endTime = startTime + truckCargoSize[i];
            
            System.out.printf("  Truck %d (cargo=%d) -> Bay %d [%d-%d]%n", 
                i, truckCargoSize[i], dock.id, startTime, endTime);
            
            dock.finishTime = endTime;
            docks.offer(dock);
        }
        
        long maxFinish = 0;
        while (!docks.isEmpty()) {
            maxFinish = Math.max(maxFinish, docks.poll().finishTime);
        }
        
        System.out.println("  Max finish time: " + maxFinish + 
            " (limit: " + maxTime + ") -> " + 
            (maxFinish <= maxTime ? "✓ VALID" : "✗ INVALID"));
    }
    
    static class DockBay {
        int id;
        long finishTime;
        
        DockBay(int id, long finishTime) {
            this.id = id;
            this.finishTime = finishTime;
        }
    }
}
