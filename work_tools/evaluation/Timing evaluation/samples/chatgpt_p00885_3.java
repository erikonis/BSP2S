import java.util.*;

public class Main {
    static class Balloon {
        int pos, time;
        Balloon(int p, int t) { pos = p; time = t; }
    }

    static int n;
    static Balloon[] balloons;
    static int INF = Integer.MAX_VALUE / 2;

    static class State implements Comparable<State> {
        int idx, pos, carry, t, dist;
        int mask; // which balloons have been collected and not yet delivered
        State(int idx, int pos, int carry, int t, int dist, int mask) {
            this.idx = idx; this.pos = pos; this.carry = carry; this.t = t; this.dist = dist; this.mask = mask;
        }
        public int compareTo(State o) {
            return this.dist - o.dist;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            n = sc.nextInt();
            if (n == 0) break;
            balloons = new Balloon[n];
            for (int i = 0; i < n; i++) {
                int p = sc.nextInt();
                int t = sc.nextInt();
                balloons[i] = new Balloon(p, t);
            }
            solve();
        }
    }

    static void solve() {
        // DP: dp[i][pos][carry][mask] = min distance to reach this state
        // Use Dijkstra to find minimal path
        PriorityQueue<State> pq = new PriorityQueue<>();
        HashMap<String, Integer> visited = new HashMap<>();

        pq.add(new State(0, 0, 0, 0, 0, 0));
        int answer = INF;
        boolean ok = false;

        // For failure
        int failIdx = -1;

        while (!pq.isEmpty()) {
            State s = pq.poll();
            String key = s.idx + "," + s.pos + "," + s.carry + "," + s.mask;
            if (visited.containsKey(key) && visited.get(key) <= s.dist) continue;
            visited.put(key, s.dist);

            // If all balloons delivered
            if (s.idx == n && s.carry == 0 && s.mask == 0) {
                answer = s.dist;
                ok = true;
                break;
            }

            // If carrying, can return to house to deliver
            if (s.carry > 0) {
                int d = Math.abs(s.pos - 0);
                int tnext = s.t + d * (s.carry + 1);
                int distNext = s.dist + d;
                int maskNext = 0;
                // Remove all carried balloons
                pq.add(new State(s.idx, 0, 0, tnext, distNext, maskNext));
            }

            // Try to pick next balloon(s) -- only next one, as order is strict
            if (s.idx < n && s.carry < 3) {
                Balloon b = balloons[s.idx];
                int d = Math.abs(s.pos - b.pos);
                int speed = s.carry + 1;
                int t_arrive = s.t + d * speed;
                if (t_arrive <= b.time) {
                    // Wait until b.time if needed
                    int tnext = Math.max(t_arrive, b.time);
                    int distNext = s.dist + d;
                    pq.add(new State(s.idx + 1, b.pos, s.carry + 1, tnext, distNext, s.mask | (1 << s.idx)));
                } else {
                    // Cannot reach this balloon in time, fail here
                    failIdx = s.idx + 1;
                    break;
                }
            }
        }

        if (ok) {
            System.out.println("OK " + answer);
        } else {
            System.out.println("NG " + failIdx);
        }
    }
}