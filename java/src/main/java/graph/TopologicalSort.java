package graph;

import java.util.*;

public class TopologicalSort {

    /**
     * Returns a valid topological ordering of a directed graph.
     * 
     * Topological sort (or topological ordering) of a directed graph is a linear
     * ordering of its vertices such that for every directed edge from vertex u to
     * vertex v, vertex u comes before vertex v in the ordering.
     * A topological sort is only possible if the graph is a Directed Acyclic Graph
     * (DAG).
     * It can also be used to detect cycles in a directed graph.
     * 
     * Throws IllegalArgumentException if the graph has a cycle.
     */
    public static <T> List<T> sort(Map<T, List<T>> graph) {
        Map<T, Integer> indegree = new HashMap<>();

        // Build indegree map.
        for (Map.Entry<T, List<T>> entry : graph.entrySet()) {
            indegree.putIfAbsent(entry.getKey(), 0);
            for (T neighbor : entry.getValue()) {
                indegree.put(neighbor, indegree.getOrDefault(neighbor, 0) + 1);
            }
        }

        Queue<T> queue = new ArrayDeque<>();

        for (Map.Entry<T, Integer> entry : indegree.entrySet()) {
            if (entry.getValue() == 0) {
                queue.add(entry.getKey());
            }
        }

        List<T> result = new ArrayList<>();

        while (!queue.isEmpty()) {
            T current = queue.remove();
            result.add(current);

            for (T neighbor : graph.getOrDefault(current, List.of())) {
                int updatedIndegree = indegree.computeIfPresent(neighbor, (key, value) -> value - 1);

                if (updatedIndegree == 0) {
                    queue.add(neighbor);
                }
            }
        }

        if (result.size() != indegree.size()) {
            throw new IllegalArgumentException("Graph contains a cycle");
        }

        return result;
    }

    /**
     * Helper for building the graph.
     * Uses computeIfAbsent so we do not need to manually check whether u exists.
     */
    public static <T> void addEdge(Map<T, List<T>> graph, T u, T v) {
        graph.computeIfAbsent(u, key -> new ArrayList<>()).add(v);
        graph.computeIfAbsent(v, key -> new ArrayList<>());
    }

    public static void main(String[] args) {
        testSimpleDAG();
        testDisconnectedDAG();
        testCycle();
        testSingleNode();

        System.out.println("All tests passed.");
    }

    private static void testSimpleDAG() {
        Map<String, List<String>> graph = new HashMap<>();

        addEdge(graph, "A", "B");
        addEdge(graph, "A", "C");
        addEdge(graph, "B", "D");
        addEdge(graph, "C", "D");

        List<String> order = sort(graph);

        assertValidTopologicalOrder(graph, order);

        System.out.println("testSimpleDAG passed: " + order);
    }

    private static void testDisconnectedDAG() {
        Map<Integer, List<Integer>> graph = new HashMap<>();

        addEdge(graph, 1, 2);
        addEdge(graph, 3, 4);

        List<Integer> order = sort(graph);

        assertValidTopologicalOrder(graph, order);

        System.out.println("testDisconnectedDAG passed: " + order);
    }

    private static void testCycle() {
        Map<String, List<String>> graph = new HashMap<>();

        addEdge(graph, "A", "B");
        addEdge(graph, "B", "C");
        addEdge(graph, "C", "A");

        boolean threwException = false;

        try {
            sort(graph);
        } catch (IllegalArgumentException e) {
            threwException = true;
        }

        assert threwException : "Expected cycle to throw IllegalArgumentException";

        System.out.println("testCycle passed.");
    }

    private static void testSingleNode() {
        Map<String, List<String>> graph = new HashMap<>();

        graph.computeIfAbsent("A", key -> new ArrayList<>());

        List<String> order = sort(graph);

        assert order.equals(List.of("A")) : "Expected [A], got " + order;

        System.out.println("testSingleNode passed: " + order);
    }

    private static <T> void assertValidTopologicalOrder(
            Map<T, List<T>> graph,
            List<T> order) {
        Map<T, Integer> position = new HashMap<>();

        for (int i = 0; i < order.size(); i++) {
            position.put(order.get(i), i);
        }

        for (T node : graph.keySet()) {
            assert position.containsKey(node) : "Missing node in output: " + node;

            for (T neighbor : graph.get(node)) {
                assert position.containsKey(neighbor) : "Missing neighbor in output: " + neighbor;

                assert position.get(node) < position.get(neighbor)
                        : "Invalid order: " + node + " should come before " + neighbor;
            }
        }
    }
}