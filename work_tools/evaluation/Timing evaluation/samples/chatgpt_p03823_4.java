import java.util.*;
import java.io.*;

public class Main {
    static final int MOD = 1_000_000_007;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] nab = br.readLine().split(" ");
        int N = Integer.parseInt(nab[0]);
        long A = Long.parseLong(nab[1]);
        long B = Long.parseLong(nab[2]);
        long[] S = new long[N];
        for (int i = 0; i < N; i++) S[i] = Long.parseLong(br.readLine());

        // dpX[i] = # of ways to select subset from S[0..i] for X (A-diff), last used is i, may use none (dpX[-1]=1)
        // dpY[i] = # of ways to select subset from S[0..i] for Y (B-diff), last used is i, may use none (dpY[-1]=1)
        // We'll compute F(A) = # of A-diff subsets (including empty), and F(B)
        long[] dpA = new long[N+1]; // dpA[i] = # of A-diff subsets of S[0..i-1]
        long[] dpB = new long[N+1]; // dpB[i] = # of B-diff subsets of S[0..i-1]
        dpA[0] = 1; dpB[0] = 1;
        int pa = 0, pb = 0;
        for(int i = 1; i <= N; i++) {
            // Find pa: smallest pa s.t. S[i-1] - S[pa] >= A
            while (pa < i && S[i-1] - S[pa] >= A) pa++;
            // Find pb: smallest pb s.t. S[i-1] - S[pb] >= B
            while (pb < i && S[i-1] - S[pb] >= B) pb++;
            // dpA[i] = dpA[i-1] + dpA[pa]
            dpA[i] = (dpA[i-1] + dpA[pa]) % MOD;
            dpB[i] = (dpB[i-1] + dpB[pb]) % MOD;
        }
        // Note: dpA[N] counts all A-diff subsets (including empty)
        // Similarly for dpB[N]
        // But if A==B, double-counted subsets where both X and Y get the same set
        // Our answer is sum over all disjoint (X,Y) (X union Y = S, X and Y disjoint), X is A-diff, Y is B-diff.
        // That is, for every subset X (A-diff), assign rest to Y, check if rest is B-diff

        // Efficient alternative: Inclusion-Exclusion
        // Let F(A): number of A-diff subsets (including empty)
        // Let F(B): number of B-diff subsets (including empty)
        // Let F(A∩B): number of subsets which are both A-diff and B-diff (i.e. max(A,B)-diff subsets)
        // For each partition (X,Y):
        //    X is A-diff, Y is B-diff, X∩Y=∅, X∪Y=S, so X,Y are disjoint, partition S
        // Each subset X (A-diff), assign rest Y=S\X, check if Y is B-diff
        // So, answer = ∑_{X: A-diff} [Y=S\X is B-diff]
        // That is, for all A-diff X, if S\X is B-diff, count 1
        // So, answer = number of subsets which are both A-diff AND complement is B-diff

        // But, we can precalculate for all S: For all subsets X which are both A-diff and B-diff, count as 2 (since both X and S\X can be assigned to X or Y), but if X==S\X, counted twice, so for A==B, need care.

        // A better way is:
        // For all subsets X, check if X is A-diff and S\X is B-diff
        // But that's too slow.

        // Instead, let's use DP:
        // Let f[i][state]: state 0 = not used, 1=assign to X, 2=assign to Y
        // But that's 3^N, too large.

        // Alternative: We can do DP for both X and Y and use Inclusion-Exclusion.
        // answer = number of pairs (X,Y): X∩Y=∅, X∪Y=S, X A-diff, Y B-diff
        // This is sum_{T: subset of S} [T is A-diff] * [S\T is B-diff]

        // So, for each subset T (A-diff), if S\T is B-diff, count 1

        // For small N, we can do this naively. For large N, we notice:
        // If A+B > S[N-1] - S[0], then no two elements can go to the same set, so only possible partitions are: assign each element to X or Y (both sets are singletons or empty), which is 2^N

        // But the problem is, we need a fast algorithm.
        // Let's use meet-in-the-middle for N<=20, but for large N, let's try another approach.

        // Let's try DP:
        // Let dpX[i]: number of A-diff subsets for S[0..i-1], no two elements < A apart
        // Similarly for dpY[i], for B

        // But from earlier, dpA[N]: all A-diff subsets

        // Let C = number of subsets which are both A-diff and B-diff, i.e. max(A,B)-diff subsets
        // For those, both X and Y can be that set, so we need to count all pairs (X,Y) where X ∩ Y = ∅, X ∪ Y = S, X is A-diff, Y is B-diff

        // The number of such divisions is:
        // answer = ∑_{T ⊆ S, T is A-diff, S\T is B-diff} 1

        // So, for all A-diff subsets, if complement is B-diff, count 1

        // Let's try to use DP to count, for each A-diff subset, if its complement is B-diff
        // But that's too slow.

        // Alternative: If we swap A and B, answer is the same.

        // Let's try the following:
        // Let dpA[N]: number of A-diff subsets
        // Let dpB[N]: number of B-diff subsets
        // Let dpC[N]: number of C-diff subsets, where C = max(A,B)

        // For all C-diff subsets, both X and Y can be that set, but since X∩Y=∅, only possible if X or Y is empty

        // For all possible partitions, if both X and Y satisfy the diff constraints, count 1

        // We can use DP for the following:
        // For each possible arrangement of assignments (X or Y), using DP with two states:
        // dp[i][0]: number of ways to assign S[0..i-1], last assigned to X at pos px, last assigned to Y at pos py
        // But that's O(N^2), too slow.

        // Let's try to use a greedy coloring:
        // Let's color S with two colors: assign element to X or Y, such that in X, all elements are at least A apart, in Y, all elements at least B apart.
        // For each position, assign to X if difference >= A from previous X, to Y if difference >= B from previous Y.
        // Let's try DP[i][lastX][lastY], but that's O(N^2), too slow.

        // Let's try the following DP:
        // dp[i][xA][yB]: number of ways to assign first i elements, last assigned to X was at xA, last to Y was at yB
        // But that's O(N^2), still too large.

        // Alternative: Let's use the original DP, but in reverse:
        // Let f[i]: number of ways to assign S[0..i-1] to X and Y, such that the last element assigned to X is at pos px, last to Y at py

        // But as an alternative method, let's try the following:
        // Let's try a BFS/greedy coloring: Assign elements greedily to X and Y, and count the number of valid colorings.
        // Let's try DP[i][mask], where mask encodes the valid states for last assignment.

        // But, let's rely on an alternative DP:
        // Let dp[i]: number of valid assignments for S[0..i-1], where dp[i] = number of ways to assign up to i, keeping track of last assigned to X and last assigned to Y, but this is still O(N^2).
        // Let's try an optimized version:

        int[] lastA = new int[N]; // last index before i that can be included in X
        int[] lastB = new int[N]; // last index before i that can be included in Y
        Arrays.fill(lastA, -1);
        Arrays.fill(lastB, -1);
        for (int i = 0, j = 0; i < N; i++) {
            while (j < i && S[i] - S[j] >= A) j++;
            lastA[i] = j-1;
        }
        for (int i = 0, j = 0; i < N; i++) {
            while (j < i && S[i] - S[j] >= B) j++;
            lastB[i] = j-1;
        }

        // dpX[i]: number of A-diff subsets ending at i (must include S[i])
        // dpY[i]: number of B-diff subsets ending at i (must include S[i])
        long[] dpX = new long[N];
        long[] dpY = new long[N];
        Arrays.fill(dpX, 0);
        Arrays.fill(dpY, 0);
        for (int i = 0; i < N; i++) {
            if (lastA[i] == -1) dpX[i] = 1;
            else dpX[i] = 0;
            for (int j = lastA[i]; j >= 0; ) {
                dpX[i] = (dpX[i] + dpX[j]) % MOD;
                j = lastA[j];
            }
            if (lastB[i] == -1) dpY[i] = 1;
            else dpY[i] = 0;
            for (int j = lastB[i]; j >= 0; ) {
                dpY[i] = (dpY[i] + dpY[j]) % MOD;
                j = lastB[j];
            }
        }

        // Let's count the number of ways to divide S into two sets X, Y (possibly empty), such that X is A-diff, Y is B-diff, X∪Y=S, X∩Y=∅
        // We'll use DP[i][xA][yB]: number of ways to assign S[i..N-1], given last used in X is xA, last used in Y is yB
        // But xA and yB can be -1 (meaning empty set), so we can do O(N) per step.

        // Let's use DP2: dp[i][0]: last assigned to X at i-1, dp[i][1]: last assigned to Y at i-1
        // But we need to process for both.

        // Let's use the following:
        // At each point, the assignment depends only on the last assigned to X and last assigned to Y.

        // Let's try the following:
        // We'll use two arrays:
        // fX[i]: number of ways to assign first i elements, with last assigned to X at i-1 (and Y may be empty)
        // fY[i]: number of ways to assign first i elements, with last assigned to Y at i-1 (and X may be empty)
        // fE[i]: number of ways to assign first i elements, with both X and Y empty

        long[] fX = new long[N+1];
        long[] fY = new long[N+1];
        long[] fE = new long[N+1];
        fE[0] = 1;
        for (int i = 0; i < N; i++) {
            // Assign S[i] to X
            // Find last position px < i such that all elements between px and i-1 are not assigned to X, and S[i] - S[px] >= A (or px=-1)
            int px = i-1;
            while (px >= 0 && S[i]-S[px] < A) px--;
            // Can assign S[i] to X, if last assigned to X at px (or never)
            long sumX = (px >= 0 ? (fX[px+1] + fE[px+1])%MOD : fE[0]);
            fX[i+1] = sumX;
            // Assign S[i] to Y
            int py = i-1;
            while (py >= 0 && S[i]-S[py] < B) py--;
            long sumY = (py >= 0 ? (fY[py+1] + fE[py+1])%MOD : fE[0]);
            fY[i+1] = sumY;
            // Not assign S[i] to either set (only possible if both X and Y are empty)
            fE[i+1] = 0;
        }
        // Now sum up all valid assignments
        long res = (fX[N] + fY[N] + fE[N]) % MOD;

        // But we need to ensure all elements are assigned to either X or Y, so fE[N]=0, only count fX[N] and fY[N]
        System.out.println((fX[N] + fY[N]) % MOD);
    }
}