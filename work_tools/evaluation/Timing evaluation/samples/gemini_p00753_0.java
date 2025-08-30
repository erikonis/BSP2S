import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int MAX_N = 123456 * 2;
        boolean[] isPrime = new boolean[MAX_N + 1];
        
        // Sieve of Eratosthenes
        for (int i = 2; i <= MAX_N; i++) {
            isPrime[i] = true;
        }
        for (int p = 2; p * p <= MAX_N; p++) {
            if (isPrime[p]) {
                for (int i = p * p; i <= MAX_N; i += p)
                    isPrime[i] = false;
            }
        }

        while (true) {
            int n = sc.nextInt();
            if (n == 0) {
                break;
            }

            int count = 0;
            for (int i = n + 1; i <= 2 * n; i++) {
                if (isPrime[i]) {
                    count++;
                }
            }
            System.out.println(count);
        }
        sc.close();
    }
}