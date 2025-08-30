import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        List<List<Edge>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<>());
        }

        for (int i = 0; i < n; i++) {
            int u = sc.nextInt();
            int k = sc.nextInt();
            for (int j = 0; j < k; j++) {
                int v = sc.nextInt();
                int c = sc.nextInt();
                adj.get(u).add(new Edge(v, c));
            }
        }

        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[0] = 0;

        PriorityQueue<Edge> pq = new PriorityQueue<>();
        pq.offer(new Edge(0, 0));

        while (!pq.isEmpty()) {
            Edge current = pq.poll();
            int u = current.to;
            if (dist[u] < current.weight) continue;

            for (Edge edge : adj.get(u)) {
                int v = edge.to;
                int newDist = dist[u] + edge.weight;
                if (newDist < dist[v]) {
                    dist[v] = newDist;
                    pq.offer(new Edge(v, dist[v]));
                }
            }
        }

        for (int i = 0; i < n; i++) {
            System.out.println(i + " " + dist[i]);
        }
    }

    static class Edge implements Comparable<Edge> {
        int to;
        int weight;

        Edge(int to, int weight) {
            this.to = to;
            this.weight = weight;
        }

        @Override
        public int compareTo(Edge other) {
            return Integer.compare(this.weight, other.weight);
        }
    }
}