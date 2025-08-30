import java.util.Scanner;

public class LargestRectangle {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int m = scanner.nextInt();
        scanner.nextLine(); // consume newline
        
        for (int mapNum = 0; mapNum < m; mapNum++) {
            // Read the 5x5 map
            int[][] grid = new int[5][5];
            for (int i = 0; i < 5; i++) {
                String line = scanner.nextLine().replaceAll("\\s+", "");
                for (int j = 0; j < 5; j++) {
                    grid[i][j] = line.charAt(j) == '1' ? 1 : 0;
                }
            }
            
            if (mapNum < m - 1 && scanner.hasNextLine()) {
                scanner.nextLine(); // skip empty line between maps
            }
            
            int maxArea = 0;
            
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    if (grid[i][j] == 1) {
                        maxArea = Math.max(maxArea, findMaxArea(grid, i, j));
                    }
                }
            }
            
            System.out.println(maxArea);
        }
        scanner.close();
    }
    
    private static int findMaxArea(int[][] grid, int row, int col) {
        int maxArea = 0;
        int maxWidth = 5 - col;
        
        for (int i = row; i < 5; i++) {
            if (grid[i][col] == 0) break;
            
            int width = 0;
            while (width < maxWidth && grid[i][col + width] == 1) {
                width++;
            }
            maxWidth = Math.min(maxWidth, width);
            
            int currentArea = maxWidth * (i - row + 1);
            maxArea = Math.max(maxArea, currentArea);
        }
        
        return maxArea;
    }
}