package misc;

import tree.TreeNode;
import tree.TreeUtils;

import java.util.*;

/**
 * The boundary of a binary tree is the concatenation of the root, the left boundary, the leaves ordered from left-to-right, and the reverse order of the right boundary.
 *
 * The left boundary is the set of nodes defined by the following:
 *
 * The root node's left child is in the left boundary. If the root does not have a left child, then the left boundary is empty.
 * If a node in the left boundary and has a left child, then the left child is in the left boundary.
 * If a node is in the left boundary, has no left child, but has a right child, then the right child is in the left boundary.
 * The leftmost leaf is not in the left boundary.
 * The right boundary is similar to the left boundary, except it is the right side of the root's right subtree. Again, the leaf is not part of the right boundary, and the right boundary is empty if the root does not have a right child.
 *
 * The leaves are nodes that do not have any children. For this problem, the root is not a leaf.
 *
 * Given the root of a binary tree, return the values of its boundary.
 *
 *
 *
 * Example 1:
 *
 *
 * Input: root = [1,null,2,3,4]
 * Output: [1,3,4,2]
 * Explanation:
 * - The left boundary is empty because the root does not have a left child.
 * - The right boundary follows the path starting from the root's right child 2 -> 4.
 *   4 is a leaf, so the right boundary is [2].
 * - The leaves from left to right are [3,4].
 * Concatenating everything results in [1] + [] + [3,4] + [2] = [1,3,4,2].
 * Example 2:
 *
 *
 * Input: root = [1,2,3,4,5,6,null,null,null,7,8,9,10]
 * Output: [1,2,4,7,8,9,10,6,3]
 * Explanation:
 * - The left boundary follows the path starting from the root's left child 2 -> 4.
 *   4 is a leaf, so the left boundary is [2].
 * - The right boundary follows the path starting from the root's right child 3 -> 6 -> 10.
 *   10 is a leaf, so the right boundary is [3,6], and in reverse order is [6,3].
 * - The leaves from left to right are [4,7,8,9,10].
 * Concatenating everything results in [1] + [2] + [4,7,8,9,10] + [6,3] = [1,2,4,7,8,9,10,6,3].
 *
 *
 * Constraints:
 *
 * The number of nodes in the tree is in the range [1, 104].
 * -1000 <= Node.val <= 1000
 */
public class BoundaryBinaryTree {

    public static void main(String[] args) {
        int[] nodes = new int[] {
//                1,-1,2,-1,-1,3,4  // returns [1,3,4,2]
//                1,2,3,4,5,6,-1,-1,-1,7,8,9,10   //
                1,2,7,3,5,-1,6,4 // returns [1,2,3,4,5,6,7], refer to binaryboundary-3.png
//                37,-34,-48,-1,-100,-100,48,-1,-1,-1,-1,-54,-1,-71,-22,-1,-1,-1,8
        };

        Map<Integer, TreeNode> map = TreeUtils.buildTree(nodes);
        TreeNode root = map.get(nodes[0]);

        BoundaryBinaryTree b = new BoundaryBinaryTree();
        List<Integer> bn = b.boundaryOfBinaryTree(root);
        System.out.println(bn);
    }

    public List<Integer> boundaryOfBinaryTree(TreeNode root) {
        List<Integer> bn = new ArrayList<>();

        List<Integer> ln = dfsLeft(root);
        List<Integer> rn = dfsRight(root);

        // Earlier, I did a bfs traversal to collect the leaf nodes. However, bfs traversal may not work if
        // the leaves at are at different levels. Refer to binaryboundary-3.png for such a tree structure.
        List<Integer> leaves =  dfsLeaves(root); // new ArrayList<>();
//        dfsRecursiveLeaves(root, leaves);

        bn.add(root.val);

        if (root.left != null || root.right != null) { // make sure that root node not the only node
            bn.addAll(ln);
            bn.addAll(leaves);
            Collections.reverse(rn);
            bn.addAll(rn);
        }

        return bn;
    }

    public List<Integer> dfsLeft(TreeNode root) {
        List<Integer> l = new ArrayList<>();

        TreeNode n = root.left;

        while (n != null) {
            if (n.left == null && n.right == null) { // reached leaf
                break;
            }
            l.add(n.val);

            if (n.left != null) {
                n = n.left;
            } else if (n.right != null) {
                n = n.right;
            }
        }

        return l;
    }

    public List<Integer> dfsRight(TreeNode root) {
        List<Integer> l = new ArrayList<>();

        TreeNode n = root.right;

        while (n != null) {
            if (n.left == null && n.right == null) {
                break;
            }
            l.add(n.val);

            if (n.right != null) {
                n = n.right;
            } else if (n.left != null) {
                n = n.left;
            }
        }

        return l;
    }

    public void dfsRecursiveLeaves(TreeNode n, List<Integer> l) {
        if (n == null || n.visited) {
            return;
        }

        n.visited = true;
        if (n.left == null && n.right == null) {
            l.add(n.val);
            return;
        }

        dfsRecursiveLeaves(n.left, l);
        dfsRecursiveLeaves(n.right, l);
    }

    public List<Integer> dfsLeaves(TreeNode root) {
        List<Integer> l = new ArrayList<>();

        Stack<TreeNode> st = new Stack<>();
        st.add(root);

        while (!st.isEmpty()) {
            TreeNode n = st.pop();
            if (!n.visited) { // add leaf node
                if (n.left == null && n.right == null) {
                    l.add(n.val);
                }

                if (n.right != null) {
                    st.add(n.right);
                }

                // VERY IMPORTANT to put left after the right, since we need to collect leaves from left to right
                if (n.left != null) {
                    st.add(n.left);
                }

                n.visited = true;
            }
        }

        return l;
    }
}
