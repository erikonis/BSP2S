import static java.lang.Math.*;
// import java.math.BigInteger;
import java.io.*;
import java.util.*;

// solution code is in solve()

public class Main {
    final static long mod = 1_000_000_007l;
    final static int maxv = 2_000_07;
    final static long infty = 1_000_000_00009l  ;

    // final static boolean DEBUG = true;
    final static boolean DEBUG = false;

    static class Solver {

        long[] fact, facti;

        public long paths(int x1, int y1, int x2, int y2) {
            int a = x2 - x1, b = y2 - y1;
            if (a < 0 || b < 0) return 0l;
            return (((fact[a + b] * facti[a]) % mod) * facti[b]) % mod;
        }

        IntPair[] blocks;
        long[] dp;

        public void solve() {
            fact = new long[maxv];
            facti = new long[maxv];
            fact[0] = facti[0] = 1l;
            for (int j = 1; j < maxv; j++) {
                fact[j] = (fact[j - 1] * j) % mod;
                facti[j] = modexp(fact[j], mod - 2);
            }

            int H = in.gi(), W = in.gi(), n = in.gi();
            
            blocks = new IntPair[n];
            dp = new long[n];

            for (int j = 0; j < n; j++) {
                blocks[j] = new IntPair(in.gi(), in.gi());
            } 

            Arrays.sort(blocks, (b1, b2) -> Integer.valueOf(b1.F + b1.S).compareTo(b2.F + b2.S));

            for (int j = n - 1; j >= 0; j--) {
                int curx = blocks[j].F, cury = blocks[j].S;
                long cpaths = paths(curx, cury, H, W);

                for (int t = j + 1; t < n; t++) {
                    int ox = blocks[t].F, oy = blocks[t].S;
                    long opath = paths(curx, cury, ox, oy) * dp[t];
                    opath %= mod;

                    cpaths = (cpaths - opath + mod) % mod;
                }

                dp[j] = cpaths;
            }

            long ans = paths(1, 1, H, W);
            for (int j = 0; j < n; j++) {
                long cpath = paths(1, 1, blocks[j].F, blocks[j].S) * dp[j];
                cpath %= mod;
                ans = (ans + mod - cpath) % mod;
            }

            println(ans);
        }   


        PrintWriter out;
        FastInput in;
        Random random_source;

        long modexp(long x, long ex) {
            long ans = 1l;
            for (; ex > 0; ex >>= 1l, x = (x * x) % mod) {
                if ((ex & (1l)) > 0) ans = (ans * x) % mod;
            }
            return ans;
        }

        long gcd(long x, long y) {
            while (y > 0) {
                long t = x % y;
                x = y; y = t;
            }
            return x;
        }

        // Random numbers

        void initRandom(Long x) {
            random_source = new Random(x);
        }

        void initRandom() {
            random_source = new Random(System.currentTimeMillis());
        }

        int rand(int bound) { // [0..bound)
            return random_source.nextInt(bound);
        }

        // Convenience functions

        void debug(Object... obj) {
            if (DEBUG) {
                out.print("# ");
                for (Object o : obj) {
                    out.print(o.toString());
                    out.print(" ");
                }
                out.println("");
                out.flush();
            }
        }

       
        String a2s(Object[] A) {
            return Arrays.toString(A);
        }

        String a2s(int[] A) {
            return Arrays.toString(A);
        }

        String a2s(long[] A) {
            return Arrays.toString(A);
        }


        void flush() {
            out.flush();
        }

        void print(Object... obj) {
            for (Object o : obj) {
                out.print(o.toString());
            }
        }

        void println(Object... obj) {
            for (Object o : obj) {
                out.print(o.toString());
            }
            out.println("");
        } 

        void verify(boolean check) {    // equivalent to assert
            if (!check) {
                throw new RuntimeException("Verification error");
            }
        }

        void reverse(long[] A, int l, int r) {
            int i = l, j = r - 1;
            while (i < j) {
                long t = A[i];
                A[i] = A[j];
                A[j] = t;
                i++; j--;
            }
        }

        void reverse(int[] A, int l, int r) {
            int i = l, j = r - 1;
            while (i < j) {
                int t = A[i];
                A[i] = A[j];
                A[j] = t;
                i++; j--;
            }
        }

        // Measure time 

        long initTime;

        public void startWatch() {
            if (DEBUG) {
                initTime = System.nanoTime();
            }
        }

        public void endWatch() {
            if (DEBUG) {
                long current = System.nanoTime();
                long diff = current - initTime;
                long memory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
                println("(Time: ", (double)diff / 1e9, ", Memory: ", memory/1000, "K)");            
            }
        }

        // Solver class constructor

        Solver(PrintWriter out, FastInput in) {
            this.out = out; this.in = in;
        }
    }

    public static void main(String args[]) {
        FastInput in = new FastInput(System.in);
        PrintWriter out = new PrintWriter(System.out);
        Solver solver = new Solver(out, in);
        solver.startWatch();
        solver.solve();
        solver.endWatch();
        out.close();
    }
    
    static class FastInput {
        BufferedReader reader;
        StringTokenizer tok;

        public FastInput(InputStream stream) {
            reader = new BufferedReader(new InputStreamReader(stream));
            tok = null;
        }

        public String next() {
            while (tok == null || !tok.hasMoreTokens()) {
                try {
                    tok = new StringTokenizer(reader.readLine());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return tok.nextToken();
        }

        public int gi() {
            return Integer.parseInt(next());
        }

        public long gl() {
            return Long.parseLong(next());
        }
    }

    static class Pair<A extends Comparable<A>, B extends Comparable<B>>
            implements Comparable<Pair<A, B>> {
        A F;
        B S;

        Pair(A x, B y) {
            F = x; S = y;
        }

        public boolean equals(Pair<A, B> oth) {
            return this.compareTo(oth) == 0;    
        }

        public int compareTo(Pair<A, B> Q) {
            if (this.F.compareTo(Q.F) == 0) {
                return this.S.compareTo(Q.S);
            } else {
                return this.F.compareTo(Q.F);
            }
        }

        public String toString() {
            return new StringBuilder("{")
                    .append(F.toString())
                    .append(", ")
                    .append(S.toString())
                    .append("}")
                    .toString();
        }
    }

    public static <A extends Comparable<A>, B extends Comparable<B>> Pair<A, B> make_pair(A a, B b) {
        return new Pair<A, B>(a, b);
    }

    static class IntPair extends Pair<Integer, Integer> {
        IntPair(Integer x, Integer y) {
            super(x, y);
        }
    }

    static class LongPair extends Pair<Long, Long> {
        LongPair(Long x, Long y) {
            super(x, y);
        }
    }
}
