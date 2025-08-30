import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;


public class Main {
    int N,K;
    ArrayList<Integer>[] v;
    private void solve() {
        N = nextInt();
        K = nextInt();

        int uToV = N * (N - 1) / 2 - (N - 1);
        if (uToV < K) {
            out.println(-1);
            return;
        }

        v = new ArrayList[N];
        for(int i = 0;i < N;i++) {
            v[i] = new ArrayList<>();
        }

        for(int i = 1;i < N;i++) {
            v[0].add(i);
        }

        int s = 1;
        int t = 2;
        for(int i = 0;i < uToV - K;i++) {
            if (t == N) {
                s++;
                t = s + 1;
            }
            v[s].add(t);
            t++;
        }

        int M = 0;
        for(int i = 0;i < N;i++) {
            M += v[i].size();
        }
        out.println(M);

        for(int i = 0;i < N;i++) {
            for(int u : v[i]) {
                int a = i + 1;
                int b = u + 1;
                out.println(a + " " + b);
            }
        }
    }

    public static void main(String[] args) {
        out.flush();
        new Main().solve();
        out.close();
    }

    /* Input */
    private static final InputStream in = System.in;
    private static final PrintWriter out = new PrintWriter(System.out);
    private final byte[] buffer = new byte[4096];
    private int p = 0;
    private int buflen = 0;

    private boolean hasNextByte() {
        if (p < buflen)
            return true;
        p = 0;
        try {
            buflen = in.read(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (buflen <= 0)
            return false;
        return true;
    }

    public boolean hasNext() {
        while (hasNextByte() && !isPrint(buffer[p])) {
            p++;
        }
        return hasNextByte();
    }

    private boolean isPrint(int ch) {
        if (ch >= '!' && ch <= '~')
            return true;
        return false;
    }

    private int nextByte() {
        if (!hasNextByte())
            return -1;
        return buffer[p++];
    }

    public String next() {
        if (!hasNext())
            throw new NoSuchElementException();
        StringBuilder sb = new StringBuilder();
        int b = -1;
        while (isPrint((b = nextByte()))) {
            sb.appendCodePoint(b);
        }
        return sb.toString();
    }

    public int nextInt() {
        return Integer.parseInt(next());
    }

    public long nextLong() {
        return Long.parseLong(next());
    }

    public double nextDouble() {
        return Double.parseDouble(next());
    }
}
