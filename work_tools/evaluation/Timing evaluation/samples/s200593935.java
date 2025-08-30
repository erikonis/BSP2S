import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

	public static void main(String[] args) throws IOException {
		new Main().run();
	}

	public void run() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(reader.readLine());
		int n = Integer.parseInt(st.nextToken());
		int W = Integer.parseInt(st.nextToken());
		int[] v = new int[n];
		int[] w = new int[n];
		for (int i = 0; i < n; i++) {
			st = new StringTokenizer(reader.readLine());
			v[i] = Integer.parseInt(st.nextToken());
			w[i] = Integer.parseInt(st.nextToken());
		}
		reader.close();
		System.out.println(getMaxValue(n, W, v, w));
	}

	private int getMaxValue(int n, int W, int[] v, int[] w) {
		int[][] dp = new int[n + 1][W + 1];
		for (int i = 0; i < n; i++) {
			int vl = v[i];
			int wt = w[i];
			for (int j = 0; j <= W; j++) {
				if (wt > j) {
					dp[i + 1][j] = dp[i][j];
				} else {
					dp[i + 1][j] = Math.max(dp[i][j], Math.max(dp[i][j - wt] + vl, dp[i + 1][j - wt] + vl));
				}
			}
		}
		return dp[n][W];
	}
}
