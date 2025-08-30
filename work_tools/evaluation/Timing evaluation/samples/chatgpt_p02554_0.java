import java.util.Scanner;

public class Main {
    static final int MOD = 1_000_000_007;

    static long modPow(long base, long exp, int mod) {
        long res = 1;
        base %= mod;
        while (exp > 0) {
            if ((exp & 1) == 1) res = res * base % mod;
            base = base * base % mod;
            exp >>= 1;
        }
        return res;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();

        long total = modPow(10, N, MOD);
        long no0   = modPow(9, N, MOD);
        long no9   = modPow(9, N, MOD);
        long no0no9 = modPow(8, N, MOD);

        long ans = (total - no0 - no9 + no0no9) % MOD;
        if (ans < 0) ans += MOD;
        System.out.println(ans);
    }
}