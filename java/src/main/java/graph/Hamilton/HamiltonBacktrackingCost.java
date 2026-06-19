package graph.Hamilton;

import java.util.*;

/**
 * HamiltonBacktrackingCost implements recursive backtracking DFS to find the
 * minimum-cost Hamiltonian path and circuit in a weighted graph (solving TSP).
 *
 * It explores all valid paths to find the path/circuit with the absolute minimum
 * cost, using a pruning optimization to skip branches that exceed the current minimum.
 *
 * Time Complexity: O(V!) in the worst case.
 * Space Complexity: O(V) for the recursion stack and visited array.
 */
public class HamiltonBacktrackingCost {

    // We use Long.MAX_VALUE / 2 to prevent overflow when adding edge weights.
    private static final long INF = Long.MAX_VALUE / 2;

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

        minCost = INF;
        bestPath = new ArrayList<>();

        boolean[] visited = new boolean[n];
        List<Integer> currentPath = new ArrayList<>();

        // Try starting the path from each possible vertex
        for (int start = 0; start < n; start++) {
            currentPath.add(start);
            visited[start] = true;
            
            dfsPath(graph, start, 0, visited, currentPath);
            
            visited[start] = false;
            currentPath.clear();
        }

        return minCost == INF ? Collections.emptyList() : new ArrayList<>(bestPath);
    }

    private void dfsPath(int[][] graph, int u, long currentCost, boolean[] visited, List<Integer> currentPath) {
        // Optimization: Prune branches that are already more expensive than the best found so far
        if (currentCost >= minCost) {
            return;
        }

        // Base case: If the path contains all vertices, we have a valid Hamiltonian path
        if (currentPath.size() == graph.length) {
            if (currentCost < minCost) {
                minCost = currentCost;
                bestPath = new ArrayList<>(currentPath);
            }
            return;
        }

        // Explore all outgoing edges / neighbors of the current vertex u
        for (int v = 0; v < graph.length; v++) {
            if (!visited[v] && graph[u][v] != -1) {
                // 1. ADD STATE: Mark neighbor v as visited and append it to our path sequence
                visited[v] = true;
                currentPath.add(v);

                // 2. RECURSE: Continue searching from the neighbor vertex v with updated cost
                dfsPath(graph, v, currentCost + graph[u][v], visited, currentPath);

                // 3. BACKTRACK (REVERT STATE): Revert our choices to explore other branches
                currentPath.remove(currentPath.size() - 1);
                visited[v] = false;
            }
        }
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

        minCost = INF;
        bestPath = new ArrayList<>();

        boolean[] visited = new boolean[n];
        List<Integer> currentPath = new ArrayList<>();

        // Fix starting vertex to 0 since a circuit must cover all vertices
        int start = 0;
        currentPath.add(start);
        visited[start] = true;

        dfsCircuit(graph, start, start, 0, visited, currentPath);

        return minCost == INF ? Collections.emptyList() : new ArrayList<>(bestPath);
    }

    private void dfsCircuit(int[][] graph, int u, int start, long currentCost, boolean[] visited, List<Integer> currentPath) {
        // Optimization: Prune branches that are already more expensive than the best found so far
        if (currentCost >= minCost) {
            return;
        }

        // Base case: If we have visited all vertices
        if (currentPath.size() == graph.length) {
            // Check if there is an edge from the last visited vertex back to the start
            if (graph[u][start] != -1) {
                long totalCost = currentCost + graph[u][start];
                if (totalCost < minCost) {
                    minCost = totalCost;
                    bestPath = new ArrayList<>(currentPath);
                    bestPath.add(start); // ADD STATE: Close the circuit loop by appending the start vertex
                }
            }
            return;
        }

        // Explore all adjacent neighbors of the current vertex u
        for (int v = 0; v < graph.length; v++) {
            if (!visited[v] && graph[u][v] != -1) {
                // 1. ADD STATE: Mark neighbor v as visited and append it to our path sequence
                visited[v] = true;
                currentPath.add(v);

                // 2. RECURSE: Continue searching from vertex v with updated cost
                dfsCircuit(graph, v, start, currentCost + graph[u][v], visited, currentPath);

                // 3. BACKTRACK (REVERT STATE): Revert choices if the recursive branch fails
                currentPath.remove(currentPath.size() - 1);
                visited[v] = false;
            }
        }
    }

    /**
     * Returns the minimum cost of the last computed path or circuit.
     */
    public long getMinCost() {
        return minCost;
    }

    // ==================== DEMO ====================
    public static void main(String[] args) {
        HamiltonBacktrackingCost solver = new HamiltonBacktrackingCost();

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

        // 2. Min Cost Circuit
        List<Integer> circuit = solver.findMinCostCircuit(graph);
        System.out.println("Min Cost Circuit (TSP): " + circuit + " with Cost: " + solver.getMinCost());
    }
}
