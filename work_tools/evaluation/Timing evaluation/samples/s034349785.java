import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Main {
    public static long MOD = 1000 * 1000 * 1000 + 7;

    public static void main(String[] args){
        PrintWriter out = new PrintWriter(System.out);
        FastScanner sc = new FastScanner();

        int N = sc.nextInt();
        int M = sc.nextInt();
        long S[] = sc.nextLongArray(N);
        long T[] = sc.nextLongArray(M);
        long dp[][] = new long[N+1][M+1];
        ArrayList<Integer> S_list[] = new ArrayList[100000+1];
        for (int i=0;i<N;i++) {
            int s = (int) S[i];
            if (S_list[s] == null) {
                S_list[s] = new ArrayList<Integer>();
            }

            S_list[s].add(i);
        }

        for (int i=0;i<=N;i++) {
            dp[i][0] = 1;
        }

        for (int j=0;j<=M;j++) {
            dp[0][j] = 1;
        }

        for (int j=1;j<=M;j++) {
            HashMap<Integer, Long> map = new HashMap<Integer, Long>();
            HashMap<Integer, Integer> map_idx = new HashMap<Integer, Integer>();

            for (int i=1;i<=N;i++) {
                long a = dp[i][j-1];
                int t = (int) T[j-1];

                if (S_list[t] != null) {
                    int len = S_list[t].size();

                    long add = 0;
                    int last_idx = -1;

                    if (map.containsKey(t)) {
                        add = map.get(t);
                        last_idx = map_idx.get(t);
                    }

                    for (int k=last_idx+1;k<len;k++) {
                        int idx = S_list[t].get(k) + 1;
                        if (idx > i) break;

                        add += dp[idx-1][j-1];
                        add %= MOD;
                        last_idx = k;
                    }
                    map.put(t, add);
                    map_idx.put(t, last_idx);

                    a += add;
                    a %= MOD;
                }
//                for (int k=1;k<=i;k++) {
//                    if (S[k-1] == T[j-1]) {
//                        a += dp[k-1][j-1];
//                        a %= MOD;
//                    }
//                }

                dp[i][j] = a;
            }
        }

        long ans = dp[N][M];

        out.println(ans);
        out.flush();
    }

    public static class FastScanner {
        BufferedReader br;
        StringTokenizer st;

        public FastScanner(String s) {
            try {
                br = new BufferedReader(new FileReader(s));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        public FastScanner() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        String nextToken() {
            while (st == null || !st.hasMoreElements()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(nextToken());
        }

        long nextLong() {
            return Long.parseLong(nextToken());
        }

        double nextDouble() {
            return Double.parseDouble(nextToken());
        }

        int[] nextIntArray(int N) {
            int a[] = new int[N];
            for (int i=0;i<N;i++) {
                a[i] = this.nextInt();
            }

            return a;
        }

        long[] nextLongArray(int N) {
            long a[] = new long[N];
            for (int i=0;i<N;i++) {
                a[i] = this.nextLong();
            }

            return a;
        }

        double[] nextDoubleArray(int N) {
            double a[] = new double[N];
            for (int i=0;i<N;i++) {
                a[i] = this.nextDouble();
            }

            return a;
        }

        String[] nextTokenArray(int N) {
            String a[] = new String[N];
            for (int i=0;i<N;i++) {
                a[i] = this.nextToken();
            }

            return a;
        }
    }
}