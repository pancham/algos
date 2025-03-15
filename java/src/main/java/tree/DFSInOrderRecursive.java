package tree;

import java.util.ArrayList;
import java.util.List;

public class DFSInOrderRecursive {
    List<Integer> result;

    // Depth-First Search (DFS) - Inorder (Left, Root, Right) - Recursive
    public List<Integer> dfsInOrderRecursive(TreeNode root) {
        result = new ArrayList<>();
        dfsInOrderHelper(root);
        return result;
    }

    private void dfsInOrderHelper(TreeNode node) {
        if (node == null) {
            return;
        }
        dfsInOrderHelper(node.left);
        result.add(node.val);
        dfsInOrderHelper(node.right);
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

        DFSInOrderRecursive traverser = new DFSInOrderRecursive();
        System.out.println("DFS IneOrder (Recursive):");
        System.out.println(traverser.dfsInOrderRecursive(root)); // Output: [4, 2, 5, 1, 6, 3, 7]
    }
}
