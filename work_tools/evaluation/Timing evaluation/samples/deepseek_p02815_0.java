import java.util.*;
import java.io.*;

public class Main {
    static final long MOD = 1000000007;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        String[] parts = br.readLine().split(" ");
        long[] C = new long[N];
        for (int i = 0; i < N; i++) {
            C[i] = Long.parseLong(parts[i]);
        }
        Arrays.sort(C);
        
        long[] pow2 = new long[N + 1];
        pow2[0] = 1;
        for (int i = 1; i <= N; i++) {
            pow2[i] = pow2[i - 1] * 2 % MOD;
        }
        
        long result = 0;
        for (int i = 0; i < N; i++) {
            long k = (N - i - 1 >= 0) ? (N - i - 1) : 0;
            long term = (pow2[k] * (k + 2) % MOD) * pow2[i] % MOD;
            term = term * C[i] % MOD;
            result = (result + term) % MOD;
        }
        
        result = result * pow2[N] % MOD;
        System.out.println(result);
    }
}