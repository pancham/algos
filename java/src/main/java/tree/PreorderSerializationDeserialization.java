package tree;

import java.util.LinkedList;
import java.util.Queue;

public class PreorderSerializationDeserialization {

    // Serialization
    public static String serialize(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        serializeHelper(root, sb);
        return sb.toString();
    }

    private static void serializeHelper(TreeNode node, StringBuilder sb) {
        if (node == null) {
            sb.append("null").append(",");
            return;
        }
        sb.append(node.val).append(",");
        serializeHelper(node.left, sb);
        serializeHelper(node.right, sb);
    }

    // Deserialization
    public static TreeNode deserialize(String data) {
        if (data == null || data.isEmpty()) {
            return null;
        }
        String[] values = data.split(",");
        Queue<String> queue = new LinkedList<>();
        for (String val : values) {
            queue.offer(val);
        }
        return deserializeHelper(queue);
    }

    private static TreeNode deserializeHelper(Queue<String> queue) {
        String val = queue.poll();
        if (val.equals("null")) {
            return null;
        }
        TreeNode node = new TreeNode(Integer.parseInt(val));
        node.left = deserializeHelper(queue);
        node.right = deserializeHelper(queue);
        return node;
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

        // Serialize the tree
        String serialized = serialize(root);
        System.out.println("Serialized: " + serialized);

        // Deserialize the tree
        TreeNode deserializedRoot = deserialize(serialized);

        // Verify deserialization (Preorder traversal)
        System.out.println("Deserialized Preorder Traversal:");
        preorderTraversal(deserializedRoot);
    }

    // Helper method for preorder traversal to verify deserialization
    public static void preorderTraversal(TreeNode root) {
        if (root == null) {
            return;
        }
        System.out.print(root.val + " ");
        preorderTraversal(root.left);
        preorderTraversal(root.right);
    }
}