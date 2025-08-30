import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;

public class Main {

	private static final String NO = "NO";
	private static final String YES = "YES";
	InputStream is;
	PrintWriter out;
	String INPUT = "";

	private static final long MOD = 1000000007;
	private static final long inf = 100000000000L;

	void solve() {
		int T = 1;
		for (int i = 0; i < T; i++)
			solve(i);
	}

	int n;
	long fac[], inv[];

	void prepare() {
		fac = new long[n + 1];
		inv = new long[n + 1];
		fac[0] = 1;
		for (int i = 1; i <= n; ++i)
			fac[i] = fac[i - 1] * i % MOD;
		inv[n] = power(fac[n], MOD - 2);
		for (int i = n - 1; i >= 0; --i)
			inv[i] = inv[i + 1] * (i + 1) % MOD;
	}

	long C(int x, int y) {
		if (x < y || y < 0 || x < 0)
			return 0;
		return fac[x] * inv[y] % MOD * inv[x - y] % MOD;
	}

	void solve(int T) {
		n = ni();
		int k = ni();
		prepare();
		int a[] = na(n);
		Arrays.sort(a);
		long ans = 0;
		for (int i = 0; i < n; ++i) {
			ans = (ans - a[i] * C(n - 1 - i, k - 1)) % MOD;
			ans = (ans + a[i] * C(i, k - 1)) % MOD;
		}
		out.println(ans);
	}

	// a^b
	long power(long a, long b) {
		long x = 1, y = a;
		while (b > 0) {
			if (b % 2 != 0) {
				x = (x * y) % MOD;
			}
			y = (y * y) % MOD;
			b /= 2;
		}
		return x % MOD;
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

	private long[][] nl(int n, int m) {
		long[][] a = new long[n][];
		for (int i = 0; i < n; i++)
			a[i] = nl(m);
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

	public class Pair<K, V> {

		/**
		 * Key of this <code>Pair</code>.
		 */
		private K key;

		/**
		 * Gets the key for this pair.
		 * 
		 * @return key for this pair
		 */
		public K getKey() {
			return key;
		}

		/**
		 * Value of this this <code>Pair</code>.
		 */
		private V value;

		/**
		 * Gets the value for this pair.
		 * 
		 * @return value for this pair
		 */
		public V getValue() {
			return value;
		}

		/**
		 * Creates a new pair
		 * 
		 * @param key   The key for this pair
		 * @param value The value to use for this pair
		 */
		public Pair(K key, V value) {
			this.key = key;
			this.value = value;
		}

		/**
		 * <p>
		 * <code>String</code> representation of this <code>Pair</code>.
		 * </p>
		 *
		 * <p>
		 * The default name/value delimiter '=' is always used.
		 * </p>
		 *
		 * @return <code>String</code> representation of this <code>Pair</code>
		 */
		@Override
		public String toString() {
			return key + "=" + value;
		}

		/**
		 * <p>
		 * Generate a hash code for this <code>Pair</code>.
		 * </p>
		 *
		 * <p>
		 * The hash code is calculated using both the name and the value of the
		 * <code>Pair</code>.
		 * </p>
		 *
		 * @return hash code for this <code>Pair</code>
		 */
		@Override
		public int hashCode() {
			// name's hashCode is multiplied by an arbitrary prime number (13)
			// in order to make sure there is a difference in the hashCode between
			// these two parameters:
			// name: a value: aa
			// name: aa value: a
			return key.hashCode() * 13 + (value == null ? 0 : value.hashCode());
		}

		/**
		 * <p>
		 * Test this <code>Pair</code> for equality with another <code>Object</code>.
		 * </p>
		 *
		 * <p>
		 * If the <code>Object</code> to be tested is not a <code>Pair</code> or is
		 * <code>null</code>, then this method returns <code>false</code>.
		 * </p>
		 *
		 * <p>
		 * Two <code>Pair</code>s are considered equal if and only if both the names and
		 * values are equal.
		 * </p>
		 *
		 * @param o the <code>Object</code> to test for equality with this
		 *          <code>Pair</code>
		 * @return <code>true</code> if the given <code>Object</code> is equal to this
		 *         <code>Pair</code> else <code>false</code>
		 */
		@Override
		public boolean equals(Object o) {
			if (this == o)
				return true;
			if (o instanceof Pair) {
				Pair pair = (Pair) o;
				if (key != null ? !key.equals(pair.key) : pair.key != null)
					return false;
				if (value != null ? !value.equals(pair.value) : pair.value != null)
					return false;
				return true;
			}
			return false;
		}
	}

}