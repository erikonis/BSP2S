import java.io.IOException;
import java.util.NoSuchElementException;

import java.io.InputStream;

import java.io.PrintWriter;

import java.util.Arrays;

@SuppressWarnings("unchecked")
public class Main {
    static final int INF = 1000000007;

    // aとbを掛けた値をmodする(a * b mod p)
    static int modmulti(int a, int b) {
        int res = 0;
        int mod = a % INF;
        while (b > 0) {
            if ((b & 1) == 1) {
                res += mod;
                if (res > INF) {
                    res -= INF;
                }
            }
            mod <<= 1;
            if (mod > INF) {
                mod -= INF;
            }
            b >>= 1;
        }
        return res;
    }

    public static void main(String[] args) throws IOException {
        PrintWriter out = new PrintWriter(System.out);

        final int N = nextInt();

        int[] A = new int[N];

        Arrays.fill(A, 0);

        for (int i = 0; i < N; i++) A[nextInt()]++;

        int pos = N % 2 == 1 ? 0 :1;

        boolean flg = true;

        int ans = 1;

        if (pos == 0 && A[0] != 1) flg = false;

        for (int i = 1; i < N; i++) {
            if (!flg) break;
            if (i % 2 == pos && A[i] != 2) flg = false;
        }

        if (flg)
            for (int i = 0; i < N / 2; i++) ans = modmulti(ans, 2);

        out.println(flg ? ans : 0);

        out.flush();
    }

    // FastScanner start
    static final InputStream in = System.in;
    static final byte[] buffer = new byte[1024];
    static int ptr = 0;
    static int buflen = 0;
    static boolean hasNextByte() {
        if (ptr < buflen) {
            return true;
        } else {
            ptr = 0;
            try {
                buflen = in.read(buffer);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (buflen <= 0) {
                return false;
            }
        }
        return true;
    }
    static int readByte() {
        if (hasNextByte()) return buffer[ptr++];
        else return -1;
    }
    static boolean isPrintableChar(int c) {
        return 33 <= c && c <= 126;
    }
    static boolean hasNext() {
        while (hasNextByte() && !isPrintableChar(buffer[ptr])) ptr++;
        return hasNextByte();
    }
    static String next() {
        if (!hasNext()) throw new NoSuchElementException();
        StringBuilder sb = new StringBuilder();
        int b = readByte();
        while (isPrintableChar(b)) {
            sb.appendCodePoint(b);
            b = readByte();
        }
        return sb.toString();
    }
    static long nextLong() {
        if (!hasNext()) throw new NoSuchElementException();
        long n = 0;
        boolean minus = false;
        int b = readByte();
        if (b == '-') {
            minus = true;
            b = readByte();
        }
        if (b < '0' || '9' < b) {
            throw new NumberFormatException();
        }
        while (true) {
            if ('0' <= b && b <= '9') {
                n *= 10;
                n += b - '0';
            } else if(b == -1 || !isPrintableChar(b)) {
                return minus ? -n : n;
            } else {
                throw new NumberFormatException();
            }
            b = readByte();
        }
    }
    static int nextInt() {
        long nl = nextLong();
        if (nl < Integer.MIN_VALUE || nl > Integer.MAX_VALUE)
            throw new NumberFormatException();
        return (int) nl;
    }
    static double nextDouble() {
        return Double.parseDouble(next());
    }
    // FastScanner end
}
