import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String S = sc.next();
        int n = S.length();
        long MOD = 1_000_000_007;

        // dp[i][j] represents the number of ways to form a prefix of length i
        // such that its remainder when divided by 13 is j.
        long[][] dp = new long[n + 1][13];
        dp[0][0] = 1; // Base case: empty prefix has remainder 0

        for (int i = 0; i < n; i++) {
            char c = S.charAt(i);
            for (int j = 0; j < 13; j++) {
                if (dp[i][j] == 0) continue;

                if (c == '?') {
                    for (int k = 0; k <= 9; k++) {
                        int nextRemainder = (j * 10 + k) % 13;
                        dp[i + 1][nextRemainder] = (dp[i + 1][nextRemainder] + dp[i][j]) % MOD;
                    }
                } else {
                    int digit = c - '0';
                    int nextRemainder = (j * 10 + digit) % 13;
                    dp[i + 1][nextRemainder] = (dp[i + 1][nextRemainder] + dp[i][j]) % MOD;
                }
            }
        }

        System.out.println(dp[n][5]);
    }
}