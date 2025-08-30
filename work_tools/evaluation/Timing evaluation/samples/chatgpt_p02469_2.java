import java.util.Scanner;

public class Main {
    static int gcd(int a, int b) {
        while (b != 0) {
            int t = b;
            b = a % b;
            a = t;
        }
        return a;
    }

    static int lcm(int a, int b) {
        return a / gcd(a, b) * b;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int res = sc.nextInt();
        for (int i = 1; i < n; i++) {
            int x = sc.nextInt();
            res = lcm(res, x);
        }
        System.out.println(res);
    }
}