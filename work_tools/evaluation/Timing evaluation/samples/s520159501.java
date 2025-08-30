import java.io.*;
import java.util.*;

public class Main {

	int[] p;
	int[] rank;
	
	void submit() {
		int n = nextInt();
		int m = nextInt();
		int[] cost = new int[n];
		for (int i = 0; i < n; i++) {
			cost[i] = nextInt();
		}
		
		p = new int[n];
		rank = new int[n];
		for (int i = 0; i < n; i++) {
			p[i] = i;
		}
		
		while (m-- > 0) {
			int v = nextInt();
			int u = nextInt();
			v = get(v);
			u = get(u);
			if (v == u) {
				continue;
			}
			if (rank[v] < rank[u]) {
				int tmp = v;
				v = u;
				u = tmp;
			}
			p[u] = v;
			if (rank[v] == rank[u]) {
				rank[v]++;
			}
		}
		
		Integer[] order = new Integer[n];
		for (int i = 0; i < n; i++) {
			order[i] = i;
		}
		
		Arrays.sort(order, (x, y) -> Integer.compare(cost[x], cost[y]));
		
		HashSet<Integer> comps = new HashSet<>();
		for (int i = 0; i < n; i++) {
			if (get(i) == i) {
				comps.add(i);
			}
		}
		
		if (comps.size() == 1) {
			out.println(0);
			return;
		}
		
		int need = 2 * comps.size() - 2;
		
		long ans = 0;
		
		boolean[] used = new boolean[n];
		
		for (int v : order) {
			if (comps.contains(p[v])) {
				ans += cost[v];
				comps.remove(p[v]);
				used[v] = true;
				need--;
			}
		}
		
		if (need < 0) {
			out.println("Impossible");
			return;
		}
		
		for (int v : order) {
			if (!used[v] && need > 0) {
				ans += cost[v];
				need--;
			}
		}
		
		if (need > 0) {
			out.println("Impossible");
		} else {
			out.println(ans);
		}
	}
	
	int get(int v) {
		return p[v] == v ? v : (p[v] = get(p[v]));
	}

	void preCalc() {

	}

	void stress() {

	}

	void test() {

	}

	Main() throws IOException {
		br = new BufferedReader(new InputStreamReader(System.in));
		out = new PrintWriter(System.out);
		preCalc();
		submit();
		// stress();
		// test();
		out.close();
	}

	static final Random rng = new Random();

	static int rand(int l, int r) {
		return l + rng.nextInt(r - l + 1);
	}

	public static void main(String[] args) throws IOException {
		new Main();
	}

	BufferedReader br;
	PrintWriter out;
	StringTokenizer st;

	String nextToken() {
		while (st == null || !st.hasMoreTokens()) {
			try {
				st = new StringTokenizer(br.readLine());
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return st.nextToken();
	}

	String nextString() {
		try {
			return br.readLine();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	int nextInt() {
		return Integer.parseInt(nextToken());
	}

	long nextLong() {
		return Long.parseLong(nextToken());
	}

	double nextDouble() {
		return Double.parseDouble(nextToken());
	}
}
