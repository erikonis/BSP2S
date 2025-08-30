import java.util.*;
import java.io.*;
import java.lang.*;
import java.math.BigInteger;

public class Main {

    public static void main(String[] args) {
        try {
            InputReader in;
            PrintWriter out;
            boolean useOutput = false;
            // if (System.getProperty("ONLINE_JUDGE") == null) useOutput = true;
            if (useOutput) {
                FileInputStream fin = new FileInputStream(new File("src/testdata.in"));
                in = new InputReader(fin);
                FileOutputStream fout = new FileOutputStream(new File("src/testData.out"));
                out = new PrintWriter(fout);
            } else {
                InputStream inputStream = System.in;
                in = new InputReader(inputStream);
                OutputStream outputStream = System.out;
                out = new PrintWriter(outputStream);
            }
            Solver solver = new Solver();
            solver.solve(in, out);
            out.close();
            // fin.close();
            // fout.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static class Solver
    {

        public void solve (InputReader cin, PrintWriter cout)
        {
            try {
                int n = cin.nextInt();
                int k = cin.nextInt();
                if (n < k) {
                    cout.println(1);
                } else {
                    if (n % k == 0) cout.println(0);
                    else cout.println(1);
                }
            } catch (RuntimeException e) {
                e.printStackTrace();
                return;
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }

        }
    }

    static class InputReader {
        public BufferedReader reader;
        public StringTokenizer tokenizer;

        public InputReader(InputStream stream) {
            reader = new BufferedReader(new InputStreamReader(stream));
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

        public long nextLong () {
            return Long.parseLong(next());
        }

        public double nextDouble () {
            return Double.parseDouble(next());
        }

    }

}