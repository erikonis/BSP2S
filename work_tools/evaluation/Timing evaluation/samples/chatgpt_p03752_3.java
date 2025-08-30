import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int K = sc.nextInt();
        int[] a = new int[N];
        for (int i = 0; i < N; i++) a[i] = sc.nextInt();

        int minCost = Integer.MAX_VALUE;

        // Try all subsets of buildings to make visible, of size K
        for (int mask = 0; mask < (1 << N); mask++) {
            if (Integer.bitCount(mask) < K) continue;

            int maxSeen = 0;
            int cost = 0;
            int[] b = Arrays.copyOf(a, N);

            for (int i = 0; i < N; i++) {
                if (((mask >> i) & 1) == 1) { // want to make building i visible
                    if (b[i] <= maxSeen) {
                        int inc = maxSeen + 1 - b[i];
                        cost += inc;
                        b[i] = maxSeen + 1;
                    }
                }
                if (b[i] > maxSeen) maxSeen = b[i];
            }
            minCost = Math.min(minCost, cost);
        }

        System.out.println(minCost);
    }
}