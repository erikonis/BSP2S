import java.util.*;

public class Main {
    static final int MOD = 1_000_000_007;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int[] x = new int[N];
        for (int i = 0; i < N; i++) {
            x[i] = sc.nextInt();
        }

        long[] dp = new long[N + 1];
        dp[0] = 1;

        for (int i = 0; i < N; i++) {
            int currentX = x[i];
            int count = 0;
            for (int j = i - 1; j >= 0; j--) {
                if (x[j] + 1 < currentX) { // can pass through
                    break;
                }
                if (x[j] + 1 == currentX) { // adjacent
                    count++;
                }
            }
            // Number of ways to insert robot i into the sequence of finished robots
            // It can be placed after any of the 'count' robots it can pass,
            // or at the very beginning of the group formed by itself and these 'count'
            // robots, or at the very end.
            // So there are (count + 1) possible positions relative to the group of 'count' robots.
            // Total robots considered so far is (i - count) + (count + 1) = i + 1.
            dp[i + 1] = (dp[i] * (count + 1)) % MOD;
        }

        long ans = dp[N];
        for (int i = 1; i <= N; i++) {
            ans = (ans * i) % MOD;
        }

        System.out.println(ans);
    }
}