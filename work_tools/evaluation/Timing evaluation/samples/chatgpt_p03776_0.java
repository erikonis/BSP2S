import java.util.*;
import java.io.*;

public class Main {
    static long[][] comb = new long[55][55];

    // Precompute combinations (nCr)
    static void precomputeCombination(int n) {
        for (int i = 0; i <= n; i++) {
            comb[i][0] = comb[i][i] = 1;
            for (int j = 1; j < i; j++) {
                comb[i][j] = comb[i-1][j-1] + comb[i-1][j];
            }
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int A = sc.nextInt();
        int B = sc.nextInt();
        long[] v = new long[N];
        for (int i = 0; i < N; i++) v[i] = sc.nextLong();

        Arrays.sort(v);
        // Descending order
        for (int i = 0; i < N/2; i++) {
            long tmp = v[i];
            v[i] = v[N-1-i];
            v[N-1-i] = tmp;
        }

        precomputeCombination(N);

        // Calculate max mean for k = A..B
        double maxMean = 0;
        long ways = 0;

        // Find the max mean and for which k(s) it occurs
        for (int k = A; k <= B; k++) {
            long sum = 0;
            for (int i = 0; i < k; i++) {
                sum += v[i];
            }
            double mean = (double) sum / k;
            if (mean > maxMean + 1e-9) {
                maxMean = mean;
                ways = countWays(v, N, k);
            } else if (Math.abs(mean - maxMean) < 1e-9) {
                ways += countWays(v, N, k);
            }
        }
        System.out.printf("%.6f\n", maxMean);
        System.out.println(ways);
    }

    // Count number of ways to select k items to achieve max mean
    static long countWays(long[] v, int N, int k) {
        // Find the value at kth position (since sorted descending)
        long val = v[k-1];
        int total = 0, pick = 0;
        for (int i = 0; i < N; i++) {
            if (v[i] == val) total++;
            if (i < k && v[i] == val) pick++;
        }
        // Count how many values >= v[k-1] in top k
        int cnt = 0;
        for (int i = 0; i < N; i++) {
            if (v[i] > val) cnt++;
        }
        // All elements in top k are the same
        if (cnt == 0) {
            return comb[total][k];
        } else {
            return comb[total][pick];
        }
    }
}