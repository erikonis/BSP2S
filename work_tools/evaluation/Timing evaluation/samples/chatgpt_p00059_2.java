import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (sc.hasNext()) {
            double xa1 = sc.nextDouble();
            double ya1 = sc.nextDouble();
            double xa2 = sc.nextDouble();
            double ya2 = sc.nextDouble();
            double xb1 = sc.nextDouble();
            double yb1 = sc.nextDouble();
            double xb2 = sc.nextDouble();
            double yb2 = sc.nextDouble();

            double ax1 = Math.min(xa1, xa2);
            double ay1 = Math.min(ya1, ya2);
            double ax2 = Math.max(xa1, xa2);
            double ay2 = Math.max(ya1, ya2);
            double bx1 = Math.min(xb1, xb2);
            double by1 = Math.min(yb1, yb2);
            double bx2 = Math.max(xb1, xb2);
            double by2 = Math.max(yb1, yb2);

            // Check for non-overlap; if not, then overlap
            if (ax2 < bx1 || bx2 < ax1 || ay2 < by1 || by2 < ay1) {
                System.out.println("NO");
            } else {
                System.out.println("YES");
            }
        }
    }
}