import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int M = sc.nextInt();

        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i <= N; i++) {
            adj.add(new ArrayList<>());
        }

        Set<Long> initialEdges = new HashSet<>();
        for (int i = 0; i < M; i++) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            adj.get(u).add(v);
            adj.get(v).add(u);
            initialEdges.add(getEdgeHash(u, v));
        }

        // Assign colors (0 or 1) based on bipartition
        int[] color = new int[N + 1];
        Arrays.fill(color, -1);
        Queue<Integer> q = new LinkedList<>();
        q.add(1);
        color[1] = 0;
        int count0 = 0;
        int count1 = 0;

        // Since the graph is connected, we can determine the bipartition
        while (!q.isEmpty()) {
            int u = q.poll();
            if (color[u] == 0) {
                count0++;
            } else {
                count1++;
            }

            for (int v : adj.get(u)) {
                if (color[v] == -1) {
                    color[v] = 1 - color[u];
                    q.add(v);
                }
            }
        }

        // Calculate maximum possible edges
        // An edge (u, v) can be added if there's a path of length 3 from u to v.
        // This means u and v must have the same color in the bipartition.
        // The total number of edges that can exist between nodes of the same color
        // is count0 * (count0 - 1) / 2 + count1 * (count1 - 1) / 2.
        // We subtract the initial edges that already connect nodes of the same color.
        long maxPossibleEdges = (long) count0 * (count0 - 1) / 2 + (long) count1 * (count1 - 1) / 2;

        long initialSameColorEdges = 0;
        for (long edgeHash : initialEdges) {
            int u = (int) (edgeHash >> 32);
            int v = (int) (edgeHash & 0xFFFFFFFFL);
            if (color[u] == color[v]) {
                initialSameColorEdges++;
            }
        }

        System.out.println(maxPossibleEdges - initialSameColorEdges);
    }

    private static long getEdgeHash(int u, int v) {
        if (u > v) {
            int temp = u;
            u = v;
            v = temp;
        }
        return ((long) u << 32) | v;
    }
}