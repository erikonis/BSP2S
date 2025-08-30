import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int[] x = new int[N];
        for (int i = 0; i < N; i++) {
            x[i] = sc.nextInt() - 1; // 0-indexed
        }
        sc.close();

        int[] a = new int[N * N];
        Arrays.fill(a, 0); // 0 indicates an empty slot

        // Store pairs of (x_i, i) and sort them by x_i
        // This helps in processing elements in increasing order of their required positions
        int[][] sortedX = new int[N][2];
        for (int i = 0; i < N; i++) {
            sortedX[i][0] = x[i]; // position
            sortedX[i][1] = i + 1; // value
        }
        Arrays.sort(sortedX, (o1, o2) -> Integer.compare(o1[0], o2[0]));

        // Iterate through each number from 1 to N
        for (int i = 0; i < N; i++) {
            int val = sortedX[i][1]; // The number we are placing (1 to N)
            int targetPos = sortedX[i][0]; // The required position for its 'val'-th occurrence

            int count = 0; // Count of 'val' encountered so far
            int emptySlots = 0; // Count of empty slots encountered so far

            // Find the target position for the 'val'-th occurrence of 'val'
            // This loop places the 'val'-th occurrence of 'val' at targetPos
            int currentPos = -1;
            for (int k = 0; k < N * N; k++) {
                if (a[k] == 0) { // If slot is empty
                    emptySlots++;
                } else if (a[k] == val) { // If slot contains 'val'
                    count++;
                }

                if (emptySlots == targetPos + 1) { // targetPos is 0-indexed, so we need targetPos + 1 empty slots
                    // This means a[k] is the (targetPos + 1)-th empty slot
                    // And this slot should be the 'val'-th occurrence of 'val'
                    if (a[k] != 0) { // If the slot is not empty, it's a conflict
                        System.out.println("No");
                        return;
                    }
                    a[k] = val; // Place the number
                    currentPos = k;
                    break;
                }
            }

            if (currentPos == -1) { // If target position was not found (not enough empty slots)
                System.out.println("No");
                return;
            }

            // Fill the remaining (N-1) occurrences of 'val'
            // We need to place 'val' (val-1) times before currentPos and (N-val) times after currentPos
            int placedBefore = 0;
            // Place (val - 1) occurrences before currentPos
            for (int k = 0; k < currentPos && placedBefore < val - 1; k++) {
                if (a[k] == 0) {
                    a[k] = val;
                    placedBefore++;
                }
            }

            if (placedBefore < val - 1) { // Not enough empty slots before currentPos
                System.out.println("No");
                return;
            }

            int placedAfter = 0;
            // Place (N - val) occurrences after currentPos
            for (int k = currentPos + 1; k < N * N && placedAfter < N - val; k++) {
                if (a[k] == 0) {
                    a[k] = val;
                    placedAfter++;
                }
            }

            if (placedAfter < N - val) { // Not enough empty slots after currentPos
                System.out.println("No");
                return;
            }
        }

        // Final check: Ensure all slots are filled and counts are correct
        // This check is implicitly handled by the placement logic, but good to be explicit
        // The problem statement guarantees all x_i are distinct, which helps.
        // If any slot is still 0, it means we couldn't fill it with any number.
        for (int k = 0; k < N * N; k++) {
            if (a[k] == 0) {
                System.out.println("No");
                return;
            }
        }


        // Verify the conditions
        for (int val = 1; val <= N; val++) {
            int count = 0;
            int targetX = -1;
            for(int j=0; j<N; j++) {
                if (sortedX[j][1] == val) {
                    targetX = sortedX[j][0];
                    break;
                }
            }

            int actualOccurrenceCount = 0;
            for (int k = 0; k < N * N; k++) {
                if (a[k] == val) {
                    actualOccurrenceCount++;
                    if (actualOccurrenceCount == val) { // This is the 'val'-th occurrence
                        if (k != targetX) {
                            System.out.println("No");
                            return;
                        }
                    }
                }
            }
            // Check if 'val' appeared N times
            if (actualOccurrenceCount != N) {
                System.out.println("No");
                return;
            }
        }


        System.out.println("Yes");
        for (int k = 0; k < N * N; k++) {
            System.out.print(a[k] + (k == N * N - 1 ? "" : " "));
        }
        System.out.println();
    }
}