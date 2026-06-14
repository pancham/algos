package graph;

import java.util.*;

public class GraphCycleIterTricolor {
    // DFS State Constants
    private static final int WHITE = 0; // Unvisited
    private static final int GREY = 1; // Visiting (on stack)
    private static final int BLACK = 2; // Visited (fully processed)

    /**
     * Detects a cycle in a directed graph using an iterative DFS with three colors.
     */
    public boolean hasCycle(int numVertices, int[][] edges) {
        // 1. Build Adjacency List from the edges array
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < numVertices; i++)
            adj.add(new ArrayList<>());
        for (int[] edge : edges) {
            adj.get(edge[0]).add(edge[1]);
        }

        int[] colors = new int[numVertices];

        // 2. Iterate through all vertices to handle disconnected components
        for (int i = 0; i < numVertices; i++) {
            if (colors[i] == WHITE) {
                if (isCyclicIterative(i, adj, colors))
                    return true;
            }
        }
        return false;
    }

    private boolean isCyclicIterative(int startNode, List<List<Integer>> adj, int[] colors) {
        Stack<NodeState> stack = new Stack<>();

        // Push initial node and mark as GREY
        colors[startNode] = GREY;
        stack.push(new NodeState(startNode, adj.get(startNode).iterator()));

        while (!stack.isEmpty()) {
            NodeState current = stack.peek();

            if (current.iterator.hasNext()) {
                int neighbor = current.iterator.next();

                // If neighbor is GREY, we found a back-edge (Cycle)
                if (colors[neighbor] == GREY) {
                    return true;
                }

                // If neighbor is WHITE, push to stack and mark as GREY
                if (colors[neighbor] == WHITE) {
                    colors[neighbor] = GREY;
                    stack.push(new NodeState(neighbor, adj.get(neighbor).iterator()));
                }
            } else {
                // No more neighbors: backtrack and mark node as BLACK
                colors[current.id] = BLACK;
                stack.pop();
            }
        }
        return false;
    }

    // Helper class to maintain state during iterative DFS
    private static class NodeState {
        int id;
        Iterator<Integer> iterator;

        NodeState(int id, Iterator<Integer> it) {
            this.id = id;
            this.iterator = it;
        }
    }

    public static void main(String[] args) {
        GraphCycleIterTricolor detector = new GraphCycleIterTricolor();

        System.out.println("--- Graph Cycle Detection Tests ---");

        // Test 1: Simple Cycle (0 -> 1 -> 2 -> 0)
        int[][] cyclicEdges = { { 0, 1 }, { 1, 2 }, { 2, 0 } };
        System.out.println("Test 1 (Simple Cycle): " + (detector.hasCycle(3, cyclicEdges) ? "PASSED" : "FAILED"));

        // Test 2: Acyclic Graph (0 -> 1 -> 2)
        int[][] acyclicEdges = { { 0, 1 }, { 1, 2 } };
        System.out.println("Test 2 (Acyclic): " + (!detector.hasCycle(3, acyclicEdges) ? "PASSED" : "FAILED"));

        // Test 3: Disconnected graph with cycle in hidden component
        int[][] disconnectedCycle = { { 0, 1 }, { 2, 3 }, { 3, 2 } };
        System.out.println(
                "Test 3 (Disconnected Cycle): " + (detector.hasCycle(4, disconnectedCycle) ? "PASSED" : "FAILED"));

        // Test 4: Self-loop (0 -> 0)
        int[][] selfLoop = { { 0, 0 } };
        System.out.println("Test 4 (Self-loop): " + (detector.hasCycle(1, selfLoop) ? "PASSED" : "FAILED"));

        // Test 5: Complex DAG (0 -> 1, 0 -> 2, 1 -> 2) - No cycle
        int[][] dagEdges = { { 0, 1 }, { 0, 2 }, { 1, 2 } };
        System.out.println("Test 5 (DAG): " + (!detector.hasCycle(3, dagEdges) ? "PASSED" : "FAILED"));
    }
}
