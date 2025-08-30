import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int datasetNum = 1;

        while (true) {
            int n = sc.nextInt();
            if (n == 0) {
                break;
            }

            int[] x = new int[n];
            int[] y = new int[n];

            for (int i = 0; i < n; i++) {
                x[i] = sc.nextInt();
                y[i] = sc.nextInt();
            }

            double area = 0.0;
            for (int i = 0; i < n; i++) {
                int j = (i + 1) % n; // Next vertex index
                area += (double) (x[i] * y[j] - x[j] * y[i]);
            }
            area = Math.abs(area / 2.0);

            System.out.printf("%d %.1f%n", datasetNum, area);
            datasetNum++;
        }

        sc.close();
    }
}