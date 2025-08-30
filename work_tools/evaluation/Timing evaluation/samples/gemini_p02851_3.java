import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        long K = sc.nextLong();
        long[] A = new long[N];
        for (int i = 0; i < N; i++) {
            A[i] = sc.nextLong();
        }

        long ans = 0;
        long[] S = new long[N + 1];
        S[0] = 0;
        for (int i = 0; i < N; i++) {
            S[i + 1] = (S[i] + A[i] - 1 + K) % K;
        }

        Map<Long, Integer> map = new HashMap<>();
        for (int j = 0; j <= N; j++) {
            if (j - K >= 0) {
                map.put(S[j - (int) K], map.get(S[j - (int) K]) - 1);
            }
            ans += map.getOrDefault(S[j], 0);
            map.put(S[j], map.getOrDefault(S[j], 0) + 1);
        }

        System.out.println(ans);
    }
}