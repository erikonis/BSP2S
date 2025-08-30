import java.util.Scanner;

public class BambooBlossoms {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            int m = scanner.nextInt();
            int n = scanner.nextInt();
            if (m == 0 && n == 0) break;

            int maxYears = m + n; // Initial estimate, adjust upwards as needed
            boolean[] covered = new boolean[maxYears + 1];
            int[] primes = generatePrimesUpTo(m + n);

            int count = 0;
            int year = m;
            while (count < n) {
                if (year >= covered.length) {
                    boolean[] newCovered = new boolean[covered.length * 2];
                    System.arraycopy(covered, 0, newCovered, 0, covered.length);
                    covered = newCovered;
                }
                if (!covered[year]) {
                    covered[year] = true;
                    count++;
                    for (int prime : primes) {
                        if (prime >= m) {
                            int multiple = year + prime;
                            if (multiple < covered.length) {
                                covered[multiple] = true;
                            }
                        }
                    }
                }
                year++;
            }

            // Find first uncovered year after m
            year = m;
            while (year < covered.length && covered[year]) {
                year++;
            }
            System.out.println(year);
        }
        scanner.close();
    }

    private static int[] generatePrimesUpTo(int limit) {
        boolean[] isPrime = new boolean[limit + 1];
        for (int i = 2; i <= limit; i++) {
            isPrime[i] = true;
        }

        for (int i = 2; i * i <= limit; i++) {
            if (isPrime[i]) {
                for (int j = i * i; j <= limit; j += i) {
                    isPrime[j] = false;
                }
            }
        }

        int count = 0;
        for (int i = 2; i <= limit; i++) {
            if (isPrime[i]) count++;
        }

        int[] primes = new int[count];
        int index = 0;
        for (int i = 2; i <= limit; i++) {
            if (isPrime[i]) primes[index++] = i;
        }
        return primes;
    }
}