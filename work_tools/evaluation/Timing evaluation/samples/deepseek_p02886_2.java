import java.util.Scanner;

public class TakoyakiFestival {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int N = scanner.nextInt();
        int[] d = new int[N];
        for (int i = 0; i < N; i++) {
            d[i] = scanner.nextInt();
        }
        
        int sum = 0;
        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                sum += d[i] * d[j];
            }
        }
        System.out.println(sum);
    }
}