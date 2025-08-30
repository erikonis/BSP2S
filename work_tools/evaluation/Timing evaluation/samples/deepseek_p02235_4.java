import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int q = scanner.nextInt();
        scanner.nextLine(); // consume newline
        
        for (int i = 0; i < q; i++) {
            String X = scanner.nextLine();
            String Y = scanner.nextLine();
            System.out.println(lcsLength(X, Y));
        }
    }
    
    private static int lcsLength(String X, String Y) {
        int m = X.length();
        int n = Y.length();
        int[][] dp = new int[2][n + 1];
        
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (X.charAt(i - 1) == Y.charAt(j - 1)) {
                    dp[i % 2][j] = dp[(i - 1) % 2][j - 1] + 1;
                } else {
                    dp[i % 2][j] = Math.max(dp[(i - 1) % 2][j], dp[i % 2][j - 1]);
                }
            }
        }
        return dp[m % 2][n];
    }
}