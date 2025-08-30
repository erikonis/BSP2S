import java.util.*;

public class Main {
    // Sieve of Eratosthenes
    static List<Integer> sieve(int n) {
        boolean[] isPrime = new boolean[n + 1];
        Arrays.fill(isPrime, true);
        isPrime[0] = isPrime[1] = false;
        List<Integer> primes = new ArrayList<>();
        for (int i = 2; i <= n; i++) {
            if (isPrime[i]) {
                primes.add(i);
                for (int j = i * 2; j <= n; j += i) {
                    isPrime[j] = false;
                }
            }
        }
        return primes;
    }

    // Count exponent of p in N!
    static int countExponent(int n, int p) {
        int exp = 0;
        int x = p;
        while (x <= n) {
            exp += n / x;
            x *= p;
        }
        return exp;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();

        List<Integer> primes = sieve(N);
        List<Integer> exponents = new ArrayList<>();
        for (int p : primes) {
            exponents.add(countExponent(N, p));
        }

        // Find number of ways to pick exponents so that product of (ei+1) = 75
        // 75 = 75, 25*3, 15*5, 5*5*3

        int cnt = 0;

        // Pattern 1: one exponent >= 74
        for (int i = 0; i < exponents.size(); i++) {
            if (exponents.get(i) >= 74) cnt++;
        }

        // Pattern 2: one >= 24, another >= 2 (distinct)
        for (int i = 0; i < exponents.size(); i++) {
            if (exponents.get(i) >= 24) {
                for (int j = 0; j < exponents.size(); j++) {
                    if (i != j && exponents.get(j) >= 2) cnt++;
                }
            }
        }

        // Pattern 3: one >= 14, another >= 4 (distinct)
        for (int i = 0; i < exponents.size(); i++) {
            if (exponents.get(i) >= 14) {
                for (int j = 0; j < exponents.size(); j++) {
                    if (i != j && exponents.get(j) >= 4) cnt++;
                }
            }
        }

        // Pattern 4: three primes, exponents >= 4,2,2 (all distinct)
        for (int i = 0; i < exponents.size(); i++) {
            if (exponents.get(i) >= 4) {
                for (int j = 0; j < exponents.size(); j++) {
                    if (j == i || exponents.get(j) < 2) continue;
                    for (int k = 0; k < exponents.size(); k++) {
                        if (k == i || k == j || exponents.get(k) < 2) continue;
                        cnt++;
                    }
                }
            }
        }
        // Each triple is counted 6 times (permutations of 3)
        cnt /= 6;

        System.out.println(cnt);
    }
}