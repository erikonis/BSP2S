import java.util.Scanner;

public class MatrixMultiplication {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        int l = sc.nextInt();
        
        int[][] A = new int[n][m];
        int[][] B = new int[m][l];
        long[][] C = new long[n][l];
        
        // Read matrix A
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                A[i][j] = sc.nextInt();
            }
        }
        
        // Read matrix B
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < l; j++) {
                B[i][j] = sc.nextInt();
            }
        }
        
        // Multiply matrices
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < l; j++) {
                long sum = 0;
                for (int k = 0; k < m; k++) {
                    sum += A[i][k] * B[k][j];
                }
                C[i][j] = sum;
            }
        }
        
        // Print result
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < l; j++) {
                if (j != 0) {
                    System.out.print(" ");
                }
                System.out.print(C[i][j]);
            }
            System.out.println();
        }
    }
}