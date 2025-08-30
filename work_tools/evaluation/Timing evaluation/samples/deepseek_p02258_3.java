import java.util.Scanner;

public class MaximumProfit {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] R = new int[n];
        for (int i = 0; i < n; i++) {
            R[i] = scanner.nextInt();
        }

        int minR = R[0];
        int maxProfit = Integer.MIN_VALUE;

        for (int i = 1; i < n; i++) {
            if (R[i] - minR > maxProfit) {
                maxProfit = R[i] - minR;
            }
            if (R[i] < minR) {
                minR = R[i];
            }
        }

        System.out.println(maxProfit);
    }
}