import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            int q = sc.nextInt();
            if (q == -1) {
                break;
            }

            double x = (double) q / 2; // x1 = q/2

            while (Math.abs(x * x * x - q) >= 0.00001 * q) {
                x = x - (x * x * x - q) / (3 * x * x);
            }
            System.out.printf("%.6f\n", x);
        }
        sc.close();
    }
}