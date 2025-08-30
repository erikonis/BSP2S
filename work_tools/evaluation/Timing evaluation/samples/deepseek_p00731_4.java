import java.util.*;

public class CliffClimbing {
    static class State {
        int lx, ly, rx, ry, time;
        boolean leftMoved;

        State(int lx, int ly, int rx, int ry, int time, boolean leftMoved) {
            this.lx = lx;
            this.ly = ly;
            this.rx = rx;
            this.ry = ry;
            this.time = time;
            this.leftMoved = leftMoved;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            int w = sc.nextInt();
            int h = sc.nextInt();
            if (w == 0 && h == 0) break;

            char[][] grid = new char[h][w];
            for (int i = 0; i < h; i++) {
                for (int j = 0; j < w; j++) {
                    grid[i][j] = sc.next().charAt(0);
                }
            }

            PriorityQueue<State> pq = new PriorityQueue<>(Comparator.comparingInt(s -> s.time));
            int[][][][] dist = new int[h][w][h][w];
            for (int i = 0; i < h; i++) {
                for (int j = 0; j < w; j++) {
                    for (int k = 0; k < h; k++) {
                        Arrays.fill(dist[i][j][k], Integer.MAX_VALUE);
                    }
                }
            }

            // Initialize queue with starting positions
            for (int j = 0; j < w; j++) {
                if (grid[h-1][j] == 'S') {
                    for (int i = 0; i < w; i++) {
                        if (grid[h-1][i] == 'S' && i != j) {
                            dist[h-1][j][h-1][i] = 0;
                            pq.add(new State(j, h-1, i, h-1, 0, false));
                            dist[h-1][i][h-1][j] = 0;
                            pq.add(new State(i, h-1, j, h-1, 0, false));
                        }
                    }
                }
            }

            int result = -1;
            int[][] moves = {{-1,0}, {1,0}, {0,-1}, {0,1}, {-1,-1}, {-1,1}, {1,-1}, {1,1}, {0,0}};

            while (!pq.isEmpty()) {
                State current = pq.poll();
                int lx = current.lx;
                int ly = current.ly;
                int rx = current.rx;
                int ry = current.ry;
                int time = current.time;
                boolean leftMoved = current.leftMoved;

                if (grid[ly][lx] == 'T' || grid[ry][rx] == 'T') {
                    result = time;
                    break;
                }

                if (time > dist[ly][lx][ry][rx]) continue;

                if (leftMoved) {
                    // Move right foot
                    for (int[] move : moves) {
                        int nrx = rx + move[0];
                        int nry = ry + move[1];
                        if (nrx < 0 || nrx >= w || nry < 0 || nry >= h) continue;
                        if (grid[nry][nrx] == 'X') continue;
                        if (nrx <= lx) continue;
                        if (Math.abs(lx - nrx) + Math.abs(ly - nry) > 3) continue;

                        int newTime = time;
                        if (grid[nry][nrx] != 'S' && grid[nry][nrx] != 'T') {
                            newTime += grid[nry][nrx] - '0';
                        }
                        if (dist[ly][lx][nry][nrx] > newTime) {
                            dist[ly][lx][nry][nrx] = newTime;
                            pq.add(new State(lx, ly, nrx, nry, newTime, false));
                        }
                    }
                } else {
                    // Move left foot
                    for (int[] move : moves) {
                        int nlx = lx + move[0];
                        int nly = ly + move[1];
                        if (nlx < 0 || nlx >= w || nly < 0 || nly >= h) continue;
                        if (grid[nly][nlx] == 'X') continue;
                        if (nlx >= rx) continue;
                        if (Math.abs(nlx - rx) + Math.abs(nly - ry) > 3) continue;

                        int newTime = time;
                        if (grid[nly][nlx] != 'S' && grid[nly][nlx] != 'T') {
                            newTime += grid[nly][nlx] - '0';
                        }
                        if (dist[nly][nlx][ry][rx] > newTime) {
                            dist[nly][nlx][ry][rx] = newTime;
                            pq.add(new State(nlx, nly, rx, ry, newTime, true));
                        }
                    }
                }
            }
            System.out.println(result);
        }
    }
}