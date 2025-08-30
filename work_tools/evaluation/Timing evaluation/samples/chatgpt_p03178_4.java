import java.util.*;
import java.io.*;

public class Main {
    static final int MOD = 1_000_000_007;
    static String K;
    static int D, N;
    static int[][][] dp;

    // pos: current digit position
    // sum: current sum of digits mod D
    // tight: 0=not tight, 1=tight
    static int solve(int pos, int sum, int tight) {
        if (pos == N) {
            return sum == 0 ? 1 : 0;
        }
        if (dp[pos][sum][tight] != -1) return dp[pos][sum][tight];
        int res = 0;
        int maxDigit = tight == 1 ? K.charAt(pos) - '0' : 9;
        for (int d = 0; d <= maxDigit; d++) {
            int nextTight = (tight == 1 && d == maxDigit) ? 1 : 0;
            res = (res + solve(pos + 1, (sum + d) % D, nextTight)) % MOD;
        }
        return dp[pos][sum][tight] = res;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        K = in.readLine().trim();
        D = Integer.parseInt(in.readLine());
        N = K.length();
        dp = new int[N+1][D][2];
        for (int[][] a : dp)
            for (int[] b : a)
                Arrays.fill(b, -1);

        int ans = solve(0, 0, 1);
        ans = (ans - 1 + MOD) % MOD; // exclude 0
        System.out.println(ans);
    }
}