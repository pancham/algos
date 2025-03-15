package tree;

import java.util.ArrayList;
import java.util.List;

public class DFSPreOrderRecursive {
    List<Integer> result;

    // Depth-First Search (DFS) - Preorder (Root, Left, Right) - Recursive
    public List<Integer> dfsPreOrderRecursive(TreeNode root) {
        result = new ArrayList<>();
        dfsPreOrderHelper(root);
        return result;
    }

    private void dfsPreOrderHelper(TreeNode node) {
        if (node == null) {
            return;
        }
        result.add(node.val);
        dfsPreOrderHelper(node.left);
        dfsPreOrderHelper(node.right);
    }

    public static void main(String[] args) {
        // Create a sample binary tree
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        root.right.left = new TreeNode(6);
        root.right.right = new TreeNode(7);

        DFSPreOrderRecursive traverser = new DFSPreOrderRecursive();
        System.out.println("DFS Preorder (Recursive):");
        System.out.println(traverser.dfsPreOrderRecursive(root)); // Output: [1, 2, 4, 5, 3, 6, 7]
    }
}
