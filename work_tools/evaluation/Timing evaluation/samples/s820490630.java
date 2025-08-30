import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.RandomAccess;
import java.util.function.IntUnaryOperator;
import java.util.function.LongUnaryOperator;

public class Main {
    public static void main(String[] args) throws Exception {
        ExtendedScanner sc = new ExtendedScanner();
        FastPrintStream pw = new FastPrintStream();
        solve(sc, pw);
        sc.close();
        pw.flush();
        pw.close();
    }

    public static void solve(ExtendedScanner sc, FastPrintStream pw) {
        int n = sc.nextInt();
        long c = sc.nextLong();
        long[] h = sc.longs(n);
        long[] dp = new long[n];
        MonotoneConvexHullTrick cht = new MonotoneConvexHullTrick(true, n);
        cht.addLine(-2 * h[n - 1], h[n - 1] * h[n - 1] + dp[n - 1]);
        for (int i = n - 1; i >= 0; i--) {
            dp[i] = cht.decreasingQuery(h[i]) + h[i] * h[i] + c;
            cht.addLine(-2 * h[i], h[i] * h[i] + dp[i]);
        }
        pw.println(dp[0]);
    }
}


/**
 * @author https://atcoder.jp/users/suisen
 * 
 * Implementation of deque for generic type, using Ring Buffer.
 */
@SuppressWarnings("unchecked")
class Deque<T> implements Iterable<T>, RandomAccess {
    static final int DEFAULT_CAPACITY = 1 << 6;
    T[] buf;
    int len = 1;
    int mask;
    int head = 0;
    int tail = 0;
    public Deque(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException(
                String.format("Capacity %d is negative.", capacity)
            );
        }
        while (this.len < capacity) {
            this.len <<= 1;
        }
        this.mask = this.len - 1;
        this.buf = (T[]) new Object[len];
    }
    public Deque() {
        this(DEFAULT_CAPACITY);
    }
    public T getLast() {
        if (size() == 0) throw new NoSuchElementException();
        return buf[(tail - 1) & mask];
    }
    public T getFirst() {
        if (size() == 0) throw new NoSuchElementException();
        return buf[head & mask];
    }
    public T get(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException(
                String.format("Index %d out of bounds for length %d.", index, size())
            );
        }
        return buf[(head + index) & mask];
    }
    public void addLast(T v) {
        if (size() == len) grow();
        buf[tail++ & mask] = v;
    }
    public void addFirst(T v) {
        if (size() == len) grow();
        buf[--head & mask] = v;
    }
    public T removeLast() {
        if (size() == 0) throw new NoSuchElementException();
        return buf[--tail & mask];
    }
    public T removeFirst() {
        if (size() == 0) throw new NoSuchElementException();
        return buf[head++ & mask];
    }
    public T pollLast() {
        if (size() == 0) return null;
        return removeLast();
    }
    public T pollFirst() {
        if (size() == 0) return null;
        return removeFirst();
    }
    public int size() {
        return tail - head;
    }
    public boolean isEmpty() {
        return size() == 0;
    }
    public void clear() {
        head = tail = 0;
    }
    public T[] toArray(Class<T> clazz) {
        T[] ret = (T[]) Array.newInstance(clazz, size());
        Iterator<T> it = iterator();
        Arrays.setAll(ret, i -> it.next());
        return ret;
    }
    private void grow() {
        T[] newBuf = (T[]) new Object[len << 1];
        head &= mask;
        tail &= mask;
        int len1 = len - head;
        int len2 = head;
        System.arraycopy(buf, head, newBuf, 0, len1);
        System.arraycopy(buf, 0, newBuf, len1, len2);
        this.head = 0;
        this.tail = this.len;
        this.len <<= 1;
        this.mask = this.len - 1;
        this.buf = newBuf;
    }
    public Iterator<T> iterator() {
        return new Iterator<T>(){
            int it = head;
            public boolean hasNext() {return it < tail;}
            public T next() {return buf[it++ & mask];}
        };
    }
    public Iterator<T> descendingIterator() {
        return new Iterator<T>(){
            int it = tail;
            public boolean hasNext() {return it > head;}
            public T next() {return buf[--it & mask];}
        };
    }

    /***************************** DEBUG *********************************/

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        Iterator<T> it = iterator();
        while (it.hasNext()) {
            sb.append(it.next().toString());
            if (it.hasNext()) sb.append(',');
        }
        sb.append(']');
        return sb.toString();
    }
}


class MonotoneConvexHullTrick {
    private static final class Func {
        private final long a, b;
        private Func(long a, long b) {
            this.a = a; this.b = b;
        }
        public long f(long x) {
            return a * x + b;
        }
    }
    private final Deque<Func> lines;
    private final boolean isMinQuery;
    public MonotoneConvexHullTrick(boolean isMinQuery) {
        this.isMinQuery = isMinQuery;
        this.lines = new Deque<>();
    }
    public MonotoneConvexHullTrick(boolean isMinQuery, int lineNum) {
        this.isMinQuery = isMinQuery;
        this.lines = new Deque<>(lineNum);
    }
    public void addLine(long a, long b) {
        if (!isMinQuery) {
            a = -a; b = -b;
        }
        if (lines.size() == 0) {
            lines.addLast(new Func(a, b));
            return;
        }
        Func last = lines.getLast();
        Func first = lines.getFirst();
        Func f = new Func(a, b);
        if (a <= last.a) {
            if (a < last.a || b < last.b) addLast(f);
        } else if (a >= first.a) {
            if (a > first.a || b > first.b) addFirst(f);
        } else {
            throw new IllegalArgumentException("Not monotone.");
        }
    }
    private void addLast(Func f) {
        for (int size = lines.size(); size >= 2; size--) {
            Func lf = lines.get(size - 2);
            Func mf = lines.get(size - 1);
            if (!noneed(lf, mf, f)) break;
            lines.removeLast();
        }
        lines.addLast(f);
    }
    private void addFirst(Func f) {
        for (int size = lines.size(); size >= 2; size--) {
            Func mf = lines.get(0);
            Func rf = lines.get(1);
            if (!noneed(f, mf, rf)) break;
            lines.removeFirst();
        }
        lines.addFirst(f);
    }
    private static boolean noneed(Func lf, Func mf, Func rf) {
        return (rf.a - mf.a) * (lf.b - mf.b) >= (mf.a - lf.a) * (mf.b - rf.b);
    }
    private boolean incrFlg = false;
    private boolean decrFlg = false;
    public long query(long x) {
        if (incrFlg || decrFlg) throw new UnsupportedOperationException();
        if (lines.size() == 0) return isMinQuery ? Const.LINF : -Const.LINF;
        int l = -1, r = lines.size() - 1;
        while (r - l > 1) {
            int m = (l + r) >> 1;
            if (lines.get(m).f(x) > lines.get(m + 1).f(x)) l = m;
            else r = m;
        }
        long y = lines.get(r).f(x);
        return isMinQuery ? y : -y;
    }
    public long increasingQuery(long x) {
        if (decrFlg) throw new UnsupportedOperationException();
        incrFlg = true;
        if (lines.size() == 0) return isMinQuery ? Const.LINF : -Const.LINF;
        for (int size = lines.size(); size >= 2; size--) {
            Func fl = lines.get(0);
            Func fr = lines.get(1);
            if (fl.f(x) > fr.f(x)) lines.removeFirst();
            else break;
        }
        long y = lines.getFirst().f(x);
        return isMinQuery ? y : -y;
    }
    public long decreasingQuery(long x) {
        if (incrFlg) throw new UnsupportedOperationException();
        decrFlg = true;
        if (lines.size() == 0) return isMinQuery ? Const.LINF : -Const.LINF;
        for (int size = lines.size(); size >= 2; size--) {
            Func fl = lines.get(size - 2);
            Func fr = lines.get(size - 1);
            if (fl.f(x) < fr.f(x)) lines.removeLast();
            else break;
        }
        long y = lines.getLast().f(x);
        return isMinQuery ? y : -y;
    }
}

/**
 * @author https://atcoder.jp/users/suisen
 */
class FastScanner implements AutoCloseable {
    private final java.io.InputStream in;
    private final byte[] buf = new byte[2048];
    private int ptr = 0;
    private int buflen = 0;

    public FastScanner(java.io.InputStream in) {
        this.in = in;
    }

    public FastScanner() {
        this(System.in);
    }

    private boolean hasNextByte() {
        if (ptr < buflen) return true;
        ptr = 0;
        try {
            buflen = in.read(buf);
        } catch (java.io.IOException e) {
            throw new RuntimeException(e);
        }
        return buflen > 0;
    }

    private int readByte() {
        return hasNextByte() ? buf[ptr++] : -1;
    }

    public boolean hasNext() {
        while (hasNextByte() && !(32 < buf[ptr] && buf[ptr] < 127)) ptr++;
        return hasNextByte();
    }

    private StringBuilder nextSequence() {
        if (!hasNext()) throw new java.util.NoSuchElementException();
        StringBuilder sb = new StringBuilder();
        for (int b = readByte(); 32 < b && b < 127; b = readByte()) {
            sb.appendCodePoint(b);
        }
        return sb;
    }

    public String next() {
        return nextSequence().toString();
    }

    public String next(int len) {
        return new String(nextChars(len));
    }

    public char nextChar() {
        if (!hasNextByte()) throw new java.util.NoSuchElementException();
        return (char) readByte();
    }

    public char[] nextChars() {
        StringBuilder sb = nextSequence();
        int l = sb.length();
        char[] dst = new char[l];
        sb.getChars(0, l, dst, 0);
        return dst;
    }
    public char[] nextChars(int len) {
        if (!hasNext()) throw new java.util.NoSuchElementException();
        char[] s = new char[len];
        int i = 0;
        int b = readByte();
        while (32 < b && b < 127 && i < len) {
            s[i++] = (char) b; b = readByte();
        }
        if (i != len) {
            throw new java.util.NoSuchElementException(
                String.format("Next token has smaller length than expected.", len)
            );
        }
        return s;
    }
    public long nextLong() {
        if (!hasNext()) throw new java.util.NoSuchElementException();
        long n = 0;
        boolean minus = false;
        int b = readByte();
        if (b == '-') {
            minus = true;
            b = readByte();
        }
        if (b < '0' || '9' < b) throw new NumberFormatException();
        while (true) {
            if ('0' <= b && b <= '9') {
                n = n * 10 + b - '0';
            } else if (b == -1 || !(32 < b && b < 127)) {
                return minus ? -n : n;
            } else throw new NumberFormatException();
            b = readByte();
        }
    }
    public int nextInt() {
        return Math.toIntExact(nextLong());
    }
    public double nextDouble() {
        return Double.parseDouble(next());
    }
    public void close() {
        try {
            in.close();
        } catch (java.io.IOException e) {
            throw new RuntimeException(e);
        }
    }
}


/**
 * @author https://atcoder.jp/users/suisen
 */
final class ExtendedScanner extends FastScanner {
    public ExtendedScanner() {super();}
    public ExtendedScanner(InputStream in) {super(in);}
    public int[] ints(final int n) {
        final int[] a = new int[n];
        Arrays.setAll(a, $ -> nextInt());
        return a;
    }
    public int[] ints(final int n, final IntUnaryOperator f) {
        final int[] a = new int[n];
        Arrays.setAll(a, $ -> f.applyAsInt(nextInt()));
        return a;
    }
    public int[][] ints(final int n, final int m) {
        final int[][] a = new int[n][];
        Arrays.setAll(a, $ -> ints(m));
        return a;
    }
    public int[][] ints(final int n, final int m, final IntUnaryOperator f) {
        final int[][] a = new int[n][];
        Arrays.setAll(a, $ -> ints(m, f));
        return a;
    }
    public long[] longs(final int n) {
        final long[] a = new long[n];
        Arrays.setAll(a, $ -> nextLong());
        return a;
    }
    public long[] longs(final int n, final LongUnaryOperator f) {
        final long[] a = new long[n];
        Arrays.setAll(a, $ -> f.applyAsLong(nextLong()));
        return a;
    }
    public long[][] longs(final int n, final int m) {
        final long[][] a = new long[n][];
        Arrays.setAll(a, $ -> longs(m));
        return a;
    }
    public long[][] longs(final int n, final int m, final LongUnaryOperator f) {
        final long[][] a = new long[n][];
        Arrays.setAll(a, $ -> longs(m, f));
        return a;
    }
    public char[][] charArrays(final int n) {
        final char[][] c = new char[n][];
        Arrays.setAll(c, $ -> nextChars());
        return c;
    }
    public char[][] charArrays(final int n, final int m) {
        final char[][] c = new char[n][];
        Arrays.setAll(c, $ -> nextChars(m));
        return c;
    }
    public double[] doubles(final int n) {
        final double[] a = new double[n];
        Arrays.setAll(a, $ -> nextDouble());
        return a;
    }
    public double[][] doubles(final int n, final int m) {
        final double[][] a = new double[n][];
        Arrays.setAll(a, $ -> doubles(m));
        return a;
    }
    public String[] strings(final int n) {
        final String[] s = new String[n];
        Arrays.setAll(s, $ -> next());
        return s;
    }
    public String[] strings(final int n, final int m) {
        final String[] s = new String[n];
        Arrays.setAll(s, $ -> next(m));
        return s;
    }
}

/**
 * @author https://atcoder.jp/users/suisen
 */
class FastPrintStream implements AutoCloseable {
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
        this.encoder = java.nio.charset.StandardCharsets.US_ASCII.newEncoder();
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
                Object value = strField.get(s);
                if (value instanceof byte[]) {
                    return print((byte[]) value);
                } else {
                    return print((char[]) value);
                }
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
            for (int i = 0; i < s.length; i++) {
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
        d += Math.pow(10, -precision) / 2;
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

class Const {
    public static final long   LINF   = 1l << 59;
    public static final int    IINF   = (1  << 30) - 1;
    public static final double DINF   = 1e150;
    
    public static final double SMALL  = 1e-12;
    public static final double MEDIUM = 1e-9;
    public static final double LARGE  = 1e-6;

    public static final long MOD1000000007 = 1000000007;
    public static final long MOD998244353  = 998244353 ;
    public static final long MOD754974721  = 754974721 ;
    public static final long MOD167772161  = 167772161 ;
    public static final long MOD469762049  = 469762049 ;
}
