import java.util.*;

public class Main {
    static List<Integer>[] adj;
    static int[] a;
    static int[] parent;
    static int[] depth;
    static int[] ans;
    static List<Integer> lis;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        a = new int[N + 1];
        for (int i = 1; i <= N; i++) {
            a[i] = sc.nextInt();
        }

        adj = new ArrayList[N + 1];
        for (int i = 1; i <= N; i++) {
            adj[i] = new ArrayList<>();
        }

        for (int i = 0; i < N - 1; i++) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            adj[u].add(v);
            adj[v].add(u);
        }

        parent = new int[N + 1];
        depth = new int[N + 1];
        ans = new int[N + 1];
        lis = new ArrayList<>();

        dfs(1, 0, 0);

        for (int i = 1; i <= N; i++) {
            System.out.println(ans[i]);
        }
    }

    static void dfs(int u, int p, int d) {
        parent[u] = p;
        depth[u] = d;

        int original_idx = -1;
        int original_val = -1;

        int pos = Collections.binarySearch(lis, a[u]);
        if (pos < 0) {
            pos = -(pos + 1);
        }

        if (pos < lis.size()) {
            original_idx = pos;
            original_val = lis.get(pos);
            lis.set(pos, a[u]);
        } else {
            lis.add(a[u]);
        }

        ans[u] = lis.size();

        for (int v : adj[u]) {
            if (v == p) continue;
            dfs(v, u, d + 1);
        }

        // Backtrack
        if (original_idx != -1) {
            lis.set(original_idx, original_val);
        } else {
            lis.remove(lis.size() - 1);
        }
    }
}