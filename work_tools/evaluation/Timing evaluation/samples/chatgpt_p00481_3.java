import java.io.*;
import java.util.*;

public class Main {
    static int H, W, N;
    static char[][] grid;
    static int[] dx = {-1, 0, 1, 0};
    static int[] dy = {0, -1, 0, 1};

    static class Pos {
        int x, y;
        Pos(int x, int y) { this.x = x; this.y = y; }
    }

    // BFS from (sx, sy) to (tx, ty)
    static int bfs(Pos start, Pos goal, int power) {
        Queue<Pos> q = new ArrayDeque<>();
        boolean[][] vis = new boolean[H][W];
        int[][] dist = new int[H][W];
        q.add(start);
        vis[start.x][start.y] = true;
        dist[start.x][start.y] = 0;
        while (!q.isEmpty()) {
            Pos p = q.poll();
            if (p.x == goal.x && p.y == goal.y) return dist[p.x][p.y];
            for (int d = 0; d < 4; d++) {
                int nx = p.x + dx[d], ny = p.y + dy[d];
                if (nx < 0 || nx >= H || ny < 0 || ny >= W) continue;
                if (vis[nx][ny]) continue;
                char c = grid[nx][ny];
                if (c == 'X') continue;
                if ('1' <= c && c <= '9' && (c - '0') > power) continue;
                vis[nx][ny] = true;
                dist[nx][ny] = dist[p.x][p.y] + 1;
                q.add(new Pos(nx, ny));
            }
        }
        return -1; // unreachable (should not happen)
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] params = br.readLine().split(" ");
        H = Integer.parseInt(params[0]);
        W = Integer.parseInt(params[1]);
        N = Integer.parseInt(params[2]);
        grid = new char[H][W];
        Pos start = null;
        Pos[] cheese = new Pos[N+1]; // cheese[1..N]
        for (int i = 0; i < H; i++) {
            String line = br.readLine();
            for (int j = 0; j < W; j++) {
                char c = line.charAt(j);
                grid[i][j] = c;
                if (c == 'S') start = new Pos(i, j);
                if ('1' <= c && c <= '9') {
                    int idx = c - '0';
                    cheese[idx] = new Pos(i, j);
                }
            }
        }
        int ans = 0;
        Pos cur = start;
        for (int i = 1; i <= N; i++) {
            ans += bfs(cur, cheese[i], i);
            cur = cheese[i];
        }
        System.out.println(ans);
    }
}