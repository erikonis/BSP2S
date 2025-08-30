import java.util.*;
import java.io.*;
import java.awt.geom.*;
import java.math.*;

public class Main {

	static final Scanner in = new Scanner(System.in);
	static final PrintWriter out = new PrintWriter(System.out,false);
	static boolean debug = false;

	static boolean[] f;

	static int rec(int n) {
		if (f[n]) return -1;
		f[n] = true;
		String s = String.valueOf(n);
		long max = 0;
		int ln = s.length();
		if (ln == 1) return 0;
		for (int i=1; i<ln; i++) {
			long t = Long.parseLong(s.substring(0, i))*Long.parseLong(s.substring(i, ln));
			if (max < t) {
				max = t;
			}
		}

		int res = rec((int)max);
		return res == -1 ? -1 : (res + 1);
	}

	static void solve() {
		int q = in.nextInt();

		while (q-- > 0) {
			int n = in.nextInt();
			f = new boolean[1000001];

			out.println(rec(n));
		}
	}

	public static void main(String[] args) {
		debug = args.length > 0;
		long start = System.nanoTime();

		solve();
		out.flush();

		long end = System.nanoTime();
		dump((end - start) / 1000000 + " ms");
		in.close();
		out.close();
	}

	static void dump(Object... o) { if (debug) System.err.println(Arrays.deepToString(o)); }
}