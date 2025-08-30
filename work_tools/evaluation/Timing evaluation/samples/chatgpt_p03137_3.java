import java.util.*;
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int M = sc.nextInt();
        int[] X = new int[M];
        for (int i = 0; i < M; i++) X[i] = sc.nextInt();

        if (N >= M) {
            System.out.println(0);
            return;
        }

        Arrays.sort(X);

        // Compute the gaps between consecutive coordinates
        int[] gaps = new int[M - 1];
        for (int i = 0; i < M - 1; i++) {
            gaps[i] = X[i + 1] - X[i];
        }

        // Select the N-1 largest gaps to split the coordinates into N groups
        Arrays.sort(gaps);
        int moves = 0;
        for (int i = 0; i < M - N; i++) {
            moves += gaps[i];
        }
        System.out.println(moves);
    }
}