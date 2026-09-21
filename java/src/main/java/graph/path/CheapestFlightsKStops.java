package graph.path;

import java.util.*;

public class CheapestFlightsKStops {

    // Time: O(K * E) — K+1 relaxation passes, each examining all E edges.
    // Space: O(V) — dist array and a per-pass snapshot.
    //
    // Variant of Bellman-Ford capped at K+1 relaxation passes (K intermediate stops).
    //
    // Why the per-pass snapshot (prev) is required:
    //   Without it, a single pass could update dist[v] and then immediately use that
    //   updated value to relax dist[w] in the same pass — effectively chaining multiple
    //   edges in a single iteration. This violates the K-stop constraint.
    //
    //   Concrete Example (Edge Chaining Bug without prev):
    //     src = 0, dst = 2, k = 0 (direct flights only; at most 1 edge).
    //     Flights given in order:
    //       1. 0 -> 1 (cost 100)
    //       2. 1 -> 2 (cost 100)
    //       3. 0 -> 2 (cost 500)
    //
    //     - WITHOUT prev (in-place update on dist[] during Pass 0):
    //         * Edge (0 -> 1, 100): dist[1] becomes 100.
    //         * Edge (1 -> 2, 100): immediately reads dist[1] = 100, sets dist[2] = 200!
    //         * Edge (0 -> 2, 500): 500 < 200 is false.
    //         Result: dist[2] = 200 (Used 2 edges / 1 stop, violating k=0!).
    //
    //     - WITH prev (snapshot of dist[] at start of pass):
    //         * prev = [0, INF, INF]
    //         * Edge (0 -> 1, 100): prev[0] = 0, updates dist[1] = 100.
    //         * Edge (1 -> 2, 100): checks prev[1], which is INF. Skipped!
    //         * Edge (0 -> 2, 500): prev[0] = 0, updates dist[2] = 500.
    //         Result: dist[2] = 500 (Correct: exactly 1 edge used for k=0).
    //         Pass 1 (if k >= 1) would then snapshot dist, allowing edge 1 -> 2.
    //
    //     The snapshot freezes distances at the start of each pass, guaranteeing each
    //     iteration adds at most one edge regardless of edge order in flights[][].
    //
    // Flight format: {from, to, price}.
    // Returns the minimum cost to reach dst from src within K stops, or -1 if unreachable.
    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[src] = 0;

        // K stops = at most K+1 edges, so run K+1 passes.
        for (int i = 0; i <= k; i++) {
            // Snapshot distances before this pass so each pass uses exactly one new edge.
            int[] prev = Arrays.copyOf(dist, n);
            boolean updated = false;
            for (int[] flight : flights) {
                int u = flight[0], v = flight[1], w = flight[2];
                if (prev[u] != Integer.MAX_VALUE && prev[u] + w < dist[v]) {
                    dist[v] = prev[u] + w;
                    updated = true;
                }
            }
            // Early stopping: if no distance changed in this pass, shortest paths have settled.
            if (!updated) {
                break;
            }
        }

        return dist[dst] == Integer.MAX_VALUE ? -1 : dist[dst];
    }

    public static void main(String[] args) {
        CheapestFlightsKStops sol = new CheapestFlightsKStops();

        // Graph (same as Dijkstra/BellmanFord for easy comparison):
        //   0 --(1)--> 1 --(2)--> 3
        //   0 --(4)--> 2 --(1)--> 3
        //   0 --(7)--> 3
        int[][] flights = {
            {0, 1, 1},
            {0, 2, 4},
            {0, 3, 7},
            {1, 3, 2},
            {2, 3, 1}
        };

        // k=0: direct flights only. 0→3 = 7
        System.out.println("k=0, dst=3: " + sol.findCheapestPrice(4, flights, 0, 3, 0)); // 7

        // k=1: at most 1 stop. 0→1→3=3, 0→2→3=5, 0→3=7. Cheapest = 3
        System.out.println("k=1, dst=3: " + sol.findCheapestPrice(4, flights, 0, 3, 1)); // 3

        // k=2: same cheapest path still 3 (extra stops don't help here)
        System.out.println("k=2, dst=3: " + sol.findCheapestPrice(4, flights, 0, 3, 2)); // 3

        // k=0, dst=2: direct flight 0→2 exists, price=4
        System.out.println("k=0, dst=2: " + sol.findCheapestPrice(4, flights, 0, 2, 0)); // 4

        // Unreachable: no path from 3 back to 0
        System.out.println("unreachable: " + sol.findCheapestPrice(4, flights, 3, 0, 2)); // -1
    }
}
