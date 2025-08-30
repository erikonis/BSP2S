import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        int C = sc.nextInt();
        int[][] D = new int[C][C];
        for (int i = 0; i < C; i++)
            for (int j = 0; j < C; j++)
                D[i][j] = sc.nextInt();

        int[][] color = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                color[i][j] = sc.nextInt() - 1; // 0-indexed

        // Count frequency of each color in each group (grouped by (i+j)%3)
        int[][] freq = new int[3][C];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                freq[(i + j) % 3][color[i][j]]++;

        // For each group, for each target color, compute cost to repaint all to that color
        int[][] cost = new int[3][C];
        for (int g = 0; g < 3; g++) {
            for (int c = 0; c < C; c++) {
                int sum = 0;
                for (int orig = 0; orig < C; orig++) {
                    sum += freq[g][orig] * D[orig][c];
                }
                cost[g][c] = sum;
            }
        }

        // Try all combinations of 3 different colors for the 3 groups
        int minTotal = Integer.MAX_VALUE;
        for (int c0 = 0; c0 < C; c0++) {
            for (int c1 = 0; c1 < C; c1++) {
                if (c1 == c0) continue;
                for (int c2 = 0; c2 < C; c2++) {
                    if (c2 == c0 || c2 == c1) continue;
                    int total = cost[0][c0] + cost[1][c1] + cost[2][c2];
                    if (total < minTotal) minTotal = total;
                }
            }
        }

        System.out.println(minTotal);
    }
}