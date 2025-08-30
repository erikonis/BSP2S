import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int M = sc.nextInt();
        long K = sc.nextLong();
        long[] A = new long[N + 1];
        long[] B = new long[M + 1];
        
        for (int i = 1; i <= N; i++) {
            A[i] = sc.nextLong();
        }
        for (int i = 1; i <= M; i++) {
            B[i] = sc.nextLong();
        }
        
        long[] prefixA = new long[N + 1];
        long[] prefixB = new long[M + 1];
        
        for (int i = 1; i <= N; i++) {
            prefixA[i] = prefixA[i - 1] + A[i];
        }
        for (int i = 1; i <= M; i++) {
            prefixB[i] = prefixB[i - 1] + B[i];
        }
        
        int maxBooks = 0;
        int j = M;
        for (int i = 0; i <= N; i++) {
            if (prefixA[i] > K) {
                break;
            }
            while (j >= 0 && prefixA[i] + prefixB[j] > K) {
                j--;
            }
            if (j >= 0) {
                maxBooks = Math.max(maxBooks, i + j);
            }
        }
        
        System.out.println(maxBooks);
    }
}