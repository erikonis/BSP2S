import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int[] W = new int[N];
        int total = 0;
        for (int i = 0; i < N; i++) {
            W[i] = sc.nextInt();
            total += W[i];
        }
        int minDiff = Integer.MAX_VALUE;
        int s1 = 0;
        for (int T = 0; T < N - 1; T++) {
            s1 += W[T];
            int s2 = total - s1;
            int diff = Math.abs(s1 - s2);
            if (diff < minDiff) minDiff = diff;
        }
        System.out.println(minDiff);
    }
}