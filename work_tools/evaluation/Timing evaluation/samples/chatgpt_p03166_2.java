import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int M = sc.nextInt();

        // Build the graph and indegree array
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i <= N; ++i) adj.add(new ArrayList<>());
        int[] indegree = new int[N + 1];
        for (int i = 0; i < M; ++i) {
            int x = sc.nextInt();
            int y = sc.nextInt();
            adj.get(x).add(y);
            indegree[y]++;
        }

        // Topological sort (Kahn's algorithm)
        Queue<Integer> q = new ArrayDeque<>();
        for (int i = 1; i <= N; ++i)
            if (indegree[i] == 0) q.add(i);

        int[] dp = new int[N + 1]; // dp[v] = length of the longest path ending at v
        while (!q.isEmpty()) {
            int v = q.poll();
            for (int u : adj.get(v)) {
                // Update dp for next node
                if (dp[u] < dp[v] + 1) dp[u] = dp[v] + 1;
                if (--indegree[u] == 0) q.add(u);
            }
        }

        // The answer is the maximum dp value
        int ans = 0;
        for (int i = 1; i <= N; ++i) ans = Math.max(ans, dp[i]);
        System.out.println(ans);
    }
}