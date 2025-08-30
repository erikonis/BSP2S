import java.util.*;
public class Main {
    static class Edge {
        int to, dist, enemy;
        Edge(int to, int dist, int enemy) {
            this.to = to; this.dist = dist; this.enemy = enemy;
        }
    }
    static class State implements Comparable<State> {
        int node, attacked, rem;
        State(int node, int attacked, int rem) {
            this.node = node; this.attacked = attacked; this.rem = rem;
        }
        public int compareTo(State o) {
            return this.attacked - o.attacked;
        }
    }
    static final int INF = (int)1e9;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            int N = sc.nextInt(), M = sc.nextInt(), L = sc.nextInt();
            if (N == 0 && M == 0 && L == 0) break;
            ArrayList<Edge>[] g = new ArrayList[N+1];
            for (int i = 1; i <= N; i++) g[i] = new ArrayList<>();
            for (int i = 0; i < M; i++) {
                int a = sc.nextInt(), b = sc.nextInt(), d = sc.nextInt(), e = sc.nextInt();
                g[a].add(new Edge(b, d, e));
                g[b].add(new Edge(a, d, e));
            }
            int[][] min = new int[N+1][L+1];
            for (int i = 1; i <= N; i++) Arrays.fill(min[i], INF);
            PriorityQueue<State> pq = new PriorityQueue<>();
            min[1][L] = 0;
            pq.add(new State(1, 0, L));
            while (!pq.isEmpty()) {
                State s = pq.poll();
                if (s.attacked > min[s.node][s.rem]) continue;
                for (Edge e : g[s.node]) {
                    // Option 1: use guard if enough budget
                    if (s.rem >= e.dist) {
                        int nr = s.rem - e.dist;
                        if (min[e.to][nr] > s.attacked) {
                            min[e.to][nr] = s.attacked;
                            pq.add(new State(e.to, s.attacked, nr));
                        }
                    }
                    // Option 2: no guard, attacked by enemy
                    if (min[e.to][s.rem] > s.attacked + e.enemy) {
                        min[e.to][s.rem] = s.attacked + e.enemy;
                        pq.add(new State(e.to, s.attacked + e.enemy, s.rem));
                    }
                }
            }
            int ans = INF;
            for (int i = 0; i <= L; i++) ans = Math.min(ans, min[N][i]);
            System.out.println(ans);
        }
    }
}