import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt(); // even balls
        int M = sc.nextInt(); // odd balls
        int evenPairs = N * (N - 1) / 2;
        int oddPairs = M * (M - 1) / 2;
        System.out.println(evenPairs + oddPairs);
    }
}