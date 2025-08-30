import java.io.*;
import java.util.*;

public class Main {
    static final double STREET_DIST = 100.0;
    static final double RADIUS = 10.0;
    static final double ARC = Math.PI * RADIUS; // half circumference
    static final int INF = Integer.MAX_VALUE / 2;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int x1 = Integer.parseInt(st.nextToken());
        int y1 = Integer.parseInt(st.nextToken());
        int x2 = Integer.parseInt(st.nextToken());
        int y2 = Integer.parseInt(st.nextToken());
        int N = Integer.parseInt(br.readLine());

        // Fountain positions
        Map<Integer, Integer> avenueToStreet = new HashMap<>(); // X_i -> Y_i
        Map<Integer, Integer> streetToAvenue = new HashMap<>(); // Y_i -> X_i
        Set<Long> fountains = new HashSet<>(); // encode (X_i, Y_i) as (X_i << 32) | Y_i

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            int X = Integer.parseInt(st.nextToken());
            int Y = Integer.parseInt(st.nextToken());
            avenueToStreet.put(X, Y);
            streetToAvenue.put(Y, X);
            fountains.add((((long)X) << 32) | ((long)Y));
        }

        // 0: start, 1: end, 2: start avenue fountain, 3: start street fountain, 4: end avenue fountain, 5: end street fountain
        double[][] adj = new double[6][6];
        for (double[] row : adj) Arrays.fill(row, Double.POSITIVE_INFINITY);

        // Coordinates for 6 "nodes" in the graph
        int[][] points = new int[6][2];
        points[0][0] = x1; points[0][1] = y1;
        points[1][0] = x2; points[1][1] = y2;

        // start avenue fountain, if any
        if (avenueToStreet.containsKey(x1)) {
            points[2][0] = x1;
            points[2][1] = avenueToStreet.get(x1);
        } else {
            points[2][0] = -1;
            points[2][1] = -1;
        }
        // start street fountain, if any
        if (streetToAvenue.containsKey(y1)) {
            points[3][0] = streetToAvenue.get(y1);
            points[3][1] = y1;
        } else {
            points[3][0] = -1;
            points[3][1] = -1;
        }
        // end avenue fountain, if any
        if (avenueToStreet.containsKey(x2)) {
            points[4][0] = x2;
            points[4][1] = avenueToStreet.get(x2);
        } else {
            points[4][0] = -1;
            points[4][1] = -1;
        }
        // end street fountain, if any
        if (streetToAvenue.containsKey(y2)) {
            points[5][0] = streetToAvenue.get(y2);
            points[5][1] = y2;
        } else {
            points[5][0] = -1;
            points[5][1] = -1;
        }

        // Build edges: only between reachable nodes
        // All edges are bidirectional, so adj[i][j] == adj[j][i]
        // 0: start, 1: end, 2: start avenue fountain, 3: start street fountain, 4: end avenue fountain, 5: end street fountain

        // 0->1: direct Manhattan, if no fountains block
        if (!hasFountainBetween(x1, y1, x2, y2, avenueToStreet, streetToAvenue, fountains)) {
            adj[0][1] = adj[1][0] = (Math.abs(x1 - x2) + Math.abs(y1 - y2)) * STREET_DIST;
        }

        // 0->2 (start to start avenue fountain)
        if (points[2][0] != -1) {
            if (y1 == points[2][1] && x1 != points[2][0] && !hasFountainBetween(x1, y1, points[2][0], y1, avenueToStreet, streetToAvenue, fountains))
                adj[0][2] = adj[2][0] = Math.abs(x1 - points[2][0]) * STREET_DIST;
            if (x1 == points[2][0] && y1 != points[2][1] && !hasFountainBetween(x1, y1, x1, points[2][1], avenueToStreet, streetToAvenue, fountains))
                adj[0][2] = adj[2][0] = Math.abs(y1 - points[2][1]) * STREET_DIST;
        }

        // 0->3 (start to start street fountain)
        if (points[3][0] != -1) {
            if (x1 == points[3][0] && y1 != points[3][1] && !hasFountainBetween(x1, y1, x1, points[3][1], avenueToStreet, streetToAvenue, fountains))
                adj[0][3] = adj[3][0] = Math.abs(y1 - points[3][1]) * STREET_DIST;
            if (y1 == points[3][1] && x1 != points[3][0] && !hasFountainBetween(x1, y1, points[3][0], y1, avenueToStreet, streetToAvenue, fountains))
                adj[0][3] = adj[3][0] = Math.abs(x1 - points[3][0]) * STREET_DIST;
        }

        // 1->4 (end to end avenue fountain)
        if (points[4][0] != -1) {
            if (y2 == points[4][1] && x2 != points[4][0] && !hasFountainBetween(x2, y2, points[4][0], y2, avenueToStreet, streetToAvenue, fountains))
                adj[1][4] = adj[4][1] = Math.abs(x2 - points[4][0]) * STREET_DIST;
            if (x2 == points[4][0] && y2 != points[4][1] && !hasFountainBetween(x2, y2, x2, points[4][1], avenueToStreet, streetToAvenue, fountains))
                adj[1][4] = adj[4][1] = Math.abs(y2 - points[4][1]) * STREET_DIST;
        }

        // 1->5 (end to end street fountain)
        if (points[5][0] != -1) {
            if (x2 == points[5][0] && y2 != points[5][1] && !hasFountainBetween(x2, y2, x2, points[5][1], avenueToStreet, streetToAvenue, fountains))
                adj[1][5] = adj[5][1] = Math.abs(y2 - points[5][1]) * STREET_DIST;
            if (y2 == points[5][1] && x2 != points[5][0] && !hasFountainBetween(x2, y2, points[5][0], y2, avenueToStreet, streetToAvenue, fountains))
                adj[1][5] = adj[5][1] = Math.abs(x2 - points[5][0]) * STREET_DIST;
        }

        // 2->4 (start avenue fountain to end avenue fountain)
        if (points[2][0] != -1 && points[4][0] != -1) {
            if (points[2][0] == points[4][0] && points[2][1] != points[4][1] &&
                !hasFountainBetween(points[2][0], points[2][1], points[4][0], points[4][1], avenueToStreet, streetToAvenue, fountains, true)) {
                adj[2][4] = adj[4][2] = Math.abs(points[2][1] - points[4][1]) * STREET_DIST;
            }
        }

        // 3->5 (start street fountain to end street fountain)
        if (points[3][1] != -1 && points[5][1] != -1) {
            if (points[3][1] == points[5][1] && points[3][0] != points[5][0] &&
                !hasFountainBetween(points[3][0], points[3][1], points[5][0], points[5][1], avenueToStreet, streetToAvenue, fountains, true)) {
                adj[3][5] = adj[5][3] = Math.abs(points[3][0] - points[5][0]) * STREET_DIST;
            }
        }

        // 2->5 (start avenue fountain to end street fountain): go via arc
        if (points[2][0] != -1 && points[5][1] != -1) {
            if (points[2][0] == points[5][0] && points[2][1] == points[5][1] && fountains.contains((((long)points[2][0]) << 32) | points[2][1])) {
                adj[2][5] = adj[5][2] = ARC;
            }
        }
        // 3->4 (start street fountain to end avenue fountain): go via arc
        if (points[3][1] != -1 && points[4][0] != -1) {
            if (points[3][0] == points[4][0] && points[3][1] == points[4][1] && fountains.contains((((long)points[3][0]) << 32) | points[3][1])) {
                adj[3][4] = adj[4][3] = ARC;
            }
        }

        // 2->3 (start avenue fountain to start street fountain): if they are the same fountain, arc
        if (points[2][0] != -1 && points[3][1] != -1) {
            if (points[2][0] == points[3][0] && points[2][1] == points[3][1] && fountains.contains((((long)points[2][0]) << 32) | points[2][1])) {
                adj[2][3] = adj[3][2] = ARC;
            }
        }
        // 4->5 (end avenue fountain to end street fountain): if they are the same fountain, arc
        if (points[4][0] != -1 && points[5][1] != -1) {
            if (points[4][0] == points[5][0] && points[4][1] == points[5][1] && fountains.contains((((long)points[4][0]) << 32) | points[4][1])) {
                adj[4][5] = adj[5][4] = ARC;
            }
        }

        // 2->4, 3->5, etc. (cross via Manhattan, if same avenue or street)
        // Other connections (e.g. 2->5, 3->4) are only possible via arc at fountain.

        // Now, Dijkstra's algorithm from 0 to 1 over graph with 6 nodes
        double[] dist = new double[6];
        Arrays.fill(dist, Double.POSITIVE_INFINITY);
        dist[0] = 0;
        PriorityQueue<State> pq = new PriorityQueue<>();
        pq.offer(new State(0, 0));
        while (!pq.isEmpty()) {
            State cur = pq.poll();
            int u = cur.idx;
            if (cur.dist > dist[u] + 1e-12) continue;
            for (int v = 0; v < 6; v++) {
                if (adj[u][v] < Double.POSITIVE_INFINITY) {
                    double cand = dist[u] + adj[u][v];
                    if (cand + 1e-12 < dist[v]) {
                        dist[v] = cand;
                        pq.offer(new State(v, cand));
                    }
                }
            }
        }
        System.out.printf("%.15f\n", dist[1]);
    }

    // Check if any fountain sits strictly between (xa, ya) and (xb, yb) along the path
    static boolean hasFountainBetween(int xa, int ya, int xb, int yb,
                                      Map<Integer, Integer> avenueToStreet,
                                      Map<Integer, Integer> streetToAvenue,
                                      Set<Long> fountains) {
        return hasFountainBetween(xa, ya, xb, yb, avenueToStreet, streetToAvenue, fountains, false);
    }

    static boolean hasFountainBetween(int xa, int ya, int xb, int yb,
                                      Map<Integer, Integer> avenueToStreet,
                                      Map<Integer, Integer> streetToAvenue,
                                      Set<Long> fountains,
                                      boolean skipBothEnds) {
        if (xa == xb) {
            // Along avenue, y changes
            int x = xa;
            int yFrom = Math.min(ya, yb) + 1;
            int yTo = Math.max(ya, yb) - 1;
            if (skipBothEnds) {
                yFrom++;
                yTo--;
            }
            for (int y = yFrom; y <= yTo; y++) {
                if (avenueToStreet.containsKey(x) && avenueToStreet.get(x) == y) return true;
            }
        } else if (ya == yb) {
            // Along street, x changes
            int y = ya;
            int xFrom = Math.min(xa, xb) + 1;
            int xTo = Math.max(xa, xb) - 1;
            if (skipBothEnds) {
                xFrom++;
                xTo--;
            }
            for (int x = xFrom; x <= xTo; x++) {
                if (streetToAvenue.containsKey(y) && streetToAvenue.get(y) == x) return true;
            }
        }
        return false;
    }

    static class State implements Comparable<State> {
        int idx;
        double dist;
        State(int i, double d) { idx = i; dist = d; }
        public int compareTo(State o) {
            return Double.compare(this.dist, o.dist);
        }
    }
}