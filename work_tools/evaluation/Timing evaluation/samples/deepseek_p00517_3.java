import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int W = scanner.nextInt();
        int H = scanner.nextInt();
        int N = scanner.nextInt();
        
        int[] X = new int[N];
        int[] Y = new int[N];
        
        for (int i = 0; i < N; i++) {
            X[i] = scanner.nextInt();
            Y[i] = scanner.nextInt();
        }
        
        int total = 0;
        for (int i = 1; i < N; i++) {
            int dx = X[i] - X[i-1];
            int dy = Y[i] - Y[i-1];
            
            if ((dx >= 0 && dy >= 0) || (dx <= 0 && dy <= 0)) {
                total += Math.max(Math.abs(dx), Math.abs(dy));
            } else {
                total += Math.abs(dx) + Math.abs(dy);
            }
        }
        
        System.out.println(total);
    }
}