import java.util.Scanner;

class TreeNode {
    TreeNode left;
    TreeNode right;
    boolean hasValue;

    TreeNode() {
        this.left = null;
        this.right = null;
        this.hasValue = false;
    }
}

public class Main {
    static int index;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) continue;
            String[] parts = line.split(" ");
            char op = parts[0].charAt(0);
            String tree1Str = parts[1];
            String tree2Str = parts[2];

            TreeNode tree1 = parseTree(tree1Str);
            TreeNode tree2 = parseTree(tree2Str);
            TreeNode result = op == 'i' ? intersect(tree1, tree2) : union(tree1, tree2);
            System.out.println(serialize(result));
        }
        scanner.close();
    }

    private static TreeNode parseTree(String s) {
        index = 0;
        return parseHelper(s);
    }

    private static TreeNode parseHelper(String s) {
        TreeNode node = new TreeNode();
        if (index >= s.length() || s.charAt(index) != '(') {
            return node;
        }
        index++; // skip '('
        if (s.charAt(index) != ',') {
            node.left = parseHelper(s);
        }
        index++; // skip ','
        if (s.charAt(index) != ')') {
            node.right = parseHelper(s);
        }
        index++; // skip ')'
        node.hasValue = true;
        return node;
    }

    private static TreeNode intersect(TreeNode t1, TreeNode t2) {
        if (t1 == null || !t1.hasValue || t2 == null || !t2.hasValue) {
            return new TreeNode();
        }
        TreeNode node = new TreeNode();
        node.hasValue = true;
        node.left = intersect(t1.left, t2.left);
        node.right = intersect(t1.right, t2.right);
        return node;
    }

    private static TreeNode union(TreeNode t1, TreeNode t2) {
        if (t1 == null || !t1.hasValue) {
            return t2 != null && t2.hasValue ? copyTree(t2) : new TreeNode();
        }
        if (t2 == null || !t2.hasValue) {
            return t1 != null && t1.hasValue ? copyTree(t1) : new TreeNode();
        }
        TreeNode node = new TreeNode();
        node.hasValue = true;
        node.left = union(t1.left, t2.left);
        node.right = union(t1.right, t2.right);
        return node;
    }

    private static TreeNode copyTree(TreeNode t) {
        if (t == null || !t.hasValue) {
            return new TreeNode();
        }
        TreeNode node = new TreeNode();
        node.hasValue = true;
        node.left = copyTree(t.left);
        node.right = copyTree(t.right);
        return node;
    }

    private static String serialize(TreeNode node) {
        if (node == null || !node.hasValue) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        if (node.left != null && node.left.hasValue) {
            sb.append(serialize(node.left));
        }
        sb.append(",");
        if (node.right != null && node.right.hasValue) {
            sb.append(serialize(node.right));
        }
        sb.append(")");
        return sb.toString();
    }
}