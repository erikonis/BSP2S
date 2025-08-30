import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            int n = sc.nextInt();
            if (n == 0) {
                break;
            }
            int[] incomes = new int[n];
            long sum = 0;
            for (int i = 0; i < n; i++) {
                incomes[i] = sc.nextInt();
                sum += incomes[i];
            }

            double average = (double) sum / n;
            int count = 0;
            for (int income : incomes) {
                if (income <= average) {
                    count++;
                }
            }
            System.out.println(count);
        }
        sc.close();
    }
}