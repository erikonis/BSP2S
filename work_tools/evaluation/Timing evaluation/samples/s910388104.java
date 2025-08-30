import java.io.*;
import java.util.*;

public class Main implements Runnable{
    private int[][] graph;
    public static void main(String[] args) throws Exception {
        new Thread(null, new Main(), "bridge", 16 * 1024 * 1024).start();
    }

    @Override
    public void run() {
        try {
            solve();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void solve() throws Exception{
        FastScanner scanner = new FastScanner(System.in);

        int V = scanner.nextInt();
        int E = scanner.nextInt();

        graph = new int[V][V];

        for(int i = 0; i < E; ++i){
            int u = scanner.nextInt();
            int v = scanner.nextInt();
            int c = scanner.nextInt();

            graph[u][v] = c;
        }

        int flow;
        int totalFlow = 0;
        int s = 0;
        int t = V - 1;
        while((flow = findPath(s, t, new boolean[V], Integer.MAX_VALUE)) != 0){
            totalFlow += flow;
        }

        System.out.println(totalFlow);
    }

    private int findPath(int u, int t, boolean[] visited, int flow){
        if(u == t){
            return flow;
        }

        visited[u] = true;
        int V = graph.length;

        for(int v = 0; v < V; ++v){
            if(!visited[v] && graph[u][v] > 0){
                int pathFlow = findPath(v, t, visited, Math.min(flow, graph[u][v]));
                if(pathFlow > 0){
                    graph[u][v] -= pathFlow;
                    graph[v][u] += pathFlow;

                    return pathFlow;
                }
            }
        }

        return 0;
    }

    static class FastScanner {
        private InputStream in;
        private final byte[] buffer = new byte[1024 * 8];
        private int ptr = 0;
        private int buflen = 0;

        public FastScanner(InputStream in){
            this.in = in;
        }

        private boolean hasNextByte() {
            if (ptr < buflen) {
                return true;
            } else {
                ptr = 0;
                try {
                    buflen = in.read(buffer);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (buflen <= 0) {
                    return false;
                }
            }
            return true;
        }

        private int readByte() {
            if (hasNextByte()) return buffer[ptr++];
            else return -1;
        }

        private static boolean isPrintableChar(int c) {
            return 33 <= c && c <= 126;
        }

        private void skipUnprintable() {
            while (hasNextByte() && !isPrintableChar(buffer[ptr])) ptr++;
        }

        public boolean hasNext() {
            skipUnprintable();
            return hasNextByte();
        }

        public String next() {
            if (!hasNext()) throw new NoSuchElementException();
            StringBuilder sb = new StringBuilder();
            int b = readByte();
            while (isPrintableChar(b)) {
                sb.appendCodePoint(b);
                b = readByte();
            }
            return sb.toString();
        }

        public long nextLong() {
            return Long.parseLong(next());
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }

        public double nextDouble() {
            return Double.parseDouble(next());
        }
    }
}