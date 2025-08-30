import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int K = sc.nextInt();
        String S = sc.next();
        
        int maxLen = 0;
        
        // Try each possible left endpoint
        for (int l = 0; l < N; l++) {
            int operations = 0;
            int r = l;
            
            // Extend right endpoint as far as possible
            while (r < N) {
                // If we're starting a new 0-block, count it
                if (S.charAt(r) == '0' && (r == l || S.charAt(r-1) == '1')) {
                    operations++;
                    if (operations > K) break;
                }
                r++;
            }
            
            // r is now one past the last valid position
            maxLen = Math.max(maxLen, r - l);
        }
        
        System.out.println(maxLen);
    }
}