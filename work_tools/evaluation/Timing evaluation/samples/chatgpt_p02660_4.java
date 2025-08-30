import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        long N = sc.nextLong();
        Map<Long, Integer> factors = primeFactorize(N);

        int count = 0;
        for (int exp : factors.values()) {
            int e = exp;
            int k = 1;
            while (e >= k) {
                e -= k;
                k++;
                count++;
            }
        }
        System.out.println(count);
    }

    static Map<Long, Integer> primeFactorize(long n) {
        Map<Long, Integer> map = new HashMap<>();
        for (long i = 2; i * i <= n; i++) {
            int cnt = 0;
            while (n % i == 0) {
                n /= i;
                cnt++;
            }
            if (cnt > 0) map.put(i, cnt);
        }
        if (n > 1) map.put(n, 1);
        return map;
    }
}