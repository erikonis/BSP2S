import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;

public class Main {

	static PrintWriter out;
	static InputReader ir;
	static boolean debug = false;
	static int s;

	static void solve() {
		for (;;) {
			int h = ir.nextInt();
			int w = ir.nextInt();
			s = ir.nextInt();
			if (h == 0 && w == 0)
				return;
			int[][] a = new int[h][];
			for (int i = 0; i < h; i++)
				a[i] = ir.nextIntArray(w);
			int[][] sum = getSumArray(a);
			int[][][][][] dp = new int[h + 1][w + 1][h + 1][w + 1][];
			int[] ret = dfs(0, 0, h, w, sum, dp);
			out.println(ret[0] + " " + (s - ret[1]));
		}
	}

	static int[] dfs(int lh, int lw, int rh, int rw, int[][] sum, int[][][][][] dp) {
		if (dp[lh][lw][rh][rw] != null)
			return dp[lh][lw][rh][rw];
		int[] ret = new int[] { -1, -1 };
		int t = sum[sum.length - 1][sum[0].length - 1] - sum(lh, lw, rh, rw, sum);
		if (sum[sum.length - 1][sum[0].length - 1] - sum(lh, lw, rh, rw, sum) <= s) {
			ret[0] = 1;
			ret[1] = t;
		} else
			return dp[lh][lw][rh][rw] = ret;
		for (int i = lh + 1; i < rh; i++) {
			int[] p = dfs(i, lw, rh, rw, sum, dp);
			int[] q = dfs(lh, lw, i, rw, sum, dp);
			if (p[0] > 0 && q[0] > 0 && p[0] + q[0] >= ret[0]) {
				if (p[0] + q[0] > ret[0]) {
					ret[0] = p[0] + q[0];
					ret[1] = Math.max(p[1], q[1]);
				} else if (ret[1] > Math.max(p[1], q[1])) {
					ret[0] = p[0] + q[0];
					ret[1] = Math.max(p[1], q[1]);
				}
			}
		}
		for (int i = lw + 1; i < rw; i++) {
			int[] p = dfs(lh, lw, rh, i, sum, dp);
			int[] q = dfs(lh, i, rh, rw, sum, dp);
			if (p[0] > 0 && q[0] > 0 && p[0] + q[0] >= ret[0]) {
				if (p[0] + q[0] > ret[0]) {
					ret[0] = p[0] + q[0];
					ret[1] = Math.max(p[1], q[1]);
				} else if (ret[1] > Math.max(p[1], q[1])) {
					ret[0] = p[0] + q[0];
					ret[1] = Math.max(p[1], q[1]);
				}
			}
		}
		return dp[lh][lw][rh][rw] = ret;
	}

	static int sum(int a, int b, int c, int d, int[][] s) {
		return s[c][d] - s[c][b] - s[a][d] + s[a][b];
	}

	public static int[][] getSumArray(int[][] a) {
		int h = a.length, w = a[0].length;
		int[][] ret = new int[h + 1][w + 1];
		for (int i = 0; i < h; i++)
			for (int j = 0; j < w; j++)
				ret[i + 1][j + 1] = a[i][j];
		for (int i = 0; i <= h; i++)
			for (int j = 0; j < w; j++)
				ret[i][j + 1] += ret[i][j];
		for (int i = 0; i < h; i++)
			for (int j = 0; j <= w; j++)
				ret[i + 1][j] += ret[i][j];
		return ret;
	}

	public static void main(String[] args) throws Exception {
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
		if (debug)
			out.println(Arrays.deepToString(o));
	}
}

