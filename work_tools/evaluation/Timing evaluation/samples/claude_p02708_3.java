import java.util.Scanner;

public class Main {
    static final int MOD = 1000000007;
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        long N = sc.nextLong();
        long K = sc.nextLong();
        
        long result = 0;
        
        for (long m = K; m <= N + 1; m++) {
            long count = (m % MOD) * ((N - m + 1) % MOD) % MOD + 1;
            count %= MOD;
            result = (result + count) % MOD;
        }
        
        System.out.println(result);
    }
}