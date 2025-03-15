package tree;

import java.util.ArrayList;
import java.util.List;

public class DFSPostOrderRecursive {

    List<Integer> result;

    // Depth-First Search (DFS) - Postorder (Left, Right, Root) - Recursive
    public List<Integer> dfsPostOrderRecursive(TreeNode root) {
        result = new ArrayList<>();
        dfsPostOrderHelper(root);
        return result;
    }

    private void dfsPostOrderHelper(TreeNode node) {
        if (node == null) {
            return;
        }
        dfsPostOrderHelper(node.left);
        dfsPostOrderHelper(node.right);
        result.add(node.val);
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

        DFSPostOrderRecursive traverser = new DFSPostOrderRecursive();
        System.out.println("DFS PostOrder (Recursive):");
        System.out.println(traverser.dfsPostOrderRecursive(root)); // Output: [4, 5, 2, 6, 7, 3, 1]
    }
}
