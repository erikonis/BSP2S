import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String S = sc.next();
        int n = S.length();

        // Count repaints for two possible alternating patterns:
        // Pattern 1: 0 1 0 1 ...
        // Pattern 2: 1 0 1 0 ...
        int repaint1 = 0, repaint2 = 0;
        for (int i = 0; i < n; i++) {
            char expected1 = (i % 2 == 0) ? '0' : '1';
            char expected2 = (i % 2 == 0) ? '1' : '0';
            if (S.charAt(i) != expected1) repaint1++;
            if (S.charAt(i) != expected2) repaint2++;
        }
        System.out.println(Math.min(repaint1, repaint2));
    }
}