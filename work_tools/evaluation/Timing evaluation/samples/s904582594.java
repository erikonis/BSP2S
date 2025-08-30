import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

public class Main {

	private void solve() throws IOException {
		int n = ni();

		int k = ni();
		long[][] a = na(n);
		Comparator<long[]> comp = new Comparator<long[]>() {
			public int compare(long[] arg0, long[] arg1) {
				return Long.signum(-(arg0[3] - arg1[3]));
			}
		};
		long ans = Long.MIN_VALUE;
		int mul[] = new int[] { 1, -1 };
		for (int i = 0; i < 2; i++)
			for (int j = 0; j < 2; j++)
				for (int l = 0; l < 2; l++) {
					apply(a, mul[i], mul[j], mul[l]);
					Arrays.sort(a, comp);
//					Helper.tr(a);
					ans = Math.max(ans, sumK(a, k));
//					Helper.tr(ans);
				}
		out.println(ans);
		out.close();

	}

	private void apply(long[][] a, int f0, int f1, int f2) {
		for (int i = 0; i < a.length; i++)
			a[i][3] = a[i][0] * f0 + a[i][1] * f1 + a[i][2] * f2;

	}

	private long sumK(long[][] a, int k) {
		long ans = 0;
		for (int i = 0; i < k; i++)
			ans += a[i][3];
		return ans;
	}

	static BufferedReader in;
	static PrintWriter out;
	static StringTokenizer tok;

	private long[][] na(int n) throws IOException {
		long[][] a = new long[n][4];
		for (int i = 0; i < n; i++) {
			a[i][0] = nl();
			a[i][1] = nl();
			a[i][2] = nl();
		}
		return a;
	}

	String ns() throws IOException {
		while (!tok.hasMoreTokens()) {
			tok = new StringTokenizer(in.readLine(), " ");
		}
		return tok.nextToken();
	}

	int ni() throws IOException {
		return Integer.parseInt(ns());
	}

	long nl() throws IOException {
		return Long.parseLong(ns());
	}

	double nd() throws IOException {
		return Double.parseDouble(ns());
	}

	String[] nsa(int n) throws IOException {
		String[] res = new String[n];
		for (int i = 0; i < n; i++) {
			res[i] = ns();
		}
		return res;
	}

	int[] nia(int n) throws IOException {
		int[] res = new int[n];
		for (int i = 0; i < n; i++) {
			res[i] = ni();
		}
		return res;
	}

	long[] nla(int n) throws IOException {
		long[] res = new long[n];
		for (int i = 0; i < n; i++) {
			res[i] = nl();
		}
		return res;
	}

	public static void main(String[] args) throws IOException {
		in = new BufferedReader(new InputStreamReader(System.in));
		out = new PrintWriter(System.out);
		tok = new StringTokenizer("");
		Main main = new Main();
		main.solve();
		out.close();
	}
}