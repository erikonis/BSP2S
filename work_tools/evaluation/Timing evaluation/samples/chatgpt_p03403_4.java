import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        int[] A = new int[N + 2];
        A[0] = 0;
        for (int i = 1; i <= N; i++) {
            A[i] = sc.nextInt();
        }
        A[N + 1] = 0;

        // Precompute the total cost for the original route
        long totalCost = 0;
        for (int i = 0; i <= N; i++) {
            totalCost += Math.abs(A[i + 1] - A[i]);
        }

        // For each spot, compute the cost when skipping it
        for (int i = 1; i <= N; i++) {
            long removed = Math.abs(A[i] - A[i - 1]) + Math.abs(A[i + 1] - A[i]);
            long added = Math.abs(A[i + 1] - A[i - 1]);
            System.out.println(totalCost - removed + added);
        }
    }
}