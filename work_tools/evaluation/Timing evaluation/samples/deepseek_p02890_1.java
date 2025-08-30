import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        int[] A = new int[N];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            A[i] = Integer.parseInt(st.nextToken());
        }

        int[] freq = new int[N + 1];
        for (int num : A) {
            freq[num]++;
        }

        Arrays.sort(freq);
        int[] sum = new int[N + 2];
        for (int i = 1; i <= N; i++) {
            sum[i] = sum[i - 1] + freq[i];
        }

        int[] ans = new int[N + 1];
        int ptr = N;
        for (int k = 1; k <= N; k++) {
            while (ptr >= 1 && freq[ptr] > ans[k - 1]) {
                ptr--;
            }
            ans[k] = ans[k - 1] + (sum[N] - sum[ptr] - ans[k - 1] * (N - ptr)) / k;
        }

        StringBuilder sb = new StringBuilder();
        for (int k = 1; k <= N; k++) {
            sb.append(ans[k]).append("\n");
        }
        System.out.print(sb.toString());
    }
}