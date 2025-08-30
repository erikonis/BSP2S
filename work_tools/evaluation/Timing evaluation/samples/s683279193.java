import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Collection;
import java.io.IOException;
import java.util.Deque;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.io.BufferedReader;
import java.util.Comparator;
import java.util.ArrayDeque;
import java.io.InputStream;

/**
 * Built using CHelper plug-in
 * Actual solution is at the top
 *
 * @author anand.oza
 */
public class Main {
    public static void main(String[] args) {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        InputReader in = new InputReader(inputStream);
        PrintWriter out = new PrintWriter(outputStream);
        EConnected solver = new EConnected();
        solver.solve(1, in, out);
        out.close();
    }

    static class EConnected {
        public void solve(int testNumber, InputReader in, PrintWriter out) {
            int r = in.nextInt();
            int c = in.nextInt();
            int n = in.nextInt();

            List<EConnected.P> l = new ArrayList<>();

            for (int i = 0; i < n; i++) {
                int x1 = in.nextInt();
                int y1 = in.nextInt();
                int x2 = in.nextInt();
                int y2 = in.nextInt();
                long t1 = transform(r, c, x1, y1);
                long t2 = transform(r, c, x2, y2);
                if (t1 == -1 || t2 == -1)
                    continue;
                l.add(new EConnected.P(i, t1));
                l.add(new EConnected.P(i, t2));
            }

            l.sort(Comparator.comparingLong(p -> p.t));

            Deque<Integer> stack = new ArrayDeque<>();
            for (EConnected.P p : l) {
                int label = p.label;
                if (!stack.isEmpty() && stack.peekLast() == label) {
                    stack.pollLast();
                } else {
                    stack.addLast(label);
                }
            }

            boolean answer = stack.isEmpty();
            out.println(answer ? "YES" : "NO");
        }

        long transform(int r, int c, int x, int y) {
            int L = Math.max(r, c) + 10;
            if (x == 0) {
                return y;
            }
            if (y == c) {
                return 3 * L + x;
            }
            if (x == r) {
                return 6 * L + L - y;
            }
            if (y == 0) {
                return 9 * L + L - x;
            }

            return -1;
        }

        static class P {
            final int label;
            final long t;

            P(int label, long t) {
                this.label = label;
                this.t = t;
            }

        }

    }

    static class InputReader {
        public BufferedReader reader;
        public StringTokenizer tokenizer;

        public InputReader(InputStream stream) {
            reader = new BufferedReader(new InputStreamReader(stream), 32768);
            tokenizer = null;
        }

        public String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return tokenizer.nextToken();
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }

    }
}

