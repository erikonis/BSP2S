import java.util.*;

public class BreadthFirstSearch {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            adj.add(new ArrayList<>());
        }

        for (int i = 0; i < n; i++) {
            int u = scanner.nextInt();
            int k = scanner.nextInt();
            for (int j = 0; j < k; j++) {
                adj.get(u).add(scanner.nextInt());
            }
        }

        int[] distances = new int[n + 1];
        Arrays.fill(distances, -1);
        distances[1] = 0;
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(1);

        while (!queue.isEmpty()) {
            int current = queue.poll();
            for (int neighbor : adj.get(current)) {
                if (distances[neighbor] == -1) {
                    distances[neighbor] = distances[current] + 1;
                    queue.offer(neighbor);
                }
            }
        }

        for (int i = 1; i <= n; i++) {
            System.out.println(i + " " + distances[i]);
        }
    }
}