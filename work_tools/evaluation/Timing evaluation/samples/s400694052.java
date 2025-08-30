import java.util.*;
import java.lang.*;
import java.math.*;
import java.io.*;

import static java.lang.Math.*;
import static java.util.Arrays.*;

public class Main {

	Scanner sc = new Scanner(System.in);

	int INF = 1 << 28;
	double EPS = 1e-9;

	int t;
	int m0, year;
	int n;
	int[] a, cost;
	double[] rate;

	void run() {
		t = sc.nextInt();
		for (int j = 0; j < t; j++) {
			m0 = sc.nextInt();
			year = sc.nextInt();
			n = sc.nextInt();
			a = new int[n];
			rate = new double[n];
			cost = new int[n];
			for (int i = 0; i < n; i++) {
				a[i] = sc.nextInt();
				rate[i] = sc.nextDouble();
				cost[i] = sc.nextInt();
			}
			solve();
		}
	}

	void solve() {
		int max = 0;
		for (int j = 0; j < n; j++) {
			if (a[j] == 0) {
				int m = m0;
				int r = 0;
				for (int i = 0; i < year; i++) {
					r += (int) (m * rate[j]);
					m -= cost[j];
				}
				max = max(max, m + r);
			} else {
				int m = m0;
				for (int i = 0; i < year; i++) {
					m += (int) (m * rate[j]) - cost[j];
				}
				max = max(max, m);
			}
		}
		println(max + "");
	}

	void debug(Object... os) {
		System.err.println(Arrays.deepToString(os));
	}

	void print(String s) {
		System.out.print(s);
	}

	void println(String s) {
		System.out.println(s);
	}

	public static void main(String[] args) {
		// System.setOut(new PrintStream(new BufferedOutputStream(System.out)));
		new Main().run();
	}
}