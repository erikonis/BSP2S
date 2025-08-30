import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        int A = sc.nextInt();
        int B = sc.nextInt();
        String S = sc.next();

        int passCount = 0;  // total students passed
        int foreignPassed = 0; // overseas students passed

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < N; i++) {
            char ch = S.charAt(i);
            if (ch == 'a') {
                if (passCount < A + B) {
                    sb.append("Yes\n");
                    passCount++;
                } else {
                    sb.append("No\n");
                }
            } else if (ch == 'b') {
                if (passCount < A + B && foreignPassed < B) {
                    sb.append("Yes\n");
                    passCount++;
                    foreignPassed++;
                } else {
                    sb.append("No\n");
                }
            } else {
                sb.append("No\n");
            }
        }
        System.out.print(sb);
    }
}