import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int N = scanner.nextInt();
        long x = scanner.nextLong();
        long[] a = new long[N];
        
        for (int i = 0; i < N; i++) {
            a[i] = scanner.nextLong();
        }
        
        long operations = 0;
        for (int i = 1; i < N; i++) {
            long sum = a[i - 1] + a[i];
            if (sum > x) {
                long excess = sum - x;
                operations += excess;
                a[i] = Math.max(0, a[i] - excess);
            }
        }
        
        System.out.println(operations);
    }
}