import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int A = sc.nextInt() - 1;
        int B = sc.nextInt() - 1;
        int C = sc.nextInt() - 1;
        int D = sc.nextInt() - 1;
        String S = sc.next();
        
        if (C > D) {
            boolean canJump = false;
            for (int i = B; i <= D; i++) {
                if (i - 1 >= 0 && i + 1 < N && S.charAt(i - 1) == '.' && S.charAt(i) == '.' && S.charAt(i + 1) == '.') {
                    canJump = true;
                    break;
                }
            }
            if (!canJump) {
                System.out.println("No");
                return;
            }
        }
        
        for (int i = A + 1; i < C; i++) {
            if (S.charAt(i) == '#' && S.charAt(i + 1) == '#') {
                System.out.println("No");
                return;
            }
        }
        
        for (int i = B + 1; i < D; i++) {
            if (S.charAt(i) == '#' && S.charAt(i + 1) == '#') {
                System.out.println("No");
                return;
            }
        }
        
        System.out.println("Yes");
    }
}