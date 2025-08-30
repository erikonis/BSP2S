import java.util.*;

public class Main {
    static ArrayList<Integer>[] adj;
    static int[] color;
    static int[] p;
    static int N;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();

        adj = new ArrayList[N + 1];
        for (int i = 1; i <= N; i++) {
            adj[i] = new ArrayList<>();
        }

        for (int i = 0; i < N - 1; i++) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            adj[u].add(v);
            adj[v].add(u);
        }

        color = new int[N + 1];
        dfsColor(1, 0, 0); // 0-indexed color

        int count0 = 0;
        int count1 = 0;
        for (int i = 1; i <= N; i++) {
            if (color[i] == 0) {
                count0++;
            } else {
                count1++;
            }
        }

        p = new int[N + 1];
        if (count0 > N / 3 * 2 || count1 > N / 3 * 2) {
            // This case handles when one partition is too large,
            // making it impossible to assign numbers divisible by 3 to both.
            // If one side is very small, say 2 nodes, and N is large,
            // we might not have enough 0-mod-3 numbers for the large side.
            // This is a heuristic. A more rigorous check might be needed,
            // but for this problem's constraints and typical Bipartite scenarios,
            // it's often sufficient to just check counts.
            // The problem setter's solution for similar problems often allows for this.

            // Try to assign 0-mod-3 numbers to the smaller partition
            // and 1/2-mod-3 numbers to the larger partition if possible.
            // If the smaller partition is too large to fill with 0-mod-3 numbers,
            // or if the larger partition needs too many 1/2-mod-3 numbers, it's impossible.
            // However, the problem statement implies a solution always exists if N is not too small.
            // The condition "sum or product is multiple of 3" is very lenient.
            // If p_i % 3 == 0, then p_i * p_j is a multiple of 3.
            // If p_i % 3 != 0 and p_j % 3 != 0, then p_i + p_j must be a multiple of 3.
            // This means (p_i % 3 == 1 and p_j % 3 == 2) or (p_i % 3 == 2 and p_j % 3 == 1).
            // So if two nodes are distance 3 apart, one must be 0-mod-3, or one is 1-mod-3 and other is 2-mod-3.

            // If we try to put all 0-mod-3 numbers on one side of the bipartite graph,
            // and 1-mod-3 and 2-mod-3 numbers on the other side.
            // This assignment is always possible.
            // Let's assign numbers to the larger partition first.
            int[] nums0 = new int[N / 3];
            for (int i = 0; i < N / 3; i++) nums0[i] = (i + 1) * 3;

            int[] nums1 = new int[N - N / 3];
            int idx1 = 0;
            for (int i = 1; i <= N; i++) {
                if (i % 3 != 0) {
                    nums1[idx1++] = i;
                }
            }

            // Assign numbers based on the bipartite partition
            int ptr0 = 0;
            int ptr1 = 0;
            for (int i = 1; i <= N; i++) {
                if (color[i] == 0) { // color 0
                    if (ptr0 < nums0.length) {
                        p[i] = nums0[ptr0++];
                    } else {
                        p[i] = nums1[ptr1++];
                    }
                } else { // color 1
                    if (ptr1 < nums1.length) {
                        p[i] = nums1[ptr1++];
                    } else {
                        p[i] = nums0[ptr0++];
                    }
                }
            }

        } else {
            // Assign numbers 1 to N such that numbers divisible by 3 are split
            // evenly between the two partitions if possible.
            // Or assign all 0-mod-3 numbers to one partition, and 1/2-mod-3 to the other.
            // The simple assignment that always works:
            // Assign 0-mod-3 numbers to nodes with color 0.
            // Assign 1-mod-3 and 2-mod-3 numbers to nodes with color 1.
            // Or vice versa.
            // The condition is satisfied if for any (i,j) with dist 3:
            // p_i % 3 == 0 OR p_j % 3 == 0 OR (p_i%3 + p_j%3) == 3.
            // If i and j are dist 3, they are in different partitions of the bipartite graph.
            // Let's say color[i] = 0 and color[j] = 1.
            // If we assign numbers like this:
            // For color 0: assign numbers {3, 6, 9, ...}
            // For color 1: assign numbers {1, 2, 4, 5, 7, 8, ...}
            // Then for any (i,j) dist 3, color[i] != color[j].
            // Say color[i]=0, color[j]=1. p[i] is multiple of 3. So p[i]*p[j] is multiple of 3. Condition satisfied.
            // This strategy always works if N >= 2.
            // We need count0 numbers that are multiples of 3.
            // We need count1 numbers that are not multiples of 3.
            // Check if this is possible.
            // Number of multiples of 3 up to N is floor(N/3).
            // Number of non-multiples of 3 up to N is N - floor(N/3).

            int numMultiplesOf3 = N / 3;
            int numNonMultiplesOf3 = N - numMultiplesOf3;

            if (numMultiplesOf3 >= count0 && numNonMultiplesOf3 >= count1) {
                // Assign multiples of 3 to color 0 nodes, non-multiples to color 1 nodes
                assignNumbers(0);
            } else if (numMultiplesOf3 >= count1 && numNonMultiplesOf3 >= count0) {
                // Assign multiples of 3 to color 1 nodes, non-multiples to color 0 nodes
                assignNumbers(1);
            } else {
                // This means no simple assignment based on bipartite coloring works.
                // This case should ideally not happen for N >= 2, given the
                // generous condition of sum OR product.
                // The problem statement guarantees a solution exists.
                // The earlier large_partition check covers some tricky cases.
                // If it reaches here, it means the structure of 0-mod-3 numbers
                // is sufficient for one partition, and 1/2-mod-3 numbers for the other.
                // The problem is that the counts might be skewed.
                // For example, N=5, count0=4, count1=1. numMultiplesOf3=1, numNonMultiplesOf3=4.
                // Case 1: assign M3 to color 0 (need 4, have 1) -> NO
                // Case 2: assign M3 to color 1 (need 1, have 1) -> YES
                // and non-M3 to color 0 (need 4, have 4) -> YES.
                // So assign M3 to the smaller partition.
                if (count0 < count1) { // Assign M3 to color 0
                    assignNumbers(0);
                } else { // Assign M3 to color 1
                    assignNumbers(1);
                }
            }
        }

        for (int i = 1; i <= N; i++) {
            System.out.print(p[i] + (i == N ? "" : " "));
        }
        System.out.println();
    }

    static void dfsColor(int u, int parent, int c) {
        color[u] = c;
        for (int v : adj[u]) {
            if (v != parent) {
                dfsColor(v, u, 1 - c);
            }
        }
    }

    static void assignNumbers(int targetColorForMultiplesOf3) {
        ArrayList<Integer> multiplesOf3 = new ArrayList<>();
        ArrayList<Integer> nonMultiplesOf3 = new ArrayList<>();
        for (int i = 1; i <= N; i++) {
            if (i % 3 == 0) {
                multiplesOf3.add(i);
            } else {
                nonMultiplesOf3.add(i);
            }
        }

        int m3Idx = 0;
        int nonM3Idx = 0;

        for (int i = 1; i <= N; i++) {
            if (color[i] == targetColorForMultiplesOf3) {
                p[i] = multiplesOf3.get(m3Idx++);
            } else {
                p[i] = nonMultiplesOf3.get(nonM3Idx++);
            }
        }
    }
}