import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        long[] A = new long[N];
        for (int i = 0; i < N; i++) {
            A[i] = sc.nextLong();
        }

        // Two pointers approach
        int right = 0;
        long sum = 0, xor = 0;
        long ans = 0;
        for (int left = 0; left < N; left++) {
            while (right < N && (sum + A[right]) == (xor ^ A[right])) {
                sum += A[right];
                xor ^= A[right];
                right++;
            }
            ans += right - left;
            // Remove A[left] from sum and xor as window moves
            sum -= A[left];
            xor ^= A[left];
        }
        System.out.println(ans);
    }
}