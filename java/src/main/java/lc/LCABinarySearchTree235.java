package lc;

class LCABinarySearchTree235 {

    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        
        return findLCA(root, p, q);

    }

    TreeNode findLCA(TreeNode n, TreeNode p, TreeNode q) {
        if (n == null) return null;

        if (n.val < p.val && n.val < q.val) {
            return findLCA(n.right, p, q);
        }

        if (n.val > p.val && n.val > q.val) {
            return findLCA(n.left, p, q);
        }

        return n;
    }
}
