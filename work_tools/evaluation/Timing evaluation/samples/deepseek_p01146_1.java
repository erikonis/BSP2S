import java.util.*;

class Node implements Comparable<Node> {
    int town;
    int time;
    int remaining;

    public Node(int town, int time, int remaining) {
        this.town = town;
        this.time = time;
        this.remaining = remaining;
    }

    @Override
    public int compareTo(Node other) {
        return this.time - other.time;
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            int N = sc.nextInt();
            int M = sc.nextInt();
            int L = sc.nextInt();
            int K = sc.nextInt();
            int A = sc.nextInt();
            int H = sc.nextInt();
            if (N == 0 && M == 0 && L == 0 && K == 0 && A == 0 && H == 0) break;

            Set<Integer> freezeTowns = new HashSet<>();
            for (int i = 0; i < L; i++) {
                freezeTowns.add(sc.nextInt());
            }
            freezeTowns.add(A);
            freezeTowns.add(H);

            List<List<int[]>> adj = new ArrayList<>();
            for (int i = 0; i < N; i++) {
                adj.add(new ArrayList<>());
            }
            for (int i = 0; i < K; i++) {
                int X = sc.nextInt();
                int Y = sc.nextInt();
                int T = sc.nextInt();
                adj.get(X).add(new int[]{Y, T});
                adj.get(Y).add(new int[]{X, T});
            }

            int[][] dist = new int[N][M + 1];
            for (int i = 0; i < N; i++) {
                Arrays.fill(dist[i], Integer.MAX_VALUE);
            }
            dist[A][M] = 0;

            PriorityQueue<Node> pq = new PriorityQueue<>();
            pq.add(new Node(A, 0, M));

            boolean found = false;
            while (!pq.isEmpty()) {
                Node current = pq.poll();
                int u = current.town;
                int time = current.time;
                int rem = current.remaining;

                if (u == H) {
                    System.out.println(time);
                    found = true;
                    break;
                }

                if (time > dist[u][rem]) continue;

                for (int[] edge : adj.get(u)) {
                    int v = edge[0];
                    int t = edge[1];

                    if (rem >= t) {
                        int newRem = rem - t;
                        if (freezeTowns.contains(v)) {
                            newRem = M;
                        }
                        if (dist[v][newRem] > time + t) {
                            dist[v][newRem] = time + t;
                            pq.add(new Node(v, time + t, newRem));
                        }
                    }

                    if (freezeTowns.contains(u)) {
                        for (int freezeTime = 1; freezeTime <= M - rem; freezeTime++) {
                            int newTime = time + freezeTime;
                            int newRem = rem + freezeTime;
                            if (newRem > M) newRem = M;
                            if (newRem >= t) {
                                int newVRem = newRem - t;
                                if (freezeTowns.contains(v)) {
                                    newVRem = M;
                                }
                                if (dist[v][newVRem] > newTime + t) {
                                    dist[v][newVRem] = newTime + t;
                                    pq.add(new Node(v, newTime + t, newVRem));
                                }
                            }
                        }
                    }
                }
            }

            if (!found) {
                System.out.println("Help!");
            }
        }
        sc.close();
    }
}