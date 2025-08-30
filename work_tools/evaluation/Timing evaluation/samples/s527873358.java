import java.io.*;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.StringJoiner;
import java.util.StringTokenizer;
import java.util.function.Function;

public class Main {

    static int N;
    static int[] A, B;

    public static void main(String[] args) {
        FastScanner sc = new FastScanner(System.in);
        N = sc.nextInt();
        A = new int[N-1];
        B = new int[N-1];
        for (int i = 0; i < N - 1; i++) {
            A[i] = sc.nextInt()-1;
            B[i] = sc.nextInt()-1;
        }

        System.out.println(solve());
    }

    static String solve() {
        int[][] G = adjB(N, A, B);
        int[] ret = diameter(N, G);
        int d = ret[2];
        // 0: W
        // 1: L
        // 2: W

        return d % 3 == 1 ? "Second" : "First";
    }

    static int[][] adjB(int n, int[] from, int[] to) {
        int[][] adj = new int[n][];
        int[] cnt = new int[n];
        for (int f : from) {
            cnt[f]++;
        }
        for (int t : to) {
            cnt[t]++;
        }
        for (int i = 0; i < n; i++) {
            adj[i] = new int[cnt[i]];
        }
        for (int i = 0; i < from.length; i++) {
            adj[from[i]][--cnt[from[i]]] = to[i];
            adj[to[i]][--cnt[to[i]]] = from[i];
        }
        return adj;
    }

    // [片方, もう片方, diameter]
    static int[] diameter(int N, int[][] G) {
        int[][] r = maxDistance(0, N, G);
        int[][] r2 = maxDistance(r[0][1], N, G);
        return r2[0];
    }

    static int[][] maxDistance(int a, int N, int[][] G) {
        ArrayDeque<int[]> q = new ArrayDeque<>();
        int[] dist = new int[N];
        Arrays.fill(dist, -1);
        dist[a] = 0;
        q.add(new int[]{a, 0});
        while(!q.isEmpty()) {
            int[] s = q.poll();

            for (int b : G[s[0]]) {
                if( dist[b] == -1 ) {
                    q.add( new int[]{b, s[1]+1} );
                    dist[b] = s[1]+1;
                }
            }
        }

        int v = -1;
        int maxd = -1;
        for (int i = 0; i < N; i++) {
            if( dist[i] > maxd ) {
                v = i;
                maxd = dist[i];
            }
        }
        int[][] ret = new int[2][];
        ret[0] = new int[]{a, v, maxd};
        ret[1] = dist;
        return ret;
    }

    @SuppressWarnings("unused")
    static class FastScanner {
        private BufferedReader reader;
        private StringTokenizer tokenizer;

        FastScanner(InputStream in) {
            reader = new BufferedReader(new InputStreamReader(in));
            tokenizer = null;
        }

        String next() {
            if (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return tokenizer.nextToken();
        }

        String nextLine() {
            if (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    return reader.readLine();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return tokenizer.nextToken("\n");
        }

        long nextLong() {
            return Long.parseLong(next());
        }

        int nextInt() {
            return Integer.parseInt(next());
        }

        int[] nextIntArray(int n) {
            int[] a = new int[n];
            for (int i = 0; i < n; i++)
                a[i] = nextInt();
            return a;
        }

        int[] nextIntArray(int n, int delta) {
            int[] a = new int[n];
            for (int i = 0; i < n; i++)
                a[i] = nextInt() + delta;
            return a;
        }

        long[] nextLongArray(int n) {
            long[] a = new long[n];
            for (int i = 0; i < n; i++)
                a[i] = nextLong();
            return a;
        }
    }

    static <A> void writeLines(A[] as, Function<A, String> f) {
        PrintWriter pw = new PrintWriter(System.out);
        for (A a : as) {
            pw.println(f.apply(a));
        }
        pw.flush();
    }

    static void writeLines(int[] as) {
        PrintWriter pw = new PrintWriter(System.out);
        for (int a : as) pw.println(a);
        pw.flush();
    }

    static void writeLines(long[] as) {
        PrintWriter pw = new PrintWriter(System.out);
        for (long a : as) pw.println(a);
        pw.flush();
    }

    static int max(int... as) {
        int max = Integer.MIN_VALUE;
        for (int a : as) max = Math.max(a, max);
        return max;
    }

    static int min(int... as) {
        int min = Integer.MAX_VALUE;
        for (int a : as) min = Math.min(a, min);
        return min;
    }

    static void debug(Object... args) {
        StringJoiner j = new StringJoiner(" ");
        for (Object arg : args) {
            if (arg instanceof int[]) j.add(Arrays.toString((int[]) arg));
            else if (arg instanceof long[]) j.add(Arrays.toString((long[]) arg));
            else if (arg instanceof double[]) j.add(Arrays.toString((double[]) arg));
            else if (arg instanceof Object[]) j.add(Arrays.toString((Object[]) arg));
            else j.add(arg.toString());
        }
        System.err.println(j.toString());
    }
}
