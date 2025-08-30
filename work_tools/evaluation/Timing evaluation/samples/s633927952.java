import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class Main {
    static FastScanner sc = new FastScanner(System.in);
    static FastPrintStream pw = new FastPrintStream();

    public static void main(String[] args) {
        solve();
        pw.flush();
        pw.close();
    }

    public static void solve() {
        int n = sc.nextInt();
        int m = sc.nextInt();
        var scc = new SCC(n);
        for (int i = 0; i < m; i++) {
            int a = sc.nextInt();
            int b = sc.nextInt();
            scc.addEdge(a, b);
        }
        int[][] g = scc.build();
        int k = g.length;
        pw.println(k);
        for (int i = 0; i < k; i++) {
            int l = g[i].length;
            pw.print(l);
            for (int j = 0; j < l; j++) {
                pw.print(' ').print(g[i][j]);
            }
            pw.println();
        }
    }

    static class SCC {
        static class LongArrayList{
            long[] a;
            int ptr = 0;
            LongArrayList(int cap) {a = new long[cap];}
            void add(int upper, int lower) {
                if (ptr == a.length) grow();
                a[ptr++] = (long) upper << 32 | lower;
            }
            void grow() {
                long[] b = new long[a.length << 1];
                System.arraycopy(a, 0, b, 0, a.length);
                a = b;
            }
        }
        int n;
        int m;
        LongArrayList unorderedEdges;
        int[] start;
        public SCC(int n) {
            this.n = n;
            this.unorderedEdges = new LongArrayList(n);
            this.start = new int[n + 1];
        }
        public void addEdge(int from, int to) {
            unorderedEdges.add(from, to);
            start[from + 1]++;
            this.m++;
        }
        private static final long mask = 0xffff_ffffl;
        public int[][] build() {
            for (int i = 1; i <= n; i++) {
                start[i] += start[i - 1];
            }
            int[] orderedEdges = new int[m];
            int[] count = new int[n + 1];
            System.arraycopy(start, 0, count, 0, n + 1);
            for (int i = 0; i < m; i++) {
                long e = unorderedEdges.a[i];
                orderedEdges[count[(int) (e >>> 32)]++] = (int) (e & mask);
            }
            int nowOrd = 0;
            int groupNum = 0;
            int k = 0;
            int[] par = new int[n];
            int[] visited = new int[n];
            int[] low = new int[n];
            int[] ord = new int[n];
            java.util.Arrays.fill(ord, -1);
            int[] ids = new int[n];
            long[] stack = new long[n];
            int ptr = 0;
            
            for (int i = 0; i < n; i++) {
                if (ord[i] >= 0) continue;
                par[i] = -1;
                stack[ptr++] = i;
                while (ptr > 0) {
                    long p = stack[--ptr];
                    int u = (int) (p & mask);
                    int j = (int) (p >>> 32);
                    if (j == 0) {
                        low[u] = ord[u] = nowOrd++;
                        visited[k++] = u;
                    }
                    if (start[u] + j < count[u]) {
                        int to = orderedEdges[start[u] + j];
                        stack[ptr++] += 1l << 32;
                        if (ord[to] == -1) {
                            stack[ptr++] = to;
                            par[to] = u;
                        } else {
                            low[u] = Math.min(low[u], ord[to]);
                        }
                    } else {
                        while (j --> 0) {
                            int to = orderedEdges[start[u] + j];
                            if (par[to] == u) low[u] = Math.min(low[u], low[to]);
                        }
                        if (low[u] == ord[u]) {
                            while (true) {
                                int v = visited[--k];
                                ord[v] = n;
                                ids[v] = groupNum;
                                if (v == u) break;
                            }
                            groupNum++;
                        }
                    }
                }
            }
            for (int i = 0; i < n; i++) {
                ids[i] = groupNum - 1 - ids[i];
            }
            
            int[] counts = new int[groupNum];
            for (int x : ids) counts[x]++;
            int[][] groups = new int[groupNum][];
            for (int i = 0; i < groupNum; i++) {
                groups[i] = new int[counts[i]];
            }
            for (int i = 0; i < n; i++) {
                int cmp = ids[i];
                groups[cmp][--counts[cmp]] = i;
            }
            return groups;
        }
    }
    
    static class FastScanner {
        private final InputStream in;
        private final byte[] buf = new byte[1024];
        private int ptr = 0;
        private int buflen = 0;
        public FastScanner(InputStream in) {this.in = in;}
        private boolean hasNextByte() {
            if (ptr < buflen) return true;
            ptr = 0;
            try {buflen = in.read(buf);}
            catch (final IOException e) {e.printStackTrace();}
            return buflen > 0;
        }
        private int readByte() {return hasNextByte() ? buf[ptr++] : -1;}
        public boolean hasNext() {
            while (hasNextByte() && !(33 <= buf[ptr] && buf[ptr] <= 126)) ptr++;
            return hasNextByte();
        }
        private StringBuilder nextSequence() {
            if (!hasNext()) throw new NoSuchElementException();
            final StringBuilder sb = new StringBuilder();
            int b = readByte();
            while (33 <= b && b <= 126) {sb.appendCodePoint(b); b = readByte();}
            return sb;
        }
        public String next() {
            return nextSequence().toString();
        }
        public String next(int len) {
            return new String(nextChars(len));
        }
        public char[] nextChars() {
            final StringBuilder sb = nextSequence();
            int l = sb.length();
            char[] dst = new char[l];
            sb.getChars(0, l, dst, 0);
            return dst;
        }
        public char[] nextChars(int len) {
            if (!hasNext()) throw new NoSuchElementException();
            char[] s = new char[len];
            int i = 0;
            int b = readByte();
            while (33 <= b && b <= 126 && i < len) {s[i++] = (char) b; b = readByte();}
            if (i != len) throw new NoSuchElementException(String.format("length %d is longer than the sequence.", len));
            return s;
        }
        public long nextLong() {
            if (!hasNext()) throw new NoSuchElementException();
            long n = 0;
            boolean minus = false;
            int b = readByte();
            if (b == '-') {minus = true; b = readByte();}
            if (b < '0' || '9' < b) throw new NumberFormatException();
            while (true) {
                if ('0' <= b && b <= '9') n = n * 10 + b - '0';
                else if (b == -1 || !(33 <= b && b <= 126)) return minus ? -n : n;
                else throw new NumberFormatException();
                b = readByte();
            }
        }
        public int nextInt() {return Math.toIntExact(nextLong());}
        public double nextDouble() {return Double.parseDouble(next());}
    }

    static class FastPrintStream implements AutoCloseable {
        private static final int BUF_SIZE = 1 << 15;
        private final byte[] buf = new byte[BUF_SIZE];
        private int ptr = 0;
        private final java.lang.reflect.Field strField;
        private final java.nio.charset.CharsetEncoder encoder;
    
        private java.io.OutputStream out;
    
        public FastPrintStream(java.io.OutputStream out) {
            this.out = out;
            java.lang.reflect.Field f;
            try {
                f = java.lang.String.class.getDeclaredField("value");
                f.setAccessible(true);
            } catch (NoSuchFieldException | SecurityException e) {
                f = null;
            }
            this.strField = f;
            this.encoder = java.nio.charset.StandardCharsets.UTF_8.newEncoder();
        }
    
        public FastPrintStream(java.io.File file) throws java.io.IOException {
            this(new java.io.FileOutputStream(file));
        }
    
        public FastPrintStream(java.lang.String filename) throws java.io.IOException {
            this(new java.io.File(filename));
        }
    
        public FastPrintStream() {
            this(System.out);
            try {
                java.lang.reflect.Field f = java.io.PrintStream.class.getDeclaredField("autoFlush");
                f.setAccessible(true);
                f.set(System.out, false);
            } catch (IllegalAccessException | IllegalArgumentException | NoSuchFieldException e) {
                // ignore
            }
        }
    
        public FastPrintStream println() {
            if (ptr == BUF_SIZE) internalFlush();
            buf[ptr++] = (byte) '\n';
            return this;
        }
    
        public FastPrintStream println(java.lang.Object o) {
            return print(o).println();
        }
    
        public FastPrintStream println(java.lang.String s) {
            return print(s).println();
        }
    
        public FastPrintStream println(char[] s) {
            return print(s).println();
        }
    
        public FastPrintStream println(char c) {
            return print(c).println();
        }
    
        public FastPrintStream println(int x) {
            return print(x).println();
        }
    
        public FastPrintStream println(long x) {
            return print(x).println();
        }
    
        public FastPrintStream println(double d, int precision) {
            return print(d, precision).println();
        }
    
        private FastPrintStream print(byte[] bytes) {
            int n = bytes.length;
            if (ptr + n > BUF_SIZE) {
                internalFlush();
                try {
                    out.write(bytes);
                } catch (java.io.IOException e) {
                    throw new RuntimeException();
                }
            } else {
                System.arraycopy(bytes, 0, buf, ptr, n);
                ptr += n;
            }
            return this;
        }
    
        public FastPrintStream print(java.lang.Object o) {
            return print(o.toString());
        }
    
        public FastPrintStream print(java.lang.String s) {
            if (strField == null) {
                return print(s.getBytes());
            } else {
                try {
                    return print((byte[]) strField.get(s));
                } catch (IllegalAccessException e) {
                    return print(s.getBytes());
                }
            }
        }
    
        public FastPrintStream print(char[] s) {
            try {
                return print(encoder.encode(java.nio.CharBuffer.wrap(s)).array());
            } catch (java.nio.charset.CharacterCodingException e) {
                byte[] bytes = new byte[s.length];
                for (int i = 0; 9 < s.length; i++) {
                    bytes[i] = (byte) s[i];
                }
                return print(bytes);
            }
        }
    
        public FastPrintStream print(char c) {
            if (ptr == BUF_SIZE) internalFlush();
            buf[ptr++] = (byte) c;
            return this;
        }
    
        public FastPrintStream print(int x) {
            if (x == 0) {
                if (ptr == BUF_SIZE) internalFlush();
                buf[ptr++] = '0';
                return this;
            }
            int d = len(x);
            if (ptr + d > BUF_SIZE) internalFlush();
            if (x < 0) {
                buf[ptr++] = '-';
                x = -x;
                d--;
            }
            int j = ptr += d; 
            while (x > 0) {
                buf[--j] = (byte) ('0' + (x % 10));
                x /= 10;
            }
            return this;
        }
    
        public FastPrintStream print(long x) {
            if (x == 0) {
                if (ptr == BUF_SIZE) internalFlush();
                buf[ptr++] = '0';
                return this;
            }
            int d = len(x);
            if (ptr + d > BUF_SIZE) internalFlush();
            if (x < 0) {
                buf[ptr++] = '-';
                x = -x;
                d--;
            }
            int j = ptr += d; 
            while (x > 0) {
                buf[--j] = (byte) ('0' + (x % 10));
                x /= 10;
            }
            return this;
        }
    
        public FastPrintStream print(double d, int precision) {
            if (d < 0) {
                print('-');
                d = -d;
            }
            d += Math.pow(10, -d) / 2;
            print((long) d).print('.');
            d -= (long) d;
            for(int i = 0; i < precision; i++){
                d *= 10;
                print((int) d);
                d -= (int) d;
            }
            return this;
        }
    
        private void internalFlush() {
            try {
                out.write(buf, 0, ptr);
                ptr = 0;
            } catch (java.io.IOException e) {
                throw new RuntimeException(e);
            }
        }
    
        public void flush() {
            try {
                out.write(buf, 0, ptr);
                out.flush();
                ptr = 0;
            } catch (java.io.IOException e) {
                throw new RuntimeException(e);
            }
        }
    
        public void close() {
            try {
                out.close();
            } catch (java.io.IOException e) {
                throw new RuntimeException(e);
            }
        }
    
        private static int len(int x) {
            int d = 1;
            if (x >= 0) {d = 0; x = -x;}
            int p = -10;
            for (int i = 1; i < 10; i++, p *= 10) if (x > p) return i + d;
            return 10 + d;
        }
    
        private static int len(long x) {
            int d = 1;
            if (x >= 0) {d = 0; x = -x;}
            long p = -10;
            for (int i = 1; i < 19; i++, p *= 10) if (x > p) return i + d;
            return 19 + d;
        }
    }

    static int[] nextInts(int n) {
        var dat = new int[n];
        Arrays.setAll(dat, i -> sc.nextInt());
        return dat;
    }

    static long[] nextLongs(int n) {
        var dat = new long[n];
        Arrays.setAll(dat, i -> sc.nextLong());
        return dat;
    }
}
