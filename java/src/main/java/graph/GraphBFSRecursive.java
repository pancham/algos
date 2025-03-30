package graph;

import java.util.*;

public class GraphBFSRecursive {

    // Note that making these variables member variables instead of passing on the call stack during recursive call
    // to reduce the call stack size
    private Map<Integer, List<Integer>> graph;
    List<Integer> result;
    Set<Integer> visited;
    Queue<Integer> queue;

    public GraphBFSRecursive(Map<Integer, List<Integer>> graph) {
        this.graph = graph;
        this.visited = new HashSet<>();
        this.result = new ArrayList<>();
        this.queue = new LinkedList<>();
    }

    // Recursive BFS returning a List of visited nodes
    public List<Integer> bfsRecursive(int startNode) {
        visited.clear();
        result.clear();
        queue.clear();

        queue.add(startNode);
        visited.add(startNode);
        bfsRecursiveHelper();
        return result;
    }

    private void bfsRecursiveHelper() {
        if (queue.isEmpty()) {
            return;
        }

        int currentNode = queue.poll();
        result.add(currentNode);

        List<Integer> neighbors = graph.get(currentNode);
        if (neighbors != null) {
            for (int neighbor : neighbors) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }
        bfsRecursiveHelper();
    }

    public static void main(String[] args) {
        Map<Integer, List<Integer>> graph = new HashMap<>();
        graph.put(0, Arrays.asList(1, 2));
        graph.put(1, Arrays.asList(2, 3));
        graph.put(2, Arrays.asList(0, 3));
        graph.put(3, Arrays.asList(1, 2, 4));
        graph.put(4, Arrays.asList(3));

        System.out.println("BFS Traversal (Recursive) starting from node 0:");
        GraphBFSRecursive graphBFS = new GraphBFSRecursive(graph);
        List<Integer> recursiveResult = graphBFS.bfsRecursive(0);
        System.out.println(recursiveResult);
    }
}
