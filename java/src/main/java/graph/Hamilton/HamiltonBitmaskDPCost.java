package graph.Hamilton;

import java.util.*;

/**
 * HamiltonBitmaskDPCost implements the iterative (Bottom-Up) Held-Karp algorithm
 * using dynamic programming and bitmasking to solve the Traveling Salesperson Problem (TSP).
 *
 * State definition:
 *   - dp[mask][u] stores the minimum cost to visit the subset of nodes represented
 *     by 'mask', ending at node 'u'.
 *
 * Time Complexity: O(n^2 * 2^n)
 * Space Complexity: O(n * 2^n) to store the DP table and parent pointers.
 */
public class HamiltonBitmaskDPCost {

    private static final long INF = Long.MAX_VALUE / 2;
    private static final int MAX_VERTICES = 22;

    private long minCost;
    private List<Integer> bestPath;

    /**
     * Finds the minimum-cost Hamiltonian path in a weighted graph.
     *
     * @param graph adjacency matrix where graph[u][v] is the cost of the edge u -> v,
     *              or -1 if no edge exists.
     * @return the sequence of vertices in the minimum-cost Hamiltonian path, 
     *         or an empty list if none exists.
     */
    public List<Integer> findMinCostPath(int[][] graph) {
        int n = graph.length;
        if (n <= 0) return Collections.emptyList();
        if (n == 1) return List.of(0);
        if (n > MAX_VERTICES) {
            throw new IllegalArgumentException("Number of vertices exceeds the limit for bitmask DP: " + MAX_VERTICES);
        }

        minCost = INF;
        bestPath = new ArrayList<>();

        int states = 1 << n;
        long[][] dp = new boolean[1][1] == null ? null : new long[states][n];
        int[][] parent = new int[states][n];

        for (long[] row : dp) {
            Arrays.fill(row, INF);
        }
        for (int[] row : parent) {
            Arrays.fill(row, -1);
        }

        // DP initialization: a path can start at any vertex.
        for (int start = 0; start < n; start++) {
            dp[1 << start][start] = 0;
        }

        // DP state transitions: process subsets of increasing sizes
        for (int mask = 1; mask < states; mask++) {
            for (int u = 0; u < n; u++) {
                if (dp[mask][u] == INF) continue;

                // Try to extend the path to an unvisited neighbor 'v'
                for (int v = 0; v < n; v++) {
                    if ((mask & (1 << v)) == 0 && graph[u][v] != -1) {
                        int nextMask = mask | (1 << v);
                        long newCost = dp[mask][u] + graph[u][v];
                        if (newCost < dp[nextMask][v]) {
                            dp[nextMask][v] = newCost;
                            parent[nextMask][v] = u;
                        }
                    }
                }
            }
        }

        // Find the ending vertex that gives the minimum cost path visiting all vertices
        int allVisitedMask = states - 1;
        int last = -1;
        for (int u = 0; u < n; u++) {
            if (dp[allVisitedMask][u] < minCost) {
                minCost = dp[allVisitedMask][u];
                last = u;
            }
        }

        if (last == -1 || minCost == INF) {
            return Collections.emptyList();
        }

        // Reconstruct path
        List<Integer> path = new ArrayList<>();
        int curr = last;
        int currMask = allVisitedMask;
        while (curr != -1) {
            path.add(curr);
            int prev = parent[currMask][curr];
            currMask ^= (1 << curr);
            curr = prev;
        }

        Collections.reverse(path);
        return path;
    }

    /**
     * Finds the minimum-cost Hamiltonian circuit in a weighted graph.
     *
     * @param graph adjacency matrix where graph[u][v] is the cost of the edge u -> v,
     *              or -1 if no edge exists.
     * @return the sequence of vertices in the minimum-cost Hamiltonian circuit (length V + 1),
     *         or an empty list if none exists.
     */
    public List<Integer> findMinCostCircuit(int[][] graph) {
        int n = graph.length;
        if (n <= 0) return Collections.emptyList();
        if (n == 1) return List.of(0, 0);
        if (n > MAX_VERTICES) {
            throw new IllegalArgumentException("Number of vertices exceeds the limit for bitmask DP: " + MAX_VERTICES);
        }

        minCost = INF;
        bestPath = new ArrayList<>();

        int states = 1 << n;
        long[][] dp = new long[states][n];
        int[][] parent = new int[states][n];

        for (long[] row : dp) {
            Arrays.fill(row, INF);
        }
        for (int[] row : parent) {
            Arrays.fill(row, -1);
        }

        // Fix starting vertex to 0
        int start = 0;
        dp[1 << start][start] = 0;

        for (int mask = 1; mask < states; mask++) {
            for (int u = 0; u < n; u++) {
                if (dp[mask][u] == INF) continue;

                for (int v = 0; v < n; v++) {
                    if ((mask & (1 << v)) == 0 && graph[u][v] != -1) {
                        int nextMask = mask | (1 << v);
                        long newCost = dp[mask][u] + graph[u][v];
                        if (newCost < dp[nextMask][v]) {
                            dp[nextMask][v] = newCost;
                            parent[nextMask][v] = u;
                        }
                    }
                }
            }
        }

        // To form a circuit, we check the path cost plus the return cost to start vertex 0
        int allVisitedMask = states - 1;
        int last = -1;
        for (int u = 0; u < n; u++) {
            if (dp[allVisitedMask][u] != INF && graph[u][start] != -1) {
                long totalCost = dp[allVisitedMask][u] + graph[u][start];
                if (totalCost < minCost) {
                    minCost = totalCost;
                    last = u;
                }
            }
        }

        if (last == -1 || minCost == INF) {
            return Collections.emptyList();
        }

        // Reconstruct path
        List<Integer> path = new ArrayList<>();
        int curr = last;
        int currMask = allVisitedMask;
        while (curr != -1) {
            path.add(curr);
            int prev = parent[currMask][curr];
            currMask ^= (1 << curr);
            curr = prev;
        }

        Collections.reverse(path);
        path.add(start); // Complete cycle
        return path;
    }

    /**
     * Returns the minimum cost of the last computed path or circuit.
     */
    public long getMinCost() {
        return minCost;
    }

    // ==================== DEMO ====================
    public static void main(String[] args) {
        HamiltonBitmaskDPCost solver = new HamiltonBitmaskDPCost();

        // 4-city graph represented as an adjacency matrix.
        // -1 represents that no direct road exists.
        int[][] graph = {
            {0, 10, 15, 20},
            {10, 0, 35, 25},
            {15, 35, 0, 30},
            {20, 25, 30, 0}
        };

        // 1. Min Cost Path
        List<Integer> path = solver.findMinCostPath(graph);
        System.out.println("Min Cost Path:         " + path + " with Cost: " + solver.getMinCost());

        // 2. Min Cost Circuit (TSP)
        List<Integer> circuit = solver.findMinCostCircuit(graph);
        System.out.println("Min Cost Circuit (TSP): " + circuit + " with Cost: " + solver.getMinCost());
    }
}
