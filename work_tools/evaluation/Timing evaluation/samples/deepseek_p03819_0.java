import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] parts = br.readLine().split(" ");
        int N = Integer.parseInt(parts[0]);
        int M = Integer.parseInt(parts[1]);
        
        int[] diff = new int[M + 2];
        
        for (int i = 0; i < N; i++) {
            parts = br.readLine().split(" ");
            int l = Integer.parseInt(parts[0]);
            int r = Integer.parseInt(parts[1]);
            int w = r - l + 1;  // width of the interval
            
            int minD = 1;
            int maxD = w;
            if (minD <= M) {
                diff[minD]++;
                if (maxD + 1 <= M) {
                    diff[maxD + 1]--;
                }
            }
        }
        
        // Compute prefix sum to get counts for each d
        int[] res = new int[M + 1];
        int current = 0;
        for (int d = 1; d <= M; d++) {
            current += diff[d];
            res[d] = current;
        }
        
        // Now, answer the queries
        StringBuilder sb = new StringBuilder();
        for (int d = 1; d <= M; d++) {
            sb.append(res[d]).append("\n");
        }
        System.out.print(sb.toString());
    }
}