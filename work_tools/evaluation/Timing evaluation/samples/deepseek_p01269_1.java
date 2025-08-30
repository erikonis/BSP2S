import java.util.*;

public class Main {
    static class Edge {
        int to, distance, danger;
        Edge(int to, int distance, int danger) {
            this.to = to;
            this.distance = distance;
            this.danger = danger;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            int N = sc.nextInt();
            int M = sc.nextInt();
            int L = sc.nextInt();
            if (N == 0 && M == 0 && L == 0) break;

            List<List<Edge>> graph = new ArrayList<>();
            for (int i = 0; i <= N; i++) {
                graph.add(new ArrayList<>());
            }

            for (int i = 0; i < M; i++) {
                int A = sc.nextInt();
                int B = sc.nextInt();
                int D = sc.nextInt();
                int E = sc.nextInt();
                graph.get(A).add(new Edge(B, D, E));
                graph.get(B).add(new Edge(A, D, E));
            }

            int[][] dp = new int[N + 1][L + 1];
            for (int[] row : dp) {
                Arrays.fill(row, Integer.MAX_VALUE);
            }
            dp[1][L] = 0;

            PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[2] - b[2]);
            pq.offer(new int[]{1, L, 0});

            int result = Integer.MAX_VALUE;
            while (!pq.isEmpty()) {
                int[] current = pq.poll();
                int u = current[0];
                int money = current[1];
                int danger = current[2];

                if (u == N) {
                    result = danger;
                    break;
                }

                if (danger > dp[u][money]) continue;

                for (Edge edge : graph.get(u)) {
                    int v = edge.to;
                    int newDanger = danger + edge.danger;
                    if (money >= edge.distance) {
                        int newMoney = money - edge.distance;
                        if (dp[v][newMoney] > danger) {
                            dp[v][newMoney] = danger;
                            pq.offer(new int[]{v, newMoney, danger});
                        }
                    }
                    if (dp[v][money] > newDanger) {
                        dp[v][money] = newDanger;
                        pq.offer(new int[]{v, money, newDanger});
                    }
                }
            }
            System.out.println(result);
        }
    }
}