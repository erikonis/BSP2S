import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int count = 0;

        for (int num = 1; num <= N; num += 2) {
            if (countDivisors(num) == 8) {
                count++;
            }
        }

        System.out.println(count);
    }

    private static int countDivisors(int num) {
        int divisors = 0;
        for (int i = 1; i * i <= num; i++) {
            if (num % i == 0) {
                if (i * i == num) {
                    divisors++;
                } else {
                    divisors += 2;
                }
            }
        }
        return divisors;
    }
}