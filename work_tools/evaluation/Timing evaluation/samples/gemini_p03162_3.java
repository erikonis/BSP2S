import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();

        int[][] dp = new int[N][3]; // dp[i][0] for activity A, dp[i][1] for B, dp[i][2] for C

        // Initialize for the first day
        dp[0][0] = sc.nextInt(); // Happiness for activity A on day 0
        dp[0][1] = sc.nextInt(); // Happiness for activity B on day 0
        dp[0][2] = sc.nextInt(); // Happiness for activity C on day 0

        // Fill DP table for subsequent days
        for (int i = 1; i < N; i++) {
            int a = sc.nextInt();
            int b = sc.nextInt();
            int c = sc.nextInt();

            // If Taro does activity A on day i, he must have done B or C on day i-1
            dp[i][0] = a + Math.max(dp[i - 1][1], dp[i - 1][2]);
            // If Taro does activity B on day i, he must have done A or C on day i-1
            dp[i][1] = b + Math.max(dp[i - 1][0], dp[i - 1][2]);
            // If Taro does activity C on day i, he must have done A or B on day i-1
            dp[i][2] = c + Math.max(dp[i - 1][0], dp[i - 1][1]);
        }

        // The maximum happiness on the last day is the answer
        System.out.println(Math.max(dp[N - 1][0], Math.max(dp[N - 1][1], dp[N - 1][2])));

        sc.close();
    }
}