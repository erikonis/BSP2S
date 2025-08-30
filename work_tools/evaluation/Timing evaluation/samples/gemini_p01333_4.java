import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            int A = sc.nextInt();
            int B = sc.nextInt();

            if (A == 0 && B == 0) {
                break;
            }

            int change = B - A;

            int thousands = change / 1000;
            change %= 1000;

            int fiveHundreds = change / 500;
            change %= 500;

            int hundreds = change / 100;

            System.out.println(hundreds + " " + fiveHundreds + " " + thousands);
        }

        sc.close();
    }
}