import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            int n = sc.nextInt();
            if (n == 0) {
                break;
            }
            int[][] grid = new int[n][n];
            for (int i = 0; i < n; i++) {
                String row = sc.next();
                for (int j = 0; j < n; j++) {
                    grid[i][j] = row.charAt(j) - '0';
                }
            }

            int maxConsecutive = 0;

            // Check horizontal
            for (int i = 0; i < n; i++) {
                int currentConsecutive = 0;
                for (int j = 0; j < n; j++) {
                    if (grid[i][j] == 1) {
                        currentConsecutive++;
                    } else {
                        maxConsecutive = Math.max(maxConsecutive, currentConsecutive);
                        currentConsecutive = 0;
                    }
                }
                maxConsecutive = Math.max(maxConsecutive, currentConsecutive);
            }

            // Check vertical
            for (int j = 0; j < n; j++) {
                int currentConsecutive = 0;
                for (int i = 0; i < n; i++) {
                    if (grid[i][j] == 1) {
                        currentConsecutive++;
                    } else {
                        maxConsecutive = Math.max(maxConsecutive, currentConsecutive);
                        currentConsecutive = 0;
                    }
                }
                maxConsecutive = Math.max(maxConsecutive, currentConsecutive);
            }

            // Check diagonal (top-left to bottom-right)
            for (int i = 0; i < n; i++) {
                int currentConsecutive = 0;
                for (int r = i, c = 0; r < n && c < n; r++, c++) {
                    if (grid[r][c] == 1) {
                        currentConsecutive++;
                    } else {
                        maxConsecutive = Math.max(maxConsecutive, currentConsecutive);
                        currentConsecutive = 0;
                    }
                }
                maxConsecutive = Math.max(maxConsecutive, currentConsecutive);
            }
            for (int j = 1; j < n; j++) {
                int currentConsecutive = 0;
                for (int r = 0, c = j; r < n && c < n; r++, c++) {
                    if (grid[r][c] == 1) {
                        currentConsecutive++;
                    } else {
                        maxConsecutive = Math.max(maxConsecutive, currentConsecutive);
                        currentConsecutive = 0;
                    }
                }
                maxConsecutive = Math.max(maxConsecutive, currentConsecutive);
            }

            // Check anti-diagonal (top-right to bottom-left)
            for (int i = 0; i < n; i++) {
                int currentConsecutive = 0;
                for (int r = i, c = n - 1; r < n && c >= 0; r++, c--) {
                    if (grid[r][c] == 1) {
                        currentConsecutive++;
                    } else {
                        maxConsecutive = Math.max(maxConsecutive, currentConsecutive);
                        currentConsecutive = 0;
                    }
                }
                maxConsecutive = Math.max(maxConsecutive, currentConsecutive);
            }
            for (int j = n - 2; j >= 0; j--) {
                int currentConsecutive = 0;
                for (int r = 0, c = j; r < n && c >= 0; r++, c--) {
                    if (grid[r][c] == 1) {
                        currentConsecutive++;
                    } else {
                        maxConsecutive = Math.max(maxConsecutive, currentConsecutive);
                        currentConsecutive = 0;
                    }
                }
                maxConsecutive = Math.max(maxConsecutive, currentConsecutive);
            }

            System.out.println(maxConsecutive);
        }
        sc.close();
    }
}