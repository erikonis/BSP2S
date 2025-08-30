import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int S = sc.nextInt();
        
        final int MOD = 1000000007;
        long[] dp = new long[S + 1];
        dp[0] = 1;
        
        for (int i = 3; i <= S; i++) {
            for (int j = 3; j <= i; j++) {
                dp[i] = (dp[i] + dp[i - j]) % MOD;
            }
        }
        
        System.out.println(dp[S]);
    }
}