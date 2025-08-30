import java.util.*;

public class Main {
    static final int MOD = 1000000007;
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        String s = sc.next();
        
        // dp[i] = number of valid subsequences considering first i characters
        long[] dp = new long[n + 1];
        dp[0] = 0; // empty subsequence not allowed (need at least 1 char)
        
        // last[c] = dp value when character c was last seen
        Map<Character, Long> last = new HashMap<>();
        
        for (int i = 1; i <= n; i++) {
            char c = s.charAt(i - 1);
            
            // Current dp = previous dp * 2 + 1 (for single char subsequence)
            // But subtract subsequences that would have duplicate chars
            dp[i] = (dp[i - 1] * 2 + 1) % MOD;
            
            if (last.containsKey(c)) {
                dp[i] = (dp[i] - last.get(c) + MOD) % MOD;
            }
            
            last.put(c, dp[i - 1] + 1);
        }
        
        System.out.println(dp[n]);
    }
}