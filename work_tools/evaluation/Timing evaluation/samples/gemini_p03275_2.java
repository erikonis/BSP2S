import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int[] a = new int[N];
        for (int i = 0; i < N; i++) {
            a[i] = sc.nextInt();
        }

        int left = 1;
        int right = 1000000000;
        int ans = 0;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (check(mid, a, N)) {
                ans = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        System.out.println(ans);
    }

    private static boolean check(int x, int[] a, int N) {
        long count = 0;
        for (int i = 0; i < N; i++) {
            int[] b = new int[N - i];
            for (int j = i; j < N; j++) {
                b[j - i] = a[j];
            }
            Arrays.sort(b);
            if (b[b.length / 2] >= x) {
                count += (N - i);
            } else {
                // Optimization: If the median of (a_i, ..., a_j) is less than x,
                // then for any k > j, the median of (a_i, ..., a_k) might also be less than x.
                // However, the number of elements in the subarray increases, so the median
                // can also increase.
                // A better approach is to count for all subarrays starting at 'i'.
                // This 'check' function is called for a specific 'x' (potential median).
                // We need to count how many 'm_lr' values are >= x.
                // If this count is >= (total_subarrays + 1) / 2, then 'x' could be the answer.
                // The current implementation of `check` is trying to count how many `m_lr` are >= x.
                // The way it's written, it's not efficient.
                // Let's rewrite the `check` function based on the problem's property.
                // A value `x` is the median of `m` if at least `(total_subarrays + 1) / 2`
                // elements in `m` are `>= x`.

                // The original logic for `check` in these types of problems usually involves
                // transforming the array `a` into `c` where `c[i] = 1` if `a[i] >= x` and `-1` otherwise.
                // Then, for each subarray, count the number of 1s and -1s. If (count_1s >= count_-1s),
                // then the median of that subarray is >= x.
                // This is equivalent to checking if the sum of `c` for the subarray is non-negative.
                // If sum is non-negative, then median >= x.

                // Let's implement the O(N log N) or O(N) check function.
                // For a fixed `x`, create a new array `b` where `b[i] = 1` if `a[i] >= x` and `-1` otherwise.
                // We need to count the number of subarrays `(l, r)` such that the median of `a[l...r]` is `>= x`.
                // This is equivalent to counting subarrays where the count of elements `>= x` is at least
                // the count of elements `< x`.
                // In terms of `b`: sum of `b[l...r]` must be `>= 0`.
                // This is a standard problem: count subarrays with non-negative sum.
                // This can be done in O(N log N) using a Fenwick tree (BIT) or O(N) using a coordinate compression
                // with BIT or by sorting prefix sums.

                // Let's re-evaluate the current `check` implementation assuming it's counting how many `m_lr` are `>= x`.
                // The current `check` is iterating `l` from `0` to `N-1`.
                // For each `l`, it iterates `r` from `l` to `N-1`.
                // It extracts the subarray `a[l...r]`, sorts it, and checks its median.
                // This is O(N^2 * N log N) which is too slow (N^3 log N).

                // Let's use the transformed array `b` and count subarrays with non-negative sum.
                // `b[i] = 1` if `a[i] >= x`, `b[i] = -1` if `a[i] < x`.
                // We need to count `(l, r)` such that `sum(b[l...r]) >= 0`.
                // Let `pref[k]` be the prefix sum `sum(b[0...k-1])`. `pref[0] = 0`.
                // `sum(b[l...r]) = pref[r+1] - pref[l]`.
                // We need `pref[r+1] - pref[l] >= 0`, or `pref[r+1] >= pref[l]`.
                // For each `r`, we need to count `l <= r` such that `pref[l] <= pref[r+1]`.
                // This is a standard BIT problem.

                // Let's make a new `check` function.
                return checkOptimized(x, a, N);
            }
        }
        return false; // Should not reach here with the current `check` structure
    }

    private static boolean checkOptimized(int x, int[] a, int N) {
        // Transform array 'a' into 'b'
        int[] b = new int[N];
        for (int i = 0; i < N; i++) {
            if (a[i] >= x) {
                b[i] = 1;
            } else {
                b[i] = -1;
            }
        }

        // Calculate prefix sums
        int[] pref = new int[N + 1];
        pref[0] = 0;
        for (int i = 0; i < N; i++) {
            pref[i + 1] = pref[i] + b[i];
        }

        // Coordinate compress prefix sums
        Set<Integer> distinctPrefixSums = new TreeSet<>();
        for (int p : pref) {
            distinctPrefixSums.add(p);
        }
        Map<Integer, Integer> coordMap = new HashMap<>();
        int id = 0;
        for (int p : distinctPrefixSums) {
            coordMap.put(p, id++);
        }

        // Use Fenwick Tree (BIT) to count pairs
        FenwickTree ft = new FenwickTree(distinctPrefixSums.size());
        long count = 0;
        for (int i = 0; i <= N; i++) {
            // For current pref[i], count how many pref[j] (j < i) satisfy pref[j] <= pref[i]
            // Add to count: ft.query(coordMap.get(pref[i]) + 1)
            count += ft.query(coordMap.get(pref[i]) + 1);
            // Add pref[i] to BIT
            ft.update(coordMap.get(pref[i]), 1);
        }

        // Total number of subarrays is N * (N + 1) / 2
        long totalSubarrays = (long) N * (N + 1) / 2;
        // We need to find the median of 'm'. This means we need the value 'X' such that
        // at least (totalSubarrays + 1) / 2 elements in 'm' are >= X.
        // The `check` function should return true if `count >= (totalSubarrays + 1) / 2`.
        return count * 2 >= totalSubarrays + 1;
    }

    static class FenwickTree {
        int[] bit;
        int size;

        public FenwickTree(int size) {
            this.size = size;
            bit = new int[size + 1];
        }

        // Update value at index `idx` by `delta`
        public void update(int idx, int delta) {
            idx++; // 1-based indexing for BIT
            while (idx <= size) {
                bit[idx] += delta;
                idx += idx & (-idx);
            }
        }

        // Query sum from index 0 to `idx`
        public int query(int idx) {
            idx++; // 1-based indexing for BIT
            int sum = 0;
            while (idx > 0) {
                sum += bit[idx];
                idx -= idx & (-idx);
            }
            return sum;
        }
    }
}