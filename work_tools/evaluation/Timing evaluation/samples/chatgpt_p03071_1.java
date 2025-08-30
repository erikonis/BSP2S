import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int A = sc.nextInt();
        int B = sc.nextInt();

        // Press A twice: A + (A-1)
        int option1 = A + (A - 1);
        // Press B twice: B + (B-1)
        int option2 = B + (B - 1);
        // Press each once: A + B
        int option3 = A + B;

        int maxCoins = Math.max(option1, Math.max(option2, option3));
        System.out.println(maxCoins);
    }
}