import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int S = sc.nextInt();
        int[] A = new int[N];
        for (int i = 0; i < N; i++) {
            A[i] = sc.nextInt();
        }

        long MOD = 998244353;

        // dp[j] will store the number of ways to form sum j using elements considered so far.
        // The value stored is (number of ways) * (2^k) where k is the number of elements
        // in A that have been processed and are greater than A[i].
        long[] dp = new long[S + 1];
        dp[0] = 1; // One way to make sum 0 (empty set)

        long totalSum = 0;

        for (int i = 0; i < N; i++) {
            // Create a new DP array for the current iteration to avoid using updated values
            // for the same A[i] within the inner loop
            long[] next_dp = new long[S + 1];
            System.arraycopy(dp, 0, next_dp, 0, S + 1);

            for (int j = 0; j <= S; j++) {
                if (dp[j] == 0) continue;

                // Option 1: Don't include A[i] in the current subset.
                // The coefficient 2 comes from the fact that for each element A[k] (k > i)
                // we can either include it in T or not.
                // This is implicit in the DP state: dp[j] already represents
                // sum `j` from a subset of elements up to `A[i-1]`, and each such element
                // `A[k]` (k < i) that was *not* chosen for the sum `j` effectively
                // contributes a factor of 2 to the count of `T`s it belongs to.
                // When we process A[i], for all sums `j` that don't include A[i],
                // the existing count `dp[j]` effectively gets multiplied by 2,
                // because A[i] can either be in T or not.
                // However, the problem asks for sum of f(T) over all T.
                // f(T) is the count of subsets {x1, ..., xk} of T such that sum A_x = S.
                // Let's re-think the DP state.

                // Let dp[j] be the sum of 2^(N - 1 - index of last element used) over all
                // subsets summing to j.

                // Let's define dp[j] as the number of ways to form sum `j` using a subsequence
                // of A[0...i-1], where each `way` is counted as 2^(count of elements A[k] with k < i
                // that were *not* used in the sum `j`).
                // This seems overly complicated and doesn't directly give f(T).

                // Alternative approach: Iterate through each element A[i].
                // For each sum `j` we can form, we need to consider how many `T`s it belongs to.
                // A sum `S` formed by a subset `P` (indices) of `{1, ..., N}` contributes to `f(T)`
                // if `P` is a subset of `T`.
                // For a fixed subset `P` that sums to `S`, it contributes `1` to `f(T)` for every `T`
                // such that `P` is a subset of `T`.
                // If `P` has `k` elements, it is a subset of `2^(N-k)` sets `T`.
                // So, the total sum is sum over all `P` summing to `S` of `2^(N - |P|)`.

                // Let dp[j] be the number of subsets of A[0...i-1] that sum to `j`.
                // When considering A[i]:
                // 1. Don't include A[i] in the sum: dp[j] remains dp[j].
                // 2. Include A[i] in the sum: dp[j] += dp[j - A[i]].
                // This is standard subset sum.

                // We need to count `2^(N - |P|)`.
                // Let dp[j][k] be the number of subsets of size `k` that sum to `j`.
                // This would be O(N*S*N), which is too slow (3000^3).

                // Let's go back to the original interpretation of DP and try to incorporate the 2^(N-k) factor.
                // Let dp[j] be the sum of 2^(N - number of elements used so far) for all ways to make sum j.
                // This is also not quite right.

                // Let's use the property that for each subset of indices P that sums to S, it contributes
                // 2^(N - |P|) to the final answer.
                // So we need to calculate sum(2^(N - |P|)) over all P such that sum(A_p for p in P) = S.

                // Define dp[j] as the sum of 2^(N - count_of_elements_in_P) for all subsets P that sum to j.
                // When we process A[i]:
                // For each sum `j` that was achievable *before* considering A[i]:
                // 1. We can form `j` using the old subsets. The exponent `N - |P|` doesn't change relative to `N`.
                //    But the `2` factor needs to be considered.
                //    Each `dp[j]` value represents `2^(N - |P|)`.
                //    When we consider `A[i]`, for all the subsets `P` that sum to `j`,
                //    we can either include `A[i]` in `T` or not.
                //    If `A[i]` is not in `T`, then these `P` still contribute.
                //    If `A[i]` is in `T`, then these `P` still contribute.

                // This is tricky. Let's simplify the sum we want to calculate:
                // Sum over all non-empty subsets P of {1, ..., N} such that sum(A_p for p in P) = S,
                // of 2^(N - |P|).

                // Let dp[j] be the number of ways to pick a subset of elements from A[0...i-1] that sum to j.
                // When processing A[i]:
                // We want to calculate the new contributions.
                // For each `j` from `S` down to `A[i]`:
                //   dp[j] = (dp[j] * 2 + dp[j - A[i]]) % MOD
                // The `* 2` factor comes from the fact that for all previous sums `j`,
                // the element `A[i]` can either be part of `T` or not.
                // `dp[j - A[i]]` means we are forming `j` by adding `A[i]` to a previous sum `j - A[i]`.
                // In this case, `A[i]` *is* part of the subset that sums to `S`.

                // Let dp[j] be "the sum of 2^(count of elements from A[0...i-1] *not* used in sum j)"
                // over all subsets that sum to j using A[0...i-1].
                // This doesn't seem to work with the N-|P| exponent.

                // Let's use a standard DP for subset sum, but maintain the factor.
                // dp[j] = sum of 2^(count of elements from {A_1, ..., A_k} *not* used in sum j)
                // where A_k is the last element processed.

                // Initialize dp[0] = 1 (empty set, 0 elements used, 2^0 = 1 contribution)
                // For each A[i]:
                //   For j from S down to A[i]:
                //     dp[j] = (dp[j] * 2 + dp[j - A[i]]) % MOD
                // This means:
                //   - (dp[j] * 2): For sums `j` that *don't* include A[i], the number of subsets `P`
                //     that sum to `j` is doubled because `A[i]` can be either included or excluded
                //     from the original set `T`.
                //   - (dp[j - A[i]]): For sums `j` that *do* include A[i], we take the ways to make
                //     `j - A[i]` and add `A[i]`. The `2` factor is not applied here because `A[i]`
                //     is now part of the sum.

                // Let's trace Sample 1: N=3, S=4, A={2,2,4}
                // Initial: dp = [1, 0, 0, 0, 0] (for sums 0 to 4)

                // i = 0, A[0] = 2
                // next_dp = [1, 0, 0, 0, 0]
                // j = 4: next_dp[4] = (dp[4]*2 + dp[2]) = (0*2 + 0) = 0
                // j = 3: next_dp[3] = (dp[3]*2 + dp[1]) = (0*2 + 0) = 0
                // j = 2: next_dp[2] = (dp[2]*2 + dp[0]) = (0*2 + 1) = 1
                // j = 1: next_dp[1] = (dp[1]*2 + dp[-1]) = (0*2 + 0) = 0
                // j = 0: next_dp[0] = (dp[0]*2 + dp[-2]) = (1*2 + 0) = 2
                // dp becomes [2, 0, 1, 0, 0] (This is incorrect. dp[0] should be 1 always for empty set)

                // The definition of dp[j] should be: the number of ways to form sum j using elements
                // considered so far, where each way is weighted by 2^(number of elements not chosen from *current* T).
                // This is extremely confusing.

                // Let's use the explicit sum formula: sum(2^(N - |P|)) over all P summing to S.
                // We need to count subsets P that sum to S and their sizes.

                // dp[j] = sum of (2^(count of elements from A[0...i-1] *not* used to form sum j))
                //         for all subsets of A[0...i-1] that sum to j.
                //
                // Initialize dp[0] = 1 (empty set, 0 elements used, exponent 0, 2^0 = 1).
                // For each A[i]:
                //   Create a temporary array `new_dp` based on `dp`.
                //   For j from S down to A[i]:
                //     new_dp[j] = (new_dp[j] + dp[j - A[i]]) % MOD
                //   After iterating through j, `dp` becomes `new_dp`.
                //   Then, after processing all elements A[0...i], multiply all `dp[j]` by 2.
                //   This accounts for the `2^(N-|P|)` factor.
                //   The `2` comes from `A[i]` not being included in the sum `j`, but being part of `T`.

                // Let dp[j] be the sum of 2^(N - number of elements in P_j) over all subsets P_j that sum to j.
                // This doesn't work because N is fixed.

                // Let's use a dynamic programming approach where `dp[j]` stores the sum of `2^k` for all subsets
                // that sum to `j`, where `k` is the number of *remaining* elements (elements `A[x]` where `x > i`).

                // Let `dp[j]` be the number of ways to choose a subset of `A[0...i-1]` that sums to `j`.
                // This includes the implicit factor for elements not chosen.
                // The `f(T)` definition is tricky. `f(T)` is the number of distinct non-empty subsets `{x_1, ..., x_k}` of `T`
                // such that `A_{x_1} + ... + A_{x_k} = S`.

                // Let's iterate through `A_i` in reverse order.
                // This doesn't simplify the `2^(N-|P|)` part.

                // Let's try the simpler interpretation of total sum:
                // For each `A_i`, it can be part of a sum `S` (contributing to `dp[S]`) or not.
                // And for each `T`, `A_i` can be in `T` or not.

                // Consider the total sum as sum over all subsets `P = {x_1, ..., x_k}` such that `sum(A_x) = S`.
                // For each such `P`, it contributes `1` to `f(T)` for every `T` that contains `P`.
                // The number of such `T`s is `2^(N - |P|)`.
                // So the total sum is `sum_{P: sum(A_p)=S} 2^(N - |P|)`.

                // We can compute this using a DP: `dp[j]` stores `sum_{P: sum(A_p)=j} 2^(N - |P|)`.
                // This requires knowing `|P|`. So `dp[j][k]` = ways to sum to `j` using `k` elements.
                // `dp[j][k]` = `dp[j][k] + dp[j - A[i]][k - 1]` (if we use `A[i]`)
                // This is O(N*S*N), too slow.

                // Let `dp[j]` denote `sum_{P: sum(A_p)=j} 2^(-|P|)`.
                // Then the final answer would be `dp[S] * 2^N`.
                // `2^(-|P|)` is `(1/2)^|P|`.
                // `dp[j]` = `sum_{P: sum(A_p)=j} (1/2)^|P|`.
                // When we consider `A[i]`:
                // For each `j` from `S` down to `A[i]`:
                //   `dp[j]` (new) = `dp[j]` (old) + `dp[j - A[i]]` (old) * `(1/2)`
                // The `dp[j]` (old) part corresponds to subsets that sum to `j` *without* `A[i]`.
                // The `dp[j - A[i]] * (1/2)` part corresponds to subsets that sum to `j` *with* `A[i]`.
                // If `P'` sums to `j - A[i]` and has size `k'`, then `P = P' U {A[i]}` sums to `j` and has size `k'+1`.
                // So its contribution is `(1/2)^(k'+1) = (1/2)^k' * (1/2)`.
                // So `sum_{(P', A[i])} (1/2)^(|P'|+1) = (sum_{(P')} (1/2)^|P'|) * (1/2)`.
                // This seems correct!

                // `(1/2)` needs to be `(MOD+1)/2` (modular inverse of 2).
                long inv2 = 499122177; // (998244353 + 1) / 2

                // Initialize dp[0] = 1 (empty set, |P|=0, (1/2)^0 = 1)
                // For each A[i]:
                //   For j from S down to A[i]:
                //     dp[j] = (dp[j] + dp[j - A[i]] * inv2) % MOD
                //
                // After the loop, the answer is dp[S] * 2^N % MOD.

                // Let's trace Sample 1: N=3, S=4, A={2,2,4}
                // inv2 = 499122177
                // Initial dp = [1, 0, 0, 0, 0]

                // i = 0, A[0] = 2
                // j = 4: dp[4] = (dp[4] + dp[2]*inv2) = (0 + 0*inv2) = 0
                // j = 3: dp[3] = (dp[3] + dp[1]*inv2) = (0 + 0*inv2) = 0
                // j = 2: dp[2] = (dp[2] + dp[0]*inv2) = (0 + 1*inv2) = inv2
                // j = 1: dp[1] = (dp[1] + dp[-1]*inv2) = 0
                // dp = [1, 0, inv2, 0, 0]

                // i = 1, A[1] = 2
                // j = 4: dp[4] = (dp[4] + dp[2]*inv2) = (0 + inv2*inv2) % MOD
                //               = (499122177 * 499122177) % MOD = 249122177000000000 % 998244353 = 249561089
                // j = 3: dp[3] = (dp[3] + dp[1]*inv2) = (0 + 0*inv2) = 0
                // j = 2: dp[2] = (dp[2] + dp[0]*inv2) = (inv2 + 1*inv2) % MOD = (2*inv2) % MOD = 1
                // dp = [1, 0, 1, 0, 249561089]

                // i = 2, A[2] = 4
                // j = 4: dp[4] = (dp[4] + dp[0]*inv2) = (249561089 + 1*inv2) % MOD
                //               = (249561089 + 499122177) % MOD = 748683266 % MOD
                // dp = [1, 0, 1, 0, 748683266]

                // Final answer = dp[S] * 2^N % MOD
                // dp[4] = 748683266
                // N = 3, 2^N = 8
                // Ans = (748683266 * 8) % MOD = 5989466128 % 998244353 = 6

                // This logic seems sound and matches Sample 1!

                // Let's implement this.
            }
        }

        long inv2 = 499122177; // (MOD + 1) / 2
        
        long[] dp = new long[S + 1];
        dp[0] = 1; // Represents the empty set, sum 0, |P|=0, (1/2)^0 = 1

        for (int i = 0; i < N; i++) {
            for (int j = S; j >= A[i]; j--) {
                dp[j] = (dp[j] + (dp[j - A[i]] * inv2) % MOD) % MOD;
            }
        }

        long ans = (dp[S] * pow(2, N, MOD)) % MOD;

        System.out.println(ans);

        sc.close();
    }

    // Modular exponentiation
    public static long pow(long base, long exp, long mod) {
        long res = 1;
        base %= mod;
        while (exp > 0) {
            if (exp % 2 == 1) res = (res * base) % mod;
            base = (base * base) % mod;
            exp /= 2;
        }
        return res;
    }
}