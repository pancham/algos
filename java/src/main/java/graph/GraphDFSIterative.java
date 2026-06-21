package graph;

import java.util.*;

class GraphDFSIterative {
    public List<Integer> dfsIterative(Map<Integer, List<Integer>> graph, int startNode) {
        Stack<Integer> stack = new Stack<>();
        Set<Integer> visited = new HashSet<>();
        List<Integer> result = new ArrayList<>();

        stack.push(startNode);
        visited.add(startNode);

        while (!stack.isEmpty()) {
            int currentNode = stack.pop();
            result.add(currentNode);

            List<Integer> neighbors = graph.get(currentNode);
            if (neighbors != null) {
                // Iterate neighbors in reverse so that the leftmost neighbor ends up on top of the stack.
                // Stack is LIFO: the last pushed is the first popped. Pushing right-to-left (e.g. [1,2] → push 2
                // then 1) leaves 1 on top, so 1 is explored first — matching the left-to-right order of recursive DFS.
                // Without this reversal, iterative DFS still visits all nodes but in a different order.
                for (int i = neighbors.size() - 1; i >= 0; i--) {
                    int neighbor = neighbors.get(i);
                    if (!visited.contains(neighbor)) {
                        stack.push(neighbor);
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

        // Using Iterative DFS
        GraphDFSIterative iterativeDFS = new GraphDFSIterative();
        System.out.println("DFS Traversal (Iterative) starting from node 0:");
        List<Integer> iterativeResult = iterativeDFS.dfsIterative(graph, 0);
        System.out.println(iterativeResult);
    }
}
