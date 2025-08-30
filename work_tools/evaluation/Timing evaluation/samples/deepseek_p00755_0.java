import java.util.*;
import java.io.*;

public class Main {
    static int h, w, c;
    static int[][] grid;
    static int[] dx = {0, 0, -1, 1};
    static int[] dy = {-1, 1, 0, 0};
    static int maxCount;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            h = Integer.parseInt(st.nextToken());
            w = Integer.parseInt(st.nextToken());
            c = Integer.parseInt(st.nextToken());
            if (h == 0 && w == 0 && c == 0) break;

            grid = new int[h][w];
            for (int i = 0; i < h; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < w; j++) {
                    grid[i][j] = Integer.parseInt(st.nextToken());
                }
            }
            maxCount = 0;
            dfs(0, grid);
            System.out.println(maxCount);
        }
    }

    static void dfs(int depth, int[][] currentGrid) {
        if (depth == 5) {
            int[][] tempGrid = new int[h][w];
            for (int i = 0; i < h; i++) {
                System.arraycopy(currentGrid[i], 0, tempGrid[i], 0, w);
            }
            floodFill(tempGrid, 0, 0, tempGrid[0][0], c);
            int count = countConnected(tempGrid, 0, 0, c);
            if (count > maxCount) {
                maxCount = count;
            }
            return;
        }

        for (int color = 1; color <= 6; color++) {
            if (color == currentGrid[0][0]) continue;
            int[][] nextGrid = new int[h][w];
            for (int i = 0; i < h; i++) {
                System.arraycopy(currentGrid[i], 0, nextGrid[i], 0, w);
            }
            floodFill(nextGrid, 0, 0, nextGrid[0][0], color);
            dfs(depth + 1, nextGrid);
        }
    }

    static void floodFill(int[][] grid, int i, int j, int oldColor, int newColor) {
        if (i < 0 || i >= h || j < 0 || j >= w || grid[i][j] != oldColor) {
            return;
        }
        grid[i][j] = newColor;
        for (int d = 0; d < 4; d++) {
            floodFill(grid, i + dx[d], j + dy[d], oldColor, newColor);
        }
    }

    static int countConnected(int[][] grid, int i, int j, int targetColor) {
        boolean[][] visited = new boolean[h][w];
        return dfsCount(grid, visited, i, j, targetColor);
    }

    static int dfsCount(int[][] grid, boolean[][] visited, int i, int j, int targetColor) {
        if (i < 0 || i >= h || j < 0 || j >= w || visited[i][j] || grid[i][j] != targetColor) {
            return 0;
        }
        visited[i][j] = true;
        int count = 1;
        for (int d = 0; d < 4; d++) {
            count += dfsCount(grid, visited, i + dx[d], j + dy[d], targetColor);
        }
        return count;
    }
}