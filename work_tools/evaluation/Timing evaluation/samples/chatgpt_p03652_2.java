import java.util.*;

public class Main {
    static int N, M;
    static int[][] A;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        M = sc.nextInt();
        A = new int[N][M];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                A[i][j] = sc.nextInt() - 1; // zero-based
            }
        }

        int ans = N;
        // Try all pairs of sports (sport1, sport2)
        for (int sport1 = 0; sport1 < M; sport1++) {
            // Each person: what is their favorite? If sport1 is played, all choose favorite
            int[] count1 = new int[M];
            for (int i = 0; i < N; i++) {
                if (A[i][0] == sport1) {
                    count1[sport1]++;
                }
            }
            // For each second sport (including the case of only one sport)
            for (int sport2 = 0; sport2 < M; sport2++) {
                if (sport1 == sport2) continue;
                // Each person's most preferred among sport1 and sport2:
                int[] cnt = new int[M];
                for (int i = 0; i < N; i++) {
                    // Find which comes first in the person's list: sport1 or sport2
                    int pos1 = -1, pos2 = -1;
                    for (int j = 0; j < M; j++) {
                        if (A[i][j] == sport1) pos1 = j;
                        if (A[i][j] == sport2) pos2 = j;
                    }
                    if (pos1 < pos2) cnt[sport1]++;
                    else cnt[sport2]++;
                }
                int max = 0;
                for (int k = 0; k < M; k++) max = Math.max(max, cnt[k]);
                ans = Math.min(ans, max);
            }
            // If only sport1 is played
            ans = Math.min(ans, count1[sport1]);
        }
        System.out.println(ans);
    }
}