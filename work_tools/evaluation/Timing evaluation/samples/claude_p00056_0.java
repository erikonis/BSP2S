import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        // Sieve of Eratosthenes to find all primes up to 50000
        boolean[] isPrime = new boolean[50001];
        Arrays.fill(isPrime, true);
        isPrime[0] = isPrime[1] = false;
        
        for (int i = 2; i * i <= 50000; i++) {
            if (isPrime[i]) {
                for (int j = i * i; j <= 50000; j += i) {
                    isPrime[j] = false;
                }
            }
        }
        
        while (true) {
            int n = sc.nextInt();
            if (n == 0) break;
            
            int count = 0;
            for (int i = 2; i <= n / 2; i++) {
                if (isPrime[i] && isPrime[n - i]) {
                    count++;
                }
            }
            
            System.out.println(count);
        }
        
        sc.close();
    }
}