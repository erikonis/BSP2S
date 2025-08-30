import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int H = sc.nextInt();
        int W = sc.nextInt();
        int D = sc.nextInt();

        // Stores the coordinates (row, col) for each number A[i][j]
        int[][] pos = new int[H * W + 1][2];
        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                int val = sc.nextInt();
                pos[val][0] = i; // row
                pos[val][1] = j; // col
            }
        }

        // dp[i] stores the cumulative magic points needed to move from i to i+D, i+2D, ..., up to the maximum possible value.
        // The maximum value is H*W.
        long[] dp = new long[H * W + 1];

        // Iterate backwards from the maximum possible value down to 1.
        // This is because dp[i] depends on dp[i+D].
        for (int i = H * W; i >= 1; i--) {
            if (i + D > H * W) {
                // If i+D is out of bounds, no further moves are possible from i in this sequence.
                dp[i] = 0;
            } else {
                // Calculate the magic points to move from i to i+D.
                // This is |row_i - row_{i+D}| + |col_i - col_{i+D}|
                long cost = Math.abs(pos[i][0] - pos[i + D][0]) + Math.abs(pos[i][1] - pos[i + D][1]);
                // Add the cost to move from i+D onwards.
                dp[i] = cost + dp[i + D];
            }
        }

        int Q = sc.nextInt();

        for (int q = 0; q < Q; q++) {
            int L = sc.nextInt();
            int R = sc.nextInt();

            // The problem asks for the sum of magic points consumed from L to R.
            // This is equivalent to dp[L] - dp[R].
            // Because dp[L] includes all costs from L to L+D, L+2D, ..., up to the end.
            // And dp[R] includes all costs from R to R+D, R+2D, ..., up to the end.
            // Since R is a multiple of D away from L, the path from R onwards is a subpath of the path from L onwards.
            // So, dp[L] - dp[R] gives the sum of costs from L to R.
            long ans = dp[L] - dp[R];
            System.out.println(ans);
        }

        sc.close();
    }
}