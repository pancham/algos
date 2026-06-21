package graph;

import java.util.*;

/**
 * Graph definitions:
 * https://www.geeksforgeeks.org/graph-measurements-length-distance-diameter-eccentricity-radius-center/
 * The longest path between any two vertices is knows as the diameter of the
 * graph
 */
public class Diameter {

    /**
     * Finds eccentricity of a node/vertex. A node eccentricity is defined as the
     * maximum distance of
     * one node from another node.
     * Uses bfs to find node eccentricity.
     *
     * @return an int array, index 0 -> farthest node, 1 -> distance to the farthest
     *         node
     */
    private int[] nodeEccentricity(Map<Integer, List<Integer>> graph, int node) {
        Map<Integer, Integer> distance = new HashMap<>();
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(node);
        distance.put(node, 0);

        int farthestNode = node;
        while (!queue.isEmpty()) {
            int current = queue.poll();
            int currentDistance = distance.get(current);
            for (int neighbor : graph.getOrDefault(current, new ArrayList<>())) {
                if (!distance.containsKey(neighbor)) { // Unvisited
                    distance.put(neighbor, currentDistance + 1);
                    queue.offer(neighbor);
                    if (distance.get(neighbor) > distance.get(farthestNode)) {
                        farthestNode = neighbor;
                    }
                }
            }
        }

        return new int[] { farthestNode, distance.get(farthestNode) };
    }

    // BFS to find the path between two nodes
    private List<Integer> bfsPath(Map<Integer, List<Integer>> graph, int start, int end) {
        Queue<Integer> queue = new LinkedList<>();
        Map<Integer, Integer> parent = new HashMap<>();
        Set<Integer> visited = new HashSet<>();

        queue.offer(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            int current = queue.poll();
            if (current == end)
                break;

            for (int neighbor : graph.getOrDefault(current, new ArrayList<>())) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    parent.put(neighbor, current);
                    queue.offer(neighbor);
                }
            }
        }

        // Trace the path from end to start using the parent map
        List<Integer> path = new ArrayList<>();
        int current = end;
        while (current != start) {
            path.add(current);
            current = parent.get(current);
        }
        path.add(start);
        Collections.reverse(path);
        return path;
    }

    // Requires an undirected, connected (acyclic) tree — every node must be mutually reachable.
    // Uses the double-BFS (2-sweep) trick: BFS from any node finds one diameter endpoint;
    // BFS from that endpoint gives the diameter. Only correct for trees — cycles break the guarantee.
    // Time: O(V + E)  Space: O(V)
    // For directed or cyclic graphs use graphDiameter instead.
    public int treeDiameter(Map<Integer, List<Integer>> graph) {
        if (graph.isEmpty()) {
            return 0;
        }
        // pick any node
        int start = graph.keySet().stream().findFirst().get();

        // Step 1: find the farthest node, for a tree it is guaranteed to be one end of
        // the furthest point
        int[] ec1 = nodeEccentricity(graph, start);
        int farthestNode = ec1[0];

        // Step 2: Find the farthest node from the previously found node
        int[] ec2 = nodeEccentricity(graph, farthestNode);
        return ec2[1];
    }

    // Same constraints as treeDiameter — undirected connected tree only.
    // Time: O(V + E)  Space: O(V)
    public List<Integer> treeLongestPathElements(Map<Integer, List<Integer>> graph) {
        if (graph.isEmpty()) {
            return List.of();
        }
        // pick any node
        int start = graph.keySet().stream().findFirst().get();

        // Step 1: find the farthest node, for a tree it is guaranteed to be one end of
        // the furthest point
        int[] ec1 = nodeEccentricity(graph, start);
        int farthestNode = ec1[0];

        // Step 2: Find the farthest node from the previously found node
        int[] ec2 = nodeEccentricity(graph, farthestNode);
        int otherEndpoint = ec2[0];

        // Step 3: Trace the path between the two endpoints
        return bfsPath(graph, otherEndpoint, farthestNode);
    }

    // Works for any undirected graph — handles cycles and disconnected components.
    // Runs BFS from every node and tracks the global maximum shortest-path distance.
    // Time: O(V * (V + E))  Space: O(V)
    public int graphDiameter(Map<Integer, List<Integer>> graph) {
        // Graph may have disconnected components, so every node must be a BFS source.

        if (graph.isEmpty()) {
            return 0;
        }

        int farthestDistance = -1;

        for (int node : graph.keySet()) {
            int[] ec = nodeEccentricity(graph, node);
            if (ec[1] > farthestDistance) {
                farthestDistance = ec[1];
            }
        }

        return farthestDistance;
    }

    // Same constraints as graphDiameter — works for any undirected graph.
    // Time: O(V * (V + E))  Space: O(V)
    public List<Integer> graphLongestPathElements(Map<Integer, List<Integer>> graph) {
        if (graph.isEmpty()) {
            return List.of();
        }

        // Graph may have disconnected components, so every node must be a BFS source.
        int start = Integer.MIN_VALUE;
        int end = Integer.MIN_VALUE;
        int farthestDistance = -1;

        for (int node : graph.keySet()) {
            int[] ec = nodeEccentricity(graph, node);
            if (ec[1] > farthestDistance) {
                farthestDistance = ec[1];
                start = node;
                end = ec[0];
            }
        }

        return bfsPath(graph, start, end);
    }

    public static void main(String[] args) {
        // Define a sample graph as an adjacency list
        // Undirected graph, with 2 distinct set of nodes
        Map<Integer, List<Integer>> graph = new HashMap<>();
        graph.put(0, List.of(1, 2));
        graph.put(1, List.of(0, 3, 4));
        graph.put(2, List.of(0));
        graph.put(3, List.of(1));
        graph.put(4, List.of(1, 5));
        graph.put(5, List.of(4));

        graph.put(6, List.of(7, 8, 9, 10, 11, 12));
        graph.put(7, List.of(6, 8));
        graph.put(8, List.of(7, 9));
        graph.put(9, List.of(8, 10));
        graph.put(10, List.of(9, 11));
        graph.put(11, List.of(10, 12));

        Diameter diameter = new Diameter();
        List<Integer> diameterNodes = diameter.graphLongestPathElements(graph);
        System.out.println("Graph Nodes on the diameter: " + diameterNodes);

        int graphDiameter = diameter.graphDiameter(graph);
        System.out.println("Graph Diameter: " + graphDiameter);

        // A tree structure
        Map<Integer, List<Integer>> tree = new HashMap<>();
        tree.put(0, List.of(1, 2));
        tree.put(1, List.of(0, 3, 4));
        tree.put(2, List.of(0, 5, 6));
        tree.put(3, List.of(1, 7, 8));
        tree.put(4, List.of(1, 9, 10));
        tree.put(5, List.of(2, 11, 12));
        tree.put(6, List.of(2, 13));
        tree.put(7, List.of(3, 14));
        tree.put(8, List.of(3));
        tree.put(9, List.of(4));
        tree.put(10, List.of(4));
        tree.put(11, List.of(5));
        tree.put(12, List.of(5));
        tree.put(13, List.of(6));
        tree.put(14, List.of(7));
        diameterNodes = diameter.treeLongestPathElements(tree);
        System.out.println("Tree Nodes on the diameter: " + diameterNodes);

        int treeDiameter = diameter.treeDiameter(tree);
        System.out.println("Tree Diameter: " + treeDiameter);

    }
}
