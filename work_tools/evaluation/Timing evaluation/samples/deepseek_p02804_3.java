import java.util.*;
import java.io.*;

public class Main {
    static final int MOD = 1000000007;
    static long[] fact, invFact;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] line = br.readLine().split(" ");
        int N = Integer.parseInt(line[0]);
        int K = Integer.parseInt(line[1]);
        line = br.readLine().split(" ");
        int[] A = new int[N];
        for (int i = 0; i < N; i++) {
            A[i] = Integer.parseInt(line[i]);
        }
        Arrays.sort(A);

        precompute(N);

        long result = 0;
        for (int i = 0; i <= N - K; i++) {
            long c = comb(N - 1 - i, K - 1);
            result = (result + c * A[N - 1 - i] % MOD) % MOD;
        }
        for (int i = 0; i <= N - K; i++) {
            long c = comb(N - 1 - i, K - 1);
            result = (result - c * A[i] % MOD + MOD) % MOD;
        }
        System.out.println(result);
    }

    static void precompute(int N) {
        fact = new long[N + 1];
        invFact = new long[N + 1];
        fact[0] = 1;
        for (int i = 1; i <= N; i++) {
            fact[i] = fact[i - 1] * i % MOD;
        }
        invFact[N] = modInverse(fact[N], MOD);
        for (int i = N - 1; i >= 0; i--) {
            invFact[i] = invFact[i + 1] * (i + 1) % MOD;
        }
    }

    static long comb(int n, int k) {
        if (k < 0 || k > n) return 0;
        return fact[n] * invFact[k] % MOD * invFact[n - k] % MOD;
    }

    static long modInverse(long a, int mod) {
        return power(a, mod - 2, mod);
    }

    static long power(long a, long b, int mod) {
        long res = 1;
        a = a % mod;
        while (b > 0) {
            if ((b & 1) == 1) {
                res = (res * a) % mod;
            }
            a = (a * a) % mod;
            b >>= 1;
        }
        return res;
    }
}