import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        long A = sc.nextLong();
        long B = sc.nextLong();
        long g = gcd(A, B);
        int count = 1; // 1 for 1 itself
        for (long i = 2; i * i <= g; i++) {
            if (g % i == 0) {
                count++;
                while (g % i == 0) g /= i;
            }
        }
        if (g > 1) count++;
        System.out.println(count);
    }

    static long gcd(long a, long b) {
        while (b != 0) {
            long t = b;
            b = a % b;
            a = t;
        }
        return a;
    }
}