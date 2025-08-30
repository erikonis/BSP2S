import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int H = sc.nextInt();
        int W = sc.nextInt();
        int[] A = new int[H];
        int[] B = new int[H];
        for (int i = 0; i < H; i++) {
            A[i] = sc.nextInt();
            B[i] = sc.nextInt();
        }

        SegmentTree segTree = new SegmentTree(W);
        for (int i = 1; i <= W; i++) {
            segTree.update(i, i, i - 1);
        }

        for (int k = 0; k < H; k++) {
            // Block the invalid range for moving down
            segTree.update(A[k], B[k], -1);

            // Calculate minimum moves to reach row k+1
            long minMoves = segTree.query(1, W);

            if (minMoves == Long.MAX_VALUE) {
                System.out.println(-1);
            } else {
                System.out.println(minMoves + k + 1);
            }

            // Restore the blocked range for the next iteration
            segTree.update(A[k], B[k], Long.MAX_VALUE); // Use MAX_VALUE to effectively unblock
        }
        sc.close();
    }

    static class SegmentTree {
        long[] tree;
        long[] lazy;
        int size;

        public SegmentTree(int W) {
            size = 1;
            while (size < W) {
                size *= 2;
            }
            tree = new long[2 * size];
            lazy = new long[2 * size];
            Arrays.fill(tree, Long.MAX_VALUE);
            Arrays.fill(lazy, Long.MAX_VALUE);
        }

        void push(int node, int start, int end) {
            if (lazy[node] != Long.MAX_VALUE) {
                tree[node] = Math.min(tree[node], lazy[node]); // Apply lazy value
                if (start != end) {
                    lazy[2 * node] = Math.min(lazy[2 * node], lazy[node]);
                    lazy[2 * node + 1] = Math.min(lazy[2 * node + 1], lazy[node]);
                }
                lazy[node] = Long.MAX_VALUE; // Reset lazy
            }
        }

        void update(int l, int r, long val) {
            update(1, 1, size, l, r, val);
        }

        void update(int node, int start, int end, int l, int r, long val) {
            push(node, start, end); // Apply pending lazy updates
            if (start > end || start > r || end < l) {
                return;
            }
            if (l <= start && end <= r) {
                lazy[node] = Math.min(lazy[node], val);
                push(node, start, end);
                return;
            }
            int mid = (start + end) / 2;
            update(2 * node, start, mid, l, r, val);
            update(2 * node + 1, mid + 1, end, l, r, val);
            tree[node] = Math.min(tree[2 * node], tree[2 * node + 1]);
        }

        long query(int l, int r) {
            return query(1, 1, size, l, r);
        }

        long query(int node, int start, int end, int l, int r) {
            push(node, start, end); // Apply pending lazy updates
            if (start > end || start > r || end < l) {
                return Long.MAX_VALUE;
            }
            if (l <= start && end <= r) {
                return tree[node];
            }
            int mid = (start + end) / 2;
            long p1 = query(2 * node, start, mid, l, r);
            long p2 = query(2 * node + 1, mid + 1, end, l, r);
            return Math.min(p1, p2);
        }
    }
}