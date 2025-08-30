import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        
        int[] x = new int[n];
        int[] y = new int[n];
        long[] p = new long[n];
        
        for (int i = 0; i < n; i++) {
            x[i] = sc.nextInt();
            y[i] = sc.nextInt();
            p[i] = sc.nextLong();
        }
        
        // Get unique x and y coordinates
        Set<Integer> xSet = new HashSet<>();
        Set<Integer> ySet = new HashSet<>();
        for (int i = 0; i < n; i++) {
            xSet.add(x[i]);
            ySet.add(y[i]);
        }
        
        List<Integer> xCoords = new ArrayList<>(xSet);
        List<Integer> yCoords = new ArrayList<>(ySet);
        
        // Calculate initial distances to existing railroads (x=0, y=0)
        long[] baseCost = new long[n];
        for (int i = 0; i < n; i++) {
            baseCost[i] = Math.min(Math.abs(x[i]), Math.abs(y[i])) * p[i];
        }
        
        // For each possible railroad, calculate which areas it covers (distance becomes 0)
        List<Integer> railroads = new ArrayList<>();
        List<Integer> masks = new ArrayList<>();
        
        // X-coordinate railroads
        for (int xc : xCoords) {
            int mask = 0;
            for (int i = 0; i < n; i++) {
                if (x[i] == xc) {
                    mask |= (1 << i);
                }
            }
            railroads.add(0); // 0 for x-railroad
            masks.add(mask);
        }
        
        // Y-coordinate railroads  
        for (int yc : yCoords) {
            int mask = 0;
            for (int i = 0; i < n; i++) {
                if (y[i] == yc) {
                    mask |= (1 << i);
                }
            }
            railroads.add(1); // 1 for y-railroad
            masks.add(mask);
        }
        
        int m = railroads.size();
        
        // DP: dp[k][mask] = minimum cost using k railroads with areas in mask covered
        long[][] dp = new long[n + 1][1 << n];
        for (int k = 0; k <= n; k++) {
            Arrays.fill(dp[k], Long.MAX_VALUE);
        }
        
        // Base case: no additional railroads
        long baseTotalCost = 0;
        for (int i = 0; i < n; i++) {
            baseTotalCost += baseCost[i];
        }
        dp[0][0] = baseTotalCost;
        
        // Fill DP table
        for (int k = 0; k < n; k++) {
            for (int mask = 0; mask < (1 << n); mask++) {
                if (dp[k][mask] == Long.MAX_VALUE) continue;
                
                for (int j = 0; j < m; j++) {
                    int newMask = mask | masks.get(j);
                    long newCost = dp[k][mask];
                    
                    // Subtract cost for newly covered areas
                    for (int i = 0; i < n; i++) {
                        if ((mask & (1 << i)) == 0 && (newMask & (1 << i)) != 0) {
                            newCost -= baseCost[i];
                        }
                    }
                    
                    dp[k + 1][newMask] = Math.min(dp[k + 1][newMask], newCost);
                }
            }
        }
        
        // Output results
        for (int k = 0; k <= n; k++) {
            long ans = Long.MAX_VALUE;
            for (int mask = 0; mask < (1 << n); mask++) {
                ans = Math.min(ans, dp[k][mask]);
            }
            System.out.println(ans);
        }
    }
}