import java.util.*;
import java.io.*;

public class Main {
    static final int MOD = 1_000_000_007;
    static long[] fact;
    static long[] invFact;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        int[] A = new int[N];
        for (int i = 0; i < N; i++) {
            A[i] = Integer.parseInt(st.nextToken());
        }

        precomputeFactorials(N);

        long[] inv = new long[N + 2];
        inv[1] = 1;
        for (int i = 2; i <= N + 1; i++) {
            inv[i] = MOD - (MOD / i) * inv[MOD % i] % MOD;
        }

        long[] sumInv = new long[N + 2];
        sumInv[0] = 0;
        for (int i = 1; i <= N + 1; i++) {
            sumInv[i] = (sumInv[i - 1] + inv[i]) % MOD;
        }

        long res = 0;
        for (int i = 0; i < N; i++) {
            long k = (sumInv[i + 1] + sumInv[N - i] - 1) % MOD;
            long contribution = (A[i] * k) % MOD;
            contribution = (contribution * fact[N]) % MOD;
            res = (res + contribution) % MOD;
        }
        System.out.println(res);
    }

    static void precomputeFactorials(int N) {
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

    static long modInverse(long a, int mod) {
        return pow(a, mod - 2, mod);
    }

    static long pow(long a, long b, int mod) {
        long res = 1;
        while (b > 0) {
            if ((b & 1) == 1) {
                res = res * a % mod;
            }
            a = a * a % mod;
            b >>= 1;
        }
        return res;
    }
}