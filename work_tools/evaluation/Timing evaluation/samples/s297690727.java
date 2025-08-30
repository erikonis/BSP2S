import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.io.BufferedWriter;
import java.io.Writer;
import java.io.OutputStreamWriter;
import java.util.InputMismatchException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Built using CHelper plug-in
 * Actual solution is at the top
 *
 * @author prakharjain
 */
public class Main {
    public static void main(String[] args) {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        InputReader in = new InputReader(inputStream);
        OutputWriter out = new OutputWriter(outputStream);
        YGrid2 solver = new YGrid2();
        solver.solve(1, in, out);
        out.close();
    }

    static class YGrid2 {
        int mod = 1000000007;
        int max = 200000;
        long[] fac = new long[max + 1];
        long[] ifac = new long[max + 1];

        public void solve(int testNumber, InputReader in, OutputWriter out) {
            int h = in.nextInt();
            int w = in.nextInt();

            int n = in.nextInt();

            cell[] cells = new cell[n];

            for (int i = 0; i < n; i++) {
                cells[i] = new cell(in.nextInt(), in.nextInt());
            }

            Arrays.sort(cells, (c1, c2) ->
            {
                if (c1.x <= c2.x && c1.y <= c2.y) {
                    return -1;
                } else if (c1.x >= c2.x && c1.y >= c2.y) {
                    return 1;
                } else {
                    return c1.x - c2.x;
                }
            });

            fac[0] = 1;
            ifac[0] = 1;

            for (int i = 1; i <= max; i++) {
                fac[i] = i * fac[i - 1];
                fac[i] %= mod;

                ifac[i] = pow(i, mod - 2, mod) * ifac[i - 1];
                ifac[i] %= mod;
            }

            long[] dp = new long[n];

            for (int i = 0; i < n; i++) {
                cell cc = cells[i];
                int x = cc.x;
                int y = cc.y;

                dp[i] = nCr(x + y - 2, x - 1, mod);

                for (int j = 0; j < i; j++) {
                    cell oc = cells[j];

                    int ox = oc.x;
                    int oy = oc.y;

                    if (ox <= x && oy <= y) {
                        long sub = dp[j];
                        sub *= nCr(x - ox + y - oy, x - ox, mod);
                        sub %= mod;

                        dp[i] -= sub;
                        dp[i] %= mod;
                    }
                }
            }

            long ans = nCr(h + w - 2, h - 1, mod);

            int x = h;
            int y = w;
            for (int j = 0; j < n; j++) {
                cell oc = cells[j];

                int ox = oc.x;
                int oy = oc.y;

                long sub = dp[j];
                sub *= nCr(x - ox + y - oy, x - ox, mod);
                sub %= mod;

                ans -= sub;
                ans %= mod;
            }

            if (ans < 0)
                ans += mod;

            out.println(ans);
        }

        long pow(long a, long p, int mod) {
            if (p == 0) {
                return 1;
            }

            long t = pow(a, p / 2, mod);

            if (p % 2 != 0) {
                return (((t * t) % mod) * a) % mod;
            } else {
                return (t * t) % mod;
            }
        }

        long nCr(int n, int r, int mod) {
            long ans = fac[n];
            ans *= ifac[n - r];
            ans %= mod;
            ans *= ifac[r];
            ans %= mod;

            return ans;
        }

        class cell {
            int x;
            int y;

            public cell(int x, int y) {
                this.x = x;
                this.y = y;
            }

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

        public static boolean isWhitespace(int c) {
            return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
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

        public int nextInt() {
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

        public boolean isSpaceChar(int c) {
            if (filter != null) {
                return filter.isSpaceChar(c);
            }
            return isWhitespace(c);
        }

        public interface SpaceCharFilter {
            public boolean isSpaceChar(int ch);

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

        public void println(long i) {
            writer.println(i);
        }

    }
}

