import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner in = new Scanner(System.in)) {
            int N = in.nextInt();
            int[] a = new int[N];
            for (int i = 0; i < N; i++) {
                a[i] = in.nextInt();
            }

            int[] counts = new int[(int) (1e5 + 1)];
            for (int i = 0; i < N; i++) {
                counts[a[i]]++;
            }

            int max = 0;
            for (int i = 0; i < counts.length; i++) {
                max = Math.max(max, (i - 1 < 0 ? 0 : counts[i - 1]) + counts[i] + (i + 1 >= counts.length ? 0 : counts[i + 1]));
            }

            System.out.println(max);
        }
    }
}
