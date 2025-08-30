import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int[] a = new int[N];
        for (int i = 0; i < N; i++) {
            a[i] = sc.nextInt();
        }

        // The possible values for the median of m are the values present in a.
        // We can binary search on the answer.
        // The search space is from 1 to 10^9.
        int low = 1;
        int high = 1_000_000_000;
        int ans = 0;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (check(mid, a, N)) {
                ans = mid;
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        System.out.println(ans);
    }

    // This function checks if the number of medians (m_l,r) that are >= val is
    // greater than or equal to (total_subsequences / 2) + 1.
    // If it is, then 'val' could be our answer, or something larger.
    // Otherwise, 'val' is too large.
    private static boolean check(int val, int[] a, int N) {
        long count = 0;
        long totalSubsequences = (long) N * (N + 1) / 2;
        long targetCount = (totalSubsequences / 2) + 1;

        // Iterate through all possible starting points l.
        for (int l = 0; l < N; l++) {
            // Maintain a data structure that allows finding the median efficiently.
            // For each subsequence (a_l, ..., a_r), we need its median.
            // When we fix 'l', as 'r' increases, we add one element.
            // We can use a Fenwick tree (BIT) or segment tree to count elements
            // greater/smaller than a certain value.
            // However, the values of a_i are large (10^9).
            // We can transform the array 'a' into a binary array for a given 'val'.
            // Let b_i = 1 if a_i >= val, and b_i = -1 if a_i < val.
            // The median of a subsequence (a_l, ..., a_r) is >= val if and only if
            // the number of elements >= val is greater than or equal to the number of
            // elements < val.
            // This is equivalent to saying that the sum of b_i for that subsequence is >= 0.

            // Create the transformed array `b` for the current `val`.
            int[] b = new int[N];
            for (int i = 0; i < N; i++) {
                if (a[i] >= val) {
                    b[i] = 1;
                } else {
                    b[i] = -1;
                }
            }

            // Now, we need to count the number of subarrays (l, r) in `b` such that
            // sum(b_l, ..., b_r) >= 0.
            // This is a standard problem that can be solved using prefix sums and a Fenwick tree.
            // Let S_k be the prefix sum of `b` up to index k (S_{-1} = 0).
            // Sum(b_l, ..., b_r) = S_r - S_{l-1}.
            // We want S_r - S_{l-1} >= 0, which means S_r >= S_{l-1}.

            // Coordinate compression for prefix sums.
            // The possible prefix sums range from -N to N.
            // We add N to all prefix sums to make them non-negative.
            // The range becomes 0 to 2N.
            int[] prefixSums = new int[N + 1];
            prefixSums[0] = 0; // S_{-1} in the original formulation
            for (int i = 0; i < N; i++) {
                prefixSums[i + 1] = prefixSums[i] + b[i];
            }

            // To map prefix sums to indices for the Fenwick tree, we need to shift them.
            // The minimum possible prefix sum is -N, max is N.
            // So, we add N to all prefix sums. They will range from 0 to 2N.
            // The Fenwick tree size will be 2N + 1.
            FenwickTree ft = new FenwickTree(2 * N + 1);

            // Iterate through prefix sums. For each S_r, we want to count how many S_{l-1}
            // satisfy S_r >= S_{l-1}.
            // When we are at index 'r', we consider S_r. We need to count S_{l-1} where l-1 < r.
            // This means we need to query the count of prefix sums that have appeared so far
            // and are less than or equal to S_r.
            // So, we iterate r from 0 to N (representing S_{-1} to S_{N-1}).
            // First, add S_{r-1} to the Fenwick tree. Then query for S_r.

            // Initialize with S_{-1} = 0.
            ft.update(prefixSums[0] + N, 1); // Add the count for S_{-1} = 0.

            for (int r = 1; r <= N; r++) { // Iterate through S_0 to S_N
                // For the current S_r, count S_{l-1} such that S_r >= S_{l-1}.
                // These S_{l-1} values are prefixSums[0] to prefixSums[r-1].
                // We query the Fenwick tree for the count of values <= prefixSums[r] + N.
                count += ft.query(prefixSums[r] + N);

                // Add the current prefix sum prefixSums[r] to the Fenwick tree for future queries.
                ft.update(prefixSums[r] + N, 1);
            }
        }
        return count >= targetCount;
    }

    static class FenwickTree {
        int[] bit;
        int size;

        public FenwickTree(int size) {
            this.size = size;
            bit = new int[size + 1];
        }

        // Add val to index idx (1-based)
        public void update(int idx, int val) {
            idx++; // Convert to 1-based index
            while (idx <= size) {
                bit[idx] += val;
                idx += idx & (-idx);
            }
        }

        // Get sum of elements from 1 to idx (0-based)
        public int query(int idx) {
            idx++; // Convert to 1-based index
            int sum = 0;
            while (idx > 0) {
                sum += bit[idx];
                idx -= idx & (-idx);
            }
            return sum;
        }
    }
}