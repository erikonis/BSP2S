import java.util.*;
import java.io.*;
import java.math.BigInteger;

public class Main {

	static char[] arr;
	static int k;
	static int[] pow26;

	public static int dp(int i, int j) {
		if (j == arr.length) {
			return pow26[arr.length + k - i];
		}
		if (i == k)
			return 1;

		return 25 * dp(i + 1, j) + dp(i, j + 1);
	}

	static long[] fac, facinv;

	static final int mod = (int) 1e9 + 7;

	public static long nck(int n, int k) {
		if (k > n)
			return 0;
		return fac[n] * facinv[k] % mod * facinv[n - k] % mod;
	}

	public static long modpow(long a, long e) {
		if (e == 0)
			return 1;
		long p = modpow(a, e / 2);
		if (e % 2 == 0)
			return (p * p) % mod;
		else
			return a * p % mod * p % mod;
	}

	public static long modInv(long a) {
		return modpow(a, mod - 2);
	}

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner(System.in);
		PrintWriter pw = new PrintWriter(System.out);
		k = sc.nextInt();
		arr = sc.next().toCharArray();
		fac = new long[arr.length + k + 1];
		fac[0] = 1;
		for (int i = 1; i < fac.length; i++) {
			fac[i] = i * fac[i - 1] % mod;
		}
		facinv = new long[arr.length + k + 1];
		facinv[0] = 1;
		for (int i = 1; i < facinv.length; i++) {
			facinv[i] = modInv(i) * facinv[i - 1] % mod;
		}
		long ans = 0;
		int s = arr.length;
		for (int i = 0; i <= k; i++) {
			ans += modpow(25, i) * nck(i + s - 1, i) % mod * modpow(26, k - i) % mod;
			ans %= mod;
		}
		pw.println(ans);
		pw.close();

	}

	static class Scanner {
		BufferedReader br;
		StringTokenizer st;

		public Scanner(InputStream s) {
			br = new BufferedReader(new InputStreamReader(s));
		}

		public String next() throws IOException {
			while (st == null || !st.hasMoreTokens())
				st = new StringTokenizer(br.readLine());
			return st.nextToken();
		}

		public int nextInt() throws IOException {
			return Integer.parseInt(next());
		}

		public long nextLong() throws IOException {
			return Long.parseLong(next());
		}

		public double nextDouble() throws IOException {
			return Double.parseDouble(next());
		}

		public int[] nextIntArr(int n) throws IOException {
			int[] arr = new int[n];
			for (int i = 0; i < n; i++) {
				arr[i] = Integer.parseInt(next());
			}
			return arr;
		}

	}

}
