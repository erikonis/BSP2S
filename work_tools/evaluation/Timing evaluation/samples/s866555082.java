import java.io.*;
import java.util.*;


/**
 * AIZU ONLINE JUDGE
 * 1042
 *  2020/5/10
 */
public class Main {

    PrintStream log = new PrintStream(new OutputStream() { public void write(int b) {} } );
    PrintStream result = System.out;
    Scanner sc = new Scanner(System.in);

    void main() throws IOException {

        for(;;) {
            String s = sc.nextLine();
            if (s.equals("END OF INPUT"))
                break;
            String r = "";
            int cnt = 0;
            for(int i = 0; i < s.length() + 1; i++) {
                if (i >= s.length() || s.charAt(i) == ' ') {
                    r += cnt;
                    cnt = 0;
                }
                else
                    cnt++;
            }
            result.println(r);
        }
    }

    public static void main(String[] args) throws IOException {
        new Main().main();
    }


}


