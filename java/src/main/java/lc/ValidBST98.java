package lc;

public class ValidBST98 {

    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    public boolean isValidBST(TreeNode root) {
        return validate(root, null, null);
    }

    private boolean validate(TreeNode node, Integer low, Integer high) {
        if (node == null) {
            return true;
        }

        int val = node.val;
        if (low != null && val <= low) {
            return false;
        }
        if (high != null && val >= high) {
            return false;
        }

        if (!validate(node.right, val, high)) {
            return false;
        }
        if (!validate(node.left, low, val)) {
            return false;
        }
        return true;
    }

}
