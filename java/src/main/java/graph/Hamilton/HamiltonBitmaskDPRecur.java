package graph.Hamilton;

import java.util.Arrays;

/**
 * HamiltonBitmaskDPRecur implements the recursive (Top-Down) Held-Karp algorithm
 * with bitmask memoization to solve the Traveling Salesperson Problem (TSP).
 *
 * Time Complexity: O(n^2 * 2^n)
 * Space Complexity: O(n * 2^n) to store the memoization cache.
 */
public class HamiltonBitmaskDPRecur {
    
    // We use Long.MAX_VALUE / 2 to represent infinity. This is large enough to represent
    // unreachable states and safe enough to prevent overflow when adding edge weights.
    private static final long INF = Long.MAX_VALUE / 2; 
    private static final int MAX_VERTICES = 22;

    /**
     * Solves the Traveling Salesperson Problem (TSP) on the given adjacency matrix.
     * 
     * @param graph adjacency matrix where graph[u][v] is the cost/weight of the edge u -> v.
     *              A value of -1 represents that no direct edge exists.
     * @return the minimum cost of a TSP tour visiting all vertices and returning to start.
     */
    public long solveTSP(int[][] graph) {
        int n = graph.length;
        if (n <= 0) return 0;
        if (n > MAX_VERTICES) {
            throw new IllegalArgumentException("Number of vertices exceeds the limit for bitmask DP: " + MAX_VERTICES);
        }
        
        // 1. BITMASK REPRESENTATION:
        // '1 << n' calculates 2^n, which is the total number of unique subsets of visited cities.
        int numStates = 1 << n; 
        
        // 2. THE MEMOIZATION TABLE (Cache):
        // memo[mask][u] stores the minimum cost to finish the TSP tour starting from city 'u'
        // having already visited the subset of cities in 'mask'.
        long[][] memo = new long[numStates][n];
        
        // Initialize the table with -1 to indicate that no states have been calculated yet.
        for (long[] row : memo) {
            Arrays.fill(row, -1);
        }

        // 3. KICKSTART THE JOURNEY:
        // We start the tour at City 0.
        // The initial mask is 1 (binary 0001), which means only City 0 has been visited.
        return tsp(graph, 1, 0, n, memo);
    }

    private long tsp(int[][] graph, int mask, int u, int n, long[][] memo) {
        
        // 4. THE BASE CASE:
        // '(1 << n) - 1' creates a mask where all bits are 1 (e.g., binary 1111 for n=4).
        // If our current mask matches this, it means every single city has been visited.
        if (mask == (1 << n) - 1) {
            // The journey through the cities is done. Now, we must return the cost 
            // of the final stretch: traveling from our current city 'u' back to the origin (City 0).
            // We use -1 to represent "no edge".
            return graph[u][0] != -1 ? graph[u][0] : INF;
        }

        // 5. THE MEMOIZATION CHECK (Cache Lookup):
        // Before exploring paths, check if we have already evaluated this exact subproblem
        // (standing at city 'u' with the exact same combination of cities visited in 'mask').
        if (memo[mask][u] != -1) {
            return memo[mask][u]; // Return the previously saved result instantly!
        }

        long minRemainingCost = INF;

        // 6. EXPLORE NEXT DESTINATIONS:
        // Try to move to every possible city 'v' from our current city 'u'.
        for (int v = 0; v < n; v++) {
            
            // A. Check if City 'v' is UNVISITED and a valid edge exists:
            // '1 << v' isolates the bit for city 'v'.
            // 'mask & (1 << v)' checks if that bit is active in our history. 
            // If it equals 0, we have NOT visited city 'v' yet.
            // We also verify that a direct edge exists (graph[u][v] != -1).
            if ((mask & (1 << v)) == 0 && graph[u][v] != -1) {
                
                // B. Mark City 'v' as VISITED:
                // 'mask | (1 << v)' sets the bit for city v's position to 1.
                int nextMask = mask | (1 << v);
                
                // C. Recurse:
                // Total cost for this choice = (Distance to v) + (Optimal cost to finish the trip from v onward)
                long totalCostFromHere = graph[u][v] + tsp(graph, nextMask, v, n, memo);
                
                // D. Track the Best Option:
                minRemainingCost = Math.min(minRemainingCost, totalCostFromHere);
            }
        }

        // 7. SAVE TO MEMO TABLE (Cache Write):
        // Save our answer into the memo table before returning it.
        return memo[mask][u] = minRemainingCost;
    }

    // ==================== DEMO ====================
    public static void main(String[] args) {
        HamiltonBitmaskDPRecur solver = new HamiltonBitmaskDPRecur();
        
        // Example: A 4-city asymmetric/symmetric graph represented as an adjacency matrix.
        // -1 represents that no direct road exists.
        int[][] graph = {
            {0, 10, 15, 20},
            {10, 0, 35, 25},
            {15, 35, 0, 30},
            {20, 25, 30, 0}
        };

        long result = solver.solveTSP(graph);
        System.out.println("Minimum TSP Cost (Held-Karp DP Recursive): " + result);
    }
}
