import java.util.Scanner;

public class KnapsackUnbounded {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int N = scanner.nextInt();
        int W = scanner.nextInt();
        int[] v = new int[N];
        int[] w = new int[N];
        
        for (int i = 0; i < N; i++) {
            v[i] = scanner.nextInt();
            w[i] = scanner.nextInt();
        }
        
        int[] dp = new int[W + 1];
        for (int i = 0; i <= W; i++) {
            for (int j = 0; j < N; j++) {
                if (w[j] <= i) {
                    dp[i] = Math.max(dp[i], dp[i - w[j]] + v[j]);
                }
            }
        }
        
        System.out.println(dp[W]);
    }
}