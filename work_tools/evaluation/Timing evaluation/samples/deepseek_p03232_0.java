import java.util.*;
import java.io.*;

public class Main {
    static final long MOD = 1000000007L;
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        long[] A = new long[N];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            A[i] = Long.parseLong(st.nextToken());
        }
        
        long[] fact = new long[N+1];
        fact[0] = 1;
        for (int i = 1; i <= N; i++) {
            fact[i] = fact[i-1] * i % MOD;
        }
        
        long[] inv = new long[N+1];
        inv[N] = pow(fact[N], MOD-2);
        for (int i = N-1; i >= 0; i--) {
            inv[i] = inv[i+1] * (i+1) % MOD;
        }
        
        long[] prefixSum = new long[N+2];
        for (int i = 1; i <= N; i++) {
            prefixSum[i] = (prefixSum[i-1] + A[i-1]) % MOD;
        }
        
        long total = 0;
        for (int k = 1; k <= N; k++) {
            long contribution = (prefixSum[N] + (prefixSum[k-1] + prefixSum[N - k]) % MOD) % MOD;
            long ways = fact[N] * inv[k] % MOD;
            ways = ways * inv[N - k + 1] % MOD;
            ways = ways * k % MOD;
            total = (total + contribution * ways) % MOD;
        }
        
        System.out.println(total);
    }
    
    static long pow(long a, long b) {
        long res = 1;
        while (b > 0) {
            if ((b & 1) == 1) {
                res = res * a % MOD;
            }
            a = a * a % MOD;
            b >>= 1;
        }
        return res;
    }
}