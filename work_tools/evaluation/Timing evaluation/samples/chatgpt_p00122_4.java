import java.util.*;

public class Main {
    static final int SIZE = 10;
    static final int[] dx = {-2, -2, -1, -1, 0, 0, 1, 1, 2, 2};
    static final int[] dy = {-1, 1, -2, 2, -2, 2, -2, 2, -1, 1};

    static final int[] sx = {0, -1, 0, 1, 0};
    static final int[] sy = {0, 0, -1, 0, 1};

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            int px = sc.nextInt();
            int py = sc.nextInt();
            if (px == 0 && py == 0) break;
            int n = sc.nextInt();
            int[] xs = new int[n];
            int[] ys = new int[n];
            for (int i = 0; i < n; i++) {
                xs[i] = sc.nextInt();
                ys[i] = sc.nextInt();
            }
            boolean res = solve(px, py, n, xs, ys);
            System.out.println(res ? "OK" : "NA");
        }
    }

    static boolean solve(int px, int py, int n, int[] xs, int[] ys) {
        boolean[][][] dp = new boolean[n + 1][SIZE][SIZE];
        // Initial positions after first jump
        for (int d = 0; d < 10; d++) {
            int nx = px + dx[d];
            int ny = py + dy[d];
            if (inPark(nx, ny) && inSprinkler(nx, ny, xs[0], ys[0])) {
                dp[1][nx][ny] = true;
            }
        }
        // If cannot jump to any valid position at first, fail
        boolean found = false;
        for (int x = 0; x < SIZE; x++)
            for (int y = 0; y < SIZE; y++)
                if (dp[1][x][y]) found = true;
        if (!found) return false;
        // For subsequent sprinklers
        for (int i = 1; i < n; i++) {
            for (int x = 0; x < SIZE; x++) {
                for (int y = 0; y < SIZE; y++) {
                    if (!dp[i][x][y]) continue;
                    for (int d = 0; d < 10; d++) {
                        int nx = x + dx[d];
                        int ny = y + dy[d];
                        if (inPark(nx, ny) && inSprinkler(nx, ny, xs[i], ys[i])) {
                            dp[i+1][nx][ny] = true;
                        }
                    }
                }
            }
        }
        // See if any position at last time is reachable
        for (int x = 0; x < SIZE; x++)
            for (int y = 0; y < SIZE; y++)
                if (dp[n][x][y]) return true;
        return false;
    }

    static boolean inPark(int x, int y) {
        return x >= 0 && x < SIZE && y >= 0 && y < SIZE;
    }

    static boolean inSprinkler(int x, int y, int cx, int cy) {
        for (int i = 0; i < 5; i++) {
            int sxp = cx + sx[i];
            int syp = cy + sy[i];
            if (x == sxp && y == syp) return true;
        }
        return false;
    }
}