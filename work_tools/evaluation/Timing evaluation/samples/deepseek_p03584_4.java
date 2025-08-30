import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int K = sc.nextInt();
        int[] A = new int[N];
        int[] B = new int[N];
        for (int i = 0; i < N; i++) {
            A[i] = sc.nextInt();
            B[i] = sc.nextInt();
        }

        long max = 0;
        for (int mask = K; ; mask = (mask - 1) & K) {
            long sum = 0;
            for (int i = 0; i < N; i++) {
                if ((A[i] & ~mask) == 0) {
                    sum += B[i];
                }
            }
            if (sum > max) {
                max = sum;
            }
            if (mask == 0) break;
        }
        System.out.println(max);
    }
}