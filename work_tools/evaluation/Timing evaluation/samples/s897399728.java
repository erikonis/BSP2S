import java.util.*;

public class Main {
    public static boolean[][] visited;
    public static char[][] map;
    public static int[] moveR = {0,0,1,-1};
    public static int[] moveC = {-1,1,0,0};
    public static int R;
    public static int C;
    
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        // int TC = Integer.parseInt(sc.next(), 10);
        
        while (true) {
            C = sc.nextInt();
            R = sc.nextInt();
            if (C == 0 && R == 0) break;
            
            int rPos = -1;
            int cPos = -1;
            visited = new boolean[R][C];
            
            map = new char[R][C];
            for (int r = 0; r < R; r++) {
                String line = sc.next();
                for (int c = 0; c < C; c++) {
                    map[r][c] = line.charAt(c);
                    if (map[r][c] == '@') {
                        rPos = r;
                        cPos = c;
                    }
                }
            }
            
            System.out.println(floodFill(rPos, cPos));
        }
    }
    
    public static int floodFill(int r, int c) {
        if (r < 0 || r >= R || c < 0 || c >= C) return 0;
        if (visited[r][c] || map[r][c] == '#') return 0;
        visited[r][c] = true;
        int ret = 1;
        for (int m = 0; m < 4; m++) {
            ret += floodFill(r + moveR[m], c + moveC[m]);
        }
        return ret;
    }
}


