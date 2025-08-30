import java.util.*;

public class Main {
    static final int MOD = 1_000_000_007;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int H = sc.nextInt(), W = sc.nextInt();
        sc.nextLine();
        char[][] grid = new char[H][W];
        for (int i = 0; i < H; i++) {
            grid[i] = sc.nextLine().toCharArray();
        }

        int[][] dp = new int[H][W];

        // Start position
        dp[0][0] = (grid[0][0] == '.') ? 1 : 0;

        // Fill DP table
        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                if (grid[i][j] == '#') continue;
                if (i > 0) dp[i][j] = (dp[i][j] + dp[i-1][j]) % MOD;
                if (j > 0) dp[i][j] = (dp[i][j] + dp[i][j-1]) % MOD;
            }
        }

        System.out.println(dp[H-1][W-1]);
    }
}