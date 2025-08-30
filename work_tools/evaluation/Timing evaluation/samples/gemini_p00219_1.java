import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            int n = sc.nextInt();
            if (n == 0) {
                break;
            }

            int[] sales = new int[10]; // To store sales count for each ice cream type (0-9)

            for (int i = 0; i < n; i++) {
                int type = sc.nextInt();
                sales[type]++;
            }

            for (int i = 0; i < 10; i++) {
                if (sales[i] == 0) {
                    System.out.println("-");
                } else {
                    for (int j = 0; j < sales[i]; j++) {
                        System.out.print("*");
                    }
                    System.out.println();
                }
            }
        }
        sc.close();
    }
}