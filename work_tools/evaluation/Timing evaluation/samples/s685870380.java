import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {

	public static void main(String args[]) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int n = Integer.parseInt(br.readLine());

		long a[] = new long[n + 1];

		String str[] = br.readLine().split(" ");
		for (int i = 0; i < n; i++) {
			a[i] = Long.parseLong(str[i]);
		}

		long dp[][] = new long[n + 1][n + 1];

		for (int len = 1; len <= n; len++) {
			for (int l = 0; l + len <= n; l++) {
				int r = l + len;
				if ((n - len) % 2 == 0) {
					dp[l][r] = Math.max(dp[l + 1][r] + a[l], dp[l][r - 1] + a[r - 1]);
				} else {
					dp[l][r] = Math.min(dp[l + 1][r] - a[l], dp[l][r - 1] - a[r - 1]);
				}
			}
		}

		System.out.println(dp[0][n]);
	}
}
