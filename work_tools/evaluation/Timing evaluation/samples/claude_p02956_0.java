import java.util.*;

public class Main {
    static final long MOD = 998244353;
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        
        int[][] points = new int[n][2];
        for (int i = 0; i < n; i++) {
            points[i][0] = sc.nextInt();
            points[i][1] = sc.nextInt();
        }
        
        long ans = 0;
        
        // For each point, count how many subsets include it in their bounding rectangle
        for (int i = 0; i < n; i++) {
            int xi = points[i][0];
            int yi = points[i][1];
            
            // Count points in each quadrant relative to (xi, yi)
            int left = 0, right = 0, down = 0, up = 0;
            
            for (int j = 0; j < n; j++) {
                if (points[j][0] <= xi) left++;
                if (points[j][0] >= xi) right++;
                if (points[j][1] <= yi) down++;
                if (points[j][1] >= yi) up++;
            }
            
            // Use inclusion-exclusion to count valid subsets
            long total = pow2(n) - 1; // All non-empty subsets
            
            // Subtract subsets that don't satisfy constraints
            long invalid = 0;
            invalid = (invalid + pow2(n - left)) % MOD; // No point with x <= xi
            invalid = (invalid + pow2(n - right)) % MOD; // No point with x >= xi
            invalid = (invalid + pow2(n - down)) % MOD; // No point with y <= yi
            invalid = (invalid + pow2(n - up)) % MOD; // No point with y >= yi
            
            // Add back over-subtracted
            invalid = (invalid - pow2(n - left - right + countBoth(points, xi, yi, 0)) + MOD) % MOD;
            invalid = (invalid - pow2(n - left - down + countBoth(points, xi, yi, 1)) + MOD) % MOD;
            invalid = (invalid - pow2(n - left - up + countBoth(points, xi, yi, 2)) + MOD) % MOD;
            invalid = (invalid - pow2(n - right - down + countBoth(points, xi, yi, 3)) + MOD) % MOD;
            invalid = (invalid - pow2(n - right - up + countBoth(points, xi, yi, 4)) + MOD) % MOD;
            invalid = (invalid - pow2(n - down - up + countBoth(points, xi, yi, 5)) + MOD) % MOD;
            
            // Continue inclusion-exclusion...
            // This becomes complex, let me use a different approach
        }
        
        // Alternative approach: for each subset, calculate f(T) directly
        // But this is O(2^n) which is too slow for n=200000
        
        // Better approach: contribution counting
        ans = 0;
        for (int i = 0; i < n; i++) {
            ans = (ans + countContributions(points, i)) % MOD;
        }
        
        System.out.println(ans);
    }
    
    static long countContributions(int[][] points, int idx) {
        int n = points.length;
        int xi = points[idx][0];
        int yi = points[idx][1];
        
        // Count points in different regions
        int leftDown = 0, leftUp = 0, rightDown = 0, rightUp = 0;
        
        for (int i = 0; i < n; i++) {
            if (points[i][0] <= xi && points[i][1] <= yi) leftDown++;
            if (points[i][0] <= xi && points[i][1] >= yi) leftUp++;
            if (points[i][0] >= xi && points[i][1] <= yi) rightDown++;
            if (points[i][0] >= xi && points[i][1] >= yi) rightUp++;
        }
        
        // Total subsets that include point idx in their bounding rectangle
        long total = (long)leftDown * leftUp % MOD * rightDown % MOD * rightUp % MOD;
        return total;
    }
    
    static int countBoth(int[][] points, int xi, int yi, int type) {
        int count = 0;
        for (int[] p : points) {
            boolean cond1 = false, cond2 = false;
            switch (type) {
                case 0: cond1 = p[0] <= xi; cond2 = p[0] >= xi; break;
                case 1: cond1 = p[0] <= xi; cond2 = p[1] <= yi; break;
                case 2: cond1 = p[0] <= xi; cond2 = p[1] >= yi; break;
                case 3: cond1 = p[0] >= xi; cond2 = p[1] <= yi; break;
                case 4: cond1 = p[0] >= xi; cond2 = p[1] >= yi; break;
                case 5: cond1 = p[1] <= yi; cond2 = p[1] >= yi; break;
            }
            if (cond1 && cond2) count++;
        }
        return count;
    }
    
    static long pow2(int exp) {
        if (exp < 0) return 0;
        long result = 1;
        long base = 2;
        while (exp > 0) {
            if (exp % 2 == 1) result = result * base % MOD;
            base = base * base % MOD;
            exp /= 2;
        }
        return result;
    }
}