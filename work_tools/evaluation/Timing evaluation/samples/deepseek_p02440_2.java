import java.util.Scanner;

public class MinMaxElement {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        int n = scanner.nextInt();
        int[] A = new int[n];
        for (int i = 0; i < n; i++) {
            A[i] = scanner.nextInt();
        }
        
        int q = scanner.nextInt();
        for (int i = 0; i < q; i++) {
            int com = scanner.nextInt();
            int b = scanner.nextInt();
            int e = scanner.nextInt();
            
            if (com == 0) { // min
                int min = A[b];
                for (int j = b + 1; j < e; j++) {
                    if (A[j] < min) {
                        min = A[j];
                    }
                }
                System.out.println(min);
            } else if (com == 1) { // max
                int max = A[b];
                for (int j = b + 1; j < e; j++) {
                    if (A[j] > max) {
                        max = A[j];
                    }
                }
                System.out.println(max);
            }
        }
    }
}