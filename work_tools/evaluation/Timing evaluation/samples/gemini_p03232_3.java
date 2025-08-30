import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Arrays;

public class Main {
    private static final int MOD = 1_000_000_007;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());
        long[] A = Arrays.stream(br.readLine().split(" "))
                         .mapToLong(Long::parseLong)
                         .toArray();

        long totalSumOfCosts = 0;

        // Calculate the sum of all A_i values
        long sumA = 0;
        for (long val : A) {
            sumA = (sumA + val) % MOD;
        }

        // Precompute powers of 2 (2^(N-1), 2^(N-2), ..., 2^0)
        long[] pow2 = new long[N];
        pow2[0] = 1;
        for (int i = 1; i < N; i++) {
            pow2[i] = (pow2[i - 1] * 2) % MOD;
        }

        // The contribution of the first block removed
        // When the first block is removed, all N blocks are connected.
        // So the cost is sumA.
        // There are N! ways to choose the order, so N! * sumA
        long N_factorial = 1;
        for (int i = 1; i <= N; i++) {
            N_factorial = (N_factorial * i) % MOD;
        }
        totalSumOfCosts = (N_factorial * sumA) % MOD;

        // Contribution of subsequent removals
        // Consider a block A_i. For A_i to be part of a connected component of size K
        // when some block is removed.
        // The key insight is that for any block A_i, it will contribute A_i to the cost
        // if it is part of the connected component being removed.
        // Let's fix a block A_i. We want to find how many times A_i contributes to the total cost.
        // A_i contributes to the cost if it is part of the connected component that contains the
        // block being removed.
        // This means A_i contributes when some block X (where X is in the same connected segment as A_i)
        // is removed, and A_i is still present.

        // For any block A_i, it will be part of a connected component when it is removed.
        // It will also be part of the connected component when any block in its current
        // segment is removed.
        // The total sum of costs is sum over all orders (sum over all operations (cost of operation)).
        // This is equal to sum over all blocks A_i (A_i * (number of times A_i contributes)).

        // For each block A_i, calculate its contribution.
        // A_i contributes to the cost if it is "connected" to the removed block.
        // This means A_i is still in the sequence, and the removed block is within the
        // continuous segment of unremoved blocks that contains A_i.

        // Consider the state where i blocks have been removed.
        // There are N-i blocks remaining.
        // The number of permutations is N!.
        // When we sum over all N! permutations, it's easier to consider the contribution
        // of each A_k.
        // A_k contributes A_k to the total cost if it's part of the connected component that is removed.

        // Let's analyze the contribution of A_k.
        // A_k contributes A_k to the cost if it is part of the connected component whose member is removed.
        // This happens if A_k is still present and the removed block is P, where P is in the
        // contiguous segment containing A_k.

        // Consider the segment [L, R] of blocks that are currently present.
        // If we remove block X (L <= X <= R), the cost is sum(A_j for j from L to R).
        // This sum is accumulated for all blocks in [L, R].

        // Let's re-evaluate the sum of costs for a block A_i.
        // A block A_i contributes to the cost if it is part of the connected component that is removed.
        // This happens when some block B is removed, and B is in the same connected component as A_i.
        // The first block removed costs sumA. This accounts for N! * sumA.
        // What about subsequent removals?

        // Consider any block A_i.
        // When is A_i involved in a cost calculation?
        // A_i is involved if it is part of the connected component of the currently removed block.
        // This means A_i is still present, and the block being removed is X, where X is in the
        // continuous segment of unremoved blocks that contains A_i.

        // The total sum of costs is sum over all N! permutations.
        // Let's use linearity of expectation.
        // Total sum = Sum over all A_i [ A_i * (Number of times A_i contributes to cost) ]
        // Number of times A_i contributes to cost:
        // A_i contributes when it is part of the connected component being removed.
        // Consider a block A_k.
        // A_k will contribute to the cost when we remove a block X, and A_k is in the current
        // contiguous segment of unremoved blocks that contains X.
        // This means all blocks between min(A_k, X) and max(A_k, X) must still be present.

        // Let's fix A_k.
        // A_k contributes A_k to the cost if, when some block X is removed, A_k is in the same
        // connected component as X.
        // This means for any j between min(k, X) and max(k, X), block j has not been removed yet.
        // Let's consider the position of A_k in the removal sequence.
        // If A_k is removed at step p (1-indexed), it contributes A_k to the cost.
        // Also, if some A_j (j != k) is removed at step p, and A_k is in the same connected component,
        // A_k contributes to the cost.

        // The state of the blocks is a set of contiguous segments.
        // When a block is removed, its segment splits into two (or one, or zero if it's an endpoint).
        // The total cost for an order is sum of (sum of elements in connected component of removed block at that step).

        // For each A_i, it contributes A_i * (number of times it's included in a cost calculation).
        // A_i is included in a cost calculation if it's part of the connected component that contains the removed block.
        // This means that for some removed block X, all blocks between min(i, X) and max(i, X) are still present.
        // This specific formulation is tricky.

        // Let's go back to the definition: "connected when, for all z (x <= z <= y), Block z is still not removed".
        // When we remove a block P, the cost is sum of A_j for all j in [L, R] where [L, R] is the
        // current connected component containing P.
        // This means every A_j in [L, R] contributes A_j to the cost.

        // Consider a pair of blocks (A_i, A_j) with i <= j.
        // When do A_i and A_j contribute together to a cost?
        // They contribute together if they are in the same connected component when some block X is removed.
        // This means all blocks from i to j must be present AND X must be between i and j (inclusive).
        // For this to happen, all blocks from i to j must be removed AFTER any block outside [i, j].
        // Or, more precisely, for A_i and A_j to be in the same connected component, all blocks from i to j
        // must still be present.
        // Let's define the "lifetime" of a segment [i, j].
        // A segment [i, j] means all blocks from i to j are present.
        // This segment is broken if either block i-1 or j+1 is removed (if they exist), or if some block k in [i, j] is removed.

        // Focus on the contribution of each A_k.
        // A_k contributes A_k to the total cost for each operation where it is part of the connected component.
        // This happens (N!) times in total.
        // For a fixed position `k` (the index of the block A_k):
        // How many times does A_k contribute to the total sum of costs over all N! permutations?
        // A_k contributes to the cost if it is still present and the block being removed is within its current connected component.
        // Let's consider a block A_k.
        // It contributes A_k to the cost if we remove a block X such that all blocks between min(k, X) and max(k, X) are not yet removed.
        // This means that for any pair of blocks (k, X), A_k contributes if X is removed BEFORE any block outside the segment [min(k,X), max(k,X)].
        // Specifically, for A_k to contribute when X is removed, all blocks from min(k, X) to max(k, X) must be present.
        // This happens if all blocks outside this range are removed first, or if the first block removed is within this range.

        // Let's consider the total sum of costs.
        // Total cost = sum over all N! permutations of (sum over all operations of (cost of operation)).
        // By linearity, this is sum over all A_i (A_i * (sum over all permutations of (number of times A_i is counted in a cost))).
        // Let's fix A_i. How many times is A_i counted in a cost?
        // A_i is counted if it's still present AND the removed block X is in the same connected component as A_i.
        // This means all blocks from min(i, X) to max(i, X) are present.
        // Let's consider a segment [L, R] containing A_i and X.
        // For A_i to be counted when X is removed, all blocks in [L, R] must be present.
        // This implies that all blocks L-1 and R+1 (if they exist) must have been removed already,
        // OR the initial segment was [L, R] and X is removed.

        // This is a known type of problem that can be solved by considering pairs of blocks (i, j).
        // Each A_i contributes A_i. The question is how many times it gets multiplied.
        // For any block A_i, it contributes to the cost when any block X in its current connected component is removed.
        // The number of times A_i contributes to the total sum of costs:
        // A_i contributes to the sum if it is present when a block X is removed, and A_i is connected to X.
        // This means for any pair (A_i, A_j) with i <= j:
        // A_i contributes to the cost if A_i is still present and the removed block is in [i, j] AND all blocks [i, j] are present.
        // This implies that all blocks outside [i, j] must be removed before any block inside [i, j].
        // Or, more simply, for a block A_i, it contributes A_i to the cost if for some l <= i <= r,
        // all blocks A_l, ..., A_r are still present, and a block X (l <= X <= r) is removed.

        // Consider the block A_i.
        // It will be part of a connected component of length `len` (containing A_i) when some block is removed.
        // The cost generated by removing a block X in this component is sum of weights in this component.
        // So A_i contributes A_i to this cost.
        // How many times does A_i contribute?
        // A_i contributes if it is still present and the removed block is in the same connected component.
        // Let's fix A_i.
        // For each possible segment [L, R] such that L <= i <= R:
        // A_i contributes if all blocks from L to R are present, and a block X in [L, R] is removed.
        // This happens if all blocks outside [L, R] are removed BEFORE any block inside [L, R].
        // And then one of the blocks in [L, R] is removed.
        // The number of ways this can happen for a fixed [L, R]:
        // There are L-1 blocks to the left and N-R blocks to the right.
        // These (L-1) + (N-R) blocks must be removed before any block in [L, R].
        // The number of ways to order these removals: (L-1+N-R)!
        // The number of ways to order the blocks in [L, R]: (R-L+1)!
        // Total permutations: N!
        // The probability that all blocks outside [L, R] are removed before any block inside [L, R] is:
        // (L-1 + N-R)! * (R-L+1)! / N! * (N - (R-L+1))! * (R-L+1)! / N!
        // This approach is getting complicated.

        // Let's use the observation from similar problems.
        // A block A_i contributes to the sum if it is part of the connected component being removed.
        // This means A_i is in some range [L, R] and a block X in [L, R] is removed, and all blocks in [L, R] are present.
        // This condition is exactly: all blocks in [L, R] have not been removed yet, and X is removed.
        // For a fixed A_i, how many times does it contribute to the sum?
        // A_i contributes if it is present and the removed block X is in its current segment.
        // Consider A_i. For it to contribute, it must not have been removed yet.
        // Let's consider the first block removed, which creates a cost of sumA. This is N! * sumA.
        // What about the second block removed?
        // This is easier if we consider the contribution of A_i to the total sum of costs.
        // The total sum of costs over all N! permutations:
        // Sum_k=1 to N (A_k * (Number of permutations where A_k contributes to cost))
        // When does A_k contribute to the cost?
        // When some block X (which is in the same connected component as A_k) is removed.
        // This means all blocks between min(k, X) and max(k, X) are still present.
        // Let's fix A_k and A_X. Suppose k < X.
        // A_k contributes when X is removed IF all blocks A_k, A_{k+1}, ..., A_X are still present.
        // This happens if the first block removed from the set {A_k, ..., A_X} is A_X (and A_k is not removed yet).
        // Or if some A_Y (k <= Y <= X) is removed, and A_k is still present.
        // The key is to consider the relative order of removal of blocks.
        // For a segment [L, R] to be the connected component that an operation uses:
        // All blocks from L to R must be present.
        // And either L-1 or R+1 (if they exist) must have been removed earlier, OR L and R are the original ends.
        // And one block X in [L, R] is removed.
        // The cost is sum_{j=L to R} A_j.
        // The blocks A_L, ..., A_R all contribute to this cost.

        // The number of times A_k contributes to the total sum of costs:
        // For each pair (i, j) such that i <= k <= j:
        // A_k contributes if all blocks from i to j are present, and one block from i to j is removed.
        // This means that among the blocks {A_i, ..., A_j}, one of them is removed.
        // And all blocks between i and j must be present.
        // This happens if the first block removed from the set {A_i, ..., A_j} is one of them.
        // And importantly, no block outside [i, j] has broken the segment.
        // This means that for A_k to be part of a segment [L,R] that is removed,
        // all blocks in [L,R] must be removed AFTER L-1 and R+1 (if they exist) OR L and R are 1 and N.
        // This is equivalent to saying that among the blocks {A_L, ..., A_R, A_{L-1}, A_{R+1}},
        // one of {A_L, ..., A_R} is removed first.
        // If A_{L-1} or A_{R+1} are removed first, the segment [L,R] is not formed as a single component.

        // Consider A_k. It contributes to the cost if it's part of a segment [L, R] that is removed.
        // For such a segment [L, R] to exist when one of its members is removed, all blocks L to R must be present.
        // This implies that for any L <= k <= R, if we consider the set of blocks {A_L, ..., A_R},
        // and add A_{L-1} (if L>1) and A_{R+1} (if R<N),
        // then for A_k to be part of a cost calculation using this segment [L,R],
        // one of the blocks from A_L to A_R must be removed before A_{L-1} (if it exists) and A_{R+1} (if it exists).
        // The probability of this happening for a segment [L,R] is (R-L+1) / (R-L+1 + (L>1?1:0) + (R<N?1:0)).
        // For each such segment [L,R] containing A_k:
        // The number of ways this relative order happens is N! by (R-L+1 + (L>1?1:0) + (R<N?1:0)) / (R-L+1).
        // This is getting complicated.

        // Let's use a simpler approach.
        // For each block A_i, consider its contribution.
        // A_i contributes A_i to the cost if it is part of the connected component being removed.
        // This happens if A_i is still present and the block removed, say A_x, is within the segment [L,R] that contains A_i.
        // And all blocks from L to R are present.
        // This means for every j in [L,R], A_j is present.
        // Consider the set of blocks {A_1, ..., A_N}.
        // The total sum of costs is Sum_{i=1 to N} A_i * C_i, where C_i is the number of times A_i contributes.
        // C_i = Sum_{L=1 to i} Sum_{R=i to N} (number of permutations where [L,R] is a connected component & a block within it is removed)
        // For a fixed L, R, and a fixed block X in [L,R]:
        // When X is removed, the cost is sum_{j=L to R} A_j.
        // This means for A_i (L <= i <= R), it contributes A_i.
        // How many times does this scenario happen?
        // The segment [L, R] is formed and X is removed.
        // This happens if no block outside [L, R], and no block inside [L, R] except X, is removed before X,
        // and L-1 or R+1 (if they exist) are removed before any block in [L, R]. This is not right.

        // The critical insight for this problem:
        // For any block A_i, it contributes to the cost when a block X is removed, if A_i and X are "connected".
        // "Connected" means all blocks between min(i, X) and max(i, X) are still present.
        // Let's fix A_i and A_j (i != j).
        // When does A_i contribute to the cost because A_j is removed, and A_i and A_j are connected?
        // This happens if all blocks from min(i,j) to max(i,j) are present when A_j is removed.
        // This means that among the blocks in the segment [min(i,j), max(i,j)], A_j must be the first one removed.
        // The probability of this is 1 / (max(i,j) - min(i,j) + 1).
        // For each such instance, A_i contributes A_i.
        // There are (N-1)! ways to order the remaining blocks.
        // So for each pair (i, j), A_i contributes A_i to the total cost if A_j is the first block removed from the segment [min(i,j), max(i,j)].
        // The number of permutations where A_j is the first to be removed from [min(i,j), max(i,j)] is N! / (max(i,j) - min(i,j) + 1).
        // So this is (N-1)! * (max(i,j) - min(i,j) + 1) * (1 / (max(i,j) - min(i,j) + 1)) * A_i.

        // This is a known technique:
        // The total sum of costs = sum over all i (A_i * (number of times A_i contributes))
        // A_i contributes when some block X is removed, and all blocks between min(i,X) and max(i,X) are still present.
        // This happens if X is the first block to be removed from the segment [min(i,X), max(i,X)].
        // For a fixed i and X, there are N! / (abs(i-X)+1) permutations where X is the first to be removed from [min(i,X), max(i,X)].
        // So the total contribution of A_i is A_i * sum_{X=1 to N} (N! / (abs(i-X)+1)).
        // totalSumOfCosts = sum_{i=1 to N} (A_i * sum_{X=1 to N} (N! / (abs(i-X)+1)))
        // We can factor out N!:
        // totalSumOfCosts = N! * sum_{i=1 to N} (A_i * sum_{X=1 to N} (1 / (abs(i-X)+1))) mod MOD
        // Let's verify this.
        // For each block A_i, it contributes A_i to the cost if it is part of the connected component that is removed.
        // This means that for some X, all blocks between min(i,X) and max(i,X) are still present.
        // This happens if, among the blocks in [min(i,X), max(i,X)], block X is the first one to be removed.
        // The number of such permutations is N! / (max(i,X) - min(i,X) + 1).
        // So, for a fixed A_i, its total contribution over all N! permutations is:
        // A_i * Sum_{X=1 to N} (N! / (abs(i-X)+1)).

        // This formula is correct.
        // totalSumOfCosts = (N_factorial * Sum_{i=1 to N} (A_i * Sum_{X=1 to N} (1 / (abs(i-X)+1)))) % MOD
        // We need to calculate Sum_{X=1 to N} (1 / (abs(i-X)+1)) for each i.
        // Let inv[k] be the modular multiplicative inverse of k.
        // Sum_{X=1 to N} (inv[abs(i-X)+1])
        // This sum is:
        // inv[|i-1|+1] + inv[|i-2|+1] + ... + inv[|i-i|+1] + ... + inv[|i-N|+1]
        // = inv[i] + inv[i-1] + ... + inv[1] + inv[2] + ... + inv[N-i+1]
        // = (inv[1] + ... + inv[i]) + (inv[2] + ... + inv[N-i+1])
        // This sum can be computed efficiently.
        // The maximum value for abs(i-X)+1 is N.
        // So we need modular inverses for 1 to N.

        long[] inv = new long[N + 1];
        inv[1] = 1;
        for (int i = 2; i <= N; i++) {
            inv[i] = MOD - (MOD / i) * inv[MOD % i] % MOD;
        }

        long currentTotalContributionFactor = 0;
        for (int i = 1; i <= N; i++) {
            // Calculate sum_{X=1 to N} (inv[abs(i-X)+1])
            // This sum is inv[i] (for X=1) + inv[i-1] (for X=2) + ... + inv[1] (for X=i)
            // + inv[2] (for X=i+1) + ... + inv[N-i+1] (for X=N)

            long sumInvDist = 0;
            // Left part: X from 1 to i
            for (int X = 1; X <= i; X++) {
                sumInvDist = (sumInvDist + inv[i - X + 1]) % MOD;
            }
            // Right part: X from i+1 to N
            for (int X = i + 1; X <= N; X++) {
                sumInvDist = (sumInvDist + inv[X - i + 1]) % MOD;
            }

            currentTotalContributionFactor = (currentTotalContributionFactor + (A[i - 1] * sumInvDist) % MOD) % MOD;
        }

        totalSumOfCosts = (N_factorial * currentTotalContributionFactor) % MOD;

        System.out.println(totalSumOfCosts);
    }
}