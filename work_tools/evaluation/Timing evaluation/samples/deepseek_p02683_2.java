import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int M = sc.nextInt();
        int X = sc.nextInt();
        
        int[] C = new int[N];
        int[][] A = new int[N][M];
        
        for (int i = 0; i < N; i++) {
            C[i] = sc.nextInt();
            for (int j = 0; j < M; j++) {
                A[i][j] = sc.nextInt();
            }
        }
        
        int minCost = Integer.MAX_VALUE;
        
        for (int mask = 0; mask < (1 << N); mask++) {
            int cost = 0;
            int[] sum = new int[M];
            
            for (int i = 0; i < N; i++) {
                if ((mask & (1 << i)) != 0) {
                    cost += C[i];
                    for (int j = 0; j < M; j++) {
                        sum[j] += A[i][j];
                    }
                }
            }
            
            boolean ok = true;
            for (int j = 0; j < M; j++) {
                if (sum[j] < X) {
                    ok = false;
                    break;
                }
            }
            
            if (ok && cost < minCost) {
                minCost = cost;
            }
        }
        
        System.out.println(minCost == Integer.MAX_VALUE ? -1 : minCost);
    }
}