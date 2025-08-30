import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        int[] A = new int[N+2];
        A[0] = 0; // start
        for (int i = 1; i <= N; i++) {
            A[i] = sc.nextInt();
        }
        A[N+1] = 0; // goal

        // Try dice with 1 to N+1 faces
        for (int k = 1; k <= N+1; k++) {
            boolean[] reachable = new boolean[N+2];
            reachable[0] = true; // start

            for (int i = 0; i <= N+1; i++) {
                if (!reachable[i]) continue;
                for (int d = 1; d <= k; d++) {
                    int next = i + d;
                    if (next > N+1) continue;
                    if (A[next] == 1 && next != N+1) continue; // cannot stop on 1, except goal
                    reachable[next] = true;
                }
            }

            if (reachable[N+1]) {
                System.out.println(k);
                return;
            }
        }
    }
}