import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        long N = sc.nextLong();
        sc.close();

        long totalSum = N * (N + 1) / 2;

        long sumMultiplesOf3 = 0;
        long countMultiplesOf3 = N / 3;
        sumMultiplesOf3 = 3 * countMultiplesOf3 * (countMultiplesOf3 + 1) / 2;

        long sumMultiplesOf5 = 0;
        long countMultiplesOf5 = N / 5;
        sumMultiplesOf5 = 5 * countMultiplesOf5 * (countMultiplesOf5 + 1) / 2;

        long sumMultiplesOf15 = 0;
        long countMultiplesOf15 = N / 15;
        sumMultiplesOf15 = 15 * countMultiplesOf15 * (countMultiplesOf15 + 1) / 2;

        System.out.println(totalSum - (sumMultiplesOf3 + sumMultiplesOf5 - sumMultiplesOf15));
    }
}