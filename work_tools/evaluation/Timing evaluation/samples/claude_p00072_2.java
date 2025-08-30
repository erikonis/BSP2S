import java.util.*;

public class Main {
    static class Edge implements Comparable<Edge> {
        int u, v, weight;
        
        Edge(int u, int v, int weight) {
            this.u = u;
            this.v = v;
            this.weight = weight;
        }
        
        @Override
        public int compareTo(Edge other) {
            return Integer.compare(this.weight, other.weight);
        }
    }
    
    static class UnionFind {
        int[] parent, rank;
        
        UnionFind(int n) {
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
            }
        }
        
        int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]);
            }
            return parent[x];
        }
        
        boolean union(int x, int y) {
            int px = find(x), py = find(y);
            if (px == py) return false;
            
            if (rank[px] < rank[py]) {
                parent[px] = py;
            } else if (rank[px] > rank[py]) {
                parent[py] = px;
            } else {
                parent[py] = px;
                rank[px]++;
            }
            return true;
        }
    }
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        while (true) {
            int n = sc.nextInt();
            if (n == 0) break;
            
            int m = sc.nextInt();
            sc.nextLine(); // consume newline
            
            List<Edge> edges = new ArrayList<>();
            
            for (int i = 0; i < m; i++) {
                String line = sc.nextLine();
                String[] parts = line.split(",");
                int a = Integer.parseInt(parts[0]);
                int b = Integer.parseInt(parts[1]);
                int d = Integer.parseInt(parts[2]);
                edges.add(new Edge(a, b, d));
            }
            
            // Kruskal's algorithm for MST
            Collections.sort(edges);
            UnionFind uf = new UnionFind(n);
            
            int totalLanterns = 0;
            int edgesUsed = 0;
            
            for (Edge edge : edges) {
                if (uf.union(edge.u, edge.v)) {
                    // This edge is part of MST
                    // Number of lanterns needed = distance/100 - 1
                    totalLanterns += (edge.weight / 100) - 1;
                    edgesUsed++;
                    if (edgesUsed == n - 1) break;
                }
            }
            
            System.out.println(totalLanterns);
        }
        
        sc.close();
    }
}