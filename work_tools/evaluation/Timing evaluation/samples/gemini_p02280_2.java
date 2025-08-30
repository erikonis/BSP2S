import java.util.Scanner;

public class Main {

    static final int MAX_NODES = 25;
    static Node[] nodes = new Node[MAX_NODES];
    static int rootId;

    static class Node {
        int id;
        int parent;
        int left;
        int right;
        int depth;
        int height;
        String type;

        public Node(int id) {
            this.id = id;
            this.parent = -1;
            this.left = -1;
            this.right = -1;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        for (int i = 0; i < n; i++) {
            nodes[i] = new Node(i);
        }

        for (int i = 0; i < n; i++) {
            int id = sc.nextInt();
            int left = sc.nextInt();
            int right = sc.nextInt();

            nodes[id].left = left;
            nodes[id].right = right;

            if (left != -1) {
                nodes[left].parent = id;
            }
            if (right != -1) {
                nodes[right].parent = id;
            }
        }

        for (int i = 0; i < n; i++) {
            if (nodes[i].parent == -1) {
                rootId = i;
                break;
            }
        }

        setDepth(rootId, 0);
        setHeight(rootId);

        for (int i = 0; i < n; i++) {
            Node node = nodes[i];
            int degree = 0;
            if (node.left != -1) degree++;
            if (node.right != -1) degree++;

            String type;
            if (node.parent == -1) {
                type = "root";
            } else if (node.left == -1 && node.right == -1) {
                type = "leaf";
            } else {
                type = "internal node";
            }

            int sibling = -1;
            if (node.parent != -1) {
                Node parentNode = nodes[node.parent];
                if (parentNode.left == node.id && parentNode.right != -1) {
                    sibling = parentNode.right;
                } else if (parentNode.right == node.id && parentNode.left != -1) {
                    sibling = parentNode.left;
                }
            }

            System.out.printf("node %d: parent = %d, sibling = %d, degree = %d, depth = %d, height = %d, %s%n",
                    node.id, node.parent, sibling, degree, node.depth, node.height, type);
        }

        sc.close();
    }

    static void setDepth(int u, int d) {
        nodes[u].depth = d;
        if (nodes[u].left != -1) {
            setDepth(nodes[u].left, d + 1);
        }
        if (nodes[u].right != -1) {
            setDepth(nodes[u].right, d + 1);
        }
    }

    static int setHeight(int u) {
        int h1 = 0;
        int h2 = 0;
        if (nodes[u].left != -1) {
            h1 = setHeight(nodes[u].left) + 1;
        }
        if (nodes[u].right != -1) {
            h2 = setHeight(nodes[u].right) + 1;
        }
        nodes[u].height = Math.max(h1, h2);
        return nodes[u].height;
    }
}