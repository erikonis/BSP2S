import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        long K = sc.nextLong();
        int[] P = new int[N];
        for (int i = 0; i < N; i++) {
            P[i] = sc.nextInt() - 1;
        }
        long[] C = new long[N];
        for (int i = 0; i < N; i++) {
            C[i] = sc.nextLong();
        }

        long maxScore = Long.MIN_VALUE;

        for (int i = 0; i < N; i++) {
            int current = i;
            long currentPathSum = 0;
            int pathLength = 0;
            long[] pathScores = new long[N + 1]; // To store scores at each step in the cycle

            for (int k = 1; k <= N; k++) { // Max path length is N
                current = P[current];
                currentPathSum += C[current];
                pathLength++;
                pathScores[pathLength] = currentPathSum;

                if (pathLength > K) {
                    break;
                }

                long score = currentPathSum;
                if (pathLength <= K) {
                    maxScore = Math.max(maxScore, score);
                }
            }

            // After finding a cycle
            if (currentPathSum > 0) {
                long numCycles = (K - pathLength) / pathLength;
                long remainingSteps = (K - pathLength) % pathLength;

                if (numCycles > 0) {
                    maxScore = Math.max(maxScore, currentPathSum * numCycles + pathScores[pathLength]);
                    for (int j = 1; j <= remainingSteps; j++) {
                        maxScore = Math.max(maxScore, currentPathSum * numCycles + pathScores[pathLength] + pathScores[j]);
                    }
                }
            }
            
            // Consider paths that don't complete a full cycle, or start their second cycle with a negative sum
            for (int j = 1; j <= Math.min(pathLength, K); j++) {
                maxScore = Math.max(maxScore, pathScores[j]);
            }
        }

        System.out.println(maxScore);
        sc.close();
    }
}