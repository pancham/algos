package graph.path;

import java.util.*;

public class BellmanFord {

    // Time: O(V * E) — V-1 relaxation passes, each examining all E edges.
    // Space: O(V) — dist array.
    // Handles negative edge weights; detects negative cycles.
    // Prefer Dijkstra when all weights are non-negative — it is faster at O((V+E) log V).
    //
    // Edge format: {from, to, weight}.
    // Returns dist[] where dist[i] is the shortest distance from src to i;
    // Integer.MAX_VALUE means unreachable.
    // Throws IllegalArgumentException if a negative weight cycle is detected.
    public int[] shortestPath(int numVertices, int[][] edges, int src) {
        int[] dist = new int[numVertices];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[src] = 0;

        // Relax all edges V-1 times. After pass k, dist[v] holds the shortest
        // path to v using at most k edges.
        for (int i = 0; i < numVertices - 1; i++) {
            for (int[] edge : edges) {
                int u = edge[0], v = edge[1], w = edge[2];
                if (dist[u] != Integer.MAX_VALUE && dist[u] + w < dist[v]) {
                    dist[v] = dist[u] + w;
                }
            }
        }

        // V-th pass: if any distance still improves, a negative cycle exists.
        for (int[] edge : edges) {
            int u = edge[0], v = edge[1], w = edge[2];
            if (dist[u] != Integer.MAX_VALUE && dist[u] + w < dist[v]) {
                throw new IllegalArgumentException("Graph contains a negative weight cycle");
            }
        }

        return dist;
    }

    public static void main(String[] args) {
        BellmanFord bf = new BellmanFord();

        // Graph (directed, weighted) — same as Dijkstra example for easy comparison:
        //   0 --(1)--> 1 --(2)--> 3
        //   0 --(4)--> 2 --(1)--> 3
        //   0 --(7)--> 3
        // Shortest paths from 0: to 1 = 1, to 2 = 4, to 3 = 3 (via 0→1→3)
        int[][] edges = {
            {0, 1, 1},
            {0, 2, 4},
            {0, 3, 7},
            {1, 3, 2},
            {2, 3, 1}
        };

        int[] dist = bf.shortestPath(4, edges, 0);
        System.out.println("Shortest distances from node 0:");
        for (int i = 0; i < dist.length; i++) {
            System.out.println("  to " + i + ": " + (dist[i] == Integer.MAX_VALUE ? "unreachable" : dist[i]));
        }
        // Expected: to 0: 0, to 1: 1, to 2: 4, to 3: 3

        // Negative weight (no cycle) — Dijkstra would fail here, Bellman-Ford handles it.
        // 0 --(1)--> 1 --(-2)--> 2
        // Shortest to 2 = 1 + (-2) = -1
        int[][] negEdges = {{0, 1, 1}, {1, 2, -2}};
        int[] negDist = bf.shortestPath(3, negEdges, 0);
        System.out.println("\nWith negative edge (no cycle):");
        for (int i = 0; i < negDist.length; i++) {
            System.out.println("  to " + i + ": " + (negDist[i] == Integer.MAX_VALUE ? "unreachable" : negDist[i]));
        }
        // Expected: to 0: 0, to 1: 1, to 2: -1

        // Negative cycle detection
        int[][] negCycle = {{0, 1, 1}, {1, 2, -3}, {2, 0, 1}};
        try {
            bf.shortestPath(3, negCycle, 0);
        } catch (IllegalArgumentException e) {
            System.out.println("\nCorrectly detected: " + e.getMessage());
        }
    }
}
