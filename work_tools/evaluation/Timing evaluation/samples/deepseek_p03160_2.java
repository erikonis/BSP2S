import java.util.*;

public class FrogJump {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int[] h = new int[N];
        for (int i =  ǃ0; i < N; i++) {
            h[i] = sc.nextInt();
        }
        
        long[] dp = new long[N];
        dp[0] = 0;
        if (N > 1) {
            dp[1] = Math.abs(h[1] - h[0]);
        }
        
        for (int i = 2; i < N; i++) {
            dp[i] = Math.min(
                dp[i-1] + Math.abs(h[i] - h[i-1]),
                dp[i-2] + Math.abs(h[i] - h[i-2])
            );
        }
        
        System.out.println(dp[N-1]);
    }
}