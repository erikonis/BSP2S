import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        long[] A = new long[N];
        long[] B = new long[N];
        long sumA = 0;
        long sumB = 0;

        for (int i = 0; i < N; i++) {
            A[i] = sc.nextLong();
            sumA += A[i];
        }
        for (int i = 0; i < N; i++) {
            B[i] = sc.nextLong();
            sumB += B[i];
        }

        // If sumA < sumB, it's impossible to satisfy B_i <= C_i for all i
        // because sumC must be equal to sumA and sumC >= sumB.
        if (sumA < sumB) {
            System.out.println(-1);
            return;
        }

        long diff = sumA - sumB;
        int changes = 0;
        long needed = 0;
        List<Long> surplus = new ArrayList<>();

        for (int i = 0; i < N; i++) {
            if (A[i] < B[i]) {
                // Takahashi needs more readiness for this exam
                needed += (B[i] - A[i]);
                changes++; // This exam definitely needs a change
            } else if (A[i] > B[i]) {
                // Takahashi has surplus readiness for this exam
                surplus.add(A[i] - B[i]);
            }
            // If A[i] == B[i], no change is needed for this exam if we don't touch it.
        }

        // If the total readiness needed is greater than the total surplus available (sumA - sumB),
        // it's impossible. This case is implicitly covered by sumA < sumB check
        // if we consider that `needed` is the minimum required increase to meet all B_i,
        // and `diff` is the total available "extra" readiness to distribute.
        // If `needed` > `diff`, then it's impossible.
        if (needed > diff) {
            System.out.println(-1);
            return;
        }

        // Sort surplus in descending order to use the largest surpluses first
        // to cover the 'needed' amount with the fewest changes.
        Collections.sort(surplus, Collections.reverseOrder());

        // Distribute surplus to cover the 'needed' amount
        for (long s : surplus) {
            if (needed <= 0) {
                break; // All needed readiness has been covered
            }
            needed -= s;
            changes++; // Each surplus used implies a change in A_i to C_i
        }

        // After using all necessary changes and surpluses, if 'needed' is still > 0,
        // it means we couldn't meet all B_i requirements. This should not happen
        // if sumA >= sumB and we correctly calculated `needed` and `diff`.
        // The check `if (needed > diff)` above handles this.
        // If `needed` is <= 0 here, it means we successfully met all B_i requirements.
        // The number of changes is `changes`.

        System.out.println(changes);

        sc.close();
    }
}