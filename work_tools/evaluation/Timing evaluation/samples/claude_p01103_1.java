import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        while (true) {
            int d = sc.nextInt();
            int w = sc.nextInt();
            
            if (d == 0 && w == 0) break;
            
            int[][] grid = new int[d][w];
            for (int i = 0; i < d; i++) {
                for (int j = 0; j < w; j++) {
                    grid[i][j] = sc.nextInt();
                }
            }
            
            int maxCapacity = 0;
            
            // Try all possible rectangles of size at least 3x3
            for (int r1 = 0; r1 <= d - 3; r1++) {
                for (int c1 = 0; c1 <= w - 3; c1++) {
                    for (int r2 = r1 + 2; r2 < d; r2++) {
                        for (int c2 = c1 + 2; c2 < w; c2++) {
                            int capacity = calculateCapacity(grid, r1, c1, r2, c2);
                            maxCapacity = Math.max(maxCapacity, capacity);
                        }
                    }
                }
            }
            
            System.out.println(maxCapacity);
        }
    }
    
    static int calculateCapacity(int[][] grid, int r1, int c1, int r2, int c2) {
        // Find minimum elevation of outer cells
        int minOuter = Integer.MAX_VALUE;
        
        // Top and bottom rows
        for (int j = c1; j <= c2; j++) {
            minOuter = Math.min(minOuter, grid[r1][j]);
            minOuter = Math.min(minOuter, grid[r2][j]);
        }
        
        // Left and right columns (excluding corners already counted)
        for (int i = r1 + 1; i < r2; i++) {
            minOuter = Math.min(minOuter, grid[i][c1]);
            minOuter = Math.min(minOuter, grid[i][c2]);
        }
        
        // Check if all outer cells are higher than all inner cells
        int maxInner = Integer.MIN_VALUE;
        for (int i = r1 + 1; i < r2; i++) {
            for (int j = c1 + 1; j < c2; j++) {
                maxInner = Math.max(maxInner, grid[i][j]);
            }
        }
        
        if (minOuter <= maxInner) {
            return 0; // Not a valid pond
        }
        
        // Calculate capacity
        int capacity = 0;
        for (int i = r1 + 1; i < r2; i++) {
            for (int j = c1 + 1; j < c2; j++) {
                capacity += minOuter - grid[i][j];
            }
        }
        
        return capacity;
    }
}