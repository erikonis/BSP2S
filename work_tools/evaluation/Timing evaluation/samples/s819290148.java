import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.stream.IntStream;
import java.util.Arrays;
import java.util.Set;
import java.io.IOException;
import java.io.Reader;
import java.io.InputStreamReader;
import java.util.OptionalInt;
import java.util.TreeSet;
import java.io.BufferedReader;
import java.io.InputStream;

/**
 * Built using CHelper plug-in
 * Actual solution is at the top
 */
public class Main {
    public static void main(String[] args) {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        MyInput in = new MyInput(inputStream);
        PrintWriter out = new PrintWriter(outputStream);
        BDoNotDuplicate solver = new BDoNotDuplicate();
        solver.solve(1, in, out);
        out.close();
    }

    static class BDoNotDuplicate {
        public void solve(int testNumber, MyInput in, PrintWriter out) {
            int n = in.nextInt();
            long k = in.nextLong();
            int[] a = in.nextIntArray(n);
//        naive(a, 10);
            printList(out, solve(a, k));
        }

        int[] naive(int[] a, long kk) {
            int[] res = new int[a.length];
            int j = 0;
            Set<Integer> set = new TreeSet<>();
            while (kk-- > 0) {
                for (int i = 0; i < a.length; i++) {
                    if (set.contains(a[i])) {
                        while (set.contains(a[i])) {
                            set.remove(res[--j]);
                        }
                        continue;
                    }
                    res[j++] = a[i];
                    set.add(a[i]);
                }
//            dump(Arrays.copyOf(res, j));
            }
            return Arrays.copyOf(res, j);
        }

        int[] solve(int[] a, long k) {
            int n = a.length;
            int[] index = new int[n];
            Arrays.fill(index, -1);
            TreeSet<Integer>[] sets = new TreeSet[2 * 100000 + 1];
            for (int i = 0; i < sets.length; i++) {
                sets[i] = new TreeSet<>();
            }
            for (int i = 0; i < n; i++) {
                sets[a[i]].add(i);
                sets[a[i]].add(i + n);
            }

            int[] next = new int[n];
            for (int i = 0; i < n; i++) {
                next[i] = sets[a[i]].higher(i);
            }

            for (int i = 0, j = 0; i != n; ) {
                if (next[i] < n) {
                    i = next[i] + 1;
                    continue;
                }

                index[i] = j++;
                i = next[i] % n + 1;
            }

            int cycle = Arrays.stream(index).max().getAsInt() + 2;
            int st = (int) ((k - 1) % cycle);

            for (int i = 0; i < a.length; i++) {
                if (index[i] != st) {
                    continue;
                }
                int[] rest = Arrays.copyOfRange(a, i, a.length);
                return naive(rest, 1);
            }

            return new int[0];
        }

        static void printList(PrintWriter out, int[] p) {
            for (int i = 0; i < p.length; i++) {
                out.print(p[i] + (i == p.length - 1 ? "\n" : " "));
            }
        }

    }

    static class MyInput {
        private final BufferedReader in;
        private static int pos;
        private static int readLen;
        private static final char[] buffer = new char[1024 * 8];
        private static char[] str = new char[500 * 8 * 2];
        private static boolean[] isDigit = new boolean[256];
        private static boolean[] isSpace = new boolean[256];
        private static boolean[] isLineSep = new boolean[256];

        static {
            for (int i = 0; i < 10; i++) {
                isDigit['0' + i] = true;
            }
            isDigit['-'] = true;
            isSpace[' '] = isSpace['\r'] = isSpace['\n'] = isSpace['\t'] = true;
            isLineSep['\r'] = isLineSep['\n'] = true;
        }

        public MyInput(InputStream is) {
            in = new BufferedReader(new InputStreamReader(is));
        }

        public int read() {
            if (pos >= readLen) {
                pos = 0;
                try {
                    readLen = in.read(buffer);
                } catch (IOException e) {
                    throw new RuntimeException();
                }
                if (readLen <= 0) {
                    throw new MyInput.EndOfFileRuntimeException();
                }
            }
            return buffer[pos++];
        }

        public int nextInt() {
            int len = 0;
            str[len++] = nextChar();
            len = reads(len, isSpace);
            int i = 0;
            int ret = 0;
            if (str[0] == '-') {
                i = 1;
            }
            for (; i < len; i++) ret = ret * 10 + str[i] - '0';
            if (str[0] == '-') {
                ret = -ret;
            }
            return ret;
        }

        public long nextLong() {
            int len = 0;
            str[len++] = nextChar();
            len = reads(len, isSpace);
            int i = 0;
            long ret = 0;
            if (str[0] == '-') {
                i = 1;
            }
            for (; i < len; i++) ret = ret * 10 + str[i] - '0';
            if (str[0] == '-') {
                ret = -ret;
            }
            return ret;
        }

        public char nextChar() {
            while (true) {
                final int c = read();
                if (!isSpace[c]) {
                    return (char) c;
                }
            }
        }

        int reads(int len, boolean[] accept) {
            try {
                while (true) {
                    final int c = read();
                    if (accept[c]) {
                        break;
                    }
                    if (str.length == len) {
                        char[] rep = new char[str.length * 3 / 2];
                        System.arraycopy(str, 0, rep, 0, str.length);
                        str = rep;
                    }
                    str[len++] = (char) c;
                }
            } catch (MyInput.EndOfFileRuntimeException e) {
            }
            return len;
        }

        public int[] nextIntArray(final int n) {
            final int[] res = new int[n];
            for (int i = 0; i < n; i++) {
                res[i] = nextInt();
            }
            return res;
        }

        static class EndOfFileRuntimeException extends RuntimeException {
        }

    }
}

