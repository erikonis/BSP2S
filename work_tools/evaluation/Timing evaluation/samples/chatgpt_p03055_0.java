import java.util.*;
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        List<List<Integer>> g = new ArrayList<>();
        for (int i = 0; i < N; i++) g.add(new ArrayList<>());
        for (int i = 0; i < N - 1; i++) {
            int a = sc.nextInt() - 1;
            int b = sc.nextInt() - 1;
            g.get(a).add(b);
            g.get(b).add(a);
        }
        // Find farthest node from 0
        int[] d1 = bfs(g, 0);
        int far = 0;
        for (int i = 0; i < N; i++) if (d1[i] > d1[far]) far = i;
        // Find diameter
        int[] d2 = bfs(g, far);
        int diam = 0;
        for (int v : d2) if (v > diam) diam = v;
        // If diameter is odd, Second wins; else First wins
        System.out.println(diam % 2 == 0 ? "First" : "Second");
    }
    static int[] bfs(List<List<Integer>> g, int start) {
        int N = g.size();
        int[] dist = new int[N];
        Arrays.fill(dist, -1);
        Queue<Integer> q = new LinkedList<>();
        dist[start] = 0;
        q.add(start);
        while (!q.isEmpty()) {
            int v = q.poll();
            for (int to : g.get(v)) {
                if (dist[to] == -1) {
                    dist[to] = dist[v] + 1;
                    q.add(to);
                }
            }
        }
        return dist;
    }
}