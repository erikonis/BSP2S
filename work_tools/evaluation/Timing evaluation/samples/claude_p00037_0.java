import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        // Read grid
        String[] grid = new String[9];
        for (int i = 0; i < 9; i++) {
            grid[i] = sc.nextLine();
        }
        
        // Starting position and direction
        int x = 0, y = 0; // Position in grid coordinates
        int dir = 0; // 0=right, 1=down, 2=left, 3=up
        
        StringBuilder path = new StringBuilder();
        
        do {
            // Try to turn right first (right-hand rule)
            int rightDir = (dir + 1) % 4;
            if (canMove(x, y, rightDir, grid)) {
                dir = rightDir;
            } else if (canMove(x, y, dir, grid)) {
                // Continue straight
            } else {
                // Turn left until we can move
                while (!canMove(x, y, dir, grid)) {
                    dir = (dir + 3) % 4; // Turn left
                }
            }
            
            // Move in current direction
            switch (dir) {
                case 0: // Right
                    x++;
                    path.append('R');
                    break;
                case 1: // Down
                    y++;
                    path.append('D');
                    break;
                case 2: // Left
                    x--;
                    path.append('L');
                    break;
                case 3: // Up
                    y--;
                    path.append('U');
                    break;
            }
        } while (!(x == 0 && y == 0));
        
        System.out.println(path.toString());
    }
    
    private static boolean canMove(int x, int y, int direction, String[] grid) {
        switch (direction) {
            case 0: // Right
                if (x >= 3) return false;
                return grid[y * 2][x] == '0';
            case 1: // Down
                if (y >= 3) return false;
                return grid[y * 2 + 1][x] == '0';
            case 2: // Left
                if (x <= 0) return false;
                return grid[y * 2][x - 1] == '0';
            case 3: // Up
                if (y <= 0) return false;
                return grid[(y - 1) * 2 + 1][x] == '0';
        }
        return false;
    }
}