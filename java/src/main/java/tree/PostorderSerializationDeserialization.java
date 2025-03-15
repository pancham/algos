package tree;

import java.util.Stack;


public class PostorderSerializationDeserialization {

    // Serialization (Postorder)
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
        serializeHelper(node.left, sb);
        serializeHelper(node.right, sb);
        sb.append(node.val).append(",");
    }

    // Deserialization (Postorder)
    public static TreeNode deserialize(String data) {
        if (data == null || data.isEmpty()) {
            return null;
        }
        String[] values = data.split(",");
        Stack<String> stack = new Stack<>();
        for (String val : values) {
            stack.push(val);
        }
        return deserializeHelper(stack);
    }

    private static TreeNode deserializeHelper(Stack<String> stack) {
        String val = stack.pop();
        if (val.equals("null")) {
            return null;
        }
        TreeNode node = new TreeNode(Integer.parseInt(val));
        node.right = deserializeHelper(stack);
        node.left = deserializeHelper(stack);
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

        // Verify deserialization (Postorder traversal)
        System.out.println("Deserialized Postorder Traversal:");
        postorderTraversal(deserializedRoot);
    }

    // Helper method for postorder traversal to verify deserialization
    public static void postorderTraversal(TreeNode root) {
        if (root == null) {
            return;
        }
        postorderTraversal(root.left);
        postorderTraversal(root.right);
        System.out.print(root.val + " ");
    }
}