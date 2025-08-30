import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int V = sc.nextInt();
        int E = sc.nextInt();

        ArrayList<ArrayList<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < V; i++) {
            adj.add(new ArrayList<>());
        }

        for (int i = 0; i < E; i++) {
            int s = sc.nextInt();
            int t = sc.nextInt();
            adj.get(s).add(t);
        }

        if (hasCycle(V, adj)) {
            System.out.println(1);
        } else {
            System.out.println(0);
        }

        sc.close();
    }

    public static boolean hasCycle(int V, ArrayList<ArrayList<Integer>> adj) {
        boolean[] visited = new boolean[V];
        boolean[] recursionStack = new boolean[V];

        for (int i = 0; i < V; i++) {
            if (dfs(i, adj, visited, recursionStack)) {
                return true;
            }
        }
        return false;
    }

    private static boolean dfs(int u, ArrayList<ArrayList<Integer>> adj, boolean[] visited, boolean[] recursionStack) {
        visited[u] = true;
        recursionStack[u] = true;

        for (int v : adj.get(u)) {
            if (!visited[v]) {
                if (dfs(v, adj, visited, recursionStack)) {
                    return true;
                }
            } else if (recursionStack[v]) {
                return true;
            }
        }
        recursionStack[u] = false;
        return false;
    }
}