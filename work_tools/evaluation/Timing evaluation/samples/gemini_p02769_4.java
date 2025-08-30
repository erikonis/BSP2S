import java.util.Scanner;

public class Main {
    static final int MOD = 1_000_000_007;

    public static long combinations(int n, int k) {
        if (k < 0 || k > n) {
            return 0;
        }
        if (k == 0 || k == n) {
            return 1;
        }
        if (k > n / 2) {
            k = n - k;
        }

        long res = 1;
        for (int i = 0; i < k; i++) {
            res = (res * (n - i)) % MOD;
            res = (res * modInverse(i + 1)) % MOD;
        }
        return res;
    }

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

    public static long modInverse(long n) {
        return power(n, MOD - 2);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        long k = sc.nextLong();
        sc.close();

        long ans = 0;
        if (k < n) {
            for (int i = 0; i <= k; i++) {
                long term = combinations(n, i);
                term = (term * combinations(k - i + n - 1, n - 1)) % MOD;
                ans = (ans + term) % MOD;
            }
        } else {
            ans = combinations(2 * n - 1, n);
        }

        System.out.println(ans);
    }
}