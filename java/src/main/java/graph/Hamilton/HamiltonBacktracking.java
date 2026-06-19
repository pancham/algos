package graph.Hamilton;

import java.util.*;

public class HamiltonBacktracking {

    /**
     * Returns a list of vertex indices representing the path, or an empty list if
     * none exists.
     *
     * @param numVertices the number of vertices in the graph (0 to numVertices - 1)
     * @param edges       the undirected edges where edges[i] = {u, v}
     * @return the Hamiltonian path as a list of vertex IDs, or an empty list
     */
    public List<Integer> findPath(int numVertices, int[][] edges) {
        if (numVertices <= 0)
            return Collections.emptyList();
        if (numVertices == 1)
            return List.of(0);

        List<List<Integer>> graph = buildAdjacencyList(numVertices, edges);
        boolean[] visited = new boolean[numVertices];
        List<Integer> path = new ArrayList<>();

        // Try starting the path from each possible vertex
        for (int start = 0; start < numVertices; start++) {
            path.add(start);
            visited[start] = true;
            if (dfsPath(graph, start, visited, path)) {
                return path;
            }
            visited[start] = false;
            path.clear();
        }
        return Collections.emptyList();
    }

    private boolean dfsPath(List<List<Integer>> graph, int u, boolean[] visited, List<Integer> path) {
        // Base case: If we have visited all vertices, we found a valid Hamiltonian path.
        if (path.size() == graph.size()) {
            return true;
        }

        // Explore all outgoing edges / neighbors of the current vertex u
        for (int v : graph.get(u)) {
            // Check if neighbor v is not yet visited in the current path
            if (!visited[v]) {
                // 1. ADD STATE: Mark neighbor v as visited and append it to our path sequence
                visited[v] = true;
                path.add(v);

                // 2. RECURSE: Continue searching from the neighbor vertex v
                if (dfsPath(graph, v, visited, path)) {
                    return true; // Propagation of success up the call stack
                }

                // 3. BACKTRACK (REVERT STATE): The recursion path through v failed to visit all
                // vertices.
                // We undo our decisions: remove v from the path and mark it as unvisited.
                path.remove(path.size() - 1);
                visited[v] = false;
            }
        }

        // Return false if no unvisited neighbors from this vertex could lead to a full
        // Hamiltonian path
        return false;
    }

    /**
     * Finds a Hamiltonian circuit (cycle) in an undirected graph if one exists.
     * Returns a list of vertex indices representing the circuit, starting and
     * ending at
     * the same vertex, or an empty list if none exists.
     *
     * @param numVertices the number of vertices in the graph (0 to numVertices - 1)
     * @param edges       the undirected edges where edges[i] = {u, v}
     * @return the Hamiltonian circuit as a list of vertex IDs (length V + 1), or an
     *         empty list
     */
    public List<Integer> findCircuit(int numVertices, int[][] edges) {
        if (numVertices <= 0)
            return Collections.emptyList();
        if (numVertices == 1)
            return List.of(0, 0);

        List<List<Integer>> graph = buildAdjacencyList(numVertices, edges);
        boolean[] visited = new boolean[numVertices];
        List<Integer> path = new ArrayList<>();

        // A circuit is a loop, so if a circuit exists, we can start searching at any
        // vertex (e.g. 0)
        int start = 0;
        path.add(start);
        visited[start] = true;

        if (dfsCircuit(graph, start, start, visited, path)) {
            return path;
        }
        return Collections.emptyList();
    }

    private boolean dfsCircuit(List<List<Integer>> graph, int u, int start, boolean[] visited, List<Integer> path) {
        // Base case: If we have visited all vertices
        if (path.size() == graph.size()) {
            // To complete the circuit, check if there is an edge from the last vertex u
            // back to the start vertex
            for (int v : graph.get(u)) {
                if (v == start) {
                    path.add(start); // ADD STATE: Close the circuit loop by appending the start vertex
                    return true;
                }
            }
            return false; // Stuck: visited all nodes, but no edge exists back to the start vertex
        }
        for (int v : graph.get(u)) {
            if (!visited[v]) {
                // 1. ADD STATE: Mark neighbor v as visited and append it to our path sequence
                visited[v] = true;
                path.add(v);

                // 2. RECURSE: Continue searching from the neighbor vertex v
                if (dfsCircuit(graph, v, start, visited, path)) {
                    return true; // Propagation of success
                }

                // 3. BACKTRACK (REVERT STATE): The recursion path through v failed to form a
                // circuit.
                // Revert our choice: remove v from the path and mark it as unvisited.
                path.remove(path.size() - 1);
                visited[v] = false;
            }
        }

        return false;
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
        HamiltonBacktracking solver = new HamiltonBacktracking();

        // 1. Graph with a Hamiltonian circuit: 0-1-2-3-0
        // Edges: 0-1, 1-2, 2-3, 3-0, 0-2 (extra diagonal edge)
        int[][] edgesCircuit = { { 0, 1 }, { 1, 2 }, { 2, 3 }, { 3, 0 }, { 0, 2 } };
        System.out.println("Graph 1 (has circuit) - Path:    " + solver.findPath(4, edgesCircuit));
        System.out.println("Graph 1 (has circuit) - Circuit: " + solver.findCircuit(4, edgesCircuit));

        // 2. Graph with a Hamiltonian path but no circuit: 0-1-2-3
        // Edges: 0-1, 1-2, 2-3
        int[][] edgesPathOnly = { { 0, 1 }, { 1, 2 }, { 2, 3 } };
        System.out.println("Graph 2 (path only)   - Path:    " + solver.findPath(4, edgesPathOnly));
        System.out.println("Graph 2 (path only)   - Circuit: " + solver.findCircuit(4, edgesPathOnly));

        // 3. Graph with no Hamiltonian path/circuit
        // Edges: 0-1, 0-2, 0-3 (a star-like graph where 0 is central)
        int[][] edgesNone = { { 0, 1 }, { 0, 2 }, { 0, 3 } };
        System.out.println("Graph 3 (no path)     - Path:    " + solver.findPath(4, edgesNone));
        System.out.println("Graph 3 (no path)     - Circuit: " + solver.findCircuit(4, edgesNone));
    }
}
