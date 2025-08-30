import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int M = sc.nextInt();
        int[] cnt = new int[N + 1];

        for (int i = 0; i < M; i++) {
            int a = sc.nextInt();
            int b = sc.nextInt();
            cnt[a]++;
            cnt[b]++;
        }

        boolean possible = true;
        for (int i = 1; i <= N; i++) {
            if (cnt[i] % 2 != 0) {
                possible = false;
                break;
            }
        }

        System.out.println(possible ? "YES" : "NO");
    }
}