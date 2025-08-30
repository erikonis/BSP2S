import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int K = sc.nextInt();
        
        int[] h = new int[N + 1];
        for (int i = 1; i <= N; i++) {
            h[i] = sc.nextInt();
        }
        
        // dp[i] = minimum cost to reach stone i
        int[] dp = new int[N + 1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[1] = 0; // Starting at stone 1 with 0 cost
        
        for (int i = 1; i <= N; i++) {
            if (dp[i] == Integer.MAX_VALUE) continue;
            
            // Try jumping to stones i+1, i+2, ..., i+K
            for (int j = i + 1; j <= Math.min(i + K, N); j++) {
                int cost = Math.abs(h[i] - h[j]);
                dp[j] = Math.min(dp[j], dp[i] + cost);
            }
        }
        
        System.out.println(dp[N]);
        sc.close();
    }
}