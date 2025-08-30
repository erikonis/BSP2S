import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;

public class Main {

	static PrintWriter out;
	static InputReader ir;

	static void solve() {
		int n = ir.nextInt();
		int m = ir.nextInt();
		int l = ir.nextInt();
		Graph[] g = new Graph[n];
		for (int i = 0; i < n; i++)
			g[i] = new Graph();
		for (int i = 0; i < m; i++) {
			int a = ir.nextInt() - 1;
			int b = ir.nextInt() - 1;
			int c = ir.nextInt();
			if (c <= l) {
				g[a].add(new long[] { b, c });
				g[b].add(new long[] { a, c });
			}
		}
		long[][][] dist = new long[n][][];
		for (int i = 0; i < n; i++) {
			dist[i] = dijkstra(i, g, l);
		}
		int q = ir.nextInt();
		for (int i = 0; i < q; i++) {
			int s = ir.nextInt() - 1;
			int t = ir.nextInt() - 1;
			if (dist[s][t][0] >= 1L << 60) {
				out.println(-1);
			} else {
				out.println(dist[s][t][0]);
			}
		}
	}

	public static long[][] dijkstra(int s, Graph[] g, int l) {
		long[][] d = new long[g.length][2];
		PriorityQueue<long[]> pq = new PriorityQueue<long[]>(new Comparator<long[]>() {
			public int compare(long[] a, long[] b) {
				if (a[1] != b[1])
					Long.compare(a[1], b[1]);
				return -Long.compare(a[2], b[2]);
			}
		});
		for (int i = 0; i < d.length; i++) {
			Arrays.fill(d[i], 1L << 60);
		}
		d[s][0] = 0;
		d[s][1] = l;
		pq.offer(new long[] { s, 0, l });
		while (!pq.isEmpty()) {
			long[] p = pq.poll();
			int from = (int) p[0];
			if (d[from][0] != p[1] || d[from][1] != p[2])
				continue;
			for (int i = 0; i < g[from].size(); i++) {
				long[] e = g[from].get(i);
				int to = (int) e[0];
				long nxt = e[1] > p[2] ? d[from][0] + 1 : d[from][0];
				long nxo = e[1] > p[2] ? l - e[1] : p[2] - e[1];
				if (d[to][0] > nxt || (d[to][0] == nxt && d[to][1] < nxo)) {
					d[to][0] = nxt;
					d[to][1] = nxo;
					pq.offer(new long[] { to, d[to][0], d[to][1] });
				}
			}
		}
		return d;
	}

	static class Graph extends ArrayList<long[]> {
	}

	public static void main(String[] args) {
		ir = new InputReader(System.in);
		out = new PrintWriter(System.out);
		solve();
		out.flush();
	}

	static class InputReader {

		private InputStream in;
		private byte[] buffer = new byte[1024];
		private int curbuf;
		private int lenbuf;

		public InputReader(InputStream in) {
			this.in = in;
			this.curbuf = this.lenbuf = 0;
		}

		public boolean hasNextByte() {
			if (curbuf >= lenbuf) {
				curbuf = 0;
				try {
					lenbuf = in.read(buffer);
				} catch (IOException e) {
					throw new InputMismatchException();
				}
				if (lenbuf <= 0)
					return false;
			}
			return true;
		}

		private int readByte() {
			if (hasNextByte())
				return buffer[curbuf++];
			else
				return -1;
		}

		private boolean isSpaceChar(int c) {
			return !(c >= 33 && c <= 126);
		}

		private void skip() {
			while (hasNextByte() && isSpaceChar(buffer[curbuf]))
				curbuf++;
		}

		public boolean hasNext() {
			skip();
			return hasNextByte();
		}

		public String next() {
			if (!hasNext())
				throw new NoSuchElementException();
			StringBuilder sb = new StringBuilder();
			int b = readByte();
			while (!isSpaceChar(b)) {
				sb.appendCodePoint(b);
				b = readByte();
			}
			return sb.toString();
		}

		public int nextInt() {
			if (!hasNext())
				throw new NoSuchElementException();
			int c = readByte();
			while (isSpaceChar(c))
				c = readByte();
			boolean minus = false;
			if (c == '-') {
				minus = true;
				c = readByte();
			}
			int res = 0;
			do {
				if (c < '0' || c > '9')
					throw new InputMismatchException();
				res = res * 10 + c - '0';
				c = readByte();
			} while (!isSpaceChar(c));
			return (minus) ? -res : res;
		}

		public long nextLong() {
			if (!hasNext())
				throw new NoSuchElementException();
			int c = readByte();
			while (isSpaceChar(c))
				c = readByte();
			boolean minus = false;
			if (c == '-') {
				minus = true;
				c = readByte();
			}
			long res = 0;
			do {
				if (c < '0' || c > '9')
					throw new InputMismatchException();
				res = res * 10 + c - '0';
				c = readByte();
			} while (!isSpaceChar(c));
			return (minus) ? -res : res;
		}

		public double nextDouble() {
			return Double.parseDouble(next());
		}

		public int[] nextIntArray(int n) {
			int[] a = new int[n];
			for (int i = 0; i < n; i++)
				a[i] = nextInt();
			return a;
		}

		public long[] nextLongArray(int n) {
			long[] a = new long[n];
			for (int i = 0; i < n; i++)
				a[i] = nextLong();
			return a;
		}

		public char[][] nextCharMap(int n, int m) {
			char[][] map = new char[n][m];
			for (int i = 0; i < n; i++)
				map[i] = next().toCharArray();
			return map;
		}
	}

	static void tr(Object... o) {
		out.println(Arrays.deepToString(o));
	}
}