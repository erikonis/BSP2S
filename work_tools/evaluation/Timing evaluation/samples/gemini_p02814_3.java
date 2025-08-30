import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        long M = sc.nextLong();
        long[] a = new long[N];
        for (int i = 0; i < N; i++) {
            a[i] = sc.nextLong() / 2;
        }

        long lcm = a[0];
        for (int i = 1; i < N; i++) {
            lcm = lcm(lcm, a[i]);
            if (lcm > M) {
                System.out.println(0);
                return;
            }
        }

        for (int i = 0; i < N; i++) {
            if ((lcm / a[i]) % 2 == 0) {
                System.out.println(0);
                return;
            }
        }

        long count = (M / lcm + 1) / 2;
        System.out.println(count);
    }

    private static long gcd(long a, long b) {
        while (b != 0) {
            long temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    private static long lcm(long a, long b) {
        if (a == 0 || b == 0) return 0;
        return (a / gcd(a, b)) * b;
    }
}