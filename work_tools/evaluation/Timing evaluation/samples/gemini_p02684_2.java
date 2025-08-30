import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        long K = sc.nextLong();
        int[] A = new int[N + 1];
        for (int i = 1; i <= N; i++) {
            A[i] = sc.nextInt();
        }

        int[][] dp = new int[60][N + 1]; // dp[i][j] stores the town reached after 2^i steps from town j

        // Initialize for 2^0 = 1 step
        for (int i = 1; i <= N; i++) {
            dp[0][i] = A[i];
        }

        // Fill dp table for 2^i steps
        for (int i = 1; i < 60; i++) {
            for (int j = 1; j <= N; j++) {
                dp[i][j] = dp[i - 1][dp[i - 1][j]];
            }
        }

        int currentTown = 1;
        // Simulate K steps using binary representation of K
        for (int i = 0; i < 60; i++) {
            if (((K >> i) & 1) == 1) { // If the i-th bit of K is set
                currentTown = dp[i][currentTown];
            }
        }

        System.out.println(currentTown);
        sc.close();
    }
}