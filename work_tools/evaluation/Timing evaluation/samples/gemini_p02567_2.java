import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Main {
    static class SegmentTree {
        int N;
        int[] arr;
        int[] tree;

        public SegmentTree(int[] a) {
            N = a.length;
            arr = a;
            tree = new int[4 * N];
            build(0, 0, N - 1);
        }

        void build(int node, int start, int end) {
            if (start == end) {
                tree[node] = arr[start];
            } else {
                int mid = (start + end) / 2;
                build(2 * node + 1, start, mid);
                build(2 * node + 2, mid + 1, end);
                tree[node] = Math.max(tree[2 * node + 1], tree[2 * node + 2]);
            }
        }

        void update(int idx, int val) {
            arr[idx] = val;
            update(0, 0, N - 1, idx, val);
        }

        void update(int node, int start, int end, int idx, int val) {
            if (start == end) {
                tree[node] = val;
            } else {
                int mid = (start + end) / 2;
                if (start <= idx && idx <= mid) {
                    update(2 * node + 1, start, mid, idx, val);
                } else {
                    update(2 * node + 2, mid + 1, end, idx, val);
                }
                tree[node] = Math.max(tree[2 * node + 1], tree[2 * node + 2]);
            }
        }

        int queryMax(int l, int r) {
            return queryMax(0, 0, N - 1, l, r);
        }

        int queryMax(int node, int start, int end, int l, int r) {
            if (r < start || end < l) {
                return 0; // Assuming values are non-negative
            }
            if (l <= start && end <= r) {
                return tree[node];
            }
            int mid = (start + end) / 2;
            int p1 = queryMax(2 * node + 1, start, mid, l, r);
            int p2 = queryMax(2 * node + 2, mid + 1, end, l, r);
            return Math.max(p1, p2);
        }

        int findMinIndex(int node, int start, int end, int queryL, int val) {
            if (tree[node] < val || end < queryL) {
                return N; // No element in this range is >= val
            }
            if (start == end) {
                return start;
            }

            int mid = (start + end) / 2;
            if (queryL <= mid && tree[2 * node + 1] >= val) {
                int res = findMinIndex(2 * node + 1, start, mid, queryL, val);
                if (res != N) {
                    return res;
                }
            }
            return findMinIndex(2 * node + 2, mid + 1, end, queryL, val);
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(System.out);

        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int Q = Integer.parseInt(st.nextToken());

        int[] A = new int[N];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            A[i] = Integer.parseInt(st.nextToken());
        }

        SegmentTree segmentTree = new SegmentTree(A);

        for (int q = 0; q < Q; q++) {
            st = new StringTokenizer(br.readLine());
            int type = Integer.parseInt(st.nextToken());

            if (type == 1) {
                int X = Integer.parseInt(st.nextToken()) - 1; // 0-indexed
                int V = Integer.parseInt(st.nextToken());
                segmentTree.update(X, V);
            } else if (type == 2) {
                int L = Integer.parseInt(st.nextToken()) - 1; // 0-indexed
                int R = Integer.parseInt(st.nextToken()) - 1; // 0-indexed
                pw.println(segmentTree.queryMax(L, R));
            } else { // type == 3
                int X = Integer.parseInt(st.nextToken()) - 1; // 0-indexed
                int V = Integer.parseInt(st.nextToken());
                int result = segmentTree.findMinIndex(0, 0, N - 1, X, V);
                pw.println(result + 1); // 1-indexed output
            }
        }

        pw.flush();
        pw.close();
        br.close();
    }
}