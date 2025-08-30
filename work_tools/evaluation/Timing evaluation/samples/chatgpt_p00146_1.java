import java.util.*;
import java.io.*;

public class Main {
    static class Storehouse {
        int index; // input order (for DP)
        int s;     // storehouse number
        int d;     // distance from castle
        int v;     // number of boxes

        Storehouse(int index, int s, int d, int v) {
            this.index = index;
            this.s = s;
            this.d = d;
            this.v = v;
        }
    }

    static int n;
    static Storehouse[] hs;
    static double[][] dp;
    static int[][] prev;
    static final int INF = 1 << 30;
    static final int BOX_WEIGHT = 20;

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        hs = new Storehouse[n];
        for (int i = 0; i < n; i++) {
            int s = sc.nextInt();
            int d = sc.nextInt();
            int v = sc.nextInt();
            hs[i] = new Storehouse(i, s, d, v);
        }
        // DP: dp[mask][last]: minimal time to visit mask with last visited 'last'
        int N = 1 << n;
        dp = new double[N][n];
        prev = new int[N][n];
        for (int i = 0; i < N; i++) Arrays.fill(dp[i], Double.MAX_VALUE);
        for (int i = 0; i < N; i++) Arrays.fill(prev[i], -1);

        // Start from each storehouse as the 1st
        for (int i = 0; i < n; i++) {
            int mask = 1 << i;
            double time = timeToMove(0, hs[i].d, hs[i].v * BOX_WEIGHT); // from castle (0) to hs[i]
            dp[mask][i] = time;
        }

        // DP
        for (int mask = 1; mask < N; mask++) {
            for (int last = 0; last < n; last++) {
                if ((mask & (1 << last)) == 0) continue;
                double curTime = dp[mask][last];
                if (curTime == Double.MAX_VALUE) continue;
                int totalBoxes = 0;
                for (int k = 0; k < n; k++) if ((mask & (1 << k)) != 0) totalBoxes += hs[k].v;
                for (int next = 0; next < n; next++) {
                    if ((mask & (1 << next)) != 0) continue;
                    int nextMask = mask | (1 << next);
                    int newTotalBoxes = totalBoxes + hs[next].v;
                    int carriedWeight = totalBoxes * BOX_WEIGHT;
                    double moveTime = timeToMove(hs[last].d, hs[next].d, carriedWeight);
                    double nextTime = curTime + moveTime;
                    if (dp[nextMask][next] > nextTime) {
                        dp[nextMask][next] = nextTime;
                        prev[nextMask][next] = last;
                    }
                }
            }
        }

        // Find minimum
        double minTime = Double.MAX_VALUE;
        int lastIdx = -1;
        int maskAll = (1 << n) - 1;
        for (int i = 0; i < n; i++) {
            if (dp[maskAll][i] < minTime) {
                minTime = dp[maskAll][i];
                lastIdx = i;
            }
        }

        // Restore path
        int[] path = new int[n];
        int mask = maskAll;
        int idx = lastIdx;
        for (int i = n - 1; i >= 0; i--) {
            path[i] = hs[idx].s;
            int p = prev[mask][idx];
            mask ^= 1 << idx;
            idx = p;
        }

        // Output
        for (int i = 0; i < n; i++) {
            if (i > 0) System.out.print(" ");
            System.out.print(path[i]);
        }
        System.out.println();
    }

    // Move from pos1 to pos2, carrying weight in KG
    static double timeToMove(int pos1, int pos2, int weight) {
        double dist = Math.abs(pos1 - pos2);
        double speed = 2000.0 / (70 + weight);
        return dist / speed;
    }
}