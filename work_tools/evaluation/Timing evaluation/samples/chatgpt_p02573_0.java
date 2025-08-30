import java.util.*;

public class Main {
    static ArrayList<Integer>[] adj;
    static boolean[] visited;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int M = sc.nextInt();

        adj = new ArrayList[N + 1];
        for (int i = 1; i <= N; i++) adj[i] = new ArrayList<>();
        for (int i = 0; i < M; i++) {
            int a = sc.nextInt();
            int b = sc.nextInt();
            adj[a].add(b);
            adj[b].add(a);
        }

        visited = new boolean[N + 1];
        int ans = 0;
        for (int i = 1; i <= N; i++) {
            if (!visited[i]) {
                ans = Math.max(ans, maxIndependentSet(i));
            }
        }
        System.out.println(ans);
    }

    // Find the max size of an independent set by 2-coloring (bipartite)
    static int maxIndependentSet(int start) {
        Queue<Integer> q = new LinkedList<>();
        int[] color = new int[adj.length];
        Arrays.fill(color, -1);
        q.add(start);
        color[start] = 0;
        visited[start] = true;
        int[] cnt = new int[2];
        cnt[0] = 1;
        while (!q.isEmpty()) {
            int u = q.poll();
            for (int v : adj[u]) {
                if (color[v] == -1) {
                    color[v] = color[u] ^ 1;
                    visited[v] = true;
                    cnt[color[v]]++;
                    q.add(v);
                }
            }
        }
        return Math.max(cnt[0], cnt[1]);
    }
}