import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        while (true) {
            int n = sc.nextInt();
            if (n == 0) break;
            
            int[] scores = new int[n];
            for (int i = 0; i < n; i++) {
                scores[i] = sc.nextInt();
            }
            
            Arrays.sort(scores);
            
            int minDiff = Integer.MAX_VALUE;
            for (int i = 0; i < n - 1; i++) {
                int diff = scores[i + 1] - scores[i];
                minDiff = Math.min(minDiff, diff);
            }
            
            System.out.println(minDiff);
        }
        
        sc.close();
    }
}