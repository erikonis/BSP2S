import java.util.*;

public class Main {
    static final int MOD = 1000000007;
    static long[] fact, invFact;
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int H = sc.nextInt();
        int W = sc.nextInt();
        int A = sc.nextInt();
        int B = sc.nextInt();
        
        // Precompute factorials and inverse factorials
        int maxN = H + W;
        fact = new long[maxN + 1];
        invFact = new long[maxN + 1];
        fact[0] = 1;
        for (int i = 1; i <= maxN; i++) {
            fact[i] = (fact[i-1] * i) % MOD;
        }
        invFact[maxN] = modInverse(fact[maxN]);
        for (int i = maxN - 1; i >= 0; i--) {
            invFact[i] = (invFact[i+1] * (i+1)) % MOD;
        }
        
        // Total paths without restriction
        long totalPaths = combination(H + W - 2, H - 1);
        
        // Paths that go through forbidden region
        // These are paths that pass through the rectangle from (H-A, 0) to (H-1, B-1)
        long forbiddenPaths = 0;
        
        // Use inclusion-exclusion principle
        // We need to subtract paths that go through the forbidden region
        // A path goes through forbidden region if it passes through any cell (i,j) where H-A <= i <= H-1 and 0 <= j <= B-1
        
        // Alternative approach: 
        // Total paths = Paths going above forbidden region + Paths going right of forbidden region - Paths going through corner
        
        // Paths that avoid forbidden region = 
        // Paths going through (H-A-1, W-1) + Paths going through (H-1, B) - Paths going through (H-A-1, B)
        
        long pathsAbove = 0, pathsRight = 0, pathsCorner = 0;
        
        // Paths going through (H-A-1, W-1) - these avoid forbidden region by staying above
        if (H - A - 1 >= 0) {
            pathsAbove = (combination(H - A - 1 + W - 1, H - A - 1) * combination(0, 0)) % MOD;
            pathsAbove = combination(H - A - 1 + W - 1, H - A - 1);
        }
        
        // Paths going through (H-1, B) - these avoid forbidden region by going right first
        if (B < W) {
            pathsRight = (combination(H - 1 + B, H - 1) * combination(W - 1 - B, 0)) % MOD;
            pathsRight = combination(H - 1 + B, H - 1);
        }
        
        // Paths going through both conditions (intersection) - through point (H-A-1, B)
        if (H - A - 1 >= 0 && B < W) {
            pathsCorner = combination(H - A - 1 + B, H - A - 1);
        }
        
        // But this approach is getting complex. Let me use simpler inclusion-exclusion:
        
        // Forbidden paths = paths that go through bottom-left corner of forbidden region (H-A, 0) to any point in forbidden region, then to end
        forbiddenPaths = 0;
        for (int i = H - A; i < H; i++) {
            for (int j = 0; j < B; j++) {
                long pathsToForbidden = combination(i + j, i);
                long pathsFromForbidden = combination((H - 1 - i) + (W - 1 - j), H - 1 - i);
                forbiddenPaths = (forbiddenPaths + (pathsToForbidden * pathsFromForbidden) % MOD) % MOD;
            }
        }
        
        // Actually, let's use the simpler approach:
        // Total valid paths = Total paths - Paths through forbidden region
        // Paths through forbidden region = Paths to (H-A, 0) × Paths from (H-A, 0) to (H-1, W-1)
        
        long pathsToForbiddenStart = combination((H - A) + 0, H - A);
        long pathsFromForbiddenInRegion = combination(A + (W - 1), A);
        
        forbiddenPaths = (pathsToForbiddenStart * pathsFromForbiddenInRegion) % MOD;
        
        long result = (totalPaths - forbiddenPaths + MOD) % MOD;
        System.out.println(result);
    }
    
    static long combination(int n, int r) {
        if (r < 0 || r > n) return 0;
        return (fact[n] * invFact[r] % MOD) * invFact[n - r] % MOD;
    }
    
    static long modInverse(long a) {
        return modPow(a, MOD - 2);
    }
    
    static long modPow(long base, long exp) {
        long result = 1;
        while (exp > 0) {
            if (exp % 2 == 1) {
                result = (result * base) % MOD;
            }
            base = (base * base) % MOD;
            exp /= 2;
        }
        return result;
    }
}