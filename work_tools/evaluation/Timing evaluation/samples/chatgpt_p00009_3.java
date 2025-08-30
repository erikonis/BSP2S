import java.util.*;

public class PrimeNumberCounter {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Integer> inputs = new ArrayList<>();
        int maxN = 0;
        while (sc.hasNextInt()) {
            int n = sc.nextInt();
            inputs.add(n);
            if (n > maxN) maxN = n;
        }
        boolean[] isPrime = new boolean[maxN + 1];
        Arrays.fill(isPrime, true);
        isPrime[0] = false;
        if (maxN > 0) isPrime[1] = false;
        for (int i = 2; i * i <= maxN; i++) {
            if (isPrime[i]) {
                for (int j = i * i; j <= maxN; j += i) {
                    isPrime[j] = false;
                }
            }
        }
        int[] prefix = new int[maxN + 1];
        for (int i = 1; i <= maxN; i++) {
            prefix[i] = prefix[i - 1] + (isPrime[i] ? 1 : 0);
        }
        for (int n : inputs) {
            System.out.println(prefix[n]);
        }
    }
}