import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        long[][] a = new long[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                a[i][j] = sc.nextLong();
            }
        }
        sc.close();

        long[] dp = new long[1 << N];
        // dp[mask] stores the maximum score achievable for the rabbits represented by the set bits in mask.
        // This is not the final answer, but an intermediate score for a subset of rabbits.

        // Calculate scores for all possible single groups (subsets of rabbits)
        // This loop pre-calculates the score for a group if all rabbits in 'mask' were to form one group.
        long[] groupScore = new long[1 << N];
        for (int mask = 1; mask < (1 << N); mask++) {
            long currentScore = 0;
            for (int i = 0; i < N; i++) {
                if ((mask & (1 << i)) != 0) { // If rabbit i is in the current group
                    for (int j = i + 1; j < N; j++) {
                        if ((mask & (1 << j)) != 0) { // If rabbit j is also in the current group
                            currentScore += a[i][j];
                        }
                    }
                }
            }
            groupScore[mask] = currentScore;
        }

        // DP calculation
        // Iterate through all possible subsets of rabbits (masks)
        for (int mask = 1; mask < (1 << N); mask++) {
            // Initialize dp[mask] to a very small value, as we are looking for maximum.
            // A single group containing all rabbits in 'mask' is one possible way to group them.
            dp[mask] = Long.MIN_VALUE;

            // Iterate through all submasks of 'mask'.
            // 'submask' represents one group, and (mask ^ submask) represents the rest of the rabbits.
            // The iteration `submask = (submask - 1) & mask` efficiently generates all submasks.
            // We start with submask = mask, which means the whole set forms one group.
            // This is handled by `dp[mask] = groupScore[mask]` because it's a base case for a single group.
            // We need to find the optimal split into two smaller groups.
            for (int submask = mask; submask > 0; submask = (submask - 1) & mask) {
                // We only need to consider submasks that are proper subsets to avoid redundant calculations
                // and to ensure that we are combining results from smaller problems.
                // If submask == mask, it means all rabbits in 'mask' form one group.
                // This is correctly captured by groupScore[mask].
                // The main loop for dp[mask] should consider splitting 'mask' into two non-empty groups.

                // To ensure that each pair (submask, mask ^ submask) is considered exactly once,
                // we can ensure that `submask` is less than `mask ^ submask`.
                // This effectively picks one of the two partitions and calculates the score.
                // For example, if mask = {1, 2, 3}, and submask = {1}, then mask ^ submask = {2, 3}.
                // We calculate dp[{1}] + dp[{2, 3}] + groupScore[{1}] + groupScore[{2, 3}].
                // No, this is incorrect. We should be combining the scores of dp states for disjoint sets.

                // Correct DP transition:
                // dp[mask] = max(dp[submask] + groupScore[mask ^ submask]) for all proper submasks.
                // This means we are trying to find the best way to form a group from 'mask ^ submask'
                // and combine it with the already optimally grouped 'submask'.
                // The base case for this recurrence is a single group, which is `groupScore[mask]`.
                // So, for each mask, we can either consider it as one big group,
                // or split it into two (or more) smaller groups.
                // The DP state `dp[mask]` should represent the maximum score for rabbits in `mask`,
                // where `mask` is partitioned into *some* number of groups.

                // Let's refine the DP state and transition:
                // dp[mask] = maximum score for rabbits in 'mask', partitioned into any number of groups.
                // Base case: dp[0] = 0 (no rabbits, no score).
                // For each mask:
                //   dp[mask] = groupScore[mask] (option 1: all rabbits in mask form one group)
                //   For each proper submask `sub` of `mask`:
                //     dp[mask] = max(dp[mask], dp[sub] + dp[mask ^ sub])
                // This will result in overcounting group scores if not careful.
                // The `groupScore[mask]` already includes the internal compatibility of rabbits within that group.

                // The standard approach for this type of problem (partitioning a set) is:
                // dp[mask] = max score for the set of rabbits represented by 'mask'.
                // To compute dp[mask], we iterate over all possible first groups that can be formed
                // from the rabbits in 'mask'.
                // Let 'firstRabbit' be the lowest indexed rabbit in 'mask'.
                // We iterate over all submasks 'sub' of 'mask' that contain 'firstRabbit'.
                // 'sub' will be our first group.
                // Then, the remaining rabbits (mask ^ sub) are optimally grouped, which is dp[mask ^ sub].
                // So, dp[mask] = max(groupScore[sub] + dp[mask ^ sub]) for all submasks 'sub' containing 'firstRabbit'.

                int firstRabbit = -1;
                for (int i = 0; i < N; i++) {
                    if ((mask & (1 << i)) != 0) {
                        firstRabbit = i;
                        break;
                    }
                }

                // Iterate over all submasks that contain 'firstRabbit'.
                // A submask 'sub' must contain 'firstRabbit'.
                // So, 'sub' can be formed by (1 << firstRabbit) | 'restOfSubmask',
                // where 'restOfSubmask' is a submask of (mask ^ (1 << firstRabbit)).
                int remainingMask = mask ^ (1 << firstRabbit);
                for (int subOfRemaining = remainingMask; ; subOfRemaining = (subOfRemaining - 1) & remainingMask) {
                    int currentGroupMask = (1 << firstRabbit) | subOfRemaining;
                    dp[mask] = Math.max(dp[mask], groupScore[currentGroupMask] + dp[mask ^ currentGroupMask]);

                    if (subOfRemaining == 0) { // All submasks have been iterated
                        break;
                    }
                }
            }
        }

        System.out.println(dp[(1 << N) - 1]);
    }
}