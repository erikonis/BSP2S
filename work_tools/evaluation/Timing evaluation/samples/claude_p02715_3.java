import java.util.*;

public class Main {
    static final int MOD = 1000000007;
    
    static long power(long base, long exp, long mod) {
        long result = 1;
        base %= mod;
        while (exp > 0) {
            if (exp % 2 == 1) {
                result = (result * base) % mod;
            }
            base = (base * base) % mod;
            exp /= 2;
        }
        return result;
    }
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int K = sc.nextInt();
        
        // Compute Möbius function values
        int[] mu = new int[K + 1];
        boolean[] isPrime = new boolean[K + 1];
        Arrays.fill(isPrime, true);
        Arrays.fill(mu, 1);
        
        for (int i = 2; i <= K; i++) {
            if (isPrime[i]) {
                for (int j = i; j <= K; j += i) {
                    isPrime[j] = false;
                    mu[j] *= -1;
                }
                for (long j = (long)i * i; j <= K; j += (long)i * i) {
                    mu[(int)j] = 0;
                }
            }
        }
        
        long answer = 0;
        
        for (int g = 1; g <= K; g++) {
            long count = 0;
            
            // Count sequences with GCD exactly g using Möbius inversion
            for (int d = 1; d * g <= K; d++) {
                long sequences = power(K / (d * g), N, MOD);
                count = (count + (long)mu[d] * sequences % MOD + MOD) % MOD;
            }
            
            answer = (answer + (long)g * count % MOD) % MOD;
        }
        
        System.out.println(answer);
    }
}