import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int H = sc.nextInt();
        int W = sc.nextInt();
        int Ch = sc.nextInt() - 1;
        int Cw = sc.nextInt() - 1;
        int Dh = sc.nextInt() - 1;
        int Dw = sc.nextInt() - 1;
        
        char[][] grid = new char[H][W];
        for (int i = 0; i < H; i++) {
            grid[i] = sc.next().toCharArray();
        }
        
        int[][] dist = new int[H][W];
        for (int i = 0; i < H; i++) {
            Arrays.fill(dist[i], -1);
        }
        
        Deque<int[]> deque = new ArrayDeque<>();
        deque.offer(new int[]{Ch, Cw, 0});
        dist[Ch][Cw] = 0;
        
        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};
        
        while (!deque.isEmpty()) {
            int[] curr = deque.poll();
            int x = curr[0];
            int y = curr[1];
            int magic = curr[2];
            
            if (x == Dh && y == Dw) {
                System.out.println(magic);
                return;
            }
            
            // Normal moves (cost 0)
            for (int i = 0; i < 4; i++) {
                int nx = x + dx[i];
                int ny = y + dy[i];
                
                if (nx >= 0 && nx < H && ny >= 0 && ny < W && 
                    grid[nx][ny] == '.' && dist[nx][ny] == -1) {
                    dist[nx][ny] = magic;
                    deque.offerFirst(new int[]{nx, ny, magic});
                }
            }
            
            // Magic moves (cost 1)
            for (int i = -2; i <= 2; i++) {
                for (int j = -2; j <= 2; j++) {
                    int nx = x + i;
                    int ny = y + j;
                    
                    if (nx >= 0 && nx < H && ny >= 0 && ny < W && 
                        grid[nx][ny] == '.' && dist[nx][ny] == -1) {
                        dist[nx][ny] = magic + 1;
                        deque.offerLast(new int[]{nx, ny, magic + 1});
                    }
                }
            }
        }
        
        System.out.println(-1);
    }
}