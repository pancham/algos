package graph.Hamilton;

import java.util.*;

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

        List<Integer>[] graph = buildAdjacencyList(numVertices, edges);
        int states = 1 << numVertices;
        boolean[][] dp = new boolean[states][numVertices];
        int[][] parent = new int[states][numVertices];

        for (int[] row : parent) {
            Arrays.fill(row, -1);
        }

        // DP initialization: a path can start at any vertex
        for (int start = 0; start < numVertices; start++) {
            dp[1 << start][start] = true;
        }

        // DP state transitions
        for (int mask = 1; mask < states; mask++) {
            for (int u = 0; u < numVertices; u++) {
                if (!dp[mask][u]) continue;

                for (int v : graph[u]) {
                    if ((mask & (1 << v)) == 0) { // v is not visited in mask
                        int nextMask = mask | (1 << v);
                        dp[nextMask][v] = true;
                        parent[nextMask][v] = u;
                    }
                }
            }
        }

        // Find any ending vertex that has a path visiting all vertices
        int allVisitedMask = states - 1;
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

        // Reconstruct the path in reverse using parent pointers
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

        List<Integer>[] graph = buildAdjacencyList(numVertices, edges);
        int states = 1 << numVertices;
        boolean[][] dp = new boolean[states][numVertices];
        int[][] parent = new int[states][numVertices];

        for (int[] row : parent) {
            Arrays.fill(row, -1);
        }

        // For a circuit, we can fix the starting vertex to 0
        int start = 0;
        dp[1 << start][start] = true;

        for (int mask = 1; mask < states; mask++) {
            for (int u = 0; u < numVertices; u++) {
                if (!dp[mask][u]) continue;

                for (int v : graph[u]) {
                    if ((mask & (1 << v)) == 0) { // v is not visited in mask
                        int nextMask = mask | (1 << v);
                        dp[nextMask][v] = true;
                        parent[nextMask][v] = u;
                    }
                }
            }
        }

        // To form a circuit, the last vertex in the path must be adjacent to the start vertex (0)
        int allVisitedMask = states - 1;
        int last = -1;
        for (int u : graph[start]) {
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
            currMask ^= (1 << curr);
            curr = prev;
        }

        Collections.reverse(path);
        path.add(start); // Complete the circuit
        return path;
    }

    @SuppressWarnings("unchecked")
    private List<Integer>[] buildAdjacencyList(int numVertices, int[][] edges) {
        List<Integer>[] graph = new ArrayList[numVertices];
        for (int i = 0; i < numVertices; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            if (u < 0 || u >= numVertices || v < 0 || v >= numVertices) {
                throw new IllegalArgumentException("Vertex index out of bounds: " + u + ", " + v);
            }
            graph[u].add(v);
            graph[v].add(u);
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
