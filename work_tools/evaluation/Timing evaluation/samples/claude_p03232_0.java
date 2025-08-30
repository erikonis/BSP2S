import java.util.Scanner;

public class Main {
    private static final long MOD = 1000000007L;
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        long[] a = new long[n];
        
        for (int i = 0; i < n; i++) {
            a[i] = sc.nextLong();
        }
        
        // Calculate N!
        long factorial = 1;
        for (int i = 1; i <= n; i++) {
            factorial = (factorial * i) % MOD;
        }
        
        // Precompute modular inverses
        long[] inv = new long[n + 1];
        inv[1] = 1;
        for (int i = 2; i <= n; i++) {
            inv[i] = MOD - (MOD / i) * inv[(int)(MOD % i)] % MOD;
        }
        
        long result = 0;
        
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                int dist = j - i + 1;
                long contribution = (a[i] * factorial) % MOD;
                contribution = (contribution * inv[dist]) % MOD;
                result = (result + contribution) % MOD;
            }
        }
        
        System.out.println(result);
    }
}