import java.util.*;

public class Main {
    static int N;
    static long K;
    static List<List<Integer>> adj;
    static long MOD = 1_000_000_007;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        K = sc.nextLong();

        adj = new ArrayList<>();
        for (int i = 0; i <= N; i++) {
            adj.add(new ArrayList<>());
        }

        for (int i = 0; i < N - 1; i++) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            adj.get(u).add(v);
            adj.get(v).add(u);
        }

        if (N == 1) {
            System.out.println(K);
            return;
        }

        long ans = K * dfs(1, 0, 0) % MOD;
        System.out.println(ans);
    }

    static long dfs(int u, int p, int depth) {
        long ways = 1;
        int colorsUsed = 0;
        if (p != 0) { // If not root, parent uses one color
            colorsUsed++;
        }
        if (depth >= 1) { // If not root and not direct child of root, grandparent uses one color
            colorsUsed++;
        }

        long availableColors = K - colorsUsed;

        for (int v : adj.get(u)) {
            if (v == p) {
                continue;
            }
            if (availableColors <= 0) { // Not enough colors for children
                return 0; // This path is invalid
            }
            ways = (ways * availableColors) % MOD;
            availableColors--;
            ways = (ways * dfs(v, u, depth + 1)) % MOD;
        }
        return ways;
    }
}