import java.util.*;
import java.io.*;

public class Main {
    static int[] dx = {0, 0, 1, -1};
    static int[] dy = {1, -1, 0, 0};
    
    static class State {
        int x, y, mask, dist;
        State(int x, int y, int mask, int dist) {
            this.x = x; this.y = y; this.mask = mask; this.dist = dist;
        }
    }
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line;
        
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(" ");
            int W = Integer.parseInt(parts[0]);
            int H = Integer.parseInt(parts[1]);
            
            if (W == 0 && H == 0) break;
            
            char[][] grid = new char[H][W];
            int sx = -1, sy = -1, gx = -1, gy = -1;
            
            for (int i = 0; i < H; i++) {
                String row = br.readLine();
                for (int j = 0; j < W; j++) {
                    grid[i][j] = row.charAt(j);
                    if (grid[i][j] == 'S') {
                        sx = i; sy = j;
                    } else if (grid[i][j] == 'G') {
                        gx = i; gy = j;
                    }
                }
            }
            
            int bestMoves = Integer.MAX_VALUE;
            int bestAttr = -1;
            
            // Try starting with each attribute (1-5)
            for (int startAttr = 1; startAttr <= 5; startAttr++) {
                int moves = solve(grid, H, W, sx, sy, gx, gy, startAttr);
                if (moves < bestMoves) {
                    bestMoves = moves;
                    bestAttr = startAttr;
                }
            }
            
            if (bestMoves == Integer.MAX_VALUE) {
                System.out.println("NA");
            } else {
                System.out.println(bestAttr + " " + bestMoves);
            }
        }
    }
    
    static int solve(char[][] grid, int H, int W, int sx, int sy, int gx, int gy, int startAttr) {
        // State: position + bitmask of collected attributes
        boolean[][][] visited = new boolean[H][W][32]; // 32 = 2^5
        Queue<State> queue = new LinkedList<>();
        
        int startMask = 1 << (startAttr - 1);
        queue.offer(new State(sx, sy, startMask, 0));
        visited[sx][sy][startMask] = true;
        
        while (!queue.isEmpty()) {
            State curr = queue.poll();
            
            // Check if we reached goal with all attributes
            if (curr.x == gx && curr.y == gy && curr.mask == 31) { // 31 = 11111 in binary
                return curr.dist;
            }
            
            for (int d = 0; d < 4; d++) {
                int nx = curr.x + dx[d];
                int ny = curr.y + dy[d];
                
                if (nx < 0 || nx >= H || ny < 0 || ny >= W) continue;
                
                int newMask = curr.mask;
                
                // Check if we can catch a creature at this position
                if (grid[nx][ny] >= '1' && grid[nx][ny] <= '5') {
                    int creatureAttr = grid[nx][ny] - '0';
                    // Check if we have the right attribute to catch this creature
                    int catcherAttr = creatureAttr == 1 ? 5 : creatureAttr - 1;
                    if ((curr.mask & (1 << (catcherAttr - 1))) != 0) {
                        newMask |= (1 << (creatureAttr - 1));
                    }
                }
                
                if (!visited[nx][ny][newMask]) {
                    visited[nx][ny][newMask] = true;
                    queue.offer(new State(nx, ny, newMask, curr.dist + 1));
                }
            }
        }
        
        return Integer.MAX_VALUE;
    }
}