package graph.Euler;

import java.util.*;

public class EulerPathFinder {

    // ==================== DIRECTED ====================

    /**
     * Directed graph. edges[i] = {u, v} means u -> v.
     * Adjacency lists hold plain neighbors; poll() consumes the edge.
     */
    public List<Integer> findDirected(int[][] edges, int start) {
        Map<Integer, Deque<Integer>> graph = new HashMap<>();
        for (int[] e : edges) {
            graph.computeIfAbsent(e[0], k -> new ArrayDeque<>()).push(e[1]);
        }
        List<Integer> path = new ArrayList<>();
        dfsDirected(graph, start, path);
        Collections.reverse(path);
        return path;
    }

    private void dfsDirected(Map<Integer, Deque<Integer>> graph, int u,
            List<Integer> path) {
        Deque<Integer> neighbors = graph.get(u);
        while (neighbors != null && !neighbors.isEmpty()) {
            int v = neighbors.poll(); // take & delete the edge — done
            dfsDirected(graph, v, path);
        }
        path.add(u); // dead end -> post-order append
    }

    // ==================== UNDIRECTED ====================

    /**
     * Undirected graph. edges[i] = {u, v} means u -- v.
     * Each edge stored in both lists with a shared edgeId;
     * marking the id used invalidates both copies.
     */
    public List<Integer> findUndirected(int[][] edges, int start) {
        Map<Integer, Deque<int[]>> graph = new HashMap<>();
        for (int i = 0; i < edges.length; i++) {
            int u = edges[i][0], v = edges[i][1];
            graph.computeIfAbsent(u, k -> new ArrayDeque<>()).push(new int[] { v, i });
            graph.computeIfAbsent(v, k -> new ArrayDeque<>()).push(new int[] { u, i });
        }
        boolean[] usedEdge = new boolean[edges.length];
        List<Integer> path = new ArrayList<>();
        dfsUndirected(graph, start, usedEdge, path);
        Collections.reverse(path);
        return path;
    }

    private void dfsUndirected(Map<Integer, Deque<int[]>> graph, int u,
            boolean[] usedEdge, List<Integer> path) {
        Deque<int[]> neighbors = graph.get(u);
        while (neighbors != null && !neighbors.isEmpty()) {
            int[] edge = neighbors.peek(); // edge = {neighbor, edgeId}
            if (usedEdge[edge[1]]) {
                neighbors.pop(); // stale reverse copy — discard
                continue;
            }
            usedEdge[edge[1]] = true; // kills both copies of this edge
            neighbors.pop();
            dfsUndirected(graph, edge[0], usedEdge, path);
        }
        path.add(u);
    }

    // ==================== DEMO ====================

    public static void main(String[] args) {
        EulerPathFinder finder = new EulerPathFinder();

        // Directed circuit: 0->1, 1->2, 2->0
        int[][] directed = { { 0, 1 }, { 1, 2 }, { 2, 0 } };
        System.out.println(finder.findDirected(directed, 0));
        // [0, 1, 2, 0]

        // Undirected path: 0--1, 1--2, 2--0, 0--3 (odd vertices: 0 and 3)
        int[][] undirected = { { 0, 1 }, { 1, 2 }, { 2, 0 }, { 0, 3 } };
        System.out.println(finder.findUndirected(undirected, 0));
        // e.g. [0, 1, 2, 0, 3]
    }
}
