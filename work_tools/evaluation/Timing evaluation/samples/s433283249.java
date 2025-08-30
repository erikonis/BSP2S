import java.util.*;
import java.io.*;

// Template for atcoder
public class Main {

    BufferedReader br;
    PrintWriter out;
    StringTokenizer st;
    boolean eof;
    final long MOD = 1000L * 1000L * 1000L + 7;
    private static final int[] dx = {0, -1, 0, 1};
    private static final int[] dy = {1, 0, -1, 0};

    void solve() throws IOException {
        int n = nextInt();
        long[] arr = nextLongArr(n);
        long tmp = 0;
        for (long v : arr) {
            tmp = tmp ^ v;
        }
        if (tmp == 0) {
            outln(0);
            return;
        }

        long target = 0;
        for (int i = 2; i < n; i++) {
            target = target ^ arr[i];
        }
        long sum = arr[0] + arr[1];
        long and = sum - target;
        if (and % 2 != 0) {
            outln(-1);
            return;
        }
        and = and / 2;
        int[] cand = new int[64];
        for (int i = 0; i < 63; i++) {
            int xorBit = ((1L << i) & target) != 0 ? 1 : 0;
            int andBit = ((1L << i) & and) != 0 ? 1 : 0;
            if (xorBit == 1) {
                if (andBit == 0) {
                    cand[i] = -1;
                } else {
                    outln(-1);
                    return;
                }
            } else {
                if (andBit == 0) {
                    cand[i] = 0;
                } else {
                    cand[i] = 1;
                }
            }
        }

        long res = find(cand, arr[0]);
        if (res == -1 || res == 0) {
            outln(-1);
            return;
        }
        outln(arr[0] - res);
    }

    long find(int[] cand, long limit) {
        int[] arr = new int[64];
        for (int i = 0; i < 63; i++) {
            if ((limit & (1L << i)) != 0) {
                arr[i] = 1;
            }
        }
        long res = -1;
        // cand[i] < arr[i]
        for (int i = 63; i >= 0; i--) {
            boolean valid = true;
            for (int j = i + 1; j < arr.length; j++) {
                if (cand[j] != -1 && cand[j] != arr[j]) {
                    valid = false;
                    break;
                }
            }
            if (!valid) {
                continue;
            }
            if (cand[i] == -1) {
                if (arr[i] == 1) {
                    long tmp = calc(cand, i, arr);
                    if (tmp < limit) {
                        res = Math.max(res, tmp);
                    }
                }
            } else if (cand[i] == 0) {
                if (arr[i] == 1) {
                    long tmp = calc(cand, i, arr);
                    if (tmp < limit) {
                        res = Math.max(res, tmp);
                    }
                }
            }
        }
        return res;
    }

    long calc(int[] cand, int idx, int[] arr) {
        long res = 0;
        for (int i = arr.length - 1; i >= 0; i--) {
            if (i > idx) {
                res = 2 * res + arr[i];
            } else if (i == idx) {
                res = 2 * res;
            } else {
                if (cand[i] == -1) {
                    res = 2 * res + 1;
                } else {
                    res = 2 * res + cand[i];
                }
            }
        }
        return res;
    }

    void shuffle(int[] a) {
        int n = a.length;
        for(int i = 0; i < n; i++) {
            int r = i + (int) (Math.random() * (n - i));
            int tmp = a[i];
            a[i] = a[r];
            a[r] = tmp;
        }
    }
    private void outln(Object o) {
        out.println(o);
    }
    private void out(Object o) {
        out.print(o);
    }
    public Main() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        out = new PrintWriter(System.out);
        solve();
        out.close();
    }
    public static void main(String[] args) throws IOException {
        new Main();
    }

    public long[] nextLongArr(int n) throws IOException{
        long[] res = new long[n];
        for(int i = 0; i < n; i++)
            res[i] = nextLong();
        return res;
    }
    public int[] nextIntArr(int n) throws IOException {
        int[] res = new int[n];
        for(int i = 0; i < n; i++)
            res[i] = nextInt();
        return res;
    }
    public String nextToken() {
        while (st == null || !st.hasMoreTokens()) {
            try {
                st = new StringTokenizer(br.readLine());
            } catch (Exception e) {
                eof = true;
                return null;
            }
        }
        return st.nextToken();
    }
    public String nextString() {
        try {
            return br.readLine();
        } catch (IOException e) {
            eof = true;
            return null;
        }
    }
    public int nextInt() throws IOException {
        return Integer.parseInt(nextToken());
    }
    public long nextLong() throws IOException {
        return Long.parseLong(nextToken());
    }
    public double nextDouble() throws IOException {
        return Double.parseDouble(nextToken());
    }
}
