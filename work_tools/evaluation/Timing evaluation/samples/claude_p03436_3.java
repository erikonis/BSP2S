import java.util.*;

public class Main {
    static int H, W;
    static char[][] grid;
    static int[] dx = {-1, 1, 0, 0};
    static int[] dy = {0, 0, -1, 1};
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        H = sc.nextInt();
        W = sc.nextInt();
        grid = new char[H][W];
        
        for (int i = 0; i < H; i++) {
            String line = sc.next();
            for (int j = 0; j < W; j++) {
                grid[i][j] = line.charAt(j);
            }
        }
        
        // Count total white squares (excluding start and end)
        int totalWhite = 0;
        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                if (grid[i][j] == '.' && !(i == 0 && j == 0) && !(i == H-1 && j == W-1)) {
                    totalWhite++;
                }
            }
        }
        
        // Binary search on the answer
        int left = 0, right = totalWhite;
        int answer = -1;
        
        while (left <= right) {
            int mid = (left + right) / 2;
            if (canAchieveScore(mid, totalWhite)) {
                answer = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        System.out.println(answer);
    }
    
    static boolean canAchieveScore(int score, int totalWhite) {
        // We want to change 'score' white squares to black
        // This means we keep (totalWhite - score) white squares
        // Check if we can still reach destination with remaining white squares
        
        List<int[]> whiteSquares = new ArrayList<>();
        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                if (grid[i][j] == '.' && !(i == 0 && j == 0) && !(i == H-1 && j == W-1)) {
                    whiteSquares.add(new int[]{i, j});
                }
            }
        }
        
        int keepWhite = totalWhite - score;
        
        // Try all combinations of keeping 'keepWhite' squares
        return canReachWithSubset(whiteSquares, keepWhite, 0, new boolean[whiteSquares.size()]);
    }
    
    static boolean canReachWithSubset(List<int[]> whiteSquares, int keepWhite, int pos, boolean[] used) {
        if (keepWhite == 0) {
            return canReach(whiteSquares, used);
        }
        if (pos >= whiteSquares.size()) {
            return false;
        }
        
        // Try keeping current square
        used[pos] = true;
        if (canReachWithSubset(whiteSquares, keepWhite - 1, pos + 1, used)) {
            return true;
        }
        used[pos] = false;
        
        // Try not keeping current square
        return canReachWithSubset(whiteSquares, keepWhite, pos + 1, used);
    }
    
    static boolean canReach(List<int[]> whiteSquares, boolean[] used) {
        boolean[][] available = new boolean[H][W];
        
        // Mark available squares
        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                if (grid[i][j] == '#') {
                    available[i][j] = false;
                } else {
                    available[i][j] = true;
                }
            }
        }
        
        // Remove white squares that we decided to change to black
        for (int i = 0; i < whiteSquares.size(); i++) {
            if (!used[i]) {
                int[] pos = whiteSquares.get(i);
                available[pos[0]][pos[1]] = false;
            }
        }
        
        // BFS from start to end
        Queue<int[]> queue = new LinkedList<>();
        boolean[][] visited = new boolean[H][W];
        
        queue.offer(new int[]{0, 0});
        visited[0][0] = true;
        
        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            int x = curr[0], y = curr[1];
            
            if (x == H-1 && y == W-1) {
                return true;
            }
            
            for (int d = 0; d < 4; d++) {
                int nx = x + dx[d];
                int ny = y + dy[d];
                
                if (nx >= 0 && nx < H && ny >= 0 && ny < W && 
                    !visited[nx][ny] && available[nx][ny]) {
                    visited[nx][ny] = true;
                    queue.offer(new int[]{nx, ny});
                }
            }
        }
        
        return false;
    }
}