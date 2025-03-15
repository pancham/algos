package graph;

import java.util.*;

// Class for Recursive DFS
class GraphDFSRecursive {
    // Note that making these variables member variables instead of passing on the call stack during recursive call
    // to reduce the call stack size
    private Map<Integer, List<Integer>> graph;
    private Set<Integer> visited;
    private List<Integer> result;

    public GraphDFSRecursive(Map<Integer, List<Integer>> graph) {
        this.graph = graph;
        this.visited = new HashSet<>();
        this.result = new ArrayList<>();
    }

    public List<Integer> dfsRecursive(int startNode) {
        visited.clear();
        result.clear();
        dfsRecursiveHelper(startNode);
        return result;
    }

    private void dfsRecursiveHelper(int node) {
        visited.add(node);
        result.add(node);

        List<Integer> neighbors = graph.get(node);
        if (neighbors != null) {
            for (int neighbor : neighbors) {
                if (!visited.contains(neighbor)) {
                    dfsRecursiveHelper(neighbor);
                }
            }
        }
    }

    public static void main(String[] args) {
        Map<Integer, List<Integer>> graph = new HashMap<>();
        graph.put(0, Arrays.asList(1, 2));
        graph.put(1, Arrays.asList(2, 3));
        graph.put(2, Arrays.asList(0, 3));
        graph.put(3, Arrays.asList(1, 2, 4));
        graph.put(4, Arrays.asList(3));

        // Using Recursive DFS
        GraphDFSRecursive graphDFS = new GraphDFSRecursive(graph);
        System.out.println("DFS Traversal (Recursive) starting from node 0:");
        List<Integer> recursiveResult = graphDFS.dfsRecursive(0);
        System.out.println(recursiveResult);
    }
}