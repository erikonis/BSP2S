import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        long[] A = new long[N];
        for (int i = 0; i < N; i++) {
            A[i] = sc.nextLong();
        }
        sc.close();

        long result = 1;
        long limit = 1_000_000_000_000_000_000L;

        for (long val : A) {
            if (val == 0) {
                result = 0;
                break;
            }
        }

        if (result == 0) {
            System.out.println(0);
            return;
        }

        for (long val : A) {
            if (limit / val < result) { // Check for overflow before multiplication
                result = -1;
                break;
            }
            result *= val;
        }

        System.out.println(result);
    }
}