import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int H = sc.nextInt();
        int W = sc.nextInt();

        int[][] c = new int[10][10];
        for (int i = 0; i < 10; i++)
            for (int j = 0; j < 10; j++)
                c[i][j] = sc.nextInt();

        // Floyd-Warshall for all-pairs min-cost conversion
        for (int k = 0; k < 10; k++)
            for (int i = 0; i < 10; i++)
                for (int j = 0; j < 10; j++)
                    if (c[i][j] > c[i][k] + c[k][j])
                        c[i][j] = c[i][k] + c[k][j];

        int total = 0;
        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                int a = sc.nextInt();
                if (a != -1 && a != 1) {
                    total += c[a][1];
                }
            }
        }
        System.out.println(total);
    }
}