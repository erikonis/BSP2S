import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

public class Main {
    static Input in = new Input(System.in);
    static PrintWriter out = new PrintWriter(System.out);

    public static void main(String[] args) {
        int N = in.nextInt();
        String X = in.next();

        int[] ans = new int[200000];
        for (int i = 1; i < 200000; i++) {
            int j = i % Integer.bitCount(i);
            ans[i] = ans[j] + 1;
        }

        int pop = 0;
        for (int n = 0; n < N; n++) if (X.charAt(n) == '1')
            pop++;
        int[] rem0 = new int[N];
        int[] rem1 = new int[N];
        for (int n = 0, i0 = 1, i1 = 1; n < N; n++) {
            if (pop > 1) {
                rem0[n] = i0 % (pop - 1);
                i0 = (i0 * 2) % (pop - 1);
            }
            rem1[n] = i1 % (pop + 1);
            i1 = (i1 * 2) % (pop + 1);
        }
        long sum0 = 0;
        long sum1 = 0;
        for (int n = 0; n < N; n++) if (X.charAt(n) == '1') {
            sum0 += rem0[N - n - 1];
            sum1 += rem1[N - n - 1];
        }
        for (int n = 0; n < N; n++) {
            int sum = 0;
            if (X.charAt(n) == '0') {
                sum = (int) ((sum1 + rem1[N - n - 1]) % (pop + 1));
                out.println(ans[sum] + 1);
            } else {
                if (pop > 1) {
                    sum = (int) ((sum0 - rem0[N - n - 1]) % (pop - 1));
                    out.println(ans[sum] + 1);
                } else {
                    out.println(0);
                }
            }
        }


        out.flush();
    }

    static class Input {
        private BufferedReader br;
        private String[] buff;
        private int index = 0;

        Input(InputStream is) {
            br = new BufferedReader(new InputStreamReader(is));
        }
        String nextLine() {
            try {
                return br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        String next() {
            while (buff == null || index >= buff.length) {
                buff = nextLine().split(" ");
                index = 0;
            }
            return buff[index++];
        }
        byte nextByte() {
            return Byte.parseByte(next());
        }
        short nextShort() {
            return Short.parseShort(next());
        }
        int nextInt() {
            return Integer.parseInt(next());
        }
        long nextLong() {
            return Long.parseLong(next());
        }
        float nextFloat() {
            return Float.parseFloat(next());
        }
        double nextDouble() {
            return Double.parseDouble(next());
        }
        BigInteger nextBigInteger() {
            return new BigInteger(next());
        }
        BigDecimal nextBigDecimal() {
            return new BigDecimal(next());
        }
    }
}

