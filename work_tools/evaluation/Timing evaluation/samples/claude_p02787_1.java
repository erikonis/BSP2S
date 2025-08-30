import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int H = sc.nextInt();
        int N = sc.nextInt();
        
        int[] A = new int[N];
        int[] B = new int[N];
        
        for (int i = 0; i < N; i++) {
            A[i] = sc.nextInt();
            B[i] = sc.nextInt();
        }
        
        // dp[i] = minimum magic points to deal exactly i damage
        int[] dp = new int[H + 1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;
        
        for (int i = 0; i <= H; i++) {
            if (dp[i] == Integer.MAX_VALUE) continue;
            
            for (int j = 0; j < N; j++) {
                int nextDamage = Math.min(H, i + A[j]);
                dp[nextDamage] = Math.min(dp[nextDamage], dp[i] + B[j]);
            }
        }
        
        System.out.println(dp[H]);
    }
}