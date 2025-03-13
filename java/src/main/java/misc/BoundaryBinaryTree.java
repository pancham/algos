package misc;

import tree.TreeNode;
import tree.TreeUtils;

import java.util.*;

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
