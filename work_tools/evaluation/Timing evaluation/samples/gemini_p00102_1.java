import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            int n = sc.nextInt();
            if (n == 0) {
                break;
            }

            int[][] table = new int[n + 1][n + 1];

            // Read input and calculate row sums
            for (int i = 0; i < n; i++) {
                int rowSum = 0;
                for (int j = 0; j < n; j++) {
                    table[i][j] = sc.nextInt();
                    rowSum += table[i][j];
                }
                table[i][n] = rowSum; // Store row sum in the last column
            }

            // Calculate column sums and total sum
            for (int j = 0; j <= n; j++) {
                int colSum = 0;
                for (int i = 0; i < n; i++) {
                    colSum += table[i][j];
                }
                table[n][j] = colSum; // Store column sum in the last row
            }

            // Print the table
            for (int i = 0; i <= n; i++) {
                for (int j = 0; j <= n; j++) {
                    System.out.printf("%5d", table[i][j]);
                }
                System.out.println();
            }
        }
        sc.close();
    }
}