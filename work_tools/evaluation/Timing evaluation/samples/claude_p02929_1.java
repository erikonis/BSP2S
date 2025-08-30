import java.util.*;

public class Main {
    static final int MOD = 1000000007;
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        String S = sc.next();
        
        int len = 2 * N;
        long[][][] dp = new long[len][len][2];
        
        // Base cases
        for (int i = 0; i < len; i++) {
            dp[i][i-1][0] = 1;
        }
        
        for (int length = 0; length <= len; length += 2) {
            for (int i = 0; i + length - 1 < len; i++) {
                int j = i + length - 1;
                
                if (length == 0) {
                    dp[i][j][0] = 1;
                    continue;
                }
                
                // Try pairing i with each valid position
                for (int k = i + 1; k <= j; k += 2) {
                    for (int left = 0; left < 2; left++) {
                        for (int right = 0; right < 2; right++) {
                            // Check if this configuration works
                            boolean valid = true;
                            
                            // Calculate final state for positions i to k
                            for (int pos = i; pos <= k && valid; pos++) {
                                boolean initialBlack = (S.charAt(pos) == 'B');
                                boolean flipped = true; // from pair (i,k)
                                
                                if (pos > i && pos < k) {
                                    flipped ^= (left == 1);
                                }
                                if (pos > k) {
                                    flipped ^= (right == 1);
                                }
                                
                                boolean finalWhite = initialBlack ^ flipped;
                                if (!finalWhite) valid = false;
                            }
                            
                            if (valid) {
                                dp[i][j][1] = (dp[i][j][1] + dp[i+1][k-1][left] * dp[k+1][j][right]) % MOD;
                            }
                        }
                    }
                }
            }
        }
        
        System.out.println(dp[0][len-1][0]);
    }
}