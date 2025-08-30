package test;

import java.util.*;

class Test1 {
    public static int bfsTraversal(Map<Integer, Integer> graph, int startNodeId) {
        Queue<Integer> queue = new LinkedList<>();
        Set<Integer> visited = new HashSet<>();

        queue.add(startNodeId);
        visited.add(startNodeId);

        int lastNode = -1;
        int prevNode = -1;
        while (!queue.isEmpty()) {
            prevNode = lastNode;
            int node = queue.poll(); // Remove the front element
            lastNode = node;
//            System.out.print(node + " "); // Process the node

            if (graph.containsKey(node)) {
                int nextNode = graph.get(node);
                if (!visited.contains(nextNode)) {
                    queue.add(nextNode);
                    visited.add(nextNode);
                } else {
                    return prevNode;
                }
            }
        }

        return lastNode;
    }

    public static int compute(int startNodeId, List<Integer> fromIds, List<Integer> toIds) {
        Map<Integer, Integer> graph = new HashMap<>();
        for (int i = 0; i < fromIds.size(); i++) {
            graph.put(fromIds.get(i), toIds.get(i));
        }

        return bfsTraversal(graph, startNodeId);
    }

    public static void main(String[] args) {
        // Graph where each node has only one outgoing connection
       List<Integer> fromIds = List.of(1, 7, 3, 4, 2, 6, 9);
       List<Integer> toIds = List.of(3,3,4,6,6,9,5);
        // Node 6 has no outgoing node

        System.out.println("BFS Traversal:" + compute(3, fromIds, toIds));
    }
}

