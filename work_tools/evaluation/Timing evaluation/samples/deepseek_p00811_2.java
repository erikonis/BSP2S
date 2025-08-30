import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Integer> primes = sieveOfEratosthenes(100000);

        while (true) {
            int m = scanner.nextInt();
            int a = scanner.nextInt();
            int b = scanner.nextInt();
            if (m == 0 && a == 0 && b == 0) break;

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

    private static List<Integer> sieveOfEratosthenes(int n) {
        boolean[] isPrime = new boolean[n + 1];
        Arrays.fill(isPrime, true);
        isPrime[0] = isPrime[1] = false;
        for (int i = 2; i * i <= n; i++) {
            if (isPrime[i]) {
                for (int j = i * i; j <= n; j += i) {
                    isPrime[j] = false;
                }
            }
        }
        List<Integer> primes = new ArrayList<>();
        for (int i = 2; i <= n; i++) {
            if (isPrime[i]) {
                primes.add(i);
            }
        }
        return primes;
    }
}