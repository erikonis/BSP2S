import java.util.*;

public class Main {
    static final int INF = Integer.MAX_VALUE / 2;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        int Ma = sc.nextInt();
        int Mb = sc.nextInt();

        int[] a = new int[N];
        int[] b = new int[N];
        int[] c = new int[N];

        int totalA = 0, totalB = 0;
        for (int i = 0; i < N; i++) {
            a[i] = sc.nextInt();
            b[i] = sc.nextInt();
            c[i] = sc.nextInt();
            totalA += a[i];
            totalB += b[i];
        }

        // DP[sumA][sumB] = minimum cost to obtain (sumA, sumB)
        int[][] dp = new int[totalA + 1][totalB + 1];
        for (int i = 0; i <= totalA; i++)
            Arrays.fill(dp[i], INF);
        dp[0][0] = 0;

        // For each package, update DP
        for (int idx = 0; idx < N; idx++) {
            int[][] next = new int[totalA + 1][totalB + 1];
            for (int i = 0; i <= totalA; i++)
                System.arraycopy(dp[i], 0, next[i], 0, totalB + 1);

            for (int sumA = 0; sumA <= totalA; sumA++) {
                for (int sumB = 0; sumB <= totalB; sumB++) {
                    if (dp[sumA][sumB] < INF) {
                        int na = sumA + a[idx];
                        int nb = sumB + b[idx];
                        if (na <= totalA && nb <= totalB) {
                            next[na][nb] = Math.min(next[na][nb], dp[sumA][sumB] + c[idx]);
                        }
                    }
                }
            }
            dp = next;
        }

        int answer = INF;
        // Check for all k such that (k*Ma, k*Mb) is possible
        for (int sumA = 1; sumA <= totalA; sumA++) {
            for (int sumB = 1; sumB <= totalB; sumB++) {
                if (dp[sumA][sumB] < INF) {
                    // Check if (sumA, sumB) is in ratio Ma:Mb
                    if (sumA * Mb == sumB * Ma) {
                        int k = sumA / Ma; // Since gcd(Ma, Mb)=1
                        if (k * Ma == sumA && k * Mb == sumB)
                            answer = Math.min(answer, dp[sumA][sumB]);
                    }
                }
            }
        }

        System.out.println(answer == INF ? -1 : answer);
    }
}