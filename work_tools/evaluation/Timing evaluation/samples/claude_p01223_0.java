import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        
        for (int i = 0; i < t; i++) {
            int n = sc.nextInt();
            int[] heights = new int[n];
            
            for (int j = 0; j < n; j++) {
                heights[j] = sc.nextInt();
            }
            
            int maxUp = 0;
            int maxDown = 0;
            
            for (int j = 1; j < n; j++) {
                int diff = heights[j] - heights[j-1];
                if (diff > 0) {
                    maxUp = Math.max(maxUp, diff);
                } else if (diff < 0) {
                    maxDown = Math.max(maxDown, -diff);
                }
            }
            
            System.out.println(maxUp + " " + maxDown);
        }
        
        sc.close();
    }
}