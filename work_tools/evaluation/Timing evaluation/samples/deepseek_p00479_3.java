import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int K = sc.nextInt();
        
        for (int i = 0; i < K; i++) {
            int a = sc.nextInt();
            int b = sc.nextInt();
            
            int minDist = Math.min(Math.min(a - 1, N - a), Math.min(b - 1, N - b));
            int layer = minDist % 3;
            
            System.out.println(layer + 1);
        }
    }
}