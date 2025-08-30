import java.io.PrintWriter;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int A = Integer.parseInt(sc.next());
        int B = Integer.parseInt(sc.next());
        int C = Integer.parseInt(sc.next());
        int D = Integer.parseInt(sc.next());
        int time = 0;

        if (A <= C) {
            if (B <= C) {
                time = 0;
            } else if (C < B && B <= D) {
                time = B - C;
            } else if (D < B) {
                time = D - C;
            }
        } else {
            if (D <= A) {
                time = 0;
            } else if (A < D && D <= B) {
                time = D - A;
            } else if (B < D) {
                time = B - A;
            }
        }

        PrintWriter out = new PrintWriter(System.out);
        out.println(time);

        sc.close();
        out.flush();
    }
}