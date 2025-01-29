package graph;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GoogleMinDistanceToFurthestNode {
    /**
     * https://www.fastprep.io/problems/google-find-min-distance-to-farthest-node
     * https://leetcode.com/discuss/interview-question/356378/
     * Question:
     * You are given a tree-shaped undirected graph consisting of n nodes labeled 1...n and n-1 edges.
     * The i-th edge connects nodes edges[i][0] and edges[i][1] together.
     * For a node x in the tree, let d(x) be the distance (the number of edges) from x to its farthest node.
     * Find the min value of d(x) for the given tree.
     * The tree has the following properties:
     *
     * It is connected.
     * It has no cycles.
     * For any pair of distinct nodes x and y in the tree, there's exactly 1 path connecting x and y.
     *
     * Solutions:
     * 1. Since this is an undirected tree, find diameter of the tree, and the solution is:
     *      a) if diameter is even, return (diameter/2)
     *      b) if diameter is odd, return (diameter/2) + 1
     *
     * 2. An inefficient solution, can be to find eccentricity of all nodes, and return the lowest value.
     */

    public static void main(String[] args) {
        // Since it is a tree, Diameter.treeLongestPathElements can be called to get the influencers on the list
        Map<Integer, List<Integer>> graph = new HashMap<>();
        Diameter diameter = new Diameter();
        int d = diameter.treeDiameter(graph);

        int min = 0;
        if (d % 2 == 0) {
            min = d/2;
        } else {
            min = d/2 + 1;
        }

        System.out.println("Min " + min);
    }
}
