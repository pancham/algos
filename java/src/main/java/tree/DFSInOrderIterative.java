package tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class DFSInOrderIterative {

    // Depth-First Search (DFS) - Inorder - Iterative using Stack
    public List<Integer> dfsInOrderIterative(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        TreeNode current = root;

        while (current != null || !stack.isEmpty()) {
            while (current != null) {
                stack.push(current);
                current = current.left;
            }
            current = stack.pop();
            result.add(current.val);
            current = current.right;
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

        DFSInOrderIterative traverser = new DFSInOrderIterative();
        System.out.println("DFS IneOrder (Iterative):");
        System.out.println(traverser.dfsInOrderIterative(root)); // Output: [4, 2, 5, 1, 6, 3, 7]
    }
}
