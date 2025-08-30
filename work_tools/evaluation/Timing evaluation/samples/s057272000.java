import java.util.Scanner;

public class Main {
	int size, edge[][];
	int queue[];
	int first, last;

	int[] topologicalSort() {
		int res[] = new int[size];
		int flg[] = new int[size];
		int id = 0;
		boolean isUpdate = true;
		while (isUpdate) {
			isUpdate = false;
			for (int v = 0; v < size; v++) {
				if (flg[v] == 1) continue;
				int u;
				for (u = 0; u < size; u++) {
					if (flg[u] == 0 && u != v && edge[u][v] == 1) {
						break;
					}
				}
				if (u == size) {
					res[id++] = v;
					flg[v] = 1;
					isUpdate = true;
				}
			}
		}
		if (id == size)
			return res;
		else
			return null;
	}

	void run() {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt(), m = sc.nextInt();
		size = n;
		edge = new int[n][n];
		for (int i = 0; i < m; i++) {
			int x = sc.nextInt() - 1;
			int y = sc.nextInt() - 1;
			edge[x][y] = 1;
		}
		int top[] = topologicalSort();
		if (top != null) {
			for (int i = 0; i < n; i++) {
				System.out.println(top[i] + 1);
			}
		} else {
			System.out.println("NA");
		}
	}

	public static void main(String[] args) {
		new Main().run();
	}
}