import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        int A = sc.nextInt() - 1;
        int B = sc.nextInt() - 1;
        int C = sc.nextInt() - 1;
        int D = sc.nextInt() - 1;
        String S = sc.next();

        // Check for impossible paths (two consecutive rocks)
        for (int i = Math.min(A, B); i < Math.max(C, D); ++i) {
            if (i + 1 < N && S.charAt(i) == '#' && S.charAt(i + 1) == '#') {
                System.out.println("No");
                return;
            }
        }

        // If Fnuke needs to overtake Snuke (C < D), check for 3 consecutive empty cells
        if (C > D) {
            boolean canOvertake = false;
            for (int i = B - 1; i <= D + 1 && i + 2 < N; ++i) {
                if (S.charAt(i) == '.' && S.charAt(i + 1) == '.' && S.charAt(i + 2) == '.') {
                    canOvertake = true;
                    break;
                }
            }
            if (!canOvertake) {
                System.out.println("No");
                return;
            }
        }

        System.out.println("Yes");
    }
}