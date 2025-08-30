import java.util.Arrays;
import java.util.Scanner;

public class Main {

	static Scanner sc = new Scanner(System.in);
	static long MOD = 1_000_000_007;

	public static void main(String[] args) {
		int N = sc.nextInt();
		long[] sum = new long[N + 1];
		int[] A = new int[N];
		for (int i = 0; i < N; i++) {
			A[i] = sc.nextInt();
			sum[i + 1] = sum[i] + A[i];
		}
		long[][] dp = new long[N + 1][N + 1];
		for (long[] a : dp) {
			Arrays.fill(a, Long.MAX_VALUE / 8);
		}
		for (int i = 0; i < N; i++) {
			dp[i][i + 1] = A[i];
		}
		for (int i = 2; i <= N; i++) {
			for (int j = 0; j + i <= N; j++) {
				for (int k = 1; k < i; k++) {
					dp[j][j + i] = Math.min(dp[j][j + i], dp[j][j + k] + dp[j + k][j + i] + sum[j + i] - sum[j]);
				}
			}
		}
		System.out.println(dp[0][N] - sum[N]);
	}
}