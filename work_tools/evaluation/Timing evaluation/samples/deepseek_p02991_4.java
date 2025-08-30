import java.util.*;

public class Main {
    static class State {
        int node;
        int step;

        State(int node, int step) {
            this.node = node;
            this.step = step;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int M = sc.nextInt();
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i <= N; i++) {
            graph.add(new ArrayList<>());
        }
        for (int i = 0; i < M; i++) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            graph.get(u).add(v);
        }
        int S = sc.nextInt();
        int T = sc.nextInt();

        int[][] dist = new int[N + 1][3];
        for (int[] row : dist) {
            Arrays.fill(row, -1);
        }
        Queue<State> q = new LinkedList<>();
        q.add(new State(S, 0));
        dist[S][0] = 0;

        while (!q.isEmpty()) {
            State current = q.poll();
            int node = current.node;
            int step = current.step;
            if (node == T && step == 0 && dist[node][step] != 0) {
                System.out.println(dist[node][step] / 3);
                return;
            }
            for (int neighbor : graph.get(node)) {
                int nextStep = (step + 1) % 3;
                if (dist[neighbor][nextStep] == -1) {
                    dist[neighbor][nextStep] = dist[node][step] + 1;
                    q.add(new State(neighbor, nextStep));
                }
            }
        }

        System.out.println(-1);
    }
}