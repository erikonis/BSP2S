import java.util.*;

public class Main {
    static int H, W;
    static char[][] S;
    static int[] dx = {1, 0, -1, 0};
    static int[] dy = {0, 1, 0, -1};

    static int bfs(int sx, int sy) {
        int[][] dist = new int[H][W];
        for (int i = 0; i < H; i++) Arrays.fill(dist[i], -1);
        Queue<int[]> q = new LinkedList<>();
        q.add(new int[]{sx, sy});
        dist[sx][sy] = 0;
        int max = 0;
        while (!q.isEmpty()) {
            int[] cur = q.poll();
            int x = cur[0], y = cur[1];
            for (int d = 0; d < 4; d++) {
                int nx = x + dx[d];
                int ny = y + dy[d];
                if (nx < 0 || nx >= H || ny < 0 || ny >= W) continue;
                if (S[nx][ny] == '#') continue;
                if (dist[nx][ny] >= 0) continue;
                dist[nx][ny] = dist[x][y] + 1;
                max = Math.max(max, dist[nx][ny]);
                q.add(new int[]{nx, ny});
            }
        }
        return max;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        H = sc.nextInt();
        W = sc.nextInt();
        S = new char[H][W];
        for (int i = 0; i < H; i++) {
            S[i] = sc.next().toCharArray();
        }
        int ans = 0;
        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                if (S[i][j] == '.') {
                    ans = Math.max(ans, bfs(i, j));
                }
            }
        }
        System.out.println(ans);
    }
}