import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        while (true) {
            int n = sc.nextInt();
            int m = sc.nextInt();
            
            if (n == 0 && m == 0) break;
            
            int[] prices = new int[n];
            for (int i = 0; i < n; i++) {
                prices[i] = sc.nextInt();
            }
            
            Arrays.sort(prices);
            
            int maxSum = -1;
            int left = 0;
            int right = n - 1;
            
            while (left < right) {
                int sum = prices[left] + prices[right];
                if (sum <= m) {
                    maxSum = Math.max(maxSum, sum);
                    left++;
                } else {
                    right--;
                }
            }
            
            if (maxSum == -1) {
                System.out.println("NONE");
            } else {
                System.out.println(maxSum);
            }
        }
        
        sc.close();
    }
}