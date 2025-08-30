import java.util.*;
import java.io.*;

public class Main {
    static final int MOD = 998244353;
    static long[] fact, invFact;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        String S = br.readLine();

        fact = new long[3*N + 1];
        invFact = new long[3*N + 1];
        fact[0] = 1;
        for (int i = 1; i <= 3*N; i++) {
            fact[i] = fact[i-1] * i % MOD;
        }
        invFact[3*N] = pow(fact[3*N], MOD-2);
        for (int i = 3*N-1; i >= 0; i--) {
            invFact[i] = invFact[i+1] * (i+1) % MOD;
        }

        long res = fact[3*N] % MOD;
        res = res * pow(invFact[N], 3) % MOD;
        res = res * pow(fact[N], 3) % MOD;

        System.out.println(res);
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