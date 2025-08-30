import java.util.*;

public class Main {
    static int[] vehicles = {4, 4, 2, 2, 1, 1, 1, 1}; // 4,4,2,2,1,1,1,1
    static int[] bestAssignment = new int[8];
    static int minUnseated;
    static long minV;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextInt()) {
            int[] people = new int[8];
            for (int i = 0; i < 8; i++) people[i] = sc.nextInt();

            minUnseated = Integer.MAX_VALUE;
            minV = Long.MAX_VALUE;
            // Try all 8 possible rotations
            for (int r = 0; r < 8; r++) {
                int[] assign = new int[8];
                for (int i = 0; i < 8; i++) {
                    assign[(i + r) % 8] = vehicles[i];
                }
                int unseated = 0;
                for (int i = 0; i < 8; i++) {
                    unseated += Math.max(people[i] - assign[i], 0);
                }
                long V = 0;
                for (int i = 0; i < 8; i++) {
                    V = V * 10 + assign[i];
                }
                if (unseated < minUnseated || (unseated == minUnseated && V < minV)) {
                    minUnseated = unseated;
                    minV = V;
                    System.arraycopy(assign, 0, bestAssignment, 0, 8);
                }
            }
            // Output
            for (int i = 0; i < 8; i++) {
                if (i > 0) System.out.print(" ");
                System.out.print(bestAssignment[i]);
            }
            System.out.println();
        }
    }
}