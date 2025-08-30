import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        int n = sc.nextInt();
        int m = sc.nextInt();
        
        // Store treasure coordinates
        int[] treasureX = new int[n];
        int[] treasureY = new int[n];
        
        for (int i = 0; i < n; i++) {
            treasureX[i] = sc.nextInt();
            treasureY[i] = sc.nextInt();
        }
        
        // Process each region query
        for (int i = 0; i < m; i++) {
            int x1 = sc.nextInt();
            int y1 = sc.nextInt();
            int x2 = sc.nextInt();
            int y2 = sc.nextInt();
            
            int count = 0;
            
            // Check each treasure if it's within the region
            for (int j = 0; j < n; j++) {
                if (treasureX[j] >= x1 && treasureX[j] <= x2 && 
                    treasureY[j] >= y1 && treasureY[j] <= y2) {
                    count++;
                }
            }
            
            System.out.println(count);
        }
        
        sc.close();
    }
}