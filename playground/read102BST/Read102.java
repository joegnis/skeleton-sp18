package read102BST;

public class Read102 {
    public static boolean isBST(TreeNode p) {
        // From discussion 09
        return isBSTHelper(p, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    private static boolean isBSTHelper(TreeNode p, int min, int max) {
        if (p == null) {
            return true;
        }

        int val = p.val;
        if (val <= min || val >= max) {
            return false;
        }

        return isBSTHelper(p.left, min, val) && isBSTHelper(p.right, val, max);
    }
}
