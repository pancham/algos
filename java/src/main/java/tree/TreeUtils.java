package tree;

import misc.AllNodesDistanceKBTree;

import java.util.HashMap;
import java.util.Map;
import java.util.NavigableSet;

public class TreeUtils {

    public static Map<Integer, TreeNode> buildTree(int[] nodes) {
        Map<Integer, TreeNode> treeNodes = new HashMap<>();
        for (int i = 0; i < nodes.length; i++) {
            if (nodes[i] == -1) {
                continue;
            }
            TreeNode n = treeNodes.get(nodes[i]);
            if (n == null) {
                n = new TreeNode(nodes[i]);
                treeNodes.put(nodes[i], n);
            }

            int li = 2 * i + 1;
            int ri = 2 * i + 2;

            if (li < nodes.length && nodes[li] != -1) {
                TreeNode l = new TreeNode(nodes[li]);
                n.left = l;
                treeNodes.put(nodes[li], l);
            }

            if (ri < nodes.length && nodes[ri] != -1) {
                TreeNode r = new TreeNode(nodes[ri]);
                n.right = r;
                treeNodes.put(nodes[ri], r);
            }
        }

        return treeNodes;
    }
}
