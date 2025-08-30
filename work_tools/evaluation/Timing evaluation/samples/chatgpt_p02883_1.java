import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        String[] line = br.readLine().split(" ");
        int N = Integer.parseInt(line[0]);
        long K = Long.parseLong(line[1]);
        
        int[] A = new int[N];
        int[] F = new int[N];
        
        String[] aStr = br.readLine().split(" ");
        String[] fStr = br.readLine().split(" ");
        
        for (int i = 0; i < N; i++) A[i] = Integer.parseInt(aStr[i]);
        for (int i = 0; i < N; i++) F[i] = Integer.parseInt(fStr[i]);
        
        Arrays.sort(A);
        Arrays.sort(F);
        for (int i = 0; i < N / 2; i++) {
            int tmp = F[i];
            F[i] = F[N - 1 - i];
            F[N - 1 - i] = tmp;
        }
        
        long left = -1, right = (long)1e12;
        while (right - left > 1) {
            long mid = (left + right) / 2;
            if (canAchieve(mid, A, F, N, K)) right = mid;
            else left = mid;
        }
        System.out.println(right);
    }
    
    static boolean canAchieve(long score, int[] A, int[] F, int N, long K) {
        long total = 0;
        for (int i = 0; i < N; i++) {
            long maxA = score / F[i];
            if (A[i] > maxA) {
                total += (A[i] - maxA);
                if (total > K) return false;
            }
        }
        return total <= K;
    }
}