import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Built using CHelper plug-in
 * Actual solution is at the top
 */
public class Main {
    public static void main(String[] args) {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        Scanner in = new Scanner(inputStream);
        PrintWriter out = new PrintWriter(outputStream);
        B solver = new B();
        solver.solve(1, in, out);
        out.close();
    }

    static class B {
        public void solve(int testNumber, Scanner in, PrintWriter out) {
            // 入力
            int a = Integer.parseInt(in.next());
            int b = Integer.parseInt(in.next());
            int ans = 0;

            StringBuilder sb = new StringBuilder();
            for (int i = a; i <= b; i++) {
                sb.append(i);
                String t = sb.toString();
                sb.reverse();
                String rev = sb.toString();
                sb.delete(0, sb.length());
                if (t.equals(rev)) ans++;
            }

            // 出力
            out.println(ans);

        }

    }
}

