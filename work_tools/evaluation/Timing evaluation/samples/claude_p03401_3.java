import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] a = new int[n + 2];
        
        a[0] = 0; // starting point
        for (int i = 1; i <= n; i++) {
            a[i] = sc.nextInt();
        }
        a[n + 1] = 0; // ending point
        
        // Calculate total cost of visiting all spots
        int totalCost = 0;
        for (int i = 0; i <= n; i++) {
            totalCost += Math.abs(a[i + 1] - a[i]);
        }
        
        // For each spot to skip, calculate the new cost
        for (int skip = 1; skip <= n; skip++) {
            // Remove cost of going to and from the skipped spot
            int removedCost = Math.abs(a[skip] - a[skip - 1]) + Math.abs(a[skip + 1] - a[skip]);
            // Add cost of going directly from previous to next spot
            int addedCost = Math.abs(a[skip + 1] - a[skip - 1]);
            
            int newCost = totalCost - removedCost + addedCost;
            System.out.println(newCost);
        }
        
        sc.close();
    }
}