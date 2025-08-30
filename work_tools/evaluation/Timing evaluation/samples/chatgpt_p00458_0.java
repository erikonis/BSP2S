import java.util.*;

public class Main {
    static int m, n;
    static int[][] grid;
    static boolean[][] visited;
    static int maxCount;
    static int[] dx = {0, 0, 1, -1}; // E, W, S, N
    static int[] dy = {1, -1, 0, 0};

    static int dfs(int x, int y, int cnt) {
        visited[x][y] = true;
        int ret = cnt;

        for (int dir = 0; dir < 4; dir++) {
            int nx = x + dx[dir];
            int ny = y + dy[dir];
            if (nx >= 0 && nx < n && ny >= 0 && ny < m && grid[nx][ny] == 1 && !visited[nx][ny]) {
                ret = Math.max(ret, dfs(nx, ny, cnt + 1));
            }
        }
        visited[x][y] = false;
        return ret;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            m = sc.nextInt();
            n = sc.nextInt();
            if (m == 0 && n == 0) break;
            grid = new int[n][m];
            for (int i = 0; i < n; i++)
                for (int j = 0; j < m; j++)
                    grid[i][j] = sc.nextInt();

            maxCount = 0;
            visited = new boolean[n][m];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    if (grid[i][j] == 1) {
                        maxCount = Math.max(maxCount, dfs(i, j, 1));
                    }
                }
            }
            System.out.println(maxCount);
        }
    }
}