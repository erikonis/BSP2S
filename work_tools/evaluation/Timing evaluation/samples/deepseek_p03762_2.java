import java.util.*;

public class Main {
    static final int MOD = 1000000007;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        long[] x = new long[n];
        long[] y = new long[m];
        
        for (int i = 0; i < n; i++) x[i] = sc.nextLong();
        for (int i = 0; i < m; i++) y[i] = sc.nextLong();
        
        long sumX = 0;
        long sumY = 0;
        
        // Compute sum of x differences: sum_{i<j} (x_j - x_i)
        long[] prefixX = new long[n];
        prefixX[0] = x[0];
        for (int i = 1; i < n; i++) {
            prefixX[i] = (prefixX[i-1] + x[i]) % MOD;
        }
        for (int i = 1; i < n; i++) {
            long term = (x[i] * i - prefixX[i-1]) % MOD;
            sumX = (sumX + term) % MOD;
        }
        
        // Compute sum of y differences: sum_{k<l} (y_l - y_k)
        long[] prefixY = new long[m];
        prefixY[0] = y[0];
        for (int i = 1; i < m; i++) {
            prefixY[i] = (prefixY[i-1] + y[i]) % MOD;
        }
        for (int i = 1; i < m; i++) {
            long term = (y[i] * i - prefixY[i-1]) % MOD;
            sumY = (sumY + term) % MOD;
        }
        
        long result = (sumX * sumY) % MOD;
        System.out.println(result);
    }
}