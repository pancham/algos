package misc;

import java.util.*;

/**
 * https://www.youtube.com/watch?v=LQh2g3ygCVU
 *
 * Given the root of a binary tree, the value of a target node target, and an integer k, return an array of the values of all nodes that have a distance k from the target node.
 *
 * You can return the answer in any order.
 *
 * Example 1:
 *
 * Input: root = [3,5,1,6,2,0,8,null,null,7,4], target = 5, k = 2
 * Output: [7,4,1]
 * Explanation: The nodes that are a distance 2 from the target node (with value 5) have values 7, 4, and 1.
 * Example 2:
 *
 * Input: root = [1], target = 1, k = 3
 * Output: []
 */
public class AllNodesDistanceKBTree {

    public static void main(String[] args) {
        int[] nodes = new int[] {
            3,5,1,6,2,0,8,-1,-1,7,4
        };
        int target = 5;
        int distance = 2;

        Map<Integer, TreeNode> treeNodes = new HashMap<>();
        for (int i = 0; i < nodes.length; i++) {
            if (nodes[i] == -1) {
                continue;
            }
            TreeNode n = treeNodes.get(nodes[i]);
            if (n == null) {
                n = new TreeNode(nodes[i]);
                treeNodes.put(nodes[i], n);
            }

            int li = 2 * i + 1;
            int ri = 2 * i + 2;

            if (li < nodes.length && nodes[li] != -1) {
                TreeNode l = new TreeNode(nodes[li]);
                n.left = l;
                treeNodes.put(nodes[li], l);
            }

            if (ri < nodes.length && nodes[ri] != -1) {
                TreeNode r = new TreeNode(nodes[ri]);
                n.right = r;
                treeNodes.put(nodes[ri], r);
            }
        }

        AllNodesDistanceKBTree t = new AllNodesDistanceKBTree();
        List<Integer> res = t.distanceK(treeNodes.get(nodes[0]), treeNodes.get(target), distance);
        System.out.println(res);
    }

    public List<Integer> distanceK(TreeNode root, TreeNode target, int k) {
        Map<TreeNode, TreeNode> parents = bfsParent(root);
        List<Integer> res = bfsDistance(parents, target, k);
        return res;
    }

     public static class TreeNode {
          int val;
          TreeNode left;
          TreeNode right;
          TreeNode(int x) { val = x; }

         @Override
         public boolean equals(Object o) {
             if (this == o) return true;
             if (o == null || getClass() != o.getClass()) return false;
             TreeNode treeNode = (TreeNode) o;
             return val == treeNode.val;
         }

         @Override
         public int hashCode() {
             return Objects.hash(val);
         }
     }

    Map<TreeNode,TreeNode> bfsParent(TreeNode root) {
        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);

        Set<TreeNode> visited = new HashSet<>();
        Map<TreeNode, TreeNode> map = new HashMap<>();
        while (!q.isEmpty()) {
            // Note: VERY IMPORTANT to take size in a variable, since the queue is being modified in the
            // for loop body
            int size = q.size();
            for (int i = 0; i < size; i++) {
                TreeNode n = q.poll();

                if (!visited.contains(n)) {
                    if (n.left != null) {
                        map.put(n.left, n);
                        q.add(n.left);
                    }

                    if (n.right != null) {
                        map.put(n.right, n);
                        q.add(n.right);
                    }
                    visited.add(n);
                }
            }
        }

        return map;
    }

    List<Integer> bfsDistance(Map<TreeNode, TreeNode> parents, TreeNode target, int k) {
        Queue<TreeNode> q = new LinkedList<>();
        q.add(target);

        Set<TreeNode> visited = new HashSet<>();
        int level = 0;
        while (!q.isEmpty() && level < k) {
            // process all nodes at this level
            int size = q.size();
            for (int i = 0; i < size; i++) {
                TreeNode n = q.poll();
                if (!visited.contains(n)) {
                    if (n.left != null && !visited.contains(n.left)) {
                        q.add(n.left);
                    }
                    if (n.right != null && !visited.contains(n.right)) {
                        q.add(n.right);
                    }

                    TreeNode parent = parents.get(n);
                    if (parent != null && !visited.contains(parent)) {
                        q.add(parent);
                    }

                    visited.add(n);
                }
            }
            level++;
        }

        List<Integer> res = new ArrayList<>();
        for (TreeNode n: q) {
            res.add(n.val);
        }

        return res;
    }


}
