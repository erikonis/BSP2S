import java.util.*;

public class Main {
    static class Edge {
        int to;
        int weight;
        Edge(int to, int weight) {
            this.to = to;
            this.weight = weight;
        }
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        List<Edge>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++) graph[i] = new ArrayList<>();
        for (int i = 0; i < n - 1; i++) {
            int u = sc.nextInt() - 1, v = sc.nextInt() - 1, w = sc.nextInt();
            graph[u].add(new Edge(v, w));
            graph[v].add(new Edge(u, w));
        }
        int[] color = new int[n];
        Arrays.fill(color, -1);
        Deque<Integer> queue = new ArrayDeque<>();
        color[0] = 0;
        queue.add(0);
        while (!queue.isEmpty()) {
            int cur = queue.poll();
            for (Edge edge : graph[cur]) {
                if (color[edge.to] == -1) {
                    color[edge.to] = (color[cur] + edge.weight % 2) % 2;
                    queue.add(edge.to);
                }
            }
        }
        for (int c : color) System.out.println(c);
    }
}