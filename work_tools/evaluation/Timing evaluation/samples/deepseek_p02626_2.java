import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int N = scanner.nextInt();
        long[] A = new long[N];
        for (int i = 0; i < N; i++) {
            A[i] = scanner.nextLong();
        }
        
        if (N == 2) {
            long xor = A[0] ^ A[1];
            if (xor == 0) {
                System.out.println(-1);
            } else {
                long maxMove = A[0] - 1;
                long low = 0;
                long high = maxMove;
                long answer = -1;
                while (low <= high) {
                    long mid = (low + high) / 2;
                    long newA1 = A[0] - mid;
                    long newA2 = A[1] + mid;
                    if ((newA1 ^ newA2) == 0) {
                        answer = mid;
                        high = mid - 1;
                    } else {
                        low = mid + 1;
                    }
                }
                System.out.println(answer);
            }
        } else {
            long totalXor = 0;
            for (int i = 0; i < N; i++) {
                totalXor ^= A[i];
            }
            if (totalXor == 0) {
                System.out.println(0);
            } else {
                long move = A[0] - (A[0] ^ (totalXor ^ A[0]));
                if (move > 0 && move < A[0]) {
                    System.out.println(move);
                } else {
                    System.out.println(-1);
                }
            }
        }
    }
}