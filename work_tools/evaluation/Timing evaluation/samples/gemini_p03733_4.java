import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        long T = sc.nextLong();
        long[] t = new long[N];
        for (int i = 0; i < N; i++) {
            t[i] = sc.nextLong();
        }
        sc.close();

        long totalTime = 0;
        for (int i = 0; i < N; i++) {
            // If it's the last person, simply add T to total time
            if (i == N - 1) {
                totalTime += T;
            } else {
                // Calculate the duration the shower runs due to this push before the next push
                // This is the minimum of T and the time until the next push
                totalTime += Math.min(T, t[i + 1] - t[i]);
            }
        }

        System.out.println(totalTime);
    }
}