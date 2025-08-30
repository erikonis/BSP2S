import java.util.*;

public class Main {
    // Directions: Right, Down, Left, Up
    static final int[] dx = {1, 0, -1, 0};
    static final int[] dy = {0, 1, 0, -1};
    static final char[] dirChar = {'R', 'D', 'L', 'U'};

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] hwall = new String[5];
        String[] vwall = new String[4];
        for (int i = 0; i < 9; i++) {
            String s = sc.next();
            if (i % 2 == 0) hwall[i / 2] = s;
            else vwall[i / 2] = s;
        }

        int x = 0, y = 0, d = 0; // Start at (0,0), facing right (0)
        int startX = 0, startY = 0, startD = 0;
        StringBuilder sb = new StringBuilder();

        boolean[][][] visited = new boolean[4][4][4];
        boolean first = true;
        while (first || x != startX || y != startY || d != startD) {
            first = false;
            int right = (d + 1) % 4;
            int nx = x + dx[right], ny = y + dy[right];
            if (!isWall(x, y, right, hwall, vwall)) {
                d = right;
                x = nx; y = ny;
                sb.append(dirChar[d]);
            } else if (!isWall(x, y, d, hwall, vwall)) {
                x += dx[d]; y += dy[d];
                sb.append(dirChar[d]);
            } else if (!isWall(x, y, (d + 3) % 4, hwall, vwall)) {
                d = (d + 3) % 4;
                x += dx[d]; y += dy[d];
                sb.append(dirChar[d]);
            } else {
                d = (d + 2) % 4;
                x += dx[d]; y += dy[d];
                sb.append(dirChar[d]);
            }
        }
        System.out.println(sb.toString());
    }

    static boolean isWall(int x, int y, int dir, String[] hwall, String[] vwall) {
        if (dir == 0) { // right
            if (x >= 3) return true;
            return vwall[y].charAt(x + 1) == '1';
        }
        if (dir == 1) { // down
            if (y >= 3) return true;
            return hwall[y + 1].charAt(x) == '1';
        }
        if (dir == 2) { // left
            if (x <= 0) return true;
            return vwall[y].charAt(x) == '1';
        }
        if (dir == 3) { // up
            if (y <= 0) return true;
            return hwall[y].charAt(x) == '1';
        }
        return true;
    }
}