import java.util.*;

public class Main {
    static class Child implements Comparable<Child> {
        int activeness, index;
        Child(int a, int i) {
            activeness = a;
            index = i;
        }
        public int compareTo(Child o) {
            return Integer.compare(o.activeness, activeness); // Descending
        }
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int[] A = new int[N];
        for (int i = 0; i < N; i++) A[i] = sc.nextInt();

        Child[] children = new Child[N];
        for (int i = 0; i < N; i++) children[i] = new Child(A[i], i);

        Arrays.sort(children);

        long[][] dp = new long[N+1][N+1];
        for (int i = 0; i <= N; i++)
            Arrays.fill(dp[i], Long.MIN_VALUE/2);
        dp[0][0] = 0;

        for (int k = 0; k < N; k++) {
            for (int l = 0; l <= k; l++) {
                int r = k - l;
                // Place child[k] to the leftmost available
                dp[k+1][l+1] = Math.max(
                    dp[k+1][l+1],
                    dp[k][l] + (long)children[k].activeness * Math.abs(children[k].index - l)
                );
                // Place child[k] to the rightmost available
                dp[k+1][l] = Math.max(
                    dp[k+1][l],
                    dp[k][l] + (long)children[k].activeness * Math.abs(children[k].index - (N-1-r))
                );
            }
        }

        long ans = 0;
        for (int l = 0; l <= N; l++) ans = Math.max(ans, dp[N][l]);
        System.out.println(ans);
    }
}