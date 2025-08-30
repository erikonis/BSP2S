import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        long N = scanner.nextLong();
        long n = N;
        
        while (n % 2 == 0) {
            n /= 2;
        }
        
        long res = Long.MAX_VALUE;
        for (long d = 1; d * d <= n; d++) {
            if (n % d == 0) {
                long e = d;
                long f = n / d;
                res = Math.min(res, solve(N, e));
                res = Math.min(res, solve(N, f));
            }
        }
        System.out.println(res);
    }
    
    private static long solve(long N, long m) {
        long k = m;
        if (k % 2 == 0) {
            long tmp = k;
            k = 2 * N / tmp;
            if (k < tmp) {
                return Long.MAX_VALUE;
            }
        }
        return k;
    }
}