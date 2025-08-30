import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.util.InputMismatchException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.Writer;
import java.io.OutputStreamWriter;
import java.io.InputStream;

/**
 * Built using CHelper plug-in
 * Actual solution is at the top
 */
public class Main {
    public static void main(String[] args) {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        InputReader in = new InputReader(inputStream);
        OutputWriter out = new OutputWriter(outputStream);
        BFusingSlimes solver = new BFusingSlimes();
        solver.solve(1, in, out);
        out.close();
    }

    static class BFusingSlimes {
        public void solve(int testNumber, InputReader in, OutputWriter out) {
            int n = in.readInt();
            long[] x = in.readLongArray(n);
            Modulo.mod = MiscUtils.MOD7;

            long[] dp = new long[n - 1];
            long tmp2 = 0;
            for (int i = 0; i < n - 1; i++) {
                tmp2 = Modulo.add(tmp2, Modulo.div(1, i + 1));
                dp[i] = tmp2;
            }

            long sum = 0;
            for (int i = 0; i < n - 1; i++) {
                long diff = x[i + 1] - x[i];
                sum = Modulo.add(sum, Modulo.mlt(diff, dp[i]));
            }
            for (int i = 1; i < n; i++) {
                sum = Modulo.mlt(sum, i);
            }
            out.printLine(sum);
        }

    }

    static class Modulo {
        public static long mod;
        private long value;
        public static Map<Long, List<Long>> pows = new HashMap<>();

        public Modulo(long value) {
            this.value = (value % mod + mod) % mod;
        }

        public static long add(long a, long b) {
            return ((a + b) % mod + mod) % mod;
        }

        public static long mlt(long a, long b) {
            return ((a % mod + mod) * (b % mod + mod)) % mod;
        }

        public static long rev(long a) {
            return pow(a, mod - 2);
        }

        public static long div(long a, long b) {
            return mlt(a, rev(b));
        }

        public static long pow(long a, long x) {
            if (pows.containsKey(a) && x < pows.get(a).size()) return pows.get(a).get((int) x);
            long ans = 1 % mod;
            while (x > 0) {
                if ((x & 1) != 0) ans = (ans * a) % mod;
                x >>= 1;
                a = (a * a) % mod;
            }
            return ans;
        }

        public String toString() {
            return "" + value;
        }

    }

    static class OutputWriter {
        private final PrintWriter writer;

        public OutputWriter(OutputStream outputStream) {
            writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream)));
        }

        public OutputWriter(Writer writer) {
            this.writer = new PrintWriter(writer);
        }

        public void close() {
            writer.close();
        }

        public void printLine(long i) {
            writer.println(i);
        }

    }

    static class InputReader {
        private InputStream stream;
        private byte[] buf = new byte[1024];
        private int curChar;
        private int numChars;
        private InputReader.SpaceCharFilter filter;

        public InputReader(InputStream stream) {
            this.stream = stream;
        }

        public long[] readLongArray(int size) {
            long[] array = new long[size];
            for (int i = 0; i < size; i++) {
                array[i] = readLong();
            }
            return array;
        }

        public int read() {
            if (numChars == -1) {
                throw new InputMismatchException();
            }
            if (curChar >= numChars) {
                curChar = 0;
                try {
                    numChars = stream.read(buf);
                } catch (IOException e) {
                    throw new InputMismatchException();
                }
                if (numChars <= 0) {
                    return -1;
                }
            }
            return buf[curChar++];
        }

        public int readInt() {
            int c = read();
            while (isSpaceChar(c)) {
                c = read();
            }
            int sgn = 1;
            if (c == '-') {
                sgn = -1;
                c = read();
            }
            int res = 0;
            do {
                if (c < '0' || c > '9') {
                    throw new InputMismatchException();
                }
                res *= 10;
                res += c - '0';
                c = read();
            } while (!isSpaceChar(c));
            return res * sgn;
        }

        public long readLong() {
            int c = read();
            while (isSpaceChar(c)) {
                c = read();
            }
            int sgn = 1;
            if (c == '-') {
                sgn = -1;
                c = read();
            }
            long res = 0;
            do {
                if (c < '0' || c > '9') {
                    throw new InputMismatchException();
                }
                res *= 10;
                res += c - '0';
                c = read();
            } while (!isSpaceChar(c));
            return res * sgn;
        }

        public boolean isSpaceChar(int c) {
            if (filter != null) {
                return filter.isSpaceChar(c);
            }
            return isWhitespace(c);
        }

        public static boolean isWhitespace(int c) {
            return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
        }

        public interface SpaceCharFilter {
            public boolean isSpaceChar(int ch);

        }

    }

    static class MiscUtils {
        public static final int MOD7 = (int) (1e9 + 7);

    }
}

