import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int H = sc.nextInt();
        int W = sc.nextInt();
        
        int[][] grid = new int[H][W];
        int totalCoins = 0;
        
        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                grid[i][j] = sc.nextInt();
                totalCoins += grid[i][j];
            }
        }
        
        List<String> operations = new ArrayList<>();
        boolean[][] used = new boolean[H][W];
        
        // Directions: up, down, left, right
        int[] dy = {-1, 1, 0, 0};
        int[] dx = {0, 0, -1, 1};
        
        // Try to make as many cells even as possible
        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                if (used[i][j] || grid[i][j] == 0) continue;
                
                // If current cell has odd coins, try to move one coin
                if (grid[i][j] % 2 == 1) {
                    // Find an adjacent cell to move to
                    boolean moved = false;
                    for (int d = 0; d < 4; d++) {
                        int ni = i + dy[d];
                        int nj = j + dx[d];
                        
                        if (ni >= 0 && ni < H && nj >= 0 && nj < W) {
                            // Move coin to make both cells more even
                            operations.add((i+1) + " " + (j+1) + " " + (ni+1) + " " + (nj+1));
                            grid[i][j]--;
                            grid[ni][nj]++;
                            used[i][j] = true;
                            moved = true;
                            break;
                        }
                    }
                }
            }
        }
        
        System.out.println(operations.size());
        for (String op : operations) {
            System.out.println(op);
        }
    }
}