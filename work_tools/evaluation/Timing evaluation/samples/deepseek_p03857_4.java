import java.io.*;
import java.util.*;

public class Main {
    static class UnionFind {
        int[] parent;

        UnionFind(int n) {
            parent = new int[n + 1];
            for (int i = 1; i <= n; i++) {
                parent[i] = i;
            }
        }

        int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]);
            }
            return parent[x];
        }

        void union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);
            if (rootX != rootY) {
                parent[rootY] = rootX;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] line = br.readLine().split(" ");
        int N = Integer.parseInt(line[0]);
        int K = Integer.parseInt(line[1]);
        int L = Integer.parseInt(line[2]);

        UnionFind ufRoad = new UnionFind(N);
        UnionFind ufRail = new UnionFind(N);

        for (int i = 0; i < K; i++) {
            line = br.readLine().split(" ");
            int p = Integer.parseInt(line[0]);
            int q = Integer.parseInt(line[1]);
            ufRoad.union(p, q);
        }

        for (int i = 0; i < L; i++) {
            line = br.readLine().split(" ");
            int r = Integer.parseInt(line[0]);
            int s = Integer.parseInt(line[1]);
            ufRail.union(r, s);
        }

        Map<String, Integer> count = new HashMap<>();
        int[] res = new int[N + 1];
        for (int i = 1; i <= N; i++) {
            int roadParent = ufRoad.find(i);
            int railParent = ufRail.find(i);
            String key = roadParent + " " + railParent;
            count.put(key, count.getOrDefault(key, 0) + 1);
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= N; i++) {
            int roadParent = ufRoad.find(i);
            int railParent = ufRail.find(i);
            String key = roadParent + " " + railParent;
            sb.append(count.get(key)).append(" ");
        }
        System.out.println(sb.toString().trim());
    }
}