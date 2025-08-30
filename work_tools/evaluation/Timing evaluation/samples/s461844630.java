import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        new Main().solve();
    }

    private void solve() throws Exception{
        FastScanner scanner = new FastScanner(System.in);

        int V = scanner.nextInt();
        int E = scanner.nextInt();

        int[] parent = new int[V];
        for(int i = 0; i < V; ++i){
            parent[i] = i;
        }

        PriorityQueue<Edge> queue = new PriorityQueue<>(Math.max(1, E), new Comparator<Edge>() {
            @Override
            public int compare(Edge o1, Edge o2) {
                return o1.weight - o2.weight;
            }
        });

        for(int i = 0; i < E; ++i){
            int u = scanner.nextInt();
            int v = scanner.nextInt();
            int w = scanner.nextInt();

            queue.add(new Edge(u, v, w));
        }

        int count = 0;
        int total = 0;
        while(!queue.isEmpty()){
            Edge edge = queue.poll();
            if(union(parent, edge.u, edge.v)){
                ++count;
                total += edge.weight;
                if(count == V - 1){
                    break;
                }
            }
        }

        System.out.println(total);
    }

    private int find(int[] parent, int x){
        int father = parent[x];
        while(father != parent[father]){
            father = parent[father];
        }

        int fa = x;
        while(fa != parent[fa]){
            int temp = parent[fa];
            parent[fa] = father;
            fa = temp;
        }

        return father;
    }

    private boolean union(int[] parent, int x, int y){
        int faX = find(parent, x);
        int faY = find(parent, y);

        if(faX == faY){
            return false;
        }

        parent[faX] = faY;

        return true;
    }

    static class Edge{
        int u;
        int v;
        int weight;

        public Edge(int u, int v, int weight){
            this.u = u;
            this.v = v;
            this.weight = weight;
        }
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