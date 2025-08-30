import java.util.*;

public class Main {
    static class Node {
        int key;
        Node left, right, parent;
        
        Node(int key) {
            this.key = key;
        }
    }
    
    static class BST {
        Node root;
        
        void insert(int key) {
            Node newNode = new Node(key);
            if (root == null) {
                root = newNode;
                return;
            }
            
            Node current = root;
            Node parent = null;
            
            while (current != null) {
                parent = current;
                if (key < current.key) {
                    current = current.left;
                } else {
                    current = current.right;
                }
            }
            
            newNode.parent = parent;
            if (key < parent.key) {
                parent.left = newNode;
            } else {
                parent.right = newNode;
            }
        }
        
        boolean find(int key) {
            Node current = root;
            while (current != null) {
                if (key == current.key) {
                    return true;
                } else if (key < current.key) {
                    current = current.left;
                } else {
                    current = current.right;
                }
            }
            return false;
        }
        
        void delete(int key) {
            Node z = findNode(key);
            if (z == null) return;
            
            if (z.left == null) {
                transplant(z, z.right);
            } else if (z.right == null) {
                transplant(z, z.left);
            } else {
                Node y = minimum(z.right);
                if (y.parent != z) {
                    transplant(y, y.right);
                    y.right = z.right;
                    y.right.parent = y;
                }
                transplant(z, y);
                y.left = z.left;
                y.left.parent = y;
            }
        }
        
        Node findNode(int key) {
            Node current = root;
            while (current != null) {
                if (key == current.key) {
                    return current;
                } else if (key < current.key) {
                    current = current.left;
                } else {
                    current = current.right;
                }
            }
            return null;
        }
        
        void transplant(Node u, Node v) {
            if (u.parent == null) {
                root = v;
            } else if (u == u.parent.left) {
                u.parent.left = v;
            } else {
                u.parent.right = v;
            }
            if (v != null) {
                v.parent = u.parent;
            }
        }
        
        Node minimum(Node node) {
            while (node.left != null) {
                node = node.left;
            }
            return node;
        }
        
        void inorder(Node node, List<Integer> result) {
            if (node != null) {
                inorder(node.left, result);
                result.add(node.key);
                inorder(node.right, result);
            }
        }
        
        void preorder(Node node, List<Integer> result) {
            if (node != null) {
                result.add(node.key);
                preorder(node.left, result);
                preorder(node.right, result);
            }
        }
        
        void print() {
            List<Integer> inorderResult = new ArrayList<>();
            List<Integer> preorderResult = new ArrayList<>();
            
            inorder(root, inorderResult);
            preorder(root, preorderResult);
            
            for (int key : inorderResult) {
                System.out.print(" " + key);
            }
            System.out.println();
            
            for (int key : preorderResult) {
                System.out.print(" " + key);
            }
            System.out.println();
        }
    }
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int m = sc.nextInt();
        BST bst = new BST();
        
        for (int i = 0; i < m; i++) {
            String operation = sc.next();
            
            if (operation.equals("insert")) {
                int key = sc.nextInt();
                bst.insert(key);
            } else if (operation.equals("find")) {
                int key = sc.nextInt();
                System.out.println(bst.find(key) ? "yes" : "no");
            } else if (operation.equals("delete")) {
                int key = sc.nextInt();
                bst.delete(key);
            } else if (operation.equals("print")) {
                bst.print();
            }
        }
        
        sc.close();
    }
}