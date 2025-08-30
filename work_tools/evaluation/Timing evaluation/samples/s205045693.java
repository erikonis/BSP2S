import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;

public class Main {

	private static final int MAXN = 10;
	private static final String NO = "No";
	private static final String YES = "Yes";
	InputStream is;
	PrintWriter out;
	String INPUT = "";

	private static final long MOD = 1000000007L;

	int N;

	void solve() {

		char[] a = ns().toCharArray();
		char[] b = ns().toCharArray();
		char[] c = ns().toCharArray();
		N = a.length + b.length + c.length;

		int ans = N;
		ans = Math.min(ans, calc(a, b, c));
		ans = Math.min(ans, calc(a, c, b));
		ans = Math.min(ans, calc(b, a, c));
		ans = Math.min(ans, calc(b, c, a));
		ans = Math.min(ans, calc(c, a, b));
		ans = Math.min(ans, calc(c, b, a));

		out.println(ans);
	}

	private int calc(char[] a, char[] b, char[] c) {
		int ans = N;
		boolean[] unmatchAb = new boolean[N];
		boolean[] unmatchAc = new boolean[N];
		boolean[] unmatchBc = new boolean[N];
		cal(unmatchAb, a, b);
		cal(unmatchAc, a, c);
		cal(unmatchBc, b, c);
//		tr(unmatchAb);

		for (int i = 0; i <= a.length; i++)
			for (int j = 0; j <= Math.max(b.length, a.length - i); j++)
				if (!unmatchAb[i] && !unmatchBc[j] && !unmatchAc[i + j]) {
					ans = Math.min(ans, Math.max(Math.max(a.length, i + b.length), i + j + c.length));
				}

		return ans;
	}

	private void cal(boolean[] unmatchAb, char[] a, char[] b) {
		for (int i = 0; i < a.length; i++)
			for (int j = 0; j < b.length && i + j < a.length; j++)
				if (!match(a[i + j], b[j])) {
					unmatchAb[i] = true;
					break;
				}
	}

	private boolean match(char c, char d) {
		return c == '?' || d == '?' || c == d;
	}

	private long gcd(long a, long b) {
		while (a != 0) {
			long tmp = b % a;
			b = a;
			a = tmp;
		}
		return b;
	}

	void run() throws Exception {
		is = INPUT.isEmpty() ? System.in : new ByteArrayInputStream(INPUT.getBytes());
		out = new PrintWriter(System.out);

		long s = System.currentTimeMillis();
		solve();
		out.flush();
		if (!INPUT.isEmpty())
			tr(System.currentTimeMillis() - s + "ms");
	}

	public static void main(String[] args) throws Exception {
		new Main().run();
	}

	private byte[] inbuf = new byte[1024];
	public int lenbuf = 0, ptrbuf = 0;
	private boolean vis[];

	private int readByte() {
		if (lenbuf == -1)
			throw new InputMismatchException();
		if (ptrbuf >= lenbuf) {
			ptrbuf = 0;
			try {
				lenbuf = is.read(inbuf);
			} catch (IOException e) {
				throw new InputMismatchException();
			}
			if (lenbuf <= 0)
				return -1;
		}
		return inbuf[ptrbuf++];
	}

	private boolean isSpaceChar(int c) {
		return !(c >= 33 && c <= 126);
	}

	private int skip() {
		int b;
		while ((b = readByte()) != -1 && isSpaceChar(b))
			;
		return b;
	}

	private double nd() {
		return Double.parseDouble(ns());
	}

	private char nc() {
		return (char) skip();
	}

	private String ns() {
		int b = skip();
		StringBuilder sb = new StringBuilder();
		while (!(isSpaceChar(b))) { // when nextLine, (isSpaceChar(b) && b != '
									// ')
			sb.appendCodePoint(b);
			b = readByte();
		}
		return sb.toString();
	}

	private char[] ns(int n) {
		char[] buf = new char[n];
		int b = skip(), p = 0;
		while (p < n) {
			if (!(isSpaceChar(b)))
				buf[p++] = (char) b;
			b = readByte();
		}
		return n == p ? buf : Arrays.copyOf(buf, p);
	}

	private char[][] nm(int n, int m) {
		char[][] map = new char[n][];
		for (int i = 0; i < n; i++)
			map[i] = ns(m);
		return map;
	}

	private int[] na(int n) {
		int[] a = new int[n];
		for (int i = 0; i < n; i++)
			a[i] = ni();
		return a;
	}

	private List<Integer> na2(int n) {
		List<Integer> a = new ArrayList<Integer>();
		for (int i = 0; i < n; i++)
			a.add(ni());
		return a;
	}

	private int[][] na(int n, int m) {
		int[][] a = new int[n][];
		for (int i = 0; i < n; i++)
			a[i] = na(m);
		return a;
	}

	private int ni() {
		int num = 0, b;
		boolean minus = false;
		while ((b = readByte()) != -1 && !((b >= '0' && b <= '9') || b == '-'))
			;
		if (b == '-') {
			minus = true;
			b = readByte();
		}

		while (true) {
			if (b >= '0' && b <= '9') {
				num = num * 10 + (b - '0');
			} else {
				return minus ? -num : num;
			}
			b = readByte();
		}
	}

	private long[] nl(int n) {
		long[] a = new long[n];
		for (int i = 0; i < n; i++)
			a[i] = nl();
		return a;
	}

	private long nl() {
		long num = 0;
		int b;
		boolean minus = false;
		while ((b = readByte()) != -1 && !((b >= '0' && b <= '9') || b == '-'))
			;
		if (b == '-') {
			minus = true;
			b = readByte();
		}

		while (true) {
			if (b >= '0' && b <= '9') {
				num = num * 10 + (b - '0');
			} else {
				return minus ? -num : num;
			}
			b = readByte();
		}
	}

	private static void tr(Object... o) {
		System.out.println(Arrays.deepToString(o));
	}
}