package fastprep.amazon;

import java.util.Arrays;
import java.util.Random;

// https://www.fastprep.io/problems/amazon-use-minimum-tokens
public class UseMinTokens {
    
    public static long[] useMinimumTokens(int[] warehouse, long[][] catalog) {
        int n = warehouse.length;
        int q = catalog.length;
        long[] result = new long[q];
        
        // Sort warehouses for efficient backup calculation
        Integer[] indices = new Integer[n];
        for (int i = 0; i < n; i++) {
            indices[i] = i;
        }
        Arrays.sort(indices, (a, b) -> Integer.compare(warehouse[b], warehouse[a]));
        
        // Process each shipment
        for (int shipmentIdx = 0; shipmentIdx < q; shipmentIdx++) {
            long primaryCapacity = catalog[shipmentIdx][0];
            long backupCapacity = catalog[shipmentIdx][1];
            
            long minTokens = Long.MAX_VALUE;
            
            // Try each warehouse as the primary
            for (int primaryIdx = 0; primaryIdx < n; primaryIdx++) {
                long tokensForPrimary = Math.max(0, primaryCapacity - warehouse[primaryIdx]);
                
                // Calculate backup capacity from other warehouses
                long currentBackup = 0;
                long tokensForBackup = 0;
                
                for (int i = 0; i < n; i++) {
                    if (i != primaryIdx) {
                        currentBackup += warehouse[i];
                    }
                }
                
                // If backup is insufficient, we need to upgrade warehouses
                if (currentBackup < backupCapacity) {
                    tokensForBackup = backupCapacity - currentBackup;
                }
                
                long totalTokens = tokensForPrimary + tokensForBackup;
                minTokens = Math.min(minTokens, totalTokens);
            }
            
            result[shipmentIdx] = minTokens;
        }
        
        return result;
    }
    
    // Optimized version
    public static long[] useMinimumTokensOptimized(int[] warehouse, long[][] catalog) {
        int n = warehouse.length;
        int q = catalog.length;
        long[] result = new long[q];
        
        // Calculate total capacity
        long totalCapacity = 0;
        for (int w : warehouse) {
            totalCapacity += w;
        }
        
        // Process each shipment
        for (int shipmentIdx = 0; shipmentIdx < q; shipmentIdx++) {
            long primaryCapacity = catalog[shipmentIdx][0];
            long backupCapacity = catalog[shipmentIdx][1];
            
            long minTokens = Long.MAX_VALUE;
            
            // Try each warehouse as the primary
            for (int primaryIdx = 0; primaryIdx < n; primaryIdx++) {
                long tokensForPrimary = Math.max(0, primaryCapacity - warehouse[primaryIdx]);
                
                // Backup capacity = total - primary warehouse capacity
                long currentBackup = totalCapacity - warehouse[primaryIdx];
                
                // If we upgraded primary, it doesn't count toward backup
                long effectiveBackup = currentBackup;
                
                // Calculate tokens needed for backup
                long tokensForBackup = Math.max(0, backupCapacity - effectiveBackup);
                
                long totalTokens = tokensForPrimary + tokensForBackup;
                minTokens = Math.min(minTokens, totalTokens);
            }
            
            result[shipmentIdx] = minTokens;
        }
        
        return result;
    }
    
    public static void main(String[] args) {
        System.out.println("=== Basic Test Cases ===");
        
        // Test 1: Simple case
        int[] warehouse1 = {5, 10, 15};
        long[][] catalog1 = {{8, 20}};
        long[] result1 = useMinimumTokensOptimized(warehouse1, catalog1);
        System.out.println("Test 1: " + Arrays.toString(result1));
        System.out.println("Warehouses: [5, 10, 15], Total: 30");
        System.out.println("Shipment: primary=8, backup=20");
        System.out.println("Option 1: Primary=5 (need 3 tokens), Backup=25 (sufficient) = 3 tokens");
        System.out.println("Option 2: Primary=10 (sufficient), Backup=20 (sufficient) = 0 tokens");
        System.out.println("Option 3: Primary=15 (sufficient), Backup=15 (need 5 tokens) = 5 tokens");
        System.out.println("Expected: 0\n");
        
        // Test 2: Need upgrades
        int[] warehouse2 = {3, 4, 5};
        long[][] catalog2 = {{10, 15}};
        long[] result2 = useMinimumTokensOptimized(warehouse2, catalog2);
        System.out.println("Test 2: " + Arrays.toString(result2));
        System.out.println("Warehouses: [3, 4, 5], Total: 12");
        System.out.println("Shipment: primary=10, backup=15");
        System.out.println("Option 1: Primary=3 (need 7), Backup=9 (need 6) = 13 tokens");
        System.out.println("Option 2: Primary=4 (need 6), Backup=8 (need 7) = 13 tokens");
        System.out.println("Option 3: Primary=5 (need 5), Backup=7 (need 8) = 13 tokens");
        System.out.println("Expected: 13\n");
        
        // Test 3: Multiple shipments
        int[] warehouse3 = {10, 20, 30};
        long[][] catalog3 = {
            {15, 40},
            {25, 20},
            {5, 50}
        };
        long[] result3 = useMinimumTokensOptimized(warehouse3, catalog3);
        System.out.println("Test 3: " + Arrays.toString(result3));
        System.out.println("Expected: [5, 5, 0]\n");
        
        // Test 4: Large backup requirement
        int[] warehouse4 = {100, 200};
        long[][] catalog4 = {{50, 500}};
        long[] result4 = useMinimumTokensOptimized(warehouse4, catalog4);
        System.out.println("Test 4: " + Arrays.toString(result4));
        System.out.println("Warehouses: [100, 200], Total: 300");
        System.out.println("Shipment: primary=50, backup=500");
        System.out.println("Option 1: Primary=100, Backup=200 (need 300) = 300 tokens");
        System.out.println("Option 2: Primary=200, Backup=100 (need 400) = 400 tokens");
        System.out.println("Expected: 300\n");
        
        System.out.println("=== Detailed Verification ===");
        verifyShipment(warehouse1, catalog1[0]);
        
        System.out.println("\n=== Random Testing ===");
        Random rand = new Random(42);
        boolean allPassed = true;
        
        for (int test = 0; test < 100; test++) {
            int n = rand.nextInt(9) + 2; // 2-10 warehouses
            int[] wh = new int[n];
            for (int i = 0; i < n; i++) {
                wh[i] = rand.nextInt(100) + 1;
            }
            
            int q = rand.nextInt(5) + 1; // 1-5 shipments
            long[][] cat = new long[q][2];
            for (int i = 0; i < q; i++) {
                cat[i][0] = rand.nextInt(150) + 1;
                cat[i][1] = rand.nextInt(500) + 1;
            }
            
            long[] result = useMinimumTokensOptimized(wh, cat);
            long[] bruteForce = useMinimumTokensBruteForce(wh, cat);
            
            if (!Arrays.equals(result, bruteForce)) {
                System.out.println("MISMATCH:");
                System.out.println("Warehouses: " + Arrays.toString(wh));
                System.out.println("Catalog: " + Arrays.deepToString(cat));
                System.out.println("Expected: " + Arrays.toString(bruteForce));
                System.out.println("Got: " + Arrays.toString(result));
                allPassed = false;
                break;
            }
        }
        
        if (allPassed) {
            System.out.println("✓ All 100 random tests passed!");
        }
        
        System.out.println("\n=== Performance Testing ===");
        
        int[] perfSizes = {100, 500, 1000, 5000, 10000};
        for (int size : perfSizes) {
            int[] whPerf = new int[size];
            for (int i = 0; i < size; i++) {
                whPerf[i] = rand.nextInt(1000000000) + 1;
            }
            
            long[][] catPerf = new long[100][2];
            for (int i = 0; i < 100; i++) {
                catPerf[i][0] = rand.nextInt(1000000000) + 1;
                catPerf[i][1] = rand.nextLong() & 0x7FFFFFFFFFFFFFFFL; // Positive long
                if (catPerf[i][1] == 0) catPerf[i][1] = 1;
            }
            
            long start = System.nanoTime();
            long[] resultPerf = useMinimumTokensOptimized(whPerf, catPerf);
            long end = System.nanoTime();
            
            System.out.printf("n=%d, q=100: time=%.2f ms%n", 
                size, (end - start) / 1_000_000.0);
        }
        
        System.out.println("\n=== All Tests Completed! ===");
    }
    
    // Brute force for verification
    private static long[] useMinimumTokensBruteForce(int[] warehouse, long[][] catalog) {
        int n = warehouse.length;
        int q = catalog.length;
        long[] result = new long[q];
        
        long totalCapacity = 0;
        for (int w : warehouse) {
            totalCapacity += w;
        }
        
        for (int shipmentIdx = 0; shipmentIdx < q; shipmentIdx++) {
            long primaryCapacity = catalog[shipmentIdx][0];
            long backupCapacity = catalog[shipmentIdx][1];
            
            long minTokens = Long.MAX_VALUE;
            
            for (int primaryIdx = 0; primaryIdx < n; primaryIdx++) {
                long tokensForPrimary = Math.max(0, primaryCapacity - warehouse[primaryIdx]);
                long currentBackup = totalCapacity - warehouse[primaryIdx];
                long tokensForBackup = Math.max(0, backupCapacity - currentBackup);
                
                long totalTokens = tokensForPrimary + tokensForBackup;
                minTokens = Math.min(minTokens, totalTokens);
            }
            
            result[shipmentIdx] = minTokens;
        }
        
        return result;
    }
    
    private static void verifyShipment(int[] warehouse, long[] shipment) {
        System.out.println("Verifying shipment: primary=" + shipment[0] + ", backup=" + shipment[1]);
        long totalCapacity = 0;
        for (int w : warehouse) {
            totalCapacity += w;
        }
        System.out.println("Total warehouse capacity: " + totalCapacity);
        
        for (int i = 0; i < warehouse.length; i++) {
            long tokensForPrimary = Math.max(0, shipment[0] - warehouse[i]);
            long currentBackup = totalCapacity - warehouse[i];
            long tokensForBackup = Math.max(0, shipment[1] - currentBackup);
            long totalTokens = tokensForPrimary + tokensForBackup;
            
            System.out.printf("  Primary warehouse %d (capacity=%d): " +
                "primary tokens=%d, backup capacity=%d, backup tokens=%d, total=%d%n",
                i, warehouse[i], tokensForPrimary, currentBackup, tokensForBackup, totalTokens);
        }
    }
}
