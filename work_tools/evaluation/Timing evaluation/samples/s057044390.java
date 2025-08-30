import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.StringTokenizer;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.TreeSet;
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
        ADiverseWord solver = new ADiverseWord();
        solver.solve(1, in, out);
        out.close();
    }

    static class ADiverseWord {
        static final char lower = 'a';
        static final char upper = 'z';
        static final int alphabetSize = upper - lower + 1;

        public void solve(int testNumber, InputReader in, PrintWriter out) {
            String s = in.next();
            char[] chars = s.toCharArray();
            int n = chars.length;

            String answer = "-1";
            if (n < alphabetSize) {
                TreeSet<Character> available = new TreeSet<>();
                for (char c = lower; c <= upper; c++) {
                    available.add(c);
                }
                for (char c : chars) {
                    available.remove(c);
                }
                answer = s + available.first();
            } else {
                outer:
                for (int i = n - 1; i >= 0; i--) {
                    for (int j = n - 1; j > i; j--) {
                        if (chars[i] < chars[j]) {
                            char c = chars[i];
                            chars[i] = chars[j];
                            chars[j] = c;
                            answer = String.valueOf(chars);
                            answer = answer.substring(0, i + 1);
                            break outer;
                        }
                    }
                }
            }

            out.println(answer);
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

    }
}

