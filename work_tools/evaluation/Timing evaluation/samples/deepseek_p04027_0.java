import java.util.*;

public class Main {
    static final int MOD = 1000000007;
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int C = sc.nextInt();
        int[] A = new int[N];
        int[] B = new int[N];
        for (int i = 0; i < N; i++) A[i] = sc.nextInt();
        for (int i = 0; i < N; i++) B[i] = sc.nextInt();
        
        // Precompute powers: pow[x][k] = x^k mod MOD
        int[][] pow = new int[401][401];
        for (int x = 1; x <= 400; x++) {
            pow[x][0] = 1;
            for (int k = 1; k <= 400; k++) {
                pow[x][k] = (int)((long)pow[x][k-1] * x % MOD);
            }
        }
        
        // dp[i][c] = sum of products for first i children with c candies
        int[][] dp = new int[N+1][C+1];
        dp[0][0] = 1;
        
        for (int i = 1; i <= N; i++) {
            int a = A[i-1], b = B[i-1];
            // Precompute prefix sums for current child's contribution
            int[][] prefix = new int[b - a + 1][C + 1];
            for (int x = a; x <= b; x++) {
                int xi = x - a;
                prefix[xi][0] = 1;
                for (int k = 1; k <= C; k++) {
                    prefix[xi][k] = pow[x][k];
                }
                // Compute prefix sums
                if (xi > 0) {
                    for (int k = 0; k <= C; k++) {
                        prefix[xi][k] = (prefix[xi][k] + prefix[xi-1][k]) % MOD;
                    }
                }
            }
            
            for (int c = 0; c <= C; c++) {
                for (int k = 0; k <= c; k++) {
                    int sum = (b >= a) ? prefix[b - a][k] : 0;
                    dp[i][c] = (int)((dp[i][c] + (long)dp[i-1][c - k] * sum) % MOD);
                }
            }
        }
        
        System.out.println(dp[N][C]);
    }
}