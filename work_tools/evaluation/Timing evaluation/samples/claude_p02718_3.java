import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int M = sc.nextInt();
        
        Integer[] A = new Integer[N];
        int totalVotes = 0;
        
        for (int i = 0; i < N; i++) {
            A[i] = sc.nextInt();
            totalVotes += A[i];
        }
        
        // Sort in descending order
        Arrays.sort(A, Collections.reverseOrder());
        
        // Check if top M items meet the requirement
        double minRequired = (double) totalVotes / (4.0 * M);
        
        boolean canSelect = true;
        for (int i = 0; i < M; i++) {
            if (A[i] < minRequired) {
                canSelect = false;
                break;
            }
        }
        
        System.out.println(canSelect ? "Yes" : "No");
    }
}