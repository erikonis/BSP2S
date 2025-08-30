import java.util.*;

public class Main {

    private static int X_SIZE;
    private static int Y_SIZE;
    private static ArrayList<ArrayList<Integer>> adj;
    private static int[] matchY;
    private static boolean[] visited;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        X_SIZE = sc.nextInt();
        Y_SIZE = sc.nextInt();
        int E_SIZE = sc.nextInt();

        adj = new ArrayList<>();
        for (int i = 0; i < X_SIZE; i++) {
            adj.add(new ArrayList<>());
        }

        for (int i = 0; i < E_SIZE; i++) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            adj.get(u).add(v);
        }

        System.out.println(maxBPM());

        sc.close();
    }

    private static int maxBPM() {
        matchY = new int[Y_SIZE];
        Arrays.fill(matchY, -1);

        int result = 0;
        for (int u = 0; u < X_SIZE; u++) {
            visited = new boolean[X_SIZE];
            if (dfs(u)) {
                result++;
            }
        }
        return result;
    }

    private static boolean dfs(int u) {
        visited[u] = true;
        for (int v : adj.get(u)) {
            if (matchY[v] == -1 || (!visited[matchY[v]] && dfs(matchY[v]))) {
                matchY[v] = u;
                return true;
            }
        }
        return false;
    }
}