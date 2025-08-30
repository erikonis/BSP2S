import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Integer> inputs = new ArrayList<>();
        int maxInput = 0;
        
        while (true) {
            int n = scanner.nextInt();
            if (n == 0) break;
            inputs.add(n);
            if (n > maxInput) {
                maxInput = n;
            }
        }

        int limit = Math.max(maxInput, 20);
        boolean[] isPrime = new boolean[limit + 10];
        Arrays.fill(isPrime, true);
        isPrime[0] = isPrime[1] = false;
        
        for (int i = 2; i * i <= limit; i++) {
            if (isPrime[i]) {
                for (int j = i * i; j <= limit; j += i) {
                    isPrime[j] = false;
                }
            }
        }

        List<Integer> quadPrimes = new ArrayList<>();
        for (int a = 5; a <= limit - 8; a++) {
            if (isPrime[a] && isPrime[a + 2] && isPrime[a + 6] && isPrime[a + 8]) {
                quadPrimes.add(a + 8);
            }
        }

        for (int n : inputs) {
            int result = 13;
            for (int num : quadPrimes) {
                if (num <= n) {
                    result = num;
                } else {
                    break;
                }
            }
            System.out.println(result);
        }
    }
}