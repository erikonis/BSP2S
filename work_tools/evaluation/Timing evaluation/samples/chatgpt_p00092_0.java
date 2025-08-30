import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while(true) {
            line = br.readLine();
            if(line == null) break;
            int n = Integer.parseInt(line.trim());
            if(n == 0) break;
            char[][] grid = new char[n][n];
            for(int i=0;i<n;i++) {
                grid[i] = br.readLine().toCharArray();
            }
            int[][] dp = new int[n+1][n+1];
            int max = 0;
            for(int i=1;i<=n;i++) {
                for(int j=1;j<=n;j++) {
                    if(grid[i-1][j-1] == '.') {
                        dp[i][j] = Math.min(dp[i-1][j], Math.min(dp[i][j-1], dp[i-1][j-1])) + 1;
                        if(dp[i][j] > max) max = dp[i][j];
                    }
                }
            }
            System.out.println(max);
        }
    }
}