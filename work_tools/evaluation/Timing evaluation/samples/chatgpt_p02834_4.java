import java.util.*;
public class Main {
    static ArrayList<Integer>[] tree;
    static int N, u, v;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        u = sc.nextInt() - 1;
        v = sc.nextInt() - 1;
        tree = new ArrayList[N];
        for (int i = 0; i < N; i++) tree[i] = new ArrayList<>();
        for (int i = 0; i < N - 1; i++) {
            int a = sc.nextInt() - 1;
            int b = sc.nextInt() - 1;
            tree[a].add(b);
            tree[b].add(a);
        }

        int[] distU = bfs(u);
        int[] distV = bfs(v);

        int maxAokiMoves = 0;
        for (int i = 0; i < N; i++) {
            if (distU[i] < distV[i]) {
                maxAokiMoves = Math.max(maxAokiMoves, distV[i] - 1);
            }
        }
        System.out.println(maxAokiMoves);
    }

    static int[] bfs(int start) {
        int[] dist = new int[N];
        Arrays.fill(dist, -1);
        Queue<Integer> q = new ArrayDeque<>();
        q.add(start);
        dist[start] = 0;
        while (!q.isEmpty()) {
            int cur = q.poll();
            for (int nxt : tree[cur]) {
                if (dist[nxt] == -1) {
                    dist[nxt] = dist[cur] + 1;
                    q.add(nxt);
                }
            }
        }
        return dist;
    }
}