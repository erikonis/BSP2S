import java.util.*;

public class Main {
    static class Edge {
        int to, weight;
        Edge(int to, int weight) {
            this.to = to;
            this.weight = weight;
        }
    }
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        
        List<List<Edge>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }
        
        for (int i = 0; i < n; i++) {
            int u = sc.nextInt();
            int k = sc.nextInt();
            for (int j = 0; j < k; j++) {
                int v = sc.nextInt();
                int c = sc.nextInt();
                graph.get(u).add(new Edge(v, c));
            }
        }
        
        // Bellman-Ford algorithm
        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[0] = 0;
        
        // Relax edges n-1 times
        for (int i = 0; i < n - 1; i++) {
            for (int u = 0; u < n; u++) {
                if (dist[u] != Integer.MAX_VALUE) {
                    for (Edge edge : graph.get(u)) {
                        if (dist[u] + edge.weight < dist[edge.to]) {
                            dist[edge.to] = dist[u] + edge.weight;
                        }
                    }
                }
            }
        }
        
        for (int i = 0; i < n; i++) {
            System.out.println(i + " " + dist[i]);
        }
    }
}