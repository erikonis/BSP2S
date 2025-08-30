import java.util.*;

public class Main {
    static final int MOD = 1000000007;
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        
        // dp[i][last2][last1] = number of valid strings of length i ending with last2, last1
        // Use 0=A, 1=C, 2=G, 3=T
        long[][][] dp = new long[N + 1][4][4];
        
        // Base case: length 2
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                dp[2][i][j] = 1;
            }
        }
        
        // Fill dp table
        for (int len = 3; len <= N; len++) {
            for (int prev2 = 0; prev2 < 4; prev2++) {
                for (int prev1 = 0; prev1 < 4; prev1++) {
                    for (int curr = 0; curr curr < 4; curr++) {
                        // Check if adding curr creates forbidden pattern
                        if (isValid(prev2, prev1, curr)) {
                            dp[len][prev1][curr] = (dp[len][prev1][curr] + dp[len-1][prev2][prev1]) % MOD;
                        }
                    }
                }
            }
        }
        
        long result = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                result = (result + dp[N][i][j]) % MOD;
            }
        }
        
        System.out.println(result);
    }
    
    static boolean isValid(int prev2, int prev1, int curr) {
        // Check for AGC pattern: A=0, G=2, C=1
        if (prev2 == 0 && prev1 == 2 && curr == 1) return false; // AGC
        
        // Check for patterns that become AGC after one adjacent swap
        if (prev2 == 0 && prev1 == 1 && curr == 2) return false; // ACG -> AGC
        if (prev2 == 2 && prev1 == 0 && curr == 1) return false; // GAC -> AGC
        
        return true;
    }
}