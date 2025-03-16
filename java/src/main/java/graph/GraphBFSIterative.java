package graph;

import java.util.*;

public class GraphBFSIterative {
    public List<Integer> bfsIterative(Map<Integer, List<Integer>> graph, int startNode) {
        // There is no need to use class members for iterative traversal
        List<Integer> result = new ArrayList<>();
        Queue<Integer> queue = new LinkedList<>();
        Set<Integer> visited = new HashSet<>();

        queue.add(startNode);
        visited.add(startNode);

        while (!queue.isEmpty()) {
            int currentNode = queue.poll();
            result.add(currentNode);

            List<Integer> neighbors = graph.get(currentNode);
            if (neighbors != null) {
                for (int i = 0; i < neighbors.size(); i++) {
                    int neighbor = neighbors.get(i);
                    if (!visited.contains(neighbor)) {
                        queue.add(neighbor);
                        visited.add(neighbor);
                    }
                }
            }
        }

        return result;
    }

    public static void main(String[] args) {
        Map<Integer, List<Integer>> graph = new HashMap<>();
        graph.put(0, Arrays.asList(1, 2));
        graph.put(1, Arrays.asList(2, 3));
        graph.put(2, Arrays.asList(0, 3));
        graph.put(3, Arrays.asList(1, 2, 4));
        graph.put(4, Arrays.asList(3));

        System.out.println("BFS Traversal (Iterative) starting from node 0:");
        GraphBFSIterative graphBFSIterative = new GraphBFSIterative();
        List<Integer> result = graphBFSIterative.bfsIterative(graph, 0);
        System.out.println("result = " + result);
    }
}