package read102BST;

import java.util.*;

public class TreeNode {
    public int val;
    public TreeNode left;
    public TreeNode right;

    public TreeNode() {
    }

    public TreeNode(int val) {
        this.val = val;
    }

    public TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }

    public static TreeNode of(Integer... values) {
        Queue<TreeNode> nodeQueue = new ArrayDeque<>();
        TreeNode root;
        if (values.length > 0) {
            root = new TreeNode(values[0]);
            nodeQueue.add(root);
        } else {
            throw new NoSuchElementException("empty arguments");
        }

        int i = 1;
        while (!nodeQueue.isEmpty() && i < values.length) {
            TreeNode node = nodeQueue.poll();
            if (values[i] != null) {
                node.left = new TreeNode(values[i]);
                nodeQueue.offer(node.left);
            }
            i++;

            if (i >= values.length) {
                break;
            }

            if (values[i] != null) {
                node.right = new TreeNode(values[i]);
                nodeQueue.offer(node.right);
            }
            i++;
        }

        return root;
    }

    public TreeNode findFirst(int target) {
        return findFirst(this, target);
    }

    private static TreeNode findFirst(TreeNode root, int target) {
        if (root == null) {
            return null;
        }

        if (root.val == target) {
            return root;
        }

        TreeNode resultLeft = findFirst(root.left, target);
        if (resultLeft != null) {
            return resultLeft;
        }
        return findFirst(root.right, target);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof TreeNode)) {
            return false;
        }
        Stack<TreeNode> stackThis = new Stack<>();
        Stack<TreeNode> stackOther = new Stack<>();
        stackThis.push(this);
        stackOther.push((TreeNode) other);

        while (!stackThis.isEmpty() && !stackOther.isEmpty()) {
            TreeNode nodeThis = stackThis.pop();
            TreeNode nodeOther = stackOther.pop();
            if (nodeThis.val != nodeOther.val) {
                return false;
            }

            if (nodeThis.left != null) stackThis.push(nodeThis.left);
            if (nodeThis.right != null) stackThis.push(nodeThis.right);
            if (nodeOther.left != null) stackOther.push(nodeOther.left);
            if (nodeOther.right != null) stackOther.push(nodeOther.right);
        }

        return stackThis.isEmpty() && stackOther.isEmpty();
    }

    @Override
    public String toString() {
        Queue<TreeNode> nodeQueue = new LinkedList<>();  // ArrayDeque doesn't support null elements
        StringBuilder result = new StringBuilder();

        nodeQueue.offer(this);
        while (!nodeQueue.isEmpty()) {
            TreeNode node = nodeQueue.poll();
            result.append(",");
            if (node == null) {
                result.append("null");
            } else {
                result.append(node.val);
                nodeQueue.add(node.left);
                nodeQueue.add(node.right);
            }
        }

        result.replace(0, 1, "(");
        // Removes trailing nulls
        int lastNonNull = result.length() - 1;
        while (result.charAt(lastNonNull - 1) == 'l') {
            result.delete(lastNonNull - 4, lastNonNull + 1);
            lastNonNull -= 5;
        }
        result.append(")");

        return result.toString();
    }
}
