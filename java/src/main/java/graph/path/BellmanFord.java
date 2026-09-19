package graph.path;

import java.util.*;

public class BellmanFord {
    // Time: O(V * E) — V-1 relaxation passes over all edges in the adjacency list.
    // Space: O(V) — dist array.
    // Handles negative edge weights; detects negative cycles.
    //
    // Graph format: adjacency list where each entry is u -> List of {neighbor,
    // weight}, matching Dijkstra.
    // Returns dist[] where dist[i] is the shortest distance from src to i;
    // Integer.MAX_VALUE means unreachable.
    // Throws IllegalArgumentException if a negative weight cycle is detected.
    public int[] shortestPath(Map<Integer, List<int[]>> graph, int src, int numVertices) {
        int[] dist = new int[numVertices];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[src] = 0;

        // Relax all edges V-1 times.
        for (int i = 0; i < numVertices - 1; i++) {
            boolean updated = false;
            for (Map.Entry<Integer, List<int[]>> entry : graph.entrySet()) {
                int u = entry.getKey();
                if (u < 0 || u >= numVertices || dist[u] == Integer.MAX_VALUE)
                    continue;

                for (int[] edge : entry.getValue()) {
                    int v = edge[0], w = edge[1];
                    if (v >= 0 && v < numVertices && dist[u] + w < dist[v]) {
                        dist[v] = dist[u] + w;
                        updated = true;
                    }
                }
            }
            // Early stopping: if no distance was updated in this pass, shortest paths have
            // settled.
            if (!updated)
                break;
        }

        // V-th pass: if any distance still improves, a negative cycle exists reachable
        // from src.
        for (Map.Entry<Integer, List<int[]>> entry : graph.entrySet()) {
            int u = entry.getKey();
            if (u < 0 || u >= numVertices || dist[u] == Integer.MAX_VALUE)
                continue;

            for (int[] edge : entry.getValue()) {
                int v = edge[0], w = edge[1];
                if (v >= 0 && v < numVertices && dist[u] + w < dist[v]) {
                    throw new IllegalArgumentException("Graph contains a negative weight cycle");
                }
            }
        }

        return dist;
    }

    // Overload supporting (numVertices, graph, src) parameter ordering
    public int[] shortestPath(int numVertices, Map<Integer, List<int[]>> graph, int src) {
        return shortestPath(graph, src, numVertices);
    }

    public static void main(String[] args) {
        BellmanFord bf = new BellmanFord();

        // Same graph represented as Map<Integer, List<int[]>> (identical format to
        // Dijkstra.java):
        Map<Integer, List<int[]>> graph = new HashMap<>();
        graph.put(0, Arrays.asList(new int[] { 1, 1 }, new int[] { 2, 4 }, new int[] { 3, 7 }));
        graph.put(1, Arrays.asList(new int[] { 3, 2 }));
        graph.put(2, Arrays.asList(new int[] { 3, 1 }));
        graph.put(3, new ArrayList<>());

        int[] distMap = bf.shortestPath(graph, 0, 4);
        System.out.println("\nShortest distances from node 0 (using Map<Integer, List<int[]>>):");
        for (int i = 0; i < distMap.length; i++) {
            System.out.println("  to " + i + ": " + (distMap[i] == Integer.MAX_VALUE ? "unreachable" : distMap[i]));
        }

        // Negative weight with Map representation:
        Map<Integer, List<int[]>> negGraph = new HashMap<>();
        negGraph.put(0, List.of(new int[] { 1, 1 }));
        negGraph.put(1, List.of(new int[] { 2, -2 }));
        int[] negDistMap = bf.shortestPath(negGraph, 0, 3);
        System.out.println("\nWith negative edge using Map (no cycle):");
        for (int i = 0; i < negDistMap.length; i++) {
            System.out
                    .println("  to " + i + ": " + (negDistMap[i] == Integer.MAX_VALUE ? "unreachable" : negDistMap[i]));
        }

        // Negative cycle detection
        Map<Integer, List<int[]>> negCycleMap = new HashMap<>();
        negCycleMap.put(0, List.of(new int[] { 1, 1 }));
        negCycleMap.put(1, List.of(new int[] { 2, -3 }));
        negCycleMap.put(2, List.of(new int[] { 0, 1 }));
        try {
            bf.shortestPath(negCycleMap, 0, 3);
        } catch (IllegalArgumentException e) {
            System.out.println("Correctly detected (Map): " + e.getMessage());
        }
    }
}
