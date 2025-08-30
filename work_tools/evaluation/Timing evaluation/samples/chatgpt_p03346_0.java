import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int[] pos = new int[N + 1]; // pos[value] = index in array (0-based)
        for (int i = 0; i < N; i++) {
            int val = sc.nextInt();
            pos[val] = i;
        }

        // Find the longest increasing consecutive sequence in positions
        int maxLen = 1, curLen = 1;
        for (int i = 2; i <= N; i++) {
            if (pos[i] > pos[i - 1]) {
                curLen++;
            } else {
                curLen = 1;
            }
            maxLen = Math.max(maxLen, curLen);
        }

        // Minimum number of moves = N - length of such sequence
        System.out.println(N - maxLen);
    }
}