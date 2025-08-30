import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            int N = sc.nextInt();
            int M = sc.nextInt();
            if (N == 0 && M == 0) break;
            
            int[] P = new int[N];
            for (int i = 0; i < N; i++) {
                P[i] = sc.nextInt();
            }
            
            // Generate all possible sums of up to 2 darts
            ArrayList<Integer> sums = new ArrayList<>();
            sums.add(0); // 0 darts
            
            // 1 dart
            for (int i = 0; i < N; i++) {
                if (P[i] <= M) {
                    sums.add(P[i]);
                }
            }
            
            // 2 darts
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    int sum = P[i] + P[j];
                    if (sum <= M) {
                        sums.add(sum);
                    }
                }
            }
            
            Collections.sort(sums);
            
            int maxScore = 0;
            
            // Check combinations of up to 4 darts (sum of two 2-dart sums)
            for (int i = 0; i < sums.size(); i++) {
                int sum1 = sums.get(i);
                if (sum1 > M) break;
                
                int left = M - sum1;
                if (left < 0) continue;
                
                // Binary search the largest sum2 <= left
                int low = 0;
                int high = sums.size() - 1;
                int best = 0;
                
                while (low <= high) {
                    int mid = (low + high) / 2;
                    int sum2 = sums.get(mid);
                    if (sum2 <= left) {
                        best = sum2;
                        low = mid + 1;
                    } else {
                        high = mid - 1;
                    }
                }
                
                int total = sum1 + best;
                if (total <= M && total > maxScore) {
                    maxScore = total;
                }
            }
            
            System.out.println(maxScore);
        }
        sc.close();
    }
}