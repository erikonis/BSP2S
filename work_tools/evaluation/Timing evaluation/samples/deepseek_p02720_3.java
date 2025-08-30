import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int K = scanner.nextInt();
        Queue<Long> queue = new LinkedList<>();
        for (long i = 1; i <= 9; i++) {
            queue.add(i);
        }
        long result = 0;
        for (int i = 0; i < K; i++) {
            result = queue.poll();
            long lastDigit = result % 10;
            if (lastDigit > 0) {
                queue.add(result * 10 + lastDigit - 1);
            }
            queue.add(result * 10 + lastDigit);
            if (lastDigit < 9) {
                queue.add(result * 10 + lastDigit + 1);
            }
        }
        System.out.println(result);
    }
}