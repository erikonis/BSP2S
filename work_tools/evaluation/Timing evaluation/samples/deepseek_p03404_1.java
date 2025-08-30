import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int A = sc.nextInt();
        int B = sc.nextInt();
        
        int h = 100;
        int w = 100;
        char[][] grid = new char[h][w];
        
        // Initialize all white
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                grid[i][j] = '.';
            }
        }
        
        // Make top half black (for B components)
        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < w; j++) {
                grid[i][j] = '#';
            }
        }
        
        // Create B-1 white squares in black area
        int bCount = 1;
        for (int i = 1; i < 50 && bCount < B; i += 2) {
            for (int j = 1; j < w && bCount < B; j += 2) {
                grid[i][j] = '.';
                bCount++;
            }
        }
        
        // Create A-1 black squares in white area
        int aCount = 1;
        for (int i = 51; i < h && aCount < A; i += 2) {
            for (int j = 1; j < w && aCount < A; j += 2) {
                grid[i][j] = '#';
                aCount++;
            }
        }
        
        // Output
        System.out.println(h + " " + w);
        for (int i = 0; i < h; i++) {
            System.out.println(new String(grid[i]));
        }
    }
}