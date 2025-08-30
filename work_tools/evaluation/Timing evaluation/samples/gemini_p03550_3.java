import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        long Z = sc.nextLong();
        long W = sc.nextLong();
        long[] a = new long[N];
        for (int i = 0; i < N; i++) {
            a[i] = sc.nextLong();
        }

        long score;

        // Case 1: X draws all cards
        // X gets a[N-1], Y keeps W
        score = Math.abs(a[N - 1] - W);

        // Case 2: X draws N-1 cards, leaving one for Y
        // X gets a[N-2], Y gets a[N-1]
        if (N >= 2) {
            score = Math.max(score, Math.abs(a[N - 2] - a[N - 1]));
        }

        System.out.println(score);
    }
}