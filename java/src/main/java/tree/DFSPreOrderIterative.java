package tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class DFSPreOrderIterative {

    // Depth-First Search (DFS) - Preorder - Iterative using Stack
    public List<Integer> dfsPreOrderIterative(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            result.add(node.val);

            if (node.right != null) {
                stack.push(node.right);
            }
            if (node.left != null) {
                stack.push(node.left);
            }
        }
        return result;
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

        DFSPreOrderIterative traverser = new DFSPreOrderIterative();
        System.out.println("DFS Pre)rder (Iterative):");
        System.out.println(traverser.dfsPreOrderIterative(root)); // Output: [1, 2, 4, 5, 3, 6, 7]
    }
}
