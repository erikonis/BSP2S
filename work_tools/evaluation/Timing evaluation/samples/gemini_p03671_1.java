import java.util.Scanner;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int a = sc.nextInt();
        int b = sc.nextInt();
        int c = sc.nextInt();

        // Store the prices in an array
        int[] prices = {a, b, c};

        // Sort the array in ascending order
        Arrays.sort(prices);

        // The minimum total price of two different bells will be the sum of the two smallest prices
        System.out.println(prices[0] + prices[1]);

        sc.close();
    }
}