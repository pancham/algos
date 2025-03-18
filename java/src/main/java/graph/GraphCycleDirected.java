package graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GraphCycleDirected {
    HashMap<Integer, List<Integer>> graph;
    Set<Integer> visited;

    /**
     * Using Depth First Search (DFS)
     *
     * In directed graph, a cycle exists if a node is encountered at least twice while visiting all connected
     * from a starting node.
     *
     * <pre>
     *     <img src="./graphcycledirected.webp" />
     * </pre>
     *
     * In the above graph, 2 cycles exist when we start from node 0:
     *  0 -> 2 -> 0
     *  0 -> 1 -> 2 -> 0
     *
     * @return
     */
    public boolean isCyclicDFS() {
        visited = new HashSet<>();
        // Recursion stack (or visiting stack) maintains items that are being visited for the current node, after the
        // call returns for the current it will be empty, refer to {@link#isCyclicUtilDFS} method. The item being
        // visited is added to the visiting stack when its exploration is started and then removed from the stack
        // when done.
        // Note that the recursion stack maintains items that are unique for each recursive call, hence it is passed
        // in as parameter in the recursive call
        Set<Integer> visitingStack = new HashSet<>();

        // Call the recursive helper function to detect cycle in different connected components
        for (int v: graph.keySet()) {
            if (!visited.contains(v)) {
                if (isCyclicUtilDFS(v, visitingStack)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isCyclicUtilDFS(int v, Set<Integer> visitingStack) {
        visited.add(v);
        visitingStack.add(v);

        List<Integer> neighbors = graph.get(v);
        if (neighbors != null) {
            for (int neighbor : graph.get(v)) {
                if (!visited.contains(neighbor)) {
                    return isCyclicUtilDFS(neighbor, visitingStack);
                } else if (visitingStack.contains(neighbor)) {
                    // If the neighbor is already in the recursion stack,
                    // then there is a cycle.
                    return true;
                }
            }
        }

        visitingStack.remove(v); // Backtrack
        return false;
    }

    public static void main(String[] args) {
        HashMap<Integer, List<Integer>> graph = new HashMap<>();
        graph.put(0, List.of(1));
        graph.put(1, List.of(2));
        graph.put(2, List.of(3));

        GraphCycleDirected g = new GraphCycleDirected();
        g.graph = graph;
        boolean cycle = g.isCyclicDFS();
        System.out.println("cycle = " + cycle); // no cycle

        graph = new HashMap<>();
        graph.put(0, List.of(1, 2));
        graph.put(1, List.of(2));
        graph.put(2, List.of(0));
        g.graph = graph;
        cycle = g.isCyclicDFS();
        System.out.println("cycle = " + cycle); // cycle

        graph = new HashMap<>();
        graph.put(0, List.of(1));
        graph.put(2, List.of(3));g.graph = graph;
        g.graph = graph;
        cycle = g.isCyclicDFS();
        System.out.println("cycle = " + cycle); // no cycle

        graph = new HashMap<>();
        graph.put(0, List.of(1));
        graph.put(1, List.of(2));
        graph.put(2, List.of(0));
        graph.put(3, List.of(4));
        graph.put(4, List.of(3));
        g.graph = graph;
        cycle = g.isCyclicDFS();
        System.out.println("cycle = " + cycle); // cycle
    }

}
