import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int K = sc.nextInt();
        int T = sc.nextInt();
        
        int maxCount = 0;
        for (int i = 0; i < T; i++) {
            int a = sc.nextInt();
            maxCount = Math.max(maxCount, a);
        }
        
        // If we can interleave perfectly, answer is 0
        // Otherwise, it's the excess that can't be separated
        int result = Math.max(0, 2 * maxCount - K - 1);
        System.out.println(result);
        
        sc.close();
    }
}