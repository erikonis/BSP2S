import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int M = sc.nextInt();
        
        int[] A = new int[N];
        int totalVotes = 0;
        
        for (int i = 0; i < N; i++) {
            A[i] = sc.nextInt();
            totalVotes += A[i];
        }
        
        double threshold = (double) totalVotes / (4.0 * M);
        int eligibleCount = 0;
        
        for (int i = 0; i < N; i++) {
            if (A[i] >= threshold) {
                eligibleCount++;
            }
        }
        
        if (eligibleCount >= M) {
            System.out.println("Yes");
        } else {
            System.out.println("No");
        }
    }
}