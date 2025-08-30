import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] d = new int[n];
        
        for (int i = 0; i < n; i++) {
            d[i] = sc.nextInt();
        }
        
        Arrays.sort(d);
        
        // For equal split, we need exactly n/2 problems with difficulty >= K
        // This means K should be > d[n/2-1] and <= d[n/2]
        int left = d[n/2 - 1];
        int right = d[n/2];
        
        // Count integers in range (left, right]
        int result = right - left;
        
        System.out.println(result);
    }
}