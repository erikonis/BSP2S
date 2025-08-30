import java.util.Scanner;

public class Main {
    // Minimum path length between (x1, y1) and (x2, y2)
    static int minDist(int x1, int y1, int x2, int y2) {
        int dx = Math.abs(x1 - x2);
        int dy = Math.abs(y1 - y2);
        int diag = Math.min(dx, dy);
        int straight = Math.abs(dx - dy);
        return diag + straight;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int W = sc.nextInt();
        int H = sc.nextInt();
        int N = sc.nextInt();
        int[] X = new int[N];
        int[] Y = new int[N];
        for (int i = 0; i < N; ++i) {
            X[i] = sc.nextInt();
            Y[i] = sc.nextInt();
        }
        int sum = 0;
        for (int i = 0; i < N-1; ++i) {
            sum += minDist(X[i], Y[i], X[i+1], Y[i+1]);
        }
        System.out.println(sum);
    }
}