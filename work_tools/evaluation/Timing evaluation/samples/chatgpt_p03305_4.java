import java.util.*;
import java.io.*;

public class Main {
    static class Edge {
        int to;
        long a, b;
        Edge(int to, long a, long b) {
            this.to = to;
            this.a = a;
            this.b = b;
        }
    }

    static int n, m, s, t;
    static ArrayList<Edge>[] graph;

    static long[] dijkstra(int start, boolean useA) {
        long[] dist = new long[n];
        Arrays.fill(dist, Long.MAX_VALUE);
        PriorityQueue<long[]> pq = new PriorityQueue<>(Comparator.comparingLong(x -> x[0]));
        dist[start] = 0;
        pq.add(new long[]{0, start});
        while (!pq.isEmpty()) {
            long[] cur = pq.poll();
            long cost = cur[0];
            int v = (int)cur[1];
            if (cost > dist[v]) continue;
            for (Edge e : graph[v]) {
                long ncost = cost + (useA ? e.a : e.b);
                if (dist[e.to] > ncost) {
                    dist[e.to] = ncost;
                    pq.add(new long[]{ncost, e.to});
                }
            }
        }
        return dist;
    }

    public static void main(String[] args) throws IOException {
        final long INF = 1000000000000000L;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] line = br.readLine().split(" ");
        n = Integer.parseInt(line[0]);
        m = Integer.parseInt(line[1]);
        s = Integer.parseInt(line[2]) - 1;
        t = Integer.parseInt(line[3]) - 1;
        graph = new ArrayList[n];
        for (int i = 0; i < n; i++) graph[i] = new ArrayList<>();

        for (int i = 0; i < m; i++) {
            String[] l = br.readLine().split(" ");
            int u = Integer.parseInt(l[0]) - 1;
            int v = Integer.parseInt(l[1]) - 1;
            long a = Long.parseLong(l[2]);
            long b = Long.parseLong(l[3]);
            graph[u].add(new Edge(v, a, b));
            graph[v].add(new Edge(u, a, b));
        }

        // distYen: min cost from s to each city in yen
        long[] distYen = dijkstra(s, true);

        // distSnuuk: min cost from t to each city in snuuk
        long[] distSnuuk = dijkstra(t, false);

        long[] ans = new long[n];
        long minTotal = Long.MAX_VALUE;
        // We'll fill from n-1 downto 0 for the minTotal trick
        for (int i = n-1; i >= 0; i--) {
            if (distYen[i] != Long.MAX_VALUE && distSnuuk[i] != Long.MAX_VALUE) {
                minTotal = Math.min(minTotal, distYen[i] + distSnuuk[i]);
            }
            ans[i] = INF * 10000 - minTotal; // INF * 10000 >= max cost possible
        }
        // Correction: the initial yen is 1e15, not 1e19. So subtract from 1e15.
        for (int i = 0; i < n; i++) {
            System.out.println(INF - ans[i]);
        }
    }
}