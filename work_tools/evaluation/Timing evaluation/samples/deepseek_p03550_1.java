import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int Z = sc.nextInt();
        int W = sc.nextInt();
        int[] a = new int[N];
        for (int i = 0; i < N; i++) {
            a[i] = sc.nextInt();
        }
        
        if (N == 1) {
            System.out.println(Math.abs(a[0] - W));
            return;
        }
        
        int res = Math.max(Math.abs(a[N-1] - W), Math.abs(a[N-2] - a[N-1]));
        System.out.println(res);
    }
}