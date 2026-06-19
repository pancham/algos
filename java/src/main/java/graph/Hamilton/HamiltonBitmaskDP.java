package graph.Hamilton;

import java.util.*;

/**
 * HamiltonBitmaskDP implements the Held-Karp algorithm using dynamic programming (DP)
 * and bitmasking to find Hamiltonian paths and circuits in an undirected graph.
 *
 * ==================== HOW THE CACHE (DP TABLE) HELPS ====================
 * A naive backtracking search explores every permutation of vertices, resulting in
 * a time complexity of O(V!). This is because it does not remember past choices.
 *
 * For example, if we visit nodes in order [1 -> 2 -> 3] vs [2 -> 1 -> 3], both paths
 * end up at vertex 3 having visited the subset of vertices {1, 2, 3}. In both cases,
 * the remaining subproblem is exactly the same: find a path from vertex 3 that visits
 * all remaining unvisited vertices.
 *
 * The cache (`dp[mask][u]`) stores the solution to these subproblems:
 *   - State: `dp[mask][u]` represents whether there is a valid path visiting the subset 
 *            of vertices represented by `mask`, ending at vertex `u`.
 *   - By caching this state, we avoid re-evaluating the search for identical subsets 
 *     and end vertices, reducing the time complexity from O(V!) to O(V^2 * 2^V).
 *
 * Space Complexity: O(V * 2^V) to store the DP table and parent pointers.
 * Limit: V is restricted to <= 22 to prevent OutOfMemoryError.
 */
public class HamiltonBitmaskDP {

    private static final int MAX_VERTICES = 22;

    /**
     * Finds a Hamiltonian path in an undirected graph if one exists.
     * Returns a list of vertex indices representing the path, or an empty list if none exists.
     *
     * @param numVertices the number of vertices in the graph (0 to numVertices - 1)
     * @param edges the undirected edges where edges[i] = {u, v}
     * @return the Hamiltonian path as a list of vertex IDs, or an empty list
     * @throws IllegalArgumentException if numVertices exceeds 22 (to avoid OutOfMemoryError)
     */
    public List<Integer> findPath(int numVertices, int[][] edges) {
        if (numVertices < 0) {
            throw new IllegalArgumentException("Number of vertices cannot be negative.");
        }
        if (numVertices > MAX_VERTICES) {
            throw new IllegalArgumentException("Number of vertices exceeds the limit for bitmask DP: " + MAX_VERTICES);
        }
        if (numVertices == 0) return Collections.emptyList();
        if (numVertices == 1) return List.of(0);

        List<List<Integer>> graph = buildAdjacencyList(numVertices, edges);
        
        // Total possible subsets (masks) of V vertices is 2^V.
        // A set bit at position 'i' in the mask indicates that vertex 'i' has been visited.
        int states = 1 << numVertices; 
        
        // dp[mask][u] stores whether a path visiting the subset of nodes in 'mask' and ending at 'u' exists.
        boolean[][] dp = new boolean[states][numVertices];
        
        // parent[mask][u] stores the predecessor vertex of 'u' in the state 'mask' to reconstruct the path later.
        int[][] parent = new int[states][numVertices];

        for (int[] row : parent) {
            Arrays.fill(row, -1);
        }

        // DP initialization: a path can start at any vertex.
        // For a single visited vertex 'start', the mask value is (1 << start).
        for (int start = 0; start < numVertices; start++) {
            dp[1 << start][start] = true;
        }

        // DP state transitions: process masks in increasing order.
        // Processing smaller masks first ensures all subproblems (subsets) are solved before their supersets.
        for (int mask = 1; mask < states; mask++) {
            for (int u = 0; u < numVertices; u++) {
                // If there is no valid path visiting the subset 'mask' ending at 'u', skip it.
                if (!dp[mask][u]) continue;

                // Attempt to extend the path from the current end vertex 'u' to an unvisited neighbor 'v'.
                for (int v : graph.get(u)) {
                    // MASK CHECKING (Unvisited check):
                    // (1 << v) creates a mask with only the bit for v set.
                    // (mask & (1 << v)) checks if the bit for v is set in the current mask.
                    // If it is 0, then v has NOT been visited yet.
                    if ((mask & (1 << v)) == 0) { 
                        
                        // MASKING (Marking as visited):
                        // (mask | (1 << v)) sets the bit for v in the mask, creating the next state.
                        int nextMask = mask | (1 << v);
                        
                        // Cache the transition and record the parent pointer.
                        dp[nextMask][v] = true;
                        parent[nextMask][v] = u;
                    }
                }
            }
        }

        // Find any ending vertex that has a path visiting all vertices (all bits set in the mask).
        int allVisitedMask = states - 1; // Mask containing V ones (e.g. 1111 for V=4)
        int last = -1;
        for (int u = 0; u < numVertices; u++) {
            if (dp[allVisitedMask][u]) {
                last = u;
                break;
            }
        }

        if (last == -1) {
            return Collections.emptyList();
        }

        // Reconstruct the path in reverse using parent pointers.
        List<Integer> path = new ArrayList<>();
        int curr = last;
        int currMask = allVisitedMask;
        
        while (curr != -1) {
            path.add(curr);
            int prev = parent[currMask][curr];
            
            // UNMASKING (Backtracking the state):
            // currMask ^= (1 << curr) clears the bit for the current node 'curr' in the mask.
            // This rolls back the state to the subset of nodes visited prior to reaching 'curr'.
            currMask ^= (1 << curr);
            curr = prev;
        }

        Collections.reverse(path);
        return path;
    }

    /**
     * Finds a Hamiltonian circuit (cycle) in an undirected graph if one exists.
     * Returns a list of vertex indices representing the circuit, starting and ending at
     * the same vertex, or an empty list if none exists.
     *
     * @param numVertices the number of vertices in the graph (0 to numVertices - 1)
     * @param edges the undirected edges where edges[i] = {u, v}
     * @return the Hamiltonian circuit as a list of vertex IDs (length V + 1), or an empty list
     * @throws IllegalArgumentException if numVertices exceeds 22
     */
    public List<Integer> findCircuit(int numVertices, int[][] edges) {
        if (numVertices < 0) {
            throw new IllegalArgumentException("Number of vertices cannot be negative.");
        }
        if (numVertices > MAX_VERTICES) {
            throw new IllegalArgumentException("Number of vertices exceeds the limit for bitmask DP: " + MAX_VERTICES);
        }
        if (numVertices == 0) return Collections.emptyList();
        if (numVertices == 1) return List.of(0, 0);

        List<List<Integer>> graph = buildAdjacencyList(numVertices, edges);
        int states = 1 << numVertices;
        boolean[][] dp = new boolean[states][numVertices];
        int[][] parent = new int[states][numVertices];

        for (int[] row : parent) {
            Arrays.fill(row, -1);
        }

        // For a circuit, we can fix the starting vertex to 0.
        // If a cycle visits all vertices, it must visit vertex 0, so start vertex choice is arbitrary.
        int start = 0;
        dp[1 << start][start] = true;

        for (int mask = 1; mask < states; mask++) {
            for (int u = 0; u < numVertices; u++) {
                if (!dp[mask][u]) continue;

                for (int v : graph.get(u)) {
                    // MASK CHECKING (v unvisited check)
                    if ((mask & (1 << v)) == 0) { 
                        // MASKING (mark v as visited)
                        int nextMask = mask | (1 << v);
                        dp[nextMask][v] = true;
                        parent[nextMask][v] = u;
                    }
                }
            }
        }

        // To form a circuit, the last vertex 'last' in the path must have an edge back to the start vertex (0).
        int allVisitedMask = states - 1;
        int last = -1;
        for (int u : graph.get(start)) {
            if (dp[allVisitedMask][u]) {
                last = u;
                break;
            }
        }

        if (last == -1) {
            return Collections.emptyList();
        }

        // Reconstruct the path from last back to start (0)
        List<Integer> path = new ArrayList<>();
        int curr = last;
        int currMask = allVisitedMask;
        
        while (curr != -1) {
            path.add(curr);
            int prev = parent[currMask][curr];
            
            // UNMASKING (Backtracking the state)
            currMask ^= (1 << curr);
            curr = prev;
        }

        Collections.reverse(path);
        path.add(start); // Complete the circuit loop by appending the start vertex at the end
        return path;
    }

    private List<List<Integer>> buildAdjacencyList(int numVertices, int[][] edges) {
        List<List<Integer>> graph = new ArrayList<>(numVertices);
        for (int i = 0; i < numVertices; i++) {
            graph.add(new ArrayList<>());
        }
        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            if (u < 0 || u >= numVertices || v < 0 || v >= numVertices) {
                throw new IllegalArgumentException("Vertex index out of bounds: " + u + ", " + v);
            }
            graph.get(u).add(v);
            graph.get(v).add(u);
        }
        return graph;
    }

    // ==================== DEMO ====================
    public static void main(String[] args) {
        HamiltonBitmaskDP solver = new HamiltonBitmaskDP();

        // 1. Graph with a Hamiltonian circuit: 0-1-2-3-0
        // Edges: 0-1, 1-2, 2-3, 3-0, 0-2 (extra diagonal edge)
        int[][] edgesCircuit = { {0, 1}, {1, 2}, {2, 3}, {3, 0}, {0, 2} };
        System.out.println("Graph 1 (has circuit) - Path:    " + solver.findPath(4, edgesCircuit));
        System.out.println("Graph 1 (has circuit) - Circuit: " + solver.findCircuit(4, edgesCircuit));

        // 2. Graph with a Hamiltonian path but no circuit: 0-1-2-3
        // Edges: 0-1, 1-2, 2-3
        int[][] edgesPathOnly = { {0, 1}, {1, 2}, {2, 3} };
        System.out.println("Graph 2 (path only)   - Path:    " + solver.findPath(4, edgesPathOnly));
        System.out.println("Graph 2 (path only)   - Circuit: " + solver.findCircuit(4, edgesPathOnly));

        // 3. Graph with no Hamiltonian path/circuit
        // Edges: 0-1, 0-2, 0-3 (a star-like graph where 0 is central)
        int[][] edgesNone = { {0, 1}, {0, 2}, {0, 3} };
        System.out.println("Graph 3 (no path)     - Path:    " + solver.findPath(4, edgesNone));
        System.out.println("Graph 3 (no path)     - Circuit: " + solver.findCircuit(4, edgesNone));
    }
}
