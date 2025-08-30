import java.util.*;

public class Main {
    static final int MAX = 105;
    static List<int[]> edges;
    static int[] degree = new int[MAX];

    // Union-Find
    static int[] par = new int[MAX];
    static void initUF(int n) {
        for (int i = 0; i < n; i++) par[i] = i;
    }
    static int find(int x) {
        if (par[x] == x) return x;
        return par[x] = find(par[x]);
    }
    static void unite(int x, int y) {
        int px = find(x);
        int py = find(y);
        if (px != py) par[px] = py;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            edges = new ArrayList<>();
            Arrays.fill(degree, 0);

            // Read dataset
            Set<Integer> nodes = new HashSet<>();
            while (true) {
                int a = sc.nextInt();
                int b = sc.nextInt();
                if (a == 0 && b == 0) break;
                edges.add(new int[]{a, b});
                degree[a]++;
                degree[b]++;
                nodes.add(a);
                nodes.add(b);
            }

            if (edges.isEmpty()) break; // no more dataset

            // Check degree conditions
            boolean ok = true;
            if (degree[1] != 1 || degree[2] != 1) ok = false;
            for (int v : nodes) {
                if (v != 1 && v != 2 && (degree[v] % 2 != 0)) ok = false;
            }

            // Check connectivity (all nodes should be connected)
            initUF(MAX);
            for (int[] e : edges) {
                unite(e[0], e[1]);
            }
            int root = find(1);
            for (int v : nodes) {
                if (find(v) != root) ok = false;
            }

            // Check if an Euler path exists from 1 to 2 (Hierholzer's algorithm)
            // Since degree conditions are already checked, if the graph is connected, such path exists.

            System.out.println(ok ? "OK" : "NG");
        }
    }
}