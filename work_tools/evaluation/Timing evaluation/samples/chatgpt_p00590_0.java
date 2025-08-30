import java.util.*;

public class PairOfPrimes {
    static final int MAX = 10000;
    static boolean[] isPrime = new boolean[MAX + 1];

    // Sieve of Eratosthenes
    static void sieve() {
        Arrays.fill(isPrime, true);
        isPrime[0] = isPrime[1] = false;
        for (int i = 2; i * i <= MAX; i++) {
            if (isPrime[i]) {
                for (int j = i * i; j <= MAX; j += i) {
                    isPrime[j] = false;
                }
            }
        }
    }

    public static void main(String[] args) {
        sieve();
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextInt()) {
            int N = sc.nextInt();
            int count = 0;
            int mid = N / 2;
            for (int i = 1; i <= mid; i++) {
                int a = i;
                int b = N - i + 1;
                if (isPrime[a] && isPrime[b]) count++;
            }
            System.out.println(count);
        }
    }
}