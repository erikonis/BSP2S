import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int H = sc.nextInt();
        int W = sc.nextInt();
        char[][] s = new char[H][W];
        for (int i = 0; i < H; i++) {
            s[i] = sc.next().toCharArray();
        }

        int[][] dp = new int[H][W];
        for (int i = 0; i < H; i++) {
            Arrays.fill(dp[i], Integer.MAX_VALUE);
        }

        dp[0][0] = (s[0][0] == '#') ? 1 : 0;

        Queue<int[]> q = new LinkedList<>();
        q.offer(new int[]{0, 0});

        int[] dr = {0, 1};
        int[] dc = {1, 0};

        while (!q.isEmpty()) {
            int[] curr = q.poll();
            int r = curr[0];
            int c = curr[1];

            for (int i = 0; i < 2; i++) {
                int nr = r + dr[i];
                int nc = c + dc[i];

                if (nr < H && nc < W) {
                    int cost = (s[nr][nc] == '#') ? 1 : 0;
                    if (dp[nr][nc] > dp[r][c] + cost) {
                        dp[nr][nc] = dp[r][c] + cost;
                        q.offer(new int[]{nr, nc});
                    }
                }
            }
        }

        System.out.println(dp[H - 1][W - 1]);
    }
}