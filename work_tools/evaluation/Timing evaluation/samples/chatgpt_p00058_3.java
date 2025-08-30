import java.util.Scanner;

public class Main {
    // Epsilon for floating point comparison
    static final double EPS = 1e-8;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            double xA = sc.nextDouble();
            double yA = sc.nextDouble();
            double xB = sc.nextDouble();
            double yB = sc.nextDouble();
            double xC = sc.nextDouble();
            double yC = sc.nextDouble();
            double xD = sc.nextDouble();
            double yD = sc.nextDouble();

            // Vector AB: (xB - xA, yB - yA)
            // Vector CD: (xD - xC, yD - yC)
            double v1x = xB - xA;
            double v1y = yB - yA;
            double v2x = xD - xC;
            double v2y = yD - yC;

            // Dot product
            double dot = v1x * v2x + v1y * v2y;

            if (Math.abs(dot) < EPS) {
                System.out.println("YES");
            } else {
                System.out.println("NO");
            }
        }
        sc.close();
    }
}