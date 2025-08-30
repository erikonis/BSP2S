import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.function.IntPredicate;

public class Main {

    static int N;
    static int[] A;

    public static void main(String[] args) {
        FastScanner sc = new FastScanner(System.in);
        N = sc.nextInt();
        A = sc.nextIntArray(N);

        System.out.println( solve() );
    }

    static int solve() {
        if( N == 1 ) return A[0];

        long H = (long)N * (N+1) / 2 / 2;

        return binarySearch(1, 1_000_000_001, x -> count(x) >= H);
    }

    static int binarySearch(int lo, int hi, IntPredicate p) {
        while(lo < hi) {
            int x = ((hi - lo) >>> 1) + lo;
            if( p.test(x) ) {
                lo = x + 1;
            } else {
                hi = x;
            }
        }
        return lo-1;
    }

    static long count(int x) {
        boolean[] gtEq = new boolean[N];
        for (int i = 0; i < N; i++) {
            gtEq[i] = A[i] >= x;
        }

        int[] c = new int[N];
        c[0] = (gtEq[0] ? 1 : -1);
        for (int i = 1; i < N; i++) {
            c[i] = c[i-1] + (gtEq[i] ? 1 : -1);
        }

        // [-100000, 100000] -> [1, 200001]
        BinaryIndexedTree bit = new BinaryIndexedTree(N*2+1);
        long ret = 0;
        for (int i = 0; i < N; i++) {
            int c_bit = c[i]+N+1;
            // [0, i]
            if( c[i] >= 0 ) ret++;

            // [x, i] = [0, i] - [0, x-1]
            ret += bit.sum(c_bit);

            bit.add(c_bit, 1);
        }
        return ret;
    }

    static class BinaryIndexedTree {

        int n;
        int[] bit;

        BinaryIndexedTree(int n) {
            this.n = n;
            this.bit = new int[n+1];
        }

        void add(int i, int v) {
            while( i <= n ) {
                bit[i] += v;
                i += i & -i;
            }
        }

        int sum(int i) {
            int ret = 0;
            while(i > 0) {
                ret += bit[i];
                i -= i & -i;
            }
            return ret;
        }
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

        long[] nextLongArray(int n) {
            long[] a = new long[n];
            for (int i = 0; i < n; i++)
                a[i] = nextLong();
            return a;
        }
    }
}
