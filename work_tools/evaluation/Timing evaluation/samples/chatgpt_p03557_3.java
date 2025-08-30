import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // Fast input
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        long[] A = new long[N];
        long[] B = new long[N];
        long[] C = new long[N];

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) A[i] = Long.parseLong(st.nextToken());

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) B[i] = Long.parseLong(st.nextToken());

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) C[i] = Long.parseLong(st.nextToken());

        Arrays.sort(A);
        Arrays.sort(B);
        Arrays.sort(C);

        // For each B[j], count number of A[i] < B[j] and C[k] > B[j]
        // For efficiency, precompute prefix/suffix counts

        // For A: For each B[j], how many A[i] < B[j]?
        // For C: For each B[j], how many C[k] > B[j]?

        long ans = 0;

        // For each B[j], binary search A and C
        for (int j = 0; j < N; j++) {
            long b = B[j];

            // Num of A[i] < b
            int upper = upperBound(A, b);

            // Num of C[k] > b
            int lower = lowerBound(C, b + 1); // C[lower] >= b+1
            int cntC = N - lower;

            ans += (long)upper * cntC;
        }

        System.out.println(ans);
    }

    // Returns index of first element >= x
    static int lowerBound(long[] arr, long x) {
        int l = 0, r = arr.length;
        while (l < r) {
            int m = (l + r) / 2;
            if (arr[m] < x) l = m + 1;
            else r = m;
        }
        return l;
    }

    // Returns index of first element > x or length if not found
    static int upperBound(long[] arr, long x) {
        int l = 0, r = arr.length;
        while (l < r) {
            int m = (l + r) / 2;
            if (arr[m] <= x - 1) l = m + 1;
            else r = m;
        }
        return l;
    }
}