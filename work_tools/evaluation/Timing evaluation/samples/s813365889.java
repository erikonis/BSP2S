import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.io.*;
import java.util.Arrays;
import java.util.StringJoiner;
import java.util.StringTokenizer;

public class Main {

    static int N;
    static double[] X, Y;

    public static void main(String[] args) {
        FastScanner sc = new FastScanner(System.in);
        PrintWriter pw = new PrintWriter(System.out);
        while(true) {
            N = sc.nextInt();
            if( N == 0 ) break;

            X = new double[N];
            Y = new double[N];
            for (int i = 0; i < N; i++) {
                X[i] = Double.parseDouble(sc.next());
                Y[i] = Double.parseDouble(sc.next());
            }
            pw.println(solve());
        }
        pw.flush();
    }

    static int solve() {
        int ans = 1;
        for (int i = 0; i < N-1; i++) {
            for (int j = i+1; j < N; j++) {

                double dx = X[i] - X[j];
                double dy = Y[i] - Y[j];
                double dd = dx * dx + dy * dy;
                if( dd > 4 ) continue;

                double d = Math.sqrt(dd);
                double hd = d/2;
                double e = Math.sqrt(1 - hd*hd); // e^2 + hd^2 = 1

                double mx = (X[i] + X[j]) / 2;
                double my = (Y[i] + Y[j]) / 2;
                {
                    // 垂直なベクトル / 長さ(d) * 長さ(e)
                    double px = -dy / d * e;
                    double py = dx / d * e;
                    double cx = mx + px;
                    double cy = my + py;
                    ans = Math.max(ans, count(cx, cy));
                }
                {
                    double px = dy / d * e;
                    double py = -dx / d * e;
                    double cx = mx + px;
                    double cy = my + py;
                    ans = Math.max(ans, count(cx, cy));
                }
            }
        }
        return ans;
    }

    static int count(double x, double y) {
        int ret = 0;
        for (int i = 0; i < N; i++) {
            double d = Point2D.distance(x, y, X[i], Y[i]);
            if( d < 1.0 || Math.abs(d - 1.0) < EPS) ret++;
        }
        return ret;
    }

    static double EPS = 1e-9;

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
            for (int i = 0; i < n; i++) a[i] = nextInt();
            return a;
        }

        int[] nextIntArray(int n, int delta) {
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = nextInt() + delta;
            return a;
        }

        long[] nextLongArray(int n) {
            long[] a = new long[n];
            for (int i = 0; i < n; i++) a[i] = nextLong();
            return a;
        }
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

    static void writeSingleLine(int[] as) {
        PrintWriter pw = new PrintWriter(System.out);
        for (int i = 0; i < as.length; i++) {
            if (i != 0) pw.print(" ");
            pw.print(as[i]);
        }
        pw.println();
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
            if (arg == null) j.add("null");
            else if (arg instanceof int[]) j.add(Arrays.toString((int[]) arg));
            else if (arg instanceof long[]) j.add(Arrays.toString((long[]) arg));
            else if (arg instanceof double[]) j.add(Arrays.toString((double[]) arg));
            else if (arg instanceof Object[]) j.add(Arrays.toString((Object[]) arg));
            else j.add(arg.toString());
        }
        System.err.println(j.toString());
    }

    static void printSingleLine(int[] array) {
        PrintWriter pw = new PrintWriter(System.out);
        for (int i = 0; i < array.length; i++) {
            if (i != 0) pw.print(" ");
            pw.print(array[i]);
        }
        pw.println();
        pw.flush();
    }

    static int lowerBound(int[] array, int value) {
        int lo = 0, hi = array.length, mid;
        while (lo < hi) {
            mid = (hi + lo) / 2;
            if (array[mid] < value) lo = mid + 1;
            else hi = mid;
        }
        return lo;
    }

    static int upperBound(int[] array, int value) {
        int lo = 0, hi = array.length, mid;
        while (lo < hi) {
            mid = (hi + lo) / 2;
            if (array[mid] <= value) lo = mid + 1;
            else hi = mid;
        }
        return lo;
    }
}

