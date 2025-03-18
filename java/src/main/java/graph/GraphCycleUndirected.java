package graph;

import javax.swing.*;
import java.util.*;

public class GraphCycleUndirected {
    HashMap<Integer, List<Integer>> graph = new HashMap<>();
    Set<Integer> visited;

    public void addEdge(Integer from, Integer to) {
        graph.getOrDefault(from, new ArrayList<>()).add(to);
        graph.getOrDefault(to, new ArrayList<>()).add(from);
    }

    // Using Depth First Search (DFS)
    public boolean isCyclicDFS() {
        visited = new HashSet<>();

        for (int v: graph.keySet()) {
            if (!visited.contains(v)) {
                if (isCyclicUtilDFS(v, 1)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isCyclicUtilDFS(int v, int parent) {
        visited.add(v);

        List<Integer> neighbors = graph.get(v);

        if (neighbors != null) {
            for (int neighbor : neighbors) {
                if (!visited.contains(neighbor)) {
                    if (isCyclicUtilDFS(neighbor, v)) {
                        return true;
                    }
                } else if (neighbor != parent) {
                    // If the neighbor is visited and is not the parent of the current vertex,
                    // then there is a cycle.
                    return true;
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        GraphCycleUndirected graph1 = new GraphCycleUndirected();
        graph1.addEdge(0, 1);
        graph1.addEdge(1, 2);
        graph1.addEdge(2, 3);
        graph1.addEdge(3, 4);
        System.out.println("Graph 1 (No cycle) - DFS: " + graph1.isCyclicDFS());

        GraphCycleUndirected graph2 = new GraphCycleUndirected();
        graph2.addEdge(0, 1);
        graph2.addEdge(1, 2);
        graph2.addEdge(2, 0); // Cycle here
        System.out.println("\nGraph 2 (Cycle) - DFS: " + graph2.isCyclicDFS());

        GraphCycleUndirected graph3 = new GraphCycleUndirected();
        graph3.addEdge(0, 1);
        graph3.addEdge(2, 3);
        System.out.println("\nGraph 3 (Disconnected, No cycle) - DFS: " + graph3.isCyclicDFS());

        GraphCycleUndirected graph4 = new GraphCycleUndirected();
        graph4.addEdge(0, 1);
        graph4.addEdge(1, 2);
        graph4.addEdge(2, 0);
        graph4.addEdge(3, 4);
        graph4.addEdge(4, 3); // Cycle in a different component
        System.out.println("\nGraph 4 (Disconnected, with cycle) - DFS: " + graph4.isCyclicDFS());
    }
}
