import java.util.Scanner;

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
                    grid[i] = sc.nextInt();
                }
            }
            
            int maxCapacity = 0;
            
            // Try all possible rectangles with size at least 3x3
            for (int r1 = 0; r1 < d; r1++) {
                for (int c1 = 0; c1 < w; c1++) {
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
        sc.close();
    }
    
    private static int calculateCapacity(int[][] grid, int r1, int c1, int r2, int c2) {
        // Find minimum elevation of outer cells
        int minOuter = Integer.MAX_VALUE;
        
        // Top and bottom rows
        for (int c = c1; c <= c2; c++) {
            minOuter = Math.min(minOuter, grid[r1][c]);
            minOuter = Math.min(minOuter, grid[r2][c]);
        }
        
        // Left and right columns (excluding corners already counted)
        for (int r = r1 + 1; r < r2; r++) {
            minOuter = Math.min(minOuter, grid[r][c1]);
            minOuter = Math.min(minOuter, grid[r][c2]);
        }
        
        // Find maximum elevation of inner cells
        int maxInner = Integer.MIN_VALUE;
        for (int r = r1 + 1; r < r2; r++) {
            for (int c = c1 + 1; c < c2; c++) {
                maxInner = Math.max(maxInner, grid[r][c]);
            }
        }
        
        // Check if valid pond (all outer cells higher than all inner cells)
        if (minOuter <= maxInner) {
            return 0;
        }
        
        // Calculate capacity
        int capacity = 0;
        for (int r = r1 + 1; r < r2; r++) {
            for (int c = c1 + 1; c < c2; c++) {
                capacity += minOuter - grid[r][c];
            }
        }
        
        return capacity;
    }
}