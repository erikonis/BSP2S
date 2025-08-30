import java.io.*;
import java.util.*;

public class CarpentersLanguage {
    static class Node {
        Node left, right;
        int size;
        int sum, minPrefix, maxSuffix;
        char c;

        Node(char c, int size) {
            this.c = c;
            this.size = size;
            update();
        }

        void update() {
            sum = (c == '(' ? size : -size);
            minPrefix = Math.min(0, sum);
            maxSuffix = Math.max(0, sum);
            if (left != null) {
                sum += left.sum;
                minPrefix = Math.min(left.minPrefix, left.sum + minPrefix);
            }
            if (right != null) {
                sum += right.sum;
                maxSuffix = Math.max(maxSuffix + right.sum, right.maxSuffix);
            }
        }
    }

    static Random rand = new Random();

    static int size(Node n) {
        return n == null ? 0 : n.size + size(n.left) + size(n.right);
    }

    static void pull(Node n) {
        if (n == null) return;
        n.size = 1 + getSize(n.left) + getSize(n.right);
        n.sum = (n.c == '(' ? n.size : -n.size);
        n.minPrefix = Math.min(0, n.sum);
        n.maxSuffix = Math.max(0, n.sum);
        if (n.left != null) {
            n.sum += n.left.sum;
            n.minPrefix = Math.min(n.left.minPrefix, n.left.sum + n.minPrefix);
        }
        if (n.right != null) {
            n.sum += n.right.sum;
            n.maxSuffix = Math.max(n.maxSuffix + n.right.sum, n.right.maxSuffix);
        }
    }

    static int getSize(Node n) {
        return n == null ? 0 : n.size;
    }

    // Split treap into [0,k), [k,...) by position
    static Node[] split(Node t, int k) {
        if (t == null) return new Node[]{null, null};
        if (getSize(t.left) >= k) {
            Node[] res = split(t.left, k);
            t.left = res[1];
            pull(t);
            return new Node[]{res[0], t};
        } else {
            Node[] res = split(t.right, k - getSize(t.left) - t.size);
            t.right = res[0];
            pull(t);
            return new Node[]{t, res[1]};
        }
    }

    // Merge two treaps
    static Node merge(Node a, Node b) {
        if (a == null) return b;
        if (b == null) return a;
        if (rand.nextBoolean()) {
            a.right = merge(a.right, b);
            pull(a);
            return a;
        } else {
            b.left = merge(a, b.left);
            pull(b);
            return b;
        }
    }

    // Build a chain of n nodes with char c
    static Node build(char c, int n) {
        if (n == 0) return null;
        Node root = new Node(c, n);
        return root;
    }

    // Query for language validity in O(1): sum==0 and minPrefix==0
    static boolean isValid(Node t) {
        if (t == null) return true;
        return t.sum == 0 && t.minPrefix == 0 && t.maxSuffix == 0;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int q = Integer.parseInt(br.readLine());
        Node root = null;
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < q; ++i) {
            String[] parts = br.readLine().split("\\s+");
            int p = Integer.parseInt(parts[0]);
            char c = parts[1].charAt(0);
            int n = Integer.parseInt(parts[2]);
            Node[] leftMid = split(root, p);
            Node mid = build(c, n);
            root = merge(merge(leftMid[0], mid), leftMid[1]);
            out.append(isCarpentersLanguage(root) ? "Yes" : "No").append('\n');
        }
        System.out.print(out);
    }

    // Check for Carpenters language: # of ( == # of ), for any prefix, #) >= #( and #( >= #)
    static boolean isCarpentersLanguage(Node t) {
        if (t == null) return true;
        int sum = getSum(t);
        int minPref = getMinPrefix(t);
        int maxSuf = getMaxSuffix(t);
        return sum == 0 && minPref == 0 && maxSuf == 0;
    }

    // Get sum of ( as +1, ) as -1
    static int getSum(Node t) {
        return t == null ? 0 : t.sum;
    }

    // Get minimum prefix sum of t
    static int getMinPrefix(Node t) {
        return t == null ? 0 : t.minPrefix;
    }

    // Get maximum suffix sum of t
    static int getMaxSuffix(Node t) {
        return t == null ? 0 : t.maxSuffix;
    }
}