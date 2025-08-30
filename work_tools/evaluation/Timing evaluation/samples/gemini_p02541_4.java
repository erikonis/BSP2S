import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        long n = sc.nextLong();

        long minK = Long.MAX_VALUE;

        Set<Long> divisors = new HashSet<>();
        for (long i = 1; i * i <= n; i++) {
            if (n % i == 0) {
                divisors.add(i);
                divisors.add(n / i);
            }
        }

        for (long d : divisors) {
            long M = n / d;
            long M2 = 2 * M;

            Set<Long> divisorsM2 = new HashSet<>();
            for (long i = 1; i * i <= M2; i++) {
                if (M2 % i == 0) {
                    divisorsM2.add(i);
                    divisorsM2.add(M2 / i);
                }
            }

            for (long x : divisorsM2) {
                long k = x - 1;
                if (k > 0 && (k * (k + 1) / 2) % n == 0) {
                    minK = Math.min(minK, k);
                }
            }
        }
        System.out.println(minK);
        sc.close();
    }
}