import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {

    static final int MAX_N = 100000;
    static final int MAX_SUM_CANDIDATE = 2 * (100000 + 400); // N + approx 400th prime after N
    static boolean[] isPrime;
    static List<Integer> primes;

    public static void main(String[] args) {
        sieveEratosthenes(MAX_SUM_CANDIDATE);
        Scanner sc = new Scanner(System.in);
        while (true) {
            int n = sc.nextInt();
            int p = sc.nextInt();
            if (n == -1 && p == -1) {
                break;
            }

            List<Integer> primesGreaterThanN = new ArrayList<>();
            for (int prime : primes) {
                if (prime > n) {
                    primesGreaterThanN.add(prime);
                }
            }

            List<Integer> sums = new ArrayList<>();
            for (int i = 0; i < primesGreaterThanN.size(); i++) {
                for (int j = i; j < primesGreaterThanN.size(); j++) {
                    sums.add(primesGreaterThanN.get(i) + primesGreaterThanN.get(j));
                    if (sums.size() > p + 20) { // Optimization to limit sums generated
                        break;
                    }
                }
                if (sums.size() > p + 20) { // Optimization to limit sums generated
                    break;
                }
            }
            Collections.sort(sums);
            System.out.println(sums.get(p - 1));
        }
        sc.close();
    }

    static void sieveEratosthenes(int limit) {
        isPrime = new boolean[limit + 1];
        for (int i = 0; i <= limit; i++) {
            isPrime[i] = true;
        }
        isPrime[0] = false;
        isPrime[1] = false;
        for (int p = 2; p * p <= limit; p++) {
            if (isPrime[p]) {
                for (int i = p * p; i <= limit; i += p)
                    isPrime[i] = false;
            }
        }
        primes = new ArrayList<>();
        for (int i = 2; i <= limit; i++) {
            if (isPrime[i]) {
                primes.add(i);
            }
        }
    }
}