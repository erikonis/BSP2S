import java.util.Scanner;

public class Main {
    static final int MOD = 1000000007;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int S = sc.nextInt();
        if (S < 3) {
            System.out.println(0);
            return;
        }
        long[] dp = new long[S + 1];
        dp[0] = 1;
        for (int i = 3; i <= S; i++) {
            for (int j = 0; j <= i - 3; j++) {
                dp[i] = (dp[i] + dp[j]) % MOD;
            }
        }
        System.out.println(dp[S]);
    }
}