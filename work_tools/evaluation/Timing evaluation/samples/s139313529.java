import java.util.*;
import java.io.*;
import java.awt.geom.*;
import java.math.*;

public class Main {

	static final Scanner in = new Scanner(System.in);
	static final PrintWriter out = new PrintWriter(System.out,false);
	static boolean debug = false;

	static void solve() {
		int n = in.nextInt();
		int m = in.nextInt();

		double[][] p = new double[n][3];
		for (int i=0; i<n; i++) {
			for (int j=0; j<3; j++) {
				p[i][j] = in.nextDouble();
			}
		}

		double max = 0;

		for (int k=0; k<1<<n; k++) {
			if (Integer.bitCount(k) != m) continue;
			double sum = 0;
			for (int i=0; i<n; i++) {
				if ((k>>i&1) != 1) continue;
				for (int j=i+1; j<n; j++) {
					if ((k>>j&1) != 1) continue;
					for (int c=0; c<3; c++)
						sum += (p[i][c] - p[j][c])*(p[i][c] - p[j][c]);
				}
			}
			max = Math.max(max, sum);
		}

		out.printf("%.6f\n",max);
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