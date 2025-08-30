import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int[] a = new int[N];
        int totalXorSum = 0;
        for (int i = 0; i < N; i++) {
            a[i] = sc.nextInt();
            totalXorSum ^= a[i];
        }

        int[] x = new int[N];
        for (int i = 0; i < N; i++) {
            x[i] = totalXorSum ^ a[i];
        }

        for (int i = 0; i < N; i++) {
            System.out.print(x[i] + (i == N - 1 ? "" : " "));
        }
        System.out.println();

        sc.close();
    }
}