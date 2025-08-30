import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            int A = scanner.nextInt();
            int B = scanner.nextInt();
            int C = scanner.nextInt();
            
            if (A == 0 && B == 0 && C == 0) {
                break;
            }
            
            int n = scanner.nextInt();
            double[] holes = new double[n];
            
            for (int i = 0; i < n; i++) {
                holes[i] = scanner.nextDouble();
            }
            
            int min1 = Math.min(A, Math.min(B, C));
            int min2 = Math.min(Math.max(A, B), Math.min(Math.max(A, C), Math.max(B, C)));
            double minDiameter = Math.sqrt(min1 * min1 + min2 * min2);
            
            for (double r : holes) {
                if (2 * r > minDiameter) {
                    System.out.println("OK");
                } else {
                    System.out.println("NA");
                }
            }
        }
    }
}