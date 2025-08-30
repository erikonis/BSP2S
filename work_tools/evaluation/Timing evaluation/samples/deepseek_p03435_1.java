import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int[][] c = new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                c[i][j] = sc.nextInt();
            }
        }
        
        boolean isValid = true;
        // Check row differences
        for (int i = 0; i < 2; i++) {
            int diff1 = c[i][0] - c[i+1][0];
            int diff2 = c[i][1] - c[i+1][1];
            int diff3 = c[i][2] - c[i+1][2];
            if (diff1 != diff2 || diff2 != diff3) {
                isValid = false;
                break;
            }
        }
        
        // Check column differences
        for (int j = 0; j < 2; j++) {
            int diff1 = c[0][j] - c[0][j+1];
            int diff2 = c[1][j] - c[1][j+1];
            int diff3 = c[2][j] - c[2][j+1];
            if (diff1 != diff2 || diff2 != diff3) {
                isValid = false;
                break;
            }
        }
        
        System.out.println(isValid ? "Yes" : "No");
    }
}