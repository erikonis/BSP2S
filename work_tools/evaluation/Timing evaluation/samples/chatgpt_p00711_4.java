import java.util.*;

public class Main {
    static int W, H;
    static char[][] grid;
    static boolean[][] visited;
    static int[] dx = {1, -1, 0, 0};
    static int[] dy = {0, 0, 1, -1};

    static int bfs(int sx, int sy) {
        Queue<int[]> q = new LinkedList<>();
        q.add(new int[]{sx, sy});
        visited[sy][sx] = true;
        int count = 1;

        while (!q.isEmpty()) {
            int[] cur = q.poll();
            int x = cur[0], y = cur[1];
            for (int d = 0; d < 4; d++) {
                int nx = x + dx[d], ny = y + dy[d];
                if (nx >= 0 && nx < W && ny >= 0 && ny < H && !visited[ny][nx] && (grid[ny][nx] == '.' || grid[ny][nx] == '@')) {
                    visited[ny][nx] = true;
                    q.add(new int[]{nx, ny});
                    count++;
                }
            }
        }
        return count;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            W = sc.nextInt();
            H = sc.nextInt();
            if (W == 0 && H == 0) break;
            grid = new char[H][W];
            visited = new boolean[H][W];
            int sx = 0, sy = 0;
            for (int i = 0; i < H; i++) {
                String line = sc.next();
                for (int j = 0; j < W; j++) {
                    grid[i][j] = line.charAt(j);
                    if (grid[i][j] == '@') {
                        sx = j;
                        sy = i;
                    }
                }
            }
            System.out.println(bfs(sx, sy));
        }
    }
}