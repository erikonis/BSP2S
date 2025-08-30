import java.util.*;

public class Main {
    static final int MOD = (int)1e9 + 7;
    static long[] fact;
    static long[] invFact;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int k = sc.nextInt();

        int max = Math.min(n, k);
        preprocess(n);

        long res = 0;
        for (int m = 0; m <= max; m++) {
            long c1 = comb(n, m);
            long c2 = comb(n - 1, n - m - 1);
            res = (res + c1 * c2) % MOD;
        }
        System.out.println(res);
    }

    static void preprocess(int n) {
        fact = new long[n + 1];
        invFact = new long[n + 1];
        fact[0] = 1;
        for (int i = 1; i <= n; i++) {
            fact[i] = fact[i - 1] * i % MOD;
        }
        invFact[n] = modInverse(fact[n], MOD);
        for (int i = n - 1; i >= 0; i--) {
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