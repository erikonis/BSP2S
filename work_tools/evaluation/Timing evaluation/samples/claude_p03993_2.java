import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] a = new int[n + 1]; // 1-indexed
        
        for (int i = 1; i <= n; i++) {
            a[i] = sc.nextInt();
        }
        
        int friendlyPairs = 0;
        
        // Check all pairs (i,j) where i < j
        for (int i = 1; i <= n; i++) {
            for (int j = i + 1; j <= n; j++) {
                // Check if i likes j and j likes i
                if (a[i] == j && a[j] == i) {
                    friendlyPairs++;
                }
            }
        }
        
        System.out.println(friendlyPairs);
        sc.close();
    }
}