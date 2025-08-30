import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        while (true) {
            int N = sc.nextInt();
            int P = sc.nextInt();
            
            if (N == -1 && P == -1) break;
            
            // Generate primes greater than N
            List<Integer> primes = new ArrayList<>();
            for (int i = N + 1; i <= 200000; i++) {
                if (isPrime(i)) {
                    primes.add(i);
                    if (primes.size() >= 200) break; // Should be enough for P <= 100
                }
            }
            
            // Generate all sums
            List<Integer> sums = new ArrayList<>();
            for (int i = 0; i < primes.size(); i++) {
                for (int j = i; j < primes.size(); j++) {
                    sums.add(primes.get(i) + primes.get(j));
                }
            }
            
            // Sort sums
            Collections.sort(sums);
            
            // Output P-th element (1-indexed)
            System.out.println(sums.get(P - 1));
        }
        
        sc.close();
    }
    
    private static boolean isPrime(int n) {
        if (n < 2) return false;
        if (n == 2) return true;
        if (n % 2 == 0) return false;
        
        for (int i = 3; i * i <= n; i += 2) {
            if (n % i == 0) return false;
        }
        return true;
    }
}