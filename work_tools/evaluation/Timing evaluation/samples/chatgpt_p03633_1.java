import java.util.Scanner;

public class Main {
    // Compute gcd(a, b)
    static long gcd(long a, long b) {
        while (b != 0) {
            long t = b;
            b = a % b;
            a = t;
        }
        return a;
    }

    // Compute lcm(a, b)
    static long lcm(long a, long b) {
        return a / gcd(a, b) * b;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        long res = 1;
        for (int i = 0; i < N; i++) {
            long t = sc.nextLong();
            res = lcm(res, t);
        }
        System.out.println(res);
    }
}