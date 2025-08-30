import java.util.*;

public class Main {
    static final int MOD = 1000000007;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int K = sc.nextInt();
        long[] A = new long[N];
        for (int i = 0; i < N; i++) {
            A[i] = sc.nextLong();
        }
        Arrays.sort(A);

        long[] fact = new long[N + 1];
        long[] invFact = new long[N + 1];
        fact[0] = 1;
        invFact[0] = 1;
        for (int i = 1; i <= N; i++) {
            fact[i] = (fact[i - 1] * i) % MOD;
            invFact[i] = pow(fact[i], MOD - 2);
        }

        long ans = 0;
        for (int i = 0; i < N; i++) {
            // A[i] as max
            if (i >= K - 1) {
                long combinations = nCr(i, K - 1, fact, invFact);
                ans = (ans + A[i] * combinations) % MOD;
            }
            // A[i] as min
            if (N - 1 - i >= K - 1) {
                long combinations = nCr(N - 1 - i, K - 1, fact, invFact);
                ans = (ans - A[i] * combinations + MOD) % MOD;
            }
        }

        System.out.println(ans);
    }

    static long nCr(int n, int r, long[] fact, long[] invFact) {
        if (r < 0 || r > n) {
            return 0;
        }
        return (((fact[n] * invFact[r]) % MOD) * invFact[n - r]) % MOD;
    }

    static long pow(long base, long exp) {
        long res = 1;
        base %= MOD;
        while (exp > 0) {
            if (exp % 2 == 1) res = (res * base) % MOD;
            base = (base * base) % MOD;
            exp /= 2;
        }
        return res;
    }
}