import java.util.*;
import java.io.*;
import java.awt.geom.*;
import java.math.*;

public class Main {

	static final Scanner in = new Scanner(System.in);
	static final PrintWriter out = new PrintWriter(System.out,false);
	static boolean debug = false;

	static int[] dy = { 1, 1, 1,};
	static int[] dx = { -1, 0, 1};

	static boolean solve() {
		int w = in.nextInt();
		int h = in.nextInt();
		if (w + h == 0) return false;

		int[][] t = new int[h][w];
		for (int i=0; i<h; i++) {
			for (int j=0; j<w; j++) {
				t[i][j] = in.nextInt();
			}
		}

		int[][] dp = new int[h][w];
		for (int i=0; i<w; i++) {
			if (t[0][i] == 0) dp[0][i] = 1;
		}

		for (int i=1; i<h; i++) {
			for (int j=0; j<w; j++) {
				if (t[i][j] == 1) continue;
				int sum = 0;
				if (j - 1 >= 0 && t[i][j] != 2 && t[i-1][j-1] == 0) {
					sum += dp[i-1][j-1];
				}
				if (j + 1 < w && t[i][j] != 2 && t[i-1][j+1] == 0) {
					sum += dp[i-1][j+1];
				}
				if (t[i-1][j] == 0) {
					sum += dp[i-1][j];
				}
				if (i - 2 >= 0 && t[i-2][j] == 2) {
					sum += dp[i-2][j];
				}
				dp[i][j] = sum;
			}
		}

		int ans = 0;
		for (int i=0; i<w; i++) {
			ans += dp[h-1][i];
			if (h - 2 >= 0 && t[h-2][i] == 2) {
				ans += dp[h-2][i];
			}
		}

		out.println(ans);

		return true;
	}

	public static void main(String[] args) {
		debug = args.length > 0;
		long start = System.nanoTime();

		while(solve());
		out.flush();

		long end = System.nanoTime();
		dump((end - start) / 1000000 + " ms");
		in.close();
		out.close();
	}

	static void dump(Object... o) { if (debug) System.err.println(Arrays.deepToString(o)); }
}