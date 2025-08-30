import java.util.*;
import java.io.*;

public class Main {
    static class Edge {
        int u, v, id;
        Edge(int u, int v, int id) {
            this.u = u;
            this.v = v;
            this.id = id;
        }
    }

    static List<Edge>[] adj;
    static int[] degree;
    static boolean[] visited;
    static List<String> res;
    static Edge[] edges;
    static int[] outDegree;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] line = br.readLine().split(" ");
        int N = Integer.parseInt(line[0]);
        int M = Integer.parseInt(line[1]);

        adj = new ArrayList[N + 1];
        degree = new int[N + 1];
        visited = new boolean[N + 1];
        res = new ArrayList<>();
        edges = new Edge[M + 1];
        outDegree = new int[N + 1];

        for (int i = 1; i <= N; i++) {
            adj[i] = new ArrayList<>();
        }

        for (int i = 1; i <= M; i++) {
            line = br.readLine().split(" ");
            int u = Integer.parseInt(line[0]);
            int v = Integer.parseInt(line[1]);
            adj[u].add(new Edge(u, v, i));
            adj[v].add(new Edge(v, u, i));
            edges[i] = new Edge(u, v, i);
            degree[u]++;
            degree[v]++;
        }

        for (int i = 1; i <= N; i++) {
            if (degree[i] % 2 != 0) {
                System.out.println(-1);
                return;
            }
        }

        dfs(1);

        for (int i = 1; i <= N; i++) {
            if (outDegree[i] % 2 != 0) {
                System.out.println(-1);
                return;
            }
        }

        for (String s : res) {
            System.out.println(s);
        }
    }

    static void dfs(int u) {
        visited[u] = true;
        for (Edge e : adj[u]) {
            if (!visited[e.v]) {
                res.add(u + " " + e.v);
                outDegree[u]++;
                dfs(e.v);
            } else {
                if (outDegree[u] % 2 != 0) {
                    res.add(u + " " + e.v);
                    outDegree[u]++;
                }
            }
        }
    }
}