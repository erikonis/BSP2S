import java.util.Arrays;
import java.util.Scanner;

public class Main {
    private static final int INF = Integer.MAX_VALUE / 2; // Use a large enough value for infinity

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            int n = sc.nextInt();
            if (n == 0) {
                break;
            }

            int[][] adjMatrix = new int[10][10];
            for (int i = 0; i < 10; i++) {
                Arrays.fill(adjMatrix[i], INF);
                adjMatrix[i][i] = 0;
            }

            int maxTownNum = -1;
            for (int i = 0; i < n; i++) {
                int a = sc.nextInt();
                int b = sc.nextInt();
                int c = sc.nextInt();

                adjMatrix[a][b] = Math.min(adjMatrix[a][b], c);
                adjMatrix[b][a] = Math.min(adjMatrix[b][a], c);

                maxTownNum = Math.max(maxTownNum, Math.max(a, b));
            }

            int numTowns = maxTownNum + 1;

            // Floyd-Warshall algorithm
            for (int k = 0; k < numTowns; k++) {
                for (int i = 0; i < numTowns; i++) {
                    for (int j = 0; j < numTowns; j++) {
                        if (adjMatrix[i][k] != INF && adjMatrix[k][j] != INF) {
                            adjMatrix[i][j] = Math.min(adjMatrix[i][j], adjMatrix[i][k] + adjMatrix[k][j]);
                        }
                    }
                }
            }

            int minTotalTime = INF;
            int bestTown = -1;

            for (int i = 0; i < numTowns; i++) {
                int currentTotalTime = 0;
                for (int j = 0; j < numTowns; j++) {
                    currentTotalTime += adjMatrix[i][j];
                }

                if (currentTotalTime < minTotalTime) {
                    minTotalTime = currentTotalTime;
                    bestTown = i;
                }
            }
            System.out.println(bestTown + " " + minTotalTime);
        }
        sc.close();
    }
}