import java.util.Scanner;

public class Main {
    // Alternative method: directly check if N is even
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        long N = sc.nextLong();
        // If N is even, answer is N; if odd, answer is N*2
        System.out.println((N % 2 == 0) ? N : N * 2);
    }
}