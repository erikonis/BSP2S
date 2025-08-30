import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt();
        
        while (T-- > 0) {
            long N = sc.nextLong();
            long A = sc.nextLong();
            long B = sc.nextLong();
            long C = sc.nextLong();
            long D = sc.nextLong();
            
            long minCost = Long.MAX_VALUE;
            
            // Try different combinations of 2^a * 3^b * 5^c
            // Limit bounds to avoid overflow and keep reasonable search space
            for (int a = 0; a <= 60; a++) {
                long pow2 = 1L;
                for (int i = 0; i < a; i++) {
                    if (pow2 > N) break;
                    pow2 *= 2;
                }
                if (pow2 > N * 2) break;
                
                for (int b = 0; b <= 40; b++) {
                    long pow23 = pow2;
                    for (int i = 0; i < b; i++) {
                        if (pow23 > N) break;
                        pow23 *= 3;
                    }
                    if (pow23 > N * 2) break;
                    
                    for (int c = 0; c <= 30; c++) {
                        long current = pow23;
                        for (int i = 0; i < c; i++) {
                            if (current > N) break;
                            current *= 5;
                        }
                        if (current > N * 2) break;
                        
                        // Cost to reach this number
                        long cost = (long)a * A + (long)b * B + (long)c * C;
                        
                        // Add cost to reach N from current
                        if (current == 0) {
                            cost += D + Math.abs(N - 1) * D;
                        } else {
                            cost += Math.abs(N - current) * D;
                        }
                        
                        minCost = Math.min(minCost, cost);
                    }
                }
            }
            
            System.out.println(minCost);
        }
    }
}