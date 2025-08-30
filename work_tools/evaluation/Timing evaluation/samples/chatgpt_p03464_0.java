import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int K = sc.nextInt();
        int[] A = new int[K];
        for (int i = 0; i < K; i++) {
            A[i] = sc.nextInt();
        }

        long min = 2;
        long max = 2;

        // Work backwards from the end to the start
        for (int i = K - 1; i >= 0; i--) {
            long a = A[i];

            // The number of children before this round must be a multiple of a,
            // and after grouping, the survivors are between min and max.

            // The minimal possible number: the smallest x >= min s.t. x % a == 0
            min = ((min + a - 1) / a) * a;

            // The maximal possible number: the largest x <= max s.t. x % a == a-1, so x = y*a + (a-1), y*a + (a-1) <= max
            max = (max / a) * a + (a - 1);

            if (min > max) {
                System.out.println(-1);
                return;
            }
        }

        System.out.println(min + " " + max);
    }
}