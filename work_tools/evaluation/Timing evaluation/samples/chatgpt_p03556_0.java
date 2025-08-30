import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();

        // Binary search for the largest integer k such that k*k <= N
        int left = 1, right = 46340; // sqrt(1e9) < 46341
        int ans = 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            long sq = (long) mid * mid;
            if (sq <= N) {
                ans = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        System.out.println(ans * ans);
    }
}