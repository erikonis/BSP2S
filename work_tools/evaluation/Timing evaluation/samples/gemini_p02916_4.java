import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int[] A = new int[N];
        for (int i = 0; i < N; i++) {
            A[i] = sc.nextInt();
        }
        int[] B = new int[N + 1];
        for (int i = 1; i <= N; i++) {
            B[i] = sc.nextInt();
        }
        int[] C = new int[N];
        for (int i = 1; i < N; i++) {
            C[i] = sc.nextInt();
        }

        long totalSatisfaction = 0;
        for (int i = 0; i < N; i++) {
            totalSatisfaction += B[A[i]];
            if (i > 0 && A[i] == A[i - 1] + 1) {
                totalSatisfaction += C[A[i - 1]];
            }
        }

        System.out.println(totalSatisfaction);

        sc.close();
    }
}