import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Precompute primes up to 100000 using Sieve of Eratosthenes
        int maxM = 100000;
        boolean[] isPrime = new boolean[maxM + 1];
        Arrays.fill(isPrime, true);
        isPrime[0] = isPrime[1] = false;
        for (int i = 2; i * i <= maxM; i++) {
            if (isPrime[i]) {
                for (int j = i * i; j <= maxM; j += i) {
                    isPrime[j] = false;
                }
            }
        }
        List<Integer> primes = new ArrayList<>();
        for (int i = 2; i <= maxM; i++) {
            if (isPrime[i]) {
                primes.add(i);
            }
        }

        while (true) {
            int m = scanner.nextInt();
            int a = scanner.nextInt();
            int b = scanner.nextInt();
            if (m == 0 && a == 0 && b == 0) {
                break;
            }

            double ratio = (double) a / b;
            int bestP = 0, bestQ = 0;
            int maxArea = 0;

            for (int i = 0; i < primes.size(); i++) {
                int p = primes.get(i);
                if (p * p > m) break;
                for (int j = i; j < primes.size(); j++) {
                    int q = primes.get(j);
                    if (p * q > m) break;
                    double currentRatio = (double) p / q;
                    if (currentRatio >= ratio && currentRatio <= 1.0) {
                        int area = p * q;
                        if (area > maxArea) {
                            maxArea = area;
                            bestP = p;
                            bestQ = q;
                        }
                    }
                }
            }

            System.out.println(bestP + " " + bestQ);
        }
    }
}