import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] nk = br.readLine().split(" ");
        int N = Integer.parseInt(nk[0]);
        long K = Long.parseLong(nk[1]);
        long[] A = new long[N];
        String[] a = br.readLine().split(" ");
        for (int i = 0; i < N; i++) {
            A[i] = Long.parseLong(a[i]);
        }
        Arrays.sort(A);

        long left = -1000000000000000000L;
        long right = 1000000000000000000L;

        // Find smallest x such that there are at least K pairs with product <= x
        while (left < right) {
            long mid = left + (right - left) / 2;
            if (countPairs(A, mid) < K) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        System.out.println(left);
    }

    static long countPairs(long[] A, long x) {
        int N = A.length;
        long count = 0;

        for (int i = 0; i < N; i++) {
            if (A[i] < 0) {
                int l = i + 1, r = N - 1, res = i - 1;
                while (l <= r) {
                    int m = (l + r) / 2;
                    if (A[i] * A[m] <= x) {
                        res = m;
                        l = m + 1;
                    } else {
                        r = m - 1;
                    }
                }
                count += res - i;
            } else if (A[i] == 0) {
                if (x >= 0) {
                    count += N - i - 1;
                }
            } else {
                int l = i + 1, r = N - 1, res = N;
                while (l <= r) {
                    int m = (l + r) / 2;
                    if (A[i] * A[m] <= x) {
                        res = m;
                        l = m + 1;
                    } else {
                        r = m - 1;
                    }
                }
                count += res - i - 1;
            }
        }
        return count;
    }
}