import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Calculate sum for A
        int sumA = java.util.stream.IntStream.range(0, 4).map(i -> sc.nextInt()).sum();

        // Calculate sum for B
        int sumB = java.util.stream.IntStream.range(0, 4).map(i -> sc.nextInt()).sum();

        // Output the larger sum
        System.out.println(Math.max(sumA, sumB));

        sc.close();
    }
}