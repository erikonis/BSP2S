import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    static class Node {
        Node left;
        Node right;

        public Node() {
            this.left = null;
            this.right = null;
        }
    }

    public static Node parseTree(String s) {
        if (s == null || s.isEmpty()) {
            return null;
        }
        return parseTree(s, 0).node;
    }

    private static ParseResult parseTree(String s, int index) {
        if (index >= s.length() || s.charAt(index) != '(') {
            return new ParseResult(null, index);
        }

        Node node = new Node();
        int currentIndex = index + 1; // Skip '('

        ParseResult leftResult = parseTree(s, currentIndex);
        node.left = leftResult.node;
        currentIndex = leftResult.nextIndex;

        currentIndex++; // Skip ','

        ParseResult rightResult = parseTree(s, currentIndex);
        node.right = rightResult.node;
        currentIndex = rightResult.nextIndex;

        currentIndex++; // Skip ')'

        return new ParseResult(node, currentIndex);
    }

    static class ParseResult {
        Node node;
        int nextIndex;

        public ParseResult(Node node, int nextIndex) {
            this.node = node;
            this.nextIndex = nextIndex;
        }
    }

    public static String serializeTree(Node node) {
        if (node == null) {
            return "";
        }
        return "(" + serializeTree(node.left) + "," + serializeTree(node.right) + ")";
    }

    public static Node intersect(Node t1, Node t2) {
        if (t1 == null || t2 == null) {
            return null;
        }
        Node result = new Node();
        result.left = intersect(t1.left, t2.left);
        result.right = intersect(t1.right, t2.right);
        return result;
    }

    public static Node union(Node t1, Node t2) {
        if (t1 == null && t2 == null) {
            return null;
        }
        Node result = new Node();
        result.left = union(t1 != null ? t1.left : null, t2 != null ? t2.left : null);
        result.right = union(t1 != null ? t1.right : null, t2 != null ? t2.right : null);
        return result;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(" ");
            char operation = parts[0].charAt(0);
            String tree1Str = parts[1];
            String tree2Str = parts[2];

            Node tree1 = parseTree(tree1Str);
            Node tree2 = parseTree(tree2Str);

            Node resultTree;
            if (operation == 'i') {
                resultTree = intersect(tree1, tree2);
            } else { // operation == 'u'
                resultTree = union(tree1, tree2);
            }
            System.out.println(serializeTree(resultTree));
        }
    }
}