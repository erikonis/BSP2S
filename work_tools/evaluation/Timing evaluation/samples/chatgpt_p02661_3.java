import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        int[] A = new int[N];
        int[] B = new int[N];
        for (int i = 0; i < N; ++i) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            A[i] = Integer.parseInt(st.nextToken());
            B[i] = Integer.parseInt(st.nextToken());
        }

        Arrays.sort(A);
        Arrays.sort(B);

        if (N % 2 == 1) {
            int mid = N / 2;
            int minMedian = A[mid];
            int maxMedian = B[mid];
            System.out.println(maxMedian - minMedian + 1);
        } else {
            int mid1 = N / 2 - 1;
            int mid2 = N / 2;
            int minSum = A[mid1] + A[mid2];
            int maxSum = B[mid1] + B[mid2];
            System.out.println(maxSum - minSum + 1);
        }
    }
}