import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class Main {

	static List<edge> graph;
	static int V;

	static int total;

	public static void main(String[] args) throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		String[] V_E = br.readLine().split(" ");

		V = Integer.parseInt(V_E[0]);

		int E = Integer.parseInt(V_E[1]);

		graph = new ArrayList<>();

		for (int i = 0; i < E; i++) {
			String[] edge = br.readLine().split(" ");

			int from = Integer.parseInt(edge[0]);

			int to = Integer.parseInt(edge[1]);

			int cost = Integer.parseInt(edge[2]);

			graph.add(new edge(from, to, cost));

		}

		System.out.println(kurapika(V));

	}

	static int kurapika(int v) {

		unionFindtree union_tree = new unionFindtree(v);
		Queue<edge> all_edge = new PriorityQueue<>(graph);
		total = 0;

		while (!(all_edge.isEmpty())) {
			edge e = all_edge.poll();
			if (!(union_tree.same(e.from, e.to))) {
				total += e.cost;
				union_tree.united(e.from, e.to);
			}

		}
		return total;

	}

	static class edge implements Comparable<edge> {
		int from;
		int to;
		int cost;

		edge(int a, int b, int c) {
			this.from = a;
			this.to = b;
			this.cost = c;

		}

		@Override
		public int compareTo(edge o) {
			// TODO ?????????????????????????????????????????????
			return this.cost - o.cost;
		}
	}

	static class unionFindtree {
		int[] parent;
		int[] rank;

		public unionFindtree(int n) {
			parent = new int[n];
			rank = new int[n];

			inite(n);
		}

		void inite(int n) {
			for (int i = 0; i < n; i++) {
				parent[i] = i;
				rank[i] = 0;
			}
		}

		int find(int x) {
			if (!(parent[x] == x)) {
				parent[x] = find(parent[x]);
			}
			return parent[x];
		}

		boolean same(int x, int y) {
			return find(x) == find(y);
		}

		void united(int x, int y) {
			int xRoot = parent[x];
			int yRoot = parent[y];

			if (rank[xRoot] > rank[yRoot]) {
				parent[yRoot] = xRoot;
			} else if (rank[xRoot] < rank[yRoot]) {
				parent[xRoot] = yRoot;
			} else if (xRoot != yRoot) {
				parent[yRoot] = xRoot;
				rank[xRoot]++;

			}
		}
	}

}