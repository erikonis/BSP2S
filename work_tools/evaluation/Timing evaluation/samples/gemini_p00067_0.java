import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Main {

    private static final int GRID_SIZE = 12;
    private static int[][] grid;
    private static boolean[][] visited;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (sc.hasNext()) {
            grid = new int[GRID_SIZE][GRID_SIZE];
            visited = new boolean[GRID_SIZE][GRID_SIZE];
            int islands = 0;

            // Read the grid
            for (int i = 0; i < GRID_SIZE; i++) {
                String line = sc.next();
                for (int j = 0; j < GRID_SIZE; j++) {
                    grid[i][j] = Character.getNumericValue(line.charAt(j));
                }
            }

            // Count islands using BFS
            for (int i = 0; i < GRID_SIZE; i++) {
                for (int j = 0; j < GRID_SIZE; j++) {
                    if (grid[i][j] == 1 && !visited[i][j]) {
                        bfs(i, j);
                        islands++;
                    }
                }
            }
            System.out.println(islands);

            // Consume the empty line between datasets
            if (sc.hasNextLine()) {
                sc.nextLine();
            }
        }
        sc.close();
    }

    private static void bfs(int startRow, int startCol) {
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{startRow, startCol});
        visited[startRow][startCol] = true;

        int[] dr = {-1, 1, 0, 0}; // Row changes for up, down, left, right
        int[] dc = {0, 0, -1, 1}; // Col changes for up, down, left, right

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int r = current[0];
            int c = current[1];

            for (int i = 0; i < 4; i++) {
                int newR = r + dr[i];
                int newC = c + dc[i];

                if (isValid(newR, newC) && grid[newR][newC] == 1 && !visited[newR][newC]) {
                    visited[newR][newC] = true;
                    queue.offer(new int[]{newR, newC});
                }
            }
        }
    }

    private static boolean isValid(int r, int c) {
        return r >= 0 && r < GRID_SIZE && c >= 0 && c < GRID_SIZE;
    }
}