import java.util.*;
import java.io.*;

public class Main {
	static class Node {
		int notNIL = 1;
		int id = -1;
		Node parent = NIL;
		Node left = NIL;
		Node right = NIL;
		Node sibling = NIL;
		int degree;
		int depth = -2;
		int height = -2;
	}
	static Node NIL = new Node();
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		int n = scanner.nextInt();
		Node[] tree = new Node[n + 1];
		NIL.notNIL = 0;
		NIL.depth = -1;
		NIL.height = -1;
		tree[0] = NIL;
		for (int i = 1; i < n + 1; i ++) {
			tree[i] = new Node();
		}

		for (int i = 0; i < n; i ++) {
			int id = scanner.nextInt();
			Node u = tree[id + 1];
			u.id = id;
			
			Node left = tree[scanner.nextInt() + 1];
			u.left = left;
			left.parent = u;
			
			Node right = tree[scanner.nextInt() + 1];
			u.right = right;
			right.parent = u;
			
			left.sibling = right;
			right.sibling = left;
			
			u.degree = left.notNIL + right.notNIL;
		}

		scanner.close();
		
		Node root;
		for (root = tree[1]; root.parent != NIL; root = root.parent);
		
		System.out.println("Preorder");
		preorder(root);
		System.out.println();
		System.out.println("Inorder");
		inorder(root);
		System.out.println();
		System.out.println("Postorder");
		postorder(root);
		System.out.println();
	}
	
	static void preorder(Node u) {
		if (u == NIL) return;
		
		System.out.print(" ");
		System.out.print(u.id);
		preorder(u.left);
		preorder(u.right);
	}
	
	static void inorder(Node u) {
		if (u == NIL) return;
		
		inorder(u.left);
		System.out.print(" ");
		System.out.print(u.id);
		inorder(u.right);
	}
	
	static void postorder(Node u) {
		if (u == NIL) return;
		
		postorder(u.left);
		postorder(u.right);
		System.out.print(" ");
		System.out.print(u.id);
	}
} 

