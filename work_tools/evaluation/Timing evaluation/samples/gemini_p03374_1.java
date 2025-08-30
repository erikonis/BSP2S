import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        long C = sc.nextLong();
        long[] x = new long[N];
        long[] v = new long[N];
        for (int i = 0; i < N; i++) {
            x[i] = sc.nextLong();
            v[i] = sc.nextLong();
        }

        long[] sumV = new long[N + 1];
        for (int i = 0; i < N; i++) {
            sumV[i + 1] = sumV[i] + v[i];
        }

        long[] maxLeft = new long[N + 1];
        long[] maxLeftR = new long[N + 1]; // for going right then left
        maxLeft[0] = 0;
        maxLeftR[0] = 0;
        for (int i = 0; i < N; i++) {
            maxLeft[i + 1] = Math.max(maxLeft[i], sumV[i + 1] - x[i]);
            maxLeftR[i + 1] = Math.max(maxLeftR[i], sumV[i + 1] - 2 * x[i]);
        }

        long[] sumVR = new long[N + 1];
        for (int i = N - 1; i >= 0; i--) {
            sumVR[N - i] = sumVR[N - i - 1] + v[i];
        }

        long[] maxRight = new long[N + 1];
        long[] maxRightR = new long[N + 1]; // for going left then right
        maxRight[0] = 0;
        maxRightR[0] = 0;
        for (int i = 0; i < N; i++) {
            maxRight[i + 1] = Math.max(maxRight[i], sumVR[i + 1] - (C - x[N - 1 - i]));
            maxRightR[i + 1] = Math.max(maxRightR[i], sumVR[i + 1] - 2 * (C - x[N - 1 - i]));
        }

        long ans = 0;
        for (int i = 0; i <= N; i++) {
            // go right i times from current pos
            long currentV = sumV[i];
            long currentCost = x[i - 1]; // if i > 0
            if (i == 0) currentCost = 0;

            ans = Math.max(ans, currentV - currentCost + maxRightR[N - i]);

            // go left i times from current pos
            currentV = sumVR[i];
            currentCost = C - x[N - i]; // if i > 0
            if (i == 0) currentCost = 0;

            ans = Math.max(ans, currentV - currentCost + maxLeftR[N - i]);
        }

        // Case 1: only go right
        for (int i = 0; i < N; i++) {
            ans = Math.max(ans, sumV[i + 1] - x[i]);
        }

        // Case 2: only go left
        for (int i = 0; i < N; i++) {
            ans = Math.max(ans, sumVR[i + 1] - (C - x[N - 1 - i]));
        }

        System.out.println(ans);
    }
}