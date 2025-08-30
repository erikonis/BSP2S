import java.util.*;

class Circle {
    double x, y, r;
    Circle(double x, double y, double r) {
        this.x = x;
        this.y = y;
        this.r = r;
    }
}

class Node implements Comparable<Node> {
    int idx;
    double dist;
    Node(int idx, double dist) {
        this.idx = idx;
        this.dist = dist;
    }
    public int compareTo(Node other) {
        return Double.compare(this.dist, other.dist);
    }
}

public class Main {
    static double distance(Circle a, Circle b) {
        double dx = a.x - b.x;
        double dy = a.y - b.y;
        return Math.sqrt(dx*dx + dy*dy);
    }

    static double tangentLength(Circle a, Circle b) {
        double d = distance(a, b);
        return Math.sqrt(d*d - (a.r - b.r) * (a.r - b.r));
    }

    static boolean isVisible(Circle[] circles, int i, int j) {
        for (int k = i + 1; k < j; k++) {
            Circle a = circles[i];
            Circle b = circles[j];
            Circle c = circles[k];
            double d = distance(a, b);
            double area = Math.abs((b.x - a.x) * (c.y - a.y) - (b.y - a.y) * (c.x - a.x));
            double h = area / d;
            if (h > c.r + 1e-8) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            int n = sc.nextInt();
            if (n == 0) break;
            
            Circle[] circles = new Circle[n];
            for (int i = 0; i < n; i++) {
                double x = sc.nextDouble();
                double y = sc.nextDouble();
                double r = sc.nextDouble();
                circles[i] = new Circle(x, y, r);
            }

            double[][] adj = new double[n][n];
            for (int i = 0; i < n; i++) {
                Arrays.fill(adj[i], Double.POSITIVE_INFINITY);
                adj[i][i] = 0;
            }

            for (int i = 0; i < n; i++) {
                for (int j = i + 1; j < n; j++) {
                    if (isVisible(circles, i, j)) {
                        double dist = tangentLength(circles[i], circles[j]);
                        adj[i][j] = adj[j][i] = dist;
                    }
                }
            }

            // Dijkstra's algorithm
            double[] dist = new double[n];
            Arrays.fill(dist, Double.POSITIVE_INFINITY);
            dist[0] = 0;
            PriorityQueue<Node> pq = new PriorityQueue<>();
            pq.add(new Node(0, 0));

            while (!pq.isEmpty()) {
                Node node = pq.poll();
                int u = node.idx;
                if (u == n-1) break;
                if (node.dist > dist[u]) continue;

                for (int v = 0; v < n; v++) {
                    if (u == v) continue;
                    double newDist = dist[u] + adj[u][v];
                    if (newDist < dist[v]) {
                        dist[v] = newDist;
                        pq.add(new Node(v, newDist));
                    }
                }
            }

            System.out.printf("%.6f\n", dist[n-1]);
        }
    }
}