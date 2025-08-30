import java.util.*;

public class Main {
    static final int MOD = (int)1e9 + 7;
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        long K = sc.nextLong();
        int[][] adj = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                adj[i][j] = sc.nextInt();
            }
        }
        
        // Initialize result as identity matrix
        long[][] res = new long[N][N];
        for (int i = 0; i < N; i++) {
            res[i][i] = 1;
        }
        
        // Binary exponentiation
        long[][] a = new long[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                a[i][j] = adj[i][j];
            }
        }
        
        while (K > 0) {
            if (K % 2 == 1) {
                res = multiply(res, a);
            }
            a = multiply(a, a);
            K /= 2;
        }
        
        long sum = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                sum = (sum + res[i][j]) % MOD;
            }
        }
        System.out.println(sum);
    }
    
    static long[][] multiply(long[][] a, long[][] b) {
        int n = a.length;
        long[][] res = new long[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    res[i][j] = (res[i][j] + a[i][k] * b[k][j]) % MOD;
                }
            }
        }
        return res;
    }
}