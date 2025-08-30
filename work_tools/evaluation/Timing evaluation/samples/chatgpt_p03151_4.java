import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        long[] A = new long[n];
        long[] B = new long[n];

        long sumA = 0, sumB = 0;
        for (int i = 0; i < n; i++) {
            A[i] = sc.nextLong();
            sumA += A[i];
        }
        for (int i = 0; i < n; i++) {
            B[i] = sc.nextLong();
            sumB += B[i];
        }

        // If total readiness required > readiness available, impossible
        if (sumB > sumA) {
            System.out.println(-1);
            return;
        }

        // Calculate how much needs to be increased at each index
        List<Long> surplus = new ArrayList<>();
        long need = 0;
        int changes = 0;

        for (int i = 0; i < n; i++) {
            if (A[i] < B[i]) {
                need += B[i] - A[i];
                changes++;
            } else if (A[i] > B[i]) {
                surplus.add(A[i] - B[i]);
            }
        }

        // Sort surplus in descending order for greedy distribution
        surplus.sort(Collections.reverseOrder());
        int idx = 0;
        while (need > 0 && idx < surplus.size()) {
            long use = Math.min(need, surplus.get(idx));
            need -= use;
            changes++;
            idx++;
        }

        System.out.println(need == 0 ? changes : -1);
    }
}