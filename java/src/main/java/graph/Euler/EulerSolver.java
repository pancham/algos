package graph.Euler;

import java.util.*;

public class EulerSolver {

    private final EulerPathFinder finder = new EulerPathFinder();

    // ==================== UNDIRECTED ====================

    /**
     * Determines whether an Euler path/circuit exists, picks the correct
     * start vertex, and returns the route. Throws if none exists.
     */
    public List<Integer> solveUndirected(int[][] edges) {
        Map<Integer, Integer> degree = new HashMap<>();
        for (int[] e : edges) {
            degree.merge(e[0], 1, Integer::sum);
            degree.merge(e[1], 1, Integer::sum);
        }

        List<Integer> oddVertices = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : degree.entrySet()) {
            if (entry.getValue() % 2 != 0) {
                oddVertices.add(entry.getKey());
            }
        }

        int start;
        if (oddVertices.isEmpty()) {
            // Euler CIRCUIT: start anywhere (pick any vertex with an edge)
            start = edges[0][0];
        } else if (oddVertices.size() == 2) {
            // Euler PATH: must start at an odd-degree vertex
            start = oddVertices.get(0);
        } else {
            throw new IllegalArgumentException(
                    "No Euler path/circuit: " + oddVertices.size()
                            + " odd-degree vertices (must be 0 or 2)");
        }

        List<Integer> path = finder.findUndirected(edges, start);
        verifyAllEdgesUsed(path, edges.length); // catches disconnected graphs
        return path;
    }

    // ==================== DIRECTED ====================

    public List<Integer> solveDirected(int[][] edges) {
        Map<Integer, Integer> outDeg = new HashMap<>();
        Map<Integer, Integer> inDeg = new HashMap<>();
        Set<Integer> vertices = new HashSet<>();
        for (int[] e : edges) {
            outDeg.merge(e[0], 1, Integer::sum);
            inDeg.merge(e[1], 1, Integer::sum);
            vertices.add(e[0]);
            vertices.add(e[1]);
        }

        Integer pathStart = null; // out - in == +1
        Integer pathEnd = null; // in - out == +1
        for (int v : vertices) {
            int diff = outDeg.getOrDefault(v, 0) - inDeg.getOrDefault(v, 0);
            if (diff == 1) {
                if (pathStart != null)
                    throw new IllegalArgumentException(
                            "No Euler path/circuit: multiple start candidates");
                pathStart = v;
            } else if (diff == -1) {
                if (pathEnd != null)
                    throw new IllegalArgumentException(
                            "No Euler path/circuit: multiple end candidates");
                pathEnd = v;
            } else if (diff != 0) {
                throw new IllegalArgumentException(
                        "No Euler path/circuit: vertex " + v + " has |out-in| > 1");
            }
        }

        // Both null -> circuit (start anywhere); both set -> path; mixed -> invalid
        if ((pathStart == null) != (pathEnd == null)) {
            throw new IllegalArgumentException(
                    "No Euler path/circuit: unbalanced degree structure");
        }
        int start = (pathStart != null) ? pathStart : edges[0][0];

        List<Integer> path = finder.findDirected(edges, start);
        verifyAllEdgesUsed(path, edges.length);
        return path;
    }

    // ==================== SHARED ====================

    /**
     * Degree checks alone can't detect a disconnected graph (two separate
     * even-degree components pass the count test). Cheapest robust check:
     * a valid Euler route over E edges visits exactly E+1 vertices.
     */
    private void verifyAllEdgesUsed(List<Integer> path, int edgeCount) {
        if (path.size() != edgeCount + 1) {
            throw new IllegalArgumentException(
                    "No Euler path/circuit: graph is disconnected ("
                            + (path.size() - 1) + " of " + edgeCount + " edges reachable)");
        }
    }

    // ==================== DEMO ====================

    public static void main(String[] args) {
        EulerSolver solver = new EulerSolver();

        // Undirected, two odd vertices (0 and 3) -> path, auto-starts at odd vertex
        int[][] undirectedPath = { { 0, 1 }, { 1, 2 }, { 2, 0 }, { 0, 3 } };
        System.out.println("Undirected path:    " + solver.solveUndirected(undirectedPath));

        // Undirected, all even -> circuit
        int[][] undirectedCircuit = { { 0, 1 }, { 1, 2 }, { 2, 0 } };
        System.out.println("Undirected circuit: " + solver.solveUndirected(undirectedCircuit));

        // Directed, balanced -> circuit
        int[][] directedCircuit = { { 0, 1 }, { 1, 2 }, { 2, 0 } };
        System.out.println("Directed circuit:   " + solver.solveDirected(directedCircuit));

        // Directed path: 3 has out-in=+1 (start), 2 has in-out=+1 (end)
        int[][] directedPath = { { 3, 0 }, { 0, 1 }, { 1, 2 } };
        System.out.println("Directed path:      " + solver.solveDirected(directedPath));

        // Invalid: 4 odd vertices
        int[][] invalid = { { 0, 1 }, { 2, 3 } };
        try {
            solver.solveUndirected(invalid);
        } catch (IllegalArgumentException ex) {
            System.out.println("Rejected:           " + ex.getMessage());
        }
    }
}
