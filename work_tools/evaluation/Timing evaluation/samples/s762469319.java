import static java.lang.Math.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Stream;

public class Main {

	public static void main(String[] args) throws IOException {
		InputStream inputStream = System.in;
		OutputStream outputStream = System.out;
		InputReader in = new InputReader(inputStream);
		PrintWriter out = new PrintWriter(outputStream);
		TaskX solver = new TaskX();
		solver.solve(1, in, out);
		out.close();
	}

	static int INF = 1 << 30;
	static long LINF = 1L << 55;
	static int MOD = 1000000007;
	static int[] mh4 = { 0, -1, 1, 0 };
	static int[] mw4 = { -1, 0, 0, 1 };
	static int[] mh8 = { -1, -1, -1, 0, 0, 1, 1, 1 };
	static int[] mw8 = { -1, 0, 1, -1, 1, -1, 0, 1 };

	@SuppressWarnings("unchecked")
	static class TaskX {

		public void solve(int testNumber, InputReader in, PrintWriter out) {

			int l = in.nextInt();

			int r = f(l);
			List<E>[] g = new ArrayList[r+1];
			g = Stream.generate(ArrayList::new).limit(r+1).toArray(List[]::new);

			int m = 0;
			for (int i = 0; i < r; i++) {
				g[i].add(new E(i+1, 0));
				g[i].add(new E(i+1, (int)Math.pow(2, i)));
				m += 2;
			}

			int min = (int)pow(2, r);
			int max = l-1;

			for (int i = r; i >= 0; i--) {
				if (min + (int)pow(2, i)-1 <= max) {
					g[i].add(new E(r, min));
					min += (int)pow(2, i);
					m++;
				}
			}

			out.printf("%d %d\n", r+1, m);
			for (int i = 0; i < r+1; i++) {
				for (int j = 0; j < g[i].size(); j++) {
					out.printf("%d %d %d\n", i+1, g[i].get(j).to+1, g[i].get(j).cost);
				}
			}

		}
	}

	static int f(int n) {
		int ret = 0;
		while (n / 2 > 0) {
			ret++;
			n /= 2;
		}
		return ret;
	}

	static class E {
		int to, cost;

		public E(int to, int cost) {
			super();
			this.to = to;
			this.cost = cost;
		}
	}

	static class InputReader {
		BufferedReader in;
		StringTokenizer tok;

		public String nextString() {
			while (!tok.hasMoreTokens()) {
				try {
					tok = new StringTokenizer(in.readLine(), " ");
				} catch (IOException e) {
					throw new InputMismatchException();
				}
			}
			return tok.nextToken();
		}

		public int nextInt() {
			return Integer.parseInt(nextString());
		}

		public long nextLong() {
			return Long.parseLong(nextString());
		}

		public double nextDouble() {
			return Double.parseDouble(nextString());
		}

		public int[] nextIntArray(int n) {
			int[] res = new int[n];
			for (int i = 0; i < n; i++) {
				res[i] = nextInt();
			}
			return res;
		}

		public int[] nextIntArrayDec(int n) {
			int[] res = new int[n];
			for (int i = 0; i < n; i++) {
				res[i] = nextInt() - 1;
			}
			return res;
		}

		public int[] nextIntArray1Index(int n) {
			int[] res = new int[n + 1];
			for (int i = 0; i < n; i++) {
				res[i + 1] = nextInt();
			}
			return res;
		}

		public long[] nextLongArray(int n) {
			long[] res = new long[n];
			for (int i = 0; i < n; i++) {
				res[i] = nextLong();
			}
			return res;
		}

		public long[] nextLongArrayDec(int n) {
			long[] res = new long[n];
			for (int i = 0; i < n; i++) {
				res[i] = nextLong() - 1;
			}
			return res;
		}

		public long[] nextLongArray1Index(int n) {
			long[] res = new long[n + 1];
			for (int i = 0; i < n; i++) {
				res[i + 1] = nextLong();
			}
			return res;
		}

		public double[] nextDoubleArray(int n) {
			double[] res = new double[n];
			for (int i = 0; i < n; i++) {
				res[i] = nextDouble();
			}
			return res;
		}

		public InputReader(InputStream inputStream) {
			in = new BufferedReader(new InputStreamReader(inputStream));
			tok = new StringTokenizer("");
		}
	}

}
