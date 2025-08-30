import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] parts = br.readLine().split(" ");
        int N = Integer.parseInt(parts[0]);
        int M = Integer.parseInt(parts[1]);
        int Q = Integer.parseInt(parts[2]);

        // Initialize a 2D array to count L_i and R_i
        int[][] count = new int[N+2][N+2];
        for (int i = 0; i < M; i++) {
            parts = br.readLine().split(" ");
            int L = Integer.parseInt(parts[0]);
            int R = Integer.parseInt(parts[1]);
            count[L][R]++;
        }

        // Create the prefix sum array
        int[][] prefix = new int[N+2][N+2];
        for (int l = N; l >= 1; l--) {
            for (int r = 1; r <= N; r++) {
                prefix[l][r] = count[l][r] + prefix[l][r-1] + prefix[l+1][r] - prefix[l+1][r-1];
            }
        }

        // Process queries
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < Q; i++) {
            parts = br.readLine().split(" ");
            int p = Integer.parseInt(parts[0]);
            int q = Integer.parseInt(parts[1]);
            sb.append(prefix[p][q]).append("\n");
        }
        System.out.print(sb);
    }
}