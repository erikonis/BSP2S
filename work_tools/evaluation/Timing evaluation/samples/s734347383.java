import java.util.*;
public class Main {
    static int n;
    static String s;
    static int q;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        q = sc.nextInt();
        s = sc.next();
        char[] t = new char[q];
        char[] d = new char[q];
        for (int i = 0; i < q; i++) {
            t[i] = sc.next().charAt(0);
            d[i] = sc.next().charAt(0);
        }
        int left = binSearch(t, d, -1, n);
        // System.out.println("left = " + left);
        int right = binSearch(t, d, n, -1);
        // System.out.println("right = " + right);
        int ans = Math.max(0, right - left - 1);
        System.out.println(ans);
    }
    public static int binSearch(char[] t, char[] d, int ok, int ng) {
        int out = ok;
        while (Math.abs(ok - ng) > 1) {
            int mid = (ok + ng) / 2;
            if (solve(mid, t, d, out)) {
                ok = mid;
            } else {
                ng = mid;
            }
            // System.out.printf("ok = %d,ng = %d %n", ok, ng);
        }
        return ok;
    }
    public static boolean solve(int mid, char[] t, char[] d, int out) {
        for (int i = 0; i < q; i++) {
            if (mid == -1 || mid == n) {
                return false;
            }
            char ch = s.charAt(mid);
            // System.out.println("ch = " + ch);
            // System.out.println("mid = " + mid);
            // System.out.printf("t[%d] = %s %n", i, t[i]);
            // System.out.printf("d[%d] = %s %n", i, d[i]);
            if (ch != t[i]) {
                continue;
            }
            if (d[i] == 'L') {
                mid--;
            } else if (d[i] == 'R') {
                mid++;
            }
            if (mid == out) {
                //    System.out.println("out mid = " + mid);
                return true;
            }
        }
        return false;
    }
}
