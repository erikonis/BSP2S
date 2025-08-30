import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int N = scanner.nextInt();
        long D = scanner.nextLong();
        D *= D;
        int count = 0;
        
        for (int i = 0; i < N; i++) {
            long X = scanner.nextLong();
            long Y = scanner.nextLong();
            if (X * X + Y * Y <= D) {
                count++;
            }
        }
        
        System.out.println(count);
    }
}