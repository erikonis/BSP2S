import java.util.ArrayDeque;
import java.util.Deque;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] hwn = br.readLine().split(" ");
        int h = Integer.parseInt(hwn[0]);
        int w = Integer.parseInt(hwn[1]);
        int n = Integer.parseInt(hwn[2]);
        char[][] grid = new char[h][w];
        int sx=-1, sy=-1;
        
        for (int i = 0; i < h; i++) {
            String raw = br.readLine();
            for (int j = 0; j < w; j++) {
                grid[i][j] = raw.charAt(j);
                if (grid[i][j] == 'S') {
                    sy = i;
                    sx = j;
                }
            }
        }
        
        int next = 1;
        int[][] dfyxs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        boolean[][] visited = new boolean[h][w];
        visited[sy][sx] = true;
        int[][] distance = new int[h][w];
        distance[sy][sx] = 0;
        Deque<Integer> xq = new ArrayDeque<Integer>();
        Deque<Integer> yq = new ArrayDeque<Integer>();
        int d = 0;
        int ans = 0;
        outer: while (true) {
            inner: for (int[] dfyx: dfyxs) {
                if (sy-dfyx[0] < 0 || sx-dfyx[1] < 0 || sy-dfyx[0] >= h || sx-dfyx[1] >= w) continue;
                if (visited[sy-dfyx[0]][sx-dfyx[1]] == true) continue;
                if (grid[sy-dfyx[0]][sx-dfyx[1]] == 'X') {
                    continue;
                }
                distance[sy-dfyx[0]][sx-dfyx[1]] = distance[sy][sx] + 1;
                //System.out.println((sy-dfyx[0]) + " " + (sx-dfyx[1]));
                if ((int)grid[sy-dfyx[0]][sx-dfyx[1]]-48 == next) {
                    //System.out.println("aaa");
                    //System.out.println(distance[sy-dfyx[0]][sx-dfyx[1]]);
                    yq.clear();
                    xq.clear();
                    ans += distance[sy-dfyx[0]][sx-dfyx[1]];
                    if (next == n) break outer;
                    next++;
                    distance = new int[h][w];
                    distance[sy-dfyx[0]][sx-dfyx[1]] = 0;
                    visited = new boolean[h][w];
                    sy = sy-dfyx[0];
                    sx = sx-dfyx[1];
                    continue outer;
                }
                visited[sy-dfyx[0]][sx-dfyx[1]] = true;
                xq.addLast(sx-dfyx[1]);
                yq.addLast(sy-dfyx[0]);
            }
            sy = yq.removeFirst();
            sx = xq.removeFirst();
        }
        //System.out.println(distance[3][1]);
        System.out.println(ans);
    }
}

