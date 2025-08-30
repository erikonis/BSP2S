import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	static Node nil = new Node();

	public static void main(String[] args) throws IOException {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in));) {
			int n = Integer.parseInt(br.readLine());
			Main main = new Main();
			main.init();
			for (int i = 0; i < n; i++) {
				String str[] = br.readLine().split(" ");
				switch (str[0]) {
				case "insert":
					main.insert(Integer.parseInt(str[1]));
					break;
				case "delete":
					main.deleteKey(Integer.parseInt(str[1]));
					break;
				case "deleteFirst":
					main.deleteFirst();
					break;
				case "deleteLast":
					main.deleteLast();
					break;
				}
			}
		}
		// リストのすべての要素を表示
		Node cur = nil.next;
		StringBuilder sb = new StringBuilder();
		for (int i = 0;; i++) {
			if (cur == nil)
				break;
			if (i == 0) {
				sb.append(cur.key);
			} else {
				sb.append(" " + cur.key);
			}
			cur = cur.next;
		}
		System.out.println(sb);

	}

	// リストの初期化
	void init() {
		nil.next = nil;
		nil.prev = nil;

	}

	// 要素の挿入
	void insert(int key) {
		Node x = new Node(key);
		x.next = nil.next;
		nil.next.prev = x;
		x.prev = nil;
		nil.next = x;

	}

	// 要素の探索
	Node listSearch(int key) {
		Node cur = nil.next;
		/* while (cur.next != nil && cur.key != key) { */
		/* cur.nextだとテストケース4で99999999が表示されなかった */
		while (cur != nil && cur.key != key) { // keyが一致するまで
			cur = cur.next;
		}
		return cur;
	}

	// 特定のノードを削除
	void deleteNode(Node t) {
		if (t != nil) { // 削除するノードが番兵でないこと
			t.prev.next = t.next;
			t.next.prev = t.prev;
			t = null;
		}
	}

	// 先頭の要素を削除
	void deleteFirst() {
		deleteNode(nil.next);
	}

	// 末尾の要素を削除
	void deleteLast() {
		deleteNode(nil.prev);
	}

	// 特定のキーを持つ要素を削除
	void deleteKey(int key) {
		// 検索したノードを削除
		deleteNode(listSearch(key));
	}

}

class Node {
	int key;
	Node prev;
	Node next;

	public Node() {
	}

	public Node(int key) {
		this.key = key;
	}

}
