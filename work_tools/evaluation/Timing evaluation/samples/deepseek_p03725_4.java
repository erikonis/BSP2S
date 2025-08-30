import java.util.*;
import java.io.*;

public class Main {
    static int H, W, K;
    static char[][] grid;
    static int[][] dist;
    static int[] dx = {0, 1, 0, -1};
    static int[] dy = {1, 0, -1, 0};

    static class State {
        int x, y, casts;
        State(int x, int y, int casts) {
            this.x = x;
            this.y = y;
            this.casts = casts;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        H = Integer.parseInt(st.nextToken());
        W = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        grid = new char[H][W];
        dist = new int[H][W];
        int startX = -1, startY = -1;

        for (int i = 0; i < H; i++) {
            String line = br.readLine();
            for (int j = 0; j < W; j++) {
                grid[i][j] = line.charAt(j);
                dist[i][j] = Integer.MAX_VALUE;
                if (grid[i][j] == 'S') {
                    startX = i;
                    startY = j;
                }
            }
        }

        PriorityQueue<State> queue = new PriorityQueue<>((a, b) -> a.casts - b.casts);
        queue.add(new State(startX, startY, 0));
        dist[startX][startY] = 0;
        int res = Integer.MAX_VALUE;

        while (!queue.isEmpty()) {
            State current = queue.poll();
            int x = current.x;
            int y = current.y;
            int currentCasts = current.casts;

            if (isExit(x, y)) {
                res = currentCasts + 1;
                break;
            }

            if (currentCasts > dist[x][y]) continue;

            for (int d = 0; d < 4; d++) {
                for (int k = 1; k <= K; k++) {
                    int nx = x + dx[d] * k;
                    int ny = y + dy[d] * k;
                    if (nx < 0 || nx >= H || ny < 0 || ny >= W || grid[nx][ny] == '#') break;
                    if (dist[nx][ny] <= currentCasts) break;
                    dist[nx][ny] = currentCasts;
                    queue.add(new State(nx, ny, currentCasts));
                }
            }

            for (int d = 0; d < 4; d++) {
                int nx = x + dx[d];
                int ny = y + dy[d];
                if (nx < 0 || nx >= H || ny < 0 || ny >= W) continue;
                if (grid[nx][ny] == '#') {
                    if (dist[nx][ny] > currentCasts + 1) {
                        dist[nx][ny] = currentCasts + 1;
                        queue.add(new State(nx, ny, currentCasts + 1));
                    }
                }
            }
        }

        System.out.println(res);
    }

    static boolean isExit(int x, int y) {
        return x == 0 || x == H - 1 || y == 0 || y == W - 1;
    }
}