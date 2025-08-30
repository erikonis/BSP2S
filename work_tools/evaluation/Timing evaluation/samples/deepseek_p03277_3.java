import java.util.*;
import java.io.*;

public class Main {
    static int N;
    static int[] a;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        a = new int[N];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            a[i] = Integer.parseInt(st.nextToken());
        }

        int left = 1;
        int right = 1_000_000_000;
        long total = (long) N * (N + 1) / 2;
        long target = (total + 1) / 2; // We need the median, which is the (total+1)/2-th smallest element

        int answer = 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (countLessOrEqual(mid) >= target) {
                answer = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        System.out.println(answer);
    }

    static long countLessOrEqual(int x) {
        int[] prefix = new int[N + 1];
        for (int i = 1; i <= N; i++) {
            prefix[i] = prefix[i - 1] + (a[i - 1] <= x ? 1 : -1);
        }

        // Coordinate compression for prefix sums
        Set<Integer> set = new TreeSet<>();
        for (int num : prefix) {
            set.add(num);
        }
        int idx = 1;
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : set) {
            map.put(num, idx++);
        }

        FenwickTree ft = new FenwickTree(set.size() + 2);
        long res = 0;
        for (int i = 0; i <= N; i++) {
            int compressed = map.get(prefix[i]);
            res += ft.query(compressed);
            ft.update(compressed, 1);
        }
        return res;
    }

    static class FenwickTree {
        int[] tree;
        int size;

        FenwickTree(int size) {
            this.size = size;
            tree = new int[size + 1];
        }

        void update(int idx, int delta) {
            while (idx <= size) {
                tree[idx] += delta;
                idx += idx & -idx;
            }
        }

        int query(int idx) {
            int res = 0;
            while (idx > 0) {
                res += tree[idx];
                idx -= idx & -idx;
            }
            return res;
        }
    }
}