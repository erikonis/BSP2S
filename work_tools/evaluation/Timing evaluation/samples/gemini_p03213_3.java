import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        sc.close();

        // Calculate the prime factorization of N!
        // primeFactors[i] stores the exponent of prime i in N!
        int[] primeFactors = new int[N + 1];
        for (int i = 2; i <= N; i++) {
            int num = i;
            for (int j = 2; j <= num; j++) {
                while (num % j == 0) {
                    primeFactors[j]++;
                    num /= j;
                }
            }
        }

        // Count primes with at least 74, 24, 14, 4, 2 exponents
        int count74 = 0;
        int count24 = 0;
        int count14 = 0;
        int count4 = 0;
        int count2 = 0;

        for (int i = 2; i <= N; i++) {
            if (primeFactors[i] >= 74) {
                count74++;
            }
            if (primeFactors[i] >= 24) {
                count24++;
            }
            if (primeFactors[i] >= 14) {
                count14++;
            }
            if (primeFactors[i] >= 4) {
                count4++;
            }
            if (primeFactors[i] >= 2) {
                count2++;
            }
        }

        long ans = 0;

        // Case 1: p^74
        // A number with 75 divisors can be p^74.
        // We need one prime with exponent at least 74.
        ans += count74;

        // Case 2: p1^24 * p2^2
        // We need one prime with exponent at least 24, and another prime with exponent at least 2.
        // The two primes must be distinct.
        // If p1 has exponent >= 24, and p2 has exponent >= 2, and p1 != p2.
        // Number of choices for p1: count24
        // Number of choices for p2: count2 - 1 (since p2 must be different from p1)
        ans += (long) count24 * (count2 - 1);

        // Case 3: p1^14 * p2^4
        // We need one prime with exponent at least 14, and another prime with exponent at least 4.
        // The two primes must be distinct.
        ans += (long) count14 * (count4 - 1);

        // Case 4: p1^4 * p2^4 * p3^2
        // We need three distinct primes with exponents at least 4, 4, and 2 respectively.
        // Choose 3 primes from those with exponent >= 4: C(count4, 3) ways.
        // From these 3 primes, choose 2 to have exponent 4 and 1 to have exponent 2. This is automatically handled by the product.
        // No, this is incorrect. We need to choose 3 distinct primes.
        // Choose one prime with exponent >= 4 (p1)
        // Choose another prime with exponent >= 4 (p2), different from p1
        // Choose a third prime with exponent >= 2 (p3), different from p1 and p2
        // The order of choosing p1 and p2 doesn't matter, so divide by 2! for the two p^4 terms.
        ans += (long) count4 * (count4 - 1) / 2 * (count2 - 2);

        System.out.println(ans);
    }
}