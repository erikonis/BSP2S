import java.util.*;

public class Main {
    static final int MAX = 51000; // 50000 + buffer

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Sieve of Eratosthenes
        boolean[] isPrime = new boolean[MAX];
        Arrays.fill(isPrime, true);
        isPrime[0] = isPrime[1] = false;
        for (int i = 2; i * i < MAX; i++) {
            if (isPrime[i]) {
                for (int j = i * i; j < MAX; j += i) {
                    isPrime[j] = false;
                }
            }
        }

        // Collect all primes in order
        ArrayList<Integer> primes = new ArrayList<>();
        for (int i = 2; i < MAX; i++) {
            if (isPrime[i]) primes.add(i);
        }

        while (sc.hasNextInt()) {
            int n = sc.nextInt();

            // Find largest prime < n
            int prevPrime = -1, nextPrime = -1;
            // Lower bound search for n in primes
            int idx = Collections.binarySearch(primes, n);
            if (idx < 0) idx = -idx - 1;
            else idx = idx; // n is prime, but we want the one < n

            prevPrime = primes.get(idx - 1);

            // Now find smallest prime > n
            int idx2 = Collections.binarySearch(primes, n);
            if (idx2 < 0) idx2 = -idx2 - 1;
            else idx2++; // n is prime, so next one

            nextPrime = primes.get(idx2);

            System.out.println(prevPrime + " " + nextPrime);
        }
    }
}