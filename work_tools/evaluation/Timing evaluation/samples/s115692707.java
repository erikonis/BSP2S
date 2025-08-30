import java.util.Scanner;

public class Main {
	public static void main(String[] args) throws Exception {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		int m = sc.nextInt();
		int[] p = new int[n];
		for (int i = 0; i < n; i++) {
			p[i] = sc.nextInt() - 1;
		}
		int[] parent = new int[n];
		for (int i = 0; i < n; i++) {
			parent[i] = i;
		}
		for (int i = 0; i < m; i++) {
			int x = sc.nextInt() - 1;
			int y = sc.nextInt() - 1;
			union(parent, x, y);
		}
		sc.close();

		int ans = 0;
		for (int i = 0; i < n; i++) {
			if (find(parent, i) == find(parent, p[i])) {
				ans++;
			}
		}
		System.out.println(ans);
	}

	static void union(int[] parent, int x, int y) {
		int px = find(parent, x);
		int py = find(parent, y);
		if (px != py) {
			parent[px] = py;
		}
	}

	static int find(int[] parent, int x) {
		if (parent[x] == x) {
			return x;
		}
		parent[x] = find(parent, parent[x]);
		return parent[x];
	}
}
