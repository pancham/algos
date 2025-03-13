package tree;
import java.util.Objects;

public class TreeNode {
    public int val;
    public TreeNode left;
    public TreeNode right;

    // Sometimes, the val may not be unique across different nodes in a tree. In such cases, while dfs traversal,
    // a set cannot be used to determine if a node has been visited or not. For such cases, following attribute can be
    // used to determine if it has been visited or not.
    public boolean visited;

    TreeNode(int x) {
        val = x;
    }

    TreeNode(int x, TreeNode l, TreeNode r) {
        val = x;
        left = l;
        right = r;
    }

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
