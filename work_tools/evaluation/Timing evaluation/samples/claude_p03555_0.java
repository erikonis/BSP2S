import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] grid = new String[2];
        grid[0] = sc.nextLine();
        grid[1] = sc.nextLine();
        
        // Create rotated grid by reversing both rows and their order
        String rotated0 = new StringBuilder(grid[1]).reverse().toString();
        String rotated1 = new StringBuilder(grid[0]).reverse().toString();
        
        // Check if original equals rotated
        if (grid[0].equals(rotated1) && grid[1].equals(rotated0)) {
            System.out.println("YES");
        } else {
            System.out.println("NO");
        }
        
        sc.close();
    }
}