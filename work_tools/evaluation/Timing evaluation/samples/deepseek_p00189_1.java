import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            int n = scanner.nextInt();
            if (n == 0) break;
            
            int maxNode = 0;
            List<Edge> edges = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                int a = scanner.nextInt();
                int b = scanner.nextInt();
                int c = scanner.nextInt();
                edges.add(new Edge(a, b, c));
                maxNode = Math.max(maxNode, Math.max(a, b));
            }
            
            int nodeCount = maxNode + 1;
            int[][] dist = new int[nodeCount][nodeCount];
            for (int i = 0; i < nodeCount; i++) {
                Arrays.fill(dist[i], Integer.MAX_VALUE);
                dist[i][i] = 0;
            }
            
            for (Edge edge : edges) {
                int a = edge.a;
                int b = edge.b;
                int c = edge.c;
                dist[a][b] = Math.min(dist[a][b], c);
                dist[b][a] = Math.min(dist[b][a], c);
            }
            
            // Floyd-Warshall
            for (int k = 0; k < nodeCount; k++) {
                for (int i = 0; i < nodeCount; i++) {
                    for (int j = 0; j < nodeCount; j++) {
                        if (dist[i][k] != Integer.MAX_VALUE && dist[k][j] != Integer.MAX_VALUE) {
                            dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
                        }
                    }
                }
            }
            
            int minSum = Integer.MAX_VALUE;
            int bestNode = 0;
            for (int i = 0; i < nodeCount; i++) {
                int sum = 0;
                for (int j = 0; j < nodeCount; j++) {
                    sum += dist[i][j];
                }
                if (sum < minSum) {
                    minSum = sum;
                    bestNode = i;
                }
            }
            
            System.out.println(bestNode + " " + minSum);
        }
    }
    
    static class Edge {
        int a, b, c;
        Edge(int a, int b, int c) {
            this.a = a;
            this.b = b;
            this.c = c;
        }
    }
}