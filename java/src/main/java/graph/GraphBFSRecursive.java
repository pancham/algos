package graph;

import java.util.*;

public class GraphBFSRecursive {

    // Note that making these variables member variables instead of passing on the call stack during recursive call
    // to reduce the call stack size
    List<Integer> result;
    Queue<Integer> queue;
    Set<Integer> visited;

    // Recursive BFS returning a List of visited nodes
    public List<Integer> bfsRecursive(Map<Integer, List<Integer>> graph, int startNode) {
        result = new ArrayList<>();
        queue = new LinkedList<>();
        visited = new HashSet<>();

        queue.add(startNode);
        visited.add(startNode);
        result.add(startNode);

        bfsRecursiveHelper(graph);
        return result;
    }

    private void bfsRecursiveHelper(Map<Integer, List<Integer>> graph) {
        if (queue.isEmpty()) {
            return;
        }

        int currentNode = queue.poll();
        List<Integer> neighbors = graph.get(currentNode);
        if (neighbors != null) {
            for (int neighbor : neighbors) {
                if (!visited.contains(neighbor)) {
                    queue.add(neighbor);
                    visited.add(neighbor);
                    result.add(neighbor);
                }
            }
        }
        bfsRecursiveHelper(graph);
    }

    public static void main(String[] args) {
        Map<Integer, List<Integer>> graph = new HashMap<>();
        graph.put(0, Arrays.asList(1, 2));
        graph.put(1, Arrays.asList(2, 3));
        graph.put(2, Arrays.asList(0, 3));
        graph.put(3, Arrays.asList(1, 2, 4));
        graph.put(4, Arrays.asList(3));

        System.out.println("BFS Traversal (Recursive) starting from node 0:");
        GraphBFSRecursive graphBFS = new GraphBFSRecursive();
        List<Integer> recursiveResult = graphBFS.bfsRecursive(graph, 0);
        System.out.println(recursiveResult);
    }
}
