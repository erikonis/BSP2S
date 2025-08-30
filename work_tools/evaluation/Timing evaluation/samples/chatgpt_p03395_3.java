import java.util.*;

public class Main {
    static int N;
    static int[] a, b;
    static final int MAX_K = 50;
    static long answer = Long.MAX_VALUE;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        a = new int[N];
        b = new int[N];
        for (int i = 0; i < N; i++) a[i] = sc.nextInt();
        for (int i = 0; i < N; i++) b[i] = sc.nextInt();

        // Early exit: if a == b, no cost
        boolean same = true;
        for (int i = 0; i < N; i++) if (a[i] != b[i]) same = false;
        if (same) {
            System.out.println(0);
            return;
        }

        // For each i, precompute possible k (1 <= k <= MAX_K) such that a[i] % k == b[i]
        List<Integer>[] possibleKs = new ArrayList[N];
        for (int i = 0; i < N; i++) {
            possibleKs[i] = new ArrayList<>();
            if (a[i] == b[i]) possibleKs[i].add(0); // 0 means "do nothing"
            for (int k = 1; k <= MAX_K; k++) {
                if (a[i] % k == b[i]) possibleKs[i].add(k);
            }
        }

        // If for any i, possibleKs[i] is empty, impossible
        for (int i = 0; i < N; i++) {
            if (possibleKs[i].isEmpty()) {
                System.out.println(-1);
                return;
            }
        }

        // We try all subsets of k (1..MAX_K) (there are at most 2^50, but we prune)
        // Instead, we try bitmask from 0 to (1<<MAX_K)-1, but only for relevant k's.
        // But that's too much. Instead, we try greedy from large k to small, as large k gives smaller cost.
        // The minimum cost can be found by trying all combinations of k that "cover" all transformations.
        // We use DP: dp[mask]: minimal cost to achieve mask.
        // mask: for each position, whether b[i] is achieved.

        // But the states are too big (2^N). Instead, we can try all subsets of k (1..MAX_K) (up to 50).
        // For each subset, check whether we can achieve b from a by using these k's (apply in any order).

        // Since N is small (<=50), we can use a bitmask to represent which positions need to be changed.
        // For each k, for each i, if a[i] % k == b[i], then with k we can change a[i] to b[i].

        // Try all combinations of k. Since MAX_K = 50, 2^15 = 32k, but 2^50 is too big.
        // But since the possible k's for each position is small, we can use a recursive search.

        // Let's use DFS with pruning.

        dfs(0, a, 0);
        System.out.println(answer == Long.MAX_VALUE ? -1 : answer);
    }

    static void dfs(long cost, int[] cur, int used) {
        // If already more costly than best answer, prune
        if (cost >= answer) return;

        // Check if cur == b
        boolean done = true;
        for (int i = 0; i < N; i++) {
            if (cur[i] != b[i]) {
                done = false;
                break;
            }
        }
        if (done) {
            answer = Math.min(answer, cost);
            return;
        }

        // Try all k from 1 to MAX_K
        for (int k = 1; k <= MAX_K; k++) {
            if ((used & (1 << k)) != 0) continue; // already used this k

            // For each position, decide whether to apply k or not
            boolean canChange = false;
            int[] next = cur.clone();
            for (int i = 0; i < N; i++) {
                if (cur[i] != b[i] && cur[i] % k == b[i]) {
                    next[i] = b[i];
                    canChange = true;
                }
            }
            if (!canChange) continue; // using this k does not help

            dfs(cost + (1L << k), next, used | (1 << k));
        }
    }
}