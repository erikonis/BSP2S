import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        long x = sc.nextLong();
        long y = sc.nextLong();

        // Four possible routes:
        // 1. x -> y (A only)
        // 2. x -> -y (B at end)
        // 3. -x -> y (B at start)
        // 4. -x -> -y (B at start and end)

        long ans = Long.MAX_VALUE;

        // 1. Direct: x -> y
        ans = Math.min(ans, Math.abs(y - x));

        // 2. x -> -y (press B at end)
        ans = Math.min(ans, Math.abs(-y - x) + 1);

        // 3. -x -> y (press B at start)
        ans = Math.min(ans, Math.abs(y + x) + 1);

        // 4. -x -> -y (press B at start and end)
        ans = Math.min(ans, Math.abs(-y + x) + 2);

        System.out.println(ans);
    }
}