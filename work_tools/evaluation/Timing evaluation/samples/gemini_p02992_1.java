import java.util.*;

public class Main {
    static final int MOD = 1_000_000_007;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        long N = sc.nextLong();
        int K = sc.nextInt();
        sc.close();

        List<Long> divisors = new ArrayList<>();
        for (long i = 1; i * i <= N; i++) {
            divisors.add(i);
            if (i * i != N) {
                divisors.add(N / i);
            }
        }
        Collections.sort(divisors);

        int m = divisors.size();
        long[][] dp = new long[K][m];

        // Base case: K=1
        for (int j = 0; j < m; j++) {
            dp[0][j] = divisors.get(j);
        }

        for (int i = 1; i < K; i++) {
            for (int j = 0; j < m; j++) {
                long currentVal = divisors.get(j);
                for (int prevIdx = 0; prevIdx < m; prevIdx++) {
                    long prevVal = divisors.get(prevIdx);
                    if (currentVal * prevVal <= N) {
                        dp[i][j] = (dp[i][j] + dp[i - 1][prevIdx]) % MOD;
                    }
                }
            }
        }

        long total = 0;
        for (int j = 0; j < m; j++) {
            total = (total + dp[K - 1][j]) % MOD;
        }

        System.out.println(total);
    }
}