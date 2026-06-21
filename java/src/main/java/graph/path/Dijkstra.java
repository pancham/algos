package graph.path;

import java.util.*;

public class Dijkstra {

    // Time: O((V + E) log V) — each node extracted from the heap once, each edge relaxed once;
    //       log V factor comes from heap operations.
    // Space: O(V) — dist array and priority queue.
    // Requires non-negative edge weights. Use Bellman-Ford for graphs with negative weights.
    //
    // Graph format: adjacency list where each entry is {neighbor, weight}.
    // Returns dist[] where dist[i] is the shortest distance from src to i;
    // Integer.MAX_VALUE means unreachable.
    public int[] shortestPath(Map<Integer, List<int[]>> graph, int src, int numVertices) {
        int[] dist = new int[numVertices];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[src] = 0;

        // Min-heap: {node, distance}
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
        pq.offer(new int[]{src, 0});

        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int u = curr[0], d = curr[1];

            // Stale entry — a shorter path to u was already settled.
            if (d > dist[u]) continue;

            for (int[] edge : graph.getOrDefault(u, List.of())) {
                int v = edge[0], w = edge[1];
                if (dist[u] + w < dist[v]) {
                    dist[v] = dist[u] + w;
                    pq.offer(new int[]{v, dist[v]});
                }
            }
        }

        return dist;
    }

    public static void main(String[] args) {
        // Graph (directed, weighted):
        //   0 --(1)--> 1 --(2)--> 3
        //   0 --(4)--> 2 --(1)--> 3
        //   0 --(7)--> 3
        // Shortest paths from 0: to 1 = 1, to 2 = 4, to 3 = 3 (via 0→1→3)
        Map<Integer, List<int[]>> graph = new HashMap<>();
        graph.put(0, Arrays.asList(new int[]{1, 1}, new int[]{2, 4}, new int[]{3, 7}));
        graph.put(1, Arrays.asList(new int[]{3, 2}));
        graph.put(2, Arrays.asList(new int[]{3, 1}));
        graph.put(3, new ArrayList<>());

        Dijkstra dijkstra = new Dijkstra();
        int[] dist = dijkstra.shortestPath(graph, 0, 4);

        System.out.println("Shortest distances from node 0:");
        for (int i = 0; i < dist.length; i++) {
            System.out.println("  to " + i + ": " + (dist[i] == Integer.MAX_VALUE ? "unreachable" : dist[i]));
        }
        // Expected: to 0: 0, to 1: 1, to 2: 4, to 3: 3
    }
}
