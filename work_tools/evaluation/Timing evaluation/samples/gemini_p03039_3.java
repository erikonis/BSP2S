import java.util.Scanner;

public class Main {
    static final int MOD = 1_000_000_007;

    public static long power(long base, long exp) {
        long res = 1;
        base %= MOD;
        while (exp > 0) {
            if (exp % 2 == 1) res = (res * base) % MOD;
            base = (base * base) % MOD;
            exp /= 2;
        }
        return res;
    }

    public static long inv(long n) {
        return power(n, MOD - 2);
    }

    public static long nCr(int n, int r, long[] fact, long[] invFact) {
        if (r < 0 || r > n) return 0;
        return (((fact[n] * invFact[r]) % MOD) * invFact[n - r]) % MOD;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int M = sc.nextInt();
        int K = sc.nextInt();
        sc.close();

        int totalSquares = N * M;

        if (K == 0) {
            System.out.println(0);
            return;
        }

        long[] fact = new long[totalSquares + 1];
        long[] invFact = new long[totalSquares + 1];
        fact[0] = 1;
        invFact[0] = 1;
        for (int i = 1; i <= totalSquares; i++) {
            fact[i] = (fact[i - 1] * i) % MOD;
            invFact[i] = inv(fact[i]);
        }

        long combinations = nCr(totalSquares - 2, K - 2, fact, invFact);

        long sumX = 0;
        for (int i = 0; i < N; i++) {
            sumX = (sumX + (long) i * (N - 1 - i)) % MOD;
        }
        sumX = (sumX * M) % MOD;
        sumX = (sumX * M) % MOD;

        long sumY = 0;
        for (int j = 0; j < M; j++) {
            sumY = (sumY + (long) j * (M - 1 - j)) % MOD;
        }
        sumY = (sumY * N) % MOD;
        sumY = (sumY * N) % MOD;

        long totalSum = (sumX + sumY) % MOD;
        totalSum = (totalSum * combinations) % MOD;

        System.out.println(totalSum);
    }
}