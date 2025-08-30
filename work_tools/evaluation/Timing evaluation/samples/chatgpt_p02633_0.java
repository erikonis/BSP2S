import java.util.Scanner;

public class Main {
    // Compute gcd of a and b
    static int gcd(int a, int b) {
        while (b != 0) {
            int t = b;
            b = a % b;
            a = t;
        }
        return a;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int X = sc.nextInt();
        int d = gcd(360, X);
        int K = 360 / d;
        System.out.println(K);
    }
}