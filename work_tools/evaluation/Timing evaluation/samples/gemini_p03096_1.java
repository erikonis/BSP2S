import java.util.*;

public class Main {
    private static final int MOD = 1_000_000_007;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int[] C = new int[N];
        for (int i = 0; i < N; i++) {
            C[i] = sc.nextInt();
        }
        sc.close();

        // dp[i] will store the number of ways to color the prefix of length i
        // such that C[i-1] is the rightmost element that is not yet determined
        // by a "spanning" operation from a pair of its own color.
        long[] dp = new long[N + 1];
        dp[0] = 1; // Base case: 1 way to color an empty prefix

        // last_occurrence[color] stores the last index where 'color' appeared
        Map<Integer, Integer> last_occurrence = new HashMap<>();

        // sum_dp_prefix[i] stores the sum of dp[0]...dp[i] modulo MOD
        long[] sum_dp_prefix = new long[N + 1];
        sum_dp_prefix[0] = 1;

        for (int i = 1; i <= N; i++) {
            int current_color = C[i - 1];

            // Case 1: The current stone C[i-1] is not covered by any operation
            // that spans across it. This means the prefix up to C[i-1] is
            // determined independently. The number of ways for this case is dp[i-1].
            dp[i] = dp[i - 1];

            // Case 2: The current stone C[i-1] is covered by an operation
            // (j, i-1) where C[j] == C[i-1].
            // If such a j exists, all stones from j to i-1 become C[j].
            // The number of ways to form this state is derived from dp[j].
            // Specifically, if last_occurrence[current_color] = j,
            // then we sum dp[k] for all k from j to i-1.
            // This represents that the segment [k, i-1] becomes current_color,
            // and the prefix up to k-1 is valid.
            // However, a simpler interpretation is that for a stone at index `j`
            // with color `c`, if we find another stone at index `i-1` with color `c`,
            // we can make the range `[j, i-1]` all color `c`.
            // The number of ways to achieve this is the sum of `dp[k]` for `k` from `j` to `i-1`.
            // This sum is `sum_dp_prefix[i-1] - sum_dp_prefix[j-1]`.
            if (last_occurrence.containsKey(current_color)) {
                int prev_idx = last_occurrence.get(current_color);
                // The number of ways to form the sequence up to i-1, where C[prev_idx]...C[i-1]
                // are all current_color, is sum_dp_prefix[i-1] - sum_dp_prefix[prev_idx-1].
                // This means the configuration up to prev_idx-1 is valid, and the segment
                // [prev_idx, i-1] is filled with current_color.
                // Note: sum_dp_prefix[k] is sum of dp[0]...dp[k].
                // So, sum of dp[prev_idx]...dp[i-1] is sum_dp_prefix[i-1] - sum_dp_prefix[prev_idx-1].
                // We add this to dp[i].
                long ways_from_prev_occurrence = (sum_dp_prefix[i - 1] - sum_dp_prefix[prev_idx] + MOD) % MOD;
                dp[i] = (dp[i] + ways_from_prev_occurrence) % MOD;
            }

            // Update sum_dp_prefix for the current index
            sum_dp_prefix[i] = (sum_dp_prefix[i - 1] + dp[i]) % MOD;

            // Update the last occurrence for the current color
            last_occurrence.put(current_color, i - 1);
        }

        System.out.println(dp[N]);
    }
}