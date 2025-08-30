import java.util.Scanner;

public class Knapsack {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        int W = sc.nextInt();

        int[] v = new int[N];
        int[] w = new int[N];

        for (int i = 0; i < N; i++) {
            v[i] = sc.nextInt();
            w[i] = sc.nextInt();
        }

        // dp[i][j] will store the maximum value that can be obtained with first i items
        // and a knapsack capacity of j.
        long[][] dp = new long[N + 1][W + 1];

        // Fill the dp array
        for (int i = 1; i <= N; i++) {
            for (int j = 0; j <= W; j++) {
                // Option 1: Don't include the current item (item i-1)
                dp[i][j] = dp[i - 1][j];

                // Option 2: Include the current item (item i-1) if its weight allows
                if (j >= w[i - 1]) {
                    dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - w[i - 1]] + v[i - 1]);
                }
            }
        }

        System.out.println(dp[N][W]);

        sc.close();
    }
}